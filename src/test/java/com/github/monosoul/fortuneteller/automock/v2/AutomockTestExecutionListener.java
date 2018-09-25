package com.github.monosoul.fortuneteller.automock.v2;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static org.springframework.core.ResolvableType.forClass;
import static org.springframework.core.ResolvableType.forConstructorParameter;
import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;
import com.github.monosoul.fortuneteller.automock.Automocked;
import java.lang.reflect.Constructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

@SuppressWarnings("Duplicates")
@Slf4j
public class AutomockTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public int getOrder() {
        return 1000;
    }

    @Override
    public void prepareTestInstance(final TestContext testContext) {
        val beanFactory = ((DefaultListableBeanFactory) testContext.getApplicationContext().getAutowireCapableBeanFactory());
        setByNameCandidateResolver(beanFactory);

        for (val field : testContext.getTestClass().getDeclaredFields()) {
            if (field.getAnnotation(Automocked.class) == null) {
                continue;
            }
            log.debug("Performing automocking for the field: {}", field.getName());

            makeAccessible(field);
            setField(
                    field,
                    testContext.getTestInstance(),
                    createBeanWithMocks(findConstructorToAutomock(field.getType()), beanFactory)
            );
        }
    }

    private void setByNameCandidateResolver(final DefaultListableBeanFactory beanFactory) {
        if ((beanFactory.getAutowireCandidateResolver() instanceof MockedBeanByNameAutowireCandidateResolver)) {
            return;
        }
        beanFactory.setAutowireCandidateResolver(
                new MockedBeanByNameAutowireCandidateResolver(beanFactory.getAutowireCandidateResolver())
        );
    }

    private Constructor<?> findConstructorToAutomock(final Class<?> clazz) {
        log.debug("Looking for suitable constructor of {}", clazz.getCanonicalName());

        var fallBackConstructor = clazz.getDeclaredConstructors()[0];
        for (val constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length > fallBackConstructor.getParameterTypes().length) {
                fallBackConstructor = constructor;
            }

            val autowired = getAnnotation(constructor, Autowired.class);
            if (autowired != null) {
                return constructor;
            }
        }

        return fallBackConstructor;
    }

    private <T> T createBeanWithMocks(final Constructor<T> constructor, final DefaultListableBeanFactory beanFactory) {
        createMocksForParameters(constructor, beanFactory);

        val clazz = constructor.getDeclaringClass();
        val beanName = forClass(clazz).toString();
        log.debug("Creating bean {}", beanName);

        val bean = beanFactory.createBean(clazz);
        beanFactory.registerSingleton(beanName, bean);

        return bean;
    }

    private <T> void createMocksForParameters(final Constructor<T> constructor, final DefaultListableBeanFactory beanFactory) {
        log.debug("{} is going to be used for auto mocking", constructor);

        val constructorArgsAmount = constructor.getParameterTypes().length;

        for (var i = 0; i < constructorArgsAmount; i++) {
            val parameterType = forConstructorParameter(constructor, i);
            val beanName = parameterType.toString();

            if (!beanFactory.containsBean(beanName)) {
                beanFactory.registerSingleton(
                        beanName,
                        mock(parameterType.resolve(), withSettings().stubOnly())
                );
            }

            log.debug("Mocked {}", beanName);
        }
    }
}

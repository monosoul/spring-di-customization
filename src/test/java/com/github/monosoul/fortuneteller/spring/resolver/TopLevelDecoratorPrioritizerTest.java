package com.github.monosoul.fortuneteller.spring.resolver;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

class TopLevelDecoratorPrioritizerTest {

    private static final int LIMIT = 10;

    @Mock
    private Function<String, DecoratorType> decoratorTypeDeterminer;
    @Mock
    private ConfigurableListableBeanFactory beanFactory;
    @Mock
    private BeanDefinition beanDefinition;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(beanFactory.getBeanDefinition(anyString())).thenReturn(beanDefinition);
    }

    @Test
    void skipWhenBeanClassNameIsNull() {
        val beanName = randomAlphabetic(LIMIT);

        when(beanFactory.getBeanDefinitionNames()).thenReturn(new String[]{beanName});
        when(beanDefinition.getBeanClassName()).thenReturn(null);

        new TopLevelDecoratorPrioritizer(decoratorTypeDeterminer).accept(beanFactory);

        verify(beanFactory).getBeanDefinitionNames();
        verify(beanFactory).getBeanDefinition(beanName);
        verify(beanDefinition).getBeanClassName();
        verifyNoMoreInteractions(beanFactory, beanDefinition);
        verifyZeroInteractions(decoratorTypeDeterminer);
    }

    @ParameterizedTest
    @MethodSource("beanNameAndClassNameStream")
    void applyWhenBeanIsTopLevelDecorator(final String beanName, final String beanClassName) {
        when(beanFactory.getBeanDefinitionNames()).thenReturn(new String[]{beanName});
        when(beanDefinition.getBeanClassName()).thenReturn(beanClassName);
        when(decoratorTypeDeterminer.apply(beanClassName)).thenReturn(topLevelDecoratorType());

        new TopLevelDecoratorPrioritizer(decoratorTypeDeterminer).accept(beanFactory);

        verify(beanFactory).getBeanDefinitionNames();
        verify(beanFactory).getBeanDefinition(beanName);
        verify(beanDefinition).getBeanClassName();
        verify(decoratorTypeDeterminer).apply(beanClassName);
        verify(beanDefinition).setPrimary(true);
        verifyNoMoreInteractions(beanFactory, decoratorTypeDeterminer, beanDefinition);
    }

    @ParameterizedTest
    @MethodSource("beanNameAndClassNameStream")
    void doNothingWhenBeanIsNotTopLevelDecorator(final String beanName, final String beanClassName) {
        when(beanFactory.getBeanDefinitionNames()).thenReturn(new String[]{beanName});
        when(beanDefinition.getBeanClassName()).thenReturn(beanClassName);
        when(decoratorTypeDeterminer.apply(beanClassName)).thenReturn(notTopLevelDecoratorType());

        new TopLevelDecoratorPrioritizer(decoratorTypeDeterminer).accept(beanFactory);

        verify(beanFactory).getBeanDefinitionNames();
        verify(beanFactory).getBeanDefinition(beanName);
        verify(beanDefinition).getBeanClassName();
        verify(decoratorTypeDeterminer).apply(beanClassName);
        verifyNoMoreInteractions(beanFactory, decoratorTypeDeterminer, beanDefinition);
    }

    private DecoratorType topLevelDecoratorType() {
        return DecoratorType.values()[0];
    }

    private DecoratorType notTopLevelDecoratorType() {
        val decoratorTypes = DecoratorType.values();

        return decoratorTypes[nextInt(1, decoratorTypes.length)];
    }

    private static Stream<Arguments> beanNameAndClassNameStream() {
        return generate(() -> of(
                randomAlphabetic(LIMIT),
                randomAlphabetic(LIMIT)
        )).limit(LIMIT);
    }
}
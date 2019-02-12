package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.util.Util.randomEnum;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
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

class DependencyMapProviderTest {

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

        val actual = new DependencyMapProvider(decoratorTypeDeterminer).apply(beanFactory);

        verify(beanFactory).getBeanDefinitionNames();
        verify(beanFactory).getBeanDefinition(beanName);
        verifyNoMoreInteractions(beanFactory);
        verifyZeroInteractions(decoratorTypeDeterminer);

        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("decoratorTypeBeanNameAndClassNameStream")
    void apply(final DecoratorType decoratorType, final String beanName, final String beanClassName) {
        when(beanFactory.getBeanDefinitionNames()).thenReturn(new String[]{beanName});
        when(beanDefinition.getBeanClassName()).thenReturn(beanClassName);
        when(decoratorTypeDeterminer.apply(beanClassName)).thenReturn(decoratorType);

        val actual = new DependencyMapProvider(decoratorTypeDeterminer).apply(beanFactory);

        verify(beanFactory).getBeanDefinitionNames();
        verify(beanFactory).getBeanDefinition(beanName);
        verify(decoratorTypeDeterminer).apply(beanClassName);
        verifyNoMoreInteractions(beanFactory, decoratorTypeDeterminer);

        assertThat(actual).isNotNull()
                          .hasSize(1);
        assertThat(actual.get(decoratorType)).isNotNull()
                                             .hasSize(1)
                                             .containsExactly(beanName);
    }

    private static Stream<Arguments> decoratorTypeBeanNameAndClassNameStream() {
        return generate(() -> of(
                randomEnum(DecoratorType.class),
                randomAlphabetic(LIMIT),
                randomAlphabetic(LIMIT)
        )).limit(LIMIT);
    }
}
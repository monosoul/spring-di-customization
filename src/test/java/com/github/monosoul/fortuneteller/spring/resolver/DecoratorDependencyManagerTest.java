package com.github.monosoul.fortuneteller.spring.resolver;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

class DecoratorDependencyManagerTest {

    private static final int LIMIT = 10;

    @Mock
    private Map<DecoratorType, Set<String>> dependencies;
    @Mock
    private Set<String> decoratorTypeDependencies;
    @Mock
    private Set<String> foundDecoratorsContainer;
    @Mock
    private Function<String, DecoratorType> decoratorTypeDeterminer;
    @Mock
    private BeanDefinitionHolder beanDefinitionHolder;
    private DecoratorDependencyManager dependencyManager;

    @BeforeEach
    void setUp() {
        initMocks(this);

        dependencyManager = new DecoratorDependencyManager(dependencies, foundDecoratorsContainer, decoratorTypeDeterminer);

        when(dependencies.get(any(DecoratorType.class))).thenReturn(decoratorTypeDependencies);
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Test
    void alreadyFound() {
        val clazz = Object.class;

        when(foundDecoratorsContainer.contains(anyString())).thenReturn(true);

        val actual = dependencyManager.alreadyFound(clazz);

        verify(foundDecoratorsContainer).contains(clazz.getName());
        verifyNoMoreInteractions(foundDecoratorsContainer);
        verifyZeroInteractions(dependencies, decoratorTypeDeterminer);

        assertThat(actual).isTrue();
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    @Test
    void notFoundYet() {
        val clazz = Object.class;

        when(foundDecoratorsContainer.contains(anyString())).thenReturn(false);

        val actual = dependencyManager.alreadyFound(clazz);

        verify(foundDecoratorsContainer).contains(clazz.getName());
        verifyNoMoreInteractions(foundDecoratorsContainer);
        verifyZeroInteractions(dependencies, decoratorTypeDeterminer);

        assertThat(actual).isFalse();
    }

    @Test
    void isNotChildDecoratorIfDependentClassIsBottomLevelDecorator() {
        val clazz = Object.class;

        when(beanDefinitionHolder.getBeanName()).thenReturn(randomAlphabetic(LIMIT));
        when(decoratorTypeDeterminer.apply(anyString())).thenReturn(bottomLevelDecoratorType());

        val actual = dependencyManager.isChildDecorator(beanDefinitionHolder, clazz);

        verify(decoratorTypeDeterminer).apply(clazz.getSimpleName());
        verifyNoMoreInteractions(decoratorTypeDeterminer);
        verifyZeroInteractions(dependencies, foundDecoratorsContainer);

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @MethodSource("decoratorTypeAndBeanNameStream")
    void isNotChildDecoratorIfDependencyDecoratorTypeIsBottom(final DecoratorType decoratorType, final String beanName) {
        val clazz = Object.class;

        when(beanDefinitionHolder.getBeanName()).thenReturn(beanName);
        when(decoratorTypeDeterminer.apply(anyString())).thenReturn(decoratorType);
        when(decoratorTypeDependencies.contains(beanName)).thenReturn(false);

        val actual = dependencyManager.isChildDecorator(beanDefinitionHolder, clazz);

        verify(beanDefinitionHolder).getBeanName();
        verify(decoratorTypeDeterminer).apply(clazz.getSimpleName());
        verify(decoratorTypeDependencies, atLeastOnce()).contains(beanName);
        verifyNoMoreInteractions(beanDefinitionHolder, decoratorTypeDeterminer, decoratorTypeDependencies);
        verifyZeroInteractions(foundDecoratorsContainer);

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @MethodSource("decoratorTypeAndBeanNameStream")
    void isChildDecorator(final DecoratorType decoratorType, final String beanName) {
        val clazz = Object.class;

        when(beanDefinitionHolder.getBeanName()).thenReturn(beanName);
        when(decoratorTypeDeterminer.apply(anyString())).thenReturn(decoratorType);
        when(decoratorTypeDependencies.contains(beanName)).thenReturn(true);

        val actual = dependencyManager.isChildDecorator(beanDefinitionHolder, clazz);

        verify(beanDefinitionHolder).getBeanName();
        verify(decoratorTypeDeterminer).apply(clazz.getSimpleName());
        verify(decoratorTypeDependencies).contains(beanName);
        verify(foundDecoratorsContainer).add(clazz.getName());
        verifyNoMoreInteractions(beanDefinitionHolder, decoratorTypeDeterminer, decoratorTypeDependencies, foundDecoratorsContainer);

        assertThat(actual).isTrue();
    }

    private static DecoratorType bottomLevelDecoratorType() {
        val decoratorTypes = DecoratorType.values();

        return decoratorTypes[decoratorTypes.length - 1];
    }

    private static DecoratorType notBottomLevelDecoratorType() {
        val decoratorTypes = DecoratorType.values();

        return decoratorTypes[nextInt(0, decoratorTypes.length - 1)];
    }

    private static Stream<Arguments> decoratorTypeAndBeanNameStream() {
        return generate(() -> (Arguments) () -> new Object[]{notBottomLevelDecoratorType(), randomAlphabetic(LIMIT)}).limit(LIMIT);
    }
}
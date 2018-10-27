package com.github.monosoul.fortuneteller.spring.order;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.lang.reflect.Member;
import java.util.stream.Stream;
import lombok.val;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

class DecoratorAutowireCandidateResolverTest {

    private static final int LIMIT = 10;

    @Mock
    private AutowireCandidateResolver resolver;
    @Mock
    private BeanDefinitionHolder bdHolder;
    @Mock
    private AbstractBeanDefinition beanDefinition;
    @Mock
    private AutowireCandidateQualifier candidateQualifier;
    @Mock
    private DependencyDescriptor descriptor;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(bdHolder.getBeanDefinition()).thenReturn(beanDefinition);
    }

    @ParameterizedTest
    @MethodSource("booleanStream")
    void fallbackToInternalResolverIfDependentIsNotInstanceOfDependency(final Boolean result) {
        when(resolver.isAutowireCandidate(bdHolder, descriptor)).thenReturn(result);
        stubDependencyDescriptor(NotAssignableClass.class, SomeInterface.class);

        val actual = new DecoratorAutowireCandidateResolver(resolver).isAutowireCandidate(bdHolder, descriptor);

        verify(resolver).isAutowireCandidate(bdHolder, descriptor);
        verifyNoMoreInteractions(resolver);

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("booleanStream")
    void fallbackToInternalResolverIfBeanDefinitionDoesNotHaveClass(final Boolean result) {
        when(resolver.isAutowireCandidate(bdHolder, descriptor)).thenReturn(result);
        stubDependencyDescriptor(AssignableClass.class, SomeInterface.class);
        when(beanDefinition.hasBeanClass()).thenReturn(false);

        val actual = new DecoratorAutowireCandidateResolver(resolver).isAutowireCandidate(bdHolder, descriptor);

        verify(resolver).isAutowireCandidate(bdHolder, descriptor);
        verifyNoMoreInteractions(resolver);

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("booleanStream")
    void fallbackToInternalResolverIfBeanIsNotInstanceOfDependency(final Boolean result) {
        when(resolver.isAutowireCandidate(bdHolder, descriptor)).thenReturn(result);
        stubDependencyDescriptor(AssignableClass.class, SomeInterface.class);
        when(beanDefinition.hasBeanClass()).thenReturn(true);
        doReturn(NotAssignableClass.class).when(beanDefinition).getBeanClass();

        val actual = new DecoratorAutowireCandidateResolver(resolver).isAutowireCandidate(bdHolder, descriptor);

        verify(resolver).isAutowireCandidate(bdHolder, descriptor);
        verifyNoMoreInteractions(resolver);

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("booleanStream")
    void fallbackToInternalResolverIfCandidateQualifierIsNull(final Boolean result) {
        when(resolver.isAutowireCandidate(bdHolder, descriptor)).thenReturn(result);
        stubDependencyDescriptor(AssignableClass.class, SomeInterface.class);
        when(beanDefinition.hasBeanClass()).thenReturn(true);
        doReturn(AnotherAssignableClass.class).when(beanDefinition).getBeanClass();
        when(beanDefinition.getQualifier(anyString())).thenReturn(null);

        val actual = new DecoratorAutowireCandidateResolver(resolver).isAutowireCandidate(bdHolder, descriptor);

        verify(resolver).isAutowireCandidate(bdHolder, descriptor);
        verifyNoMoreInteractions(resolver);

        assertThat(actual).isEqualTo(result);
    }

    @Test
    void trueIfDependentTypeIsSameAsInTheValueOfTheQualifier() {
        stubDependencyDescriptor(AssignableClass.class, SomeInterface.class);
        when(beanDefinition.hasBeanClass()).thenReturn(true);
        doReturn(AnotherAssignableClass.class).when(beanDefinition).getBeanClass();
        when(beanDefinition.getQualifier(anyString())).thenReturn(candidateQualifier);
        when(candidateQualifier.getAttribute("value")).thenReturn(AssignableClass.class.getTypeName());

        val actual = new DecoratorAutowireCandidateResolver(resolver).isAutowireCandidate(bdHolder, descriptor);

        verifyZeroInteractions(resolver);

        assertThat(actual).isTrue();
    }

    @Test
    void falseIfDependentTypeIsNotTheSameAsInTheValueOfTheQualifier() {
        stubDependencyDescriptor(AssignableClass.class, SomeInterface.class);
        when(beanDefinition.hasBeanClass()).thenReturn(true);
        doReturn(AnotherAssignableClass.class).when(beanDefinition).getBeanClass();
        when(beanDefinition.getQualifier(anyString())).thenReturn(candidateQualifier);
        when(candidateQualifier.getAttribute("value")).thenReturn(randomAlphabetic(LIMIT));

        val actual = new DecoratorAutowireCandidateResolver(resolver).isAutowireCandidate(bdHolder, descriptor);

        verifyZeroInteractions(resolver);

        assertThat(actual).isFalse();
    }


    @Test
    void isRequired() {
        new DecoratorAutowireCandidateResolver(resolver).isRequired(descriptor);

        verify(resolver).isRequired(descriptor);
        verifyNoMoreInteractions(resolver);
    }

    @Test
    void getSuggestedValue() {
        new DecoratorAutowireCandidateResolver(resolver).getSuggestedValue(descriptor);

        verify(resolver).getSuggestedValue(descriptor);
        verifyNoMoreInteractions(resolver);
    }

    @Test
    void getLazyResolutionProxyIfNecessary() {
        val beanName = randomAlphabetic(LIMIT);

        new DecoratorAutowireCandidateResolver(resolver).getLazyResolutionProxyIfNecessary(descriptor, beanName);

        verify(resolver).getLazyResolutionProxyIfNecessary(descriptor, beanName);
        verifyNoMoreInteractions(resolver);
    }

    private void stubDependencyDescriptor(final Class<?> dependentType, final Class<?> dependencyType) {
        val member = mock(Member.class);

        doReturn(member).when(descriptor).getMember();
        doReturn(dependentType).when(member).getDeclaringClass();
        doReturn(dependencyType).when(descriptor).getDependencyType();
    }

    private static Stream<Boolean> booleanStream() {
        return generate(RandomUtils::nextBoolean).limit(LIMIT);
    }

    private interface SomeInterface {

    }

    private static class AssignableClass implements SomeInterface {

    }

    private static class NotAssignableClass {

    }

    private static class AnotherAssignableClass implements SomeInterface {

    }
}
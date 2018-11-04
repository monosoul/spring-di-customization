package com.github.monosoul.fortuneteller.spring.resolver;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.lang.reflect.Member;
import java.util.function.Predicate;
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
import org.springframework.beans.factory.support.AutowireCandidateResolver;

class DecoratorAutowireCandidateResolverTest {

    private static final int LIMIT = 10;

    @Mock
    private AutowireCandidateResolver internalResolver;
    @Mock
    private Predicate<DependencyDescriptor> isSameTypeAsDependent;
    @Mock
    private DependencyManager dependencyManager;
    @Mock
    private BeanDefinitionHolder bdHolder;
    @Mock
    private DependencyDescriptor descriptor;
    @Mock
    private Member member;
    private Class<?> clazz;
    private DecoratorAutowireCandidateResolver candidateResolver;

    @BeforeEach
    void setUp() {
        initMocks(this);

        clazz = Object.class;
        candidateResolver = new DecoratorAutowireCandidateResolver(internalResolver, isSameTypeAsDependent, dependencyManager);

        doReturn(member).when(descriptor).getMember();
        doReturn(clazz).when(member).getDeclaringClass();
    }

    @ParameterizedTest
    @MethodSource("booleanStream")
    void fallBackToInternalResolverIfDependencyAndDependentHasDifferentTypes(final Boolean internalResolverResponse) {
        when(isSameTypeAsDependent.test(descriptor)).thenReturn(false);
        when(internalResolver.isAutowireCandidate(bdHolder, descriptor)).thenReturn(internalResolverResponse);

        val actual = candidateResolver.isAutowireCandidate(bdHolder, descriptor);

        verify(isSameTypeAsDependent).test(descriptor);
        verify(internalResolver).isAutowireCandidate(bdHolder, descriptor);
        verifyNoMoreInteractions(isSameTypeAsDependent, internalResolver);
        verifyZeroInteractions(dependencyManager);

        assertThat(actual).isEqualTo(internalResolverResponse);
    }

    @Test
    void isNotCandidateIfDecoratorDependencyIsAlreadyFound() {
        when(isSameTypeAsDependent.test(descriptor)).thenReturn(true);
        when(dependencyManager.alreadyFound(any(Class.class))).thenReturn(true);

        val actual = candidateResolver.isAutowireCandidate(bdHolder, descriptor);

        verify(isSameTypeAsDependent).test(descriptor);
        verify(dependencyManager).alreadyFound(clazz);
        verifyNoMoreInteractions(isSameTypeAsDependent, dependencyManager);
        verifyZeroInteractions(internalResolver);

        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @MethodSource("booleanStream")
    void fallBackToInternalResolverIfNotChildDecorator(final Boolean internalResolverResponse) {
        when(isSameTypeAsDependent.test(descriptor)).thenReturn(true);
        when(dependencyManager.alreadyFound(any(Class.class))).thenReturn(false);
        when(dependencyManager.isChildDecorator(bdHolder, clazz)).thenReturn(false);
        when(internalResolver.isAutowireCandidate(bdHolder, descriptor)).thenReturn(internalResolverResponse);

        val actual = candidateResolver.isAutowireCandidate(bdHolder, descriptor);

        verify(isSameTypeAsDependent).test(descriptor);
        verify(dependencyManager).alreadyFound(clazz);
        verify(dependencyManager).isChildDecorator(bdHolder, clazz);
        verify(internalResolver).isAutowireCandidate(bdHolder, descriptor);
        verifyNoMoreInteractions(isSameTypeAsDependent, dependencyManager, internalResolver);

        assertThat(actual).isEqualTo(internalResolverResponse);
    }

    @Test
    void isAutowireCandidate() {
        when(isSameTypeAsDependent.test(descriptor)).thenReturn(true);
        when(dependencyManager.alreadyFound(any(Class.class))).thenReturn(false);
        when(dependencyManager.isChildDecorator(bdHolder, clazz)).thenReturn(true);

        val actual = candidateResolver.isAutowireCandidate(bdHolder, descriptor);

        verify(isSameTypeAsDependent).test(descriptor);
        verify(dependencyManager).alreadyFound(clazz);
        verify(dependencyManager).isChildDecorator(bdHolder, clazz);
        verifyNoMoreInteractions(isSameTypeAsDependent, dependencyManager);
        verifyZeroInteractions(internalResolver);

        assertThat(actual).isTrue();
    }

    @Test
    void isRequired() {
        candidateResolver.isRequired(descriptor);

        verify(internalResolver).isRequired(descriptor);
        verifyNoMoreInteractions(internalResolver);
        verifyZeroInteractions(isSameTypeAsDependent, dependencyManager);
    }

    @Test
    void getSuggestedValue() {
        candidateResolver.getSuggestedValue(descriptor);

        verify(internalResolver).getSuggestedValue(descriptor);
        verifyNoMoreInteractions(internalResolver);
        verifyZeroInteractions(isSameTypeAsDependent, dependencyManager);
    }

    @ParameterizedTest
    @MethodSource("stringStream")
    void getLazyResolutionProxyIfNecessary(final String beanName) {
        candidateResolver.getLazyResolutionProxyIfNecessary(descriptor, beanName);

        verify(internalResolver).getLazyResolutionProxyIfNecessary(descriptor, beanName);
        verifyNoMoreInteractions(internalResolver);
        verifyZeroInteractions(isSameTypeAsDependent, dependencyManager);
    }

    private static Stream<String> stringStream() {
        return generate(() -> randomAlphabetic(LIMIT)).limit(LIMIT);
    }

    private static Stream<Boolean> booleanStream() {
        return generate(RandomUtils::nextBoolean).limit(LIMIT);
    }
}
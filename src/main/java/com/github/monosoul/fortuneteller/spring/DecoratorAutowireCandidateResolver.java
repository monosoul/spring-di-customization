package com.github.monosoul.fortuneteller.spring;

import lombok.NonNull;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class DecoratorAutowireCandidateResolver implements AutowireCandidateResolver {

    private final AutowireCandidateResolver resolver;

    public DecoratorAutowireCandidateResolver(
            @NonNull final DefaultListableBeanFactory beanFactory
    ) {
        this.resolver = beanFactory.getAutowireCandidateResolver();
        buildDependencies(beanFactory);
    }

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder bdHolder, final DependencyDescriptor descriptor) {
        return false;
    }

    @Override
    public boolean isRequired(final DependencyDescriptor descriptor) {
        return false;
    }

    @Override
    public Object getSuggestedValue(final DependencyDescriptor descriptor) {
        return null;
    }

    @Override
    public Object getLazyResolutionProxyIfNecessary(final DependencyDescriptor descriptor, final String beanName) {
        return null;
    }

    private void buildDependencies(final DefaultListableBeanFactory beanFactory) {
    }
}

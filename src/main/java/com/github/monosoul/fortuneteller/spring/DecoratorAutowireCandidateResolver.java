package com.github.monosoul.fortuneteller.spring;

import lombok.NonNull;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;

public class DecoratorAutowireCandidateResolver implements AutowireCandidateResolver {

    private final AutowireCandidateResolver resolver;

    public DecoratorAutowireCandidateResolver(
            @NonNull final AutowireCandidateResolver resolver,
            @NonNull final BeanFactory beanFactory
    ) {
        Assert.state(beanFactory instanceof DefaultListableBeanFactory,
                "BeanFactory needs to be a DefaultListableBeanFactory");
        this.resolver = resolver;
        buildDependencies((DefaultListableBeanFactory) beanFactory);
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

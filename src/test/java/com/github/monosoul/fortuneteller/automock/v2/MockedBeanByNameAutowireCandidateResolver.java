package com.github.monosoul.fortuneteller.automock.v2;

import static java.util.Objects.requireNonNull;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class MockedBeanByNameAutowireCandidateResolver implements AutowireCandidateResolver {

    private final AutowireCandidateResolver candidateResolver;

    public MockedBeanByNameAutowireCandidateResolver(final AutowireCandidateResolver candidateResolver) {
        this.candidateResolver = requireNonNull(candidateResolver);
    }

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder beanDefinitionHolder, final DependencyDescriptor descriptor) {
        final String expectedName = descriptor.getResolvableType().toString();
        final RootBeanDefinition rootBeanDefinition = (RootBeanDefinition) beanDefinitionHolder.getBeanDefinition();
        final String actualName = beanDefinitionHolder.getBeanName();

        if (actualName.equals(expectedName) && rootBeanDefinition.getBeanClass() != null
            && descriptor.getResolvableType().resolve().isAssignableFrom(rootBeanDefinition.getBeanClass())) {
            return true;
        }

        return candidateResolver.isAutowireCandidate(beanDefinitionHolder, descriptor);
    }

    @Override
    public Object getSuggestedValue(final DependencyDescriptor descriptor) {
        return candidateResolver.getSuggestedValue(descriptor);
    }

    @Override
    public Object getLazyResolutionProxyIfNecessary(final DependencyDescriptor descriptor, final String beanName) {
        return candidateResolver.getLazyResolutionProxyIfNecessary(descriptor, beanName);
    }
}

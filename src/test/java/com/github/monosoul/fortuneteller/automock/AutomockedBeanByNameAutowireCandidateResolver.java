package com.github.monosoul.fortuneteller.automock;

import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

public class AutomockedBeanByNameAutowireCandidateResolver implements AutowireCandidateResolver {

    private final AutowireCandidateResolver candidateResolver;

    public AutomockedBeanByNameAutowireCandidateResolver(@NonNull final AutowireCandidateResolver candidateResolver) {
        this.candidateResolver = candidateResolver;
    }

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder beanDefinitionHolder, final DependencyDescriptor descriptor) {
        val dependencyType = descriptor.getResolvableType().resolve();
        val dependencyTypeName = descriptor.getResolvableType().toString();

        val candidateBeanDefinition = (AbstractBeanDefinition) beanDefinitionHolder.getBeanDefinition();
        val candidateTypeName = beanDefinitionHolder.getBeanName();

        if (candidateTypeName.equals(dependencyTypeName) && candidateBeanDefinition.getBeanClass() != null
            && dependencyType != null && dependencyType.isAssignableFrom(candidateBeanDefinition.getBeanClass())
        ) {
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

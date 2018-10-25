package com.github.monosoul.fortuneteller.spring.order;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

@RequiredArgsConstructor
public final class DecoratorAutowireCandidateResolver implements AutowireCandidateResolver {

    private final AutowireCandidateResolver resolver;

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder bdHolder, final DependencyDescriptor descriptor) {
        val dependentType = descriptor.getMember().getDeclaringClass();
        val dependencyType = descriptor.getDependencyType();
        val candidateBeanDefinition = (AbstractBeanDefinition) bdHolder.getBeanDefinition();

        if (dependencyType.isAssignableFrom(dependentType) && candidateBeanDefinition.hasBeanClass()
            && dependencyType.isAssignableFrom(candidateBeanDefinition.getBeanClass())) {
            val candidateQualifier = candidateBeanDefinition.getQualifier(OrderQualifier.class.getTypeName());
            if (candidateQualifier != null) {
                return dependentType.getTypeName().equals(candidateQualifier.getAttribute("value"));
            }
        }

        return resolver.isAutowireCandidate(bdHolder, descriptor);
    }

    @Override
    public boolean isRequired(final DependencyDescriptor descriptor) {
        return resolver.isRequired(descriptor);
    }

    @Override
    public Object getSuggestedValue(final DependencyDescriptor descriptor) {
        return resolver.getSuggestedValue(descriptor);
    }

    @Override
    public Object getLazyResolutionProxyIfNecessary(final DependencyDescriptor descriptor, final String beanName) {
        return resolver.getLazyResolutionProxyIfNecessary(descriptor, beanName);
    }
}

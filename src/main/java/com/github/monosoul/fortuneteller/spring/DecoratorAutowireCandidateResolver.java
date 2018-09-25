package com.github.monosoul.fortuneteller.spring;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
class DecoratorAutowireCandidateResolver implements AutowireCandidateResolver {

    private final Map<Decorator, Set<String>> dependencies = new HashMap<>();
    private final AutowireCandidateResolver resolver;

    DecoratorAutowireCandidateResolver(@NonNull final DefaultListableBeanFactory beanFactory) {
        this.resolver = beanFactory.getAutowireCandidateResolver();
        buildDependencies(beanFactory);
    }

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder bdHolder, final DependencyDescriptor descriptor) {
        return false;
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

    private void buildDependencies(final DefaultListableBeanFactory beanFactory) {
        log.debug("Building dependencies");
        for (val beanName : beanFactory.getBeanDefinitionNames()) {
            log.debug("Determining decorator type for bean: {}", beanName);
            val beanDefinition = beanFactory.getBeanDefinition(beanName);
            if (beanDefinition.getBeanClassName() == null) {
                log.debug("{} doesn't have class name", beanName);
                break;
            }

            val beanClassSimpleName = getSimpleName(beanDefinition.getBeanClassName());
            log.debug("{} simple class name is: {}", beanName, beanClassSimpleName);
            for (val decoratorType : Decorator.values()) {
                log.debug("Comparing {} with {} decorator type", beanClassSimpleName, decoratorType);
                if (beanClassSimpleName.startsWith(decoratorType.getPrefix())) {
                    log.debug("Adding {} to dependencies with type {}", beanName, decoratorType);
                    dependencies.computeIfAbsent(decoratorType, d -> new HashSet<>()).add(beanName);
                    break;
                }
            }
        }
    }

    private String getSimpleName(@NonNull final String beanClassName) {
        return beanClassName.replaceAll(".*\\.", "");
    }
}

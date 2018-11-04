package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.spring.resolver.CommonUtils.getDependentClass;
import java.util.function.Predicate;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

@Slf4j
final class DecoratorAutowireCandidateResolver implements AutowireCandidateResolver {

    private final AutowireCandidateResolver resolver;
    private final Predicate<DependencyDescriptor> isSameTypeAsDependent;
    private final DependencyManager dependencyManager;

    DecoratorAutowireCandidateResolver(
            @NonNull final AutowireCandidateResolver resolver,
            @NonNull final Predicate<DependencyDescriptor> isSameTypeAsDependent,
            @NonNull final DependencyManager dependencyManager
    ) {
        this.resolver = resolver;
        this.isSameTypeAsDependent = isSameTypeAsDependent;
        this.dependencyManager = dependencyManager;
    }

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder bdHolder, final DependencyDescriptor descriptor) {
        val dependentClass = getDependentClass(descriptor);
        val dependentClassName = dependentClass.getName();
        log.debug("Looking for dependencies for {}", dependentClassName);

        if (!isSameTypeAsDependent.test(descriptor)) {
            log.debug(
                    "Dependency type differs from dependent type. Delegating to internal resolver ({})",
                    resolver.getClass().getName()
            );
            return resolver.isAutowireCandidate(bdHolder, descriptor);
        }

        if (dependencyManager.alreadyFound(dependentClass)) {
            log.debug("Already found dependency for {}. Skipping.", dependentClassName);
            return false;
        }

        if (dependencyManager.isChildDecorator(bdHolder, dependentClass)) {
            return true;
        }
        log.debug(
                "Candidate is not child decorator. Falling back to internal resolver ({})",
                resolver.getClass().getName()
        );

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

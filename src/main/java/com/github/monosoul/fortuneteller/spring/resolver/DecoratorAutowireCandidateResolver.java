package com.github.monosoul.fortuneteller.spring.resolver;

import com.github.monosoul.fortuneteller.spring.DecoratorType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.NOT_DECORATOR;

@Slf4j
class DecoratorAutowireCandidateResolver implements AutowireCandidateResolver {

    private final Map<DecoratorType, Set<String>> dependencies = new HashMap<>();
    private final Set<String> decoratorsWithFoundDependencies = new HashSet<>();
    private final AutowireCandidateResolver resolver;

    DecoratorAutowireCandidateResolver(@NonNull final DefaultListableBeanFactory beanFactory) {
        this.resolver = beanFactory.getAutowireCandidateResolver();
        buildDependencies(beanFactory);
    }

    @Override
    public boolean isAutowireCandidate(final BeanDefinitionHolder bdHolder, final DependencyDescriptor descriptor) {
        val dependentClass = getDependentClass(descriptor);
        val dependentClassName = dependentClass.getName();
        log.debug("Looking for dependencies for {}", dependentClassName);

        if (!isSameTypeAsDependent(descriptor)) {
            log.debug(
                    "Dependency type differs from dependent type. Delegating to internal resolver ({})",
                    resolver.getClass().getName()
            );
            return resolver.isAutowireCandidate(bdHolder, descriptor);
        }
        if (decoratorsWithFoundDependencies.contains(dependentClassName)) {
            log.debug("Already found dependency for {}. Skipping.", dependentClassName);
            return false;
        }

        primaryIfTopLevel(bdHolder, dependentClass);
        if (isChildDecorator(bdHolder, dependentClass)) {
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
            for (val decoratorType : DecoratorType.values()) {
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

    private Class<?> getDependentClass(final DependencyDescriptor descriptor) {
        return descriptor.getMember().getDeclaringClass();
    }

    private boolean isSameTypeAsDependent(final DependencyDescriptor descriptor) {
        val dependent = getDependentClass(descriptor);

        return descriptor.getDependencyType().isAssignableFrom(dependent);
    }

    private DecoratorType getDecoratorTypeFor(final Class<?> clazz) {
        for (val decoratorType : DecoratorType.values()) {
            if (clazz.getSimpleName().startsWith(decoratorType.getPrefix())) {
                return decoratorType;
            }
        }

        return NOT_DECORATOR;
    }

    private boolean isChildDecorator(final BeanDefinitionHolder bdHolder, final Class<?> dependentClass) {
        val dependentClassName = dependentClass.getName();

        val candidateBeanName = bdHolder.getBeanName();
        log.debug("Candidate bean name is {}", candidateBeanName);

        val dependentDecoratorType = getDecoratorTypeFor(dependentClass);
        val decoratorTypes = DecoratorType.values();
        val lastDecoratorTypeIndex = decoratorTypes.length - 1;
        if (dependentDecoratorType.ordinal() == lastDecoratorTypeIndex) {
            log.debug("{} of decorator type {} has same type dependency. Probably not decorator. Falling back to " +
                    "internal resolver ({})", dependentClassName, dependentDecoratorType, resolver.getClass().getName());
            return false;
        }

        for (var i = dependentDecoratorType.ordinal(); i < decoratorTypes.length; i++) {
            if (i == lastDecoratorTypeIndex) {
                break;
            }
            val dependencyDecoratorType = decoratorTypes[i + 1];
            log.debug("Looking for dependencies of decorator type {}", dependencyDecoratorType);
            if (dependencies.get(dependencyDecoratorType).contains(candidateBeanName)) {
                log.debug(
                        "{} is a decorator type {} dependency for {}",
                        candidateBeanName, dependencyDecoratorType, dependentClassName
                );
                decoratorsWithFoundDependencies.add(dependentClassName);
                return true;
            }
        }

        return false;
    }

    private void primaryIfTopLevel(final BeanDefinitionHolder bdHolder, final Class<?> dependentClass) {
        val dependentClassName = dependentClass.getName();
        val dependentDecoratorType = getDecoratorTypeFor(dependentClass);
        log.debug("{} is {} decorator", dependentClassName, dependentDecoratorType);
        if (dependentDecoratorType.ordinal() == 0) {
            log.debug("{} is top level decorator. Making it primary.", dependentClassName);
            bdHolder.getBeanDefinition().setPrimary(true);
        }
    }
}

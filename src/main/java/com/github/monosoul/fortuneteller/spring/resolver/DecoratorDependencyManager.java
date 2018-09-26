package com.github.monosoul.fortuneteller.spring.resolver;

import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

@Slf4j
final class DecoratorDependencyManager implements DependencyManager {

    private final Map<DecoratorType, Set<String>> dependencies;
    private final Set<String> foundDecoratorsContainer;
    private final Function<String, DecoratorType> decoratorTypeDeterminer;

    DecoratorDependencyManager(
            @NonNull final Map<DecoratorType, Set<String>> dependencies,
            @NonNull final Set<String> foundDecoratorsContainer,
            @NonNull final Function<String, DecoratorType> decoratorTypeDeterminer) {
        this.dependencies = dependencies;
        this.foundDecoratorsContainer = foundDecoratorsContainer;
        this.decoratorTypeDeterminer = decoratorTypeDeterminer;
    }

    @Override
    public boolean alreadyFound(@NonNull final Class<?> dependentClass) {
        return foundDecoratorsContainer.contains(dependentClass.getName());
    }

    @Override
    public boolean isChildDecorator(@NonNull final BeanDefinitionHolder bdHolder, @NonNull final Class<?> dependentClass) {
        val dependentClassName = dependentClass.getName();

        val candidateBeanName = bdHolder.getBeanName();
        log.debug("Candidate bean name is {}", candidateBeanName);

        val dependentDecoratorType = decoratorTypeDeterminer.apply(dependentClass.getSimpleName());
        val decoratorTypes = DecoratorType.values();
        val lastDecoratorTypeIndex = decoratorTypes.length - 1;
        if (dependentDecoratorType.ordinal() == lastDecoratorTypeIndex) {
            log.debug("{} is bottom level decorator. Probably not decorator.", dependentClassName);
            return false;
        }

        for (var i = dependentDecoratorType.ordinal(); i < lastDecoratorTypeIndex; i++) {
            val dependencyDecoratorType = decoratorTypes[i + 1];
            log.debug("Looking for dependencies of decorator type {}", dependencyDecoratorType);
            if (dependencies.get(dependencyDecoratorType).contains(candidateBeanName)) {
                log.debug(
                        "{} is a decorator type {} dependency for {}",
                        candidateBeanName, dependencyDecoratorType, dependentClassName
                );
                foundDecoratorsContainer.add(dependentClassName);
                return true;
            }
        }

        return false;
    }
}

package com.github.monosoul.fortuneteller.spring.resolver;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

interface DependencyManager {

    boolean alreadyFound(final Class<?> dependentClass);

    boolean isChildDecorator(final BeanDefinitionHolder bdHolder, final Class<?> dependentClass);
}

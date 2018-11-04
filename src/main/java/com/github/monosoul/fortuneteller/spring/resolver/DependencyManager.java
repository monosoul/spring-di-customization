package com.github.monosoul.fortuneteller.spring.resolver;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

interface DependencyManager {

    boolean alreadyFound(Class<?> dependentClass);

    boolean isChildDecorator(BeanDefinitionHolder bdHolder, Class<?> dependentClass);
}

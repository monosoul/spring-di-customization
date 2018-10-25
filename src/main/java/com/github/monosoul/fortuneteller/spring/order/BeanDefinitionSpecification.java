package com.github.monosoul.fortuneteller.spring.order;

interface BeanDefinitionSpecification {

    Class<?> getClazz();

    String getPreviousBeanName();

    boolean isPrimary();
}

package com.github.monosoul.fortuneteller.spring.order;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class BeanDefinitionSpecificationImpl implements BeanDefinitionSpecification {

    Class<?> clazz;
    String previousBeanName;
    boolean primary;
}

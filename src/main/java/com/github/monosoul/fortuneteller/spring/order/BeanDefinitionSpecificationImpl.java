package com.github.monosoul.fortuneteller.spring.order;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

@Value
@Builder
class BeanDefinitionSpecificationImpl implements BeanDefinitionSpecification {

    Class<?> clazz;
    @Default
    String previousBeanName = "";
    @Default
    boolean primary = false;
}

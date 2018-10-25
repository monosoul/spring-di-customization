package com.github.monosoul.fortuneteller.spring.order;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_AUTODETECT;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;
import java.util.function.Function;
import lombok.val;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.util.Assert;

final class BeanDefinitionProvider implements Function<BeanDefinitionSpecification, AbstractBeanDefinition> {

    @SuppressWarnings("deprecation")
    @Override
    public AbstractBeanDefinition apply(final BeanDefinitionSpecification specification) {
        Assert.notNull(specification.getClazz(), "Clazz can't be null");

        val beanDefinition = genericBeanDefinition(specification.getClazz())
                .setAutowireMode(AUTOWIRE_AUTODETECT)
                .getBeanDefinition();
        beanDefinition.setPrimary(specification.isPrimary());
        beanDefinition.addQualifier(new AutowireCandidateQualifier(OrderQualifier.class, specification.getPreviousBeanName()));

        return beanDefinition;
    }
}

package com.github.monosoul.fortuneteller.spring.order;

import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public final class DecoratorAutowireCandidateResolverConfigurer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Assert.state(configurableListableBeanFactory instanceof DefaultListableBeanFactory,
                "BeanFactory needs to be a DefaultListableBeanFactory");
        val beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;

        beanFactory.setAutowireCandidateResolver(
                new DecoratorAutowireCandidateResolver(beanFactory.getAutowireCandidateResolver())
        );
    }
}

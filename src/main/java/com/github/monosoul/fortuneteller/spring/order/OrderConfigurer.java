package com.github.monosoul.fortuneteller.spring.order;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_AUTODETECT;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;
import lombok.val;
import lombok.var;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderConfigurer implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Assert.state(configurableListableBeanFactory instanceof DefaultListableBeanFactory,
                "BeanFactory needs to be a DefaultListableBeanFactory");
        val beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;

        beanFactory.getBeansOfType(OrderConfig.class)
                   .forEach((key, value) -> processOrderConfig(value, beanFactory));
    }

    private void processOrderConfig(final OrderConfig<?> orderConfig, final DefaultListableBeanFactory beanFactory) {
        val lastItemIndex = orderConfig.getClasses().length - 1;
        val topLevelBeanClass = orderConfig.getClasses()[lastItemIndex];
        val topLevelBeanName = topLevelBeanClass.getTypeName();
        val topLevelBeanDefinition = genericBeanDefinition(topLevelBeanClass)
                .setAutowireMode(AUTOWIRE_AUTODETECT)
                .getBeanDefinition();
        topLevelBeanDefinition.setPrimary(true);
        topLevelBeanDefinition.addQualifier(new AutowireCandidateQualifier(OrderQualifier.class, ""));
        beanFactory.registerBeanDefinition(topLevelBeanName, topLevelBeanDefinition);

        var nextBeanName = topLevelBeanName;
        for (var i = lastItemIndex - 1; i >= 0; i--) {
            val currentBeanClass = orderConfig.getClasses()[i];
            val currentBeanName = currentBeanClass.getTypeName();

            val currentBeanDefinition = genericBeanDefinition(currentBeanClass)
                    .setAutowireMode(AUTOWIRE_AUTODETECT)
                    .getBeanDefinition();
            currentBeanDefinition.addQualifier(new AutowireCandidateQualifier(OrderQualifier.class, nextBeanName));
            beanFactory.registerBeanDefinition(currentBeanName, currentBeanDefinition);

            nextBeanName = currentBeanName;
        }
    }
}

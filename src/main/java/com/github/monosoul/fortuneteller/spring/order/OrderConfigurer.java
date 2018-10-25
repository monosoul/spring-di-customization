package com.github.monosoul.fortuneteller.spring.order;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_AUTODETECT;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;
import lombok.val;
import lombok.var;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
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
        var previousBeanName = "";
        for (var i = 0; i < orderConfig.getClasses().length; i++) {
            val currentBeanClass = orderConfig.getClasses()[i];
            val currentBeanName = currentBeanClass.getTypeName();

            beanFactory.registerBeanDefinition(
                    currentBeanName,
                    createBeanDefinition(currentBeanClass, previousBeanName, i == 0)
            );

            previousBeanName = currentBeanName;
        }
    }

    @SuppressWarnings("deprecation")
    private AbstractBeanDefinition createBeanDefinition(final Class<?> clazz, final String nextBeanName, final boolean isPrimary) {
        val beanDefinition = genericBeanDefinition(clazz)
                .setAutowireMode(AUTOWIRE_AUTODETECT)
                .getBeanDefinition();
        beanDefinition.setPrimary(isPrimary);
        beanDefinition.addQualifier(new AutowireCandidateQualifier(OrderQualifier.class, nextBeanName));

        return beanDefinition;
    }
}

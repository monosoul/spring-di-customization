package com.github.monosoul.fortuneteller.spring.order;

import java.util.function.Consumer;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public final class OrderConfigurer implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(final BeanDefinitionRegistry registry) throws BeansException {
        Assert.state(registry instanceof ListableBeanFactory,
                "BeanRegistry needs to be a ListableBeanFactory");
        val beanFactory = (ListableBeanFactory) registry;

        val orderConfigPreProcessor = orderConfigPreProcessor(registry);
        val orderConfigProcessor = orderConfigProcessor(registry);

        beanFactory.getBeansOfType(OrderConfig.class).values().stream()
                   .peek(orderConfigPreProcessor::accept)
                   .forEach(orderConfigProcessor::accept);
    }

    Consumer<OrderConfig<?>> orderConfigPreProcessor(final BeanDefinitionRegistry beanDefinitionRegistry) {
        return new OrderConfigPreProcessor(beanDefinitionRegistry);
    }

    Consumer<OrderConfig<?>> orderConfigProcessor(final BeanDefinitionRegistry beanDefinitionRegistry) {
        return new OrderConfigProcessor(beanDefinitionRegistry, new BeanDefinitionProvider());
    }
}

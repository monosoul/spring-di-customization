package com.github.monosoul.fortuneteller.spring.order;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

@RequiredArgsConstructor
public class OrderConfigPreProcessor implements Consumer<OrderConfig<?>> {

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void accept(final OrderConfig<?> orderConfig) {
        val classesSet = stream(orderConfig.getClasses()).map(Class::getName).collect(toSet());

        stream(beanDefinitionRegistry.getBeanDefinitionNames())
                .map(n -> new SimpleImmutableEntry<>(n, beanDefinitionRegistry.getBeanDefinition(n)))
                .filter(e -> AbstractBeanDefinition.class.isAssignableFrom(e.getValue().getClass()))
                .map(e -> new SimpleImmutableEntry<>(e.getKey(), (AbstractBeanDefinition) e.getValue()))
                .filter(e -> classesSet.contains(e.getValue().getBeanClassName()))
                .forEach(e -> beanDefinitionRegistry.removeBeanDefinition(e.getKey()));
    }
}

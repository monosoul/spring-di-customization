package com.github.monosoul.fortuneteller.spring.order;

import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

@RequiredArgsConstructor
final class OrderConfigProcessor implements Consumer<OrderConfig<?>> {

    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private final Function<BeanDefinitionSpecification, AbstractBeanDefinition> beanDefinitionProvider;

    @Override
    public void accept(final OrderConfig<?> orderConfig) {
        String previousBeanName = "";
        for (int i = 0; i < orderConfig.getClasses().size(); i++) {
            val currentBeanClass = orderConfig.getClasses().get(i);
            val currentBeanName = currentBeanClass.getTypeName();

            beanDefinitionRegistry.registerBeanDefinition(
                    currentBeanName,
                    beanDefinitionProvider.apply(
                            BeanDefinitionSpecificationImpl
                                    .builder()
                                    .clazz(currentBeanClass)
                                    .previousBeanName(previousBeanName)
                                    .primary(i == 0)
                                    .build()
                    )
            );

            previousBeanName = currentBeanName;
        }
    }
}

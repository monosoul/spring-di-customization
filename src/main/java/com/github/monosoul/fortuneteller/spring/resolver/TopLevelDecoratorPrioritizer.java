package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.spring.resolver.CommonUtils.getSimpleName;
import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@Slf4j
class TopLevelDecoratorPrioritizer implements Consumer<ConfigurableListableBeanFactory> {

    private final Function<String, DecoratorType> decoratorTypeDeterminer;

    TopLevelDecoratorPrioritizer(@NonNull final Function<String, DecoratorType> decoratorTypeDeterminer) {
        this.decoratorTypeDeterminer = decoratorTypeDeterminer;
    }

    @Override
    public void accept(final ConfigurableListableBeanFactory beanFactory) {
        log.debug("Prioritizing decorator beans");
        for (val beanName : beanFactory.getBeanDefinitionNames()) {
            log.debug("Checking {}", beanName);
            val beanDefinition = beanFactory.getBeanDefinition(beanName);
            val beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName == null) {
                log.debug("{} doesn't have bean class name. Skipping it.", beanName);
                continue;
            }

            val decoratorType = decoratorTypeDeterminer.apply(getSimpleName(beanClassName));
            if (decoratorType.ordinal() == 0) {
                log.debug("{} is top level decorator. Making it primary.", beanName);
                beanDefinition.setPrimary(true);
            }
        }
    }
}

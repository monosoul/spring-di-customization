package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.NOT_DECORATOR;
import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
final class DecoratorTypeDeterminer implements Function<String, DecoratorType> {

    @Override
    public DecoratorType apply(final String classSimpleName) {
        log.debug("Determining type for {}", classSimpleName);
        for (val decoratorType : DecoratorType.values()) {
            if (classSimpleName.startsWith(decoratorType.getPrefix())) {
                log.debug("{} is {}", classSimpleName, decoratorType);
                return decoratorType;
            }
        }
        log.debug("Wasn't able to determine {} type. Falling back to {}.", classSimpleName, NOT_DECORATOR);

        return NOT_DECORATOR;
    }
}

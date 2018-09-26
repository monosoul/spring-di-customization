package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.util.Util.randomEnum;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.spring.DecoratorType;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DecoratorTypeDeterminerTest {

    private static final int LIMIT = 10;

    @ParameterizedTest
    @MethodSource("decoratorTypeAndSimpleClassNameStream")
    void apply(final DecoratorType decoratorType, final String simpleClassname) {
        val actual = new DecoratorTypeDeterminer().apply(simpleClassname);

        assertThat(actual).isEqualByComparingTo(decoratorType);
    }

    private static Stream<Arguments> decoratorTypeAndSimpleClassNameStream() {
        return generate(() -> (Arguments) () -> {
                    val decoratorType = randomEnum(DecoratorType.class);

                    return new Object[]{
                            decoratorType,
                            decoratorType.getPrefix() + randomAlphabetic(LIMIT)
                    };
                }
        ).limit(LIMIT);
    }
}
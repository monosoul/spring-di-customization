package com.github.monosoul.fortuneteller.spring.order;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextBoolean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class BeanDefinitionProviderTest {

    private static final int LIMIT = 10;

    @Test
    void throwExceptionIfClazzIsNull() {
        val specification = mock(BeanDefinitionSpecification.class);

        when(specification.getClazz()).thenReturn(null);

        assertThatThrownBy(() -> new BeanDefinitionProvider().apply(specification))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Clazz can't be null");
    }

    @ParameterizedTest
    @MethodSource("specificationStream")
    void apply(final BeanDefinitionSpecification specification) {
        val actual = new BeanDefinitionProvider().apply(specification);

        assertThat(actual).isNotNull();
        assertThat(actual.getBeanClass()).isEqualTo(specification.getClazz());
        assertThat(actual.isPrimary()).isEqualTo(specification.isPrimary());

        val candidateQualifier = actual.getQualifier(OrderQualifier.class.getName());
        assertThat(candidateQualifier).isNotNull();
        assertThat(candidateQualifier.getAttribute("value")).isEqualTo(specification.getPreviousBeanName());

    }

    private static Stream<BeanDefinitionSpecification> specificationStream() {
        return generate(() -> {
            val specification = mock(BeanDefinitionSpecification.class);

            doReturn(Object.class).when(specification).getClazz();
            doReturn(nextBoolean()).when(specification).isPrimary();
            doReturn(randomAlphabetic(LIMIT)).when(specification).getPreviousBeanName();

            return specification;
        }).limit(LIMIT);
    }
}
package com.github.monosoul.fortuneteller.spring.resolver;

import static java.lang.String.join;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.CollectionUtils.lastElement;
import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.config.DependencyDescriptor;

class CommonUtilsTest {

    private static final int LIMIT = 10;

    @Test
    void getDependentClass() {
        val descriptor = mock(DependencyDescriptor.class);
        val member = mock(Member.class);
        val clazz = Object.class;

        when(descriptor.getMember()).thenReturn(member);
        doReturn(clazz).when(member).getDeclaringClass();

        val actual = CommonUtils.getDependentClass(descriptor);

        assertThat(actual).isSameAs(clazz);
    }

    @ParameterizedTest
    @MethodSource("classNameStream")
    void getSimpleName(final String fqName, final String simpleName) {
        val actual = CommonUtils.getSimpleName(fqName);

        assertThat(actual).isEqualTo(simpleName);
    }

    private static List<String> stringList() {
        return generate(() -> randomAlphabetic(LIMIT)).limit(nextInt(1, LIMIT)).collect(toList());
    }

    private static Stream<Arguments> classNameStream() {
        return generate(() -> (Arguments) () -> {
            val stringList = stringList();
            val simpleName = lastElement(stringList);
            val fqName = join(".", stringList);

            return new Object[]{fqName, simpleName};
        }).limit(LIMIT);
    }
}
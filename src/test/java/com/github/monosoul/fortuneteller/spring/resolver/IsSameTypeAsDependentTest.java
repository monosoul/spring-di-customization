package com.github.monosoul.fortuneteller.spring.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.lang.reflect.Member;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.config.DependencyDescriptor;

class IsSameTypeAsDependentTest {

    @Mock
    private DependencyDescriptor descriptor;
    @Mock
    private Member member;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(descriptor.getMember()).thenReturn(member);
    }

    @Test
    void isSameTypeAsDependent() {
        val clazz = Object.class;

        doReturn(clazz).when(member).getDeclaringClass();
        doReturn(clazz).when(descriptor).getDependencyType();

        val actual = new IsSameTypeAsDependent().test(descriptor);

        assertThat(actual).isTrue();
    }

    @Test
    void isNotSameTypeAsDependent() {
        val clazz = Object.class;
        val anotherClazz = String.class;

        doReturn(clazz).when(member).getDeclaringClass();
        doReturn(anotherClazz).when(descriptor).getDependencyType();

        val actual = new IsSameTypeAsDependent().test(descriptor);

        assertThat(actual).isFalse();
    }
}
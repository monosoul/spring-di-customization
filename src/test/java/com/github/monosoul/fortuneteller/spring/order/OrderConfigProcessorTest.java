package com.github.monosoul.fortuneteller.spring.order;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.function.Function;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

class OrderConfigProcessorTest {

    @Mock
    private BeanDefinitionRegistry beanDefinitionRegistry;
    @Mock
    private Function<BeanDefinitionSpecification, AbstractBeanDefinition> beanDefinitionProvider;
    @Mock
    private AbstractBeanDefinition beanDefinition;
    @Mock
    private OrderConfig<SomeInterface> orderConfig;
    @Captor
    private ArgumentCaptor<BeanDefinitionSpecification> captor;
    @InjectMocks
    private OrderConfigProcessor orderConfigProcessor;

    @BeforeEach
    void setUp() {
        this.orderConfigProcessor = null;
        initMocks(this);

        when(beanDefinitionProvider.apply(any(BeanDefinitionSpecification.class))).thenReturn(beanDefinition);
    }

    @Test
    void makeFirstBeanDefinitionPrimary() {
        when(orderConfig.getClasses()).thenReturn(singletonList(SomeClass.class));

        orderConfigProcessor.accept(orderConfig);

        verify(beanDefinitionProvider).apply(captor.capture());
        verify(beanDefinitionRegistry).registerBeanDefinition(SomeClass.class.getTypeName(), beanDefinition);
        verifyNoMoreInteractions(beanDefinitionProvider, beanDefinitionRegistry);

        val specification = captor.getValue();
        assertThat(specification).isNotNull();
        assertThat(specification.getClazz()).isEqualTo(SomeClass.class);
        assertThat(specification.getPreviousBeanName()).isEmpty();
        assertThat(specification.isPrimary()).isTrue();
    }

    @Test
    void makeOnlyFirstBeanDefinitionPrimary() {
        when(orderConfig.getClasses()).thenReturn(asList(SomeClass.class, AnotherClass.class));

        orderConfigProcessor.accept(orderConfig);

        verify(beanDefinitionProvider, times(2)).apply(captor.capture());
        verify(beanDefinitionRegistry).registerBeanDefinition(SomeClass.class.getTypeName(), beanDefinition);
        verify(beanDefinitionRegistry).registerBeanDefinition(AnotherClass.class.getTypeName(), beanDefinition);
        verifyNoMoreInteractions(beanDefinitionProvider, beanDefinitionRegistry);

        val someClassSpecification = captor.getAllValues().get(0);
        assertThat(someClassSpecification).isNotNull();
        assertThat(someClassSpecification.getClazz()).isEqualTo(SomeClass.class);
        assertThat(someClassSpecification.getPreviousBeanName()).isEmpty();
        assertThat(someClassSpecification.isPrimary()).isTrue();

        val anotherClassSpecification = captor.getAllValues().get(1);
        assertThat(anotherClassSpecification).isNotNull();
        assertThat(anotherClassSpecification.getClazz()).isEqualTo(AnotherClass.class);
        assertThat(anotherClassSpecification.getPreviousBeanName()).isEqualTo(SomeClass.class.getTypeName());
        assertThat(anotherClassSpecification.isPrimary()).isFalse();
    }

    private interface SomeInterface {

    }

    private static class SomeClass implements SomeInterface {

    }

    private static class AnotherClass implements SomeInterface {

    }
}
package com.github.monosoul.fortuneteller.spring.order;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

class OrderConfigurerTest {

    private static final int LIMIT = 10;

    @Mock(extraInterfaces = ListableBeanFactory.class)
    private BeanDefinitionRegistry beanRegistry;
    @Mock
    private Consumer<OrderConfig<?>> orderConfigPreProcessor;
    @Mock
    private Consumer<OrderConfig<?>> orderConfigProcessor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void postProcessBeanFactory() {
        val beanFactory = mock(ConfigurableListableBeanFactory.class);

        new OrderConfigurer().postProcessBeanFactory(beanFactory);

        verifyZeroInteractions(beanFactory);
    }

    @ParameterizedTest
    @MethodSource("orderConfigMapStream")
    void postProcessBeanDefinitionRegistry(final Map<String, OrderConfig> orderConfigMap) {
        when(((ListableBeanFactory) beanRegistry).getBeansOfType(OrderConfig.class)).thenReturn(orderConfigMap);

        val orderConfigurer = spy(new OrderConfigurer());
        doReturn(orderConfigPreProcessor).when(orderConfigurer).orderConfigPreProcessor(any(BeanDefinitionRegistry.class));
        doReturn(orderConfigProcessor).when(orderConfigurer).orderConfigProcessor(any(BeanDefinitionRegistry.class));

        orderConfigurer.postProcessBeanDefinitionRegistry(beanRegistry);

        verify(orderConfigPreProcessor, times(orderConfigMap.size())).accept(any(OrderConfig.class));
        verify(orderConfigProcessor, times(orderConfigMap.size())).accept(any(OrderConfig.class));
        verifyNoMoreInteractions(orderConfigPreProcessor, orderConfigProcessor);
    }

    private static Stream<Map<String, OrderConfig>> orderConfigMapStream() {
        return generate(() ->
                orderConfigStream()
                        .limit(nextInt(1, LIMIT))
                        .collect(toMap(k -> randomAlphabetic(LIMIT), v -> v))
        ).limit(LIMIT);
    }

    private static Stream<OrderConfig> orderConfigStream() {
        return generate(() -> mock(OrderConfig.class)).limit(LIMIT);
    }

    @Test
    void orderConfigPreProcessor() {
        val actual = new OrderConfigurer().orderConfigPreProcessor(beanRegistry);
        val expected = new OrderConfigPreProcessor(beanRegistry);

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void orderConfigProcessor() {
        val actual = new OrderConfigurer().orderConfigProcessor(beanRegistry);
        val expected = new OrderConfigProcessor(beanRegistry, new BeanDefinitionProvider());

        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
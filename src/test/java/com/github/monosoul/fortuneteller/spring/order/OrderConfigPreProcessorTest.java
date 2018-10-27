package com.github.monosoul.fortuneteller.spring.order;

import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

class OrderConfigPreProcessorTest {

    private static final int LIMIT = 10;

    @Mock
    private BeanDefinitionRegistry beanDefinitionRegistry;
    @Mock
    private OrderConfig<SomeInterface> orderConfig;
    @Mock
    private Set<String> classNameSet;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        initMocks(this);

        stubOrderConfig();
    }

    @ParameterizedTest
    @MethodSource("stringArrayAsArgumentsStream")
    void doNothingIfNotAbstractBeanDefinition(final String[] beanDefinitionNames) {
        when(beanDefinitionRegistry.getBeanDefinitionNames()).thenReturn(beanDefinitionNames);
        when(beanDefinitionRegistry.getBeanDefinition(anyString())).thenAnswer((Answer<BeanDefinition>) i -> mock(BeanDefinition.class));

        new OrderConfigPreProcessor(beanDefinitionRegistry).accept(orderConfig);

        verify(beanDefinitionRegistry).getBeanDefinitionNames();
        verify(beanDefinitionRegistry, times(beanDefinitionNames.length)).getBeanDefinition(anyString());
        verify(beanDefinitionRegistry, never()).removeBeanDefinition(anyString());
        verifyZeroInteractions(classNameSet);
    }

    @ParameterizedTest
    @MethodSource("stringArrayAsArgumentsStream")
    void doNothingIfClassesDoesNotContainClassOfSuchBean(final String[] beanDefinitionNames) {
        val beanDefinition = mock(AbstractBeanDefinition.class);
        when(beanDefinition.getBeanClassName()).thenAnswer((Answer<String>) invocation -> randomAlphabetic(LIMIT));

        when(beanDefinitionRegistry.getBeanDefinitionNames()).thenReturn(beanDefinitionNames);
        when(beanDefinitionRegistry.getBeanDefinition(anyString())).thenReturn(beanDefinition);
        when(classNameSet.contains(anyString())).thenReturn(false);

        new OrderConfigPreProcessor(beanDefinitionRegistry).accept(orderConfig);

        verify(beanDefinitionRegistry).getBeanDefinitionNames();
        val beanDefinitionsAmount = beanDefinitionNames.length;
        verify(beanDefinitionRegistry, times(beanDefinitionsAmount)).getBeanDefinition(anyString());
        verify(beanDefinition, times(beanDefinitionsAmount)).getBeanClassName();
        verify(classNameSet, times(beanDefinitionsAmount)).contains(anyString());
        verify(beanDefinitionRegistry, never()).removeBeanDefinition(anyString());
    }

    @ParameterizedTest
    @MethodSource("stringArrayAsArgumentsStream")
    void removeDefinitionsIfClassesContainsSuchBeans(final String[] beanDefinitionNames) {
        val beanDefinition = mock(AbstractBeanDefinition.class);
        when(beanDefinition.getBeanClassName()).thenAnswer((Answer<String>) invocation -> randomAlphabetic(LIMIT));

        when(beanDefinitionRegistry.getBeanDefinitionNames()).thenReturn(beanDefinitionNames);
        when(beanDefinitionRegistry.getBeanDefinition(anyString())).thenReturn(beanDefinition);
        when(classNameSet.contains(anyString())).thenReturn(true);

        new OrderConfigPreProcessor(beanDefinitionRegistry).accept(orderConfig);

        verify(beanDefinitionRegistry).getBeanDefinitionNames();
        val beanDefinitionsAmount = beanDefinitionNames.length;
        verify(beanDefinitionRegistry, times(beanDefinitionsAmount)).getBeanDefinition(anyString());
        verify(beanDefinition, times(beanDefinitionsAmount)).getBeanClassName();
        verify(classNameSet, times(beanDefinitionsAmount)).contains(anyString());
        verify(beanDefinitionRegistry, times(beanDefinitionsAmount)).removeBeanDefinition(anyString());
    }

    @SuppressWarnings("unchecked")
    private void stubOrderConfig() {
        val classes = mock(List.class);
        val classStream = (Stream<Class<? extends SomeInterface>>) mock(Stream.class);
        val classNameStream = (Stream<String>) mock(Stream.class);

        when(orderConfig.getClasses()).thenReturn(classes);
        when(classes.stream()).thenReturn(classStream);
        when(classStream.map(any(Function.class))).thenReturn(classNameStream);
        when(classNameStream.collect(any(Collector.class))).thenReturn(classNameSet);
    }

    private static Stream<Arguments> stringArrayAsArgumentsStream() {
        return stringArrayStream().map(sa -> (Arguments) () -> new Object[]{sa}).limit(LIMIT);
    }

    private static Stream<String[]> stringArrayStream() {
        return generate(() -> stringStream().limit(nextInt(1, LIMIT)).toArray(String[]::new)).limit(LIMIT);
    }

    private static Stream<String> stringStream() {
        return generate(() -> randomAlphabetic(LIMIT)).limit(LIMIT);
    }

    private interface SomeInterface {

    }
}
package com.github.monosoul.fortuneteller.spring.order;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

class DecoratorAutowireCandidateResolverConfigurerTest {

    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    void setUp() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Test
    void postProcessBeanFactory() {
        new DecoratorAutowireCandidateResolverConfigurer().postProcessBeanFactory(beanFactory);

        assertThat(beanFactory.getAutowireCandidateResolver()).isInstanceOf(DecoratorAutowireCandidateResolver.class);
    }
}
package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.domain.impl.CacheFactoryBean.FortuneCache;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheFactoryBeanTest {

    @Test
    void getObject() {
        assertThat(new CacheFactoryBean().getObject()).isEqualTo(new FortuneCache());
    }

    @Test
    void getObjectType() {
        assertThat(new CacheFactoryBean().getObjectType()).isEqualTo(FortuneCache.class);
    }
}
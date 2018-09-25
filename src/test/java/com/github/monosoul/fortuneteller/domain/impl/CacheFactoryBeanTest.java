package com.github.monosoul.fortuneteller.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

class CacheFactoryBeanTest {

    @Test
    void getObject() {
        assertThat(new CacheFactoryBean().getObject()).isEqualTo(new ConcurrentHashMap<>());
    }

    @Test
    void getObjectType() {
        assertThat(new CacheFactoryBean().getObjectType()).isEqualTo(ConcurrentHashMap.class);
    }
}
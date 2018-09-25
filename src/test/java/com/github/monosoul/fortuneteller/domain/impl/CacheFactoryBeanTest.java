package com.github.monosoul.fortuneteller.domain.impl;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CacheFactoryBeanTest {

    @Test
    void getObject() {
        assertThat(new CacheFactoryBean().getObject()).isEqualTo(new HashMap<>());
    }

    @Test
    void getObjectType() {
        assertThat(new CacheFactoryBean().getObjectType()).isEqualTo(Map.class);
    }
}
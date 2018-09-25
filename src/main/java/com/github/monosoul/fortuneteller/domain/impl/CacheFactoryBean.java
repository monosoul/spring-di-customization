package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CacheFactoryBean implements FactoryBean<Map<FortuneRequest, FortuneResponse>> {

    @Override
    public Map<FortuneRequest, FortuneResponse> getObject() {
        return new ConcurrentHashMap<>();
    }

    @Override
    public Class<?> getObjectType() {
        return ConcurrentHashMap.class;
    }
}

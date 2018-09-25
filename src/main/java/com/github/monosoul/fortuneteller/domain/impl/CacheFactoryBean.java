package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheFactoryBean implements FactoryBean<CacheFactoryBean.FortuneCache> {

    @Override
    public FortuneCache getObject() {
        return new FortuneCache();
    }

    @Override
    public Class<?> getObjectType() {
        return FortuneCache.class;
    }

    static class FortuneCache extends ConcurrentHashMap<FortuneRequest, FortuneResponse> {

    }
}

package com.github.monosoul.fortuneteller.spring.order;

import java.util.List;

@FunctionalInterface
public interface OrderConfig<T> {

    List<Class<? extends T>> getClasses();
}

package com.github.monosoul.fortuneteller.spring.order;

public interface OrderConfig<T> {

    Class<? extends T>[] getClasses();
}

package com.github.monosoul.fortuneteller.spring.order;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class OrderConfig<T> {

    private final Class<? extends T>[] classes;

    public OrderConfig(@NonNull final Class<? extends T>... classes) {
        this.classes = classes;
    }
}

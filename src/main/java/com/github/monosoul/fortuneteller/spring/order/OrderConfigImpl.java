package com.github.monosoul.fortuneteller.spring.order;

import java.util.List;
import lombok.Value;

@Value
public class OrderConfigImpl<T> implements OrderConfig<T> {

    List<Class<? extends T>> classes;
}

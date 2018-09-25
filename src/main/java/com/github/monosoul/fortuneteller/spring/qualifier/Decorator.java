package com.github.monosoul.fortuneteller.spring.qualifier;

import com.github.monosoul.fortuneteller.spring.DecoratorType;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.NOT_DECORATOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//@Qualifier
@Retention(RUNTIME)
public @interface Decorator {

    DecoratorType value() default NOT_DECORATOR;
}

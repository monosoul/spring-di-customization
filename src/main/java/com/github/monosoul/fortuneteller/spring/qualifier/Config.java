package com.github.monosoul.fortuneteller.spring.qualifier;

import lombok.val;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Set.of;

@Configuration
public class Config {

    @Bean
    public CustomAutowireConfigurer customAutowireConfigurer() {
        val configurer = new CustomAutowireConfigurer();
        configurer.setCustomQualifierTypes(of(Decorator.class));

        return configurer;
    }
}

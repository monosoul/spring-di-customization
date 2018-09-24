package com.github.monosoul.fortuneteller.da;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class DaConfig {

    @Bean
    public List<String> fortunes() {
        return asList(
                "You'll get married!",
                "Wow! You'll be very rich!",
                "You'll have 10 children!",
                "You'll meet your love soon!",
                "You'll get a promotion at your work in a year!",
                "You'll become very popular soon!"
        );
    }
}

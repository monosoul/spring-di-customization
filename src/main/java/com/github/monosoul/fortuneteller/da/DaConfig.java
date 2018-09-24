package com.github.monosoul.fortuneteller.da;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.github.monosoul.fortuneteller.common.Fortunes.FORTUNES;
import static java.util.Arrays.asList;

@Configuration
public class DaConfig {

    @Bean
    public List<String> fortunes() {
        return asList(FORTUNES);
    }
}

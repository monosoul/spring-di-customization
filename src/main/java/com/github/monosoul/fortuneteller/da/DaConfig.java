package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static com.github.monosoul.fortuneteller.common.Fortunes.FORTUNES;
import static com.github.monosoul.fortuneteller.common.Horoscopes.HOROSCOPES;
import static java.util.Arrays.asList;

@Configuration
public class DaConfig {

    @Bean
    public List<String> fortunes() {
        return asList(FORTUNES);
    }

    @Bean
    public Map<ZodiacSign, String> horoscopes() {
        return HOROSCOPES;
    }
}

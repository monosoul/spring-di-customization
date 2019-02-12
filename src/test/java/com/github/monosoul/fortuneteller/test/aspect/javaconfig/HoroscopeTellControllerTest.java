package com.github.monosoul.fortuneteller.test.aspect.javaconfig;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.github.monosoul.fortuneteller.aspect.AccessDeniedException;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.web.HoroscopeTellController;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles({"unit", "javaconfig"})
public class HoroscopeTellControllerTest {

    private static final int LIMIT = 10;

    @Autowired
    private HoroscopeTellController controller;
    @Autowired
    private Predicate<String> ipIsAllowed;

    @Test
    void doNothingWhenAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(true);

        controller.tell(randomAlphabetic(LIMIT));
    }

    @Test
    void throwExceptionWhenNotAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(false);

        assertThatThrownBy(() -> controller.tell(randomAlphabetic(LIMIT)))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Configuration
    @Import(AspectConfiguration.class)
    @Profile({"unit", "javaconfig"})
    @EnableAspectJAutoProxy
    public static class Config {

        @Bean
        public HoroscopeTellController horoscopeTellController(
                final HoroscopeTeller horoscopeTeller,
                final Function<String, ZodiacSign> zodiacSignConverter
        ) {
            return new HoroscopeTellController(horoscopeTeller, zodiacSignConverter);
        }

        @Bean
        public HoroscopeTeller horoscopeTeller() {
            return mock(HoroscopeTeller.class);
        }

        @SuppressWarnings("unchecked")
        @Bean
        public Function<String, ZodiacSign> zodiacSignConverter() {
            return mock(Function.class);
        }
    }
}

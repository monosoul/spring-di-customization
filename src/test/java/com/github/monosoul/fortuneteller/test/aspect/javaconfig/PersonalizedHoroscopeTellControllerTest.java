package com.github.monosoul.fortuneteller.test.aspect.javaconfig;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.web.PersonalizedHoroscopeTellController;
import java.util.function.Function;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("unit-javaconfig")
public class PersonalizedHoroscopeTellControllerTest {

    private static final int LIMIT = 10;

    @Autowired
    private PersonalizedHoroscopeTellController controller;

    @Test
    void tellTheTruth() {
        val actual = controller.tell(randomAlphabetic(LIMIT), randomAlphabetic(LIMIT));

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Anonymous");
        assertThat(actual.getHoroscope().getMessage()).isEqualTo(THE_TRUTH);
    }

    @Configuration
    @Profile("unit-javaconfig")
    @EnableAspectJAutoProxy
    public static class Config {

        @Bean
        public PersonalizedHoroscopeTellController personalizedHoroscopeTellController(
                final HoroscopeTeller horoscopeTeller,
                final Function<String, ZodiacSign> zodiacSignConverter,
                final Function<String, String> nameNormalizer
        ) {
            return new PersonalizedHoroscopeTellController(horoscopeTeller, zodiacSignConverter, nameNormalizer);
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

        @SuppressWarnings("unchecked")
        @Bean
        public Function<String, String> nameNormalizer() {
            return mock(Function.class);
        }

        @Bean
        public TellTheTruthAspect tellTheTruthAspect() {
            return new TellTheTruthAspect();
        }
    }
}

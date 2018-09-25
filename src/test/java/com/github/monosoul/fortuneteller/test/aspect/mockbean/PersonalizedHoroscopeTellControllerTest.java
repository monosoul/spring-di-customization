package com.github.monosoul.fortuneteller.test.aspect.mockbean;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.web.PersonalizedHoroscopeTellController;
import java.util.function.Function;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("unit-mockbean")
public class PersonalizedHoroscopeTellControllerTest {

    private static final int LIMIT = 10;

    @MockBean
    private HoroscopeTeller horoscopeTeller;
    @MockBean
    private Function<String, ZodiacSign> zodiacSignConverter;
    @MockBean
    private Function<String, String> nameNormalizer;
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
    @Profile("unit-mockbean")
    @Import({PersonalizedHoroscopeTellController.class, TellTheTruthAspect.class})
    @EnableAspectJAutoProxy
    public static class Config {
    }
}

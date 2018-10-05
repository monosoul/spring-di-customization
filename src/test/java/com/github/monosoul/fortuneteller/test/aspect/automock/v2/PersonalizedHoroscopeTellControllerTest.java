package com.github.monosoul.fortuneteller.test.aspect.automock.v2;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.automock.Automocked;
import com.github.monosoul.fortuneteller.web.PersonalizedHoroscopeTellController;
import lombok.val;
import org.junit.jupiter.api.Test;

public class PersonalizedHoroscopeTellControllerTest extends TestBase {

    private static final int LIMIT = 10;

    @Automocked
    private PersonalizedHoroscopeTellController controller;

    //This will NOT fail
    @Test
    void tellTheTruth() {
        val actual = controller.tell(randomAlphabetic(LIMIT), randomAlphabetic(LIMIT));

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("Anonymous");
        assertThat(actual.getHoroscope().getMessage()).isEqualTo(THE_TRUTH);
    }
}

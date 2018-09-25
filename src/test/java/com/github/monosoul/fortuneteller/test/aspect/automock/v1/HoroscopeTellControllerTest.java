package com.github.monosoul.fortuneteller.test.aspect.automock.v1;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.automock.Automocked;
import com.github.monosoul.fortuneteller.web.HoroscopeTellController;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class HoroscopeTellControllerTest extends TestBase {

    private static final int LIMIT = 10;

    @Automocked
    private HoroscopeTellController controller;

    @Test
    void tellTheTruth() {
        val actual = controller.tell(randomAlphabetic(LIMIT));

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(THE_TRUTH);
    }
}

package com.github.monosoul.fortuneteller.test.integration;

import static com.github.monosoul.fortuneteller.common.Horoscopes.HOROSCOPES;
import static com.github.monosoul.fortuneteller.util.Util.randomEnum;
import static java.util.stream.Stream.generate;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("integration")
@ContextConfiguration(classes = TestITConfiguration.class)
class HoroscopeTellerTestIT {

    private static final int LIMIT = 10;

    @Autowired
    private HoroscopeTeller horoscopeTeller;

    @SuppressWarnings("Java8MapApi")
    @ParameterizedTest
    @MethodSource("zodiacSignStream")
    void tell(final ZodiacSign sign) {
        val actual = horoscopeTeller.tell(sign);

        assertThat(actual).isNotNull();
        if (HOROSCOPES.containsKey(sign)) {
            assertThat(actual.getMessage()).isEqualTo(HOROSCOPES.get(sign));
        } else {
            assertThat(actual.getMessage()).isEqualTo("Wow! Such luck! Much money!");
        }
    }

    private static Stream<ZodiacSign> zodiacSignStream() {
        return generate(() -> randomEnum(ZodiacSign.class)).limit(LIMIT);
    }
}

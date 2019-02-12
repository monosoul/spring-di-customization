package com.github.monosoul.fortuneteller.aspect;

import static java.lang.String.format;
import static java.util.stream.Stream.generate;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class IpIsAllowedTest {

    private static final int LIMIT = 100;

    @ParameterizedTest
    @MethodSource("randomIpStream")
    void noIpsAreAllowed(final String ip) {
        val actual = new IpIsAllowed().test(ip);

        assertThat(actual).isFalse();
    }

    private static Stream<String> randomIpStream() {
        return generate(() -> format(
                "%d.%d.%d.%d",
                nextInt(0, 256), nextInt(0, 256), nextInt(0, 256), nextInt(0, 256)
        )).limit(LIMIT);
    }
}
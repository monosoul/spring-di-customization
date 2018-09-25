package com.github.monosoul.fortuneteller.test.functional;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import com.github.monosoul.fortuneteller.test.functional.model.PersonalizedHoroscope;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PersonalizedHoroscopeTellControllerTestFT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate client;

    @Test
    void test() {
        val actual = client.getForEntity(
                "http://localhost:" + port + "/horoscope/tell/personal/pETROV/aquarius", PersonalizedHoroscope.class
        );

        assertThat(actual.getStatusCode()).isEqualByComparingTo(OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo("Petrov");
        assertThat(actual.getBody().getHoroscope().getMessage()).isNotBlank();

        log.info("Received response: {}", actual.getBody());
    }

    @Test
    @Disabled
    void testWithAspect() {
        val actual = client.getForEntity(
                "http://localhost:" + port + "/horoscope/tell/personal/Petrov/aquarius", PersonalizedHoroscope.class
        );

        assertThat(actual.getStatusCode()).isEqualByComparingTo(OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo("Anonymous");
        assertThat(actual.getBody().getHoroscope().getMessage()).isEqualTo(THE_TRUTH);

        log.info("Received response: {}", actual.getBody());
    }
}

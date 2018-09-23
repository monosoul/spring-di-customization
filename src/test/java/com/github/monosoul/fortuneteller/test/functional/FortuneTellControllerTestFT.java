package com.github.monosoul.fortuneteller.test.functional;

import com.github.monosoul.fortuneteller.test.functional.model.FortuneRequest;
import com.github.monosoul.fortuneteller.test.functional.model.FortuneResponse;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FortuneTellControllerTestFT {

    private static final Logger LOGGER = getLogger(FortuneTellControllerTestFT.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate client;

    @Test
    void test() {
        val actual = client.postForEntity(
                "http://localhost:" + port + "/fortune/tell",
                FortuneRequest.builder()
                        .name("Name")
                        .zodiacSign("Sign")
                        .age(100)
                        .email("someone@somewhere.fake")
                        .build(),
                FortuneResponse.class
        );

        assertThat(actual.getStatusCode()).isEqualByComparingTo(OK);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getMessage()).isNotBlank();

        LOGGER.info("Received response: {}", actual.getBody());
    }
}

package com.github.monosoul.fortuneteller.test.functional.access;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import com.github.monosoul.fortuneteller.test.functional.model.FortuneRequest;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("restrictAccess")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class FortuneTellControllerTestFT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate client;

    @SuppressWarnings("unchecked")
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
                Map.class
        );

        assertThat(actual.getStatusCode()).isEqualByComparingTo(FORBIDDEN);
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().get("message")).isEqualTo("Access for IP [127.0.0.1] is denied");

        log.info("Received response: {}", actual.getBody());
    }
}

package com.github.monosoul.fortuneteller.web;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/fortune",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE
)
public class FortuneTellController {

    private static final Logger LOGGER = getLogger(FortuneTellController.class);

    private final FortuneTeller fortuneTeller;

    public FortuneTellController(FortuneTeller fortuneTeller) {
        this.fortuneTeller = fortuneTeller;
    }

    @PostMapping(value = "/tell")
    public FortuneResponse tell(@RequestBody final FortuneRequest request) {
        LOGGER.info("Received request: {}", request);
        return fortuneTeller.tell(request);
    }
}

package com.github.monosoul.fortuneteller.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(
        value = "/fortune",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE
)
public class FortuneTellController {

    private final FortuneTeller fortuneTeller;

    public FortuneTellController(FortuneTeller fortuneTeller) {
        this.fortuneTeller = fortuneTeller;
    }

    @PostMapping(value = "/tell")
    public FortuneResponse tell(@RequestBody final FortuneRequest request) {
        log.info("Received request: {}", request);
        return fortuneTeller.tell(request);
    }
}

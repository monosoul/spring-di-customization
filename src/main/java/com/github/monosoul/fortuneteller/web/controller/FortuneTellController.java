package com.github.monosoul.fortuneteller.web.controller;

import com.github.monosoul.fortuneteller.web.model.FortuneRequestJSON;
import com.github.monosoul.fortuneteller.web.model.FortuneResponseJSON;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Controller
@RequestMapping(
        value = "/fortune",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE
)
@ResponseBody
public class FortuneTellController {

    private static final Logger LOGGER = getLogger(FortuneTellController.class);

    @PostMapping(value = "/tell")
    public FortuneResponseJSON tell(final FortuneRequestJSON request) {
        LOGGER.info("Received request: {}", request);
        return new FortuneResponseJSON(UUID.randomUUID().toString());
    }
}

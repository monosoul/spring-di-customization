package com.github.monosoul.fortuneteller.web;

import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.Horoscope;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(
        value = "/horoscope",
        produces = APPLICATION_JSON_UTF8_VALUE
)
@Slf4j
public class HoroscopeTellController {

    private final HoroscopeTeller horoscopeTeller;
    private final Function<String, ZodiacSign> zodiacSignConverter;

    public HoroscopeTellController(
            @NonNull final HoroscopeTeller horoscopeTeller,
            @NonNull final Function<String, ZodiacSign> zodiacSignConverter
    ) {
        this.horoscopeTeller = horoscopeTeller;
        this.zodiacSignConverter = zodiacSignConverter;
    }

    @GetMapping(value = "/tell/{sign}")
    public Horoscope tell(@PathVariable final String sign) {
        log.info("Received request: {}", sign);
        return horoscopeTeller.tell(
                zodiacSignConverter.apply(sign)
        );
    }
}

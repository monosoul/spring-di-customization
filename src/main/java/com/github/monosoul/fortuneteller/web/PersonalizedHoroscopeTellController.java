package com.github.monosoul.fortuneteller.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.model.PersonalizedHoroscope;
import java.util.function.Function;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(
        value = "/horoscope",
        produces = APPLICATION_JSON_UTF8_VALUE
)
public class PersonalizedHoroscopeTellController {

    private final HoroscopeTeller horoscopeTeller;
    private final Function<String, ZodiacSign> zodiacSignConverter;
    private final Function<String, String> nameNormalizer;

    public PersonalizedHoroscopeTellController(
            @NonNull final HoroscopeTeller horoscopeTeller,
            @NonNull final Function<String, ZodiacSign> zodiacSignConverter,
            @NonNull final Function<String, String> nameNormalizer
    ) {
        this.horoscopeTeller = horoscopeTeller;
        this.zodiacSignConverter = zodiacSignConverter;
        this.nameNormalizer = nameNormalizer;
    }

    @GetMapping(value = "/tell/personal/{name}/{sign}")
    public PersonalizedHoroscope tell(@PathVariable final String name, @PathVariable final String sign) {
        log.info("Received name: {}; sign: {}", name, sign);

        return PersonalizedHoroscope.builder()
                                    .name(
                                            nameNormalizer.apply(name)
                                    )
                                    .horoscope(
                                            horoscopeTeller.tell(
                                                    zodiacSignConverter.apply(sign)
                                            )
                                    )
                                    .build();
    }
}

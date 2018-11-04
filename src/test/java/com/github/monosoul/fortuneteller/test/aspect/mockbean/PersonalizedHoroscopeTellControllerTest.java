package com.github.monosoul.fortuneteller.test.aspect.mockbean;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.github.monosoul.fortuneteller.aspect.AccessDeniedException;
import com.github.monosoul.fortuneteller.aspect.RestrictionAspect;
import com.github.monosoul.fortuneteller.common.ZodiacSign;
import com.github.monosoul.fortuneteller.domain.HoroscopeTeller;
import com.github.monosoul.fortuneteller.test.aspect.RequestContextHolderConfigurer;
import com.github.monosoul.fortuneteller.web.PersonalizedHoroscopeTellController;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles({"unit", "mockbean", "restrictAccess"})
public class PersonalizedHoroscopeTellControllerTest {

    private static final int LIMIT = 10;

    @MockBean
    private HoroscopeTeller horoscopeTeller;
    @MockBean
    private Function<String, ZodiacSign> zodiacSignConverter;
    @MockBean
    private Function<String, String> nameNormalizer;
    @MockBean
    private Predicate<String> ipIsAllowed;
    @Autowired
    private PersonalizedHoroscopeTellController controller;

    @Test
    void doNothingWhenAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(true);

        controller.tell(randomAlphabetic(LIMIT), randomAlphabetic(LIMIT));
    }

    @Test
    void throwExceptionWhenNotAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(false);

        assertThatThrownBy(() -> controller.tell(randomAlphabetic(LIMIT), randomAlphabetic(LIMIT)))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Configuration
    @Profile({"unit", "mockbean"})
    @Import({PersonalizedHoroscopeTellController.class, RestrictionAspect.class, RequestContextHolderConfigurer.class})
    @EnableAspectJAutoProxy
    public static class Config {

    }
}

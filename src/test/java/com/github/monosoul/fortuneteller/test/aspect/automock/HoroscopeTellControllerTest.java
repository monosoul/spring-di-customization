package com.github.monosoul.fortuneteller.test.aspect.automock;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import com.github.monosoul.fortuneteller.aspect.AccessDeniedException;
import com.github.monosoul.fortuneteller.automock.Automocked;
import com.github.monosoul.fortuneteller.web.HoroscopeTellController;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HoroscopeTellControllerTest extends TestBase {

    private static final int LIMIT = 10;

    @Automocked
    private HoroscopeTellController controller;
    @Autowired
    private Predicate<String> ipIsAllowed;

    @Test
    void doNothingWhenAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(true);

        controller.tell(randomAlphabetic(LIMIT));
    }

    @Test
    void throwExceptionWhenNotAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(false);

        assertThatThrownBy(() -> controller.tell(randomAlphabetic(LIMIT)))
                .isInstanceOf(AccessDeniedException.class);
    }
}

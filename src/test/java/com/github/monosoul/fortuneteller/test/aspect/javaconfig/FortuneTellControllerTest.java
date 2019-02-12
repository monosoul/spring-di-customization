package com.github.monosoul.fortuneteller.test.aspect.javaconfig;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.aspect.AccessDeniedException;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.web.FortuneTellController;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles({"unit", "javaconfig"})
public class FortuneTellControllerTest {

    @Autowired
    private FortuneTellController controller;
    @Autowired
    private Predicate<String> ipIsAllowed;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void doNothingWhenAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(true);

        controller.tell(mock(FortuneRequest.class));
    }

    @Test
    void throwExceptionWhenNotAllowed() {
        when(ipIsAllowed.test(anyString())).thenReturn(false);

        assertThatThrownBy(() -> controller.tell(mock(FortuneRequest.class)))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Configuration
    @Import(AspectConfiguration.class)
    @Profile({"unit", "javaconfig"})
    @EnableAspectJAutoProxy
    public static class Config {

        @Bean
        public FortuneTellController fortuneTellController() {
            return new FortuneTellController(mock(FortuneTeller.class));
        }
    }
}

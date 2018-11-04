package com.github.monosoul.fortuneteller.test.aspect.mockbean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.github.monosoul.fortuneteller.aspect.AccessDeniedException;
import com.github.monosoul.fortuneteller.aspect.RestrictionAspect;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.test.aspect.RequestContextHolderConfigurer;
import com.github.monosoul.fortuneteller.web.FortuneTellController;
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
public class FortuneTellControllerTest {

    @MockBean
    private FortuneTeller fortuneTeller;
    @MockBean
    private Predicate<String> ipIsAllowed;
    @Autowired
    private FortuneTellController controller;

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
    @Profile({"unit", "mockbean"})
    @Import({FortuneTellController.class, RestrictionAspect.class, RequestContextHolderConfigurer.class})
    @EnableAspectJAutoProxy
    public static class Config {

    }
}

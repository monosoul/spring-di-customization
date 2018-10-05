package com.github.monosoul.fortuneteller.test.aspect.mockbean;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.web.FortuneTellController;
import lombok.val;
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
@ActiveProfiles({"unit-mockbean", "tellTheTruth"})
public class FortuneTellControllerTest {

    @MockBean
    private FortuneTeller fortuneTeller;
    @Autowired
    private FortuneTellController controller;

    @Test
    void tellTheTruth() {
        val actual = controller.tell(mock(FortuneRequest.class));

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(THE_TRUTH);
    }

    @Configuration
    @Profile("unit-mockbean")
    @Import({FortuneTellController.class, TellTheTruthAspect.class})
    @EnableAspectJAutoProxy
    public static class Config {

    }
}

package com.github.monosoul.fortuneteller.test.aspect.javaconfig;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.web.FortuneTellController;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Disabled
@SpringJUnitConfig
@ActiveProfiles("unit-javaconfig")
public class FortuneTellControllerTest {

    @Autowired
    private FortuneTellController controller;
    @Mock
    private FortuneRequest request;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void tellTheTruth() {
        val actual = controller.tell(request);

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(THE_TRUTH);
    }

    @Configuration
    @Profile("unit-javaconfig")
    @EnableAspectJAutoProxy
    public static class Config {

        @Bean
        public FortuneTellController fortuneTellController() {
            return new FortuneTellController(mock(FortuneTeller.class));
        }

        @Bean
        public TellTheTruthAspect tellTheTruthAspect() {
            return new TellTheTruthAspect();
        }
    }
}

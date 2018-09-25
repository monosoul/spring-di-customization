package com.github.monosoul.fortuneteller.test.aspect.automock.v1;

import static com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect.THE_TRUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import com.github.monosoul.fortuneteller.automock.Automocked;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.web.FortuneTellController;
import lombok.val;
import org.junit.jupiter.api.Test;

public class FortuneTellControllerTest extends TestBase {

    @Automocked
    private FortuneTellController controller;

    @Test
    void tellTheTruth() {
        val actual = controller.tell(mock(FortuneRequest.class));

        assertThat(actual).isNotNull();
        assertThat(actual.getMessage()).isEqualTo(THE_TRUTH);
    }
}

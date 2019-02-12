package com.github.monosoul.fortuneteller.test.aspect.automock;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;
import com.github.monosoul.fortuneteller.automock.AutomockTestExecutionListener;
import com.github.monosoul.fortuneteller.test.aspect.automock.AspectConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles({"unit", "automock"})
@ContextConfiguration(classes = AspectConfiguration.class)
@TestExecutionListeners(listeners = AutomockTestExecutionListener.class, mergeMode = MERGE_WITH_DEFAULTS)
abstract class TestBase {

}

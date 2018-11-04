package com.github.monosoul.fortuneteller.test.aspect.automock;

import com.github.monosoul.fortuneteller.automock.AutomockTestExecutionListener;
import com.github.monosoul.fortuneteller.test.aspect.automock.AspectConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("unit-automock")
@ContextConfiguration(classes = AspectConfiguration.class)
@TestExecutionListeners(AutomockTestExecutionListener.class)
public abstract class TestBase {

}

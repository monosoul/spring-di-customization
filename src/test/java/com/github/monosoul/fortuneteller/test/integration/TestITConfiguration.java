package com.github.monosoul.fortuneteller.test.integration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integration")
@ComponentScan({"com.github.monosoul.fortuneteller.domain", "com.github.monosoul.fortuneteller.da"})
public class TestITConfiguration {
}

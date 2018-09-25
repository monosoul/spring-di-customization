package com.github.monosoul.fortuneteller.test.aspect.automock;

import com.github.monosoul.fortuneteller.aspect.TellTheTruthAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("unit-automock")
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Bean
    public TellTheTruthAspect tellTheTruthAspect() {
        return new TellTheTruthAspect();
    }
}

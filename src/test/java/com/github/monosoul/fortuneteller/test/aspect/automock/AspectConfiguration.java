package com.github.monosoul.fortuneteller.test.aspect.automock;

import static org.mockito.Mockito.mock;
import com.github.monosoul.fortuneteller.aspect.RestrictionAspect;
import com.github.monosoul.fortuneteller.test.aspect.RequestContextHolderConfigurer;
import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"unit", "automock"})
@EnableAspectJAutoProxy
public class AspectConfiguration {

    @Bean
    public RequestContextHolderConfigurer requestContextHolderConfigurer() {
        return new RequestContextHolderConfigurer();
    }

    @Bean
    public RestrictionAspect tellTheTruthAspect() {
        return new RestrictionAspect(ipIsAllowed());
    }

    @SuppressWarnings("unchecked")
    @Bean
    public Predicate<String> ipIsAllowed() {
        return mock(Predicate.class);
    }
}

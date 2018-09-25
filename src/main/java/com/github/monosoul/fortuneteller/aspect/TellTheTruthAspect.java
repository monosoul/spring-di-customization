package com.github.monosoul.fortuneteller.aspect;

import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.model.Horoscope;
import com.github.monosoul.fortuneteller.model.PersonalizedHoroscope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TellTheTruthAspect {

    public static final String THE_TRUTH = "Actually, I know nothing and you're the only master of your faith.";

    @Around("@annotation(com.github.monosoul.fortuneteller.aspect.TellTheTruth)&&" +
            "execution(public com.github.monosoul.fortuneteller.model.FortuneResponse " +
            "com.github.monosoul.fortuneteller.web..*(*))")
    public FortuneResponse tellTheTruthFortune(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Forcing the truth...");
        joinPoint.proceed();

        return FortuneResponse.builder().message(THE_TRUTH).build();
    }

    @Around("@annotation(com.github.monosoul.fortuneteller.aspect.TellTheTruth)&&" +
            "execution(public com.github.monosoul.fortuneteller.model.Horoscope " +
            "com.github.monosoul.fortuneteller.web..*(*))")
    public Horoscope tellTheTruthHoroscope(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Forcing the truth...");
        joinPoint.proceed();

        return Horoscope.builder().message(THE_TRUTH).build();
    }

    @Around("@annotation(com.github.monosoul.fortuneteller.aspect.TellTheTruth)&&" +
            "execution(public com.github.monosoul.fortuneteller.model.PersonalizedHoroscope " +
            "com.github.monosoul.fortuneteller.web..*(*, *))")
    public PersonalizedHoroscope tellTheTruthPersonalizedHoroscope(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Forcing the truth...");
        joinPoint.proceed();

        return PersonalizedHoroscope.builder()
                .name("Anonymous")
                .horoscope(Horoscope.builder().message(THE_TRUTH).build())
                .build();
    }
}

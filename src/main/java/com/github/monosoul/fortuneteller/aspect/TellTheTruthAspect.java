package com.github.monosoul.fortuneteller.aspect;

import com.github.monosoul.fortuneteller.model.FortuneResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
    public FortuneResponse tellTheTruth(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Forcing the truth...");
        val response = (FortuneResponse) joinPoint.proceed();

        return response.toBuilder().message(THE_TRUTH).build();
    }
}

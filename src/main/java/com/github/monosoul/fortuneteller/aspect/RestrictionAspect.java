package com.github.monosoul.fortuneteller.aspect;

import static java.lang.String.format;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;
import java.util.function.Predicate;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Profile("restrictAccess")
@Component
@Slf4j
public class RestrictionAspect {

    private final Predicate<String> ipIsAllowed;

    public RestrictionAspect(@NonNull final Predicate<String> ipIsAllowed) {
        this.ipIsAllowed = ipIsAllowed;
    }

    @Before("execution(public * com.github.monosoul.fortuneteller.web.*.*(..))")
    public void checkAccess() {
        val ip = getRequestSourceIp();
        log.debug("Source IP: {}", ip);

        if (!ipIsAllowed.test(getRequestSourceIp())) {
            throw new AccessDeniedException(format("Access for IP [%s] is denied", ip));
        }
    }

    private String getRequestSourceIp() {
        val requestAttributes = currentRequestAttributes();
        Assert.state(requestAttributes instanceof ServletRequestAttributes,
                "RequestAttributes needs to be a ServletRequestAttributes");

        val request = ((ServletRequestAttributes) requestAttributes).getRequest();

        return request.getRemoteAddr();
    }
}

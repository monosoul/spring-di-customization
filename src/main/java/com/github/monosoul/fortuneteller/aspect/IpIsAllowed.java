package com.github.monosoul.fortuneteller.aspect;

import static java.util.Collections.emptySet;
import java.util.Set;
import java.util.function.Predicate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("restrictAccess")
@Component
final class IpIsAllowed implements Predicate<String> {

    private final Set<String> allowed = emptySet();

    @Override
    public boolean test(final String s) {
        return allowed.contains(s);
    }
}

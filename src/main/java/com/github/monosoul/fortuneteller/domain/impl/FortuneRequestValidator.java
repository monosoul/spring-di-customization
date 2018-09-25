package com.github.monosoul.fortuneteller.domain.impl;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import java.util.function.Predicate;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class FortuneRequestValidator implements Predicate<FortuneRequest> {

    private final Predicate<String> emailValidator;

    public FortuneRequestValidator(@NonNull final Predicate<String> emailValidator) {
        this.emailValidator = emailValidator;
    }

    @Override
    public boolean test(final FortuneRequest request) {
        return emailValidator.test(request.getEmail());
    }
}

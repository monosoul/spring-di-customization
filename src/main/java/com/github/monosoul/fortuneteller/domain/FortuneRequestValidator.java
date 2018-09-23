package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class FortuneRequestValidator implements Predicate<FortuneRequest> {

    private final Predicate<String> emailValidator;

    public FortuneRequestValidator(@NonNull final Predicate<String> emailValidator) {
        this.emailValidator = emailValidator;
    }

    @Override
    public boolean test(final FortuneRequest request) {
        return emailValidator.test(request.getEmail());
    }
}

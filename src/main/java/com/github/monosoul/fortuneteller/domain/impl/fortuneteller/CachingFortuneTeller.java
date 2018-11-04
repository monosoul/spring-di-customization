package com.github.monosoul.fortuneteller.domain.impl.fortuneteller;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.CACHING;
import static com.github.monosoul.fortuneteller.spring.DecoratorType.NOT_DECORATOR;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.spring.qualifier.Decorator;
import java.util.Map;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Decorator(CACHING)
@Component
public final class CachingFortuneTeller implements FortuneTeller {

    private final FortuneTeller internal;
    private final Map<FortuneRequest, FortuneResponse> cache;

    public CachingFortuneTeller(
            @Decorator(NOT_DECORATOR)
            @NonNull final FortuneTeller internal,
            @NonNull final Map<FortuneRequest, FortuneResponse> cache
    ) {
        this.internal = internal;
        this.cache = cache;
    }

    @Override
    public FortuneResponse tell(final FortuneRequest request) {
        return cache.computeIfAbsent(request, internal::tell);
    }
}

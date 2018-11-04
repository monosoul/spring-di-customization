package com.github.monosoul.fortuneteller.domain.impl.fortuneteller;

import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import java.util.Map;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class CachingFortuneTeller implements FortuneTeller {

    private final FortuneTeller internal;
    private final Map<FortuneRequest, FortuneResponse> cache;

    public CachingFortuneTeller(
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

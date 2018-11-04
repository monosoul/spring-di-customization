package com.github.monosoul.fortuneteller.domain.impl.fortuneteller;

import static com.github.monosoul.fortuneteller.spring.DecoratorType.CACHING;
import static org.slf4j.LoggerFactory.getLogger;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.spring.qualifier.Decorator;
import lombok.NonNull;
import lombok.val;
import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public final class LoggingFortuneTeller implements FortuneTeller {

    private final FortuneTeller internal;
    private final Logger logger;

    public LoggingFortuneTeller(
            @Decorator(CACHING)
            @NonNull final FortuneTeller internal
    ) {
        this.internal = internal;
        this.logger = getLogger(internal.getClass());
    }

    @Override
    public FortuneResponse tell(final FortuneRequest request) {
        logger.info("Got request: {}", request);

        val response = internal.tell(request);

        logger.info("Going to answer: {}", response);

        return response;
    }
}

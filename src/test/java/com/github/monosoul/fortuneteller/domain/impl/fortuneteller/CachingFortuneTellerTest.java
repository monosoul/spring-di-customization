package com.github.monosoul.fortuneteller.domain.impl.fortuneteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import java.util.Map;
import java.util.function.Function;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

@SuppressWarnings("unchecked")
class CachingFortuneTellerTest {

    @Mock
    private FortuneTeller internal;
    @Mock
    private Map<FortuneRequest, FortuneResponse> cache;
    @Mock
    private FortuneRequest request;
    @Mock
    private FortuneResponse response;
    @Captor
    private ArgumentCaptor<Function> captor;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(internal.tell(request)).thenReturn(response);
        when(cache.computeIfAbsent(any(FortuneRequest.class), any(Function.class))).thenReturn(response);
    }

    @Test
    void tell() {
        val actual = new CachingFortuneTeller(internal, cache).tell(request);

        verify(cache).computeIfAbsent(eq(request), captor.capture());
        verifyNoMoreInteractions(cache, internal);

        assertThat(actual).isSameAs(response);

        val internalResponse = captor.getValue().apply(request);
        verify(internal).tell(request);
        verifyNoMoreInteractions(internal);

        assertThat(internalResponse).isSameAs(response);
    }
}
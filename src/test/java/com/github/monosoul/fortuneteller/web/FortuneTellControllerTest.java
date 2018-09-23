package com.github.monosoul.fortuneteller.web;

import com.github.monosoul.fortuneteller.domain.FortuneTeller;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class FortuneTellControllerTest {

    @Mock
    private FortuneTeller fortuneTeller;
    @Mock
    private FortuneRequest request;
    @Mock
    private FortuneResponse response;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(fortuneTeller.tell(request)).thenReturn(response);
    }

    @Test
    void tell() {
        val actual = new FortuneTellController(fortuneTeller).tell(request);

        verify(fortuneTeller).tell(request);
        verifyNoMoreInteractions(fortuneTeller);

        assertThat(actual).isSameAs(response);
    }
}
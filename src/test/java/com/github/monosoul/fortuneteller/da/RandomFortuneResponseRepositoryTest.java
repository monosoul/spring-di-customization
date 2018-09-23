package com.github.monosoul.fortuneteller.da;

import com.github.monosoul.fortuneteller.model.FortuneResponse;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.IntStream.generate;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class RandomFortuneResponseRepositoryTest {

    @Mock
    private List<FortuneResponse> responses;
    @Mock
    private FortuneResponse response;
    @Captor
    private ArgumentCaptor<Integer> captor;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(responses.get(anyInt())).thenReturn(response);
    }

    @ParameterizedTest
    @MethodSource("positiveIntStream")
    void get(final int listSize) {
        when(responses.size()).thenReturn(listSize);

        val actual = new RandomFortuneResponseRepository(responses).get();

        verify(responses).size();
        verify(responses).get(captor.capture());
        verifyNoMoreInteractions(responses);

        assertThat(actual).isSameAs(response);
        assertThat(captor.getValue()).isBetween(0, listSize);
    }

    private static IntStream positiveIntStream() {
        return generate(() -> nextInt(0, MAX_VALUE)).limit(10L);
    }
}
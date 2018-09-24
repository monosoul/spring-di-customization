package com.github.monosoul.fortuneteller.util;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public final class Util {
    public static <T extends Enum> T randomEnum(final Class<T> input) {
        return input.getEnumConstants()[nextInt(0, input.getEnumConstants().length)];
    }
}

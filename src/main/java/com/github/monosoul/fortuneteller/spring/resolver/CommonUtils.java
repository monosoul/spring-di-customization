package com.github.monosoul.fortuneteller.spring.resolver;

import lombok.NonNull;
import org.springframework.beans.factory.config.DependencyDescriptor;

final class CommonUtils {

    static Class<?> getDependentClass(final DependencyDescriptor descriptor) {
        return descriptor.getMember().getDeclaringClass();
    }

    static String getSimpleName(@NonNull final String beanClassName) {
        return beanClassName.replaceAll(".*\\.", "");
    }
}

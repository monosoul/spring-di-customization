package com.github.monosoul.fortuneteller.spring.resolver;

import static com.github.monosoul.fortuneteller.spring.resolver.CommonUtils.getDependentClass;
import java.util.function.Predicate;
import lombok.val;
import org.springframework.beans.factory.config.DependencyDescriptor;

class IsSameTypeAsDependent implements Predicate<DependencyDescriptor> {

    @Override
    public boolean test(final DependencyDescriptor descriptor) {
        val dependent = getDependentClass(descriptor);

        return descriptor.getDependencyType().isAssignableFrom(dependent);
    }
}

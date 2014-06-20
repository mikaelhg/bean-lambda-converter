package io.mikael.convert.bean;

import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface ExceptionalSupplier<T> extends Supplier<Optional<T>> {

    @Override
    default Optional<T> get() {
        try {
            return Optional.of(getWithException());
        } catch (final Exception e) {
            return Optional.empty();
        }
    }

    T getWithException() throws Exception;

}

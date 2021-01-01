package io.mikael.convert.bean;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class FieldWithExceptionHandler<SOURCE, TARGET, D> extends Field<SOURCE, TARGET, D> {

    protected final Consumer<Exception> exceptionHandler;

    protected FieldWithExceptionHandler(final Function<SOURCE, D> in, final BiConsumer<TARGET, D> out,
                                        final Consumer<Exception> exceptionHandler)
    {
        super(in, out);
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    protected void transferData(final SOURCE source, final TARGET target) {
        try {
            out.accept(target, in.apply(source));
        } catch (final Exception e) {
            exceptionHandler.accept(e);
        }
    }
}

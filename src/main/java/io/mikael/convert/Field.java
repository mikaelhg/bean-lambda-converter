package io.mikael.convert;

import java.util.function.BiConsumer;
import java.util.function.Function;

class Field<SOURCE, TARGET, D> extends AbstractFieldTransfer<SOURCE, TARGET> {

    private final Function<SOURCE, D> in;

    private final BiConsumer<TARGET, D> out;

    protected Field(final Converter<SOURCE, TARGET> converter,
                 final Function<SOURCE, D> in, final BiConsumer<TARGET, D> out)
    {
        super(converter);
        this.in = in;
        this.out = out;
    }

    @Override
    protected void transferData(final SOURCE source, final TARGET target) {
        out.accept(target, in.apply(source));
    }
}

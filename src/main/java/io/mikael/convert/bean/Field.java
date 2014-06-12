package io.mikael.convert.bean;

import java.util.function.BiConsumer;
import java.util.function.Function;

class Field<SOURCE, TARGET, D> extends AbstractFieldTransfer<SOURCE, TARGET> {

    protected final Function<SOURCE, D> in;

    protected final BiConsumer<TARGET, D> out;

    protected Field(final Function<SOURCE, D> in, final BiConsumer<TARGET, D> out) {
        this.in = in;
        this.out = out;
    }

    @Override
    protected void transferData(final SOURCE source, final TARGET target) {
        out.accept(target, in.apply(source));
    }
}

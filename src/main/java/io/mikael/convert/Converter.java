package io.mikael.convert;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The primary public face of the converter prototype.
 */
public class Converter<SOURCE, TARGET> {

    private final List<AbstractFieldTransfer<SOURCE, TARGET>> transfers = new LinkedList<>();

    public static <S, T> Converter<S, T> fromTo(final Class<S> cs, final Class<T> ct) {
        return new Converter<>();
    }

    public static <S, T> Converter<S, T> fromTo() {
        return new Converter<>();
    }

    public <D> Converter<SOURCE, TARGET> field(
            final Function<SOURCE, D> in, final BiConsumer<TARGET, D> out)
    {
        transfers.add(new Field<>(this, in, out));
        return this;
    }

    public FieldGroup<SOURCE, TARGET> fieldGroup() {
        final FieldGroup<SOURCE, TARGET> ret = new FieldGroup<>(this);
        transfers.add(ret);
        return ret;
    }

    public FieldGroup<SOURCE, TARGET> fieldGroup(final Predicate<SOURCE> isGroupActive) {
        final FieldGroup<SOURCE, TARGET> ret = new FieldGroup<>(this, isGroupActive);
        transfers.add(ret);
        return ret;
    }

    public TARGET convert(final SOURCE source, final TARGET target) {
        transfers.stream()
                .filter(t -> t.isActive(source))
                .forEach(t -> t.transferData(source, target));
        return target;
    }

    public TARGET convert(final SOURCE source, final Supplier<TARGET> targetSupplier) {
        return convert(source, targetSupplier.get());
    }

    public TARGET convert(final Supplier<SOURCE> sourceSupplier, final TARGET target) {
        return convert(sourceSupplier.get(), target);
    }

    public TARGET convert(final Supplier<SOURCE> sourceSupplier, final Supplier<TARGET> targetSupplier) {
        return convert(sourceSupplier.get(), targetSupplier.get());
    }

}

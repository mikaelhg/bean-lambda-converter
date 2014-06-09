package io.mikael.convert;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FieldGroup<SOURCE, TARGET> extends AbstractFieldTransfer<SOURCE, TARGET> {

    private final List<AbstractFieldTransfer<SOURCE, TARGET>> transfers = new LinkedList<>();

    private final Predicate<SOURCE> predicate;

    private FieldGroup() {
        this(null);
    }

    protected FieldGroup(final Converter<SOURCE, TARGET> converter) {
        this(converter, null);
    }

    protected FieldGroup(final Converter<SOURCE, TARGET> converter, final Predicate<SOURCE> predicate) {
        super(converter);
        this.predicate = predicate;
    }

    public Converter<SOURCE, TARGET> end() {
        return converter;
    }

    public <D> FieldGroup<SOURCE, TARGET> field(
            final Function<SOURCE, D> in, final BiConsumer<TARGET, D> out)
    {
        transfers.add(new Field<>(converter, in, out));
        return this;
    }

    @Override
    protected void transferData(final SOURCE source, final TARGET target) {
        transfers.stream()
                .filter(t -> t.isActive(source))
                .forEach(t -> t.transferData(source, target));
    }

    @Override
    protected boolean isActive(final SOURCE source) {
        return predicate == null || predicate.test(source);
    }
}

package io.mikael.convert.bean;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A group of field transfers, which will only be executed if the given predicate
 * evaluates as true. Typically used to configure transfers which only make sense
 * in certain conditions.
 */
public class FieldGroup<SOURCE, TARGET> extends AbstractFieldTransfer<SOURCE, TARGET> {

    protected final List<AbstractFieldTransfer<SOURCE, TARGET>> transfers = new LinkedList<>();

    protected final Predicate<SOURCE> predicate;

    protected final BeanConverter<SOURCE, TARGET> converter;

    protected FieldGroup(final BeanConverter<SOURCE, TARGET> converter) {
        this(converter, null);
    }

    protected FieldGroup(final BeanConverter<SOURCE, TARGET> converter, final Predicate<SOURCE> predicate) {
        this.converter = converter;
        this.predicate = predicate;
    }

    /**
     * End this field group, continue the fluent expression from its parent converter.
     */
    public BeanConverter<SOURCE, TARGET> end() {
        return converter;
    }

    /**
     * Indicate the transfer of information from a source bean method or lambda,
     * to a target bean method or lambda.
     */
    public <D> FieldGroup<SOURCE, TARGET> field(
            final Function<SOURCE, D> in, final BiConsumer<TARGET, D> out)
    {
        transfers.add(new Field<>(in, out));
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

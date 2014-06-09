package io.mikael.convert;

public abstract class AbstractFieldTransfer<SOURCE, TARGET> {
    protected final Converter<SOURCE, TARGET> converter;
    public AbstractFieldTransfer(final Converter<SOURCE, TARGET> converter) {
        this.converter = converter;
    }
    public boolean isActive(final SOURCE source) {
        return true;
    }
    public abstract void transferData(final SOURCE source, final TARGET target);
}

package io.mikael.convert;

abstract class AbstractFieldTransfer<SOURCE, TARGET> {

    protected final Converter<SOURCE, TARGET> converter;

    protected AbstractFieldTransfer(final Converter<SOURCE, TARGET> converter) {
        this.converter = converter;
    }

    protected boolean isActive(final SOURCE source) {
        return true;
    }

    protected abstract void transferData(final SOURCE source, final TARGET target);
}

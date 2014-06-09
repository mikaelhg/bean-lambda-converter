package io.mikael.convert.bean;

abstract class AbstractFieldTransfer<SOURCE, TARGET> {

    protected final BeanConverter<SOURCE, TARGET> converter;

    protected AbstractFieldTransfer(final BeanConverter<SOURCE, TARGET> converter) {
        this.converter = converter;
    }

    protected boolean isActive(final SOURCE source) {
        return true;
    }

    protected abstract void transferData(final SOURCE source, final TARGET target);
}

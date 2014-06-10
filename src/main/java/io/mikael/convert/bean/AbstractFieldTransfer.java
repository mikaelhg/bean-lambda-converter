package io.mikael.convert.bean;

abstract class AbstractFieldTransfer<SOURCE, TARGET> {

    protected boolean isActive(final SOURCE source) {
        return true;
    }

    protected abstract void transferData(final SOURCE source, final TARGET target);
}

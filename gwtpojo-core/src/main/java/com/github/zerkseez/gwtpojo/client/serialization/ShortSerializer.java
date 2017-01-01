package com.github.zerkseez.gwtpojo.client.serialization;

public class ShortSerializer extends AbstractNumberSerializer<Short> {
    public static final ShortSerializer INSTANCE = new ShortSerializer();

    private ShortSerializer() {
    }

    @Override
    protected Short castFromDouble(final double value) {
        return (short)value;
    }

    @Override
    protected Short parseFromString(final String value) {
        return Short.valueOf(value);
    }
}

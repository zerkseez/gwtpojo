package com.github.zerkseez.gwtpojo.client.serialization;

public class LongSerializer extends AbstractNumberSerializer<Long> {
    public static final LongSerializer INSTANCE = new LongSerializer();

    private LongSerializer() {
    }

    @Override
    protected Long castFromDouble(final double value) {
        return (long)value;
    }

    @Override
    protected Long parseFromString(final String value) {
        return Long.valueOf(value);
    }
}

package com.github.zerkseez.gwtpojo.client.serialization;

public class IntegerSerializer extends AbstractNumberSerializer<Integer> {
    public static final IntegerSerializer INSTANCE = new IntegerSerializer();

    private IntegerSerializer() {
    }

    @Override
    protected Integer castFromDouble(final double value) {
        return (int)value;
    }

    @Override
    protected Integer parseFromString(final String value) {
        return Integer.valueOf(value);
    }
}

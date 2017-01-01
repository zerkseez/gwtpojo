package com.github.zerkseez.gwtpojo.client.serialization;

public class DoubleSerializer extends AbstractNumberSerializer<Double> {
    public static final DoubleSerializer INSTANCE = new DoubleSerializer();

    private DoubleSerializer() {
    }

    @Override
    protected Double castFromDouble(final double value) {
        return value;
    }

    @Override
    protected Double parseFromString(final String value) {
        return Double.valueOf(value);
    }
}

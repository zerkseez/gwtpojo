package com.github.zerkseez.gwtpojo.client.serialization;

public class FloatSerializer extends AbstractNumberSerializer<Float> {
    public static final FloatSerializer INSTANCE = new FloatSerializer();

    private FloatSerializer() {
    }

    @Override
    protected Float castFromDouble(final double value) {
        return (float)value;
    }

    @Override
    protected Float parseFromString(final String value) {
        return Float.valueOf(value);
    }
}

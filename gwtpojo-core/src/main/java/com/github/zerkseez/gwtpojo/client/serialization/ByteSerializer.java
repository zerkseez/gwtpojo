package com.github.zerkseez.gwtpojo.client.serialization;

public class ByteSerializer extends AbstractNumberSerializer<Byte> {
    public static final ByteSerializer INSTANCE = new ByteSerializer();

    private ByteSerializer() {
    }

    @Override
    protected Byte castFromDouble(final double value) {
        return (byte)value;
    }

    @Override
    protected Byte parseFromString(final String value) {
        return Byte.valueOf(value);
    }
}

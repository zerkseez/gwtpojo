package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class DoubleArraySerializer implements GWTPojoSerializer<double[]> {
    public static final DoubleArraySerializer INSTANCE = new DoubleArraySerializer();

    private DoubleArraySerializer() {
    }

    @Override
    public JSONValue serialize(final double[] value) {
        if (value == null) {
            return JSONNull.getInstance();
        }

        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            jsonArray.set(i, DoubleSerializer.INSTANCE.serialize(value[i]));
        }
        return jsonArray;
    }

    @Override
    public double[] deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as double[]");
        }

        final double[] array = new double[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = DoubleSerializer.INSTANCE.deserialize(jsonArray.get(i));
        }
        return array;
    }
}

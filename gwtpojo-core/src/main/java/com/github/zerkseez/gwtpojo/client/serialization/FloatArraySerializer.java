package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class FloatArraySerializer implements GWTPojoSerializer<float[]> {
    public static final FloatArraySerializer INSTANCE = new FloatArraySerializer();

    private FloatArraySerializer() {
    }

    @Override
    public JSONValue serialize(final float[] value) {
        if (value == null) {
            return JSONNull.getInstance();
        }

        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            jsonArray.set(i, FloatSerializer.INSTANCE.serialize(value[i]));
        }
        return jsonArray;
    }

    @Override
    public float[] deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as float[]");
        }

        final float[] array = new float[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = FloatSerializer.INSTANCE.deserialize(jsonArray.get(i));
        }
        return array;
    }
}

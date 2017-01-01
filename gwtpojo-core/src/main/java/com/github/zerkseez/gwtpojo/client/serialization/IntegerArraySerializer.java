package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class IntegerArraySerializer implements GWTPojoSerializer<int[]> {
    public static final IntegerArraySerializer INSTANCE = new IntegerArraySerializer();

    private IntegerArraySerializer() {
    }

    @Override
    public JSONValue serialize(final int[] value) {
        if (value == null) {
            return JSONNull.getInstance();
        }

        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            jsonArray.set(i, IntegerSerializer.INSTANCE.serialize(value[i]));
        }
        return jsonArray;
    }

    @Override
    public int[] deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as int[]");
        }

        final int[] array = new int[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = IntegerSerializer.INSTANCE.deserialize(jsonArray.get(i));
        }
        return array;
    }
}

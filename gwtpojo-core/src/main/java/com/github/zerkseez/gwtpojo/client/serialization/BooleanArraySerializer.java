package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class BooleanArraySerializer implements GWTPojoSerializer<boolean[]> {
    public static final BooleanArraySerializer INSTANCE = new BooleanArraySerializer();

    private BooleanArraySerializer() {
    }

    @Override
    public JSONValue serialize(final boolean[] value) {
        if (value == null) {
            return JSONNull.getInstance();
        }

        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            jsonArray.set(i, BooleanSerializer.INSTANCE.serialize(value[i]));
        }
        return jsonArray;
    }

    @Override
    public boolean[] deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as boolean[]");
        }

        final boolean[] array = new boolean[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = BooleanSerializer.INSTANCE.deserialize(jsonArray.get(i));
        }
        return array;
    }
}

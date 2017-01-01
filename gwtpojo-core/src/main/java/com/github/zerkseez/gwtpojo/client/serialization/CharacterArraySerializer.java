package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class CharacterArraySerializer implements GWTPojoSerializer<char[]> {
    public static final CharacterArraySerializer INSTANCE = new CharacterArraySerializer();

    private CharacterArraySerializer() {
    }

    @Override
    public JSONValue serialize(final char[] value) {
        if (value == null) {
            return JSONNull.getInstance();
        }

        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            jsonArray.set(i, CharacterSerializer.INSTANCE.serialize(value[i]));
        }
        return jsonArray;
    }

    @Override
    public char[] deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as byte[]");
        }

        final char[] array = new char[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = CharacterSerializer.INSTANCE.deserialize(jsonArray.get(i));
        }
        return array;
    }
}

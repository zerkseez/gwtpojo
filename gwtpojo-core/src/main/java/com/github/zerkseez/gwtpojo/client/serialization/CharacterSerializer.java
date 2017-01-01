package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class CharacterSerializer implements GWTPojoSerializer<Character> {
    public static final CharacterSerializer INSTANCE = new CharacterSerializer();

    private CharacterSerializer() {
    }

    @Override
    public JSONValue serialize(final Character value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        return new JSONString(value.toString());
    }

    @Override
    public Character deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONString jsonString = jsonValue.isString();
        if (jsonString != null) {
            if (jsonString.stringValue().isEmpty()) {
                return null;
            }
            if (jsonString.stringValue().length() == 1) {
                return jsonString.stringValue().charAt(0);
            }
        }

        throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as character");
    }
}

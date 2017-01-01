package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class StringSerializer implements GWTPojoSerializer<String> {
    public static final StringSerializer INSTANCE = new StringSerializer();

    private StringSerializer() {
    }

    @Override
    public JSONValue serialize(final String value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        return new JSONString(value);
    }

    @Override
    public String deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONString jsonString = jsonValue.isString();
        if (jsonString != null) {
            return jsonString.stringValue();
        }

        final JSONNumber jsonNumber = jsonValue.isNumber();
        if (jsonNumber != null) {
            return Double.toString(jsonNumber.doubleValue());
        }

        final JSONBoolean jsonBoolean = jsonValue.isBoolean();
        if (jsonBoolean != null) {
            return Boolean.toString(jsonBoolean.booleanValue());
        }

        throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as String");
    }
}

package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class BooleanSerializer implements GWTPojoSerializer<Boolean> {
    public static final BooleanSerializer INSTANCE = new BooleanSerializer();
	
	private BooleanSerializer() {
	}
	
	@Override
	public JSONValue serialize(final Boolean value) {
		if (value == null) {
			return JSONNull.getInstance();
		}
		return JSONBoolean.getInstance(value.booleanValue());
	}

	@Override
	public Boolean deserialize(final JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isNull() != null) {
			return null;
		}
		
		final JSONBoolean jsonBoolean = jsonValue.isBoolean();
		if (jsonBoolean != null) {
			return jsonBoolean.booleanValue();
		}

		final JSONString jsonString = jsonValue.isString();
		if (jsonString != null) {
			if (jsonString.stringValue().isEmpty()) {
				return null;
			}
			return Boolean.valueOf(jsonString.stringValue());
		}

		throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as Boolean");
	}
}

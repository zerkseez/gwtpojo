package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public final class GWTPojoSerializerUtils {
    public static void setProperty(final JSONObject jsonObject, final String propertyName, final JSONValue value) {
        JSONValue finalValue = value;
        if (GWTPojoConfigurations.isExcludeNullProperties() && value.isNull() != null) {
            finalValue = null;
        }
        jsonObject.put(propertyName, finalValue);
    }

    private GWTPojoSerializerUtils() {
        // Prevent instantiation
    }
}

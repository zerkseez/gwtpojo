package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class VoidSerializer implements GWTPojoSerializer<Void> {
    public static final VoidSerializer INSTANCE = new VoidSerializer();

    private VoidSerializer() {
    }

    @Override
    public JSONValue serialize(final Void value) {
        return JSONNull.getInstance();
    }

    @Override
    public Void deserialize(final JSONValue jsonValue) {
        return null;
    }
}

package com.github.zerkseez.gwtpojo.client.serialization;

import com.google.gwt.json.client.JSONValue;

public interface GWTPojoSerializer<T> {
    JSONValue serialize(T value);
    T deserialize(JSONValue jsonValue);
}

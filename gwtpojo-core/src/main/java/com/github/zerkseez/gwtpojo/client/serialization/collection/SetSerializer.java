package com.github.zerkseez.gwtpojo.client.serialization.collection;

import java.util.HashSet;
import java.util.Set;

import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializerException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class SetSerializer<T> implements GWTPojoSerializer<Set<T>> {
    private final GWTPojoSerializer<T> tSerializer;

    public SetSerializer(final GWTPojoSerializer<T> tSerializer) {
        this.tSerializer = tSerializer;
    }

    @Override
    public JSONValue serialize(final Set<T> value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        final JSONArray jsonArray = new JSONArray();
        int i = 0;
        for (T t : value) {
            jsonArray.set(i++, tSerializer.serialize(t));
        }
        return jsonArray;
    }

    @Override
    public Set<T> deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as Set<?>");
        }

        final Set<T> set = new HashSet<T>();
        for (int i = 0; i < jsonArray.size(); i++) {
            set.add(tSerializer.deserialize(jsonArray.get(i)));
        }
        return set;
    }
}

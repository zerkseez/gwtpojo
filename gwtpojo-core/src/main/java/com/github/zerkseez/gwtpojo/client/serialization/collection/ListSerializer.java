package com.github.zerkseez.gwtpojo.client.serialization.collection;

import java.util.ArrayList;
import java.util.List;

import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializerException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class ListSerializer<T> implements GWTPojoSerializer<List<T>> {
    private final GWTPojoSerializer<T> tSerializer;

    public ListSerializer(final GWTPojoSerializer<T> tSerializer) {
        this.tSerializer = tSerializer;
    }

    @Override
    public JSONValue serialize(final List<T> value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.size(); i++) {
            jsonArray.set(i, tSerializer.serialize(value.get(i)));
        }
        return jsonArray;
    }

    @Override
    public List<T> deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as List<?>");
        }

        final List<T> list = new ArrayList<T>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(tSerializer.deserialize(jsonArray.get(i)));
        }
        return list;
    }
}

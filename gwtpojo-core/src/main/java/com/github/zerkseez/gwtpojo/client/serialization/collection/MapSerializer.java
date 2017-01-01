package com.github.zerkseez.gwtpojo.client.serialization.collection;

import java.util.HashMap;
import java.util.Map;

import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializerException;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializerUtils;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class MapSerializer<K, V> implements GWTPojoSerializer<Map<K, V>> {
    private final GWTPojoSerializer<K> kSerializer;
    private final GWTPojoSerializer<V> vSerializer;

    public MapSerializer(final GWTPojoSerializer<K> kSerializer, final GWTPojoSerializer<V> vSerializer) {
        this.kSerializer = kSerializer;
        this.vSerializer = vSerializer;
    }

    @Override
    public JSONValue serialize(final Map<K, V> map) {
        if (map == null) {
            return JSONNull.getInstance();
        }
        final JSONObject jsonObject = new JSONObject();
        for (K key : map.keySet()) {
            GWTPojoSerializerUtils.setProperty(jsonObject, key.toString(), vSerializer.serialize(map.get(key)));
        }
        return jsonObject;
    }

    @Override
    public Map<K, V> deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONObject jsonObject = jsonValue.isObject();
        if (jsonObject == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as Map<?, ?>");
        }

        final Map<K, V> map = new HashMap<K, V>();
        for (String keyString : jsonObject.keySet()) {
            final K key = kSerializer.deserialize(new JSONString(keyString));
            final V value = vSerializer.deserialize(jsonObject.get(keyString));
            map.put(key, value);
        }
        return map;
    }
}

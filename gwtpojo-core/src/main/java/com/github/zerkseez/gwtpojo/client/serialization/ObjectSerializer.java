/*******************************************************************************
 * Copyright 2016 Xerxes Tsang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.github.zerkseez.gwtpojo.client.serialization;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.zerkseez.gwtpojo.client.serialization.collection.ListSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.collection.MapSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.collection.SetSerializer;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class ObjectSerializer implements GWTPojoSerializer<Object> {
    public static final ObjectSerializer INSTANCE = new ObjectSerializer();

    private ObjectSerializer() {
    }

    @Override
    public JSONValue serialize(final Object value) {
        if (value == null) {
            return JSONNull.getInstance();
        } else if (value instanceof Number) {
            return DoubleSerializer.INSTANCE.serialize((Number)value);
        } else if (value instanceof Boolean) {
            return BooleanSerializer.INSTANCE.serialize((Boolean)value);
        } else if (value instanceof boolean[]) {
            return BooleanArraySerializer.INSTANCE.serialize((boolean[])value);
        } else if (value instanceof byte[]) {
            return ByteArraySerializer.INSTANCE.serialize((byte[])value);
        } else if (value instanceof short[]) {
            return ShortArraySerializer.INSTANCE.serialize((short[])value);
        } else if (value instanceof int[]) {
            return IntegerArraySerializer.INSTANCE.serialize((int[])value);
        } else if (value instanceof long[]) {
            return LongArraySerializer.INSTANCE.serialize((long[])value);
        } else if (value instanceof float[]) {
            return FloatArraySerializer.INSTANCE.serialize((float[])value);
        } else if (value instanceof double[]) {
            return DoubleArraySerializer.INSTANCE.serialize((double[])value);
        } else if (value instanceof Object[]) {
            final Object[] objectArray = (Object[])value;
            final JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < objectArray.length; i++) {
                jsonArray.set(i, serialize(objectArray[i]));
            }
            return jsonArray;
        } else if (value instanceof Date) {
            return DateSerializer.INSTANCE.serialize((Date)value);
        } else if (value instanceof List) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>)value;
            return new ListSerializer<Object>(this).serialize(list);
        } else if (value instanceof Set) {
            @SuppressWarnings("unchecked")
            final Set<Object> set = (Set<Object>)value;
            return new SetSerializer<Object>(this).serialize(set);
        } else if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> map = (Map<String, Object>)value;
            return new MapSerializer<String, Object>(StringSerializer.INSTANCE, this).serialize(map);
        }
        return new JSONString(value.toString());
    }

    @Override
    public Object deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONString jsonString = jsonValue.isString();
        if (jsonString != null) {
            return jsonString.stringValue();
        }

        final JSONNumber jsonNumber = jsonValue.isNumber();
        if (jsonNumber != null) {
            return jsonNumber.doubleValue();
        }

        final JSONBoolean jsonBoolean = jsonValue.isBoolean();
        if (jsonBoolean != null) {
            return jsonBoolean.booleanValue();
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray != null) {
            final List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < jsonArray.size(); i++) {
                list.add(deserialize(jsonArray.get(i)));
            }
        }

        final JSONObject jsonObject = jsonValue.isObject();
        if (jsonObject != null) {
            final Map<String, Object> map = new HashMap<String, Object>();
            for (String key : jsonObject.keySet()) {
                map.put(key, deserialize(jsonObject.get(key)));
            }
        }

        throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as Object");
    }
}

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

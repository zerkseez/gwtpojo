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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class ByteArraySerializer implements GWTPojoSerializer<byte[]> {
    public static final ByteArraySerializer INSTANCE = new ByteArraySerializer();

    private ByteArraySerializer() {
    }

    @Override
    public JSONValue serialize(final byte[] value) {
        if (value == null) {
            return JSONNull.getInstance();
        }

        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.length; i++) {
            jsonArray.set(i, ByteSerializer.INSTANCE.serialize(value[i]));
        }
        return jsonArray;
    }

    @Override
    public byte[] deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONArray jsonArray = jsonValue.isArray();
        if (jsonArray == null) {
            throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as byte[]");
        }

        final byte[] array = new byte[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = ByteSerializer.INSTANCE.deserialize(jsonArray.get(i));
        }
        return array;
    }
}

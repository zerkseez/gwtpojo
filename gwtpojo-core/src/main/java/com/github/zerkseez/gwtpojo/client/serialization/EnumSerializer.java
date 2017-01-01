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

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class EnumSerializer<T extends Enum<T>> implements GWTPojoSerializer<T> {
    private final Class<T> enumClass;

    public EnumSerializer(final Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public JSONValue serialize(final T value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        return new JSONString(value.toString());
    }

    @Override
    public T deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONString jsonString = jsonValue.isString();
        if (jsonString == null) {
            throw new GWTPojoSerializerException(
                    "Unable to deserialize " + jsonValue.toString() + " as " + enumClass.getName());
        }

        final String stringValue = jsonString.stringValue();
        if (stringValue.isEmpty()) {
            return null;
        }

        return Enum.valueOf(enumClass, stringValue);
    }
}

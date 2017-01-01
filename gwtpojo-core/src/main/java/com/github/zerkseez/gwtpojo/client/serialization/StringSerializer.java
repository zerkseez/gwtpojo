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

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class StringSerializer implements GWTPojoSerializer<String> {
    public static final StringSerializer INSTANCE = new StringSerializer();

    private StringSerializer() {
    }

    @Override
    public JSONValue serialize(final String value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        return new JSONString(value);
    }

    @Override
    public String deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONString jsonString = jsonValue.isString();
        if (jsonString != null) {
            return jsonString.stringValue();
        }

        final JSONNumber jsonNumber = jsonValue.isNumber();
        if (jsonNumber != null) {
            return Double.toString(jsonNumber.doubleValue());
        }

        final JSONBoolean jsonBoolean = jsonValue.isBoolean();
        if (jsonBoolean != null) {
            return Boolean.toString(jsonBoolean.booleanValue());
        }

        throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as String");
    }
}

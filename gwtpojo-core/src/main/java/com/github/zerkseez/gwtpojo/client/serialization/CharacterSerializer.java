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

public class CharacterSerializer implements GWTPojoSerializer<Character> {
    public static final CharacterSerializer INSTANCE = new CharacterSerializer();

    private CharacterSerializer() {
    }

    @Override
    public JSONValue serialize(final Character value) {
        if (value == null) {
            return JSONNull.getInstance();
        }
        return new JSONString(value.toString());
    }

    @Override
    public Character deserialize(final JSONValue jsonValue) {
        if (jsonValue == null || jsonValue.isNull() != null) {
            return null;
        }

        final JSONString jsonString = jsonValue.isString();
        if (jsonString != null) {
            if (jsonString.stringValue().isEmpty()) {
                return null;
            }
            if (jsonString.stringValue().length() == 1) {
                return jsonString.stringValue().charAt(0);
            }
        }

        throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as character");
    }
}

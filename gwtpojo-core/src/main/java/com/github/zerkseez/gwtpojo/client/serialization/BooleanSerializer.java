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
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class BooleanSerializer implements GWTPojoSerializer<Boolean> {
    public static final BooleanSerializer INSTANCE = new BooleanSerializer();
	
	private BooleanSerializer() {
	}
	
	@Override
	public JSONValue serialize(final Boolean value) {
		if (value == null) {
			return JSONNull.getInstance();
		}
		return JSONBoolean.getInstance(value.booleanValue());
	}

	@Override
	public Boolean deserialize(final JSONValue jsonValue) {
		if (jsonValue == null || jsonValue.isNull() != null) {
			return null;
		}
		
		final JSONBoolean jsonBoolean = jsonValue.isBoolean();
		if (jsonBoolean != null) {
			return jsonBoolean.booleanValue();
		}

		final JSONString jsonString = jsonValue.isString();
		if (jsonString != null) {
			if (jsonString.stringValue().isEmpty()) {
				return null;
			}
			return Boolean.valueOf(jsonString.stringValue());
		}

		throw new GWTPojoSerializerException("Unable to deserialize " + jsonValue.toString() + " as Boolean");
	}
}

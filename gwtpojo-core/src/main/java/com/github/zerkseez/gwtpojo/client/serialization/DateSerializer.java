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

import java.util.Date;

import com.google.gwt.json.client.JSONValue;

public class DateSerializer implements GWTPojoSerializer<Date> {
    public static final DateSerializer INSTANCE = new DateSerializer();

    private DateSerializer() {
    }

    @Override
    public JSONValue serialize(final Date value) {
        return StringSerializer.INSTANCE.serialize(serializeAsString(value));
    }

    public String serializeAsString(final Date value) {
        if (value == null) {
            return null;
        }
        return GWTPojoConfigurations.getDateFormatter().format(value);
    }

    @Override
    public Date deserialize(final JSONValue jsonValue) {
        return deserialize(StringSerializer.INSTANCE.deserialize(jsonValue));
    }

    public Date deserialize(final String dateString) {
        if (dateString == null) {
            return null;
        }
        return GWTPojoConfigurations.getDateFormatter().parse(dateString);
    }
}

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

package com.github.zerkseez.gwtpojo.client.serialization;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;

public class DefaultDateFormatter implements DateFormatter {
    public static final DefaultDateFormatter INSTANCE = new DefaultDateFormatter();
    private static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);

    private DefaultDateFormatter() {
    }

    @Override
    public Date parse(final String dateString) {
        return DATE_TIME_FORMAT.parse(dateString);
    }

    @Override
    public String format(final Date date) {
        return DATE_TIME_FORMAT.format(date);
    }
}

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

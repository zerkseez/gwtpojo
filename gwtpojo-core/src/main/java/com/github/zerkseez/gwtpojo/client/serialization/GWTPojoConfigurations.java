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

public final class GWTPojoConfigurations {
    private static boolean excludeNullProperties = true;
    private static DateFormatter dateFormatter = DefaultDateFormatter.INSTANCE;

    public static boolean isExcludeNullProperties() {
        return excludeNullProperties;
    }

    public static void setExcludeNullProperties(boolean excludeNullProperties) {
        GWTPojoConfigurations.excludeNullProperties = excludeNullProperties;
    }

    public static DateFormatter getDateFormatter() {
        return dateFormatter;
    }

    public static void setDateFormatter(DateFormatter dateFormatter) {
        GWTPojoConfigurations.dateFormatter = dateFormatter;
    }

    private GWTPojoConfigurations() {
        // Prevent instantiation
    }
}

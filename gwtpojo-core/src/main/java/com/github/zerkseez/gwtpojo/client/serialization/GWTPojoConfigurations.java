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

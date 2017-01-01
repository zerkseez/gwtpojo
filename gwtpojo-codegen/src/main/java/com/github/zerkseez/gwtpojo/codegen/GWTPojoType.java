package com.github.zerkseez.gwtpojo.codegen;

import com.github.zerkseez.gwtpojo.codegen.jackson.JacksonPojoParser;

public enum GWTPojoType {
    JACKSON(new JacksonPojoParser());

    private final GWTPojoParser parser;

    GWTPojoType(final GWTPojoParser parser) {
        this.parser = parser;
    }

    public GWTPojoParser getParser() {
        return parser;
    }
}

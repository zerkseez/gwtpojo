package com.github.zerkseez.gwtpojo.codegen;

public class GWTPojoException extends RuntimeException {
    private static final long serialVersionUID = 2114512177113568424L;

    public GWTPojoException(final String message) {
        super(message);
    }

    public GWTPojoException(final String format, final Object... args) {
        super(String.format(format, args));
    }

    public GWTPojoException(final Throwable e) {
        super(e);
    }
}

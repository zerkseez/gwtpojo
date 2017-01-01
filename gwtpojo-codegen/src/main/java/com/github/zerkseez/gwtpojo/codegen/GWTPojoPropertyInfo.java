package com.github.zerkseez.gwtpojo.codegen;

import com.github.zerkseez.reflection.FieldInfo;
import com.github.zerkseez.reflection.MethodInfo;

public class GWTPojoPropertyInfo {
    private String name = null;
    private FieldInfo field = null;
    private MethodInfo getter = null;
    private MethodInfo setter = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldInfo getField() {
        return field;
    }

    public void setField(FieldInfo field) {
        this.field = field;
    }

    public MethodInfo getGetter() {
        return getter;
    }

    public void setGetter(MethodInfo getter) {
        this.getter = getter;
    }

    public MethodInfo getSetter() {
        return setter;
    }

    public void setSetter(MethodInfo setter) {
        this.setter = setter;
    }
}

package com.github.zerkseez.gwtpojo.codegen;

import java.util.List;
import java.util.Map;

import com.github.zerkseez.reflection.TypeInfo;
import com.google.common.base.Strings;

public class GWTPojoClassInfo {
    private GWTPojoParser parser = null;
    private TypeInfo<?> pojoType = null;
    private String packageName = null;
    private String simplePojoClassName = null;
    private String simpleSerializerClassName = null;
    private List<GWTPojoPropertyInfo> serializationProperties = null;
    private List<GWTPojoPropertyInfo> deserializationProperties = null;
    private String subTypePropertyName = null;
    private Map<String, TypeInfo<?>> subTypes = null;

    public GWTPojoParser getParser() {
        return parser;
    }

    public void setParser(GWTPojoParser parser) {
        this.parser = parser;
    }

    public TypeInfo<?> getPojoType() {
        return pojoType;
    }

    public void setPojoType(TypeInfo<?> pojoType) {
        this.pojoType = pojoType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSimplePojoClassName() {
        return simplePojoClassName;
    }

    public void setSimplePojoClassName(String simplePojoClassName) {
        this.simplePojoClassName = simplePojoClassName;
    }

    public String getSimpleSerializerClassName() {
        return simpleSerializerClassName;
    }

    public void setSimpleSerializerClassName(String simpleSerializerClassName) {
        this.simpleSerializerClassName = simpleSerializerClassName;
    }

    public String getSerializerFullClassName() {
        if (Strings.isNullOrEmpty(getPackageName())) {
            return getSimpleSerializerClassName();
        }
        return String.format("%s.%s", getPackageName(), getSimpleSerializerClassName());
    }

    public List<GWTPojoPropertyInfo> getSerializationProperties() {
        return serializationProperties;
    }

    public void setSerializationProperties(List<GWTPojoPropertyInfo> serializationProperties) {
        this.serializationProperties = serializationProperties;
    }

    public List<GWTPojoPropertyInfo> getDeserializationProperties() {
        return deserializationProperties;
    }

    public void setDeserializationProperties(List<GWTPojoPropertyInfo> deserializationProperties) {
        this.deserializationProperties = deserializationProperties;
    }

    public String getSubTypePropertyName() {
        return subTypePropertyName;
    }

    public void setSubTypePropertyName(String subTypePropertyName) {
        this.subTypePropertyName = subTypePropertyName;
    }

    public Map<String, TypeInfo<?>> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(Map<String, TypeInfo<?>> subTypes) {
        this.subTypes = subTypes;
    }
}

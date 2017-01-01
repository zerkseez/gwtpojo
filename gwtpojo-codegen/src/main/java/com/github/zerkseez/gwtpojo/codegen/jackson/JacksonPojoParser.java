package com.github.zerkseez.gwtpojo.codegen.jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.zerkseez.gwtpojo.codegen.AbstractGWTPojoParser;
import com.github.zerkseez.gwtpojo.codegen.GWTPojoClassInfo;
import com.github.zerkseez.gwtpojo.codegen.GWTPojoPropertyInfo;
import com.github.zerkseez.reflection.MethodInfo;
import com.github.zerkseez.reflection.Reflection;
import com.github.zerkseez.reflection.TypeInfo;
import com.google.common.base.Strings;

public class JacksonPojoParser extends AbstractGWTPojoParser {
    @Override
    protected GWTPojoClassInfo doParse(final GWTPojoClassInfo pojoInfo) {        
        if (pojoInfo.getPojoType().isAnnotationPresent(JsonTypeInfo.class)) {
            final JsonTypeInfo jsonTypeInfo = pojoInfo.getPojoType().getAnnotation(JsonTypeInfo.class);
            if (
                    pojoInfo.getPojoType().isAnnotationPresent(JsonSubTypes.class)
                    && JsonTypeInfo.Id.NAME.equals(jsonTypeInfo.use())
                    && JsonTypeInfo.As.PROPERTY.equals(jsonTypeInfo.include())
                    && !Strings.isNullOrEmpty(jsonTypeInfo.property())
            ) {
                final Map<String, TypeInfo<?>> subTypes = new HashMap<String, TypeInfo<?>>();
                for (Type jacksonSubType : pojoInfo.getPojoType().getAnnotation(JsonSubTypes.class).value()) {
                    if (!Strings.isNullOrEmpty(jacksonSubType.name()) && jacksonSubType.value() != null) {
                        subTypes.put(jacksonSubType.name(), Reflection.getTypeInfo(jacksonSubType.value()));
                    }
                }
                if (!subTypes.isEmpty()) {
                    pojoInfo.setSubTypePropertyName(jsonTypeInfo.property());
                    pojoInfo.setSubTypes(subTypes);
                }
            }
        }
        else {
            final ObjectMapper jacksonObjectMapper = new ObjectMapper();
            final TypeFactory jacksonTypeFactory = jacksonObjectMapper.getTypeFactory();
            final SerializationConfig serializationConfig = jacksonObjectMapper.getSerializationConfig();
            final DeserializationConfig deserializationConfig = jacksonObjectMapper.getDeserializationConfig();
            final JavaType jacksonJavaType = jacksonTypeFactory.constructType(pojoInfo.getPojoType().getType());

            final List<GWTPojoPropertyInfo> serializationProperties = new ArrayList<GWTPojoPropertyInfo>();
            for (BeanPropertyDefinition property : serializationConfig.introspect(jacksonJavaType).findProperties()) {
                serializationProperties.add(translateJacksonBeanPropertyDefinition(pojoInfo, property));
            }
            pojoInfo.setSerializationProperties(serializationProperties);

            final List<GWTPojoPropertyInfo> deserializationProperties = new ArrayList<GWTPojoPropertyInfo>();
            for (BeanPropertyDefinition property : deserializationConfig.introspect(jacksonJavaType).findProperties()) {
                deserializationProperties.add(translateJacksonBeanPropertyDefinition(pojoInfo, property));
            }
            pojoInfo.setDeserializationProperties(deserializationProperties);
        }
        return pojoInfo;
    }
    
    protected GWTPojoPropertyInfo translateJacksonBeanPropertyDefinition(
            final GWTPojoClassInfo pojoInfo,
            final BeanPropertyDefinition beanDef
    ) {
        final GWTPojoPropertyInfo propertyInfo = new GWTPojoPropertyInfo();
        propertyInfo.setName(beanDef.getName());
        if (beanDef.hasGetter() && beanDef.getGetter().isPublic()) {
            propertyInfo.setGetter(translateJacksonAnnotatedMethod(pojoInfo, beanDef.getGetter()));
        }
        if (beanDef.hasSetter() && beanDef.getSetter().isPublic()) {
            propertyInfo.setSetter(translateJacksonAnnotatedMethod(pojoInfo, beanDef.getSetter()));
        }
        if (beanDef.hasField() && beanDef.getField().isPublic()) {
            propertyInfo.setField(pojoInfo.getPojoType().getPublicField(beanDef.getField().getName()));
        }
        return propertyInfo;
    }
    
    protected MethodInfo translateJacksonAnnotatedMethod(
            final GWTPojoClassInfo pojoInfo,
            final AnnotatedMethod method
    ) {
        final List<MethodInfo> methodList = pojoInfo.getPojoType().getPublicMethods();
        for (int i = 0; i < methodList.size(); i++) {
            if (method.getAnnotated().equals(methodList.get(i).getExecutable())) {
                return methodList.get(i);
            }
        }
        return null;
    }
}

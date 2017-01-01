package com.github.zerkseez.gwtpojo.codegen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.zerkseez.gwtpojo.BuiltinSerializers;
import com.github.zerkseez.gwtpojo.client.serialization.ArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.EnumSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializerException;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializerUtils;
import com.github.zerkseez.gwtpojo.codegen.codewriter.CodeWriter;
import com.github.zerkseez.gwtpojo.codegen.codewriter.JavaFileWriter;
import com.github.zerkseez.reflection.FieldInfo;
import com.github.zerkseez.reflection.MethodInfo;
import com.github.zerkseez.reflection.Reflection;
import com.github.zerkseez.reflection.TypeInfo;
import com.github.zerkseez.reflection.TypeVariableInfo;
import com.google.common.base.Joiner;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class GWTPojoSerializerGenerator {
    public String generate(final GWTPojoClassInfo pojoInfo) {
        final GWTPojoSerializerGeneratorContextImpl context = new GWTPojoSerializerGeneratorContextImpl(pojoInfo);
        final JavaFileWriter jw = context.getWriter();
        jw.setGeneratorClass(getClass());
        
        // Class declaration
        jw.append("public class ").append(pojoInfo.getSimpleSerializerClassName());

        String typeParamDec = "";
        String typeParamRef = "";
        if (pojoInfo.getPojoType().hasTypeVariables()) {
            typeParamDec = jw.getStringOfTypeVariables(pojoInfo.getPojoType().getTypeVariables());
            typeParamRef = jw.getStringOfTypeVariables(pojoInfo.getPojoType().getTypeVariables());
            jw.append(typeParamDec);
        }

        jw.append(" implements ").append(GWTPojoSerializer.class, false).append("<")
                .append(pojoInfo.getPojoType(), false).append(typeParamRef).appendLine("> {");

        // Placeholder for fields and constructor generation
        final CodeWriter<?> fieldsAndConstructorWriter = jw.newCodeBlock();
        
        // Serializer
        generateSerializer(jw, context);
        jw.appendLine();
        
        // Deserializer
        generateDeserializer(jw, context);

        for (CodeWriter<?> method : context.getMethods()) {
            jw.appendLine().append(method.toString());
        }
        
        // Fields and constructor
        generateFieldsAndConstructor(fieldsAndConstructorWriter, context);

        return jw.appendLine("}").toString();
    }

    public String getSerializer(final TypeInfo<?> type, final GWTPojoSerializerGeneratorContext context) {
        final CodeWriter<?> jw = context.getWriter();
        final StringBuilder sb = new StringBuilder();
        final Class<?> erasedClass = type.getErasedClass();

        if (type.isEnum()) {
            sb.append("new ").append(jw.getStringOfType(EnumSerializer.class, false)).append("<")
                    .append(jw.getStringOfType(type)).append(">(").append(jw.getStringOfType(type)).append(".class)");
        }
        else if (type.isArray()) {
            if (BuiltinSerializers.BASIC_SERIALIZERS.containsKey(erasedClass)) {
                sb.append(jw.getStringOfType(BuiltinSerializers.BASIC_SERIALIZERS.get(erasedClass)))
                        .append(".INSTANCE");
            }
            else {
                sb.append("new ").append(jw.getStringOfType(ArraySerializer.class, false)).append("<")
                        .append(jw.getStringOfType(type.getArrayElementType())).append(">(")
                        .append(getSerializer(type.getArrayElementType(), context)).append(")");
            }
        }
        else if (type.isTypeVariable()) {
            sb.append(getTypeVariableSerializerFieldName(type.getTypeVariableName(), context));
        }
        else if (BuiltinSerializers.BASIC_SERIALIZERS.containsKey(erasedClass)) {
            sb.append(jw.getStringOfType(BuiltinSerializers.BASIC_SERIALIZERS.get(erasedClass))).append(".INSTANCE");
        }
        else if (type.isWildcardType()) {
            sb.append(getSerializer(resolveWildcardType(type), context));
        }
        else if (type.hasTypeVariables()) {
            final List<TypeVariableInfo> typeVariables = type.getTypeVariables();
            final String serializerTypeName = BuiltinSerializers.COLLECTION_SERIALIZERS.containsKey(erasedClass)
                    ? jw.getStringOfType(
                            Reflection.getTypeInfo(BuiltinSerializers.COLLECTION_SERIALIZERS.get(erasedClass)), false
                    )
                    : GWTPojoParserChain.INSTANCE.parse(erasedClass).getSerializerFullClassName();

            sb.append("new ").append(jw.tryImportType(serializerTypeName)).append("<");
            for (int i = 0; i < typeVariables.size(); i++) {
                if (i != 0) {
                    sb.append(", ");
                }

                TypeInfo<?> typeVariableValue = typeVariables.get(i);
                while (typeVariableValue.getTypeVariableValue() != null) {
                    typeVariableValue = typeVariableValue.getTypeVariableValue();
                }
                
                if (typeVariableValue.isWildcardType()) {
                    sb.append(jw.getStringOfType(resolveWildcardType(typeVariableValue)));
                }
                else {
                    sb.append(jw.getStringOfType(typeVariableValue));
                }
            }
            sb.append(">(");
            
            for (int i = 0; i < typeVariables.size(); i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                
                TypeInfo<?> typeVariableValue = typeVariables.get(i);
                while (typeVariableValue.getTypeVariableValue() != null) {
                    typeVariableValue = typeVariableValue.getTypeVariableValue();
                }
                
                sb.append(getSerializer(typeVariableValue, context));
            }

            sb.append(")");
        }
        else {
            final GWTPojoClassInfo pojoInfo = GWTPojoParserChain.INSTANCE.parse(erasedClass);
            final String serializerTypeName = String.format(
                    "%s.%s", pojoInfo.getPackageName(), pojoInfo.getSimpleSerializerClassName()
            );
            sb.append("new ").append(jw.tryImportType(serializerTypeName)).append("()");
        }
        return sb.toString();
    }
    
    protected void generateFieldsAndConstructor(
            final CodeWriter<?> jw,
            final GWTPojoSerializerGeneratorContextImpl context
    ) {
        if (!context.getPojoInfo().getPojoType().hasTypeVariables()) {
            return;
        }

        final Set<String> referencedTypeVariableNames = new HashSet<String>(context.getReferencedTypeVariableNames());
        final List<String> typeVariableNames = context.getPojoInfo().getPojoType().getTypeVariables().stream()
                .map(i -> i.getTypeVariableName())
                .collect(Collectors.toList());

        // Fields
        if (!referencedTypeVariableNames.isEmpty()) {
            for (int i = 0; i < typeVariableNames.size(); i++) {
                final String typeVariableName = typeVariableNames.get(i);
                if (referencedTypeVariableNames.contains(typeVariableName)) {
                    final String fieldName = getTypeVariableSerializerFieldName(typeVariableName, context);
                    jw.indent(1).append("private final ").append(GWTPojoSerializer.class, false).append("<")
                            .append(typeVariableName).append("> ").append(fieldName).appendLine(";");
                }
            }
            jw.appendLine();
        }
        
        // Constructor
        jw.indent().append("public ").append(context.getPojoInfo().getSimpleSerializerClassName())
                .appendLine("(");
        for (int i = 0; i < typeVariableNames.size(); i++) {
            final String typeVariableName = typeVariableNames.get(i);
            final String fieldName = getTypeVariableSerializerFieldName(typeVariableName, context);
            jw.indent(2).append("final ").append(GWTPojoSerializer.class, false).append("<").append(typeVariableName)
                    .append("> ").append(fieldName);
            if (i != typeVariableNames.size() - 1) {
                jw.append(",");
            }
            jw.appendLine();
        }
        jw.indent().appendLine(") {");
        for (int i = 0; i < typeVariableNames.size(); i++) {
            final String typeVariableName = typeVariableNames.get(i);
            if (referencedTypeVariableNames.contains(typeVariableName)) {
                final String fieldName = getTypeVariableSerializerFieldName(typeVariableName, context);
                jw.indent(2).append("this.").append(fieldName).append(" = ").append(fieldName).appendLine(";");
            }
        }
        jw.indent().appendLine("}").appendLine();
    }
    
    protected void generateSerializer(
            final CodeWriter<?> jw,
            final GWTPojoSerializerGeneratorContextImpl context
    ) {
        final GWTPojoClassInfo pojoInfo = context.getPojoInfo();
        
        jw.indent().append("@").append(Override.class).appendLine();
        jw.indent().append("public ").append(JSONValue.class).append(" serialize(final ")
                .append(pojoInfo.getPojoType()).appendLine(" value) {");
        jw.indent(2).appendLine("if (value == null) {");
        jw.indent(3).append("return ").append(JSONNull.class).appendLine(".getInstance();");
        jw.indent(2).appendLine("}");
        jw.appendLine();
        
        if (pojoInfo.getSubTypePropertyName() == null) {
            jw.indent(2).append("final ").append(JSONObject.class).append(" jsonObject = new ").append(JSONObject.class)
                    .appendLine("();");
            for (GWTPojoPropertyInfo property : pojoInfo.getSerializationProperties()) {
                final String propertyName = property.getName();
                final String serializeMethod = getSerializeMethodNameForProperty(propertyName);
    
                if (property.getGetter() != null) {
                    final MethodInfo getter = property.getGetter();
                    jw.indent(2).append(GWTPojoSerializerUtils.class).append(".setProperty(jsonObject, ")
                            .appendStringLiteral(propertyName).append(", ").append(serializeMethod).append("(value.")
                            .append(getter.getName()).appendLine("()));");
                    generateSerializeMethodForProperty(serializeMethod, getter.getReturnType(), context);
                }
                else if (property.getField() != null) {
                    final FieldInfo field = property.getField();
                    jw.indent(2).append(GWTPojoSerializerUtils.class).append(".setProperty(jsonObject, ")
                            .appendStringLiteral(propertyName).append(", ").append(serializeMethod).append("(value.")
                            .append(field.getName()).appendLine("));");
                    generateDeserializeMethodForProperty(serializeMethod, field.getType(), context);
                }
            }
            jw.indent(2).appendLine("return jsonObject;");
        }
        else {
            jw.indent(2).append(String.class).appendLine(" type = null;");
            jw.indent(2).append(JSONObject.class).appendLine(" jsonObject = null;");
            final List<String> subTypeNames = pojoInfo.getSubTypes().keySet().stream().sorted()
                    .collect(Collectors.toList());
            for (int i = 0; i < subTypeNames.size(); i++) {
                final String subTypeName = subTypeNames.get(i);
                final TypeInfo<?> subType = pojoInfo.getSubTypes().get(subTypeName);
                jw.indent(2);
                if (i != 0) {
                    jw.append("else ");
                }
                jw.append("if (value instanceof ").append(subType, false).appendLine(") {");
                jw.indent(3).append("type = ").appendStringLiteral(subTypeName).appendLine(";");
                jw.indent(3).append("jsonObject = (").append(JSONObject.class).append(")")
                        .append(getSerializer(subType, context)).append(".serialize((").append(subType)
                        .appendLine(")value);");
                jw.indent(2).appendLine("}");
            }
            jw.indent(2).append("jsonObject.put(").appendStringLiteral(pojoInfo.getSubTypePropertyName())
                    .append(", new ").append(JSONString.class).appendLine("(type));");
            jw.indent(2).appendLine("return jsonObject;");
        }
        
        jw.indent().appendLine("}");
    }
    
    protected void generateDeserializer(
            final CodeWriter<?> jw,
            final GWTPojoSerializerGeneratorContextImpl context
    ) {
        final GWTPojoClassInfo pojoInfo = context.getPojoInfo();

        jw.indent().append("@").append(Override.class).appendLine();
        jw.indent().append("public ").append(pojoInfo.getPojoType()).append(" deserialize(final ")
                .append(JSONValue.class).appendLine(" jsonValue) {");
        jw.indent(2).appendLine("if (jsonValue == null || jsonValue.isNull() != null) {");
        jw.indent(3).appendLine("return null;");
        jw.indent(2).appendLine("}");
        jw.appendLine();
        jw.indent(2).append("final ").append(JSONObject.class).appendLine(" jsonObject = jsonValue.isObject();");
        jw.indent(2).appendLine("if (jsonObject == null) {");
        jw.indent(3).append("throw new ").append(GWTPojoSerializerException.class)
                .append("(\"Unable to deserialize \" + jsonValue.toString() + \" as ")
                .append(pojoInfo.getSimplePojoClassName()).appendLine("\");");
        jw.indent(2).appendLine("}");
        jw.appendLine();

        if (pojoInfo.getSubTypePropertyName() == null) {
            jw.indent(2).append("final ").append(pojoInfo.getPojoType()).append(" pojo = new ")
                    .append(pojoInfo.getPojoType()).appendLine("();");
            for (GWTPojoPropertyInfo property : pojoInfo.getDeserializationProperties()) {
                final String propertyName = property.getName();
                final String deserializeMethod = getDeserializeMethodNameForProperty(propertyName);
    
                if (property.getSetter() != null) {
                    final MethodInfo setter = property.getSetter();
                    final TypeInfo<?> parameterType = setter.getParameters().get(0).getType();
                    jw.indent(2).append("pojo.").append(setter.getName()).append("(");
                    jw.append(deserializeMethod).append("(jsonObject.get(").appendStringLiteral(propertyName)
                            .append("))");
                    jw.appendLine(");");
                    generateDeserializeMethodForProperty(deserializeMethod, parameterType, context);
                }
                else if (property.getField() != null) {
                    final FieldInfo field = property.getField();
                    jw.indent(2).append("pojo.").append(field.getName()).append(" = ");
                    jw.append(deserializeMethod).append("(jsonObject.get(").appendStringLiteral(propertyName)
                            .append("))");
                    jw.appendLine(";");
                    generateDeserializeMethodForProperty(deserializeMethod, field.getType(), context);
                }
            }
            jw.indent(2).appendLine("return pojo;");
        }
        else {
            jw.indent(2).append("final ").append(JSONValue.class).append(" subTypeJsonValue = jsonObject.get(")
                    .appendStringLiteral(pojoInfo.getSubTypePropertyName()).appendLine(");");
            jw.indent(2).appendLine("if (subTypeJsonValue == null) {");
            jw.indent(3).appendLine("return null;");
            jw.indent(2).appendLine("}");
            jw.appendLine();
            jw.indent(2).append("final ").append(String.class).append(" subType = ")
                    .append(getSerializer(Reflection.getTypeInfo(String.class), context))
                    .appendLine(".deserialize(subTypeJsonValue);");
            jw.indent(2).appendLine("switch (subType) {");
            for (String subType : pojoInfo.getSubTypes().keySet().stream().sorted().collect(Collectors.toList())) {
                jw.indent(2).append("case ").appendStringLiteral(subType).appendLine(":");
                jw.indent(3).append("return ").append(getSerializer(pojoInfo.getSubTypes().get(subType), context))
                        .appendLine(".deserialize(jsonValue);");
            }
            jw.indent(2).appendLine("default:");
            jw.indent(3).appendLine("return null;"); // Throw exception here?
            jw.indent(2).appendLine("}");
        }

        jw.indent().appendLine("}");
    }
    
    protected CodeWriter<?> generateSerializeMethodForProperty(
            final String methodName,
            final TypeInfo<?> propertyType,
            final GWTPojoSerializerGeneratorContextImpl context
    ) {
        final CodeWriter<?> jw = context.getWriter().newCodeBlock(false);
        context.getMethods().add(jw);

        jw.indent().append("public ").append(JSONValue.class).space().append(methodName).append("(final ")
                .append(propertyType).appendLine(" value) {");

        jw.indent(2).append("return ").append(getSerializer(propertyType, context))
                .appendLine(".serialize(value);");
        
        return jw.indent().appendLine("}");
    }
    
    protected CodeWriter<?> generateDeserializeMethodForProperty(
            final String methodName,
            final TypeInfo<?> propertyType,
            final GWTPojoSerializerGeneratorContextImpl context
    ) {
        final CodeWriter<?> jw = context.getWriter().newCodeBlock(false);
        context.getMethods().add(jw);

        jw.indent().append("public ").append(propertyType).space().append(methodName).append("(final ")
                .append(JSONValue.class).appendLine(" jsonValue) {");

        jw.indent(2).append("return ").append(getSerializer(propertyType, context))
                .appendLine(".deserialize(jsonValue);");
        
        return jw.indent().appendLine("}");
    }
    
    protected String getTypeVariableSerializerFieldName(
            final String typeVariableName,
            final GWTPojoSerializerGeneratorContext context
    ) {
        context.getReferencedTypeVariableNames().add(typeVariableName);
        return String.format("%sSerializer", typeVariableName.toLowerCase());
    }
    
    protected String getSerializeMethodNameForProperty(final String propertyName) {
        return String.format("serialize%s%s", propertyName.substring(0, 1).toUpperCase(), propertyName.substring(1));
    }
    
    protected String getDeserializeMethodNameForProperty(final String propertyName) {
        return String.format("deserialize%s%s", propertyName.substring(0, 1).toUpperCase(), propertyName.substring(1));
    }

    protected TypeInfo<?> resolveWildcardType(final TypeInfo<?> type) {
        final List<TypeInfo<?>> extendsBound = type.getExtendsBounds();
        if (extendsBound.isEmpty()) {
            return Reflection.getTypeInfo(Object.class);
        }
        if (extendsBound.size() == 1) {
            return extendsBound.get(0);
        }
        throw new GWTPojoException(String.format(
                "Unable to resolve <? extends %s>", Joiner.on(" & ").join(extendsBound)
        ));
    }
}

package com.github.zerkseez.gwtpojo.codegen;

import java.lang.reflect.Modifier;

import com.github.zerkseez.reflection.Reflection;
import com.github.zerkseez.reflection.TypeInfo;

public abstract class AbstractGWTPojoParser implements GWTPojoParser {
    @Override
    public GWTPojoClassInfo parse(final Class<?> pojoClass) {
        final TypeInfo<?> typeInfo = Reflection.getTypeInfo(pojoClass);
        if (
                typeInfo.isAnonymousClass()
                || typeInfo.isLocalClass()
                || (typeInfo.isMemberClass() && !Modifier.isStatic(typeInfo.getModifiers()))
        ) {
            throw new GWTPojoException("Anonymous, local and non-static member classes are not supported");
        }

        final String fullName = pojoClass.getName();
        final int indexOfLastDot = fullName.lastIndexOf('.');
        final String className = (indexOfLastDot == -1) ? fullName : fullName.substring(indexOfLastDot + 1);
        final GWTPojoClassInfo pojoInfo = new GWTPojoClassInfo();
        pojoInfo.setParser(this);
        pojoInfo.setPojoType(typeInfo);
        pojoInfo.setPackageName((indexOfLastDot == -1) ? "" : fullName.substring(0, indexOfLastDot));
        pojoInfo.setSimplePojoClassName(pojoClass.getSimpleName());
        pojoInfo.setSimpleSerializerClassName(String.format("%sSerializer", className.replaceAll("\\$", "_")));
        return doParse(pojoInfo);
    }

    protected abstract GWTPojoClassInfo doParse(final GWTPojoClassInfo pojoInfo);
}

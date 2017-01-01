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

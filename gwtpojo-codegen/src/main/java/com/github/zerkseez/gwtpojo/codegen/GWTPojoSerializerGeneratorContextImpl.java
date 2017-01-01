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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.zerkseez.gwtpojo.codegen.codewriter.CodeWriter;
import com.github.zerkseez.gwtpojo.codegen.codewriter.JavaFileWriter;

public class GWTPojoSerializerGeneratorContextImpl implements GWTPojoSerializerGeneratorContext {
    private final GWTPojoClassInfo pojoInfo;
    private final JavaFileWriter writer;
    private final Set<String> referencedTypeParameterNames;
    private final List<CodeWriter<?>> methods;

    public GWTPojoSerializerGeneratorContextImpl(final GWTPojoClassInfo pojoInfo) {
        this.pojoInfo = pojoInfo;
        this.writer = new JavaFileWriter(pojoInfo.getPackageName());
        this.referencedTypeParameterNames = new HashSet<String>();
        this.methods = new ArrayList<CodeWriter<?>>();
    }

    public GWTPojoClassInfo getPojoInfo() {
        return pojoInfo;
    }

    @Override
    public JavaFileWriter getWriter() {
        return writer;
    }

    @Override
    public Set<String> getReferencedTypeVariableNames() {
        return referencedTypeParameterNames;
    }

    public List<CodeWriter<?>> getMethods() {
        return methods;
    }
}

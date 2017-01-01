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

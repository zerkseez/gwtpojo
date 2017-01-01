package com.github.zerkseez.gwtpojo.codegen;

import java.util.Set;

import com.github.zerkseez.gwtpojo.codegen.codewriter.CodeWriter;

public interface GWTPojoSerializerGeneratorContext {
    CodeWriter<?> getWriter();
    Set<String> getReferencedTypeVariableNames();
}

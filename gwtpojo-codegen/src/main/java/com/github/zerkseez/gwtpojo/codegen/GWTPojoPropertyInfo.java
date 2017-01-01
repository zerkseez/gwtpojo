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

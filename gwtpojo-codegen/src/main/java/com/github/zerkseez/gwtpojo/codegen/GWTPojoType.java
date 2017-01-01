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

import com.github.zerkseez.gwtpojo.codegen.jackson.JacksonPojoParser;

public enum GWTPojoType {
    JACKSON(new JacksonPojoParser());

    private final GWTPojoParser parser;

    GWTPojoType(final GWTPojoParser parser) {
        this.parser = parser;
    }

    public GWTPojoParser getParser() {
        return parser;
    }
}

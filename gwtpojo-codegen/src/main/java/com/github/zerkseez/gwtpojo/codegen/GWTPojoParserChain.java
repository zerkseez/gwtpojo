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

import java.util.HashMap;
import java.util.Map;

public class GWTPojoParserChain implements GWTPojoParser {
    public static final GWTPojoParserChain INSTANCE = new GWTPojoParserChain();

    private static final Map<Class<?>, CacheEntry> CACHE = new HashMap<Class<?>, CacheEntry>();

    private GWTPojoType[] pojoTypes;

    private GWTPojoParserChain() {
        this.pojoTypes = new GWTPojoType[] { GWTPojoType.JACKSON };
    }

    public GWTPojoType[] getPojoTypes() {
        return pojoTypes;
    }

    public void setPojoTypes(final GWTPojoType[] pojoTypes) {
        this.pojoTypes = pojoTypes;
    }

    @Override
    public GWTPojoClassInfo parse(final Class<?> pojoClass) {
        CacheEntry cacheEntry = CACHE.get(pojoClass);
        if (cacheEntry != null) {
            return cacheEntry.getPojoInfo();
        }

        final GWTPojoClassInfo pojoInfo = doParse(pojoClass);
        CACHE.put(pojoClass, new CacheEntry(pojoInfo));
        return pojoInfo;
    }

    protected GWTPojoClassInfo doParse(final Class<?> pojoClass) {
        for (GWTPojoType type : getPojoTypes()) {
            final GWTPojoClassInfo pojoInfo = type.getParser().parse(pojoClass);
            if (pojoInfo != null) {
                return pojoInfo;
            }
        }
        return null;
    }

    private static class CacheEntry {
        private final GWTPojoClassInfo pojoInfo;

        public CacheEntry(final GWTPojoClassInfo pojoInfo) {
            this.pojoInfo = pojoInfo;
        }

        public GWTPojoClassInfo getPojoInfo() {
            return pojoInfo;
        }
    }
}

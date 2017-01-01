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

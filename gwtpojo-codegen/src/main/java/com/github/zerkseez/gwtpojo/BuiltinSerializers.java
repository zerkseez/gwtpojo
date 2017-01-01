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
package com.github.zerkseez.gwtpojo;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.zerkseez.gwtpojo.client.serialization.BooleanArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.BooleanSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.ByteArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.ByteSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.CharacterArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.CharacterSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.DateSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.DoubleArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.DoubleSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.FloatArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.FloatSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.GWTPojoSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.IntegerArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.IntegerSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.LongArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.LongSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.ObjectSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.ShortArraySerializer;
import com.github.zerkseez.gwtpojo.client.serialization.ShortSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.StringSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.VoidSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.collection.ListSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.collection.MapSerializer;
import com.github.zerkseez.gwtpojo.client.serialization.collection.SetSerializer;
import com.google.common.collect.ImmutableMap;

public final class BuiltinSerializers {
    public static final Map<Class<?>, Class<? extends GWTPojoSerializer<?>>> BASIC_SERIALIZERS =
            ImmutableMap.<Class<?>, Class<? extends GWTPojoSerializer<?>>>builder()
                    .put(Object.class, ObjectSerializer.class)
                    .put(Void.class, VoidSerializer.class)
                    .put(void.class, VoidSerializer.class)
                    .put(Byte.class, ByteSerializer.class)
                    .put(byte.class, ByteSerializer.class)
                    .put(byte[].class, ByteArraySerializer.class)
                    .put(Short.class, ShortSerializer.class)
                    .put(short.class, ShortSerializer.class)
                    .put(short[].class, ShortArraySerializer.class)
                    .put(Integer.class, IntegerSerializer.class)
                    .put(int.class, IntegerSerializer.class)
                    .put(int[].class, IntegerArraySerializer.class)
                    .put(Long.class, LongSerializer.class)
                    .put(long.class, LongSerializer.class)
                    .put(long[].class, LongArraySerializer.class)
                    .put(Float.class, FloatSerializer.class)
                    .put(float.class, FloatSerializer.class)
                    .put(float[].class, FloatArraySerializer.class)
                    .put(Double.class, DoubleSerializer.class)
                    .put(double.class, DoubleSerializer.class)
                    .put(double[].class, DoubleArraySerializer.class)
                    .put(Boolean.class, BooleanSerializer.class)
                    .put(boolean.class, BooleanSerializer.class)
                    .put(boolean[].class, BooleanArraySerializer.class)
                    .put(Character.class, CharacterSerializer.class)
                    .put(char.class, CharacterSerializer.class)
                    .put(char[].class, CharacterArraySerializer.class)
                    .put(String.class, StringSerializer.class)
                    .put(Date.class, DateSerializer.class)
                    .build();

    public static final Map<Class<?>, String> COLLECTION_SERIALIZERS =
            ImmutableMap.<Class<?>, String>builder()
                    .put(Collection.class, ListSerializer.class.getName())
                    .put(List.class, ListSerializer.class.getName())
                    .put(Map.class, MapSerializer.class.getName())
                    .put(Set.class, SetSerializer.class.getName())
                    .build();

    private BuiltinSerializers() {
        // Prevent instantiation
    }
}

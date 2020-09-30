/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

abstract class TypeCapture<T> {
    TypeCapture() {
    }

    final Type capture() {
        Type type = this.getClass().getGenericSuperclass();
        Preconditions.checkArgument(type instanceof ParameterizedType, "%s isn't parameterized", (Object)type);
        return ((ParameterizedType)type).getActualTypeArguments()[0];
    }
}


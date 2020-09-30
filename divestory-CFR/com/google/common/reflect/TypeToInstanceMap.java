/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.reflect.TypeToken;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableTypeToInstanceMap or MutableTypeToInstanceMap")
public interface TypeToInstanceMap<B>
extends Map<TypeToken<? extends B>, B> {
    @NullableDecl
    public <T extends B> T getInstance(TypeToken<T> var1);

    @NullableDecl
    public <T extends B> T getInstance(Class<T> var1);

    @NullableDecl
    public <T extends B> T putInstance(TypeToken<T> var1, @NullableDecl T var2);

    @NullableDecl
    public <T extends B> T putInstance(Class<T> var1, @NullableDecl T var2);
}


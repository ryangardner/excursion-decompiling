/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableClassToInstanceMap or MutableClassToInstanceMap")
public interface ClassToInstanceMap<B>
extends Map<Class<? extends B>, B> {
    public <T extends B> T getInstance(Class<T> var1);

    public <T extends B> T putInstance(Class<T> var1, @NullableDecl T var2);
}


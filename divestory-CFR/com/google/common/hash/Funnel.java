/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.hash.PrimitiveSink;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;

@DoNotMock(value="Implement with a lambda")
public interface Funnel<T>
extends Serializable {
    public void funnel(T var1, PrimitiveSink var2);
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.errorprone.annotations.DoNotMock;

@DoNotMock(value="Use an instance of one of the Finalizable*Reference classes")
public interface FinalizableReference {
    public void finalizeReferent();
}


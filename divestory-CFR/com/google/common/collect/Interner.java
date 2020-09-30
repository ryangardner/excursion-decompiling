/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;

@DoNotMock(value="Use Interners.new*Interner")
public interface Interner<E> {
    public E intern(E var1);
}


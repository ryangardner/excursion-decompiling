/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

public interface Weigher<K, V> {
    public int weigh(K var1, V var2);
}


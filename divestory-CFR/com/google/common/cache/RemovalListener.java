/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.cache.RemovalNotification;

public interface RemovalListener<K, V> {
    public void onRemoval(RemovalNotification<K, V> var1);
}


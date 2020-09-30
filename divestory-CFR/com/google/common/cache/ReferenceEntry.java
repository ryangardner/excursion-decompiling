/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.cache.LocalCache;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

interface ReferenceEntry<K, V> {
    public long getAccessTime();

    public int getHash();

    @NullableDecl
    public K getKey();

    @NullableDecl
    public ReferenceEntry<K, V> getNext();

    public ReferenceEntry<K, V> getNextInAccessQueue();

    public ReferenceEntry<K, V> getNextInWriteQueue();

    public ReferenceEntry<K, V> getPreviousInAccessQueue();

    public ReferenceEntry<K, V> getPreviousInWriteQueue();

    public LocalCache.ValueReference<K, V> getValueReference();

    public long getWriteTime();

    public void setAccessTime(long var1);

    public void setNextInAccessQueue(ReferenceEntry<K, V> var1);

    public void setNextInWriteQueue(ReferenceEntry<K, V> var1);

    public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1);

    public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1);

    public void setValueReference(LocalCache.ValueReference<K, V> var1);

    public void setWriteTime(long var1);
}


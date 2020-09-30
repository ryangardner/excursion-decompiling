/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractListMultimap;
import java.util.Collection;
import java.util.Map;

abstract class ArrayListMultimapGwtSerializationDependencies<K, V>
extends AbstractListMultimap<K, V> {
    ArrayListMultimapGwtSerializationDependencies(Map<K, Collection<V>> map) {
        super(map);
    }
}


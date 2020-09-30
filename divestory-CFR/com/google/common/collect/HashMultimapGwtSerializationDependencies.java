/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractSetMultimap;
import java.util.Collection;
import java.util.Map;

abstract class HashMultimapGwtSerializationDependencies<K, V>
extends AbstractSetMultimap<K, V> {
    HashMultimapGwtSerializationDependencies(Map<K, Collection<V>> map) {
        super(map);
    }
}


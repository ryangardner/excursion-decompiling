/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractSetMultimap;
import java.util.Collection;
import java.util.Map;

abstract class LinkedHashMultimapGwtSerializationDependencies<K, V>
extends AbstractSetMultimap<K, V> {
    LinkedHashMultimapGwtSerializationDependencies(Map<K, Collection<V>> map) {
        super(map);
    }
}


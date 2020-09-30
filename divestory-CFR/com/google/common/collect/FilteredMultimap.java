/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import java.util.Map;

interface FilteredMultimap<K, V>
extends Multimap<K, V> {
    public Predicate<? super Map.Entry<K, V>> entryPredicate();

    public Multimap<K, V> unfiltered();
}


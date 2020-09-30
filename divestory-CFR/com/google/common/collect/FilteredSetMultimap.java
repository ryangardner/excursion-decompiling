/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.SetMultimap;

interface FilteredSetMultimap<K, V>
extends FilteredMultimap<K, V>,
SetMultimap<K, V> {
    @Override
    public SetMultimap<K, V> unfiltered();
}


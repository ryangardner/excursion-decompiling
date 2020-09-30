/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ArrayListMultimapGwtSerializationDependencies;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CompactHashMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Platform;
import com.google.common.collect.Serialization;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ArrayListMultimap<K, V>
extends ArrayListMultimapGwtSerializationDependencies<K, V> {
    private static final int DEFAULT_VALUES_PER_KEY = 3;
    private static final long serialVersionUID = 0L;
    transient int expectedValuesPerKey;

    private ArrayListMultimap() {
        this(12, 3);
    }

    private ArrayListMultimap(int n, int n2) {
        super(Platform.newHashMapWithExpectedSize(n));
        CollectPreconditions.checkNonnegative(n2, "expectedValuesPerKey");
        this.expectedValuesPerKey = n2;
    }

    private ArrayListMultimap(Multimap<? extends K, ? extends V> multimap) {
        int n = multimap.keySet().size();
        int n2 = multimap instanceof ArrayListMultimap ? ((ArrayListMultimap)multimap).expectedValuesPerKey : 3;
        this(n, n2);
        this.putAll(multimap);
    }

    public static <K, V> ArrayListMultimap<K, V> create() {
        return new ArrayListMultimap<K, V>();
    }

    public static <K, V> ArrayListMultimap<K, V> create(int n, int n2) {
        return new ArrayListMultimap<K, V>(n, n2);
    }

    public static <K, V> ArrayListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new ArrayListMultimap<K, V>(multimap);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.expectedValuesPerKey = 3;
        int n = Serialization.readCount(objectInputStream);
        this.setMap(CompactHashMap.create());
        Serialization.populateMultimap(this, objectInputStream, n);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @Override
    List<V> createCollection() {
        return new ArrayList(this.expectedValuesPerKey);
    }

    @Deprecated
    public void trimToSize() {
        Iterator iterator2 = this.backingMap().values().iterator();
        while (iterator2.hasNext()) {
            ((ArrayList)iterator2.next()).trimToSize();
        }
    }
}


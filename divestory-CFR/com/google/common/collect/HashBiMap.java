/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.BiMap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class HashBiMap<K, V>
extends AbstractMap<K, V>
implements BiMap<K, V>,
Serializable {
    private static final int ABSENT = -1;
    private static final int ENDPOINT = -2;
    private transient Set<Map.Entry<K, V>> entrySet;
    @NullableDecl
    private transient int firstInInsertionOrder;
    private transient int[] hashTableKToV;
    private transient int[] hashTableVToK;
    @LazyInit
    @MonotonicNonNullDecl
    private transient BiMap<V, K> inverse;
    private transient Set<K> keySet;
    transient K[] keys;
    @NullableDecl
    private transient int lastInInsertionOrder;
    transient int modCount;
    private transient int[] nextInBucketKToV;
    private transient int[] nextInBucketVToK;
    private transient int[] nextInInsertionOrder;
    private transient int[] prevInInsertionOrder;
    transient int size;
    private transient Set<V> valueSet;
    transient V[] values;

    private HashBiMap(int n) {
        this.init(n);
    }

    private int bucket(int n) {
        return n & this.hashTableKToV.length - 1;
    }

    public static <K, V> HashBiMap<K, V> create() {
        return HashBiMap.create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int n) {
        return new HashBiMap<K, V>(n);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> hashBiMap = HashBiMap.create(map.size());
        hashBiMap.putAll(map);
        return hashBiMap;
    }

    private static int[] createFilledWithAbsent(int n) {
        int[] arrn = new int[n];
        Arrays.fill(arrn, -1);
        return arrn;
    }

    private void deleteFromTableKToV(int n, int n2) {
        boolean bl = n != -1;
        Preconditions.checkArgument(bl);
        n2 = this.bucket(n2);
        Object object = this.hashTableKToV;
        if (object[n2] == n) {
            int[] arrn = this.nextInBucketKToV;
            object[n2] = arrn[n];
            arrn[n] = -1;
            return;
        }
        int n3 = object[n2];
        n2 = this.nextInBucketKToV[n3];
        do {
            int n4 = n3;
            n3 = n2;
            if (n3 == -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Expected to find entry with key ");
                ((StringBuilder)object).append(this.keys[n]);
                throw new AssertionError((Object)((StringBuilder)object).toString());
            }
            if (n3 == n) {
                object = this.nextInBucketKToV;
                object[n4] = object[n];
                object[n] = -1;
                return;
            }
            n2 = this.nextInBucketKToV[n3];
        } while (true);
    }

    private void deleteFromTableVToK(int n, int n2) {
        boolean bl = n != -1;
        Preconditions.checkArgument(bl);
        n2 = this.bucket(n2);
        Object object = this.hashTableVToK;
        if (object[n2] == n) {
            int[] arrn = this.nextInBucketVToK;
            object[n2] = arrn[n];
            arrn[n] = -1;
            return;
        }
        int n3 = object[n2];
        n2 = this.nextInBucketVToK[n3];
        do {
            int n4 = n3;
            n3 = n2;
            if (n3 == -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Expected to find entry with value ");
                ((StringBuilder)object).append(this.values[n]);
                throw new AssertionError((Object)((StringBuilder)object).toString());
            }
            if (n3 == n) {
                object = this.nextInBucketVToK;
                object[n4] = object[n];
                object[n] = -1;
                return;
            }
            n2 = this.nextInBucketVToK[n3];
        } while (true);
    }

    private void ensureCapacity(int n) {
        int n2;
        int[] arrn = this.nextInBucketKToV;
        if (arrn.length < n) {
            n2 = ImmutableCollection.Builder.expandedCapacity(arrn.length, n);
            this.keys = Arrays.copyOf(this.keys, n2);
            this.values = Arrays.copyOf(this.values, n2);
            this.nextInBucketKToV = HashBiMap.expandAndFillWithAbsent(this.nextInBucketKToV, n2);
            this.nextInBucketVToK = HashBiMap.expandAndFillWithAbsent(this.nextInBucketVToK, n2);
            this.prevInInsertionOrder = HashBiMap.expandAndFillWithAbsent(this.prevInInsertionOrder, n2);
            this.nextInInsertionOrder = HashBiMap.expandAndFillWithAbsent(this.nextInInsertionOrder, n2);
        }
        if (this.hashTableKToV.length >= n) return;
        n = Hashing.closedTableSize(n, 1.0);
        this.hashTableKToV = HashBiMap.createFilledWithAbsent(n);
        this.hashTableVToK = HashBiMap.createFilledWithAbsent(n);
        n = 0;
        while (n < this.size) {
            n2 = this.bucket(Hashing.smearedHash(this.keys[n]));
            arrn = this.nextInBucketKToV;
            int[] arrn2 = this.hashTableKToV;
            arrn[n] = arrn2[n2];
            arrn2[n2] = n;
            n2 = this.bucket(Hashing.smearedHash(this.values[n]));
            arrn2 = this.nextInBucketVToK;
            arrn = this.hashTableVToK;
            arrn2[n] = arrn[n2];
            arrn[n2] = n++;
        }
    }

    private static int[] expandAndFillWithAbsent(int[] arrn, int n) {
        int n2 = arrn.length;
        arrn = Arrays.copyOf(arrn, n);
        Arrays.fill(arrn, n2, n, -1);
        return arrn;
    }

    private void insertIntoTableKToV(int n, int n2) {
        boolean bl = n != -1;
        Preconditions.checkArgument(bl);
        n2 = this.bucket(n2);
        int[] arrn = this.nextInBucketKToV;
        int[] arrn2 = this.hashTableKToV;
        arrn[n] = arrn2[n2];
        arrn2[n2] = n;
    }

    private void insertIntoTableVToK(int n, int n2) {
        boolean bl = n != -1;
        Preconditions.checkArgument(bl);
        n2 = this.bucket(n2);
        int[] arrn = this.nextInBucketVToK;
        int[] arrn2 = this.hashTableVToK;
        arrn[n] = arrn2[n2];
        arrn2[n2] = n;
    }

    /*
     * Unable to fully structure code
     */
    private void moveEntryToIndex(int var1_1, int var2_2) {
        block5 : {
            block6 : {
                if (var1_1 == var2_2) {
                    return;
                }
                var3_3 = this.prevInInsertionOrder[var1_1];
                var4_4 = this.nextInInsertionOrder[var1_1];
                this.setSucceeds(var3_3, var2_2);
                this.setSucceeds(var2_2, var4_4);
                var5_5 = this.keys;
                var6_8 = var5_5[var1_1];
                var7_9 = this.values;
                var8_10 = var7_9[var1_1];
                var5_5[var2_2] = var6_8;
                var7_9[var2_2] = var8_10;
                var3_3 = this.bucket(Hashing.smearedHash(var6_8));
                var5_6 = this.hashTableKToV;
                if (var5_6[var3_3] != var1_1) break block6;
                var5_6[var3_3] = var2_2;
                ** GOTO lbl24
            }
            var4_4 = var5_6[var3_3];
            var3_3 = this.nextInBucketKToV[var4_4];
            do {
                block7 : {
                    if (var3_3 != var1_1) break block7;
                    this.nextInBucketKToV[var4_4] = var2_2;
lbl24: // 2 sources:
                    var5_7 = this.nextInBucketKToV;
                    var5_7[var2_2] = var5_7[var1_1];
                    var5_7[var1_1] = -1;
                    var3_3 = this.bucket(Hashing.smearedHash(var8_10));
                    var8_11 = this.hashTableVToK;
                    if (var8_11[var3_3] != var1_1) break;
                    var8_11[var3_3] = var2_2;
                    break block5;
                }
                var9_13 = this.nextInBucketKToV[var3_3];
                var4_4 = var3_3;
                var3_3 = var9_13;
            } while (true);
            var4_4 = var8_11[var3_3];
            var3_3 = this.nextInBucketVToK[var4_4];
            do {
                if (var3_3 == var1_1) {
                    this.nextInBucketVToK[var4_4] = var2_2;
                    break;
                }
                var9_13 = this.nextInBucketVToK[var3_3];
                var4_4 = var3_3;
                var3_3 = var9_13;
            } while (true);
        }
        var8_12 = this.nextInBucketVToK;
        var8_12[var2_2] = var8_12[var1_1];
        var8_12[var1_1] = -1;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = Serialization.readCount(objectInputStream);
        this.init(16);
        Serialization.populateMap(this, objectInputStream, n);
    }

    private void removeEntry(int n, int n2, int n3) {
        boolean bl = n != -1;
        Preconditions.checkArgument(bl);
        this.deleteFromTableKToV(n, n2);
        this.deleteFromTableVToK(n, n3);
        this.setSucceeds(this.prevInInsertionOrder[n], this.nextInInsertionOrder[n]);
        this.moveEntryToIndex(this.size - 1, n);
        K[] arrK = this.keys;
        n = this.size;
        arrK[n - 1] = null;
        this.values[n - 1] = null;
        this.size = n - 1;
        ++this.modCount;
    }

    private void replaceKeyInEntry(int n, @NullableDecl K k, boolean bl) {
        boolean bl2 = n != -1;
        Preconditions.checkArgument(bl2);
        int n2 = Hashing.smearedHash(k);
        int n3 = this.findEntryByKey(k, n2);
        int n4 = this.lastInInsertionOrder;
        int n5 = -2;
        int n6 = n;
        if (n3 != -1) {
            if (!bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Key already present in map: ");
                stringBuilder.append(k);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            int n7 = this.prevInInsertionOrder[n3];
            int n8 = this.nextInInsertionOrder[n3];
            this.removeEntryKeyHashKnown(n3, n2);
            n4 = n7;
            n5 = n8;
            n6 = n;
            if (n == this.size) {
                n6 = n3;
                n4 = n7;
                n5 = n8;
            }
        }
        if (n4 == n6) {
            n = this.prevInInsertionOrder[n6];
        } else {
            n = n4;
            if (n4 == this.size) {
                n = n3;
            }
        }
        if (n5 == n6) {
            n3 = this.nextInInsertionOrder[n6];
        } else if (n5 != this.size) {
            n3 = n5;
        }
        this.setSucceeds(this.prevInInsertionOrder[n6], this.nextInInsertionOrder[n6]);
        this.deleteFromTableKToV(n6, Hashing.smearedHash(this.keys[n6]));
        this.keys[n6] = k;
        this.insertIntoTableKToV(n6, Hashing.smearedHash(k));
        this.setSucceeds(n, n6);
        this.setSucceeds(n6, n3);
    }

    private void replaceValueInEntry(int n, @NullableDecl V v, boolean bl) {
        boolean bl2 = n != -1;
        Preconditions.checkArgument(bl2);
        int n2 = Hashing.smearedHash(v);
        int n3 = this.findEntryByValue(v, n2);
        int n4 = n;
        if (n3 != -1) {
            if (!bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value already present in map: ");
                stringBuilder.append(v);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.removeEntryValueHashKnown(n3, n2);
            n4 = n;
            if (n == this.size) {
                n4 = n3;
            }
        }
        this.deleteFromTableVToK(n4, Hashing.smearedHash(this.values[n4]));
        this.values[n4] = v;
        this.insertIntoTableVToK(n4, n2);
    }

    private void setSucceeds(int n, int n2) {
        if (n == -2) {
            this.firstInInsertionOrder = n2;
        } else {
            this.nextInInsertionOrder[n] = n2;
        }
        if (n2 == -2) {
            this.lastInInsertionOrder = n;
            return;
        }
        this.prevInInsertionOrder[n2] = n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }

    @Override
    public void clear() {
        Arrays.fill(this.keys, 0, this.size, null);
        Arrays.fill(this.values, 0, this.size, null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        ++this.modCount;
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        if (this.findEntryByKey(object) == -1) return false;
        return true;
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        if (this.findEntryByValue(object) == -1) return false;
        return true;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet;
        EntrySet entrySet2 = entrySet = this.entrySet;
        if (entrySet != null) return entrySet2;
        this.entrySet = entrySet2 = new EntrySet();
        return entrySet2;
    }

    int findEntry(@NullableDecl Object object, int n, int[] arrn, int[] arrn2, Object[] arrobject) {
        n = arrn[this.bucket(n)];
        while (n != -1) {
            if (Objects.equal(arrobject[n], object)) {
                return n;
            }
            n = arrn2[n];
        }
        return -1;
    }

    int findEntryByKey(@NullableDecl Object object) {
        return this.findEntryByKey(object, Hashing.smearedHash(object));
    }

    int findEntryByKey(@NullableDecl Object object, int n) {
        return this.findEntry(object, n, this.hashTableKToV, this.nextInBucketKToV, this.keys);
    }

    int findEntryByValue(@NullableDecl Object object) {
        return this.findEntryByValue(object, Hashing.smearedHash(object));
    }

    int findEntryByValue(@NullableDecl Object object, int n) {
        return this.findEntry(object, n, this.hashTableVToK, this.nextInBucketVToK, this.values);
    }

    @NullableDecl
    @Override
    public V forcePut(@NullableDecl K k, @NullableDecl V v) {
        return this.put(k, v, true);
    }

    @NullableDecl
    @Override
    public V get(@NullableDecl Object object) {
        int n = this.findEntryByKey(object);
        if (n == -1) {
            object = null;
            return (V)object;
        }
        object = this.values[n];
        return (V)object;
    }

    @NullableDecl
    K getInverse(@NullableDecl Object object) {
        int n = this.findEntryByValue(object);
        if (n == -1) {
            object = null;
            return (K)object;
        }
        object = this.keys[n];
        return (K)object;
    }

    void init(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedSize");
        int n2 = Hashing.closedTableSize(n, 1.0);
        this.size = 0;
        this.keys = new Object[n];
        this.values = new Object[n];
        this.hashTableKToV = HashBiMap.createFilledWithAbsent(n2);
        this.hashTableVToK = HashBiMap.createFilledWithAbsent(n2);
        this.nextInBucketKToV = HashBiMap.createFilledWithAbsent(n);
        this.nextInBucketVToK = HashBiMap.createFilledWithAbsent(n);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = HashBiMap.createFilledWithAbsent(n);
        this.nextInInsertionOrder = HashBiMap.createFilledWithAbsent(n);
    }

    @Override
    public BiMap<V, K> inverse() {
        BiMap<K, V> biMap;
        BiMap<K, V> biMap2 = biMap = this.inverse;
        if (biMap != null) return biMap2;
        biMap2 = new Inverse(this);
        this.inverse = biMap2;
        return biMap2;
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet;
        KeySet keySet2 = keySet = this.keySet;
        if (keySet != null) return keySet2;
        this.keySet = keySet2 = new KeySet();
        return keySet2;
    }

    @Override
    public V put(@NullableDecl K k, @NullableDecl V v) {
        return this.put(k, v, false);
    }

    @NullableDecl
    V put(@NullableDecl K object, @NullableDecl V v, boolean bl) {
        int n = Hashing.smearedHash(object);
        int n2 = this.findEntryByKey(object, n);
        if (n2 != -1) {
            object = this.values[n2];
            if (Objects.equal(object, v)) {
                return v;
            }
            this.replaceValueInEntry(n2, v, bl);
            return (V)object;
        }
        n2 = Hashing.smearedHash(v);
        int n3 = this.findEntryByValue(v, n2);
        if (bl) {
            if (n3 != -1) {
                this.removeEntryValueHashKnown(n3, n2);
            }
        } else {
            bl = n3 == -1;
            Preconditions.checkArgument(bl, "Value already present: %s", v);
        }
        this.ensureCapacity(this.size + 1);
        K[] arrK = this.keys;
        n3 = this.size;
        arrK[n3] = object;
        this.values[n3] = v;
        this.insertIntoTableKToV(n3, n);
        this.insertIntoTableVToK(this.size, n2);
        this.setSucceeds(this.lastInInsertionOrder, this.size);
        this.setSucceeds(this.size, -2);
        ++this.size;
        ++this.modCount;
        return null;
    }

    @NullableDecl
    K putInverse(@NullableDecl V object, @NullableDecl K k, boolean bl) {
        int n = Hashing.smearedHash(object);
        int n2 = this.findEntryByValue(object, n);
        if (n2 != -1) {
            object = this.keys[n2];
            if (Objects.equal(object, k)) {
                return k;
            }
            this.replaceKeyInEntry(n2, k, bl);
            return (K)object;
        }
        n2 = this.lastInInsertionOrder;
        int n3 = Hashing.smearedHash(k);
        int n4 = this.findEntryByKey(k, n3);
        if (bl) {
            if (n4 != -1) {
                n2 = this.prevInInsertionOrder[n4];
                this.removeEntryKeyHashKnown(n4, n3);
            }
        } else {
            bl = n4 == -1;
            Preconditions.checkArgument(bl, "Key already present: %s", k);
        }
        this.ensureCapacity(this.size + 1);
        K[] arrK = this.keys;
        n4 = this.size;
        arrK[n4] = k;
        this.values[n4] = object;
        this.insertIntoTableKToV(n4, n3);
        this.insertIntoTableVToK(this.size, n);
        n = n2 == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[n2];
        this.setSucceeds(n2, this.size);
        this.setSucceeds(this.size, n);
        ++this.size;
        ++this.modCount;
        return null;
    }

    @NullableDecl
    @Override
    public V remove(@NullableDecl Object object) {
        int n = Hashing.smearedHash(object);
        int n2 = this.findEntryByKey(object, n);
        if (n2 == -1) {
            return null;
        }
        object = this.values[n2];
        this.removeEntryKeyHashKnown(n2, n);
        return (V)object;
    }

    void removeEntry(int n) {
        this.removeEntryKeyHashKnown(n, Hashing.smearedHash(this.keys[n]));
    }

    void removeEntryKeyHashKnown(int n, int n2) {
        this.removeEntry(n, n2, Hashing.smearedHash(this.values[n]));
    }

    void removeEntryValueHashKnown(int n, int n2) {
        this.removeEntry(n, Hashing.smearedHash(this.keys[n]), n2);
    }

    @NullableDecl
    K removeInverse(@NullableDecl Object object) {
        int n = Hashing.smearedHash(object);
        int n2 = this.findEntryByValue(object, n);
        if (n2 == -1) {
            return null;
        }
        object = this.keys[n2];
        this.removeEntryValueHashKnown(n2, n);
        return (K)object;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Set<V> values() {
        ValueSet valueSet;
        ValueSet valueSet2 = valueSet = this.valueSet;
        if (valueSet != null) return valueSet2;
        this.valueSet = valueSet2 = new ValueSet();
        return valueSet2;
    }

    final class EntryForKey
    extends AbstractMapEntry<K, V> {
        int index;
        @NullableDecl
        final K key;

        EntryForKey(int n) {
            this.key = HashBiMap.this.keys[n];
            this.index = n;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @NullableDecl
        @Override
        public V getValue() {
            V v;
            this.updateIndex();
            if (this.index == -1) {
                v = null;
                return v;
            }
            v = HashBiMap.this.values[this.index];
            return v;
        }

        @Override
        public V setValue(V v) {
            this.updateIndex();
            if (this.index == -1) {
                return HashBiMap.this.put(this.key, v);
            }
            Object v2 = HashBiMap.this.values[this.index];
            if (Objects.equal(v2, v)) {
                return v;
            }
            HashBiMap.this.replaceValueInEntry(this.index, v, false);
            return v2;
        }

        void updateIndex() {
            int n = this.index;
            if (n != -1 && n <= HashBiMap.this.size) {
                if (Objects.equal(HashBiMap.this.keys[this.index], this.key)) return;
            }
            this.index = HashBiMap.this.findEntryByKey(this.key);
        }
    }

    static final class EntryForValue<K, V>
    extends AbstractMapEntry<V, K> {
        final HashBiMap<K, V> biMap;
        int index;
        final V value;

        EntryForValue(HashBiMap<K, V> hashBiMap, int n) {
            this.biMap = hashBiMap;
            this.value = hashBiMap.values[n];
            this.index = n;
        }

        private void updateIndex() {
            int n = this.index;
            if (n != -1 && n <= this.biMap.size) {
                if (Objects.equal(this.value, this.biMap.values[this.index])) return;
            }
            this.index = this.biMap.findEntryByValue(this.value);
        }

        @Override
        public V getKey() {
            return this.value;
        }

        @Override
        public K getValue() {
            K k;
            this.updateIndex();
            if (this.index == -1) {
                k = null;
                return k;
            }
            k = this.biMap.keys[this.index];
            return k;
        }

        @Override
        public K setValue(K k) {
            this.updateIndex();
            if (this.index == -1) {
                return this.biMap.putInverse(this.value, k, false);
            }
            Object k2 = this.biMap.keys[this.index];
            if (Objects.equal(k2, k)) {
                return k;
            }
            this.biMap.replaceKeyInEntry(this.index, k, false);
            return k2;
        }
    }

    final class EntrySet
    extends View<K, V, Map.Entry<K, V>> {
        EntrySet() {
            super(HashBiMap.this);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof Map.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            entry = entry.getValue();
            int n = HashBiMap.this.findEntryByKey(object);
            bl3 = bl;
            if (n == -1) return bl3;
            bl3 = bl;
            if (!Objects.equal(entry, HashBiMap.this.values[n])) return bl3;
            return true;
        }

        @Override
        Map.Entry<K, V> forEntry(int n) {
            return new EntryForKey(n);
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            if (!(object instanceof Map.Entry)) return false;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            entry = entry.getValue();
            int n = Hashing.smearedHash(object);
            int n2 = HashBiMap.this.findEntryByKey(object, n);
            if (n2 == -1) return false;
            if (!Objects.equal(entry, HashBiMap.this.values[n2])) return false;
            HashBiMap.this.removeEntryKeyHashKnown(n2, n);
            return true;
        }
    }

    static class Inverse<K, V>
    extends AbstractMap<V, K>
    implements BiMap<V, K>,
    Serializable {
        private final HashBiMap<K, V> forward;
        private transient Set<Map.Entry<V, K>> inverseEntrySet;

        Inverse(HashBiMap<K, V> hashBiMap) {
            this.forward = hashBiMap;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.forward.inverse = this;
        }

        @Override
        public void clear() {
            this.forward.clear();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            return this.forward.containsValue(object);
        }

        @Override
        public boolean containsValue(@NullableDecl Object object) {
            return this.forward.containsKey(object);
        }

        @Override
        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set;
            Set<Map.Entry<V, K>> set2 = set = this.inverseEntrySet;
            if (set != null) return set2;
            this.inverseEntrySet = set2 = new InverseEntrySet<K, V>(this.forward);
            return set2;
        }

        @NullableDecl
        @Override
        public K forcePut(@NullableDecl V v, @NullableDecl K k) {
            return this.forward.putInverse(v, k, true);
        }

        @NullableDecl
        @Override
        public K get(@NullableDecl Object object) {
            return this.forward.getInverse(object);
        }

        @Override
        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @Override
        public Set<V> keySet() {
            return this.forward.values();
        }

        @NullableDecl
        @Override
        public K put(@NullableDecl V v, @NullableDecl K k) {
            return this.forward.putInverse(v, k, false);
        }

        @NullableDecl
        @Override
        public K remove(@NullableDecl Object object) {
            return this.forward.removeInverse(object);
        }

        @Override
        public int size() {
            return this.forward.size;
        }

        @Override
        public Set<K> values() {
            return this.forward.keySet();
        }
    }

    static class InverseEntrySet<K, V>
    extends View<K, V, Map.Entry<V, K>> {
        InverseEntrySet(HashBiMap<K, V> hashBiMap) {
            super(hashBiMap);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof Map.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            entry = entry.getValue();
            int n = this.biMap.findEntryByValue(object);
            bl3 = bl;
            if (n == -1) return bl3;
            bl3 = bl;
            if (!Objects.equal(this.biMap.keys[n], entry)) return bl3;
            return true;
        }

        @Override
        Map.Entry<V, K> forEntry(int n) {
            return new EntryForValue(this.biMap, n);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) return false;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            entry = entry.getValue();
            int n = Hashing.smearedHash(object);
            int n2 = this.biMap.findEntryByValue(object, n);
            if (n2 == -1) return false;
            if (!Objects.equal(this.biMap.keys[n2], entry)) return false;
            this.biMap.removeEntryValueHashKnown(n2, n);
            return true;
        }
    }

    final class KeySet
    extends View<K, V, K> {
        KeySet() {
            super(HashBiMap.this);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return HashBiMap.this.containsKey(object);
        }

        @Override
        K forEntry(int n) {
            return HashBiMap.this.keys[n];
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            int n = Hashing.smearedHash(object);
            int n2 = HashBiMap.this.findEntryByKey(object, n);
            if (n2 == -1) return false;
            HashBiMap.this.removeEntryKeyHashKnown(n2, n);
            return true;
        }
    }

    final class ValueSet
    extends View<K, V, V> {
        ValueSet() {
            super(HashBiMap.this);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return HashBiMap.this.containsValue(object);
        }

        @Override
        V forEntry(int n) {
            return HashBiMap.this.values[n];
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            int n = Hashing.smearedHash(object);
            int n2 = HashBiMap.this.findEntryByValue(object, n);
            if (n2 == -1) return false;
            HashBiMap.this.removeEntryValueHashKnown(n2, n);
            return true;
        }
    }

    static abstract class View<K, V, T>
    extends AbstractSet<T> {
        final HashBiMap<K, V> biMap;

        View(HashBiMap<K, V> hashBiMap) {
            this.biMap = hashBiMap;
        }

        @Override
        public void clear() {
            this.biMap.clear();
        }

        abstract T forEntry(int var1);

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>(){
                private int expectedModCount;
                private int index;
                private int indexToRemove;
                private int remaining;
                {
                    this.index = View.this.biMap.firstInInsertionOrder;
                    this.indexToRemove = -1;
                    this.expectedModCount = View.this.biMap.modCount;
                    this.remaining = View.this.biMap.size;
                }

                private void checkForComodification() {
                    if (View.this.biMap.modCount != this.expectedModCount) throw new ConcurrentModificationException();
                }

                @Override
                public boolean hasNext() {
                    this.checkForComodification();
                    if (this.index == -2) return false;
                    if (this.remaining <= 0) return false;
                    return true;
                }

                @Override
                public T next() {
                    if (!this.hasNext()) throw new NoSuchElementException();
                    Object t = View.this.forEntry(this.index);
                    this.indexToRemove = this.index;
                    this.index = View.this.biMap.nextInInsertionOrder[this.index];
                    --this.remaining;
                    return t;
                }

                @Override
                public void remove() {
                    this.checkForComodification();
                    boolean bl = this.indexToRemove != -1;
                    CollectPreconditions.checkRemove(bl);
                    View.this.biMap.removeEntry(this.indexToRemove);
                    if (this.index == View.this.biMap.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = View.this.biMap.modCount;
                }
            };
        }

        @Override
        public int size() {
            return this.biMap.size;
        }

    }

}


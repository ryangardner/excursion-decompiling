/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CompactHashing;
import com.google.common.collect.Hashing;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactHashMap<K, V>
extends AbstractMap<K, V>
implements Serializable {
    static final double HASH_FLOODING_FPP = 0.001;
    private static final int MAX_HASH_BUCKET_LENGTH = 9;
    private static final Object NOT_FOUND = new Object();
    @NullableDecl
    transient int[] entries;
    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> entrySetView;
    @MonotonicNonNullDecl
    private transient Set<K> keySetView;
    @NullableDecl
    transient Object[] keys;
    private transient int metadata;
    private transient int size;
    @NullableDecl
    private transient Object table;
    @NullableDecl
    transient Object[] values;
    @MonotonicNonNullDecl
    private transient Collection<V> valuesView;

    CompactHashMap() {
        this.init(3);
    }

    CompactHashMap(int n) {
        this.init(n);
    }

    static /* synthetic */ int access$710(CompactHashMap compactHashMap) {
        int n = compactHashMap.size;
        compactHashMap.size = n - 1;
        return n;
    }

    public static <K, V> CompactHashMap<K, V> create() {
        return new CompactHashMap<K, V>();
    }

    public static <K, V> CompactHashMap<K, V> createWithExpectedSize(int n) {
        return new CompactHashMap<K, V>(n);
    }

    private int hashTableMask() {
        return (1 << (this.metadata & 31)) - 1;
    }

    private int indexOf(@NullableDecl Object object) {
        int n;
        if (this.needsAllocArrays()) {
            return -1;
        }
        int n2 = Hashing.smearedHash(object);
        int n3 = CompactHashing.tableGet(this.table, n2 & (n = this.hashTableMask()));
        if (n3 == 0) {
            return -1;
        }
        int n4 = CompactHashing.getHashPrefix(n2, n);
        do {
            if (CompactHashing.getHashPrefix(n2 = this.entries[--n3], n) == n4 && Objects.equal(object, this.keys[n3])) {
                return n3;
            }
            n3 = n2 = CompactHashing.getNext(n2, n);
        } while (n2 != 0);
        return -1;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        int n = ((ObjectInputStream)object).readInt();
        if (n < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid size: ");
            ((StringBuilder)object).append(n);
            throw new InvalidObjectException(((StringBuilder)object).toString());
        }
        this.init(n);
        int n2 = 0;
        while (n2 < n) {
            this.put(((ObjectInputStream)object).readObject(), ((ObjectInputStream)object).readObject());
            ++n2;
        }
    }

    @NullableDecl
    private Object removeHelper(@NullableDecl Object object) {
        if (this.needsAllocArrays()) {
            return NOT_FOUND;
        }
        int n = this.hashTableMask();
        int n2 = CompactHashing.remove(object, null, n, this.table, this.entries, this.keys, null);
        if (n2 == -1) {
            return NOT_FOUND;
        }
        object = this.values[n2];
        this.moveLastEntry(n2, n);
        --this.size;
        this.incrementModCount();
        return object;
    }

    private void resizeMeMaybe(int n) {
        int n2 = this.entries.length;
        if (n <= n2) return;
        n = Math.min(1073741823, Math.max(1, n2 >>> 1) + n2 | 1);
        if (n == n2) return;
        this.resizeEntries(n);
    }

    private int resizeTable(int n, int n2, int n3, int n4) {
        Object object = CompactHashing.createTable(n2);
        int n5 = n2 - 1;
        if (n4 != 0) {
            CompactHashing.tableSet(object, n3 & n5, n4 + 1);
        }
        Object object2 = this.table;
        int[] arrn = this.entries;
        n2 = 0;
        do {
            if (n2 > n) {
                this.table = object;
                this.setHashTableMask(n5);
                return n5;
            }
            n3 = CompactHashing.tableGet(object2, n2);
            while (n3 != 0) {
                int n6 = n3 - 1;
                int n7 = arrn[n6];
                n4 = CompactHashing.getHashPrefix(n7, n) | n2;
                int n8 = n4 & n5;
                int n9 = CompactHashing.tableGet(object, n8);
                CompactHashing.tableSet(object, n8, n3);
                arrn[n6] = CompactHashing.maskCombine(n4, n9, n5);
                n3 = CompactHashing.getNext(n7, n);
            }
            ++n2;
        } while (true);
    }

    private void setHashTableMask(int n) {
        n = Integer.numberOfLeadingZeros(n);
        this.metadata = CompactHashing.maskCombine(this.metadata, 32 - n, 31);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size());
        Iterator<Map.Entry<K, V>> iterator2 = this.entrySetIterator();
        while (iterator2.hasNext()) {
            Map.Entry<K, V> entry = iterator2.next();
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    void accessEntry(int n) {
    }

    int adjustAfterRemove(int n, int n2) {
        return n - 1;
    }

    int allocArrays() {
        Preconditions.checkState(this.needsAllocArrays(), "Arrays already allocated");
        int n = this.metadata;
        int n2 = CompactHashing.tableSize(n);
        this.table = CompactHashing.createTable(n2);
        this.setHashTableMask(n2 - 1);
        this.entries = new int[n];
        this.keys = new Object[n];
        this.values = new Object[n];
        return n;
    }

    @Override
    public void clear() {
        if (this.needsAllocArrays()) {
            return;
        }
        this.incrementModCount();
        if (this.delegateOrNull() != null) {
            this.metadata = Ints.constrainToRange(this.size(), 3, 1073741823);
            this.table = null;
            this.size = 0;
            return;
        }
        Arrays.fill(this.keys, 0, this.size, null);
        Arrays.fill(this.values, 0, this.size, null);
        CompactHashing.tableClear(this.table);
        Arrays.fill(this.entries, 0, this.size, 0);
        this.size = 0;
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        Map<K, V> map = this.delegateOrNull();
        if (map != null) {
            return map.containsKey(object);
        }
        if (this.indexOf(object) == -1) return false;
        return true;
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        Map<K, V> map = this.delegateOrNull();
        if (map != null) {
            return map.containsValue(object);
        }
        int n = 0;
        while (n < this.size) {
            if (Objects.equal(object, this.values[n])) {
                return true;
            }
            ++n;
        }
        return false;
    }

    Map<K, V> convertToHashFloodingResistantImplementation() {
        Map<Object, Object> map = this.createHashFloodingResistantDelegate(this.hashTableMask() + 1);
        int n = this.firstEntryIndex();
        do {
            if (n < 0) {
                this.table = map;
                this.entries = null;
                this.keys = null;
                this.values = null;
                this.incrementModCount();
                return map;
            }
            map.put(this.keys[n], this.values[n]);
            n = this.getSuccessor(n);
        } while (true);
    }

    Set<Map.Entry<K, V>> createEntrySet() {
        return new EntrySetView();
    }

    Map<K, V> createHashFloodingResistantDelegate(int n) {
        return new LinkedHashMap(n, 1.0f);
    }

    Set<K> createKeySet() {
        return new KeySetView();
    }

    Collection<V> createValues() {
        return new ValuesView();
    }

    @NullableDecl
    Map<K, V> delegateOrNull() {
        Object object = this.table;
        if (!(object instanceof Map)) return null;
        return (Map)object;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set;
        Set<Map.Entry<K, V>> set2 = set = this.entrySetView;
        if (set != null) return set2;
        this.entrySetView = set2 = this.createEntrySet();
        return set2;
    }

    Iterator<Map.Entry<K, V>> entrySetIterator() {
        Map<K, V> map = this.delegateOrNull();
        if (map == null) return new CompactHashMap<K, V>(){

            Map.Entry<K, V> getOutput(int n) {
                return new MapEntry(n);
            }
        };
        return map.entrySet().iterator();
    }

    int firstEntryIndex() {
        if (!this.isEmpty()) return 0;
        return -1;
    }

    @Override
    public V get(@NullableDecl Object object) {
        Map<K, V> map = this.delegateOrNull();
        if (map != null) {
            return map.get(object);
        }
        int n = this.indexOf(object);
        if (n == -1) {
            return null;
        }
        this.accessEntry(n);
        return (V)this.values[n];
    }

    int getSuccessor(int n) {
        if (++n >= this.size) return -1;
        return n;
    }

    void incrementModCount() {
        this.metadata += 32;
    }

    void init(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "Expected size must be >= 0");
        this.metadata = Ints.constrainToRange(n, 1, 1073741823);
    }

    void insertEntry(int n, @NullableDecl K k, @NullableDecl V v, int n2, int n3) {
        this.entries[n] = CompactHashing.maskCombine(n2, 0, n3);
        this.keys[n] = k;
        this.values[n] = v;
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set;
        Set<K> set2 = set = this.keySetView;
        if (set != null) return set2;
        this.keySetView = set2 = this.createKeySet();
        return set2;
    }

    Iterator<K> keySetIterator() {
        Map<K, V> map = this.delegateOrNull();
        if (map == null) return new CompactHashMap<K, V>(){

            K getOutput(int n) {
                return (K)CompactHashMap.this.keys[n];
            }
        };
        return map.keySet().iterator();
    }

    void moveLastEntry(int n, int n2) {
        Object object;
        int n3 = this.size() - 1;
        if (n >= n3) {
            this.keys[n] = null;
            this.values[n] = null;
            this.entries[n] = 0;
            return;
        }
        Object[] arrobject = this.keys;
        arrobject[n] = object = arrobject[n3];
        Object[] arrobject2 = this.values;
        arrobject2[n] = arrobject2[n3];
        arrobject[n3] = null;
        arrobject2[n3] = null;
        arrobject2 = this.entries;
        arrobject2[n] = arrobject2[n3];
        arrobject2[n3] = false;
        int n4 = Hashing.smearedHash(object) & n2;
        int n5 = CompactHashing.tableGet(this.table, n4);
        int n6 = n3 + 1;
        n3 = n5;
        if (n5 == n6) {
            CompactHashing.tableSet(this.table, n4, n + 1);
            return;
        }
        do {
            n4 = n3 - 1;
        } while ((n3 = CompactHashing.getNext(n5 = this.entries[n4], n2)) != n6);
        this.entries[n4] = CompactHashing.maskCombine(n5, n + 1, n2);
    }

    boolean needsAllocArrays() {
        if (this.table != null) return false;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    @NullableDecl
    @Override
    public V put(@NullableDecl K var1_1, @NullableDecl V var2_2) {
        block9 : {
            block7 : {
                block8 : {
                    if (this.needsAllocArrays()) {
                        this.allocArrays();
                    }
                    if ((var3_4 = this.delegateOrNull()) != null) {
                        return var3_4.put(var1_1 /* !! */ , var2_3);
                    }
                    var4_5 = this.entries;
                    var5_6 = this.keys;
                    var3_4 = this.values;
                    var6_7 = this.size;
                    var7_8 = var6_7 + 1;
                    var8_9 = Hashing.smearedHash(var1_1 /* !! */ );
                    var10_11 = var8_9 & (var9_10 = this.hashTableMask());
                    var11_12 = CompactHashing.tableGet(this.table, var10_11);
                    if (var11_12 != 0) break block7;
                    if (var7_8 <= var9_10) break block8;
                    var10_11 = this.resizeTable(var9_10, CompactHashing.newCapacity(var9_10), var8_9, var6_7);
                    break block9;
                }
                CompactHashing.tableSet(this.table, var10_11, var7_8);
                ** GOTO lbl39
            }
            var12_13 = CompactHashing.getHashPrefix(var8_9, var9_10);
            var10_11 = 0;
            do {
                if (CompactHashing.getHashPrefix(var14_15 = var4_5[var13_14 = var11_12 - 1], var9_10) == var12_13 && Objects.equal(var1_1 /* !! */ , var5_6[var13_14])) {
                    var1_2 = var3_4[var13_14];
                    var3_4[var13_14] = var2_3;
                    this.accessEntry(var13_14);
                    return (V)var1_2;
                }
                var11_12 = CompactHashing.getNext(var14_15, var9_10);
                ++var10_11;
            } while (var11_12 != 0);
            if (var10_11 >= 9) {
                return this.convertToHashFloodingResistantImplementation().put(var1_1 /* !! */ , var2_3);
            }
            if (var7_8 > var9_10) {
                var10_11 = this.resizeTable(var9_10, CompactHashing.newCapacity(var9_10), var8_9, var6_7);
            } else {
                var4_5[var13_14] = CompactHashing.maskCombine(var14_15, var7_8, var9_10);
lbl39: // 2 sources:
                var10_11 = var9_10;
            }
        }
        this.resizeMeMaybe(var7_8);
        this.insertEntry(var6_7, var1_1 /* !! */ , var2_3, var8_9, var10_11);
        this.size = var7_8;
        this.incrementModCount();
        return null;
    }

    @NullableDecl
    @Override
    public V remove(@NullableDecl Object object) {
        Map<K, V> map = this.delegateOrNull();
        if (map != null) {
            return map.remove(object);
        }
        object = map = this.removeHelper(object);
        if (map != NOT_FOUND) return (V)object;
        object = null;
        return (V)object;
    }

    void resizeEntries(int n) {
        this.entries = Arrays.copyOf(this.entries, n);
        this.keys = Arrays.copyOf(this.keys, n);
        this.values = Arrays.copyOf(this.values, n);
    }

    @Override
    public int size() {
        Map<K, V> map = this.delegateOrNull();
        if (map == null) return this.size;
        return map.size();
    }

    public void trimToSize() {
        if (this.needsAllocArrays()) {
            return;
        }
        Map<K, V> map = this.delegateOrNull();
        if (map != null) {
            Map<K, V> map2 = this.createHashFloodingResistantDelegate(this.size());
            map2.putAll(map);
            this.table = map2;
            return;
        }
        int n = this.size;
        if (n < this.entries.length) {
            this.resizeEntries(n);
        }
        int n2 = CompactHashing.tableSize(n);
        n = this.hashTableMask();
        if (n2 >= n) return;
        this.resizeTable(n, n2, 0, 0);
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection;
        Collection<V> collection2 = collection = this.valuesView;
        if (collection != null) return collection2;
        this.valuesView = collection2 = this.createValues();
        return collection2;
    }

    Iterator<V> valuesIterator() {
        Map<K, V> map = this.delegateOrNull();
        if (map == null) return new CompactHashMap<K, V>(){

            V getOutput(int n) {
                return (V)CompactHashMap.this.values[n];
            }
        };
        return map.values().iterator();
    }

    class EntrySetView
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySetView() {
        }

        @Override
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            boolean bl;
            Map map = CompactHashMap.this.delegateOrNull();
            if (map != null) {
                return map.entrySet().contains(object);
            }
            boolean bl2 = object instanceof Map.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (Map.Entry)object;
            int n = CompactHashMap.this.indexOf(object.getKey());
            bl3 = bl;
            if (n == -1) return bl3;
            bl3 = bl;
            if (!Objects.equal(CompactHashMap.this.values[n], object.getValue())) return bl3;
            return true;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return CompactHashMap.this.entrySetIterator();
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            Map map = CompactHashMap.this.delegateOrNull();
            if (map != null) {
                return map.entrySet().remove(object);
            }
            if (!(object instanceof Map.Entry)) return false;
            object = (Map.Entry)object;
            if (CompactHashMap.this.needsAllocArrays()) {
                return false;
            }
            int n = CompactHashMap.this.hashTableMask();
            int n2 = CompactHashing.remove(object.getKey(), object.getValue(), n, CompactHashMap.this.table, CompactHashMap.this.entries, CompactHashMap.this.keys, CompactHashMap.this.values);
            if (n2 == -1) {
                return false;
            }
            CompactHashMap.this.moveLastEntry(n2, n);
            CompactHashMap.access$710(CompactHashMap.this);
            CompactHashMap.this.incrementModCount();
            return true;
        }

        @Override
        public int size() {
            return CompactHashMap.this.size();
        }
    }

    private abstract class Itr<T>
    implements Iterator<T> {
        int currentIndex;
        int expectedMetadata;
        int indexToRemove;

        private Itr() {
            this.expectedMetadata = CompactHashMap.this.metadata;
            this.currentIndex = CompactHashMap.this.firstEntryIndex();
            this.indexToRemove = -1;
        }

        private void checkForConcurrentModification() {
            if (CompactHashMap.this.metadata != this.expectedMetadata) throw new ConcurrentModificationException();
        }

        abstract T getOutput(int var1);

        @Override
        public boolean hasNext() {
            if (this.currentIndex < 0) return false;
            return true;
        }

        void incrementExpectedModCount() {
            this.expectedMetadata += 32;
        }

        @Override
        public T next() {
            int n;
            this.checkForConcurrentModification();
            if (!this.hasNext()) throw new NoSuchElementException();
            this.indexToRemove = n = this.currentIndex;
            T t = this.getOutput(n);
            this.currentIndex = CompactHashMap.this.getSuccessor(this.currentIndex);
            return t;
        }

        @Override
        public void remove() {
            this.checkForConcurrentModification();
            boolean bl = this.indexToRemove >= 0;
            CollectPreconditions.checkRemove(bl);
            this.incrementExpectedModCount();
            CompactHashMap compactHashMap = CompactHashMap.this;
            compactHashMap.remove(compactHashMap.keys[this.indexToRemove]);
            this.currentIndex = CompactHashMap.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
            this.indexToRemove = -1;
        }
    }

    class KeySetView
    extends AbstractSet<K> {
        KeySetView() {
        }

        @Override
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return CompactHashMap.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return CompactHashMap.this.keySetIterator();
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            Map map = CompactHashMap.this.delegateOrNull();
            if (map != null) {
                return map.keySet().remove(object);
            }
            if (CompactHashMap.this.removeHelper(object) == NOT_FOUND) return false;
            return true;
        }

        @Override
        public int size() {
            return CompactHashMap.this.size();
        }
    }

    final class MapEntry
    extends AbstractMapEntry<K, V> {
        @NullableDecl
        private final K key;
        private int lastKnownIndex;

        MapEntry(int n) {
            this.key = CompactHashMap.this.keys[n];
            this.lastKnownIndex = n;
        }

        private void updateLastKnownIndex() {
            int n = this.lastKnownIndex;
            if (n != -1 && n < CompactHashMap.this.size()) {
                if (Objects.equal(this.key, CompactHashMap.this.keys[this.lastKnownIndex])) return;
            }
            this.lastKnownIndex = CompactHashMap.this.indexOf(this.key);
        }

        @NullableDecl
        @Override
        public K getKey() {
            return this.key;
        }

        @NullableDecl
        @Override
        public V getValue() {
            Map map = CompactHashMap.this.delegateOrNull();
            if (map != null) {
                return map.get(this.key);
            }
            this.updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                map = null;
                return (V)map;
            }
            map = CompactHashMap.this.values[this.lastKnownIndex];
            return (V)map;
        }

        @Override
        public V setValue(V v) {
            Map<K, V> map = CompactHashMap.this.delegateOrNull();
            if (map != null) {
                return map.put(this.key, v);
            }
            this.updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                CompactHashMap.this.put(this.key, v);
                return null;
            }
            map = CompactHashMap.this.values[this.lastKnownIndex];
            CompactHashMap.this.values[this.lastKnownIndex] = v;
            return (V)map;
        }
    }

    class ValuesView
    extends AbstractCollection<V> {
        ValuesView() {
        }

        @Override
        public void clear() {
            CompactHashMap.this.clear();
        }

        @Override
        public Iterator<V> iterator() {
            return CompactHashMap.this.valuesIterator();
        }

        @Override
        public int size() {
            return CompactHashMap.this.size();
        }
    }

}


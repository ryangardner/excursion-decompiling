/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableMap<K, V>
extends ImmutableMap<K, V> {
    private static final byte ABSENT = -1;
    private static final int BYTE_MASK = 255;
    private static final int BYTE_MAX_SIZE = 128;
    static final ImmutableMap<Object, Object> EMPTY = new RegularImmutableMap<Object, Object>(null, new Object[0], 0);
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_MAX_SIZE = 32768;
    private static final long serialVersionUID = 0L;
    final transient Object[] alternatingKeysAndValues;
    private final transient Object hashTable;
    private final transient int size;

    private RegularImmutableMap(Object object, Object[] arrobject, int n) {
        this.hashTable = object;
        this.alternatingKeysAndValues = arrobject;
        this.size = n;
    }

    static <K, V> RegularImmutableMap<K, V> create(int n, Object[] arrobject) {
        if (n == 0) {
            return (RegularImmutableMap)EMPTY;
        }
        if (n == 1) {
            CollectPreconditions.checkEntryNotNull(arrobject[0], arrobject[1]);
            return new RegularImmutableMap<K, V>(null, arrobject, 1);
        }
        Preconditions.checkPositionIndex(n, arrobject.length >> 1);
        return new RegularImmutableMap<K, V>(RegularImmutableMap.createHashTable(arrobject, n, ImmutableSet.chooseTableSize(n), 0), arrobject, n);
    }

    static Object createHashTable(Object[] arrobject, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        block12 : {
            block11 : {
                block10 : {
                    if (n == 1) {
                        CollectPreconditions.checkEntryNotNull(arrobject[n3], arrobject[n3 ^ 1]);
                        return null;
                    }
                    n4 = n2 - 1;
                    n6 = 0;
                    n7 = 0;
                    n5 = 0;
                    if (n2 <= 128) break block10;
                    if (n2 <= 32768) break block11;
                    break block12;
                }
                byte[] arrby = new byte[n2];
                Arrays.fill(arrby, (byte)-1);
                n2 = n5;
                block0 : do {
                    if (n2 >= n) return arrby;
                    n6 = n2 * 2 + n3;
                    Object object = arrobject[n6];
                    Object object2 = arrobject[n6 ^ 1];
                    CollectPreconditions.checkEntryNotNull(object, object2);
                    n5 = Hashing.smear(object.hashCode());
                    do {
                        n7 = n5 & n4;
                        n5 = arrby[n7] & 255;
                        if (n5 == 255) {
                            arrby[n7] = (byte)n6;
                            ++n2;
                            continue block0;
                        }
                        if (arrobject[n5].equals(object)) throw RegularImmutableMap.duplicateKeyException(object, object2, arrobject, n5);
                        n5 = n7 + 1;
                    } while (true);
                    break;
                } while (true);
            }
            short[] arrs = new short[n2];
            Arrays.fill(arrs, (short)-1);
            n2 = n6;
            block2 : do {
                if (n2 >= n) return arrs;
                n6 = n2 * 2 + n3;
                Object object = arrobject[n6];
                Object object3 = arrobject[n6 ^ 1];
                CollectPreconditions.checkEntryNotNull(object, object3);
                n5 = Hashing.smear(object.hashCode());
                do {
                    n7 = n5 & n4;
                    n5 = arrs[n7] & 65535;
                    if (n5 == 65535) {
                        arrs[n7] = (short)n6;
                        ++n2;
                        continue block2;
                    }
                    if (arrobject[n5].equals(object)) throw RegularImmutableMap.duplicateKeyException(object, object3, arrobject, n5);
                    n5 = n7 + 1;
                } while (true);
                break;
            } while (true);
        }
        int[] arrn = new int[n2];
        Arrays.fill(arrn, -1);
        n2 = n7;
        block4 : while (n2 < n) {
            n6 = n2 * 2 + n3;
            Object object = arrobject[n6];
            Object object4 = arrobject[n6 ^ 1];
            CollectPreconditions.checkEntryNotNull(object, object4);
            n5 = Hashing.smear(object.hashCode());
            do {
                if ((n7 = arrn[n5 &= n4]) == -1) {
                    arrn[n5] = n6;
                    ++n2;
                    continue block4;
                }
                if (arrobject[n7].equals(object)) throw RegularImmutableMap.duplicateKeyException(object, object4, arrobject, n7);
                ++n5;
            } while (true);
            break;
        }
        return arrn;
    }

    private static IllegalArgumentException duplicateKeyException(Object object, Object object2, Object[] arrobject, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Multiple entries with same key: ");
        stringBuilder.append(object);
        stringBuilder.append("=");
        stringBuilder.append(object2);
        stringBuilder.append(" and ");
        stringBuilder.append(arrobject[n]);
        stringBuilder.append("=");
        stringBuilder.append(arrobject[n ^ 1]);
        return new IllegalArgumentException(stringBuilder.toString());
    }

    static Object get(@NullableDecl Object object, @NullableDecl Object[] arrobject, int n, int n2, @NullableDecl Object object2) {
        block14 : {
            block13 : {
                block12 : {
                    Object var5_5 = null;
                    if (object2 == null) {
                        return null;
                    }
                    if (n == 1) {
                        object = var5_5;
                        if (!arrobject[n2].equals(object2)) return object;
                        return arrobject[n2 ^ 1];
                    }
                    if (object == null) {
                        return null;
                    }
                    if (object instanceof byte[]) break block12;
                    if (object instanceof short[]) break block13;
                    break block14;
                }
                object = object;
                n2 = ((byte[])object).length;
                n = Hashing.smear(object2.hashCode());
                do {
                    int n3 = n & n2 - 1;
                    n = object[n3] & 255;
                    if (n == 255) {
                        return null;
                    }
                    if (arrobject[n].equals(object2)) {
                        return arrobject[n ^ 1];
                    }
                    n = n3 + 1;
                } while (true);
            }
            object = object;
            n2 = ((byte[])object).length;
            n = Hashing.smear(object2.hashCode());
            do {
                int n4 = n & n2 - 1;
                n = object[n4] & 65535;
                if (n == 65535) {
                    return null;
                }
                if (arrobject[n].equals(object2)) {
                    return arrobject[n ^ 1];
                }
                n = n4 + 1;
            } while (true);
        }
        object = object;
        n2 = ((byte[])object).length;
        n = Hashing.smear(object2.hashCode());
        do {
            int n5 = n & n2 - 1;
            n = object[n5];
            if (n == -1) {
                return null;
            }
            if (arrobject[n].equals(object2)) {
                return arrobject[n ^ 1];
            }
            n = n5 + 1;
        } while (true);
    }

    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
    }

    @Override
    ImmutableSet<K> createKeySet() {
        return new KeySet<Object>(this, new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size));
    }

    @Override
    ImmutableCollection<V> createValues() {
        return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
    }

    @NullableDecl
    @Override
    public V get(@NullableDecl Object object) {
        return (V)RegularImmutableMap.get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, object);
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    static class EntrySet<K, V>
    extends ImmutableSet<Map.Entry<K, V>> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int keyOffset;
        private final transient ImmutableMap<K, V> map;
        private final transient int size;

        EntrySet(ImmutableMap<K, V> immutableMap, Object[] arrobject, int n, int n2) {
            this.map = immutableMap;
            this.alternatingKeysAndValues = arrobject;
            this.keyOffset = n;
            this.size = n2;
        }

        @Override
        public boolean contains(Object object) {
            boolean bl;
            boolean bl2 = object instanceof Map.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            entry = entry.getValue();
            bl3 = bl;
            if (entry == null) return bl3;
            bl3 = bl;
            if (!((Object)entry).equals(this.map.get(object))) return bl3;
            return true;
        }

        @Override
        int copyIntoArray(Object[] arrobject, int n) {
            return this.asList().copyIntoArray(arrobject, n);
        }

        @Override
        ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>(){

                @Override
                public Map.Entry<K, V> get(int n) {
                    Preconditions.checkElementIndex(n, EntrySet.this.size);
                    Object[] arrobject = EntrySet.this.alternatingKeysAndValues;
                    return new AbstractMap.SimpleImmutableEntry<Object, Object>(arrobject[EntrySet.this.keyOffset + (n *= 2)], EntrySet.this.alternatingKeysAndValues[n + (EntrySet.this.keyOffset ^ 1)]);
                }

                @Override
                public boolean isPartialView() {
                    return true;
                }

                @Override
                public int size() {
                    return EntrySet.this.size;
                }
            };
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.asList().iterator();
        }

        @Override
        public int size() {
            return this.size;
        }

    }

    static final class KeySet<K>
    extends ImmutableSet<K> {
        private final transient ImmutableList<K> list;
        private final transient ImmutableMap<K, ?> map;

        KeySet(ImmutableMap<K, ?> immutableMap, ImmutableList<K> immutableList) {
            this.map = immutableMap;
            this.list = immutableList;
        }

        @Override
        public ImmutableList<K> asList() {
            return this.list;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (this.map.get(object) == null) return false;
            return true;
        }

        @Override
        int copyIntoArray(Object[] arrobject, int n) {
            return this.asList().copyIntoArray(arrobject, n);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public UnmodifiableIterator<K> iterator() {
            return this.asList().iterator();
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    static final class KeysOrValuesAsList
    extends ImmutableList<Object> {
        private final transient Object[] alternatingKeysAndValues;
        private final transient int offset;
        private final transient int size;

        KeysOrValuesAsList(Object[] arrobject, int n, int n2) {
            this.alternatingKeysAndValues = arrobject;
            this.offset = n;
            this.size = n2;
        }

        @Override
        public Object get(int n) {
            Preconditions.checkElementIndex(n, this.size);
            return this.alternatingKeysAndValues[n * 2 + this.offset];
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public int size() {
            return this.size;
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReference;

public final class CharsToNameCanonicalizer {
    private static final int DEFAULT_T_SIZE = 64;
    public static final int HASH_MULT = 33;
    static final int MAX_COLL_CHAIN_LENGTH = 100;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    private static final int MAX_T_SIZE = 65536;
    private Bucket[] _buckets;
    private boolean _canonicalize;
    private final int _flags;
    private boolean _hashShared;
    private int _indexMask;
    private int _longestCollisionList;
    private BitSet _overflows;
    private final CharsToNameCanonicalizer _parent;
    private final int _seed;
    private int _size;
    private int _sizeThreshold;
    private String[] _symbols;
    private final AtomicReference<TableInfo> _tableInfo;

    private CharsToNameCanonicalizer(int n) {
        this._parent = null;
        this._seed = n;
        this._canonicalize = true;
        this._flags = -1;
        this._hashShared = false;
        this._longestCollisionList = 0;
        this._tableInfo = new AtomicReference<TableInfo>(TableInfo.createInitial(64));
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, int n, int n2, TableInfo tableInfo) {
        this._parent = charsToNameCanonicalizer;
        this._seed = n2;
        this._tableInfo = null;
        this._flags = n;
        this._canonicalize = JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(n);
        this._symbols = tableInfo.symbols;
        this._buckets = tableInfo.buckets;
        this._size = tableInfo.size;
        this._longestCollisionList = tableInfo.longestCollisionList;
        n = this._symbols.length;
        this._sizeThreshold = CharsToNameCanonicalizer._thresholdSize(n);
        this._indexMask = n - 1;
        this._hashShared = true;
    }

    private String _addSymbol(char[] object, int n, int n2, int n3, int n4) {
        if (this._hashShared) {
            this.copyArrays();
            this._hashShared = false;
        } else if (this._size >= this._sizeThreshold) {
            this.rehash();
            n4 = this._hashToIndex(this.calcHash((char[])object, n, n2));
        }
        Object object2 = new String((char[])object, n, n2);
        object = object2;
        if (JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(this._flags)) {
            object = InternCache.instance.intern((String)object2);
        }
        ++this._size;
        object2 = this._symbols;
        if (object2[n4] == null) {
            object2[n4] = object;
            return object;
        }
        n2 = n4 >> 1;
        object2 = new Bucket((String)object, this._buckets[n2]);
        n = object2.length;
        if (n > 100) {
            this._handleSpillOverflow(n2, (Bucket)object2, n4);
            return object;
        }
        this._buckets[n2] = object2;
        this._longestCollisionList = Math.max(n, this._longestCollisionList);
        return object;
    }

    private String _findSymbol2(char[] arrc, int n, int n2, Bucket bucket) {
        while (bucket != null) {
            String string2 = bucket.has(arrc, n, n2);
            if (string2 != null) {
                return string2;
            }
            bucket = bucket.next;
        }
        return null;
    }

    private void _handleSpillOverflow(int n, Bucket bucket, int n2) {
        BitSet bitSet = this._overflows;
        if (bitSet == null) {
            this._overflows = bitSet = new BitSet();
            bitSet.set(n);
        } else if (bitSet.get(n)) {
            if (JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this._flags)) {
                this.reportTooManyCollisions(100);
            }
            this._canonicalize = false;
        } else {
            this._overflows.set(n);
        }
        this._symbols[n2] = bucket.symbol;
        this._buckets[n] = null;
        this._size -= bucket.length;
        this._longestCollisionList = -1;
    }

    private static int _thresholdSize(int n) {
        return n - (n >> 2);
    }

    private void copyArrays() {
        Object[] arrobject = this._symbols;
        this._symbols = Arrays.copyOf(arrobject, arrobject.length);
        arrobject = this._buckets;
        this._buckets = (Bucket[])Arrays.copyOf(arrobject, arrobject.length);
    }

    public static CharsToNameCanonicalizer createRoot() {
        long l = System.currentTimeMillis();
        return CharsToNameCanonicalizer.createRoot((int)l + (int)(l >>> 32) | 1);
    }

    protected static CharsToNameCanonicalizer createRoot(int n) {
        return new CharsToNameCanonicalizer(n);
    }

    private void mergeChild(TableInfo tableInfo) {
        int n = tableInfo.size;
        TableInfo tableInfo2 = this._tableInfo.get();
        if (n == tableInfo2.size) {
            return;
        }
        if (n > 12000) {
            tableInfo = TableInfo.createInitial(64);
        }
        this._tableInfo.compareAndSet(tableInfo2, tableInfo);
    }

    private void rehash() {
        Object object;
        Object object2;
        int n;
        int n2;
        int n3;
        Object object3 = this._symbols;
        int n4 = ((String[])object3).length;
        int n5 = n4 + n4;
        if (n5 > 65536) {
            this._size = 0;
            this._canonicalize = false;
            this._symbols = new String[64];
            this._buckets = new Bucket[32];
            this._indexMask = 63;
            this._hashShared = false;
            return;
        }
        Bucket[] arrbucket = this._buckets;
        this._symbols = new String[n5];
        this._buckets = new Bucket[n5 >> 1];
        this._indexMask = n5 - 1;
        this._sizeThreshold = CharsToNameCanonicalizer._thresholdSize(n5);
        int n6 = 0;
        n5 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            object2 = object3[n2];
            n3 = n6;
            n = n5;
            if (object2 != null) {
                n3 = n6 + 1;
                object = this._symbols;
                n6 = this._hashToIndex(this.calcHash((String)object2));
                if (object[n6] == null) {
                    object[n6] = object2;
                    n = n5;
                } else {
                    this._buckets[n6 >>= 1] = object2 = new Bucket((String)object2, this._buckets[n6]);
                    n = Math.max(n5, object2.length);
                }
            }
            n6 = n3;
            n5 = n;
        }
        n3 = 0;
        n2 = n5;
        n = n6;
        n5 = n3;
        do {
            if (n5 < n4 >> 1) {
                object3 = arrbucket[n5];
                n6 = n2;
                n2 = n;
            } else {
                this._longestCollisionList = n2;
                this._overflows = null;
                if (n != this._size) throw new IllegalStateException(String.format("Internal error on SymbolTable.rehash(): had %d entries; now have %d", this._size, n));
                return;
            }
            while (object3 != null) {
                ++n2;
                object2 = this._symbols;
                object = object3.symbol;
                n = this._hashToIndex(this.calcHash((String)object));
                if (object2[n] == null) {
                    object2[n] = object;
                } else {
                    this._buckets[n >>= 1] = object2 = new Bucket((String)object, this._buckets[n]);
                    n6 = Math.max(n6, object2.length);
                }
                object3 = object3.next;
            }
            ++n5;
            n = n2;
            n2 = n6;
        } while (true);
    }

    public int _hashToIndex(int n) {
        n += n >>> 15;
        n ^= n << 7;
        return n + (n >>> 3) & this._indexMask;
    }

    public int bucketCount() {
        return this._symbols.length;
    }

    public int calcHash(String string2) {
        int n = string2.length();
        int n2 = this._seed;
        int n3 = 0;
        do {
            if (n3 >= n) {
                n3 = n2;
                if (n2 != 0) return n3;
                return 1;
            }
            n2 = n2 * 33 + string2.charAt(n3);
            ++n3;
        } while (true);
    }

    public int calcHash(char[] arrc, int n, int n2) {
        int n3 = this._seed;
        int n4 = n;
        do {
            if (n4 >= n2 + n) {
                n = n3;
                if (n3 != 0) return n;
                return 1;
            }
            n3 = n3 * 33 + arrc[n4];
            ++n4;
        } while (true);
    }

    public int collisionCount() {
        Bucket[] arrbucket = this._buckets;
        int n = arrbucket.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            Bucket bucket = arrbucket[n2];
            int n4 = n3;
            if (bucket != null) {
                n4 = n3 + bucket.length;
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public String findSymbol(char[] arrc, int n, int n2, int n3) {
        if (n2 < 1) {
            return "";
        }
        if (!this._canonicalize) {
            return new String(arrc, n, n2);
        }
        int n4 = this._hashToIndex(n3);
        Object object = this._symbols[n4];
        if (object == null) return this._addSymbol(arrc, n, n2, n3, n4);
        if (((String)object).length() == n2) {
            int n5 = 0;
            while (((String)object).charAt(n5) == arrc[n + n5]) {
                int n6;
                n5 = n6 = n5 + 1;
                if (n6 != n2) continue;
                return object;
            }
        }
        if ((object = this._buckets[n4 >> 1]) == null) return this._addSymbol(arrc, n, n2, n3, n4);
        String string2 = ((Bucket)object).has(arrc, n, n2);
        if (string2 != null) {
            return string2;
        }
        object = this._findSymbol2(arrc, n, n2, ((Bucket)object).next);
        if (object == null) return this._addSymbol(arrc, n, n2, n3, n4);
        return object;
    }

    public int hashSeed() {
        return this._seed;
    }

    public CharsToNameCanonicalizer makeChild(int n) {
        return new CharsToNameCanonicalizer(this, n, this._seed, this._tableInfo.get());
    }

    public int maxCollisionLength() {
        return this._longestCollisionList;
    }

    public boolean maybeDirty() {
        return this._hashShared ^ true;
    }

    public void release() {
        if (!this.maybeDirty()) {
            return;
        }
        CharsToNameCanonicalizer charsToNameCanonicalizer = this._parent;
        if (charsToNameCanonicalizer == null) return;
        if (!this._canonicalize) return;
        charsToNameCanonicalizer.mergeChild(new TableInfo(this));
        this._hashShared = true;
    }

    protected void reportTooManyCollisions(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Longest collision chain in symbol table (of size ");
        stringBuilder.append(this._size);
        stringBuilder.append(") now exceeds maximum, ");
        stringBuilder.append(n);
        stringBuilder.append(" -- suspect a DoS attack based on hash collisions");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int size() {
        AtomicReference<TableInfo> atomicReference = this._tableInfo;
        if (atomicReference == null) return this._size;
        return atomicReference.get().size;
    }

    protected void verifyInternalConsistency() {
        int n;
        int n2 = this._symbols.length;
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            n = n3;
            if (this._symbols[i] != null) {
                n = n3 + 1;
            }
            n3 = n;
        }
        for (n = 0; n < n2 >> 1; ++n) {
            Bucket bucket = this._buckets[n];
            while (bucket != null) {
                ++n3;
                bucket = bucket.next;
            }
        }
        if (n3 != this._size) throw new IllegalStateException(String.format("Internal error: expected internal size %d vs calculated count %d", this._size, n3));
    }

    static final class Bucket {
        public final int length;
        public final Bucket next;
        public final String symbol;

        public Bucket(String string2, Bucket bucket) {
            this.symbol = string2;
            this.next = bucket;
            int n = 1;
            if (bucket != null) {
                n = 1 + bucket.length;
            }
            this.length = n;
        }

        public String has(char[] arrc, int n, int n2) {
            int n3;
            if (this.symbol.length() != n2) {
                return null;
            }
            int n4 = 0;
            do {
                if (this.symbol.charAt(n4) != arrc[n + n4]) {
                    return null;
                }
                n4 = n3 = n4 + 1;
            } while (n3 < n2);
            return this.symbol;
        }
    }

    private static final class TableInfo {
        final Bucket[] buckets;
        final int longestCollisionList;
        final int size;
        final String[] symbols;

        public TableInfo(int n, int n2, String[] arrstring, Bucket[] arrbucket) {
            this.size = n;
            this.longestCollisionList = n2;
            this.symbols = arrstring;
            this.buckets = arrbucket;
        }

        public TableInfo(CharsToNameCanonicalizer charsToNameCanonicalizer) {
            this.size = charsToNameCanonicalizer._size;
            this.longestCollisionList = charsToNameCanonicalizer._longestCollisionList;
            this.symbols = charsToNameCanonicalizer._symbols;
            this.buckets = charsToNameCanonicalizer._buckets;
        }

        public static TableInfo createInitial(int n) {
            return new TableInfo(0, 0, new String[n], new Bucket[n >> 1]);
        }
    }

}


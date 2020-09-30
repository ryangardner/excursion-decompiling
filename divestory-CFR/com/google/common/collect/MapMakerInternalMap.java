/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ForwardingConcurrentMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.MapMaker;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MapMakerInternalMap<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V>,
Serializable {
    static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final WeakValueReference<Object, Object, DummyInternalEntry> UNSET_WEAK_VALUE_REFERENCE = new WeakValueReference<Object, Object, DummyInternalEntry>(){

        @Override
        public void clear() {
        }

        @Override
        public WeakValueReference<Object, Object, DummyInternalEntry> copyFor(ReferenceQueue<Object> referenceQueue, DummyInternalEntry dummyInternalEntry) {
            return this;
        }

        @Override
        public Object get() {
            return null;
        }

        @Override
        public DummyInternalEntry getEntry() {
            return null;
        }
    };
    private static final long serialVersionUID = 5L;
    final int concurrencyLevel;
    final transient InternalEntryHelper<K, V, E, S> entryHelper;
    @MonotonicNonNullDecl
    transient Set<Map.Entry<K, V>> entrySet;
    final Equivalence<Object> keyEquivalence;
    @MonotonicNonNullDecl
    transient Set<K> keySet;
    final transient int segmentMask;
    final transient int segmentShift;
    final transient Segment<K, V, E, S>[] segments;
    @MonotonicNonNullDecl
    transient Collection<V> values;

    private MapMakerInternalMap(MapMaker arrsegment, InternalEntryHelper<K, V, E, S> internalEntryHelper) {
        int n;
        this.concurrencyLevel = Math.min(arrsegment.getConcurrencyLevel(), 65536);
        this.keyEquivalence = arrsegment.getKeyEquivalence();
        this.entryHelper = internalEntryHelper;
        int n2 = Math.min(arrsegment.getInitialCapacity(), 1073741824);
        int n3 = 0;
        int n4 = 1;
        int n5 = 0;
        for (n = 1; n < this.concurrencyLevel; ++n5, n <<= 1) {
        }
        this.segmentShift = 32 - n5;
        this.segmentMask = n - 1;
        this.segments = this.newSegmentArray(n);
        int n6 = n2 / n;
        n5 = n4;
        int n7 = n6;
        if (n * n6 < n2) {
            n7 = n6 + 1;
            n5 = n4;
        }
        do {
            n = n3;
            if (n5 >= n7) {
                while (n < (arrsegment = this.segments).length) {
                    arrsegment[n] = this.createSegment(n5, -1);
                    ++n;
                }
                return;
            }
            n5 <<= 1;
        } while (true);
    }

    static <K, V> MapMakerInternalMap<K, V, ? extends InternalEntry<K, V, ?>, ?> create(MapMaker mapMaker) {
        if (mapMaker.getKeyStrength() == Strength.STRONG && mapMaker.getValueStrength() == Strength.STRONG) {
            return new MapMakerInternalMap<K, V, E, S>(mapMaker, StrongKeyStrongValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() == Strength.STRONG && mapMaker.getValueStrength() == Strength.WEAK) {
            return new MapMakerInternalMap<K, V, E, S>(mapMaker, StrongKeyWeakValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() == Strength.WEAK && mapMaker.getValueStrength() == Strength.STRONG) {
            return new MapMakerInternalMap<K, V, E, S>(mapMaker, WeakKeyStrongValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() != Strength.WEAK) throw new AssertionError();
        if (mapMaker.getValueStrength() != Strength.WEAK) throw new AssertionError();
        return new MapMakerInternalMap<K, V, E, S>(mapMaker, WeakKeyWeakValueEntry.Helper.instance());
    }

    static <K> MapMakerInternalMap<K, MapMaker.Dummy, ? extends InternalEntry<K, MapMaker.Dummy, ?>, ?> createWithDummyValues(MapMaker mapMaker) {
        if (mapMaker.getKeyStrength() == Strength.STRONG && mapMaker.getValueStrength() == Strength.STRONG) {
            return new MapMakerInternalMap<K, V, E, S>(mapMaker, StrongKeyDummyValueEntry.Helper.instance());
        }
        if (mapMaker.getKeyStrength() == Strength.WEAK && mapMaker.getValueStrength() == Strength.STRONG) {
            return new MapMakerInternalMap<K, V, E, S>(mapMaker, WeakKeyDummyValueEntry.Helper.instance());
        }
        if (mapMaker.getValueStrength() != Strength.WEAK) throw new AssertionError();
        throw new IllegalArgumentException("Map cannot have both weak and dummy values");
    }

    static int rehash(int n) {
        n += n << 15 ^ -12931;
        n ^= n >>> 10;
        n += n << 3;
        n ^= n >>> 6;
        n += (n << 2) + (n << 14);
        return n ^ n >>> 16;
    }

    private static <E> ArrayList<E> toArrayList(Collection<E> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    static <K, V, E extends InternalEntry<K, V, E>> WeakValueReference<K, V, E> unsetWeakValueReference() {
        return UNSET_WEAK_VALUE_REFERENCE;
    }

    @Override
    public void clear() {
        Segment<K, V, E, S>[] arrsegment = this.segments;
        int n = arrsegment.length;
        int n2 = 0;
        while (n2 < n) {
            arrsegment[n2].clear();
            ++n2;
        }
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        if (object == null) {
            return false;
        }
        int n = this.hash(object);
        return this.segmentFor(n).containsKey(object, n);
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean containsValue(@NullableDecl Object var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[UNCONDITIONALDOLOOP]], but top level block is 2[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    E copyEntry(E e, E e2) {
        return this.segmentFor(e.getHash()).copyEntry(e, e2);
    }

    Segment<K, V, E, S> createSegment(int n, int n2) {
        return this.entryHelper.newSegment(this, n, n2);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet != null) {
            return entrySet;
        }
        this.entrySet = entrySet = new EntrySet();
        return entrySet;
    }

    @Override
    public V get(@NullableDecl Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).get(object, n);
    }

    E getEntry(@NullableDecl Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).getEntry(object, n);
    }

    V getLiveValue(E e) {
        if (e.getKey() != null) return e.getValue();
        return null;
    }

    int hash(Object object) {
        return MapMakerInternalMap.rehash(this.keyEquivalence.hash(object));
    }

    @Override
    public boolean isEmpty() {
        int n;
        Segment<K, V, E, S>[] arrsegment = this.segments;
        boolean bl = false;
        long l = 0L;
        for (n = 0; n < arrsegment.length; l += (long)arrsegment[n].modCount, ++n) {
            if (arrsegment[n].count == 0) continue;
            return false;
        }
        if (l == 0L) return true;
        n = 0;
        do {
            if (n >= arrsegment.length) {
                if (l != 0L) return bl;
                return true;
            }
            if (arrsegment[n].count != 0) {
                return false;
            }
            l -= (long)arrsegment[n].modCount;
            ++n;
        } while (true);
    }

    boolean isLiveForTesting(InternalEntry<K, V, ?> internalEntry) {
        if (this.segmentFor(internalEntry.getHash()).getLiveValueForTesting(internalEntry) == null) return false;
        return true;
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet = this.keySet;
        if (keySet != null) {
            return keySet;
        }
        this.keySet = keySet = new KeySet();
        return keySet;
    }

    Strength keyStrength() {
        return this.entryHelper.keyStrength();
    }

    final Segment<K, V, E, S>[] newSegmentArray(int n) {
        return new Segment[n];
    }

    @Override
    public V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).put(k, n, v, false);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        Iterator<Map.Entry<K, V>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            this.put(object.getKey(), object.getValue());
        }
    }

    @Override
    public V putIfAbsent(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).put(k, n, v, true);
    }

    void reclaimKey(E e) {
        int n = e.getHash();
        this.segmentFor(n).reclaimKey(e, n);
    }

    void reclaimValue(WeakValueReference<K, V, E> weakValueReference) {
        E e = weakValueReference.getEntry();
        int n = e.getHash();
        this.segmentFor(n).reclaimValue(e.getKey(), n, weakValueReference);
    }

    @Override
    public V remove(@NullableDecl Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).remove(object, n);
    }

    @Override
    public boolean remove(@NullableDecl Object object, @NullableDecl Object object2) {
        if (object == null) return false;
        if (object2 == null) {
            return false;
        }
        int n = this.hash(object);
        return this.segmentFor(n).remove(object, n, object2);
    }

    @Override
    public V replace(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int n = this.hash(k);
        return this.segmentFor(n).replace(k, n, v);
    }

    @Override
    public boolean replace(K k, @NullableDecl V v, V v2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v2);
        if (v == null) {
            return false;
        }
        int n = this.hash(k);
        return this.segmentFor(n).replace(k, n, v, v2);
    }

    Segment<K, V, E, S> segmentFor(int n) {
        return this.segments[n >>> this.segmentShift & this.segmentMask];
    }

    @Override
    public int size() {
        Segment<K, V, E, S>[] arrsegment = this.segments;
        long l = 0L;
        int n = 0;
        while (n < arrsegment.length) {
            l += (long)arrsegment[n].count;
            ++n;
        }
        return Ints.saturatedCast(l);
    }

    Equivalence<Object> valueEquivalence() {
        return this.entryHelper.valueStrength().defaultEquivalence();
    }

    Strength valueStrength() {
        return this.entryHelper.valueStrength();
    }

    @Override
    public Collection<V> values() {
        Values values2 = this.values;
        if (values2 != null) {
            return values2;
        }
        this.values = values2 = new Values();
        return values2;
    }

    Object writeReplace() {
        return new SerializationProxy(this.entryHelper.keyStrength(), this.entryHelper.valueStrength(), this.keyEquivalence, this.entryHelper.valueStrength().defaultEquivalence(), this.concurrencyLevel, this);
    }

    static abstract class AbstractSerializationProxy<K, V>
    extends ForwardingConcurrentMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        final int concurrencyLevel;
        transient ConcurrentMap<K, V> delegate;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;

        AbstractSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int n, ConcurrentMap<K, V> concurrentMap) {
            this.keyStrength = strength;
            this.valueStrength = strength2;
            this.keyEquivalence = equivalence;
            this.valueEquivalence = equivalence2;
            this.concurrencyLevel = n;
            this.delegate = concurrentMap;
        }

        @Override
        protected ConcurrentMap<K, V> delegate() {
            return this.delegate;
        }

        void readEntries(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            Object object;
            while ((object = objectInputStream.readObject()) != null) {
                Object object2 = objectInputStream.readObject();
                this.delegate.put(object, object2);
            }
            return;
        }

        MapMaker readMapMaker(ObjectInputStream objectInputStream) throws IOException {
            int n = objectInputStream.readInt();
            return new MapMaker().initialCapacity(n).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
        }

        void writeMapTo(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeInt(this.delegate.size());
            Iterator iterator2 = this.delegate.entrySet().iterator();
            do {
                if (!iterator2.hasNext()) {
                    objectOutputStream.writeObject(null);
                    return;
                }
                Map.Entry entry = iterator2.next();
                objectOutputStream.writeObject(entry.getKey());
                objectOutputStream.writeObject(entry.getValue());
            } while (true);
        }
    }

    static abstract class AbstractStrongKeyEntry<K, V, E extends InternalEntry<K, V, E>>
    implements InternalEntry<K, V, E> {
        final int hash;
        final K key;
        @NullableDecl
        final E next;

        AbstractStrongKeyEntry(K k, int n, @NullableDecl E e) {
            this.key = k;
            this.hash = n;
            this.next = e;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public E getNext() {
            return this.next;
        }
    }

    static abstract class AbstractWeakKeyEntry<K, V, E extends InternalEntry<K, V, E>>
    extends WeakReference<K>
    implements InternalEntry<K, V, E> {
        final int hash;
        @NullableDecl
        final E next;

        AbstractWeakKeyEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl E e) {
            super(k, referenceQueue);
            this.hash = n;
            this.next = e;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public K getKey() {
            return (K)this.get();
        }

        @Override
        public E getNext() {
            return this.next;
        }
    }

    static final class CleanupMapTask
    implements Runnable {
        final WeakReference<MapMakerInternalMap<?, ?, ?, ?>> mapReference;

        public CleanupMapTask(MapMakerInternalMap<?, ?, ?, ?> mapMakerInternalMap) {
            this.mapReference = new WeakReference(mapMakerInternalMap);
        }

        @Override
        public void run() {
            Segment<K, V, E, S>[] arrsegment = (Segment<K, V, E, S>[])this.mapReference.get();
            if (arrsegment == null) throw new CancellationException();
            arrsegment = arrsegment.segments;
            int n = arrsegment.length;
            int n2 = 0;
            while (n2 < n) {
                arrsegment[n2].runCleanup();
                ++n2;
            }
        }
    }

    static final class DummyInternalEntry
    implements InternalEntry<Object, Object, DummyInternalEntry> {
        private DummyInternalEntry() {
            throw new AssertionError();
        }

        @Override
        public int getHash() {
            throw new AssertionError();
        }

        @Override
        public Object getKey() {
            throw new AssertionError();
        }

        @Override
        public DummyInternalEntry getNext() {
            throw new AssertionError();
        }

        @Override
        public Object getValue() {
            throw new AssertionError();
        }
    }

    final class EntryIterator
    extends MapMakerInternalMap<K, V, E, S> {
        EntryIterator() {
        }

        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }

    final class EntrySet
    extends SafeToArraySet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            Object object2 = (object = (Map.Entry)object).getKey();
            if (object2 == null) {
                return false;
            }
            object2 = MapMakerInternalMap.this.get(object2);
            bl = bl2;
            if (object2 == null) return bl;
            bl = bl2;
            if (!MapMakerInternalMap.this.valueEquivalence().equivalent(object.getValue(), object2)) return bl;
            return true;
        }

        @Override
        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean remove(Object object) {
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            Map.Entry entry = (Map.Entry)object;
            object = entry.getKey();
            bl = bl2;
            if (object == null) return bl;
            bl = bl2;
            if (!MapMakerInternalMap.this.remove(object, entry.getValue())) return bl;
            return true;
        }

        @Override
        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    abstract class HashIterator<T>
    implements Iterator<T> {
        @MonotonicNonNullDecl
        Segment<K, V, E, S> currentSegment;
        @MonotonicNonNullDecl
        AtomicReferenceArray<E> currentTable;
        @NullableDecl
        MapMakerInternalMap<K, V, E, S> lastReturned;
        @NullableDecl
        E nextEntry;
        @NullableDecl
        MapMakerInternalMap<K, V, E, S> nextExternal;
        int nextSegmentIndex;
        int nextTableIndex;

        HashIterator() {
            this.nextSegmentIndex = MapMakerInternalMap.this.segments.length - 1;
            this.nextTableIndex = -1;
            this.advance();
        }

        /*
         * Unable to fully structure code
         */
        final void advance() {
            this.nextExternal = null;
            if (this.nextInChain()) {
                return;
            }
            if (this.nextInTable()) {
                return;
            }
            do lbl-1000: // 3 sources:
            {
                if (this.nextSegmentIndex < 0) return;
                var1_1 = MapMakerInternalMap.this.segments;
                var2_2 = this.nextSegmentIndex;
                this.nextSegmentIndex = var2_2 - 1;
                var1_1 = var1_1[var2_2];
                this.currentSegment = var1_1;
                if (var1_1.count == 0) ** GOTO lbl-1000
                this.currentTable = var1_1 = this.currentSegment.table;
                this.nextTableIndex = var1_1.length() - 1;
            } while (!this.nextInTable());
        }

        boolean advanceTo(E object) {
            try {
                boolean bl;
                Object k = object.getKey();
                Object v = MapMakerInternalMap.this.getLiveValue(object);
                if (v != null) {
                    object = new Object(k, v);
                    this.nextExternal = object;
                    bl = true;
                    return bl;
                } else {
                    bl = false;
                }
                return bl;
            }
            finally {
                this.currentSegment.postReadCleanup();
            }
        }

        @Override
        public boolean hasNext() {
            if (this.nextExternal == null) return false;
            return true;
        }

        @Override
        public abstract T next();

        MapMakerInternalMap<K, V, E, S> nextEntry() {
            MapMakerInternalMap<K, V, E, S> mapMakerInternalMap = this.nextExternal;
            if (mapMakerInternalMap == null) throw new NoSuchElementException();
            this.lastReturned = mapMakerInternalMap;
            this.advance();
            return this.lastReturned;
        }

        boolean nextInChain() {
            E e = this.nextEntry;
            if (e == null) return false;
            while ((e = (this.nextEntry = e.getNext())) != null) {
                if (this.advanceTo(e)) {
                    return true;
                }
                e = this.nextEntry;
            }
            return false;
        }

        /*
         * Unable to fully structure code
         */
        boolean nextInTable() {
            do lbl-1000: // 3 sources:
            {
                if ((var1_1 = this.nextTableIndex) < 0) return false;
                var2_2 = this.currentTable;
                this.nextTableIndex = var1_1 - 1;
                var2_2 = (InternalEntry)var2_2.get(var1_1);
                this.nextEntry = var2_2;
                if (var2_2 == null) ** GOTO lbl-1000
                if (this.advanceTo(var2_2) != false) return true;
            } while (!this.nextInChain());
            return true;
        }

        @Override
        public void remove() {
            boolean bl = this.lastReturned != null;
            CollectPreconditions.checkRemove(bl);
            MapMakerInternalMap.this.remove(((WriteThroughEntry)((Object)this.lastReturned)).getKey());
            this.lastReturned = null;
        }
    }

    static interface InternalEntry<K, V, E extends InternalEntry<K, V, E>> {
        public int getHash();

        public K getKey();

        public E getNext();

        public V getValue();
    }

    static interface InternalEntryHelper<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> {
        public E copy(S var1, E var2, @NullableDecl E var3);

        public Strength keyStrength();

        public E newEntry(S var1, K var2, int var3, @NullableDecl E var4);

        public S newSegment(MapMakerInternalMap<K, V, E, S> var1, int var2, int var3);

        public void setValue(S var1, E var2, V var3);

        public Strength valueStrength();
    }

    final class KeyIterator
    extends MapMakerInternalMap<K, V, E, S> {
        KeyIterator() {
        }

        public K next() {
            return this.nextEntry().getKey();
        }
    }

    final class KeySet
    extends SafeToArraySet<K> {
        KeySet() {
        }

        @Override
        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return MapMakerInternalMap.this.containsKey(object);
        }

        @Override
        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean remove(Object object) {
            if (MapMakerInternalMap.this.remove(object) == null) return false;
            return true;
        }

        @Override
        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    private static abstract class SafeToArraySet<E>
    extends AbstractSet<E> {
        private SafeToArraySet() {
        }

        @Override
        public Object[] toArray() {
            return MapMakerInternalMap.toArrayList(this).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return MapMakerInternalMap.toArrayList(this).toArray(arrT);
        }
    }

    static abstract class Segment<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>>
    extends ReentrantLock {
        volatile int count;
        final MapMakerInternalMap<K, V, E, S> map;
        final int maxSegmentSize;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        @MonotonicNonNullDecl
        volatile AtomicReferenceArray<E> table;
        int threshold;

        Segment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int n, int n2) {
            this.map = mapMakerInternalMap;
            this.maxSegmentSize = n2;
            this.initTable(this.newEntryArray(n));
        }

        static <K, V, E extends InternalEntry<K, V, E>> boolean isCollected(E e) {
            if (e.getValue() != null) return false;
            return true;
        }

        abstract E castForTesting(InternalEntry<K, V, ?> var1);

        void clear() {
            if (this.count == 0) return;
            this.lock();
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            for (int i = 0; i < atomicReferenceArray.length(); ++i) {
                atomicReferenceArray.set(i, null);
            }
            this.maybeClearReferenceQueues();
            this.readCount.set(0);
            ++this.modCount;
            this.count = 0;
            return;
        }

        <T> void clearReferenceQueue(ReferenceQueue<T> referenceQueue) {
            while (referenceQueue.poll() != null) {
            }
        }

        boolean clearValueForTesting(K k, int n, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            this.lock();
            try {
                InternalEntry internalEntry;
                AtomicReferenceArray<InternalEntry> atomicReferenceArray = this.table;
                int n2 = atomicReferenceArray.length() - 1 & n;
                InternalEntry<K, V, E> internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n2);
                while (internalEntry2 != null) {
                    K k2 = internalEntry2.getKey();
                    if (internalEntry2.getHash() == n && k2 != null && this.map.keyEquivalence.equivalent(k, k2)) {
                        if (((WeakValueEntry)internalEntry2).getValueReference() != weakValueReference) return false;
                        atomicReferenceArray.set(n2, this.removeFromChain(internalEntry, internalEntry2));
                        return true;
                    }
                    internalEntry2 = internalEntry2.getNext();
                }
                return false;
            }
            finally {
                this.unlock();
            }
        }

        boolean containsKey(Object object, int n) {
            try {
                int n2 = this.count;
                boolean bl = false;
                if (n2 == 0) {
                    this.postReadCleanup();
                    return false;
                }
                object = this.getLiveEntry(object, n);
                boolean bl2 = bl;
                if (object != null) {
                    object = object.getValue();
                    bl2 = bl;
                    if (object != null) {
                        bl2 = true;
                    }
                }
                this.postReadCleanup();
                return bl2;
            }
            catch (Throwable throwable) {
                this.postReadCleanup();
                throw throwable;
            }
        }

        boolean containsValue(Object object) {
            try {
                if (this.count == 0) return false;
                AtomicReferenceArray<E> atomicReferenceArray = this.table;
                int n = atomicReferenceArray.length();
                int n2 = 0;
                while (n2 < n) {
                    for (InternalEntry<K, V, E> internalEntry = (InternalEntry)atomicReferenceArray.get((int)n2); internalEntry != null; internalEntry = internalEntry.getNext()) {
                        boolean bl;
                        V v = this.getLiveValue(internalEntry);
                        if (v == null || !(bl = this.map.valueEquivalence().equivalent(object, v))) continue;
                        this.postReadCleanup();
                        return true;
                    }
                    ++n2;
                }
                return false;
            }
            finally {
                this.postReadCleanup();
            }
        }

        E copyEntry(E e, E e2) {
            return this.map.entryHelper.copy(this.self(), e, e2);
        }

        E copyForTesting(InternalEntry<K, V, ?> internalEntry, @NullableDecl InternalEntry<K, V, ?> internalEntry2) {
            return this.map.entryHelper.copy(this.self(), this.castForTesting(internalEntry), this.castForTesting(internalEntry2));
        }

        void drainKeyReferenceQueue(ReferenceQueue<K> referenceQueue) {
            int n;
            int n2 = 0;
            do {
                Object object;
                if ((object = referenceQueue.poll()) == null) return;
                object = (InternalEntry)object;
                this.map.reclaimKey(object);
                n2 = n = n2 + 1;
            } while (n != 16);
        }

        void drainValueReferenceQueue(ReferenceQueue<V> referenceQueue) {
            int n;
            int n2 = 0;
            do {
                Object object;
                if ((object = referenceQueue.poll()) == null) return;
                object = (WeakValueReference)object;
                this.map.reclaimValue((WeakValueReference<K, V, E>)object);
                n2 = n = n2 + 1;
            } while (n != 16);
        }

        void expand() {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            int n = atomicReferenceArray.length();
            if (n >= 1073741824) {
                return;
            }
            int n2 = this.count;
            AtomicReferenceArray<InternalEntry<K, V, E>> atomicReferenceArray2 = this.newEntryArray(n << 1);
            this.threshold = atomicReferenceArray2.length() * 3 / 4;
            int n3 = atomicReferenceArray2.length() - 1;
            int n4 = 0;
            do {
                if (n4 >= n) {
                    this.table = atomicReferenceArray2;
                    this.count = n2;
                    return;
                }
                InternalEntry<K, V, E> internalEntry = (InternalEntry)atomicReferenceArray.get(n4);
                int n5 = n2;
                if (internalEntry != null) {
                    Object object;
                    int n6 = internalEntry.getHash() & n3;
                    if (object == null) {
                        atomicReferenceArray2.set(n6, internalEntry);
                        n5 = n2;
                    } else {
                        InternalEntry<K, V, E> internalEntry2 = internalEntry;
                        for (object = internalEntry.getNext(); object != null; object = object.getNext()) {
                            int n7 = object.getHash() & n3;
                            n5 = n6;
                            if (n7 != n6) {
                                internalEntry2 = object;
                                n5 = n7;
                            }
                            n6 = n5;
                        }
                        atomicReferenceArray2.set(n6, internalEntry2);
                        do {
                            n5 = n2;
                            if (internalEntry == internalEntry2) break;
                            n5 = internalEntry.getHash() & n3;
                            object = this.copyEntry(internalEntry, (InternalEntry)atomicReferenceArray2.get(n5));
                            if (object != null) {
                                atomicReferenceArray2.set(n5, object);
                            } else {
                                --n2;
                            }
                            internalEntry = internalEntry.getNext();
                        } while (true);
                    }
                }
                ++n4;
                n2 = n5;
            } while (true);
        }

        V get(Object object, int n) {
            block4 : {
                object = this.getLiveEntry(object, n);
                if (object != null) break block4;
                this.postReadCleanup();
                return null;
            }
            try {
                object = object.getValue();
                if (object != null) return (V)object;
                this.tryDrainReferenceQueues();
                return (V)object;
            }
            finally {
                this.postReadCleanup();
            }
        }

        E getEntry(Object object, int n) {
            if (this.count == 0) return null;
            E e = this.getFirst(n);
            while (e != null) {
                if (e.getHash() == n) {
                    K k = e.getKey();
                    if (k == null) {
                        this.tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(object, k)) {
                        return e;
                    }
                }
                e = e.getNext();
            }
            return null;
        }

        E getFirst(int n) {
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            return (E)((InternalEntry)atomicReferenceArray.get(n & atomicReferenceArray.length() - 1));
        }

        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            throw new AssertionError();
        }

        E getLiveEntry(Object object, int n) {
            return this.getEntry(object, n);
        }

        @NullableDecl
        V getLiveValue(E object) {
            if (object.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if ((object = object.getValue()) != null) return (V)object;
            this.tryDrainReferenceQueues();
            return null;
        }

        @NullableDecl
        V getLiveValueForTesting(InternalEntry<K, V, ?> internalEntry) {
            return this.getLiveValue(this.castForTesting(internalEntry));
        }

        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            throw new AssertionError();
        }

        WeakValueReference<K, V, E> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            throw new AssertionError();
        }

        void initTable(AtomicReferenceArray<E> atomicReferenceArray) {
            int n;
            this.threshold = n = atomicReferenceArray.length() * 3 / 4;
            if (n == this.maxSegmentSize) {
                this.threshold = n + 1;
            }
            this.table = atomicReferenceArray;
        }

        void maybeClearReferenceQueues() {
        }

        void maybeDrainReferenceQueues() {
        }

        AtomicReferenceArray<E> newEntryArray(int n) {
            return new AtomicReferenceArray<E>(n);
        }

        E newEntryForTesting(K k, int n, @NullableDecl InternalEntry<K, V, ?> internalEntry) {
            return this.map.entryHelper.newEntry(this.self(), k, n, this.castForTesting(internalEntry));
        }

        WeakValueReference<K, V, E> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            throw new AssertionError();
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) != 0) return;
            this.runCleanup();
        }

        void preWriteCleanup() {
            this.runLockedCleanup();
        }

        /*
         * Exception decompiling
         */
        V put(K var1_1, int var2_3, V var3_4, boolean var4_5) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[FORLOOP]], but top level block is 2[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
            // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
            throw new IllegalStateException("Decompilation failed");
        }

        boolean reclaimKey(E object, int n) {
            InternalEntry internalEntry;
            InternalEntry<K, V, E> internalEntry2;
            AtomicReferenceArray<E> atomicReferenceArray;
            this.lock();
            try {
                atomicReferenceArray = this.table;
                internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n &= atomicReferenceArray.length() - 1);
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                this.unlock();
            }
            do {
                if (internalEntry2 == null) {
                    this.unlock();
                    return false;
                }
                if (internalEntry2 == object) {
                    ++this.modCount;
                    object = this.removeFromChain(internalEntry, internalEntry2);
                    int n2 = this.count;
                    atomicReferenceArray.set(n, object);
                    this.count = n2 - 1;
                    return true;
                }
                internalEntry2 = internalEntry2.getNext();
                continue;
                break;
            } while (true);
        }

        boolean reclaimValue(K object, int n, WeakValueReference<K, V, E> weakValueReference) {
            this.lock();
            try {
                InternalEntry internalEntry;
                AtomicReferenceArray<K> atomicReferenceArray = this.table;
                int n2 = atomicReferenceArray.length() - 1 & n;
                InternalEntry<K, V, E> internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n2);
                while (internalEntry2 != null) {
                    K k = internalEntry2.getKey();
                    if (internalEntry2.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        if (((WeakValueEntry)internalEntry2).getValueReference() != weakValueReference) return false;
                        ++this.modCount;
                        object = this.removeFromChain(internalEntry, internalEntry2);
                        n = this.count;
                        atomicReferenceArray.set(n2, object);
                        this.count = n - 1;
                        return true;
                    }
                    internalEntry2 = internalEntry2.getNext();
                }
                return false;
            }
            finally {
                this.unlock();
            }
        }

        V remove(Object object, int n) {
            InternalEntry internalEntry;
            int n2;
            InternalEntry<K, V, E> internalEntry2;
            AtomicReferenceArray<InternalEntry<K, V, E>> atomicReferenceArray;
            block7 : {
                this.lock();
                this.preWriteCleanup();
                atomicReferenceArray = this.table;
                n2 = atomicReferenceArray.length() - 1 & n;
                internalEntry = (InternalEntry)atomicReferenceArray.get(n2);
                internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    K k = internalEntry2.getKey();
                    if (internalEntry2.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        object = internalEntry2.getValue();
                        if (object == null) {
                            if (!Segment.isCollected(internalEntry2)) return null;
                        }
                        break block7;
                    }
                    internalEntry2 = internalEntry2.getNext();
                }
                return null;
                finally {
                    this.unlock();
                }
            }
            ++this.modCount;
            internalEntry2 = this.removeFromChain(internalEntry, internalEntry2);
            n = this.count;
            atomicReferenceArray.set(n2, internalEntry2);
            this.count = n - 1;
            return (V)object;
        }

        boolean remove(Object internalEntry, int n, Object object) {
            boolean bl;
            AtomicReferenceArray<InternalEntry<K, V, E>> atomicReferenceArray;
            InternalEntry internalEntry2;
            InternalEntry<K, V, E> internalEntry3;
            int n2;
            block8 : {
                this.lock();
                this.preWriteCleanup();
                atomicReferenceArray = this.table;
                n2 = atomicReferenceArray.length() - 1 & n;
                internalEntry2 = (InternalEntry)atomicReferenceArray.get(n2);
                internalEntry3 = internalEntry2;
                do {
                    bl = false;
                    if (internalEntry3 == null) return false;
                    K k = internalEntry3.getKey();
                    if (internalEntry3.getHash() == n && k != null && this.map.keyEquivalence.equivalent(internalEntry, k)) {
                        internalEntry = internalEntry3.getValue();
                        if (this.map.valueEquivalence().equivalent(object, internalEntry)) {
                            bl = true;
                        } else if (!Segment.isCollected(internalEntry3)) return false;
                        break block8;
                    }
                    internalEntry3 = internalEntry3.getNext();
                } while (true);
                finally {
                    this.unlock();
                }
            }
            ++this.modCount;
            internalEntry = this.removeFromChain(internalEntry2, internalEntry3);
            n = this.count;
            atomicReferenceArray.set(n2, internalEntry);
            this.count = n - 1;
            return bl;
        }

        boolean removeEntryForTesting(E object) {
            InternalEntry internalEntry;
            int n = object.getHash();
            AtomicReferenceArray<E> atomicReferenceArray = this.table;
            InternalEntry<K, V, E> internalEntry2 = internalEntry = (InternalEntry)atomicReferenceArray.get(n &= atomicReferenceArray.length() - 1);
            while (internalEntry2 != null) {
                if (internalEntry2 == object) {
                    ++this.modCount;
                    object = this.removeFromChain(internalEntry, internalEntry2);
                    int n2 = this.count;
                    atomicReferenceArray.set(n, object);
                    this.count = n2 - 1;
                    return true;
                }
                internalEntry2 = internalEntry2.getNext();
            }
            return false;
        }

        E removeFromChain(E e, E e2) {
            int n = this.count;
            E e3 = e2.getNext();
            do {
                if (e == e2) {
                    this.count = n;
                    return e3;
                }
                E e4 = this.copyEntry(e, e3);
                if (e4 != null) {
                    e3 = e4;
                } else {
                    --n;
                }
                e = e.getNext();
            } while (true);
        }

        E removeFromChainForTesting(InternalEntry<K, V, ?> internalEntry, InternalEntry<K, V, ?> internalEntry2) {
            return this.removeFromChain(this.castForTesting(internalEntry), this.castForTesting(internalEntry2));
        }

        boolean removeTableEntryForTesting(InternalEntry<K, V, ?> internalEntry) {
            return this.removeEntryForTesting(this.castForTesting(internalEntry));
        }

        V replace(K object, int n, V v) {
            InternalEntry<K, V, E> internalEntry;
            block8 : {
                this.lock();
                this.preWriteCleanup();
                AtomicReferenceArray<K> atomicReferenceArray = this.table;
                int n2 = atomicReferenceArray.length() - 1 & n;
                InternalEntry internalEntry2 = (InternalEntry)atomicReferenceArray.get(n2);
                internalEntry = internalEntry2;
                while (internalEntry != null) {
                    K k = internalEntry.getKey();
                    if (internalEntry.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        object = internalEntry.getValue();
                        if (object == null) {
                            if (!Segment.isCollected(internalEntry)) return null;
                            ++this.modCount;
                            object = this.removeFromChain(internalEntry2, internalEntry);
                            n = this.count;
                            atomicReferenceArray.set(n2, object);
                            this.count = n - 1;
                            return null;
                        }
                        break block8;
                    }
                    internalEntry = internalEntry.getNext();
                }
                return null;
                finally {
                    this.unlock();
                }
            }
            ++this.modCount;
            this.setValue(internalEntry, v);
            return (V)object;
        }

        boolean replace(K object, int n, V v, V v2) {
            InternalEntry<K, V, E> internalEntry;
            block8 : {
                this.lock();
                this.preWriteCleanup();
                AtomicReferenceArray<K> atomicReferenceArray = this.table;
                int n2 = atomicReferenceArray.length() - 1 & n;
                InternalEntry internalEntry2 = (InternalEntry)atomicReferenceArray.get(n2);
                internalEntry = internalEntry2;
                while (internalEntry != null) {
                    K k = internalEntry.getKey();
                    if (internalEntry.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        object = internalEntry.getValue();
                        if (object == null) {
                            if (!Segment.isCollected(internalEntry)) return false;
                            ++this.modCount;
                            object = this.removeFromChain(internalEntry2, internalEntry);
                            n = this.count;
                            atomicReferenceArray.set(n2, object);
                            this.count = n - 1;
                            return false;
                        }
                        break block8;
                    }
                    internalEntry = internalEntry.getNext();
                }
                return false;
                finally {
                    this.unlock();
                }
            }
            if (!this.map.valueEquivalence().equivalent(v, object)) return false;
            ++this.modCount;
            this.setValue(internalEntry, v2);
            return true;
        }

        void runCleanup() {
            this.runLockedCleanup();
        }

        void runLockedCleanup() {
            if (!this.tryLock()) return;
            try {
                this.maybeDrainReferenceQueues();
                this.readCount.set(0);
                return;
            }
            finally {
                this.unlock();
            }
        }

        abstract S self();

        void setTableEntryForTesting(int n, InternalEntry<K, V, ?> internalEntry) {
            this.table.set(n, this.castForTesting(internalEntry));
        }

        void setValue(E e, V v) {
            this.map.entryHelper.setValue(this.self(), e, v);
        }

        void setValueForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            this.map.entryHelper.setValue(this.self(), this.castForTesting(internalEntry), v);
        }

        void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            throw new AssertionError();
        }

        void tryDrainReferenceQueues() {
            if (!this.tryLock()) return;
            try {
                this.maybeDrainReferenceQueues();
                return;
            }
            finally {
                this.unlock();
            }
        }
    }

    private static final class SerializationProxy<K, V>
    extends AbstractSerializationProxy<K, V> {
        private static final long serialVersionUID = 3L;

        SerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int n, ConcurrentMap<K, V> concurrentMap) {
            super(strength, strength2, equivalence, equivalence2, n, concurrentMap);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.delegate = this.readMapMaker(objectInputStream).makeMap();
            this.readEntries(objectInputStream);
        }

        private Object readResolve() {
            return this.delegate;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            this.writeMapTo(objectOutputStream);
        }
    }

    static abstract class Strength
    extends Enum<Strength> {
        private static final /* synthetic */ Strength[] $VALUES;
        public static final /* enum */ Strength STRONG;
        public static final /* enum */ Strength WEAK;

        static {
            Strength strength;
            STRONG = new Strength(){

                @Override
                Equivalence<Object> defaultEquivalence() {
                    return Equivalence.equals();
                }
            };
            WEAK = strength = new Strength(){

                @Override
                Equivalence<Object> defaultEquivalence() {
                    return Equivalence.identity();
                }
            };
            $VALUES = new Strength[]{STRONG, strength};
        }

        public static Strength valueOf(String string2) {
            return Enum.valueOf(Strength.class, string2);
        }

        public static Strength[] values() {
            return (Strength[])$VALUES.clone();
        }

        abstract Equivalence<Object> defaultEquivalence();

    }

    static final class StrongKeyDummyValueEntry<K>
    extends AbstractStrongKeyEntry<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>>
    implements StrongValueEntry<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>> {
        StrongKeyDummyValueEntry(K k, int n, @NullableDecl StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry) {
            super(k, n, strongKeyDummyValueEntry);
        }

        StrongKeyDummyValueEntry<K> copy(StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry) {
            return new StrongKeyDummyValueEntry<Object>(this.key, this.hash, strongKeyDummyValueEntry);
        }

        @Override
        public MapMaker.Dummy getValue() {
            return MapMaker.Dummy.VALUE;
        }

        void setValue(MapMaker.Dummy dummy) {
        }

        static final class Helper<K>
        implements InternalEntryHelper<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> {
            private static final Helper<?> INSTANCE = new Helper<K>();

            Helper() {
            }

            static <K> Helper<K> instance() {
                return INSTANCE;
            }

            @Override
            public StrongKeyDummyValueEntry<K> copy(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry, @NullableDecl StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry2) {
                return strongKeyDummyValueEntry.copy(strongKeyDummyValueEntry2);
            }

            @Override
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            @Override
            public StrongKeyDummyValueEntry<K> newEntry(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, K k, int n, @NullableDecl StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry) {
                return new StrongKeyDummyValueEntry<K>(k, n, strongKeyDummyValueEntry);
            }

            @Override
            public StrongKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> mapMakerInternalMap, int n, int n2) {
                return new StrongKeyDummyValueSegment<K>(mapMakerInternalMap, n, n2);
            }

            @Override
            public void setValue(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry, MapMaker.Dummy dummy) {
            }

            @Override
            public Strength valueStrength() {
                return Strength.STRONG;
            }
        }

    }

    static final class StrongKeyDummyValueSegment<K>
    extends Segment<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> {
        StrongKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        public StrongKeyDummyValueEntry<K> castForTesting(InternalEntry<K, MapMaker.Dummy, ?> internalEntry) {
            return (StrongKeyDummyValueEntry)internalEntry;
        }

        @Override
        StrongKeyDummyValueSegment<K> self() {
            return this;
        }
    }

    static final class StrongKeyStrongValueEntry<K, V>
    extends AbstractStrongKeyEntry<K, V, StrongKeyStrongValueEntry<K, V>>
    implements StrongValueEntry<K, V, StrongKeyStrongValueEntry<K, V>> {
        @NullableDecl
        private volatile V value = null;

        StrongKeyStrongValueEntry(K k, int n, @NullableDecl StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            super(k, n, strongKeyStrongValueEntry);
        }

        StrongKeyStrongValueEntry<K, V> copy(StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            strongKeyStrongValueEntry = new StrongKeyStrongValueEntry<Object, V>(this.key, this.hash, strongKeyStrongValueEntry);
            strongKeyStrongValueEntry.value = this.value;
            return strongKeyStrongValueEntry;
        }

        @NullableDecl
        @Override
        public V getValue() {
            return this.value;
        }

        void setValue(V v) {
            this.value = v;
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<K, V>();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public StrongKeyStrongValueEntry<K, V> copy(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, @NullableDecl StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry2) {
                return strongKeyStrongValueEntry.copy(strongKeyStrongValueEntry2);
            }

            @Override
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            @Override
            public StrongKeyStrongValueEntry<K, V> newEntry(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, K k, int n, @NullableDecl StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
                return new StrongKeyStrongValueEntry<K, V>(k, n, strongKeyStrongValueEntry);
            }

            @Override
            public StrongKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new StrongKeyStrongValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public void setValue(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, V v) {
                strongKeyStrongValueEntry.setValue(v);
            }

            @Override
            public Strength valueStrength() {
                return Strength.STRONG;
            }
        }

    }

    static final class StrongKeyStrongValueSegment<K, V>
    extends Segment<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
        StrongKeyStrongValueSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        public StrongKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyStrongValueEntry)internalEntry;
        }

        @Override
        StrongKeyStrongValueSegment<K, V> self() {
            return this;
        }
    }

    static final class StrongKeyWeakValueEntry<K, V>
    extends AbstractStrongKeyEntry<K, V, StrongKeyWeakValueEntry<K, V>>
    implements WeakValueEntry<K, V, StrongKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> valueReference = MapMakerInternalMap.unsetWeakValueReference();

        StrongKeyWeakValueEntry(K k, int n, @NullableDecl StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            super(k, n, strongKeyWeakValueEntry);
        }

        @Override
        public void clearValue() {
            this.valueReference.clear();
        }

        StrongKeyWeakValueEntry<K, V> copy(ReferenceQueue<V> referenceQueue, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            strongKeyWeakValueEntry = new StrongKeyWeakValueEntry<Object, V>(this.key, this.hash, strongKeyWeakValueEntry);
            strongKeyWeakValueEntry.valueReference = this.valueReference.copyFor(referenceQueue, strongKeyWeakValueEntry);
            return strongKeyWeakValueEntry;
        }

        @Override
        public V getValue() {
            return this.valueReference.get();
        }

        @Override
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getValueReference() {
            return this.valueReference;
        }

        void setValue(V v, ReferenceQueue<V> referenceQueue) {
            WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> weakValueReference = this.valueReference;
            this.valueReference = new WeakValueReferenceImpl(referenceQueue, v, this);
            weakValueReference.clear();
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<K, V>();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public StrongKeyWeakValueEntry<K, V> copy(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, @NullableDecl StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry2) {
                if (!Segment.isCollected(strongKeyWeakValueEntry)) return strongKeyWeakValueEntry.copy(strongKeyWeakValueSegment.queueForValues, strongKeyWeakValueEntry2);
                return null;
            }

            @Override
            public Strength keyStrength() {
                return Strength.STRONG;
            }

            @Override
            public StrongKeyWeakValueEntry<K, V> newEntry(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, K k, int n, @NullableDecl StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
                return new StrongKeyWeakValueEntry<K, V>(k, n, strongKeyWeakValueEntry);
            }

            @Override
            public StrongKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new StrongKeyWeakValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public void setValue(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, V v) {
                strongKeyWeakValueEntry.setValue(v, strongKeyWeakValueSegment.queueForValues);
            }

            @Override
            public Strength valueStrength() {
                return Strength.WEAK;
            }
        }

    }

    static final class StrongKeyWeakValueSegment<K, V>
    extends Segment<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
        private final ReferenceQueue<V> queueForValues = new ReferenceQueue();

        StrongKeyWeakValueSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        public StrongKeyWeakValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyWeakValueEntry)internalEntry;
        }

        @Override
        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            return this.queueForValues;
        }

        @Override
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return ((StrongKeyWeakValueEntry)this.castForTesting((InternalEntry)internalEntry)).getValueReference();
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForValues);
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainValueReferenceQueue(this.queueForValues);
        }

        @Override
        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            return new WeakValueReferenceImpl(this.queueForValues, v, this.castForTesting((InternalEntry)internalEntry));
        }

        @Override
        StrongKeyWeakValueSegment<K, V> self() {
            return this;
        }

        @Override
        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> object, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            InternalEntry internalEntry = this.castForTesting((InternalEntry)object);
            object = ((StrongKeyWeakValueEntry)internalEntry).valueReference;
            ((StrongKeyWeakValueEntry)internalEntry).valueReference = weakValueReference;
            object.clear();
        }
    }

    static interface StrongValueEntry<K, V, E extends InternalEntry<K, V, E>>
    extends InternalEntry<K, V, E> {
    }

    final class ValueIterator
    extends MapMakerInternalMap<K, V, E, S> {
        ValueIterator() {
        }

        public V next() {
            return this.nextEntry().getValue();
        }
    }

    final class Values
    extends AbstractCollection<V> {
        Values() {
        }

        @Override
        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return MapMakerInternalMap.this.containsValue(object);
        }

        @Override
        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return MapMakerInternalMap.this.size();
        }

        @Override
        public Object[] toArray() {
            return MapMakerInternalMap.toArrayList(this).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return MapMakerInternalMap.toArrayList(this).toArray(arrT);
        }
    }

    static final class WeakKeyDummyValueEntry<K>
    extends AbstractWeakKeyEntry<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>>
    implements StrongValueEntry<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>> {
        WeakKeyDummyValueEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
            super(referenceQueue, k, n, weakKeyDummyValueEntry);
        }

        WeakKeyDummyValueEntry<K> copy(ReferenceQueue<K> referenceQueue, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
            return new WeakKeyDummyValueEntry<K>(referenceQueue, this.getKey(), this.hash, weakKeyDummyValueEntry);
        }

        @Override
        public MapMaker.Dummy getValue() {
            return MapMaker.Dummy.VALUE;
        }

        void setValue(MapMaker.Dummy dummy) {
        }

        static final class Helper<K>
        implements InternalEntryHelper<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> {
            private static final Helper<?> INSTANCE = new Helper<K>();

            Helper() {
            }

            static <K> Helper<K> instance() {
                return INSTANCE;
            }

            @Override
            public WeakKeyDummyValueEntry<K> copy(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry, @NullableDecl WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry2) {
                if (weakKeyDummyValueEntry.getKey() != null) return weakKeyDummyValueEntry.copy(weakKeyDummyValueSegment.queueForKeys, weakKeyDummyValueEntry2);
                return null;
            }

            @Override
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            @Override
            public WeakKeyDummyValueEntry<K> newEntry(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, K k, int n, @NullableDecl WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
                return new WeakKeyDummyValueEntry<K>(weakKeyDummyValueSegment.queueForKeys, k, n, weakKeyDummyValueEntry);
            }

            @Override
            public WeakKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> mapMakerInternalMap, int n, int n2) {
                return new WeakKeyDummyValueSegment<K>(mapMakerInternalMap, n, n2);
            }

            @Override
            public void setValue(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry, MapMaker.Dummy dummy) {
            }

            @Override
            public Strength valueStrength() {
                return Strength.STRONG;
            }
        }

    }

    static final class WeakKeyDummyValueSegment<K>
    extends Segment<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();

        WeakKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        public WeakKeyDummyValueEntry<K> castForTesting(InternalEntry<K, MapMaker.Dummy, ?> internalEntry) {
            return (WeakKeyDummyValueEntry)internalEntry;
        }

        @Override
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForKeys);
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainKeyReferenceQueue(this.queueForKeys);
        }

        @Override
        WeakKeyDummyValueSegment<K> self() {
            return this;
        }
    }

    static final class WeakKeyStrongValueEntry<K, V>
    extends AbstractWeakKeyEntry<K, V, WeakKeyStrongValueEntry<K, V>>
    implements StrongValueEntry<K, V, WeakKeyStrongValueEntry<K, V>> {
        @NullableDecl
        private volatile V value = null;

        WeakKeyStrongValueEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            super(referenceQueue, k, n, weakKeyStrongValueEntry);
        }

        WeakKeyStrongValueEntry<K, V> copy(ReferenceQueue<K> object, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            object = new WeakKeyStrongValueEntry<K, V>((ReferenceQueue<K>)object, this.getKey(), this.hash, weakKeyStrongValueEntry);
            ((WeakKeyStrongValueEntry)object).setValue(this.value);
            return object;
        }

        @NullableDecl
        @Override
        public V getValue() {
            return this.value;
        }

        void setValue(V v) {
            this.value = v;
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<K, V>();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public WeakKeyStrongValueEntry<K, V> copy(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, @NullableDecl WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2) {
                if (weakKeyStrongValueEntry.getKey() != null) return weakKeyStrongValueEntry.copy(weakKeyStrongValueSegment.queueForKeys, weakKeyStrongValueEntry2);
                return null;
            }

            @Override
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            @Override
            public WeakKeyStrongValueEntry<K, V> newEntry(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, K k, int n, @NullableDecl WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
                return new WeakKeyStrongValueEntry<K, V>(weakKeyStrongValueSegment.queueForKeys, k, n, weakKeyStrongValueEntry);
            }

            @Override
            public WeakKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new WeakKeyStrongValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public void setValue(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, V v) {
                weakKeyStrongValueEntry.setValue(v);
            }

            @Override
            public Strength valueStrength() {
                return Strength.STRONG;
            }
        }

    }

    static final class WeakKeyStrongValueSegment<K, V>
    extends Segment<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();

        WeakKeyStrongValueSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        public WeakKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyStrongValueEntry)internalEntry;
        }

        @Override
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForKeys);
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainKeyReferenceQueue(this.queueForKeys);
        }

        @Override
        WeakKeyStrongValueSegment<K, V> self() {
            return this;
        }
    }

    static final class WeakKeyWeakValueEntry<K, V>
    extends AbstractWeakKeyEntry<K, V, WeakKeyWeakValueEntry<K, V>>
    implements WeakValueEntry<K, V, WeakKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> valueReference = MapMakerInternalMap.unsetWeakValueReference();

        WeakKeyWeakValueEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            super(referenceQueue, k, n, weakKeyWeakValueEntry);
        }

        @Override
        public void clearValue() {
            this.valueReference.clear();
        }

        WeakKeyWeakValueEntry<K, V> copy(ReferenceQueue<K> object, ReferenceQueue<V> referenceQueue, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            object = new WeakKeyWeakValueEntry<K, V>((ReferenceQueue<K>)object, this.getKey(), this.hash, weakKeyWeakValueEntry);
            ((WeakKeyWeakValueEntry)object).valueReference = this.valueReference.copyFor(referenceQueue, (WeakKeyWeakValueEntry<K, V>)object);
            return object;
        }

        @Override
        public V getValue() {
            return this.valueReference.get();
        }

        @Override
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getValueReference() {
            return this.valueReference;
        }

        void setValue(V v, ReferenceQueue<V> referenceQueue) {
            WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> weakValueReference = this.valueReference;
            this.valueReference = new WeakValueReferenceImpl(referenceQueue, v, this);
            weakValueReference.clear();
        }

        static final class Helper<K, V>
        implements InternalEntryHelper<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> INSTANCE = new Helper<K, V>();

            Helper() {
            }

            static <K, V> Helper<K, V> instance() {
                return INSTANCE;
            }

            @Override
            public WeakKeyWeakValueEntry<K, V> copy(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, @NullableDecl WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2) {
                if (weakKeyWeakValueEntry.getKey() == null) {
                    return null;
                }
                if (!Segment.isCollected(weakKeyWeakValueEntry)) return weakKeyWeakValueEntry.copy(weakKeyWeakValueSegment.queueForKeys, weakKeyWeakValueSegment.queueForValues, weakKeyWeakValueEntry2);
                return null;
            }

            @Override
            public Strength keyStrength() {
                return Strength.WEAK;
            }

            @Override
            public WeakKeyWeakValueEntry<K, V> newEntry(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, K k, int n, @NullableDecl WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
                return new WeakKeyWeakValueEntry<K, V>(weakKeyWeakValueSegment.queueForKeys, k, n, weakKeyWeakValueEntry);
            }

            @Override
            public WeakKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
                return new WeakKeyWeakValueSegment<K, V>(mapMakerInternalMap, n, n2);
            }

            @Override
            public void setValue(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, V v) {
                weakKeyWeakValueEntry.setValue(v, weakKeyWeakValueSegment.queueForValues);
            }

            @Override
            public Strength valueStrength() {
                return Strength.WEAK;
            }
        }

    }

    static final class WeakKeyWeakValueSegment<K, V>
    extends Segment<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
        private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();
        private final ReferenceQueue<V> queueForValues = new ReferenceQueue();

        WeakKeyWeakValueSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int n, int n2) {
            super(mapMakerInternalMap, n, n2);
        }

        @Override
        public WeakKeyWeakValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyWeakValueEntry)internalEntry;
        }

        @Override
        ReferenceQueue<K> getKeyReferenceQueueForTesting() {
            return this.queueForKeys;
        }

        @Override
        ReferenceQueue<V> getValueReferenceQueueForTesting() {
            return this.queueForValues;
        }

        @Override
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return ((WeakKeyWeakValueEntry)this.castForTesting((InternalEntry)internalEntry)).getValueReference();
        }

        @Override
        void maybeClearReferenceQueues() {
            this.clearReferenceQueue(this.queueForKeys);
        }

        @Override
        void maybeDrainReferenceQueues() {
            this.drainKeyReferenceQueue(this.queueForKeys);
            this.drainValueReferenceQueue(this.queueForValues);
        }

        @Override
        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            return new WeakValueReferenceImpl(this.queueForValues, v, this.castForTesting((InternalEntry)internalEntry));
        }

        @Override
        WeakKeyWeakValueSegment<K, V> self() {
            return this;
        }

        @Override
        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            internalEntry = this.castForTesting(internalEntry);
            WeakValueReference weakValueReference2 = ((WeakKeyWeakValueEntry)internalEntry).valueReference;
            ((WeakKeyWeakValueEntry)internalEntry).valueReference = weakValueReference;
            weakValueReference2.clear();
        }
    }

    static interface WeakValueEntry<K, V, E extends InternalEntry<K, V, E>>
    extends InternalEntry<K, V, E> {
        public void clearValue();

        public WeakValueReference<K, V, E> getValueReference();
    }

    static interface WeakValueReference<K, V, E extends InternalEntry<K, V, E>> {
        public void clear();

        public WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> var1, E var2);

        @NullableDecl
        public V get();

        public E getEntry();
    }

    static final class WeakValueReferenceImpl<K, V, E extends InternalEntry<K, V, E>>
    extends WeakReference<V>
    implements WeakValueReference<K, V, E> {
        final E entry;

        WeakValueReferenceImpl(ReferenceQueue<V> referenceQueue, V v, E e) {
            super(v, referenceQueue);
            this.entry = e;
        }

        @Override
        public WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> referenceQueue, E e) {
            return new WeakValueReferenceImpl<K, V, E>(referenceQueue, this.get(), e);
        }

        @Override
        public E getEntry() {
            return this.entry;
        }
    }

    final class WriteThroughEntry
    extends AbstractMapEntry<K, V> {
        final K key;
        V value;

        WriteThroughEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof Map.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (Map.Entry)object;
            bl3 = bl;
            if (!this.key.equals(object.getKey())) return bl3;
            bl3 = bl;
            if (!this.value.equals(object.getValue())) return bl3;
            return true;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        @Override
        public V setValue(V v) {
            V v2 = MapMakerInternalMap.this.put(this.key, v);
            this.value = v;
            return v2;
        }
    }

}


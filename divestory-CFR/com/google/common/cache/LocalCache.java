/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.LocalCache.AbstractCacheSet
 *  com.google.common.cache.LocalCache.HashIterator
 */
package com.google.common.cache;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.ForwardingCache;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.ReferenceEntry;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class LocalCache<K, V>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V> {
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final Queue<?> DISCARDING_QUEUE;
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final ValueReference<Object, Object> UNSET;
    static final Logger logger;
    final int concurrencyLevel;
    @NullableDecl
    final CacheLoader<? super K, V> defaultLoader;
    final EntryFactory entryFactory;
    @MonotonicNonNullDecl
    Set<Map.Entry<K, V>> entrySet;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final AbstractCache.StatsCounter globalStatsCounter;
    final Equivalence<Object> keyEquivalence;
    @MonotonicNonNullDecl
    Set<K> keySet;
    final Strength keyStrength;
    final long maxWeight;
    final long refreshNanos;
    final RemovalListener<K, V> removalListener;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final Strength valueStrength;
    @MonotonicNonNullDecl
    Collection<V> values;
    final Weigher<K, V> weigher;

    static {
        logger = Logger.getLogger(LocalCache.class.getName());
        UNSET = new ValueReference<Object, Object>(){

            @Override
            public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @NullableDecl Object object, ReferenceEntry<Object, Object> referenceEntry) {
                return this;
            }

            @Override
            public Object get() {
                return null;
            }

            @Override
            public ReferenceEntry<Object, Object> getEntry() {
                return null;
            }

            @Override
            public int getWeight() {
                return 0;
            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public void notifyNewValue(Object object) {
            }

            @Override
            public Object waitForValue() {
                return null;
            }
        };
        DISCARDING_QUEUE = new AbstractQueue<Object>(){

            @Override
            public Iterator<Object> iterator() {
                return ImmutableSet.of().iterator();
            }

            @Override
            public boolean offer(Object object) {
                return true;
            }

            @Override
            public Object peek() {
                return null;
            }

            @Override
            public Object poll() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };
    }

    LocalCache(CacheBuilder<? super K, ? super V> cacheBuilder, @NullableDecl CacheLoader<? super K, V> arrsegment) {
        int n;
        this.concurrencyLevel = Math.min(cacheBuilder.getConcurrencyLevel(), 65536);
        this.keyStrength = cacheBuilder.getKeyStrength();
        this.valueStrength = cacheBuilder.getValueStrength();
        this.keyEquivalence = cacheBuilder.getKeyEquivalence();
        this.valueEquivalence = cacheBuilder.getValueEquivalence();
        this.maxWeight = cacheBuilder.getMaximumWeight();
        this.weigher = cacheBuilder.getWeigher();
        this.expireAfterAccessNanos = cacheBuilder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = cacheBuilder.getExpireAfterWriteNanos();
        this.refreshNanos = cacheBuilder.getRefreshNanos();
        Queue<RemovalNotification<K, V>> queue = cacheBuilder.getRemovalListener();
        this.removalListener = queue;
        queue = queue == CacheBuilder.NullListener.INSTANCE ? LocalCache.discardingQueue() : new ConcurrentLinkedQueue<RemovalNotification<K, V>>();
        this.removalNotificationQueue = queue;
        this.ticker = cacheBuilder.getTicker(this.recordsTime());
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
        this.globalStatsCounter = cacheBuilder.getStatsCounterSupplier().get();
        this.defaultLoader = arrsegment;
        int n2 = n = Math.min(cacheBuilder.getInitialCapacity(), 1073741824);
        if (this.evictsBySize()) {
            n2 = n;
            if (!this.customWeigher()) {
                n2 = (int)Math.min((long)n, this.maxWeight);
            }
        }
        int n3 = 0;
        int n4 = 0;
        int n5 = 1;
        int n6 = 0;
        for (n = 1; !(n >= this.concurrencyLevel || this.evictsBySize() && (long)(n * 20) > this.maxWeight); ++n6, n <<= 1) {
        }
        this.segmentShift = 32 - n6;
        this.segmentMask = n - 1;
        this.segments = this.newSegmentArray(n);
        int n7 = n2 / n;
        n6 = n5;
        int n8 = n7;
        if (n7 * n < n2) {
            n8 = n7 + 1;
            n6 = n5;
        }
        while (n6 < n8) {
            n6 <<= 1;
        }
        n2 = n3;
        if (!this.evictsBySize()) {
            while (n2 < (arrsegment = this.segments).length) {
                arrsegment[n2] = this.createSegment(n6, -1L, cacheBuilder.getStatsCounterSupplier().get());
                ++n2;
            }
            return;
        }
        long l = this.maxWeight;
        long l2 = n;
        long l3 = l / l2 + 1L;
        n2 = n4;
        while (n2 < this.segments.length) {
            long l4 = l3;
            if ((long)n2 == l % l2) {
                l4 = l3 - 1L;
            }
            this.segments[n2] = this.createSegment(n6, l4, cacheBuilder.getStatsCounterSupplier().get());
            ++n2;
            l3 = l4;
        }
    }

    static <K, V> void connectAccessOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry2.setPreviousInAccessQueue(referenceEntry);
    }

    static <K, V> void connectWriteOrder(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry2.setPreviousInWriteQueue(referenceEntry);
    }

    static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry<K, V> referenceEntry2 = LocalCache.nullEntry();
        referenceEntry.setNextInAccessQueue(referenceEntry2);
        referenceEntry.setPreviousInAccessQueue(referenceEntry2);
    }

    static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> referenceEntry) {
        ReferenceEntry<K, V> referenceEntry2 = LocalCache.nullEntry();
        referenceEntry.setNextInWriteQueue(referenceEntry2);
        referenceEntry.setPreviousInWriteQueue(referenceEntry2);
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

    static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    public void cleanUp() {
        Segment<K, V>[] arrsegment = this.segments;
        int n = arrsegment.length;
        int n2 = 0;
        while (n2 < n) {
            arrsegment[n2].cleanUp();
            ++n2;
        }
    }

    @Override
    public void clear() {
        Segment<K, V>[] arrsegment = this.segments;
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

    ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
        return this.segmentFor(referenceEntry.getHash()).copyEntry(referenceEntry, referenceEntry2);
    }

    Segment<K, V> createSegment(int n, long l, AbstractCache.StatsCounter statsCounter) {
        return new Segment(this, n, l, statsCounter);
    }

    boolean customWeigher() {
        if (this.weigher == CacheBuilder.OneWeigher.INSTANCE) return false;
        return true;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Object object = this.entrySet;
        if (object != null) {
            return object;
        }
        this.entrySet = object = new EntrySet(this);
        return object;
    }

    boolean evictsBySize() {
        if (this.maxWeight < 0L) return false;
        return true;
    }

    boolean expires() {
        if (this.expiresAfterWrite()) return true;
        if (this.expiresAfterAccess()) return true;
        return false;
    }

    boolean expiresAfterAccess() {
        if (this.expireAfterAccessNanos <= 0L) return false;
        return true;
    }

    boolean expiresAfterWrite() {
        if (this.expireAfterWriteNanos <= 0L) return false;
        return true;
    }

    @NullableDecl
    @Override
    public V get(@NullableDecl Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).get(object, n);
    }

    V get(K k, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
        int n = this.hash(Preconditions.checkNotNull(k));
        return this.segmentFor(n).get((K)k, n, cacheLoader);
    }

    /*
     * Exception decompiling
     */
    ImmutableMap<K, V> getAll(Iterable<? extends K> var1_1) throws ExecutionException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[UNCONDITIONALDOLOOP]], but top level block is 2[TRYBLOCK]
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

    ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        LinkedHashMap<Iterable<?>, V> linkedHashMap = Maps.newLinkedHashMap();
        Iterator<?> iterator2 = iterable.iterator();
        int n = 0;
        int n2 = 0;
        do {
            if (!iterator2.hasNext()) {
                this.globalStatsCounter.recordHits(n);
                this.globalStatsCounter.recordMisses(n2);
                return ImmutableMap.copyOf(linkedHashMap);
            }
            iterable = iterator2.next();
            V v = this.get(iterable);
            if (v == null) {
                ++n2;
                continue;
            }
            linkedHashMap.put(iterable, v);
            ++n;
        } while (true);
    }

    ReferenceEntry<K, V> getEntry(@NullableDecl Object object) {
        if (object == null) {
            return null;
        }
        int n = this.hash(object);
        return this.segmentFor(n).getEntry(object, n);
    }

    @NullableDecl
    public V getIfPresent(Object object) {
        int n = this.hash(Preconditions.checkNotNull(object));
        object = this.segmentFor(n).get(object, n);
        if (object == null) {
            this.globalStatsCounter.recordMisses(1);
            return (V)object;
        }
        this.globalStatsCounter.recordHits(1);
        return (V)object;
    }

    @NullableDecl
    V getLiveValue(ReferenceEntry<K, V> referenceEntry, long l) {
        if (referenceEntry.getKey() == null) {
            return null;
        }
        V v = referenceEntry.getValueReference().get();
        if (v == null) {
            return null;
        }
        if (!this.isExpired(referenceEntry, l)) return v;
        return null;
    }

    @NullableDecl
    @Override
    public V getOrDefault(@NullableDecl Object object, @NullableDecl V object2) {
        if ((object = this.get(object)) == null) return object2;
        object2 = object;
        return object2;
    }

    V getOrLoad(K k) throws ExecutionException {
        return this.get(k, this.defaultLoader);
    }

    int hash(@NullableDecl Object object) {
        return LocalCache.rehash(this.keyEquivalence.hash(object));
    }

    void invalidateAll(Iterable<?> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.remove(object.next());
        }
    }

    @Override
    public boolean isEmpty() {
        int n;
        Segment<K, V>[] arrsegment = this.segments;
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

    boolean isExpired(ReferenceEntry<K, V> referenceEntry, long l) {
        Preconditions.checkNotNull(referenceEntry);
        if (this.expiresAfterAccess() && l - referenceEntry.getAccessTime() >= this.expireAfterAccessNanos) {
            return true;
        }
        if (!this.expiresAfterWrite()) return false;
        if (l - referenceEntry.getWriteTime() < this.expireAfterWriteNanos) return false;
        return true;
    }

    boolean isLive(ReferenceEntry<K, V> referenceEntry, long l) {
        if (this.segmentFor(referenceEntry.getHash()).getLiveValue(referenceEntry, l) == null) return false;
        return true;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set != null) {
            return set;
        }
        this.keySet = set = new KeySet(this);
        return set;
    }

    /*
     * Exception decompiling
     */
    @NullableDecl
    Map<K, V> loadAll(Set<? extends K> var1_1, CacheLoader<? super K, V> var2_9) throws ExecutionException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    long longSize() {
        Segment<K, V>[] arrsegment = this.segments;
        long l = 0L;
        int n = 0;
        while (n < arrsegment.length) {
            l += (long)Math.max(0, arrsegment[n].count);
            ++n;
        }
        return l;
    }

    ReferenceEntry<K, V> newEntry(K object, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
        Segment<K, V> segment = this.segmentFor(n);
        segment.lock();
        try {
            object = segment.newEntry(object, n, referenceEntry);
            return object;
        }
        finally {
            segment.unlock();
        }
    }

    final Segment<K, V>[] newSegmentArray(int n) {
        return new Segment[n];
    }

    ValueReference<K, V> newValueReference(ReferenceEntry<K, V> referenceEntry, V v, int n) {
        int n2 = referenceEntry.getHash();
        return this.valueStrength.referenceValue(this.segmentFor(n2), referenceEntry, Preconditions.checkNotNull(v), n);
    }

    void processPendingNotifications() {
        RemovalNotification<K, V> removalNotification;
        while ((removalNotification = this.removalNotificationQueue.poll()) != null) {
            try {
                this.removalListener.onRemoval(removalNotification);
            }
            catch (Throwable throwable) {
                logger.log(Level.WARNING, "Exception thrown by removal listener", throwable);
            }
        }
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

    void reclaimKey(ReferenceEntry<K, V> referenceEntry) {
        int n = referenceEntry.getHash();
        this.segmentFor(n).reclaimKey(referenceEntry, n);
    }

    void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> referenceEntry = valueReference.getEntry();
        int n = referenceEntry.getHash();
        this.segmentFor(n).reclaimValue(referenceEntry.getKey(), n, valueReference);
    }

    boolean recordsAccess() {
        return this.expiresAfterAccess();
    }

    boolean recordsTime() {
        if (this.recordsWrite()) return true;
        if (this.recordsAccess()) return true;
        return false;
    }

    boolean recordsWrite() {
        if (this.expiresAfterWrite()) return true;
        if (this.refreshes()) return true;
        return false;
    }

    void refresh(K k) {
        int n = this.hash(Preconditions.checkNotNull(k));
        this.segmentFor(n).refresh((K)k, n, this.defaultLoader, false);
    }

    boolean refreshes() {
        if (this.refreshNanos <= 0L) return false;
        return true;
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

    Segment<K, V> segmentFor(int n) {
        return this.segments[n >>> this.segmentShift & this.segmentMask];
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.longSize());
    }

    boolean usesAccessEntries() {
        if (this.usesAccessQueue()) return true;
        if (this.recordsAccess()) return true;
        return false;
    }

    boolean usesAccessQueue() {
        if (this.expiresAfterAccess()) return true;
        if (this.evictsBySize()) return true;
        return false;
    }

    boolean usesKeyReferences() {
        if (this.keyStrength == Strength.STRONG) return false;
        return true;
    }

    boolean usesValueReferences() {
        if (this.valueStrength == Strength.STRONG) return false;
        return true;
    }

    boolean usesWriteEntries() {
        if (this.usesWriteQueue()) return true;
        if (this.recordsWrite()) return true;
        return false;
    }

    boolean usesWriteQueue() {
        return this.expiresAfterWrite();
    }

    @Override
    public Collection<V> values() {
        Values values2 = this.values;
        if (values2 != null) {
            return values2;
        }
        this.values = values2 = new Values(this);
        return values2;
    }

    abstract class AbstractCacheSet<T>
    extends AbstractSet<T> {
        final ConcurrentMap<?, ?> map;

        AbstractCacheSet(ConcurrentMap<?, ?> concurrentMap) {
            this.map = concurrentMap;
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        @Override
        public <E> E[] toArray(E[] arrE) {
            return LocalCache.toArrayList(this).toArray(arrE);
        }
    }

    static abstract class AbstractReferenceEntry<K, V>
    implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getHash() {
            throw new UnsupportedOperationException();
        }

        @Override
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAccessTime(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteTime(long l) {
            throw new UnsupportedOperationException();
        }
    }

    static final class AccessQueue<K, V>
    extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(){
            ReferenceEntry<K, V> nextAccess = this;
            ReferenceEntry<K, V> previousAccess = this;

            @Override
            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            @Override
            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            @Override
            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            @Override
            public void setAccessTime(long l) {
            }

            @Override
            public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextAccess = referenceEntry;
            }

            @Override
            public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousAccess = referenceEntry;
            }
        };

        AccessQueue() {
        }

        @Override
        public void clear() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue();
            do {
                ReferenceEntry<K, V> referenceEntry2;
                if (referenceEntry == (referenceEntry2 = this.head)) {
                    referenceEntry2.setNextInAccessQueue(referenceEntry2);
                    referenceEntry = this.head;
                    referenceEntry.setPreviousInAccessQueue(referenceEntry);
                    return;
                }
                referenceEntry2 = referenceEntry.getNextInAccessQueue();
                LocalCache.nullifyAccessOrder(referenceEntry);
                referenceEntry = referenceEntry2;
            } while (true);
        }

        @Override
        public boolean contains(Object object) {
            if (((ReferenceEntry)object).getNextInAccessQueue() == NullEntry.INSTANCE) return false;
            return true;
        }

        @Override
        public boolean isEmpty() {
            if (this.head.getNextInAccessQueue() != this.head) return false;
            return true;
        }

        @Override
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>((ReferenceEntry)this.peek()){

                @Override
                protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> referenceEntry2 = referenceEntry.getNextInAccessQueue();
                    referenceEntry = referenceEntry2;
                    if (referenceEntry2 != AccessQueue.this.head) return referenceEntry;
                    return null;
                }
            };
        }

        @Override
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), referenceEntry);
            LocalCache.connectAccessOrder(referenceEntry, this.head);
            return true;
        }

        @Override
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> referenceEntry;
            ReferenceEntry<K, V> referenceEntry2 = referenceEntry = this.head.getNextInAccessQueue();
            if (referenceEntry != this.head) return referenceEntry2;
            return null;
        }

        @Override
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue();
            if (referenceEntry == this.head) {
                return null;
            }
            this.remove(referenceEntry);
            return referenceEntry;
        }

        @Override
        public boolean remove(Object referenceEntry) {
            ReferenceEntry referenceEntry2 = referenceEntry;
            ReferenceEntry referenceEntry3 = referenceEntry2.getPreviousInAccessQueue();
            referenceEntry = referenceEntry2.getNextInAccessQueue();
            LocalCache.connectAccessOrder(referenceEntry3, referenceEntry);
            LocalCache.nullifyAccessOrder(referenceEntry2);
            if (referenceEntry == NullEntry.INSTANCE) return false;
            return true;
        }

        @Override
        public int size() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInAccessQueue();
            int n = 0;
            while (referenceEntry != this.head) {
                ++n;
                referenceEntry = referenceEntry.getNextInAccessQueue();
            }
            return n;
        }

    }

    static abstract class EntryFactory
    extends Enum<EntryFactory> {
        private static final /* synthetic */ EntryFactory[] $VALUES;
        static final int ACCESS_MASK = 1;
        public static final /* enum */ EntryFactory STRONG;
        public static final /* enum */ EntryFactory STRONG_ACCESS;
        public static final /* enum */ EntryFactory STRONG_ACCESS_WRITE;
        public static final /* enum */ EntryFactory STRONG_WRITE;
        public static final /* enum */ EntryFactory WEAK;
        public static final /* enum */ EntryFactory WEAK_ACCESS;
        public static final /* enum */ EntryFactory WEAK_ACCESS_WRITE;
        static final int WEAK_MASK = 4;
        public static final /* enum */ EntryFactory WEAK_WRITE;
        static final int WRITE_MASK = 2;
        static final EntryFactory[] factories;

        static {
            EntryFactory entryFactory;
            STRONG = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new StrongEntry<K, V>(k, n, referenceEntry);
                }
            };
            STRONG_ACCESS = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> object, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                    object = super.copyEntry(object, referenceEntry, referenceEntry2);
                    this.copyAccessEntry(referenceEntry, object);
                    return object;
                }

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new StrongAccessEntry<K, V>(k, n, referenceEntry);
                }
            };
            STRONG_WRITE = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> object, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                    object = super.copyEntry(object, referenceEntry, referenceEntry2);
                    this.copyWriteEntry(referenceEntry, object);
                    return object;
                }

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new StrongWriteEntry<K, V>(k, n, referenceEntry);
                }
            };
            STRONG_ACCESS_WRITE = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> object, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                    object = super.copyEntry(object, referenceEntry, referenceEntry2);
                    this.copyAccessEntry(referenceEntry, object);
                    this.copyWriteEntry(referenceEntry, object);
                    return object;
                }

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new StrongAccessWriteEntry<K, V>(k, n, referenceEntry);
                }
            };
            WEAK = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new WeakEntry(segment.keyReferenceQueue, k, n, referenceEntry);
                }
            };
            WEAK_ACCESS = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> object, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                    object = super.copyEntry(object, referenceEntry, referenceEntry2);
                    this.copyAccessEntry(referenceEntry, object);
                    return object;
                }

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new WeakAccessEntry(segment.keyReferenceQueue, k, n, referenceEntry);
                }
            };
            WEAK_WRITE = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> object, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                    object = super.copyEntry(object, referenceEntry, referenceEntry2);
                    this.copyWriteEntry(referenceEntry, object);
                    return object;
                }

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new WeakWriteEntry(segment.keyReferenceQueue, k, n, referenceEntry);
                }
            };
            WEAK_ACCESS_WRITE = entryFactory = new EntryFactory(){

                @Override
                <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> object, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
                    object = super.copyEntry(object, referenceEntry, referenceEntry2);
                    this.copyAccessEntry(referenceEntry, object);
                    this.copyWriteEntry(referenceEntry, object);
                    return object;
                }

                @Override
                <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
                    return new WeakAccessWriteEntry(segment.keyReferenceQueue, k, n, referenceEntry);
                }
            };
            EntryFactory entryFactory2 = STRONG;
            EntryFactory entryFactory3 = STRONG_ACCESS;
            EntryFactory entryFactory4 = STRONG_WRITE;
            EntryFactory entryFactory5 = STRONG_ACCESS_WRITE;
            EntryFactory entryFactory6 = WEAK;
            EntryFactory entryFactory7 = WEAK_ACCESS;
            EntryFactory entryFactory8 = WEAK_WRITE;
            $VALUES = new EntryFactory[]{entryFactory2, entryFactory3, entryFactory4, entryFactory5, entryFactory6, entryFactory7, entryFactory8, entryFactory};
            factories = new EntryFactory[]{entryFactory2, entryFactory3, entryFactory4, entryFactory5, entryFactory6, entryFactory7, entryFactory8, entryFactory};
        }

        static EntryFactory getFactory(Strength strength, boolean bl, boolean bl2) {
            Strength strength2 = Strength.WEAK;
            int n = 0;
            int n2 = strength == strength2 ? 4 : 0;
            if (!bl2) return factories[n2 | bl | n];
            n = 2;
            return factories[n2 | bl | n];
        }

        public static EntryFactory valueOf(String string2) {
            return Enum.valueOf(EntryFactory.class, string2);
        }

        public static EntryFactory[] values() {
            return (EntryFactory[])$VALUES.clone();
        }

        <K, V> void copyAccessEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setAccessTime(referenceEntry.getAccessTime());
            LocalCache.connectAccessOrder(referenceEntry.getPreviousInAccessQueue(), referenceEntry2);
            LocalCache.connectAccessOrder(referenceEntry2, referenceEntry.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(referenceEntry);
        }

        <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            return this.newEntry(segment, referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry2);
        }

        <K, V> void copyWriteEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            referenceEntry2.setWriteTime(referenceEntry.getWriteTime());
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry2);
            LocalCache.connectWriteOrder(referenceEntry2, referenceEntry.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(referenceEntry);
        }

        abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4);

    }

    final class EntryIterator
    extends com.google.common.cache.LocalCache.HashIterator<Map.Entry<K, V>> {
        EntryIterator() {
        }

        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }

    final class EntrySet
    extends com.google.common.cache.LocalCache.AbstractCacheSet<Map.Entry<K, V>> {
        EntrySet(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
        }

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
            object2 = LocalCache.this.get(object2);
            bl = bl2;
            if (object2 == null) return bl;
            bl = bl2;
            if (!LocalCache.this.valueEquivalence.equivalent(object.getValue(), object2)) return bl;
            return true;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

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
            if (!LocalCache.this.remove(object, entry.getValue())) return bl;
            return true;
        }
    }

    abstract class HashIterator<T>
    implements Iterator<T> {
        @MonotonicNonNullDecl
        Segment<K, V> currentSegment;
        @MonotonicNonNullDecl
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        @NullableDecl
        LocalCache<K, V> lastReturned;
        @NullableDecl
        ReferenceEntry<K, V> nextEntry;
        @NullableDecl
        LocalCache<K, V> nextExternal;
        int nextSegmentIndex;
        int nextTableIndex;

        HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
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
                var1_1 = LocalCache.this.segments;
                var2_2 = this.nextSegmentIndex;
                this.nextSegmentIndex = var2_2 - 1;
                var1_1 = var1_1[var2_2];
                this.currentSegment = var1_1;
                if (var1_1.count == 0) ** GOTO lbl-1000
                this.currentTable = var1_1 = this.currentSegment.table;
                this.nextTableIndex = var1_1.length() - 1;
            } while (!this.nextInTable());
        }

        boolean advanceTo(ReferenceEntry<K, V> referenceEntry) {
            try {
                boolean bl;
                long l = LocalCache.this.ticker.read();
                K k = referenceEntry.getKey();
                referenceEntry = LocalCache.this.getLiveValue(referenceEntry, l);
                if (referenceEntry != null) {
                    WriteThroughEntry writeThroughEntry = new WriteThroughEntry(k, referenceEntry);
                    this.nextExternal = writeThroughEntry;
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

        LocalCache<K, V> nextEntry() {
            LocalCache<K, V> localCache = this.nextExternal;
            if (localCache == null) throw new NoSuchElementException();
            this.lastReturned = localCache;
            this.advance();
            return this.lastReturned;
        }

        boolean nextInChain() {
            ReferenceEntry<K, V> referenceEntry = this.nextEntry;
            if (referenceEntry == null) return false;
            do {
                this.nextEntry = referenceEntry.getNext();
                referenceEntry = this.nextEntry;
                if (referenceEntry == null) return false;
                if (this.advanceTo(referenceEntry)) {
                    return true;
                }
                referenceEntry = this.nextEntry;
            } while (true);
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
                this.nextEntry = var2_2 = var2_2.get(var1_1);
                if (var2_2 == null) ** GOTO lbl-1000
                if (this.advanceTo((ReferenceEntry<K, V>)var2_2) != false) return true;
            } while (!this.nextInChain());
            return true;
        }

        @Override
        public void remove() {
            boolean bl = this.lastReturned != null;
            Preconditions.checkState(bl);
            LocalCache.this.remove(((WriteThroughEntry)((Object)this.lastReturned)).getKey());
            this.lastReturned = null;
        }
    }

    final class KeyIterator
    extends LocalCache<K, V> {
        KeyIterator() {
        }

        public K next() {
            return this.nextEntry().getKey();
        }
    }

    final class KeySet
    extends LocalCache<K, V> {
        KeySet(ConcurrentMap<?, ?> concurrentMap) {
            super(concurrentMap);
        }

        public boolean contains(Object object) {
            return this.map.containsKey(object);
        }

        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean remove(Object object) {
            if (this.map.remove(object) == null) return false;
            return true;
        }
    }

    static final class LoadingSerializationProxy<K, V>
    extends ManualSerializationProxy<K, V>
    implements LoadingCache<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        @MonotonicNonNullDecl
        transient LoadingCache<K, V> autoDelegate;

        LoadingSerializationProxy(LocalCache<K, V> localCache) {
            super(localCache);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.autoDelegate = this.recreateCacheBuilder().build(this.loader);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }

        @Override
        public final V apply(K k) {
            return this.autoDelegate.apply(k);
        }

        @Override
        public V get(K k) throws ExecutionException {
            return this.autoDelegate.get(k);
        }

        @Override
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.autoDelegate.getAll(iterable);
        }

        @Override
        public V getUnchecked(K k) {
            return this.autoDelegate.getUnchecked(k);
        }

        @Override
        public void refresh(K k) {
            this.autoDelegate.refresh(k);
        }
    }

    static class LoadingValueReference<K, V>
    implements ValueReference<K, V> {
        final SettableFuture<V> futureValue = SettableFuture.create();
        volatile ValueReference<K, V> oldValue;
        final Stopwatch stopwatch = Stopwatch.createUnstarted();

        public LoadingValueReference() {
            this(LocalCache.unset());
        }

        public LoadingValueReference(ValueReference<K, V> valueReference) {
            this.oldValue = valueReference;
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable throwable) {
            return Futures.immediateFailedFuture(throwable);
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @NullableDecl V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        @Override
        public V get() {
            return this.oldValue.get();
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        @Override
        public int getWeight() {
            return this.oldValue.getWeight();
        }

        @Override
        public boolean isActive() {
            return this.oldValue.isActive();
        }

        @Override
        public boolean isLoading() {
            return true;
        }

        public ListenableFuture<V> loadFuture(K object, CacheLoader<? super K, V> function) {
            try {
                this.stopwatch.start();
                V v = this.oldValue.get();
                if (v == null) {
                    if (!this.set(object = ((CacheLoader)((Object)function)).load(object))) return Futures.immediateFuture(object);
                    return this.futureValue;
                }
                if ((object = ((CacheLoader)((Object)function)).reload(object, v)) == null) {
                    return Futures.immediateFuture(null);
                }
                function = new Function<V, V>(){

                    @Override
                    public V apply(V v) {
                        LoadingValueReference.this.set(v);
                        return v;
                    }
                };
                return Futures.transform(object, function, MoreExecutors.directExecutor());
            }
            catch (Throwable throwable) {
                object = this.setException(throwable) ? this.futureValue : this.fullyFailedFuture(throwable);
                if (!(throwable instanceof InterruptedException)) return object;
                Thread.currentThread().interrupt();
                return object;
            }
        }

        @Override
        public void notifyNewValue(@NullableDecl V v) {
            if (v != null) {
                this.set(v);
                return;
            }
            this.oldValue = LocalCache.unset();
        }

        public boolean set(@NullableDecl V v) {
            return this.futureValue.set(v);
        }

        public boolean setException(Throwable throwable) {
            return this.futureValue.setException(throwable);
        }

        @Override
        public V waitForValue() throws ExecutionException {
            return Uninterruptibles.getUninterruptibly(this.futureValue);
        }

    }

    static class LocalLoadingCache<K, V>
    extends LocalManualCache<K, V>
    implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1L;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> cacheBuilder, CacheLoader<? super K, V> cacheLoader) {
            super(new LocalCache<K, V>(cacheBuilder, Preconditions.checkNotNull(cacheLoader)));
        }

        @Override
        public final V apply(K k) {
            return this.getUnchecked(k);
        }

        @Override
        public V get(K k) throws ExecutionException {
            return this.localCache.getOrLoad(k);
        }

        @Override
        public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
            return this.localCache.getAll(iterable);
        }

        @Override
        public V getUnchecked(K object) {
            try {
                object = this.get(object);
            }
            catch (ExecutionException executionException) {
                throw new UncheckedExecutionException(executionException.getCause());
            }
            return (V)object;
        }

        @Override
        public void refresh(K k) {
            this.localCache.refresh(k);
        }

        @Override
        Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }
    }

    static class LocalManualCache<K, V>
    implements Cache<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        final LocalCache<K, V> localCache;

        LocalManualCache(CacheBuilder<? super K, ? super V> cacheBuilder) {
            this(new LocalCache<K, V>(cacheBuilder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }

        @Override
        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        @Override
        public void cleanUp() {
            this.localCache.cleanUp();
        }

        @Override
        public V get(K k, final Callable<? extends V> callable) throws ExecutionException {
            Preconditions.checkNotNull(callable);
            return this.localCache.get(k, new CacheLoader<Object, V>(){

                @Override
                public V load(Object object) throws Exception {
                    return callable.call();
                }
            });
        }

        @Override
        public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
            return this.localCache.getAllPresent(iterable);
        }

        @NullableDecl
        @Override
        public V getIfPresent(Object object) {
            return this.localCache.getIfPresent(object);
        }

        @Override
        public void invalidate(Object object) {
            Preconditions.checkNotNull(object);
            this.localCache.remove(object);
        }

        @Override
        public void invalidateAll() {
            this.localCache.clear();
        }

        @Override
        public void invalidateAll(Iterable<?> iterable) {
            this.localCache.invalidateAll(iterable);
        }

        @Override
        public void put(K k, V v) {
            this.localCache.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            this.localCache.putAll(map);
        }

        @Override
        public long size() {
            return this.localCache.longSize();
        }

        @Override
        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter simpleStatsCounter = new AbstractCache.SimpleStatsCounter();
            simpleStatsCounter.incrementBy(this.localCache.globalStatsCounter);
            Segment<K, V>[] arrsegment = this.localCache.segments;
            int n = arrsegment.length;
            int n2 = 0;
            while (n2 < n) {
                simpleStatsCounter.incrementBy(arrsegment[n2].statsCounter);
                ++n2;
            }
            return simpleStatsCounter.snapshot();
        }

        Object writeReplace() {
            return new ManualSerializationProxy<K, V>(this.localCache);
        }

    }

    static class ManualSerializationProxy<K, V>
    extends ForwardingCache<K, V>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final int concurrencyLevel;
        @MonotonicNonNullDecl
        transient Cache<K, V> delegate;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;
        @NullableDecl
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;
        final Weigher<K, V> weigher;

        private ManualSerializationProxy(Strength object, Strength strength, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, long l, long l2, long l3, Weigher<K, V> weigher, int n, RemovalListener<? super K, ? super V> removalListener, Ticker ticker, CacheLoader<? super K, V> cacheLoader) {
            block3 : {
                block2 : {
                    this.keyStrength = object;
                    this.valueStrength = strength;
                    this.keyEquivalence = equivalence;
                    this.valueEquivalence = equivalence2;
                    this.expireAfterWriteNanos = l;
                    this.expireAfterAccessNanos = l2;
                    this.maxWeight = l3;
                    this.weigher = weigher;
                    this.concurrencyLevel = n;
                    this.removalListener = removalListener;
                    if (ticker == Ticker.systemTicker()) break block2;
                    object = ticker;
                    if (ticker != CacheBuilder.NULL_TICKER) break block3;
                }
                object = null;
            }
            this.ticker = object;
            this.loader = cacheLoader;
        }

        ManualSerializationProxy(LocalCache<K, V> localCache) {
            this(localCache.keyStrength, localCache.valueStrength, localCache.keyEquivalence, localCache.valueEquivalence, localCache.expireAfterWriteNanos, localCache.expireAfterAccessNanos, localCache.maxWeight, localCache.weigher, localCache.concurrencyLevel, localCache.removalListener, localCache.ticker, localCache.defaultLoader);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.delegate = this.recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        @Override
        protected Cache<K, V> delegate() {
            return this.delegate;
        }

        CacheBuilder<K, V> recreateCacheBuilder() {
            Ticker ticker;
            CacheBuilder<K, V> cacheBuilder = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            cacheBuilder.strictParsing = false;
            long l = this.expireAfterWriteNanos;
            if (l > 0L) {
                cacheBuilder.expireAfterWrite(l, TimeUnit.NANOSECONDS);
            }
            if ((l = this.expireAfterAccessNanos) > 0L) {
                cacheBuilder.expireAfterAccess(l, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
                cacheBuilder.weigher(this.weigher);
                l = this.maxWeight;
                if (l != -1L) {
                    cacheBuilder.maximumWeight(l);
                }
            } else {
                l = this.maxWeight;
                if (l != -1L) {
                    cacheBuilder.maximumSize(l);
                }
            }
            if ((ticker = this.ticker) == null) return cacheBuilder;
            cacheBuilder.ticker(ticker);
            return cacheBuilder;
        }
    }

    private static final class NullEntry
    extends Enum<NullEntry>
    implements ReferenceEntry<Object, Object> {
        private static final /* synthetic */ NullEntry[] $VALUES;
        public static final /* enum */ NullEntry INSTANCE;

        static {
            NullEntry nullEntry;
            INSTANCE = nullEntry = new NullEntry();
            $VALUES = new NullEntry[]{nullEntry};
        }

        public static NullEntry valueOf(String string2) {
            return Enum.valueOf(NullEntry.class, string2);
        }

        public static NullEntry[] values() {
            return (NullEntry[])$VALUES.clone();
        }

        @Override
        public long getAccessTime() {
            return 0L;
        }

        @Override
        public int getHash() {
            return 0;
        }

        @Override
        public Object getKey() {
            return null;
        }

        @Override
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        @Override
        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        @Override
        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        @Override
        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        @Override
        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        @Override
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        @Override
        public long getWriteTime() {
            return 0L;
        }

        @Override
        public void setAccessTime(long l) {
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        @Override
        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @Override
        public void setWriteTime(long l) {
        }
    }

    static class Segment<K, V>
    extends ReentrantLock {
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;
        @NullableDecl
        final ReferenceQueue<K> keyReferenceQueue;
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AbstractCache.StatsCounter statsCounter;
        @MonotonicNonNullDecl
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;
        long totalWeight;
        @NullableDecl
        final ReferenceQueue<V> valueReferenceQueue;
        final Queue<ReferenceEntry<K, V>> writeQueue;

        Segment(LocalCache<K, V> queue, int n, long l, AbstractCache.StatsCounter queue2) {
            this.map = queue;
            this.maxSegmentWeight = l;
            this.statsCounter = Preconditions.checkNotNull(queue2);
            this.initTable(this.newEntryArray(n));
            boolean bl = ((LocalCache)((Object)queue)).usesKeyReferences();
            Object var7_6 = null;
            queue2 = bl ? new ReferenceQueue() : null;
            this.keyReferenceQueue = queue2;
            queue2 = var7_6;
            if (((LocalCache)((Object)queue)).usesValueReferences()) {
                queue2 = new ReferenceQueue();
            }
            this.valueReferenceQueue = queue2;
            queue2 = ((LocalCache)((Object)queue)).usesAccessQueue() ? new ConcurrentLinkedQueue<ReferenceEntry<K, V>>() : LocalCache.discardingQueue();
            this.recencyQueue = queue2;
            queue2 = ((LocalCache)((Object)queue)).usesWriteQueue() ? new WriteQueue() : LocalCache.discardingQueue();
            this.writeQueue = queue2;
            queue = ((LocalCache)((Object)queue)).usesAccessQueue() ? new AccessQueue() : LocalCache.discardingQueue();
            this.accessQueue = queue;
        }

        void cleanUp() {
            this.runLockedCleanup(this.map.ticker.read());
            this.runUnlockedCleanup();
        }

        void clear() {
            int n;
            if (this.count == 0) return;
            this.lock();
            this.preWriteCleanup(this.map.ticker.read());
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            for (n = 0; n < atomicReferenceArray.length(); ++n) {
                for (ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get((int)n); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    if (!referenceEntry.getValueReference().isActive()) continue;
                    K k = referenceEntry.getKey();
                    V v = referenceEntry.getValueReference().get();
                    RemovalCause removalCause = k != null && v != null ? RemovalCause.EXPLICIT : RemovalCause.COLLECTED;
                    this.enqueueNotification(k, referenceEntry.getHash(), v, referenceEntry.getValueReference().getWeight(), removalCause);
                }
            }
            for (n = 0; n < atomicReferenceArray.length(); ++n) {
                atomicReferenceArray.set(n, null);
            }
            try {
                this.clearReferenceQueues();
                this.writeQueue.clear();
                this.accessQueue.clear();
                this.readCount.set(0);
                ++this.modCount;
                this.count = 0;
                return;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {
            }
        }

        void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.clearKeyReferenceQueue();
            }
            if (!this.map.usesValueReferences()) return;
            this.clearValueReferenceQueue();
        }

        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {
            }
        }

        boolean containsKey(Object referenceEntry, int n) {
            try {
                int n2 = this.count;
                boolean bl = false;
                if (n2 != 0) {
                    if ((referenceEntry = this.getLiveEntry(referenceEntry, n, this.map.ticker.read())) == null) {
                        this.postReadCleanup();
                        return false;
                    }
                    if ((referenceEntry = referenceEntry.getValueReference().get()) != null) {
                        bl = true;
                    }
                    this.postReadCleanup();
                    return bl;
                }
                this.postReadCleanup();
                return false;
            }
            catch (Throwable throwable) {
                this.postReadCleanup();
                throw throwable;
            }
        }

        boolean containsValue(Object object) {
            try {
                if (this.count == 0) return false;
                long l = this.map.ticker.read();
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n = atomicReferenceArray.length();
                int n2 = 0;
                while (n2 < n) {
                    for (ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get((int)n2); referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                        boolean bl;
                        V v = this.getLiveValue(referenceEntry, l);
                        if (v == null || !(bl = this.map.valueEquivalence.equivalent(object, v))) continue;
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

        ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            if (referenceEntry.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            V v = valueReference.get();
            if (v == null && valueReference.isActive()) {
                return null;
            }
            referenceEntry = this.map.entryFactory.copyEntry(this, referenceEntry, referenceEntry2);
            referenceEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, v, referenceEntry));
            return referenceEntry;
        }

        void drainKeyReferenceQueue() {
            int n;
            int n2 = 0;
            do {
                Object object;
                if ((object = this.keyReferenceQueue.poll()) == null) return;
                object = (ReferenceEntry)object;
                this.map.reclaimKey((ReferenceEntry<K, V>)object);
                n2 = n = n2 + 1;
            } while (n != 16);
        }

        void drainRecencyQueue() {
            ReferenceEntry<K, V> referenceEntry;
            while ((referenceEntry = this.recencyQueue.poll()) != null) {
                if (!this.accessQueue.contains(referenceEntry)) continue;
                this.accessQueue.add(referenceEntry);
            }
        }

        void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.drainKeyReferenceQueue();
            }
            if (!this.map.usesValueReferences()) return;
            this.drainValueReferenceQueue();
        }

        void drainValueReferenceQueue() {
            int n;
            int n2 = 0;
            do {
                Object object;
                if ((object = this.valueReferenceQueue.poll()) == null) return;
                object = (ValueReference)object;
                this.map.reclaimValue((ValueReference<K, V>)object);
                n2 = n = n2 + 1;
            } while (n != 16);
        }

        void enqueueNotification(@NullableDecl K object, int n, @NullableDecl V v, int n2, RemovalCause removalCause) {
            this.totalWeight -= (long)n2;
            if (removalCause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue == DISCARDING_QUEUE) return;
            object = RemovalNotification.create(object, v, removalCause);
            this.map.removalNotificationQueue.offer(object);
        }

        void evictEntries(ReferenceEntry<K, V> referenceEntry) {
            if (!this.map.evictsBySize()) {
                return;
            }
            this.drainRecencyQueue();
            if ((long)referenceEntry.getValueReference().getWeight() > this.maxSegmentWeight) {
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) throw new AssertionError();
            }
            while (this.totalWeight > this.maxSegmentWeight) {
                referenceEntry = this.getNextEvictable();
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.SIZE)) throw new AssertionError();
            }
        }

        void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int n = atomicReferenceArray.length();
            if (n >= 1073741824) {
                return;
            }
            int n2 = this.count;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray2 = this.newEntryArray(n << 1);
            this.threshold = atomicReferenceArray2.length() * 3 / 4;
            int n3 = atomicReferenceArray2.length() - 1;
            int n4 = 0;
            do {
                if (n4 >= n) {
                    this.table = atomicReferenceArray2;
                    this.count = n2;
                    return;
                }
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(n4);
                int n5 = n2;
                if (referenceEntry != null) {
                    ReferenceEntry<K, V> referenceEntry2;
                    n5 = referenceEntry.getHash() & n3;
                    if (referenceEntry2 == null) {
                        atomicReferenceArray2.set(n5, referenceEntry);
                        n5 = n2;
                    } else {
                        ReferenceEntry<K, V> referenceEntry3 = referenceEntry;
                        for (referenceEntry2 = referenceEntry.getNext(); referenceEntry2 != null; referenceEntry2 = referenceEntry2.getNext()) {
                            int n6 = referenceEntry2.getHash() & n3;
                            int n7 = n5;
                            if (n6 != n5) {
                                referenceEntry3 = referenceEntry2;
                                n7 = n6;
                            }
                            n5 = n7;
                        }
                        atomicReferenceArray2.set(n5, referenceEntry3);
                        do {
                            n5 = n2;
                            if (referenceEntry == referenceEntry3) break;
                            n5 = referenceEntry.getHash() & n3;
                            referenceEntry2 = this.copyEntry(referenceEntry, atomicReferenceArray2.get(n5));
                            if (referenceEntry2 != null) {
                                atomicReferenceArray2.set(n5, referenceEntry2);
                            } else {
                                this.removeCollectedEntry(referenceEntry);
                                --n2;
                            }
                            referenceEntry = referenceEntry.getNext();
                        } while (true);
                    }
                }
                ++n4;
                n2 = n5;
            } while (true);
        }

        void expireEntries(long l) {
            ReferenceEntry<K, V> referenceEntry;
            this.drainRecencyQueue();
            while ((referenceEntry = this.writeQueue.peek()) != null && this.map.isExpired(referenceEntry, l)) {
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.EXPIRED)) throw new AssertionError();
            }
            while ((referenceEntry = this.accessQueue.peek()) != null) {
                if (!this.map.isExpired(referenceEntry, l)) return;
                if (!this.removeEntry(referenceEntry, referenceEntry.getHash(), RemovalCause.EXPIRED)) throw new AssertionError();
            }
        }

        @NullableDecl
        V get(Object referenceEntry, int n) {
            long l;
            block6 : {
                if (this.count == 0) return null;
                l = this.map.ticker.read();
                if ((referenceEntry = this.getLiveEntry(referenceEntry, n, l)) != null) break block6;
                this.postReadCleanup();
                return null;
            }
            try {
                V v = referenceEntry.getValueReference().get();
                if (v != null) {
                    this.recordRead(referenceEntry, l);
                    referenceEntry = this.scheduleRefresh(referenceEntry, referenceEntry.getKey(), n, v, l, this.map.defaultLoader);
                    return (V)referenceEntry;
                }
                this.tryDrainReferenceQueues();
                return null;
            }
            finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled unnecessary exception pruning
         */
        V get(K object, int n, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            Throwable throwable2222;
            block9 : {
                block7 : {
                    Object object2;
                    ReferenceEntry<K, V> referenceEntry;
                    block8 : {
                        Preconditions.checkNotNull(object);
                        Preconditions.checkNotNull(cacheLoader);
                        if (this.count == 0 || (referenceEntry = this.getEntry(object, n)) == null) break block7;
                        long l = this.map.ticker.read();
                        object2 = this.getLiveValue(referenceEntry, l);
                        if (object2 == null) break block8;
                        this.recordRead(referenceEntry, l);
                        this.statsCounter.recordHits(1);
                        object = this.scheduleRefresh(referenceEntry, object, n, object2, l, cacheLoader);
                        this.postReadCleanup();
                        return (V)object;
                    }
                    object2 = referenceEntry.getValueReference();
                    if (!object2.isLoading()) break block7;
                    object = this.waitForLoadingValue(referenceEntry, object, (ValueReference<K, V>)object2);
                    this.postReadCleanup();
                    return (V)object;
                }
                object = this.lockedGetOrLoad(object, n, cacheLoader);
                this.postReadCleanup();
                return (V)object;
                {
                    catch (Throwable throwable2222) {
                        break block9;
                    }
                    catch (ExecutionException executionException) {}
                    {
                        object = executionException.getCause();
                        if (!(object instanceof Error)) {
                            if (!(object instanceof RuntimeException)) throw executionException;
                            UncheckedExecutionException uncheckedExecutionException = new UncheckedExecutionException((Throwable)object);
                            throw uncheckedExecutionException;
                        }
                        ExecutionError executionError = new ExecutionError((Error)object);
                        throw executionError;
                    }
                }
            }
            this.postReadCleanup();
            throw throwable2222;
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled unnecessary exception pruning
         */
        V getAndRecordStats(K k, int n, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> object) throws ExecutionException {
            Object v;
            void var4_7;
            block6 : {
                block5 : {
                    v = Uninterruptibles.getUninterruptibly(object);
                    if (v == null) break block5;
                    try {
                        this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                        this.storeLoadedValue(k, n, loadingValueReference, v);
                        if (v != null) return v;
                        this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                        this.removeLoadingValue(k, n, loadingValueReference);
                    }
                    catch (Throwable throwable) {}
                    return v;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("CacheLoader returned null for key ");
                ((StringBuilder)object).append(k);
                ((StringBuilder)object).append(".");
                CacheLoader.InvalidCacheLoadException invalidCacheLoadException = new CacheLoader.InvalidCacheLoadException(((StringBuilder)object).toString());
                throw invalidCacheLoadException;
                break block6;
                catch (Throwable throwable) {
                    v = null;
                }
            }
            if (v != null) throw var4_7;
            this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
            this.removeLoadingValue(k, n, loadingValueReference);
            throw var4_7;
        }

        @NullableDecl
        ReferenceEntry<K, V> getEntry(Object object, int n) {
            ReferenceEntry<K, V> referenceEntry = this.getFirst(n);
            while (referenceEntry != null) {
                if (referenceEntry.getHash() == n) {
                    K k = referenceEntry.getKey();
                    if (k == null) {
                        this.tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(object, k)) {
                        return referenceEntry;
                    }
                }
                referenceEntry = referenceEntry.getNext();
            }
            return null;
        }

        ReferenceEntry<K, V> getFirst(int n) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            return atomicReferenceArray.get(n & atomicReferenceArray.length() - 1);
        }

        @NullableDecl
        ReferenceEntry<K, V> getLiveEntry(Object referenceEntry, int n, long l) {
            if ((referenceEntry = this.getEntry(referenceEntry, n)) == null) {
                return null;
            }
            if (!this.map.isExpired(referenceEntry, l)) return referenceEntry;
            this.tryExpireEntries(l);
            return null;
        }

        V getLiveValue(ReferenceEntry<K, V> referenceEntry, long l) {
            if (referenceEntry.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            V v = referenceEntry.getValueReference().get();
            if (v == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if (!this.map.isExpired(referenceEntry, l)) return v;
            this.tryExpireEntries(l);
            return null;
        }

        ReferenceEntry<K, V> getNextEvictable() {
            ReferenceEntry referenceEntry;
            Iterator iterator2 = this.accessQueue.iterator();
            do {
                if (!iterator2.hasNext()) throw new AssertionError();
            } while ((referenceEntry = (ReferenceEntry)iterator2.next()).getValueReference().getWeight() <= 0);
            return referenceEntry;
        }

        void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray) {
            int n;
            this.threshold = atomicReferenceArray.length() * 3 / 4;
            if (!this.map.customWeigher() && (long)(n = this.threshold) == this.maxSegmentWeight) {
                this.threshold = n + 1;
            }
            this.table = atomicReferenceArray;
        }

        @NullableDecl
        LoadingValueReference<K, V> insertLoadingValueReference(K object, int n, boolean bl) {
            ValueReference valueReference;
            Object object2;
            block6 : {
                this.lock();
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = atomicReferenceArray.length() - 1 & n;
                for (object2 = valueReference = atomicReferenceArray.get((int)n2); object2 != null; object2 = object2.getNext()) {
                    K k = object2.getKey();
                    if (object2.getHash() != n || k == null || !this.map.keyEquivalence.equivalent(object, k)) continue;
                    valueReference = object2.getValueReference();
                    if (valueReference.isLoading()) return null;
                    if (bl && l - object2.getWriteTime() < this.map.refreshNanos) {
                        return null;
                    }
                    break block6;
                }
                ++this.modCount;
                object2 = new LoadingValueReference();
                object = this.newEntry((K)object, n, (ReferenceEntry<K, V>)((Object)valueReference));
                object.setValueReference(object2);
                atomicReferenceArray.set(n2, (ReferenceEntry<K, V>)object);
                return object2;
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
            ++this.modCount;
            object = new LoadingValueReference(valueReference);
            object2.setValueReference(object);
            return object;
        }

        ListenableFuture<V> loadAsync(final K k, final int n, final LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> object) {
            object = loadingValueReference.loadFuture((K)k, (CacheLoader<? super K, V>)object);
            object.addListener(new Runnable((ListenableFuture)object){
                final /* synthetic */ ListenableFuture val$loadingFuture;
                {
                    this.val$loadingFuture = listenableFuture;
                }

                @Override
                public void run() {
                    try {
                        Segment.this.getAndRecordStats(k, n, loadingValueReference, this.val$loadingFuture);
                        return;
                    }
                    catch (Throwable throwable) {
                        logger.log(Level.WARNING, "Exception thrown during refresh", throwable);
                        loadingValueReference.setException(throwable);
                    }
                }
            }, MoreExecutors.directExecutor());
            return object;
        }

        V loadSync(K k, int n, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            return this.getAndRecordStats(k, n, loadingValueReference, loadingValueReference.loadFuture((K)k, cacheLoader));
        }

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        V lockedGetOrLoad(K object, int n, CacheLoader<? super K, V> cacheLoader) throws ExecutionException {
            LoadingValueReference loadingValueReference;
            int n2;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray;
            ReferenceEntry referenceEntry;
            Object object2;
            ReferenceEntry referenceEntry2;
            int n3;
            ReferenceEntry referenceEntry3;
            block17 : {
                block18 : {
                    this.lock();
                    long l = this.map.ticker.read();
                    this.preWriteCleanup(l);
                    n3 = this.count;
                    atomicReferenceArray = this.table;
                    n2 = n & atomicReferenceArray.length() - 1;
                    referenceEntry3 = referenceEntry = atomicReferenceArray.get(n2);
                    do {
                        block19 : {
                            block22 : {
                                block21 : {
                                    K k;
                                    block20 : {
                                        loadingValueReference = null;
                                        if (referenceEntry3 == null) break;
                                        k = referenceEntry3.getKey();
                                        if (referenceEntry3.getHash() != n || k == null || !this.map.keyEquivalence.equivalent(object, k)) break block19;
                                        object2 = referenceEntry3.getValueReference();
                                        if (object2.isLoading()) {
                                            n3 = 0;
                                            referenceEntry2 = object2;
                                            break block17;
                                        }
                                        referenceEntry2 = object2.get();
                                        if (referenceEntry2 != null) break block20;
                                        this.enqueueNotification(k, n, referenceEntry2, object2.getWeight(), RemovalCause.COLLECTED);
                                        break block21;
                                    }
                                    if (!this.map.isExpired(referenceEntry3, l)) break block22;
                                    this.enqueueNotification(k, n, referenceEntry2, object2.getWeight(), RemovalCause.EXPIRED);
                                }
                                this.writeQueue.remove(referenceEntry3);
                                this.accessQueue.remove(referenceEntry3);
                                this.count = n3 - 1;
                                break block18;
                            }
                            this.recordLockedRead(referenceEntry3, l);
                            this.statsCounter.recordHits(1);
                            return (V)referenceEntry2;
                        }
                        referenceEntry3 = referenceEntry3.getNext();
                    } while (true);
                    object2 = null;
                }
                n3 = 1;
                referenceEntry2 = object2;
            }
            object2 = referenceEntry3;
            if (n3 != 0) {
                loadingValueReference = new LoadingValueReference();
                if (referenceEntry3 == null) {
                    object2 = this.newEntry(object, n, referenceEntry);
                    object2.setValueReference(loadingValueReference);
                    atomicReferenceArray.set(n2, (ReferenceEntry<K, V>)object2);
                } else {
                    referenceEntry3.setValueReference(loadingValueReference);
                    object2 = referenceEntry3;
                }
            }
            if (n3 == 0) return this.waitForLoadingValue((ReferenceEntry<K, V>)object2, object, (ValueReference<K, V>)((Object)referenceEntry2));
            object = this.loadSync(object, n, loadingValueReference, cacheLoader);
            // MONITOREXIT : object2
            this.statsCounter.recordMisses(1);
            return (V)object;
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        ReferenceEntry<K, V> newEntry(K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(k), n, referenceEntry);
        }

        AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int n) {
            return new AtomicReferenceArray<ReferenceEntry<K, V>>(n);
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) != 0) return;
            this.cleanUp();
        }

        void postWriteCleanup() {
            this.runUnlockedCleanup();
        }

        void preWriteCleanup(long l) {
            this.runLockedCleanup(l);
        }

        @NullableDecl
        V put(K k, int n, V v, boolean bl) {
            ReferenceEntry<K, V> referenceEntry;
            block11 : {
                this.lock();
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                if (this.count + 1 > this.threshold) {
                    this.expand();
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                ValueReference<K, V> valueReference = atomicReferenceArray.get(n2);
                for (referenceEntry = valueReference; referenceEntry != null; referenceEntry = referenceEntry.getNext()) {
                    K k2 = referenceEntry.getKey();
                    if (referenceEntry.getHash() != n || k2 == null || !this.map.keyEquivalence.equivalent(k, k2)) continue;
                    valueReference = referenceEntry.getValueReference();
                    atomicReferenceArray = valueReference.get();
                    if (atomicReferenceArray == null) {
                        ++this.modCount;
                        if (valueReference.isActive()) {
                            this.enqueueNotification(k, n, atomicReferenceArray, valueReference.getWeight(), RemovalCause.COLLECTED);
                            this.setValue(referenceEntry, k, v, l);
                            n = this.count;
                        } else {
                            this.setValue(referenceEntry, k, v, l);
                            n = this.count + 1;
                        }
                        break block11;
                    }
                    if (bl) {
                        this.recordLockedRead(referenceEntry, l);
                        return (V)atomicReferenceArray;
                    }
                    ++this.modCount;
                    this.enqueueNotification(k, n, atomicReferenceArray, valueReference.getWeight(), RemovalCause.REPLACED);
                    this.setValue(referenceEntry, k, v, l);
                    this.evictEntries(referenceEntry);
                    return (V)atomicReferenceArray;
                }
                ++this.modCount;
                referenceEntry = this.newEntry(k, n, (ReferenceEntry<K, V>)((Object)valueReference));
                this.setValue(referenceEntry, k, v, l);
                atomicReferenceArray.set(n2, referenceEntry);
                ++this.count;
                this.evictEntries(referenceEntry);
                return null;
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
            this.count = n;
            this.evictEntries(referenceEntry);
            return null;
        }

        boolean reclaimKey(ReferenceEntry<K, V> referenceEntry, int n) {
            ReferenceEntry<K, V> referenceEntry2;
            int n2;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray;
            ReferenceEntry<K, V> referenceEntry3;
            this.lock();
            try {
                atomicReferenceArray = this.table;
                n2 = atomicReferenceArray.length() - 1 & n;
                referenceEntry3 = referenceEntry2 = atomicReferenceArray.get(n2);
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
            do {
                if (referenceEntry3 == null) {
                    this.unlock();
                    this.postWriteCleanup();
                    return false;
                }
                if (referenceEntry3 == referenceEntry) {
                    ++this.modCount;
                    referenceEntry = this.removeValueFromChain(referenceEntry2, referenceEntry3, referenceEntry3.getKey(), n, referenceEntry3.getValueReference().get(), referenceEntry3.getValueReference(), RemovalCause.COLLECTED);
                    n = this.count;
                    atomicReferenceArray.set(n2, referenceEntry);
                    this.count = n - 1;
                    return true;
                }
                referenceEntry3 = referenceEntry3.getNext();
                continue;
                break;
            } while (true);
        }

        boolean reclaimValue(K object, int n, ValueReference<K, V> valueReference) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = atomicReferenceArray.length() - 1 & n;
                ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n2);
                while (referenceEntry2 != null) {
                    K k = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        if (referenceEntry2.getValueReference() != valueReference) return false;
                        ++this.modCount;
                        object = this.removeValueFromChain(referenceEntry, referenceEntry2, k, n, valueReference.get(), valueReference, RemovalCause.COLLECTED);
                        n = this.count;
                        atomicReferenceArray.set(n2, (ReferenceEntry<K, V>)object);
                        this.count = n - 1;
                        return true;
                    }
                    referenceEntry2 = referenceEntry2.getNext();
                }
                return false;
            }
            finally {
                this.unlock();
                if (!this.isHeldByCurrentThread()) {
                    this.postWriteCleanup();
                }
            }
        }

        void recordLockedRead(ReferenceEntry<K, V> referenceEntry, long l) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(l);
            }
            this.accessQueue.add(referenceEntry);
        }

        void recordRead(ReferenceEntry<K, V> referenceEntry, long l) {
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(l);
            }
            this.recencyQueue.add(referenceEntry);
        }

        void recordWrite(ReferenceEntry<K, V> referenceEntry, int n, long l) {
            this.drainRecencyQueue();
            this.totalWeight += (long)n;
            if (this.map.recordsAccess()) {
                referenceEntry.setAccessTime(l);
            }
            if (this.map.recordsWrite()) {
                referenceEntry.setWriteTime(l);
            }
            this.accessQueue.add(referenceEntry);
            this.writeQueue.add(referenceEntry);
        }

        @NullableDecl
        V refresh(K object, int n, CacheLoader<? super K, V> cacheLoader, boolean bl) {
            LoadingValueReference<K, V> loadingValueReference = this.insertLoadingValueReference(object, n, bl);
            if (loadingValueReference == null) {
                return null;
            }
            if (!(object = this.loadAsync(object, n, loadingValueReference, cacheLoader)).isDone()) return null;
            try {
                object = Uninterruptibles.getUninterruptibly(object);
            }
            catch (Throwable throwable) {
                return null;
            }
            return (V)object;
        }

        @NullableDecl
        V remove(Object referenceEntry, int n) {
            ReferenceEntry<K, V> referenceEntry2;
            ValueReference<K, V> valueReference;
            int n2;
            V v;
            K k;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray;
            ReferenceEntry<K, V> referenceEntry3;
            block8 : {
                this.lock();
                this.preWriteCleanup(this.map.ticker.read());
                atomicReferenceArray = this.table;
                n2 = atomicReferenceArray.length() - 1 & n;
                referenceEntry3 = referenceEntry2 = atomicReferenceArray.get(n2);
                while (referenceEntry3 != null) {
                    k = referenceEntry3.getKey();
                    if (referenceEntry3.getHash() == n && k != null && this.map.keyEquivalence.equivalent(referenceEntry, k)) {
                        valueReference = referenceEntry3.getValueReference();
                        v = valueReference.get();
                        if (v != null) {
                            referenceEntry = RemovalCause.EXPLICIT;
                        } else {
                            if (!valueReference.isActive()) return null;
                            referenceEntry = RemovalCause.COLLECTED;
                        }
                        break block8;
                    }
                    referenceEntry3 = referenceEntry3.getNext();
                }
                return null;
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
            ++this.modCount;
            referenceEntry = this.removeValueFromChain(referenceEntry2, referenceEntry3, k, n, v, valueReference, (RemovalCause)((Object)referenceEntry));
            n = this.count;
            atomicReferenceArray.set(n2, referenceEntry);
            this.count = n - 1;
            return v;
        }

        boolean remove(Object object, int n, Object object2) {
            this.lock();
            this.preWriteCleanup(this.map.ticker.read());
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int n2 = atomicReferenceArray.length();
            boolean bl = true;
            n2 = n2 - 1 & n;
            try {
                ReferenceEntry<K, V> referenceEntry;
                ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n2);
                while (referenceEntry2 != null) {
                    K k = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                        V v = valueReference.get();
                        if (this.map.valueEquivalence.equivalent(object2, v)) {
                            object = RemovalCause.EXPLICIT;
                        } else {
                            if (v != null) return false;
                            if (!valueReference.isActive()) return false;
                            object = RemovalCause.COLLECTED;
                        }
                        ++this.modCount;
                        object2 = this.removeValueFromChain(referenceEntry, referenceEntry2, k, n, v, valueReference, (RemovalCause)((Object)object));
                        n = this.count;
                        atomicReferenceArray.set(n2, (ReferenceEntry<K, V>)object2);
                        this.count = n - 1;
                        object2 = RemovalCause.EXPLICIT;
                        if (object == object2) {
                            return bl;
                        } else {
                            bl = false;
                        }
                        return bl;
                    }
                    referenceEntry2 = referenceEntry2.getNext();
                }
                return false;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        void removeCollectedEntry(ReferenceEntry<K, V> referenceEntry) {
            this.enqueueNotification(referenceEntry.getKey(), referenceEntry.getHash(), referenceEntry.getValueReference().get(), referenceEntry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(referenceEntry);
            this.accessQueue.remove(referenceEntry);
        }

        boolean removeEntry(ReferenceEntry<K, V> referenceEntry, int n, RemovalCause removalCause) {
            ReferenceEntry<K, V> referenceEntry2;
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
            int n2 = atomicReferenceArray.length() - 1 & n;
            ReferenceEntry<K, V> referenceEntry3 = referenceEntry2 = atomicReferenceArray.get(n2);
            while (referenceEntry3 != null) {
                if (referenceEntry3 == referenceEntry) {
                    ++this.modCount;
                    referenceEntry = this.removeValueFromChain(referenceEntry2, referenceEntry3, referenceEntry3.getKey(), n, referenceEntry3.getValueReference().get(), referenceEntry3.getValueReference(), removalCause);
                    n = this.count;
                    atomicReferenceArray.set(n2, referenceEntry);
                    this.count = n - 1;
                    return true;
                }
                referenceEntry3 = referenceEntry3.getNext();
            }
            return false;
        }

        @NullableDecl
        ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2) {
            int n = this.count;
            ReferenceEntry<K, V> referenceEntry3 = referenceEntry2.getNext();
            do {
                if (referenceEntry == referenceEntry2) {
                    this.count = n;
                    return referenceEntry3;
                }
                ReferenceEntry<K, V> referenceEntry4 = this.copyEntry(referenceEntry, referenceEntry3);
                if (referenceEntry4 != null) {
                    referenceEntry3 = referenceEntry4;
                } else {
                    this.removeCollectedEntry(referenceEntry);
                    --n;
                }
                referenceEntry = referenceEntry.getNext();
            } while (true);
        }

        boolean removeLoadingValue(K k, int n, LoadingValueReference<K, V> loadingValueReference) {
            AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray;
            ReferenceEntry<K, V> referenceEntry;
            ReferenceEntry<K, V> referenceEntry2;
            int n2;
            block7 : {
                this.lock();
                atomicReferenceArray = this.table;
                n2 = atomicReferenceArray.length() - 1 & n;
                referenceEntry = referenceEntry2 = atomicReferenceArray.get(n2);
                while (referenceEntry != null) {
                    K k2 = referenceEntry.getKey();
                    if (referenceEntry.getHash() == n && k2 != null && this.map.keyEquivalence.equivalent(k, k2)) {
                        if (referenceEntry.getValueReference() != loadingValueReference) return false;
                        if (loadingValueReference.isActive()) {
                            referenceEntry.setValueReference(loadingValueReference.getOldValue());
                            return true;
                        }
                        break block7;
                    }
                    referenceEntry = referenceEntry.getNext();
                }
                return false;
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
            atomicReferenceArray.set(n2, this.removeEntryFromChain(referenceEntry2, referenceEntry));
            return true;
        }

        @NullableDecl
        ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> referenceEntry, ReferenceEntry<K, V> referenceEntry2, @NullableDecl K k, int n, V v, ValueReference<K, V> valueReference, RemovalCause removalCause) {
            this.enqueueNotification(k, n, v, valueReference.getWeight(), removalCause);
            this.writeQueue.remove(referenceEntry2);
            this.accessQueue.remove(referenceEntry2);
            if (!valueReference.isLoading()) return this.removeEntryFromChain(referenceEntry, referenceEntry2);
            valueReference.notifyNewValue(null);
            return referenceEntry;
        }

        @NullableDecl
        V replace(K object, int n, V v) {
            V v2;
            long l;
            ValueReference<K, V> valueReference;
            ReferenceEntry<K, V> referenceEntry;
            block8 : {
                ReferenceEntry<K, V> referenceEntry2;
                this.lock();
                l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                referenceEntry = referenceEntry2 = atomicReferenceArray.get(n2);
                while (referenceEntry != null) {
                    K k = referenceEntry.getKey();
                    if (referenceEntry.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        valueReference = referenceEntry.getValueReference();
                        v2 = valueReference.get();
                        if (v2 == null) {
                            if (!valueReference.isActive()) return null;
                            ++this.modCount;
                            object = this.removeValueFromChain(referenceEntry2, referenceEntry, k, n, v2, valueReference, RemovalCause.COLLECTED);
                            n = this.count;
                            atomicReferenceArray.set(n2, (ReferenceEntry<K, V>)object);
                            this.count = n - 1;
                            return null;
                        }
                        break block8;
                    }
                    referenceEntry = referenceEntry.getNext();
                }
                return null;
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
            ++this.modCount;
            this.enqueueNotification(object, n, v2, valueReference.getWeight(), RemovalCause.REPLACED);
            this.setValue(referenceEntry, object, v, l);
            this.evictEntries(referenceEntry);
            return v2;
        }

        boolean replace(K object, int n, V v, V v2) {
            this.lock();
            try {
                ReferenceEntry<K, V> referenceEntry;
                long l = this.map.ticker.read();
                this.preWriteCleanup(l);
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.table;
                int n2 = n & atomicReferenceArray.length() - 1;
                ReferenceEntry<K, V> referenceEntry2 = referenceEntry = atomicReferenceArray.get(n2);
                while (referenceEntry2 != null) {
                    K k = referenceEntry2.getKey();
                    if (referenceEntry2.getHash() == n && k != null && this.map.keyEquivalence.equivalent(object, k)) {
                        ValueReference<K, V> valueReference = referenceEntry2.getValueReference();
                        V v3 = valueReference.get();
                        if (v3 == null) {
                            if (!valueReference.isActive()) return false;
                            ++this.modCount;
                            object = this.removeValueFromChain(referenceEntry, referenceEntry2, k, n, v3, valueReference, RemovalCause.COLLECTED);
                            n = this.count;
                            atomicReferenceArray.set(n2, (ReferenceEntry<K, V>)object);
                            this.count = n - 1;
                            return false;
                        }
                        if (this.map.valueEquivalence.equivalent(v, v3)) {
                            ++this.modCount;
                            this.enqueueNotification(object, n, v3, valueReference.getWeight(), RemovalCause.REPLACED);
                            this.setValue(referenceEntry2, object, v2, l);
                            this.evictEntries(referenceEntry2);
                            return true;
                        }
                        this.recordLockedRead(referenceEntry2, l);
                        return false;
                    }
                    referenceEntry2 = referenceEntry2.getNext();
                }
                return false;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        void runLockedCleanup(long l) {
            if (!this.tryLock()) return;
            try {
                this.drainReferenceQueues();
                this.expireEntries(l);
                this.readCount.set(0);
                return;
            }
            finally {
                this.unlock();
            }
        }

        void runUnlockedCleanup() {
            if (this.isHeldByCurrentThread()) return;
            this.map.processPendingNotifications();
        }

        V scheduleRefresh(ReferenceEntry<K, V> referenceEntry, K k, int n, V v, long l, CacheLoader<? super K, V> cacheLoader) {
            if (!this.map.refreshes()) return v;
            if (l - referenceEntry.getWriteTime() <= this.map.refreshNanos) return v;
            if (referenceEntry.getValueReference().isLoading()) return v;
            referenceEntry = this.refresh(k, n, cacheLoader, true);
            if (referenceEntry == null) return v;
            return (V)referenceEntry;
        }

        void setValue(ReferenceEntry<K, V> referenceEntry, K k, V v, long l) {
            ValueReference<K, V> valueReference = referenceEntry.getValueReference();
            int n = this.map.weigher.weigh(k, v);
            boolean bl = n >= 0;
            Preconditions.checkState(bl, "Weights must be non-negative");
            referenceEntry.setValueReference(this.map.valueStrength.referenceValue(this, referenceEntry, v, n));
            this.recordWrite(referenceEntry, n, l);
            valueReference.notifyNewValue(v);
        }

        /*
         * Exception decompiling
         */
        boolean storeLoadedValue(K var1_1, int var2_3, LoadingValueReference<K, V> var3_4, V var4_5) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[FORLOOP]], but top level block is 4[TRYBLOCK]
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

        void tryDrainReferenceQueues() {
            if (!this.tryLock()) return;
            try {
                this.drainReferenceQueues();
                return;
            }
            finally {
                this.unlock();
            }
        }

        void tryExpireEntries(long l) {
            if (!this.tryLock()) return;
            try {
                this.expireEntries(l);
                return;
            }
            finally {
                this.unlock();
            }
        }

        V waitForLoadingValue(ReferenceEntry<K, V> object, K k, ValueReference<K, V> object2) throws ExecutionException {
            if (!object2.isLoading()) throw new AssertionError();
            Preconditions.checkState(Thread.holdsLock(object) ^ true, "Recursive load of: %s", k);
            try {
                object2 = object2.waitForValue();
                if (object2 != null) {
                    this.recordRead((ReferenceEntry<K, V>)object, this.map.ticker.read());
                    return (V)object2;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("CacheLoader returned null for key ");
                ((StringBuilder)object).append(k);
                ((StringBuilder)object).append(".");
                object2 = new CacheLoader.InvalidCacheLoadException(((StringBuilder)object).toString());
                throw object2;
            }
            finally {
                this.statsCounter.recordMisses(1);
            }
        }

    }

    static class SoftValueReference<K, V>
    extends SoftReference<V>
    implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        SoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new SoftValueReference<K, V>(referenceQueue, v, referenceEntry);
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override
        public int getWeight() {
            return 1;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public void notifyNewValue(V v) {
        }

        @Override
        public V waitForValue() {
            return (V)this.get();
        }
    }

    static abstract class Strength
    extends Enum<Strength> {
        private static final /* synthetic */ Strength[] $VALUES;
        public static final /* enum */ Strength SOFT;
        public static final /* enum */ Strength STRONG;
        public static final /* enum */ Strength WEAK;

        static {
            Strength strength;
            STRONG = new Strength(){

                @Override
                Equivalence<Object> defaultEquivalence() {
                    return Equivalence.equals();
                }

                @Override
                <K, V> ValueReference<K, V> referenceValue(Segment<K, V> weightedStrongValueReference, ReferenceEntry<K, V> referenceEntry, V v, int n) {
                    if (n != 1) return new WeightedStrongValueReference(v, n);
                    return new StrongValueReference(v);
                }
            };
            SOFT = new Strength(){

                @Override
                Equivalence<Object> defaultEquivalence() {
                    return Equivalence.identity();
                }

                @Override
                <K, V> ValueReference<K, V> referenceValue(Segment<K, V> weightedSoftValueReference, ReferenceEntry<K, V> referenceEntry, V v, int n) {
                    if (n != 1) return new WeightedSoftValueReference(((Segment)weightedSoftValueReference).valueReferenceQueue, v, referenceEntry, n);
                    return new SoftValueReference(((Segment)weightedSoftValueReference).valueReferenceQueue, v, referenceEntry);
                }
            };
            WEAK = strength = new Strength(){

                @Override
                Equivalence<Object> defaultEquivalence() {
                    return Equivalence.identity();
                }

                @Override
                <K, V> ValueReference<K, V> referenceValue(Segment<K, V> weightedWeakValueReference, ReferenceEntry<K, V> referenceEntry, V v, int n) {
                    if (n != 1) return new WeightedWeakValueReference(((Segment)weightedWeakValueReference).valueReferenceQueue, v, referenceEntry, n);
                    return new WeakValueReference(((Segment)weightedWeakValueReference).valueReferenceQueue, v, referenceEntry);
                }
            };
            $VALUES = new Strength[]{STRONG, SOFT, strength};
        }

        public static Strength valueOf(String string2) {
            return Enum.valueOf(Strength.class, string2);
        }

        public static Strength[] values() {
            return (Strength[])$VALUES.clone();
        }

        abstract Equivalence<Object> defaultEquivalence();

        abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4);

    }

    static final class StrongAccessEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        StrongAccessEntry(K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    static final class StrongAccessWriteEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        StrongAccessWriteEntry(K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }
    }

    static class StrongEntry<K, V>
    extends AbstractReferenceEntry<K, V> {
        final int hash;
        final K key;
        @NullableDecl
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        StrongEntry(K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            this.key = k;
            this.hash = n;
            this.next = referenceEntry;
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
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }
    }

    static class StrongValueReference<K, V>
    implements ValueReference<K, V> {
        final V referent;

        StrongValueReference(V v) {
            this.referent = v;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        @Override
        public V get() {
            return this.referent;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override
        public int getWeight() {
            return 1;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public void notifyNewValue(V v) {
        }

        @Override
        public V waitForValue() {
            return this.get();
        }
    }

    static final class StrongWriteEntry<K, V>
    extends StrongEntry<K, V> {
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        StrongWriteEntry(K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, n, referenceEntry);
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }
    }

    final class ValueIterator
    extends LocalCache<K, V> {
        ValueIterator() {
        }

        public V next() {
            return this.nextEntry().getValue();
        }
    }

    static interface ValueReference<K, V> {
        public ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @NullableDecl V var2, ReferenceEntry<K, V> var3);

        @NullableDecl
        public V get();

        @NullableDecl
        public ReferenceEntry<K, V> getEntry();

        public int getWeight();

        public boolean isActive();

        public boolean isLoading();

        public void notifyNewValue(@NullableDecl V var1);

        public V waitForValue() throws ExecutionException;
    }

    final class Values
    extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> map;

        Values(ConcurrentMap<?, ?> concurrentMap) {
            this.map = concurrentMap;
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        @Override
        public <E> E[] toArray(E[] arrE) {
            return LocalCache.toArrayList(this).toArray(arrE);
        }
    }

    static final class WeakAccessEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        WeakAccessEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }
    }

    static final class WeakAccessWriteEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        WeakAccessWriteEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, n, referenceEntry);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setAccessTime(long l) {
            this.accessTime = l;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextAccess = referenceEntry;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousAccess = referenceEntry;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }
    }

    static class WeakEntry<K, V>
    extends WeakReference<K>
    implements ReferenceEntry<K, V> {
        final int hash;
        @NullableDecl
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        WeakEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(k, referenceQueue);
            this.hash = n;
            this.next = referenceEntry;
        }

        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
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
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAccessTime(long l) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        @Override
        public void setWriteTime(long l) {
            throw new UnsupportedOperationException();
        }
    }

    static class WeakValueReference<K, V>
    extends WeakReference<V>
    implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        WeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            super(v, referenceQueue);
            this.entry = referenceEntry;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeakValueReference<K, V>(referenceQueue, v, referenceEntry);
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override
        public int getWeight() {
            return 1;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public void notifyNewValue(V v) {
        }

        @Override
        public V waitForValue() {
            return (V)this.get();
        }
    }

    static final class WeakWriteEntry<K, V>
    extends WeakEntry<K, V> {
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        WeakWriteEntry(ReferenceQueue<K> referenceQueue, K k, int n, @NullableDecl ReferenceEntry<K, V> referenceEntry) {
            super(referenceQueue, k, n, referenceEntry);
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.nextWrite = referenceEntry;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            this.previousWrite = referenceEntry;
        }

        @Override
        public void setWriteTime(long l) {
            this.writeTime = l;
        }
    }

    static final class WeightedSoftValueReference<K, V>
    extends SoftValueReference<K, V> {
        final int weight;

        WeightedSoftValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int n) {
            super(referenceQueue, v, referenceEntry);
            this.weight = n;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedSoftValueReference<K, V>(referenceQueue, v, referenceEntry, this.weight);
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedStrongValueReference<K, V>
    extends StrongValueReference<K, V> {
        final int weight;

        WeightedStrongValueReference(V v, int n) {
            super(v);
            this.weight = n;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedWeakValueReference<K, V>
    extends WeakValueReference<K, V> {
        final int weight;

        WeightedWeakValueReference(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry, int n) {
            super(referenceQueue, v, referenceEntry);
            this.weight = n;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return new WeightedWeakValueReference<K, V>(referenceQueue, v, referenceEntry, this.weight);
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }

    static final class WriteQueue<K, V>
    extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(){
            ReferenceEntry<K, V> nextWrite = this;
            ReferenceEntry<K, V> previousWrite = this;

            @Override
            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            @Override
            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            @Override
            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            @Override
            public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.nextWrite = referenceEntry;
            }

            @Override
            public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
                this.previousWrite = referenceEntry;
            }

            @Override
            public void setWriteTime(long l) {
            }
        };

        WriteQueue() {
        }

        @Override
        public void clear() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue();
            do {
                ReferenceEntry<K, V> referenceEntry2;
                if (referenceEntry == (referenceEntry2 = this.head)) {
                    referenceEntry2.setNextInWriteQueue(referenceEntry2);
                    referenceEntry = this.head;
                    referenceEntry.setPreviousInWriteQueue(referenceEntry);
                    return;
                }
                referenceEntry2 = referenceEntry.getNextInWriteQueue();
                LocalCache.nullifyWriteOrder(referenceEntry);
                referenceEntry = referenceEntry2;
            } while (true);
        }

        @Override
        public boolean contains(Object object) {
            if (((ReferenceEntry)object).getNextInWriteQueue() == NullEntry.INSTANCE) return false;
            return true;
        }

        @Override
        public boolean isEmpty() {
            if (this.head.getNextInWriteQueue() != this.head) return false;
            return true;
        }

        @Override
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>((ReferenceEntry)this.peek()){

                @Override
                protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> referenceEntry) {
                    ReferenceEntry<K, V> referenceEntry2 = referenceEntry.getNextInWriteQueue();
                    referenceEntry = referenceEntry2;
                    if (referenceEntry2 != WriteQueue.this.head) return referenceEntry;
                    return null;
                }
            };
        }

        @Override
        public boolean offer(ReferenceEntry<K, V> referenceEntry) {
            LocalCache.connectWriteOrder(referenceEntry.getPreviousInWriteQueue(), referenceEntry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), referenceEntry);
            LocalCache.connectWriteOrder(referenceEntry, this.head);
            return true;
        }

        @Override
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> referenceEntry;
            ReferenceEntry<K, V> referenceEntry2 = referenceEntry = this.head.getNextInWriteQueue();
            if (referenceEntry != this.head) return referenceEntry2;
            return null;
        }

        @Override
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue();
            if (referenceEntry == this.head) {
                return null;
            }
            this.remove(referenceEntry);
            return referenceEntry;
        }

        @Override
        public boolean remove(Object referenceEntry) {
            ReferenceEntry referenceEntry2 = referenceEntry;
            referenceEntry = referenceEntry2.getPreviousInWriteQueue();
            ReferenceEntry referenceEntry3 = referenceEntry2.getNextInWriteQueue();
            LocalCache.connectWriteOrder(referenceEntry, referenceEntry3);
            LocalCache.nullifyWriteOrder(referenceEntry2);
            if (referenceEntry3 == NullEntry.INSTANCE) return false;
            return true;
        }

        @Override
        public int size() {
            ReferenceEntry<K, V> referenceEntry = this.head.getNextInWriteQueue();
            int n = 0;
            while (referenceEntry != this.head) {
                ++n;
                referenceEntry = referenceEntry.getNextInWriteQueue();
            }
            return n;
        }

    }

    final class WriteThroughEntry
    implements Map.Entry<K, V> {
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
            V v2 = LocalCache.this.put(this.key, v);
            this.value = v;
            return v2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getKey());
            stringBuilder.append("=");
            stringBuilder.append(this.getValue());
            return stringBuilder.toString();
        }
    }

}


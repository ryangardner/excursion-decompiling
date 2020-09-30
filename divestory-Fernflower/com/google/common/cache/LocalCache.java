package com.google.common.cache;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
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
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
   static final int CONTAINS_VALUE_RETRIES = 3;
   static final Queue<?> DISCARDING_QUEUE = new AbstractQueue<Object>() {
      public Iterator<Object> iterator() {
         return ImmutableSet.of().iterator();
      }

      public boolean offer(Object var1) {
         return true;
      }

      public Object peek() {
         return null;
      }

      public Object poll() {
         return null;
      }

      public int size() {
         return 0;
      }
   };
   static final int DRAIN_MAX = 16;
   static final int DRAIN_THRESHOLD = 63;
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final LocalCache.ValueReference<Object, Object> UNSET = new LocalCache.ValueReference<Object, Object>() {
      public LocalCache.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> var1, @NullableDecl Object var2, ReferenceEntry<Object, Object> var3) {
         return this;
      }

      public Object get() {
         return null;
      }

      public ReferenceEntry<Object, Object> getEntry() {
         return null;
      }

      public int getWeight() {
         return 0;
      }

      public boolean isActive() {
         return false;
      }

      public boolean isLoading() {
         return false;
      }

      public void notifyNewValue(Object var1) {
      }

      public Object waitForValue() {
         return null;
      }
   };
   static final Logger logger = Logger.getLogger(LocalCache.class.getName());
   final int concurrencyLevel;
   @NullableDecl
   final CacheLoader<? super K, V> defaultLoader;
   final LocalCache.EntryFactory entryFactory;
   @MonotonicNonNullDecl
   Set<Entry<K, V>> entrySet;
   final long expireAfterAccessNanos;
   final long expireAfterWriteNanos;
   final AbstractCache.StatsCounter globalStatsCounter;
   final Equivalence<Object> keyEquivalence;
   @MonotonicNonNullDecl
   Set<K> keySet;
   final LocalCache.Strength keyStrength;
   final long maxWeight;
   final long refreshNanos;
   final RemovalListener<K, V> removalListener;
   final Queue<RemovalNotification<K, V>> removalNotificationQueue;
   final int segmentMask;
   final int segmentShift;
   final LocalCache.Segment<K, V>[] segments;
   final Ticker ticker;
   final Equivalence<Object> valueEquivalence;
   final LocalCache.Strength valueStrength;
   @MonotonicNonNullDecl
   Collection<V> values;
   final Weigher<K, V> weigher;

   LocalCache(CacheBuilder<? super K, ? super V> var1, @NullableDecl CacheLoader<? super K, V> var2) {
      this.concurrencyLevel = Math.min(var1.getConcurrencyLevel(), 65536);
      this.keyStrength = var1.getKeyStrength();
      this.valueStrength = var1.getValueStrength();
      this.keyEquivalence = var1.getKeyEquivalence();
      this.valueEquivalence = var1.getValueEquivalence();
      this.maxWeight = var1.getMaximumWeight();
      this.weigher = var1.getWeigher();
      this.expireAfterAccessNanos = var1.getExpireAfterAccessNanos();
      this.expireAfterWriteNanos = var1.getExpireAfterWriteNanos();
      this.refreshNanos = var1.getRefreshNanos();
      RemovalListener var3 = var1.getRemovalListener();
      this.removalListener = var3;
      Object var21;
      if (var3 == CacheBuilder.NullListener.INSTANCE) {
         var21 = discardingQueue();
      } else {
         var21 = new ConcurrentLinkedQueue();
      }

      this.removalNotificationQueue = (Queue)var21;
      this.ticker = var1.getTicker(this.recordsTime());
      this.entryFactory = LocalCache.EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
      this.globalStatsCounter = (AbstractCache.StatsCounter)var1.getStatsCounterSupplier().get();
      this.defaultLoader = var2;
      int var4 = Math.min(var1.getInitialCapacity(), 1073741824);
      int var5 = var4;
      if (this.evictsBySize()) {
         var5 = var4;
         if (!this.customWeigher()) {
            var5 = (int)Math.min((long)var4, this.maxWeight);
         }
      }

      byte var6 = 0;
      byte var7 = 0;
      byte var8 = 1;
      var4 = 1;

      int var9;
      for(var9 = 0; var4 < this.concurrencyLevel && (!this.evictsBySize() || (long)(var4 * 20) <= this.maxWeight); var4 <<= 1) {
         ++var9;
      }

      this.segmentShift = 32 - var9;
      this.segmentMask = var4 - 1;
      this.segments = this.newSegmentArray(var4);
      int var10 = var5 / var4;
      var9 = var8;
      int var11 = var10;
      if (var10 * var4 < var5) {
         var11 = var10 + 1;
         var9 = var8;
      }

      while(var9 < var11) {
         var9 <<= 1;
      }

      var5 = var6;
      if (this.evictsBySize()) {
         long var12 = this.maxWeight;
         long var14 = (long)var4;
         long var16 = var12 / var14 + 1L;

         long var18;
         for(var5 = var7; var5 < this.segments.length; var16 = var18) {
            var18 = var16;
            if ((long)var5 == var12 % var14) {
               var18 = var16 - 1L;
            }

            this.segments[var5] = this.createSegment(var9, var18, (AbstractCache.StatsCounter)var1.getStatsCounterSupplier().get());
            ++var5;
         }
      } else {
         while(true) {
            LocalCache.Segment[] var20 = this.segments;
            if (var5 >= var20.length) {
               break;
            }

            var20[var5] = this.createSegment(var9, -1L, (AbstractCache.StatsCounter)var1.getStatsCounterSupplier().get());
            ++var5;
         }
      }

   }

   static <K, V> void connectAccessOrder(ReferenceEntry<K, V> var0, ReferenceEntry<K, V> var1) {
      var0.setNextInAccessQueue(var1);
      var1.setPreviousInAccessQueue(var0);
   }

   static <K, V> void connectWriteOrder(ReferenceEntry<K, V> var0, ReferenceEntry<K, V> var1) {
      var0.setNextInWriteQueue(var1);
      var1.setPreviousInWriteQueue(var0);
   }

   static <E> Queue<E> discardingQueue() {
      return DISCARDING_QUEUE;
   }

   static <K, V> ReferenceEntry<K, V> nullEntry() {
      return LocalCache.NullEntry.INSTANCE;
   }

   static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> var0) {
      ReferenceEntry var1 = nullEntry();
      var0.setNextInAccessQueue(var1);
      var0.setPreviousInAccessQueue(var1);
   }

   static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> var0) {
      ReferenceEntry var1 = nullEntry();
      var0.setNextInWriteQueue(var1);
      var0.setPreviousInWriteQueue(var1);
   }

   static int rehash(int var0) {
      var0 += var0 << 15 ^ -12931;
      var0 ^= var0 >>> 10;
      var0 += var0 << 3;
      var0 ^= var0 >>> 6;
      var0 += (var0 << 2) + (var0 << 14);
      return var0 ^ var0 >>> 16;
   }

   private static <E> ArrayList<E> toArrayList(Collection<E> var0) {
      ArrayList var1 = new ArrayList(var0.size());
      Iterators.addAll(var1, var0.iterator());
      return var1;
   }

   static <K, V> LocalCache.ValueReference<K, V> unset() {
      return UNSET;
   }

   public void cleanUp() {
      LocalCache.Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].cleanUp();
      }

   }

   public void clear() {
      LocalCache.Segment[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1[var3].clear();
      }

   }

   public boolean containsKey(@NullableDecl Object var1) {
      if (var1 == null) {
         return false;
      } else {
         int var2 = this.hash(var1);
         return this.segmentFor(var2).containsKey(var1, var2);
      }
   }

   public boolean containsValue(@NullableDecl Object var1) {
      if (var1 == null) {
         return false;
      } else {
         long var2 = this.ticker.read();
         LocalCache.Segment[] var4 = this.segments;
         long var5 = -1L;

         long var8;
         for(int var7 = 0; var7 < 3; var5 = var8) {
            var8 = 0L;
            int var10 = var4.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               LocalCache.Segment var12 = var4[var11];
               int var13 = var12.count;
               AtomicReferenceArray var14 = var12.table;

               for(var13 = 0; var13 < var14.length(); ++var13) {
                  for(ReferenceEntry var15 = (ReferenceEntry)var14.get(var13); var15 != null; var15 = var15.getNext()) {
                     Object var16 = var12.getLiveValue(var15, var2);
                     if (var16 != null && this.valueEquivalence.equivalent(var1, var16)) {
                        return true;
                     }
                  }
               }

               var8 += (long)var12.modCount;
            }

            if (var8 == var5) {
               break;
            }

            ++var7;
         }

         return false;
      }
   }

   ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> var1, ReferenceEntry<K, V> var2) {
      return this.segmentFor(var1.getHash()).copyEntry(var1, var2);
   }

   LocalCache.Segment<K, V> createSegment(int var1, long var2, AbstractCache.StatsCounter var4) {
      return new LocalCache.Segment(this, var1, var2, var4);
   }

   boolean customWeigher() {
      boolean var1;
      if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Set<Entry<K, V>> entrySet() {
      Object var1 = this.entrySet;
      if (var1 == null) {
         var1 = new LocalCache.EntrySet(this);
         this.entrySet = (Set)var1;
      }

      return (Set)var1;
   }

   boolean evictsBySize() {
      boolean var1;
      if (this.maxWeight >= 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean expires() {
      boolean var1;
      if (!this.expiresAfterWrite() && !this.expiresAfterAccess()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   boolean expiresAfterAccess() {
      boolean var1;
      if (this.expireAfterAccessNanos > 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean expiresAfterWrite() {
      boolean var1;
      if (this.expireAfterWriteNanos > 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @NullableDecl
   public V get(@NullableDecl Object var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this.hash(var1);
         return this.segmentFor(var2).get(var1, var2);
      }
   }

   V get(K var1, CacheLoader<? super K, V> var2) throws ExecutionException {
      int var3 = this.hash(Preconditions.checkNotNull(var1));
      return this.segmentFor(var3).get(var1, var3, var2);
   }

   ImmutableMap<K, V> getAll(Iterable<? extends K> param1) throws ExecutionException {
      // $FF: Couldn't be decompiled
   }

   ImmutableMap<K, V> getAllPresent(Iterable<?> var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap();
      Iterator var3 = var1.iterator();
      int var4 = 0;
      int var5 = 0;

      while(var3.hasNext()) {
         Object var7 = var3.next();
         Object var6 = this.get(var7);
         if (var6 == null) {
            ++var5;
         } else {
            var2.put(var7, var6);
            ++var4;
         }
      }

      this.globalStatsCounter.recordHits(var4);
      this.globalStatsCounter.recordMisses(var5);
      return ImmutableMap.copyOf((Map)var2);
   }

   ReferenceEntry<K, V> getEntry(@NullableDecl Object var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this.hash(var1);
         return this.segmentFor(var2).getEntry(var1, var2);
      }
   }

   @NullableDecl
   public V getIfPresent(Object var1) {
      int var2 = this.hash(Preconditions.checkNotNull(var1));
      var1 = this.segmentFor(var2).get(var1, var2);
      if (var1 == null) {
         this.globalStatsCounter.recordMisses(1);
      } else {
         this.globalStatsCounter.recordHits(1);
      }

      return var1;
   }

   @NullableDecl
   V getLiveValue(ReferenceEntry<K, V> var1, long var2) {
      if (var1.getKey() == null) {
         return null;
      } else {
         Object var4 = var1.getValueReference().get();
         if (var4 == null) {
            return null;
         } else {
            return this.isExpired(var1, var2) ? null : var4;
         }
      }
   }

   @NullableDecl
   public V getOrDefault(@NullableDecl Object var1, @NullableDecl V var2) {
      var1 = this.get(var1);
      if (var1 != null) {
         var2 = var1;
      }

      return var2;
   }

   V getOrLoad(K var1) throws ExecutionException {
      return this.get(var1, this.defaultLoader);
   }

   int hash(@NullableDecl Object var1) {
      return rehash(this.keyEquivalence.hash(var1));
   }

   void invalidateAll(Iterable<?> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.remove(var2.next());
      }

   }

   public boolean isEmpty() {
      LocalCache.Segment[] var1 = this.segments;
      boolean var2 = false;
      long var3 = 0L;

      int var5;
      for(var5 = 0; var5 < var1.length; ++var5) {
         if (var1[var5].count != 0) {
            return false;
         }

         var3 += (long)var1[var5].modCount;
      }

      if (var3 != 0L) {
         for(var5 = 0; var5 < var1.length; ++var5) {
            if (var1[var5].count != 0) {
               return false;
            }

            var3 -= (long)var1[var5].modCount;
         }

         if (var3 == 0L) {
            var2 = true;
         }

         return var2;
      } else {
         return true;
      }
   }

   boolean isExpired(ReferenceEntry<K, V> var1, long var2) {
      Preconditions.checkNotNull(var1);
      if (this.expiresAfterAccess() && var2 - var1.getAccessTime() >= this.expireAfterAccessNanos) {
         return true;
      } else {
         return this.expiresAfterWrite() && var2 - var1.getWriteTime() >= this.expireAfterWriteNanos;
      }
   }

   boolean isLive(ReferenceEntry<K, V> var1, long var2) {
      boolean var4;
      if (this.segmentFor(var1.getHash()).getLiveValue(var1, var2) != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public Set<K> keySet() {
      Object var1 = this.keySet;
      if (var1 == null) {
         var1 = new LocalCache.KeySet(this);
         this.keySet = (Set)var1;
      }

      return (Set)var1;
   }

   @NullableDecl
   Map<K, V> loadAll(Set<? extends K> var1, CacheLoader<? super K, V> var2) throws ExecutionException {
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var1);
      Stopwatch var3 = Stopwatch.createStarted();
      boolean var4 = true;
      boolean var5 = false;

      Map var6;
      label201: {
         CacheLoader.UnsupportedLoadingOperationException var31;
         try {
            ExecutionException var33;
            try {
               var6 = var2.loadAll(var1);
               break label201;
            } catch (CacheLoader.UnsupportedLoadingOperationException var24) {
               var31 = var24;
            } catch (InterruptedException var25) {
               Thread.currentThread().interrupt();
               var33 = new ExecutionException(var25);
               throw var33;
            } catch (RuntimeException var26) {
               UncheckedExecutionException var35 = new UncheckedExecutionException(var26);
               throw var35;
            } catch (Exception var27) {
               var33 = new ExecutionException(var27);
               throw var33;
            } catch (Error var28) {
               ExecutionError var30 = new ExecutionError(var28);
               throw var30;
            }
         } finally {
            ;
         }

         try {
            throw var31;
         } finally {
            if (!var4) {
               this.globalStatsCounter.recordLoadException(var3.elapsed(TimeUnit.NANOSECONDS));
            }

            throw var31;
         }
      }

      StringBuilder var34;
      if (var6 == null) {
         this.globalStatsCounter.recordLoadException(var3.elapsed(TimeUnit.NANOSECONDS));
         var34 = new StringBuilder();
         var34.append(var2);
         var34.append(" returned null map from loadAll");
         throw new CacheLoader.InvalidCacheLoadException(var34.toString());
      } else {
         var3.stop();
         Iterator var7 = var6.entrySet().iterator();
         var4 = var5;

         while(true) {
            while(var7.hasNext()) {
               Entry var8 = (Entry)var7.next();
               Object var32 = var8.getKey();
               Object var36 = var8.getValue();
               if (var32 != null && var36 != null) {
                  this.put(var32, var36);
               } else {
                  var4 = true;
               }
            }

            if (!var4) {
               this.globalStatsCounter.recordLoadSuccess(var3.elapsed(TimeUnit.NANOSECONDS));
               return var6;
            }

            this.globalStatsCounter.recordLoadException(var3.elapsed(TimeUnit.NANOSECONDS));
            var34 = new StringBuilder();
            var34.append(var2);
            var34.append(" returned null keys or values from loadAll");
            throw new CacheLoader.InvalidCacheLoadException(var34.toString());
         }
      }
   }

   long longSize() {
      LocalCache.Segment[] var1 = this.segments;
      long var2 = 0L;

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var2 += (long)Math.max(0, var1[var4].count);
      }

      return var2;
   }

   ReferenceEntry<K, V> newEntry(K var1, int var2, @NullableDecl ReferenceEntry<K, V> var3) {
      LocalCache.Segment var4 = this.segmentFor(var2);
      var4.lock();

      ReferenceEntry var7;
      try {
         var7 = var4.newEntry(var1, var2, var3);
      } finally {
         var4.unlock();
      }

      return var7;
   }

   final LocalCache.Segment<K, V>[] newSegmentArray(int var1) {
      return new LocalCache.Segment[var1];
   }

   LocalCache.ValueReference<K, V> newValueReference(ReferenceEntry<K, V> var1, V var2, int var3) {
      int var4 = var1.getHash();
      return this.valueStrength.referenceValue(this.segmentFor(var4), var1, Preconditions.checkNotNull(var2), var3);
   }

   void processPendingNotifications() {
      while(true) {
         RemovalNotification var1 = (RemovalNotification)this.removalNotificationQueue.poll();
         if (var1 == null) {
            return;
         }

         try {
            this.removalListener.onRemoval(var1);
         } catch (Throwable var3) {
            logger.log(Level.WARNING, "Exception thrown by removal listener", var3);
            continue;
         }
      }
   }

   public V put(K var1, V var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      int var3 = this.hash(var1);
      return this.segmentFor(var3).put(var1, var3, var2, false);
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         this.put(var3.getKey(), var3.getValue());
      }

   }

   public V putIfAbsent(K var1, V var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      int var3 = this.hash(var1);
      return this.segmentFor(var3).put(var1, var3, var2, true);
   }

   void reclaimKey(ReferenceEntry<K, V> var1) {
      int var2 = var1.getHash();
      this.segmentFor(var2).reclaimKey(var1, var2);
   }

   void reclaimValue(LocalCache.ValueReference<K, V> var1) {
      ReferenceEntry var2 = var1.getEntry();
      int var3 = var2.getHash();
      this.segmentFor(var3).reclaimValue(var2.getKey(), var3, var1);
   }

   boolean recordsAccess() {
      return this.expiresAfterAccess();
   }

   boolean recordsTime() {
      boolean var1;
      if (!this.recordsWrite() && !this.recordsAccess()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   boolean recordsWrite() {
      boolean var1;
      if (!this.expiresAfterWrite() && !this.refreshes()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   void refresh(K var1) {
      int var2 = this.hash(Preconditions.checkNotNull(var1));
      this.segmentFor(var2).refresh(var1, var2, this.defaultLoader, false);
   }

   boolean refreshes() {
      boolean var1;
      if (this.refreshNanos > 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public V remove(@NullableDecl Object var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this.hash(var1);
         return this.segmentFor(var2).remove(var1, var2);
      }
   }

   public boolean remove(@NullableDecl Object var1, @NullableDecl Object var2) {
      if (var1 != null && var2 != null) {
         int var3 = this.hash(var1);
         return this.segmentFor(var3).remove(var1, var3, var2);
      } else {
         return false;
      }
   }

   public V replace(K var1, V var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      int var3 = this.hash(var1);
      return this.segmentFor(var3).replace(var1, var3, var2);
   }

   public boolean replace(K var1, @NullableDecl V var2, V var3) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var3);
      if (var2 == null) {
         return false;
      } else {
         int var4 = this.hash(var1);
         return this.segmentFor(var4).replace(var1, var4, var2, var3);
      }
   }

   LocalCache.Segment<K, V> segmentFor(int var1) {
      return this.segments[var1 >>> this.segmentShift & this.segmentMask];
   }

   public int size() {
      return Ints.saturatedCast(this.longSize());
   }

   boolean usesAccessEntries() {
      boolean var1;
      if (!this.usesAccessQueue() && !this.recordsAccess()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   boolean usesAccessQueue() {
      boolean var1;
      if (!this.expiresAfterAccess() && !this.evictsBySize()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   boolean usesKeyReferences() {
      boolean var1;
      if (this.keyStrength != LocalCache.Strength.STRONG) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean usesValueReferences() {
      boolean var1;
      if (this.valueStrength != LocalCache.Strength.STRONG) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   boolean usesWriteEntries() {
      boolean var1;
      if (!this.usesWriteQueue() && !this.recordsWrite()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   boolean usesWriteQueue() {
      return this.expiresAfterWrite();
   }

   public Collection<V> values() {
      Object var1 = this.values;
      if (var1 == null) {
         var1 = new LocalCache.Values(this);
         this.values = (Collection)var1;
      }

      return (Collection)var1;
   }

   abstract class AbstractCacheSet<T> extends AbstractSet<T> {
      final ConcurrentMap<?, ?> map;

      AbstractCacheSet(ConcurrentMap<?, ?> var2) {
         this.map = var2;
      }

      public void clear() {
         this.map.clear();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public int size() {
         return this.map.size();
      }

      public Object[] toArray() {
         return LocalCache.toArrayList(this).toArray();
      }

      public <E> E[] toArray(E[] var1) {
         return LocalCache.toArrayList(this).toArray(var1);
      }
   }

   abstract static class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
      public long getAccessTime() {
         throw new UnsupportedOperationException();
      }

      public int getHash() {
         throw new UnsupportedOperationException();
      }

      public K getKey() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getNext() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getNextInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getNextInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getPreviousInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getPreviousInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public LocalCache.ValueReference<K, V> getValueReference() {
         throw new UnsupportedOperationException();
      }

      public long getWriteTime() {
         throw new UnsupportedOperationException();
      }

      public void setAccessTime(long var1) {
         throw new UnsupportedOperationException();
      }

      public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setValueReference(LocalCache.ValueReference<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setWriteTime(long var1) {
         throw new UnsupportedOperationException();
      }
   }

   static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
      final ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry<K, V>() {
         ReferenceEntry<K, V> nextAccess = this;
         ReferenceEntry<K, V> previousAccess = this;

         public long getAccessTime() {
            return Long.MAX_VALUE;
         }

         public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
         }

         public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
         }

         public void setAccessTime(long var1) {
         }

         public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
            this.nextAccess = var1;
         }

         public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
            this.previousAccess = var1;
         }
      };

      public void clear() {
         ReferenceEntry var1 = this.head.getNextInAccessQueue();

         while(true) {
            ReferenceEntry var2 = this.head;
            if (var1 == var2) {
               var2.setNextInAccessQueue(var2);
               var1 = this.head;
               var1.setPreviousInAccessQueue(var1);
               return;
            }

            var2 = var1.getNextInAccessQueue();
            LocalCache.nullifyAccessOrder(var1);
            var1 = var2;
         }
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (((ReferenceEntry)var1).getNextInAccessQueue() != LocalCache.NullEntry.INSTANCE) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean isEmpty() {
         boolean var1;
         if (this.head.getNextInAccessQueue() == this.head) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Iterator<ReferenceEntry<K, V>> iterator() {
         return new AbstractSequentialIterator<ReferenceEntry<K, V>>(this.peek()) {
            protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> var1) {
               ReferenceEntry var2 = var1.getNextInAccessQueue();
               var1 = var2;
               if (var2 == AccessQueue.this.head) {
                  var1 = null;
               }

               return var1;
            }
         };
      }

      public boolean offer(ReferenceEntry<K, V> var1) {
         LocalCache.connectAccessOrder(var1.getPreviousInAccessQueue(), var1.getNextInAccessQueue());
         LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), var1);
         LocalCache.connectAccessOrder(var1, this.head);
         return true;
      }

      public ReferenceEntry<K, V> peek() {
         ReferenceEntry var1 = this.head.getNextInAccessQueue();
         ReferenceEntry var2 = var1;
         if (var1 == this.head) {
            var2 = null;
         }

         return var2;
      }

      public ReferenceEntry<K, V> poll() {
         ReferenceEntry var1 = this.head.getNextInAccessQueue();
         if (var1 == this.head) {
            return null;
         } else {
            this.remove(var1);
            return var1;
         }
      }

      public boolean remove(Object var1) {
         ReferenceEntry var2 = (ReferenceEntry)var1;
         ReferenceEntry var3 = var2.getPreviousInAccessQueue();
         ReferenceEntry var5 = var2.getNextInAccessQueue();
         LocalCache.connectAccessOrder(var3, var5);
         LocalCache.nullifyAccessOrder(var2);
         boolean var4;
         if (var5 != LocalCache.NullEntry.INSTANCE) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      public int size() {
         ReferenceEntry var1 = this.head.getNextInAccessQueue();

         int var2;
         for(var2 = 0; var1 != this.head; var1 = var1.getNextInAccessQueue()) {
            ++var2;
         }

         return var2;
      }
   }

   static enum EntryFactory {
      static final int ACCESS_MASK = 1;
      STRONG {
         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.StrongEntry(var2, var3, var4);
         }
      },
      STRONG_ACCESS {
         <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
            ReferenceEntry var4 = super.copyEntry(var1, var2, var3);
            this.copyAccessEntry(var2, var4);
            return var4;
         }

         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.StrongAccessEntry(var2, var3, var4);
         }
      },
      STRONG_ACCESS_WRITE {
         <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
            ReferenceEntry var4 = super.copyEntry(var1, var2, var3);
            this.copyAccessEntry(var2, var4);
            this.copyWriteEntry(var2, var4);
            return var4;
         }

         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.StrongAccessWriteEntry(var2, var3, var4);
         }
      },
      STRONG_WRITE {
         <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
            ReferenceEntry var4 = super.copyEntry(var1, var2, var3);
            this.copyWriteEntry(var2, var4);
            return var4;
         }

         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.StrongWriteEntry(var2, var3, var4);
         }
      },
      WEAK {
         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.WeakEntry(var1.keyReferenceQueue, var2, var3, var4);
         }
      },
      WEAK_ACCESS {
         <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
            ReferenceEntry var4 = super.copyEntry(var1, var2, var3);
            this.copyAccessEntry(var2, var4);
            return var4;
         }

         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.WeakAccessEntry(var1.keyReferenceQueue, var2, var3, var4);
         }
      },
      WEAK_ACCESS_WRITE;

      static final int WEAK_MASK = 4;
      WEAK_WRITE {
         <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
            ReferenceEntry var4 = super.copyEntry(var1, var2, var3);
            this.copyWriteEntry(var2, var4);
            return var4;
         }

         <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
            return new LocalCache.WeakWriteEntry(var1.keyReferenceQueue, var2, var3, var4);
         }
      };

      static final int WRITE_MASK = 2;
      static final LocalCache.EntryFactory[] factories;

      static {
         LocalCache.EntryFactory var0 = new LocalCache.EntryFactory("WEAK_ACCESS_WRITE", 7) {
            <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
               ReferenceEntry var4 = super.copyEntry(var1, var2, var3);
               this.copyAccessEntry(var2, var4);
               this.copyWriteEntry(var2, var4);
               return var4;
            }

            <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
               return new LocalCache.WeakAccessWriteEntry(var1.keyReferenceQueue, var2, var3, var4);
            }
         };
         WEAK_ACCESS_WRITE = var0;
         LocalCache.EntryFactory var1 = STRONG;
         LocalCache.EntryFactory var2 = STRONG_ACCESS;
         LocalCache.EntryFactory var3 = STRONG_WRITE;
         LocalCache.EntryFactory var4 = STRONG_ACCESS_WRITE;
         LocalCache.EntryFactory var5 = WEAK;
         LocalCache.EntryFactory var6 = WEAK_ACCESS;
         LocalCache.EntryFactory var7 = WEAK_WRITE;
         factories = new LocalCache.EntryFactory[]{var1, var2, var3, var4, var5, var6, var7, var0};
      }

      private EntryFactory() {
      }

      // $FF: synthetic method
      EntryFactory(Object var3) {
         this();
      }

      static LocalCache.EntryFactory getFactory(LocalCache.Strength var0, boolean var1, boolean var2) {
         LocalCache.Strength var3 = LocalCache.Strength.WEAK;
         byte var4 = 0;
         byte var5;
         if (var0 == var3) {
            var5 = 4;
         } else {
            var5 = 0;
         }

         if (var2) {
            var4 = 2;
         }

         return factories[var5 | var1 | var4];
      }

      <K, V> void copyAccessEntry(ReferenceEntry<K, V> var1, ReferenceEntry<K, V> var2) {
         var2.setAccessTime(var1.getAccessTime());
         LocalCache.connectAccessOrder(var1.getPreviousInAccessQueue(), var2);
         LocalCache.connectAccessOrder(var2, var1.getNextInAccessQueue());
         LocalCache.nullifyAccessOrder(var1);
      }

      <K, V> ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, ReferenceEntry<K, V> var3) {
         return this.newEntry(var1, var2.getKey(), var2.getHash(), var3);
      }

      <K, V> void copyWriteEntry(ReferenceEntry<K, V> var1, ReferenceEntry<K, V> var2) {
         var2.setWriteTime(var1.getWriteTime());
         LocalCache.connectWriteOrder(var1.getPreviousInWriteQueue(), var2);
         LocalCache.connectWriteOrder(var2, var1.getNextInWriteQueue());
         LocalCache.nullifyWriteOrder(var1);
      }

      abstract <K, V> ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4);
   }

   final class EntryIterator extends LocalCache<K, V>.HashIterator<Entry<K, V>> {
      EntryIterator() {
         super();
      }

      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Entry<K, V>> {
      EntrySet(ConcurrentMap<?, ?> var2) {
         super(var2);
      }

      public boolean contains(Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         if (!var2) {
            return false;
         } else {
            Entry var5 = (Entry)var1;
            Object var4 = var5.getKey();
            if (var4 == null) {
               return false;
            } else {
               var4 = LocalCache.this.get(var4);
               var2 = var3;
               if (var4 != null) {
                  var2 = var3;
                  if (LocalCache.this.valueEquivalence.equivalent(var5.getValue(), var4)) {
                     var2 = true;
                  }
               }

               return var2;
            }
         }
      }

      public Iterator<Entry<K, V>> iterator() {
         return LocalCache.this.new EntryIterator();
      }

      public boolean remove(Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         if (!var2) {
            return false;
         } else {
            Entry var4 = (Entry)var1;
            var1 = var4.getKey();
            var2 = var3;
            if (var1 != null) {
               var2 = var3;
               if (LocalCache.this.remove(var1, var4.getValue())) {
                  var2 = true;
               }
            }

            return var2;
         }
      }
   }

   abstract class HashIterator<T> implements Iterator<T> {
      @MonotonicNonNullDecl
      LocalCache.Segment<K, V> currentSegment;
      @MonotonicNonNullDecl
      AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
      @NullableDecl
      LocalCache<K, V>.WriteThroughEntry lastReturned;
      @NullableDecl
      ReferenceEntry<K, V> nextEntry;
      @NullableDecl
      LocalCache<K, V>.WriteThroughEntry nextExternal;
      int nextSegmentIndex;
      int nextTableIndex;

      HashIterator() {
         this.nextSegmentIndex = LocalCache.this.segments.length - 1;
         this.nextTableIndex = -1;
         this.advance();
      }

      final void advance() {
         this.nextExternal = null;
         if (!this.nextInChain()) {
            if (!this.nextInTable()) {
               while(this.nextSegmentIndex >= 0) {
                  LocalCache.Segment[] var1 = LocalCache.this.segments;
                  int var2 = this.nextSegmentIndex--;
                  LocalCache.Segment var3 = var1[var2];
                  this.currentSegment = var3;
                  if (var3.count != 0) {
                     AtomicReferenceArray var4 = this.currentSegment.table;
                     this.currentTable = var4;
                     this.nextTableIndex = var4.length() - 1;
                     if (this.nextInTable()) {
                        break;
                     }
                  }
               }

            }
         }
      }

      boolean advanceTo(ReferenceEntry<K, V> param1) {
         // $FF: Couldn't be decompiled
      }

      public boolean hasNext() {
         boolean var1;
         if (this.nextExternal != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public abstract T next();

      LocalCache<K, V>.WriteThroughEntry nextEntry() {
         LocalCache.WriteThroughEntry var1 = this.nextExternal;
         if (var1 != null) {
            this.lastReturned = var1;
            this.advance();
            return this.lastReturned;
         } else {
            throw new NoSuchElementException();
         }
      }

      boolean nextInChain() {
         ReferenceEntry var1 = this.nextEntry;
         if (var1 != null) {
            while(true) {
               this.nextEntry = var1.getNext();
               var1 = this.nextEntry;
               if (var1 == null) {
                  break;
               }

               if (this.advanceTo(var1)) {
                  return true;
               }

               var1 = this.nextEntry;
            }
         }

         return false;
      }

      boolean nextInTable() {
         while(true) {
            int var1 = this.nextTableIndex;
            if (var1 >= 0) {
               AtomicReferenceArray var2 = this.currentTable;
               this.nextTableIndex = var1 - 1;
               ReferenceEntry var3 = (ReferenceEntry)var2.get(var1);
               this.nextEntry = var3;
               if (var3 == null || !this.advanceTo(var3) && !this.nextInChain()) {
                  continue;
               }

               return true;
            }

            return false;
         }
      }

      public void remove() {
         boolean var1;
         if (this.lastReturned != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1);
         LocalCache.this.remove(this.lastReturned.getKey());
         this.lastReturned = null;
      }
   }

   final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
      KeyIterator() {
         super();
      }

      public K next() {
         return this.nextEntry().getKey();
      }
   }

   final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
      KeySet(ConcurrentMap<?, ?> var2) {
         super(var2);
      }

      public boolean contains(Object var1) {
         return this.map.containsKey(var1);
      }

      public Iterator<K> iterator() {
         return LocalCache.this.new KeyIterator();
      }

      public boolean remove(Object var1) {
         boolean var2;
         if (this.map.remove(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   static final class LoadingSerializationProxy<K, V> extends LocalCache.ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
      private static final long serialVersionUID = 1L;
      @MonotonicNonNullDecl
      transient LoadingCache<K, V> autoDelegate;

      LoadingSerializationProxy(LocalCache<K, V> var1) {
         super(var1);
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         this.autoDelegate = this.recreateCacheBuilder().build(this.loader);
      }

      private Object readResolve() {
         return this.autoDelegate;
      }

      public final V apply(K var1) {
         return this.autoDelegate.apply(var1);
      }

      public V get(K var1) throws ExecutionException {
         return this.autoDelegate.get(var1);
      }

      public ImmutableMap<K, V> getAll(Iterable<? extends K> var1) throws ExecutionException {
         return this.autoDelegate.getAll(var1);
      }

      public V getUnchecked(K var1) {
         return this.autoDelegate.getUnchecked(var1);
      }

      public void refresh(K var1) {
         this.autoDelegate.refresh(var1);
      }
   }

   static class LoadingValueReference<K, V> implements LocalCache.ValueReference<K, V> {
      final SettableFuture<V> futureValue;
      volatile LocalCache.ValueReference<K, V> oldValue;
      final Stopwatch stopwatch;

      public LoadingValueReference() {
         this(LocalCache.UNSET);
      }

      public LoadingValueReference(LocalCache.ValueReference<K, V> var1) {
         this.futureValue = SettableFuture.create();
         this.stopwatch = Stopwatch.createUnstarted();
         this.oldValue = var1;
      }

      private ListenableFuture<V> fullyFailedFuture(Throwable var1) {
         return Futures.immediateFailedFuture(var1);
      }

      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @NullableDecl V var2, ReferenceEntry<K, V> var3) {
         return this;
      }

      public long elapsedNanos() {
         return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
      }

      public V get() {
         return this.oldValue.get();
      }

      public ReferenceEntry<K, V> getEntry() {
         return null;
      }

      public LocalCache.ValueReference<K, V> getOldValue() {
         return this.oldValue;
      }

      public int getWeight() {
         return this.oldValue.getWeight();
      }

      public boolean isActive() {
         return this.oldValue.isActive();
      }

      public boolean isLoading() {
         return true;
      }

      public ListenableFuture<V> loadFuture(K var1, CacheLoader<? super K, V> var2) {
         Throwable var10000;
         label447: {
            Object var3;
            boolean var10001;
            try {
               this.stopwatch.start();
               var3 = this.oldValue.get();
            } catch (Throwable var59) {
               var10000 = var59;
               var10001 = false;
               break label447;
            }

            if (var3 == null) {
               label435: {
                  label434: {
                     try {
                        var1 = var2.load(var1);
                        if (this.set(var1)) {
                           var1 = this.futureValue;
                           break label434;
                        }
                     } catch (Throwable var55) {
                        var10000 = var55;
                        var10001 = false;
                        break label435;
                     }

                     try {
                        var1 = Futures.immediateFuture(var1);
                     } catch (Throwable var54) {
                        var10000 = var54;
                        var10001 = false;
                        break label435;
                     }
                  }

                  label426:
                  try {
                     return (ListenableFuture)var1;
                  } catch (Throwable var53) {
                     var10000 = var53;
                     var10001 = false;
                     break label426;
                  }
               }
            } else {
               label443: {
                  ListenableFuture var61;
                  try {
                     var61 = var2.reload(var1, var3);
                  } catch (Throwable var58) {
                     var10000 = var58;
                     var10001 = false;
                     break label443;
                  }

                  if (var61 == null) {
                     label437:
                     try {
                        return Futures.immediateFuture((Object)null);
                     } catch (Throwable var56) {
                        var10000 = var56;
                        var10001 = false;
                        break label437;
                     }
                  } else {
                     label439:
                     try {
                        Function var62 = new Function<V, V>() {
                           public V apply(V var1) {
                              LoadingValueReference.this.set(var1);
                              return var1;
                           }
                        };
                        var61 = Futures.transform(var61, var62, MoreExecutors.directExecutor());
                        return var61;
                     } catch (Throwable var57) {
                        var10000 = var57;
                        var10001 = false;
                        break label439;
                     }
                  }
               }
            }
         }

         Throwable var60 = var10000;
         if (this.setException(var60)) {
            var1 = this.futureValue;
         } else {
            var1 = this.fullyFailedFuture(var60);
         }

         if (var60 instanceof InterruptedException) {
            Thread.currentThread().interrupt();
         }

         return (ListenableFuture)var1;
      }

      public void notifyNewValue(@NullableDecl V var1) {
         if (var1 != null) {
            this.set(var1);
         } else {
            this.oldValue = LocalCache.UNSET;
         }

      }

      public boolean set(@NullableDecl V var1) {
         return this.futureValue.set(var1);
      }

      public boolean setException(Throwable var1) {
         return this.futureValue.setException(var1);
      }

      public V waitForValue() throws ExecutionException {
         return Uninterruptibles.getUninterruptibly(this.futureValue);
      }
   }

   static class LocalLoadingCache<K, V> extends LocalCache.LocalManualCache<K, V> implements LoadingCache<K, V> {
      private static final long serialVersionUID = 1L;

      LocalLoadingCache(CacheBuilder<? super K, ? super V> var1, CacheLoader<? super K, V> var2) {
         super(new LocalCache(var1, (CacheLoader)Preconditions.checkNotNull(var2)), null);
      }

      public final V apply(K var1) {
         return this.getUnchecked(var1);
      }

      public V get(K var1) throws ExecutionException {
         return this.localCache.getOrLoad(var1);
      }

      public ImmutableMap<K, V> getAll(Iterable<? extends K> var1) throws ExecutionException {
         return this.localCache.getAll(var1);
      }

      public V getUnchecked(K var1) {
         try {
            var1 = this.get(var1);
            return var1;
         } catch (ExecutionException var2) {
            throw new UncheckedExecutionException(var2.getCause());
         }
      }

      public void refresh(K var1) {
         this.localCache.refresh(var1);
      }

      Object writeReplace() {
         return new LocalCache.LoadingSerializationProxy(this.localCache);
      }
   }

   static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
      private static final long serialVersionUID = 1L;
      final LocalCache<K, V> localCache;

      LocalManualCache(CacheBuilder<? super K, ? super V> var1) {
         this(new LocalCache(var1, (CacheLoader)null));
      }

      private LocalManualCache(LocalCache<K, V> var1) {
         this.localCache = var1;
      }

      // $FF: synthetic method
      LocalManualCache(LocalCache var1, Object var2) {
         this(var1);
      }

      public ConcurrentMap<K, V> asMap() {
         return this.localCache;
      }

      public void cleanUp() {
         this.localCache.cleanUp();
      }

      public V get(K var1, final Callable<? extends V> var2) throws ExecutionException {
         Preconditions.checkNotNull(var2);
         return this.localCache.get(var1, new CacheLoader<Object, V>() {
            public V load(Object var1) throws Exception {
               return var2.call();
            }
         });
      }

      public ImmutableMap<K, V> getAllPresent(Iterable<?> var1) {
         return this.localCache.getAllPresent(var1);
      }

      @NullableDecl
      public V getIfPresent(Object var1) {
         return this.localCache.getIfPresent(var1);
      }

      public void invalidate(Object var1) {
         Preconditions.checkNotNull(var1);
         this.localCache.remove(var1);
      }

      public void invalidateAll() {
         this.localCache.clear();
      }

      public void invalidateAll(Iterable<?> var1) {
         this.localCache.invalidateAll(var1);
      }

      public void put(K var1, V var2) {
         this.localCache.put(var1, var2);
      }

      public void putAll(Map<? extends K, ? extends V> var1) {
         this.localCache.putAll(var1);
      }

      public long size() {
         return this.localCache.longSize();
      }

      public CacheStats stats() {
         AbstractCache.SimpleStatsCounter var1 = new AbstractCache.SimpleStatsCounter();
         var1.incrementBy(this.localCache.globalStatsCounter);
         LocalCache.Segment[] var2 = this.localCache.segments;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            var1.incrementBy(var2[var4].statsCounter);
         }

         return var1.snapshot();
      }

      Object writeReplace() {
         return new LocalCache.ManualSerializationProxy(this.localCache);
      }
   }

   static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
      private static final long serialVersionUID = 1L;
      final int concurrencyLevel;
      @MonotonicNonNullDecl
      transient Cache<K, V> delegate;
      final long expireAfterAccessNanos;
      final long expireAfterWriteNanos;
      final Equivalence<Object> keyEquivalence;
      final LocalCache.Strength keyStrength;
      final CacheLoader<? super K, V> loader;
      final long maxWeight;
      final RemovalListener<? super K, ? super V> removalListener;
      @NullableDecl
      final Ticker ticker;
      final Equivalence<Object> valueEquivalence;
      final LocalCache.Strength valueStrength;
      final Weigher<K, V> weigher;

      private ManualSerializationProxy(LocalCache.Strength var1, LocalCache.Strength var2, Equivalence<Object> var3, Equivalence<Object> var4, long var5, long var7, long var9, Weigher<K, V> var11, int var12, RemovalListener<? super K, ? super V> var13, Ticker var14, CacheLoader<? super K, V> var15) {
         Ticker var16;
         label11: {
            super();
            this.keyStrength = var1;
            this.valueStrength = var2;
            this.keyEquivalence = var3;
            this.valueEquivalence = var4;
            this.expireAfterWriteNanos = var5;
            this.expireAfterAccessNanos = var7;
            this.maxWeight = var9;
            this.weigher = var11;
            this.concurrencyLevel = var12;
            this.removalListener = var13;
            if (var14 != Ticker.systemTicker()) {
               var16 = var14;
               if (var14 != CacheBuilder.NULL_TICKER) {
                  break label11;
               }
            }

            var16 = null;
         }

         this.ticker = var16;
         this.loader = var15;
      }

      ManualSerializationProxy(LocalCache<K, V> var1) {
         this(var1.keyStrength, var1.valueStrength, var1.keyEquivalence, var1.valueEquivalence, var1.expireAfterWriteNanos, var1.expireAfterAccessNanos, var1.maxWeight, var1.weigher, var1.concurrencyLevel, var1.removalListener, var1.ticker, var1.defaultLoader);
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         this.delegate = this.recreateCacheBuilder().build();
      }

      private Object readResolve() {
         return this.delegate;
      }

      protected Cache<K, V> delegate() {
         return this.delegate;
      }

      CacheBuilder<K, V> recreateCacheBuilder() {
         CacheBuilder var1 = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
         var1.strictParsing = false;
         long var2 = this.expireAfterWriteNanos;
         if (var2 > 0L) {
            var1.expireAfterWrite(var2, TimeUnit.NANOSECONDS);
         }

         var2 = this.expireAfterAccessNanos;
         if (var2 > 0L) {
            var1.expireAfterAccess(var2, TimeUnit.NANOSECONDS);
         }

         if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
            var1.weigher(this.weigher);
            var2 = this.maxWeight;
            if (var2 != -1L) {
               var1.maximumWeight(var2);
            }
         } else {
            var2 = this.maxWeight;
            if (var2 != -1L) {
               var1.maximumSize(var2);
            }
         }

         Ticker var4 = this.ticker;
         if (var4 != null) {
            var1.ticker(var4);
         }

         return var1;
      }
   }

   private static enum NullEntry implements ReferenceEntry<Object, Object> {
      INSTANCE;

      static {
         LocalCache.NullEntry var0 = new LocalCache.NullEntry("INSTANCE", 0);
         INSTANCE = var0;
      }

      public long getAccessTime() {
         return 0L;
      }

      public int getHash() {
         return 0;
      }

      public Object getKey() {
         return null;
      }

      public ReferenceEntry<Object, Object> getNext() {
         return null;
      }

      public ReferenceEntry<Object, Object> getNextInAccessQueue() {
         return this;
      }

      public ReferenceEntry<Object, Object> getNextInWriteQueue() {
         return this;
      }

      public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
         return this;
      }

      public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
         return this;
      }

      public LocalCache.ValueReference<Object, Object> getValueReference() {
         return null;
      }

      public long getWriteTime() {
         return 0L;
      }

      public void setAccessTime(long var1) {
      }

      public void setNextInAccessQueue(ReferenceEntry<Object, Object> var1) {
      }

      public void setNextInWriteQueue(ReferenceEntry<Object, Object> var1) {
      }

      public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> var1) {
      }

      public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> var1) {
      }

      public void setValueReference(LocalCache.ValueReference<Object, Object> var1) {
      }

      public void setWriteTime(long var1) {
      }
   }

   static class Segment<K, V> extends ReentrantLock {
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

      Segment(LocalCache<K, V> var1, int var2, long var3, AbstractCache.StatsCounter var5) {
         this.map = var1;
         this.maxSegmentWeight = var3;
         this.statsCounter = (AbstractCache.StatsCounter)Preconditions.checkNotNull(var5);
         this.initTable(this.newEntryArray(var2));
         boolean var6 = var1.usesKeyReferences();
         Object var7 = null;
         ReferenceQueue var9;
         if (var6) {
            var9 = new ReferenceQueue();
         } else {
            var9 = null;
         }

         this.keyReferenceQueue = var9;
         var9 = (ReferenceQueue)var7;
         if (var1.usesValueReferences()) {
            var9 = new ReferenceQueue();
         }

         this.valueReferenceQueue = var9;
         Object var10;
         if (var1.usesAccessQueue()) {
            var10 = new ConcurrentLinkedQueue();
         } else {
            var10 = LocalCache.DISCARDING_QUEUE;
         }

         this.recencyQueue = (Queue)var10;
         if (var1.usesWriteQueue()) {
            var10 = new LocalCache.WriteQueue();
         } else {
            var10 = LocalCache.DISCARDING_QUEUE;
         }

         this.writeQueue = (Queue)var10;
         Object var8;
         if (var1.usesAccessQueue()) {
            var8 = new LocalCache.AccessQueue();
         } else {
            var8 = LocalCache.DISCARDING_QUEUE;
         }

         this.accessQueue = (Queue)var8;
      }

      void cleanUp() {
         this.runLockedCleanup(this.map.ticker.read());
         this.runUnlockedCleanup();
      }

      void clear() {
         if (this.count != 0) {
            this.lock();

            label916: {
               Throwable var10000;
               label921: {
                  AtomicReferenceArray var1;
                  boolean var10001;
                  try {
                     this.preWriteCleanup(this.map.ticker.read());
                     var1 = this.table;
                  } catch (Throwable var96) {
                     var10000 = var96;
                     var10001 = false;
                     break label921;
                  }

                  int var2 = 0;

                  while(true) {
                     ReferenceEntry var3;
                     try {
                        if (var2 >= var1.length()) {
                           break;
                        }

                        var3 = (ReferenceEntry)var1.get(var2);
                     } catch (Throwable var94) {
                        var10000 = var94;
                        var10001 = false;
                        break label921;
                     }

                     while(var3 != null) {
                        label925: {
                           Object var4;
                           Object var5;
                           try {
                              if (!var3.getValueReference().isActive()) {
                                 break label925;
                              }

                              var4 = var3.getKey();
                              var5 = var3.getValueReference().get();
                           } catch (Throwable var95) {
                              var10000 = var95;
                              var10001 = false;
                              break label921;
                           }

                           RemovalCause var6;
                           if (var4 != null && var5 != null) {
                              try {
                                 var6 = RemovalCause.EXPLICIT;
                              } catch (Throwable var93) {
                                 var10000 = var93;
                                 var10001 = false;
                                 break label921;
                              }
                           } else {
                              try {
                                 var6 = RemovalCause.COLLECTED;
                              } catch (Throwable var92) {
                                 var10000 = var92;
                                 var10001 = false;
                                 break label921;
                              }
                           }

                           try {
                              this.enqueueNotification(var4, var3.getHash(), var5, var3.getValueReference().getWeight(), var6);
                           } catch (Throwable var91) {
                              var10000 = var91;
                              var10001 = false;
                              break label921;
                           }
                        }

                        try {
                           var3 = var3.getNext();
                        } catch (Throwable var90) {
                           var10000 = var90;
                           var10001 = false;
                           break label921;
                        }
                     }

                     ++var2;
                  }

                  var2 = 0;

                  while(true) {
                     try {
                        if (var2 >= var1.length()) {
                           break;
                        }

                        var1.set(var2, (Object)null);
                     } catch (Throwable var89) {
                        var10000 = var89;
                        var10001 = false;
                        break label921;
                     }

                     ++var2;
                  }

                  label875:
                  try {
                     this.clearReferenceQueues();
                     this.writeQueue.clear();
                     this.accessQueue.clear();
                     this.readCount.set(0);
                     ++this.modCount;
                     this.count = 0;
                     break label916;
                  } catch (Throwable var88) {
                     var10000 = var88;
                     var10001 = false;
                     break label875;
                  }
               }

               Throwable var97 = var10000;
               this.unlock();
               this.postWriteCleanup();
               throw var97;
            }

            this.unlock();
            this.postWriteCleanup();
         }

      }

      void clearKeyReferenceQueue() {
         while(this.keyReferenceQueue.poll() != null) {
         }

      }

      void clearReferenceQueues() {
         if (this.map.usesKeyReferences()) {
            this.clearKeyReferenceQueue();
         }

         if (this.map.usesValueReferences()) {
            this.clearValueReferenceQueue();
         }

      }

      void clearValueReferenceQueue() {
         while(this.valueReferenceQueue.poll() != null) {
         }

      }

      boolean containsKey(Object var1, int var2) {
         Throwable var10000;
         label140: {
            boolean var10001;
            int var3;
            try {
               var3 = this.count;
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label140;
            }

            boolean var4 = false;
            if (var3 == 0) {
               this.postReadCleanup();
               return false;
            }

            ReferenceEntry var17;
            try {
               var17 = this.getLiveEntry(var1, var2, this.map.ticker.read());
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label140;
            }

            if (var17 == null) {
               this.postReadCleanup();
               return false;
            }

            try {
               var1 = var17.getValueReference().get();
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label140;
            }

            if (var1 != null) {
               var4 = true;
            }

            this.postReadCleanup();
            return var4;
         }

         Throwable var18 = var10000;
         this.postReadCleanup();
         throw var18;
      }

      boolean containsValue(Object var1) {
         label328: {
            Throwable var10000;
            label330: {
               long var2;
               AtomicReferenceArray var4;
               int var5;
               boolean var10001;
               try {
                  if (this.count == 0) {
                     break label328;
                  }

                  var2 = this.map.ticker.read();
                  var4 = this.table;
                  var5 = var4.length();
               } catch (Throwable var39) {
                  var10000 = var39;
                  var10001 = false;
                  break label330;
               }

               int var6 = 0;

               label323:
               while(true) {
                  if (var6 >= var5) {
                     break label328;
                  }

                  ReferenceEntry var7;
                  try {
                     var7 = (ReferenceEntry)var4.get(var6);
                  } catch (Throwable var38) {
                     var10000 = var38;
                     var10001 = false;
                     break;
                  }

                  while(var7 != null) {
                     Object var8;
                     try {
                        var8 = this.getLiveValue(var7, var2);
                     } catch (Throwable var37) {
                        var10000 = var37;
                        var10001 = false;
                        break label323;
                     }

                     if (var8 != null) {
                        boolean var9;
                        try {
                           var9 = this.map.valueEquivalence.equivalent(var1, var8);
                        } catch (Throwable var36) {
                           var10000 = var36;
                           var10001 = false;
                           break label323;
                        }

                        if (var9) {
                           this.postReadCleanup();
                           return true;
                        }
                     }

                     try {
                        var7 = var7.getNext();
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label323;
                     }
                  }

                  ++var6;
               }
            }

            Throwable var40 = var10000;
            this.postReadCleanup();
            throw var40;
         }

         this.postReadCleanup();
         return false;
      }

      ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> var1, ReferenceEntry<K, V> var2) {
         if (var1.getKey() == null) {
            return null;
         } else {
            LocalCache.ValueReference var3 = var1.getValueReference();
            Object var4 = var3.get();
            if (var4 == null && var3.isActive()) {
               return null;
            } else {
               var1 = this.map.entryFactory.copyEntry(this, var1, var2);
               var1.setValueReference(var3.copyFor(this.valueReferenceQueue, var4, var1));
               return var1;
            }
         }
      }

      void drainKeyReferenceQueue() {
         int var1 = 0;

         int var3;
         do {
            Reference var2 = this.keyReferenceQueue.poll();
            if (var2 == null) {
               break;
            }

            ReferenceEntry var4 = (ReferenceEntry)var2;
            this.map.reclaimKey(var4);
            var3 = var1 + 1;
            var1 = var3;
         } while(var3 != 16);

      }

      void drainRecencyQueue() {
         while(true) {
            ReferenceEntry var1 = (ReferenceEntry)this.recencyQueue.poll();
            if (var1 == null) {
               return;
            }

            if (this.accessQueue.contains(var1)) {
               this.accessQueue.add(var1);
            }
         }
      }

      void drainReferenceQueues() {
         if (this.map.usesKeyReferences()) {
            this.drainKeyReferenceQueue();
         }

         if (this.map.usesValueReferences()) {
            this.drainValueReferenceQueue();
         }

      }

      void drainValueReferenceQueue() {
         int var1 = 0;

         int var3;
         do {
            Reference var2 = this.valueReferenceQueue.poll();
            if (var2 == null) {
               break;
            }

            LocalCache.ValueReference var4 = (LocalCache.ValueReference)var2;
            this.map.reclaimValue(var4);
            var3 = var1 + 1;
            var1 = var3;
         } while(var3 != 16);

      }

      void enqueueNotification(@NullableDecl K var1, int var2, @NullableDecl V var3, int var4, RemovalCause var5) {
         this.totalWeight -= (long)var4;
         if (var5.wasEvicted()) {
            this.statsCounter.recordEviction();
         }

         if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
            RemovalNotification var6 = RemovalNotification.create(var1, var3, var5);
            this.map.removalNotificationQueue.offer(var6);
         }

      }

      void evictEntries(ReferenceEntry<K, V> var1) {
         if (this.map.evictsBySize()) {
            this.drainRecencyQueue();
            if ((long)var1.getValueReference().getWeight() > this.maxSegmentWeight && !this.removeEntry(var1, var1.getHash(), RemovalCause.SIZE)) {
               throw new AssertionError();
            } else {
               do {
                  if (this.totalWeight <= this.maxSegmentWeight) {
                     return;
                  }

                  var1 = this.getNextEvictable();
               } while(this.removeEntry(var1, var1.getHash(), RemovalCause.SIZE));

               throw new AssertionError();
            }
         }
      }

      void expand() {
         AtomicReferenceArray var1 = this.table;
         int var2 = var1.length();
         if (var2 < 1073741824) {
            int var3 = this.count;
            AtomicReferenceArray var4 = this.newEntryArray(var2 << 1);
            this.threshold = var4.length() * 3 / 4;
            int var5 = var4.length() - 1;

            int var8;
            for(int var6 = 0; var6 < var2; var3 = var8) {
               ReferenceEntry var7 = (ReferenceEntry)var1.get(var6);
               var8 = var3;
               if (var7 != null) {
                  ReferenceEntry var9 = var7.getNext();
                  var8 = var7.getHash() & var5;
                  if (var9 == null) {
                     var4.set(var8, var7);
                     var8 = var3;
                  } else {
                     ReferenceEntry var10;
                     int var12;
                     for(var10 = var7; var9 != null; var8 = var12) {
                        int var11 = var9.getHash() & var5;
                        var12 = var8;
                        if (var11 != var8) {
                           var10 = var9;
                           var12 = var11;
                        }

                        var9 = var9.getNext();
                     }

                     var4.set(var8, var10);

                     while(true) {
                        var8 = var3;
                        if (var7 == var10) {
                           break;
                        }

                        var8 = var7.getHash() & var5;
                        var9 = this.copyEntry(var7, (ReferenceEntry)var4.get(var8));
                        if (var9 != null) {
                           var4.set(var8, var9);
                        } else {
                           this.removeCollectedEntry(var7);
                           --var3;
                        }

                        var7 = var7.getNext();
                     }
                  }
               }

               ++var6;
            }

            this.table = var4;
            this.count = var3;
         }
      }

      void expireEntries(long var1) {
         this.drainRecencyQueue();

         ReferenceEntry var3;
         do {
            var3 = (ReferenceEntry)this.writeQueue.peek();
            if (var3 == null || !this.map.isExpired(var3, var1)) {
               do {
                  var3 = (ReferenceEntry)this.accessQueue.peek();
                  if (var3 == null || !this.map.isExpired(var3, var1)) {
                     return;
                  }
               } while(this.removeEntry(var3, var3.getHash(), RemovalCause.EXPIRED));

               throw new AssertionError();
            }
         } while(this.removeEntry(var3, var3.getHash(), RemovalCause.EXPIRED));

         throw new AssertionError();
      }

      @NullableDecl
      V get(Object var1, int var2) {
         Throwable var10000;
         label188: {
            label190: {
               long var3;
               boolean var10001;
               ReferenceEntry var26;
               try {
                  if (this.count == 0) {
                     break label190;
                  }

                  var3 = this.map.ticker.read();
                  var26 = this.getLiveEntry(var1, var2, var3);
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break label188;
               }

               if (var26 == null) {
                  this.postReadCleanup();
                  return null;
               }

               Object var5;
               try {
                  var5 = var26.getValueReference().get();
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label188;
               }

               if (var5 != null) {
                  try {
                     this.recordRead(var26, var3);
                     var1 = this.scheduleRefresh(var26, var26.getKey(), var2, var5, var3, this.map.defaultLoader);
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label188;
                  }

                  this.postReadCleanup();
                  return var1;
               }

               try {
                  this.tryDrainReferenceQueues();
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label188;
               }
            }

            this.postReadCleanup();
            return null;
         }

         Throwable var27 = var10000;
         this.postReadCleanup();
         throw var27;
      }

      V get(K param1, int param2, CacheLoader<? super K, V> param3) throws ExecutionException {
         // $FF: Couldn't be decompiled
      }

      V getAndRecordStats(K var1, int var2, LocalCache.LoadingValueReference<K, V> var3, ListenableFuture<V> var4) throws ExecutionException {
         Object var5;
         try {
            var5 = Uninterruptibles.getUninterruptibly(var4);
         } finally {
            ;
         }

         Throwable var10000;
         boolean var10001;
         if (var5 != null) {
            label143: {
               try {
                  this.statsCounter.recordLoadSuccess(var3.elapsedNanos());
                  this.storeLoadedValue(var1, var2, var3, var5);
               } catch (Throwable var17) {
                  var10000 = var17;
                  var10001 = false;
                  break label143;
               }

               if (var5 == null) {
                  this.statsCounter.recordLoadException(var3.elapsedNanos());
                  this.removeLoadingValue(var1, var2, var3);
               }

               return var5;
            }
         } else {
            label138:
            try {
               StringBuilder var20 = new StringBuilder();
               var20.append("CacheLoader returned null for key ");
               var20.append(var1);
               var20.append(".");
               CacheLoader.InvalidCacheLoadException var6 = new CacheLoader.InvalidCacheLoadException(var20.toString());
               throw var6;
            } catch (Throwable var18) {
               var10000 = var18;
               var10001 = false;
               break label138;
            }
         }

         Throwable var19 = var10000;
         if (var5 == null) {
            this.statsCounter.recordLoadException(var3.elapsedNanos());
            this.removeLoadingValue(var1, var2, var3);
         }

         throw var19;
      }

      @NullableDecl
      ReferenceEntry<K, V> getEntry(Object var1, int var2) {
         for(ReferenceEntry var3 = this.getFirst(var2); var3 != null; var3 = var3.getNext()) {
            if (var3.getHash() == var2) {
               Object var4 = var3.getKey();
               if (var4 == null) {
                  this.tryDrainReferenceQueues();
               } else if (this.map.keyEquivalence.equivalent(var1, var4)) {
                  return var3;
               }
            }
         }

         return null;
      }

      ReferenceEntry<K, V> getFirst(int var1) {
         AtomicReferenceArray var2 = this.table;
         return (ReferenceEntry)var2.get(var1 & var2.length() - 1);
      }

      @NullableDecl
      ReferenceEntry<K, V> getLiveEntry(Object var1, int var2, long var3) {
         ReferenceEntry var5 = this.getEntry(var1, var2);
         if (var5 == null) {
            return null;
         } else if (this.map.isExpired(var5, var3)) {
            this.tryExpireEntries(var3);
            return null;
         } else {
            return var5;
         }
      }

      V getLiveValue(ReferenceEntry<K, V> var1, long var2) {
         if (var1.getKey() == null) {
            this.tryDrainReferenceQueues();
            return null;
         } else {
            Object var4 = var1.getValueReference().get();
            if (var4 == null) {
               this.tryDrainReferenceQueues();
               return null;
            } else if (this.map.isExpired(var1, var2)) {
               this.tryExpireEntries(var2);
               return null;
            } else {
               return var4;
            }
         }
      }

      ReferenceEntry<K, V> getNextEvictable() {
         Iterator var1 = this.accessQueue.iterator();

         ReferenceEntry var2;
         do {
            if (!var1.hasNext()) {
               throw new AssertionError();
            }

            var2 = (ReferenceEntry)var1.next();
         } while(var2.getValueReference().getWeight() <= 0);

         return var2;
      }

      void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> var1) {
         this.threshold = var1.length() * 3 / 4;
         if (!this.map.customWeigher()) {
            int var2 = this.threshold;
            if ((long)var2 == this.maxSegmentWeight) {
               this.threshold = var2 + 1;
            }
         }

         this.table = var1;
      }

      @NullableDecl
      LocalCache.LoadingValueReference<K, V> insertLoadingValueReference(K var1, int var2, boolean var3) {
         this.lock();

         Throwable var10000;
         label576: {
            long var4;
            AtomicReferenceArray var6;
            int var7;
            ReferenceEntry var8;
            boolean var10001;
            try {
               var4 = this.map.ticker.read();
               this.preWriteCleanup(var4);
               var6 = this.table;
               var7 = var6.length() - 1 & var2;
               var8 = (ReferenceEntry)var6.get(var7);
            } catch (Throwable var63) {
               var10000 = var63;
               var10001 = false;
               break label576;
            }

            ReferenceEntry var9 = var8;

            while(true) {
               if (var9 == null) {
                  LocalCache.LoadingValueReference var71;
                  try {
                     ++this.modCount;
                     var71 = new LocalCache.LoadingValueReference();
                     ReferenceEntry var69 = this.newEntry(var1, var2, var8);
                     var69.setValueReference(var71);
                     var6.set(var7, var69);
                  } catch (Throwable var61) {
                     var10000 = var61;
                     var10001 = false;
                     break;
                  }

                  this.unlock();
                  this.postWriteCleanup();
                  return var71;
               }

               label578: {
                  Object var10;
                  try {
                     var10 = var9.getKey();
                     if (var9.getHash() != var2) {
                        break label578;
                     }
                  } catch (Throwable var64) {
                     var10000 = var64;
                     var10001 = false;
                     break;
                  }

                  if (var10 != null) {
                     label580: {
                        LocalCache.ValueReference var70;
                        label566: {
                           label579: {
                              try {
                                 if (!this.map.keyEquivalence.equivalent(var1, var10)) {
                                    break label580;
                                 }

                                 var70 = var9.getValueReference();
                                 if (var70.isLoading()) {
                                    break label579;
                                 }
                              } catch (Throwable var66) {
                                 var10000 = var66;
                                 var10001 = false;
                                 break;
                              }

                              if (!var3) {
                                 break label566;
                              }

                              try {
                                 if (var4 - var9.getWriteTime() >= this.map.refreshNanos) {
                                    break label566;
                                 }
                              } catch (Throwable var65) {
                                 var10000 = var65;
                                 var10001 = false;
                                 break;
                              }
                           }

                           this.unlock();
                           this.postWriteCleanup();
                           return null;
                        }

                        LocalCache.LoadingValueReference var67;
                        try {
                           ++this.modCount;
                           var67 = new LocalCache.LoadingValueReference(var70);
                           var9.setValueReference(var67);
                        } catch (Throwable var60) {
                           var10000 = var60;
                           var10001 = false;
                           break;
                        }

                        this.unlock();
                        this.postWriteCleanup();
                        return var67;
                     }
                  }
               }

               try {
                  var9 = var9.getNext();
               } catch (Throwable var62) {
                  var10000 = var62;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var68 = var10000;
         this.unlock();
         this.postWriteCleanup();
         throw var68;
      }

      ListenableFuture<V> loadAsync(final K var1, final int var2, final LocalCache.LoadingValueReference<K, V> var3, CacheLoader<? super K, V> var4) {
         final ListenableFuture var5 = var3.loadFuture(var1, var4);
         var5.addListener(new Runnable() {
            public void run() {
               try {
                  Segment.this.getAndRecordStats(var1, var2, var3, var5);
               } catch (Throwable var3x) {
                  LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", var3x);
                  var3.setException(var3x);
                  return;
               }

            }
         }, MoreExecutors.directExecutor());
         return var5;
      }

      V loadSync(K var1, int var2, LocalCache.LoadingValueReference<K, V> var3, CacheLoader<? super K, V> var4) throws ExecutionException {
         return this.getAndRecordStats(var1, var2, var3, var3.loadFuture(var1, var4));
      }

      V lockedGetOrLoad(K param1, int param2, CacheLoader<? super K, V> param3) throws ExecutionException {
         // $FF: Couldn't be decompiled
      }

      ReferenceEntry<K, V> newEntry(K var1, int var2, @NullableDecl ReferenceEntry<K, V> var3) {
         return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(var1), var2, var3);
      }

      AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int var1) {
         return new AtomicReferenceArray(var1);
      }

      void postReadCleanup() {
         if ((this.readCount.incrementAndGet() & 63) == 0) {
            this.cleanUp();
         }

      }

      void postWriteCleanup() {
         this.runUnlockedCleanup();
      }

      void preWriteCleanup(long var1) {
         this.runLockedCleanup(var1);
      }

      @NullableDecl
      V put(K var1, int var2, V var3, boolean var4) {
         this.lock();

         label1103: {
            Throwable var10000;
            label1107: {
               long var5;
               boolean var10001;
               try {
                  var5 = this.map.ticker.read();
                  this.preWriteCleanup(var5);
                  if (this.count + 1 > this.threshold) {
                     this.expand();
                  }
               } catch (Throwable var143) {
                  var10000 = var143;
                  var10001 = false;
                  break label1107;
               }

               AtomicReferenceArray var7;
               int var8;
               ReferenceEntry var9;
               try {
                  var7 = this.table;
                  var8 = var2 & var7.length() - 1;
                  var9 = (ReferenceEntry)var7.get(var8);
               } catch (Throwable var142) {
                  var10000 = var142;
                  var10001 = false;
                  break label1107;
               }

               ReferenceEntry var10 = var9;

               Object var145;
               LocalCache.ValueReference var146;
               while(true) {
                  if (var10 == null) {
                     try {
                        ++this.modCount;
                        var10 = this.newEntry(var1, var2, var9);
                        this.setValue(var10, var1, var3, var5);
                        var7.set(var8, var10);
                        ++this.count;
                        this.evictEntries(var10);
                        break label1103;
                     } catch (Throwable var138) {
                        var10000 = var138;
                        var10001 = false;
                        break label1107;
                     }
                  }

                  label1091: {
                     Object var11;
                     try {
                        var11 = var10.getKey();
                        if (var10.getHash() != var2) {
                           break label1091;
                        }
                     } catch (Throwable var141) {
                        var10000 = var141;
                        var10001 = false;
                        break label1107;
                     }

                     if (var11 != null) {
                        try {
                           if (this.map.keyEquivalence.equivalent(var1, var11)) {
                              var146 = var10.getValueReference();
                              var145 = var146.get();
                              break;
                           }
                        } catch (Throwable var140) {
                           var10000 = var140;
                           var10001 = false;
                           break label1107;
                        }
                     }
                  }

                  try {
                     var10 = var10.getNext();
                  } catch (Throwable var139) {
                     var10000 = var139;
                     var10001 = false;
                     break label1107;
                  }
               }

               if (var145 == null) {
                  label1077: {
                     label1109: {
                        try {
                           ++this.modCount;
                           if (var146.isActive()) {
                              this.enqueueNotification(var1, var2, var145, var146.getWeight(), RemovalCause.COLLECTED);
                              this.setValue(var10, var1, var3, var5);
                              var2 = this.count;
                              break label1109;
                           }
                        } catch (Throwable var137) {
                           var10000 = var137;
                           var10001 = false;
                           break label1077;
                        }

                        try {
                           this.setValue(var10, var1, var3, var5);
                           var2 = this.count + 1;
                        } catch (Throwable var136) {
                           var10000 = var136;
                           var10001 = false;
                           break label1077;
                        }
                     }

                     label1067:
                     try {
                        this.count = var2;
                        this.evictEntries(var10);
                        break label1103;
                     } catch (Throwable var135) {
                        var10000 = var135;
                        var10001 = false;
                        break label1067;
                     }
                  }
               } else {
                  label1064: {
                     if (var4) {
                        try {
                           this.recordLockedRead(var10, var5);
                        } catch (Throwable var134) {
                           var10000 = var134;
                           var10001 = false;
                           break label1064;
                        }
                     } else {
                        try {
                           ++this.modCount;
                           this.enqueueNotification(var1, var2, var145, var146.getWeight(), RemovalCause.REPLACED);
                           this.setValue(var10, var1, var3, var5);
                           this.evictEntries(var10);
                        } catch (Throwable var133) {
                           var10000 = var133;
                           var10001 = false;
                           break label1064;
                        }
                     }

                     this.unlock();
                     this.postWriteCleanup();
                     return var145;
                  }
               }
            }

            Throwable var144 = var10000;
            this.unlock();
            this.postWriteCleanup();
            throw var144;
         }

         this.unlock();
         this.postWriteCleanup();
         return null;
      }

      boolean reclaimKey(ReferenceEntry<K, V> var1, int var2) {
         this.lock();

         label144: {
            Throwable var10000;
            label148: {
               AtomicReferenceArray var3;
               int var4;
               ReferenceEntry var5;
               boolean var10001;
               try {
                  var3 = this.table;
                  var4 = var3.length() - 1 & var2;
                  var5 = (ReferenceEntry)var3.get(var4);
               } catch (Throwable var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label148;
               }

               ReferenceEntry var6 = var5;

               while(true) {
                  if (var6 == null) {
                     this.unlock();
                     this.postWriteCleanup();
                     return false;
                  }

                  if (var6 == var1) {
                     try {
                        ++this.modCount;
                        var1 = this.removeValueFromChain(var5, var6, var6.getKey(), var2, var6.getValueReference().get(), var6.getValueReference(), RemovalCause.COLLECTED);
                        var2 = this.count;
                        var3.set(var4, var1);
                        this.count = var2 - 1;
                        break label144;
                     } catch (Throwable var16) {
                        var10000 = var16;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var6 = var6.getNext();
                  } catch (Throwable var17) {
                     var10000 = var17;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var19 = var10000;
            this.unlock();
            this.postWriteCleanup();
            throw var19;
         }

         this.unlock();
         this.postWriteCleanup();
         return true;
      }

      boolean reclaimValue(K var1, int var2, LocalCache.ValueReference<K, V> var3) {
         this.lock();

         Throwable var10000;
         label354: {
            AtomicReferenceArray var4;
            int var5;
            ReferenceEntry var6;
            boolean var10001;
            try {
               var4 = this.table;
               var5 = var4.length() - 1 & var2;
               var6 = (ReferenceEntry)var4.get(var5);
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break label354;
            }

            ReferenceEntry var7 = var6;

            while(true) {
               if (var7 == null) {
                  this.unlock();
                  if (!this.isHeldByCurrentThread()) {
                     this.postWriteCleanup();
                  }

                  return false;
               }

               label356: {
                  label342: {
                     label341: {
                        Object var8;
                        try {
                           var8 = var7.getKey();
                           if (var7.getHash() != var2) {
                              break label341;
                           }
                        } catch (Throwable var27) {
                           var10000 = var27;
                           var10001 = false;
                           break;
                        }

                        if (var8 != null) {
                           try {
                              if (this.map.keyEquivalence.equivalent(var1, var8)) {
                                 if (var7.getValueReference() != var3) {
                                    break label342;
                                 }

                                 ++this.modCount;
                                 ReferenceEntry var29 = this.removeValueFromChain(var6, var7, var8, var2, var3.get(), var3, RemovalCause.COLLECTED);
                                 var2 = this.count;
                                 var4.set(var5, var29);
                                 this.count = var2 - 1;
                                 break label356;
                              }
                           } catch (Throwable var26) {
                              var10000 = var26;
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     try {
                        var7 = var7.getNext();
                        continue;
                     } catch (Throwable var25) {
                        var10000 = var25;
                        var10001 = false;
                        break;
                     }
                  }

                  this.unlock();
                  if (!this.isHeldByCurrentThread()) {
                     this.postWriteCleanup();
                  }

                  return false;
               }

               this.unlock();
               if (!this.isHeldByCurrentThread()) {
                  this.postWriteCleanup();
               }

               return true;
            }
         }

         Throwable var30 = var10000;
         this.unlock();
         if (!this.isHeldByCurrentThread()) {
            this.postWriteCleanup();
         }

         throw var30;
      }

      void recordLockedRead(ReferenceEntry<K, V> var1, long var2) {
         if (this.map.recordsAccess()) {
            var1.setAccessTime(var2);
         }

         this.accessQueue.add(var1);
      }

      void recordRead(ReferenceEntry<K, V> var1, long var2) {
         if (this.map.recordsAccess()) {
            var1.setAccessTime(var2);
         }

         this.recencyQueue.add(var1);
      }

      void recordWrite(ReferenceEntry<K, V> var1, int var2, long var3) {
         this.drainRecencyQueue();
         this.totalWeight += (long)var2;
         if (this.map.recordsAccess()) {
            var1.setAccessTime(var3);
         }

         if (this.map.recordsWrite()) {
            var1.setWriteTime(var3);
         }

         this.accessQueue.add(var1);
         this.writeQueue.add(var1);
      }

      @NullableDecl
      V refresh(K var1, int var2, CacheLoader<? super K, V> var3, boolean var4) {
         LocalCache.LoadingValueReference var5 = this.insertLoadingValueReference(var1, var2, var4);
         if (var5 == null) {
            return null;
         } else {
            ListenableFuture var8 = this.loadAsync(var1, var2, var5, var3);
            if (var8.isDone()) {
               try {
                  var1 = Uninterruptibles.getUninterruptibly(var8);
                  return var1;
               } finally {
                  return null;
               }
            } else {
               return null;
            }
         }
      }

      @NullableDecl
      V remove(Object var1, int var2) {
         this.lock();

         label533: {
            Throwable var10000;
            label537: {
               AtomicReferenceArray var3;
               int var4;
               ReferenceEntry var5;
               boolean var10001;
               try {
                  this.preWriteCleanup(this.map.ticker.read());
                  var3 = this.table;
                  var4 = var3.length() - 1 & var2;
                  var5 = (ReferenceEntry)var3.get(var4);
               } catch (Throwable var65) {
                  var10000 = var65;
                  var10001 = false;
                  break label537;
               }

               ReferenceEntry var6 = var5;

               Object var7;
               LocalCache.ValueReference var8;
               Object var9;
               while(true) {
                  if (var6 == null) {
                     break label533;
                  }

                  label524: {
                     try {
                        var7 = var6.getKey();
                        if (var6.getHash() != var2) {
                           break label524;
                        }
                     } catch (Throwable var64) {
                        var10000 = var64;
                        var10001 = false;
                        break label537;
                     }

                     if (var7 != null) {
                        try {
                           if (this.map.keyEquivalence.equivalent(var1, var7)) {
                              var8 = var6.getValueReference();
                              var9 = var8.get();
                              break;
                           }
                        } catch (Throwable var63) {
                           var10000 = var63;
                           var10001 = false;
                           break label537;
                        }
                     }
                  }

                  try {
                     var6 = var6.getNext();
                  } catch (Throwable var62) {
                     var10000 = var62;
                     var10001 = false;
                     break label537;
                  }
               }

               RemovalCause var66;
               if (var9 != null) {
                  try {
                     var66 = RemovalCause.EXPLICIT;
                  } catch (Throwable var61) {
                     var10000 = var61;
                     var10001 = false;
                     break label537;
                  }
               } else {
                  try {
                     if (!var8.isActive()) {
                        break label533;
                     }

                     var66 = RemovalCause.COLLECTED;
                  } catch (Throwable var60) {
                     var10000 = var60;
                     var10001 = false;
                     break label537;
                  }
               }

               try {
                  ++this.modCount;
                  ReferenceEntry var67 = this.removeValueFromChain(var5, var6, var7, var2, var9, var8, var66);
                  var2 = this.count;
                  var3.set(var4, var67);
                  this.count = var2 - 1;
               } catch (Throwable var59) {
                  var10000 = var59;
                  var10001 = false;
                  break label537;
               }

               this.unlock();
               this.postWriteCleanup();
               return var9;
            }

            Throwable var68 = var10000;
            this.unlock();
            this.postWriteCleanup();
            throw var68;
         }

         this.unlock();
         this.postWriteCleanup();
         return null;
      }

      boolean remove(Object var1, int var2, Object var3) {
         this.lock();

         Throwable var10000;
         label647: {
            AtomicReferenceArray var4;
            int var5;
            boolean var10001;
            try {
               this.preWriteCleanup(this.map.ticker.read());
               var4 = this.table;
               var5 = var4.length();
            } catch (Throwable var64) {
               var10000 = var64;
               var10001 = false;
               break label647;
            }

            boolean var6 = true;
            var5 = var5 - 1 & var2;

            ReferenceEntry var7;
            try {
               var7 = (ReferenceEntry)var4.get(var5);
            } catch (Throwable var63) {
               var10000 = var63;
               var10001 = false;
               break label647;
            }

            ReferenceEntry var8 = var7;

            while(true) {
               label652: {
                  if (var8 != null) {
                     label651: {
                        Object var9;
                        try {
                           var9 = var8.getKey();
                           if (var8.getHash() != var2) {
                              break label652;
                           }
                        } catch (Throwable var65) {
                           var10000 = var65;
                           var10001 = false;
                           break;
                        }

                        if (var9 == null) {
                           break label652;
                        }

                        RemovalCause var68;
                        LocalCache.ValueReference var10;
                        Object var11;
                        label653: {
                           try {
                              if (!this.map.keyEquivalence.equivalent(var1, var9)) {
                                 break label652;
                              }

                              var10 = var8.getValueReference();
                              var11 = var10.get();
                              if (this.map.valueEquivalence.equivalent(var3, var11)) {
                                 var68 = RemovalCause.EXPLICIT;
                                 break label653;
                              }
                           } catch (Throwable var67) {
                              var10000 = var67;
                              var10001 = false;
                              break;
                           }

                           if (var11 != null) {
                              break label651;
                           }

                           try {
                              if (!var10.isActive()) {
                                 break label651;
                              }

                              var68 = RemovalCause.COLLECTED;
                           } catch (Throwable var66) {
                              var10000 = var66;
                              var10001 = false;
                              break;
                           }
                        }

                        RemovalCause var71;
                        try {
                           ++this.modCount;
                           ReferenceEntry var70 = this.removeValueFromChain(var7, var8, var9, var2, var11, var10, var68);
                           var2 = this.count;
                           var4.set(var5, var70);
                           this.count = var2 - 1;
                           var71 = RemovalCause.EXPLICIT;
                        } catch (Throwable var61) {
                           var10000 = var61;
                           var10001 = false;
                           break;
                        }

                        if (var68 != var71) {
                           var6 = false;
                        }

                        this.unlock();
                        this.postWriteCleanup();
                        return var6;
                     }
                  }

                  this.unlock();
                  this.postWriteCleanup();
                  return false;
               }

               try {
                  var8 = var8.getNext();
               } catch (Throwable var62) {
                  var10000 = var62;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var69 = var10000;
         this.unlock();
         this.postWriteCleanup();
         throw var69;
      }

      void removeCollectedEntry(ReferenceEntry<K, V> var1) {
         this.enqueueNotification(var1.getKey(), var1.getHash(), var1.getValueReference().get(), var1.getValueReference().getWeight(), RemovalCause.COLLECTED);
         this.writeQueue.remove(var1);
         this.accessQueue.remove(var1);
      }

      boolean removeEntry(ReferenceEntry<K, V> var1, int var2, RemovalCause var3) {
         AtomicReferenceArray var4 = this.table;
         int var5 = var4.length() - 1 & var2;
         ReferenceEntry var6 = (ReferenceEntry)var4.get(var5);

         for(ReferenceEntry var7 = var6; var7 != null; var7 = var7.getNext()) {
            if (var7 == var1) {
               ++this.modCount;
               var1 = this.removeValueFromChain(var6, var7, var7.getKey(), var2, var7.getValueReference().get(), var7.getValueReference(), var3);
               var2 = this.count;
               var4.set(var5, var1);
               this.count = var2 - 1;
               return true;
            }
         }

         return false;
      }

      @NullableDecl
      ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> var1, ReferenceEntry<K, V> var2) {
         int var3 = this.count;

         ReferenceEntry var4;
         for(var4 = var2.getNext(); var1 != var2; var1 = var1.getNext()) {
            ReferenceEntry var5 = this.copyEntry(var1, var4);
            if (var5 != null) {
               var4 = var5;
            } else {
               this.removeCollectedEntry(var1);
               --var3;
            }
         }

         this.count = var3;
         return var4;
      }

      boolean removeLoadingValue(K var1, int var2, LocalCache.LoadingValueReference<K, V> var3) {
         this.lock();

         Throwable var10000;
         label372: {
            AtomicReferenceArray var4;
            int var5;
            ReferenceEntry var6;
            boolean var10001;
            try {
               var4 = this.table;
               var5 = var4.length() - 1 & var2;
               var6 = (ReferenceEntry)var4.get(var5);
            } catch (Throwable var38) {
               var10000 = var38;
               var10001 = false;
               break label372;
            }

            ReferenceEntry var7 = var6;

            while(true) {
               label362: {
                  label376: {
                     if (var7 != null) {
                        label375: {
                           Object var8;
                           try {
                              var8 = var7.getKey();
                              if (var7.getHash() != var2) {
                                 break label362;
                              }
                           } catch (Throwable var36) {
                              var10000 = var36;
                              var10001 = false;
                              break;
                           }

                           if (var8 == null) {
                              break label362;
                           }

                           try {
                              if (!this.map.keyEquivalence.equivalent(var1, var8)) {
                                 break label362;
                              }

                              if (var7.getValueReference() != var3) {
                                 break label375;
                              }

                              if (var3.isActive()) {
                                 var7.setValueReference(var3.getOldValue());
                                 break label376;
                              }
                           } catch (Throwable var37) {
                              var10000 = var37;
                              var10001 = false;
                              break;
                           }

                           try {
                              var4.set(var5, this.removeEntryFromChain(var6, var7));
                              break label376;
                           } catch (Throwable var34) {
                              var10000 = var34;
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     this.unlock();
                     this.postWriteCleanup();
                     return false;
                  }

                  this.unlock();
                  this.postWriteCleanup();
                  return true;
               }

               try {
                  var7 = var7.getNext();
               } catch (Throwable var35) {
                  var10000 = var35;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var39 = var10000;
         this.unlock();
         this.postWriteCleanup();
         throw var39;
      }

      @NullableDecl
      ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> var1, ReferenceEntry<K, V> var2, @NullableDecl K var3, int var4, V var5, LocalCache.ValueReference<K, V> var6, RemovalCause var7) {
         this.enqueueNotification(var3, var4, var5, var6.getWeight(), var7);
         this.writeQueue.remove(var2);
         this.accessQueue.remove(var2);
         if (var6.isLoading()) {
            var6.notifyNewValue((Object)null);
            return var1;
         } else {
            return this.removeEntryFromChain(var1, var2);
         }
      }

      @NullableDecl
      V replace(K var1, int var2, V var3) {
         this.lock();

         Throwable var10000;
         label420: {
            long var4;
            AtomicReferenceArray var6;
            int var7;
            ReferenceEntry var8;
            boolean var10001;
            try {
               var4 = this.map.ticker.read();
               this.preWriteCleanup(var4);
               var6 = this.table;
               var7 = var2 & var6.length() - 1;
               var8 = (ReferenceEntry)var6.get(var7);
            } catch (Throwable var52) {
               var10000 = var52;
               var10001 = false;
               break label420;
            }

            ReferenceEntry var9 = var8;

            while(var9 != null) {
               Object var10;
               LocalCache.ValueReference var11;
               Object var12;
               label411: {
                  label410: {
                     try {
                        var10 = var9.getKey();
                        if (var9.getHash() != var2) {
                           break label410;
                        }
                     } catch (Throwable var54) {
                        var10000 = var54;
                        var10001 = false;
                        break label420;
                     }

                     if (var10 != null) {
                        try {
                           if (this.map.keyEquivalence.equivalent(var1, var10)) {
                              var11 = var9.getValueReference();
                              var12 = var11.get();
                              break label411;
                           }
                        } catch (Throwable var53) {
                           var10000 = var53;
                           var10001 = false;
                           break label420;
                        }
                     }
                  }

                  try {
                     var9 = var9.getNext();
                     continue;
                  } catch (Throwable var51) {
                     var10000 = var51;
                     var10001 = false;
                     break label420;
                  }
               }

               if (var12 != null) {
                  try {
                     ++this.modCount;
                     this.enqueueNotification(var1, var2, var12, var11.getWeight(), RemovalCause.REPLACED);
                     this.setValue(var9, var1, var3, var4);
                     this.evictEntries(var9);
                  } catch (Throwable var49) {
                     var10000 = var49;
                     var10001 = false;
                     break label420;
                  }

                  this.unlock();
                  this.postWriteCleanup();
                  return var12;
               }

               try {
                  if (var11.isActive()) {
                     ++this.modCount;
                     ReferenceEntry var55 = this.removeValueFromChain(var8, var9, var10, var2, var12, var11, RemovalCause.COLLECTED);
                     var2 = this.count;
                     var6.set(var7, var55);
                     this.count = var2 - 1;
                  }
                  break;
               } catch (Throwable var50) {
                  var10000 = var50;
                  var10001 = false;
                  break label420;
               }
            }

            this.unlock();
            this.postWriteCleanup();
            return null;
         }

         Throwable var56 = var10000;
         this.unlock();
         this.postWriteCleanup();
         throw var56;
      }

      boolean replace(K var1, int var2, V var3, V var4) {
         this.lock();

         Throwable var10000;
         label553: {
            long var5;
            AtomicReferenceArray var7;
            int var8;
            ReferenceEntry var9;
            boolean var10001;
            try {
               var5 = this.map.ticker.read();
               this.preWriteCleanup(var5);
               var7 = this.table;
               var8 = var2 & var7.length() - 1;
               var9 = (ReferenceEntry)var7.get(var8);
            } catch (Throwable var69) {
               var10000 = var69;
               var10001 = false;
               break label553;
            }

            ReferenceEntry var10 = var9;

            while(true) {
               label557: {
                  if (var10 != null) {
                     Object var11;
                     try {
                        var11 = var10.getKey();
                        if (var10.getHash() != var2) {
                           break label557;
                        }
                     } catch (Throwable var67) {
                        var10000 = var67;
                        var10001 = false;
                        break;
                     }

                     if (var11 == null) {
                        break label557;
                     }

                     LocalCache.ValueReference var12;
                     Object var13;
                     try {
                        if (!this.map.keyEquivalence.equivalent(var1, var11)) {
                           break label557;
                        }

                        var12 = var10.getValueReference();
                        var13 = var12.get();
                     } catch (Throwable var66) {
                        var10000 = var66;
                        var10001 = false;
                        break;
                     }

                     if (var13 == null) {
                        try {
                           if (var12.isActive()) {
                              ++this.modCount;
                              ReferenceEntry var70 = this.removeValueFromChain(var9, var10, var11, var2, var13, var12, RemovalCause.COLLECTED);
                              var2 = this.count;
                              var7.set(var8, var70);
                              this.count = var2 - 1;
                           }
                        } catch (Throwable var64) {
                           var10000 = var64;
                           var10001 = false;
                           break;
                        }
                     } else {
                        label540: {
                           try {
                              if (!this.map.valueEquivalence.equivalent(var3, var13)) {
                                 break label540;
                              }

                              ++this.modCount;
                              this.enqueueNotification(var1, var2, var13, var12.getWeight(), RemovalCause.REPLACED);
                              this.setValue(var10, var1, var4, var5);
                              this.evictEntries(var10);
                           } catch (Throwable var68) {
                              var10000 = var68;
                              var10001 = false;
                              break;
                           }

                           this.unlock();
                           this.postWriteCleanup();
                           return true;
                        }

                        try {
                           this.recordLockedRead(var10, var5);
                        } catch (Throwable var65) {
                           var10000 = var65;
                           var10001 = false;
                           break;
                        }
                     }
                  }

                  this.unlock();
                  this.postWriteCleanup();
                  return false;
               }

               try {
                  var10 = var10.getNext();
               } catch (Throwable var63) {
                  var10000 = var63;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var71 = var10000;
         this.unlock();
         this.postWriteCleanup();
         throw var71;
      }

      void runLockedCleanup(long var1) {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
               this.expireEntries(var1);
               this.readCount.set(0);
            } finally {
               this.unlock();
            }
         }

      }

      void runUnlockedCleanup() {
         if (!this.isHeldByCurrentThread()) {
            this.map.processPendingNotifications();
         }

      }

      V scheduleRefresh(ReferenceEntry<K, V> var1, K var2, int var3, V var4, long var5, CacheLoader<? super K, V> var7) {
         if (this.map.refreshes() && var5 - var1.getWriteTime() > this.map.refreshNanos && !var1.getValueReference().isLoading()) {
            Object var8 = this.refresh(var2, var3, var7, true);
            if (var8 != null) {
               return var8;
            }
         }

         return var4;
      }

      void setValue(ReferenceEntry<K, V> var1, K var2, V var3, long var4) {
         LocalCache.ValueReference var6 = var1.getValueReference();
         int var7 = this.map.weigher.weigh(var2, var3);
         boolean var8;
         if (var7 >= 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         Preconditions.checkState(var8, "Weights must be non-negative");
         var1.setValueReference(this.map.valueStrength.referenceValue(this, var1, var3, var7));
         this.recordWrite(var1, var7, var4);
         var6.notifyNewValue(var3);
      }

      boolean storeLoadedValue(K var1, int var2, LocalCache.LoadingValueReference<K, V> var3, V var4) {
         this.lock();

         Throwable var10000;
         label1882: {
            long var5;
            int var7;
            boolean var10001;
            try {
               var5 = this.map.ticker.read();
               this.preWriteCleanup(var5);
               var7 = this.count + 1;
            } catch (Throwable var248) {
               var10000 = var248;
               var10001 = false;
               break label1882;
            }

            int var8 = var7;

            try {
               if (var7 > this.threshold) {
                  this.expand();
                  var8 = this.count + 1;
               }
            } catch (Throwable var247) {
               var10000 = var247;
               var10001 = false;
               break label1882;
            }

            AtomicReferenceArray var9;
            ReferenceEntry var10;
            try {
               var9 = this.table;
               var7 = var2 & var9.length() - 1;
               var10 = (ReferenceEntry)var9.get(var7);
            } catch (Throwable var246) {
               var10000 = var246;
               var10001 = false;
               break label1882;
            }

            ReferenceEntry var11 = var10;

            label1876: {
               while(true) {
                  if (var11 == null) {
                     try {
                        ++this.modCount;
                        ReferenceEntry var254 = this.newEntry(var1, var2, var10);
                        this.setValue(var254, var1, var4, var5);
                        var9.set(var7, var254);
                        this.count = var8;
                        this.evictEntries(var254);
                        break;
                     } catch (Throwable var239) {
                        var10000 = var239;
                        var10001 = false;
                        break label1882;
                     }
                  }

                  LocalCache.ValueReference var256;
                  Object var255;
                  label1872: {
                     label1871: {
                        Object var12;
                        try {
                           var12 = var11.getKey();
                           if (var11.getHash() != var2) {
                              break label1871;
                           }
                        } catch (Throwable var252) {
                           var10000 = var252;
                           var10001 = false;
                           break label1882;
                        }

                        if (var12 != null) {
                           try {
                              if (this.map.keyEquivalence.equivalent(var1, var12)) {
                                 var256 = var11.getValueReference();
                                 var255 = var256.get();
                                 break label1872;
                              }
                           } catch (Throwable var251) {
                              var10000 = var251;
                              var10001 = false;
                              break label1882;
                           }
                        }
                     }

                     try {
                        var11 = var11.getNext();
                        continue;
                     } catch (Throwable var245) {
                        var10000 = var245;
                        var10001 = false;
                        break label1882;
                     }
                  }

                  if (var3 != var256) {
                     if (var255 != null) {
                        break label1876;
                     }

                     try {
                        if (var256 == LocalCache.UNSET) {
                           break label1876;
                        }
                     } catch (Throwable var250) {
                        var10000 = var250;
                        var10001 = false;
                        break label1882;
                     }
                  }

                  try {
                     ++this.modCount;
                  } catch (Throwable var244) {
                     var10000 = var244;
                     var10001 = false;
                     break label1882;
                  }

                  var7 = var8;

                  label1885: {
                     try {
                        if (!var3.isActive()) {
                           break label1885;
                        }
                     } catch (Throwable var249) {
                        var10000 = var249;
                        var10001 = false;
                        break label1882;
                     }

                     RemovalCause var257;
                     if (var255 == null) {
                        try {
                           var257 = RemovalCause.COLLECTED;
                        } catch (Throwable var243) {
                           var10000 = var243;
                           var10001 = false;
                           break label1882;
                        }
                     } else {
                        try {
                           var257 = RemovalCause.REPLACED;
                        } catch (Throwable var242) {
                           var10000 = var242;
                           var10001 = false;
                           break label1882;
                        }
                     }

                     try {
                        this.enqueueNotification(var1, var2, var255, var3.getWeight(), var257);
                     } catch (Throwable var241) {
                        var10000 = var241;
                        var10001 = false;
                        break label1882;
                     }

                     var7 = var8 - 1;
                  }

                  try {
                     this.setValue(var11, var1, var4, var5);
                     this.count = var7;
                     this.evictEntries(var11);
                     break;
                  } catch (Throwable var240) {
                     var10000 = var240;
                     var10001 = false;
                     break label1882;
                  }
               }

               this.unlock();
               this.postWriteCleanup();
               return true;
            }

            try {
               this.enqueueNotification(var1, var2, var4, 0, RemovalCause.REPLACED);
            } catch (Throwable var238) {
               var10000 = var238;
               var10001 = false;
               break label1882;
            }

            this.unlock();
            this.postWriteCleanup();
            return false;
         }

         Throwable var253 = var10000;
         this.unlock();
         this.postWriteCleanup();
         throw var253;
      }

      void tryDrainReferenceQueues() {
         if (this.tryLock()) {
            try {
               this.drainReferenceQueues();
            } finally {
               this.unlock();
            }
         }

      }

      void tryExpireEntries(long var1) {
         if (this.tryLock()) {
            try {
               this.expireEntries(var1);
            } finally {
               this.unlock();
            }
         }

      }

      V waitForLoadingValue(ReferenceEntry<K, V> var1, K var2, LocalCache.ValueReference<K, V> var3) throws ExecutionException {
         if (var3.isLoading()) {
            Preconditions.checkState(Thread.holdsLock(var1) ^ true, "Recursive load of: %s", var2);

            Throwable var10000;
            label119: {
               boolean var10001;
               Object var18;
               try {
                  var18 = var3.waitForValue();
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label119;
               }

               if (var18 != null) {
                  label113: {
                     try {
                        this.recordRead(var1, this.map.ticker.read());
                     } catch (Throwable var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label113;
                     }

                     this.statsCounter.recordMisses(1);
                     return var18;
                  }
               } else {
                  label115:
                  try {
                     StringBuilder var17 = new StringBuilder();
                     var17.append("CacheLoader returned null for key ");
                     var17.append(var2);
                     var17.append(".");
                     CacheLoader.InvalidCacheLoadException var19 = new CacheLoader.InvalidCacheLoadException(var17.toString());
                     throw var19;
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label115;
                  }
               }
            }

            Throwable var16 = var10000;
            this.statsCounter.recordMisses(1);
            throw var16;
         } else {
            throw new AssertionError();
         }
      }
   }

   static class SoftValueReference<K, V> extends SoftReference<V> implements LocalCache.ValueReference<K, V> {
      final ReferenceEntry<K, V> entry;

      SoftValueReference(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         super(var2, var1);
         this.entry = var3;
      }

      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         return new LocalCache.SoftValueReference(var1, var2, var3);
      }

      public ReferenceEntry<K, V> getEntry() {
         return this.entry;
      }

      public int getWeight() {
         return 1;
      }

      public boolean isActive() {
         return true;
      }

      public boolean isLoading() {
         return false;
      }

      public void notifyNewValue(V var1) {
      }

      public V waitForValue() {
         return this.get();
      }
   }

   static enum Strength {
      SOFT {
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.identity();
         }

         <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4) {
            Object var5;
            if (var4 == 1) {
               var5 = new LocalCache.SoftValueReference(var1.valueReferenceQueue, var3, var2);
            } else {
               var5 = new LocalCache.WeightedSoftValueReference(var1.valueReferenceQueue, var3, var2, var4);
            }

            return (LocalCache.ValueReference)var5;
         }
      },
      STRONG {
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.equals();
         }

         <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4) {
            Object var5;
            if (var4 == 1) {
               var5 = new LocalCache.StrongValueReference(var3);
            } else {
               var5 = new LocalCache.WeightedStrongValueReference(var3, var4);
            }

            return (LocalCache.ValueReference)var5;
         }
      },
      WEAK;

      static {
         LocalCache.Strength var0 = new LocalCache.Strength("WEAK", 2) {
            Equivalence<Object> defaultEquivalence() {
               return Equivalence.identity();
            }

            <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4) {
               Object var5;
               if (var4 == 1) {
                  var5 = new LocalCache.WeakValueReference(var1.valueReferenceQueue, var3, var2);
               } else {
                  var5 = new LocalCache.WeightedWeakValueReference(var1.valueReferenceQueue, var3, var2, var4);
               }

               return (LocalCache.ValueReference)var5;
            }
         };
         WEAK = var0;
      }

      private Strength() {
      }

      // $FF: synthetic method
      Strength(Object var3) {
         this();
      }

      abstract Equivalence<Object> defaultEquivalence();

      abstract <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4);
   }

   static final class StrongAccessEntry<K, V> extends LocalCache.StrongEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

      StrongAccessEntry(K var1, int var2, @NullableDecl ReferenceEntry<K, V> var3) {
         super(var1, var2, var3);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      public ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public void setAccessTime(long var1) {
         this.accessTime = var1;
      }

      public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
         this.nextAccess = var1;
      }

      public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
         this.previousAccess = var1;
      }
   }

   static final class StrongAccessWriteEntry<K, V> extends LocalCache.StrongEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;

      StrongAccessWriteEntry(K var1, int var2, @NullableDecl ReferenceEntry<K, V> var3) {
         super(var1, var2, var3);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      public ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      public ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setAccessTime(long var1) {
         this.accessTime = var1;
      }

      public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
         this.nextAccess = var1;
      }

      public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
         this.nextWrite = var1;
      }

      public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
         this.previousAccess = var1;
      }

      public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
         this.previousWrite = var1;
      }

      public void setWriteTime(long var1) {
         this.writeTime = var1;
      }
   }

   static class StrongEntry<K, V> extends LocalCache.AbstractReferenceEntry<K, V> {
      final int hash;
      final K key;
      @NullableDecl
      final ReferenceEntry<K, V> next;
      volatile LocalCache.ValueReference<K, V> valueReference;

      StrongEntry(K var1, int var2, @NullableDecl ReferenceEntry<K, V> var3) {
         this.valueReference = LocalCache.UNSET;
         this.key = var1;
         this.hash = var2;
         this.next = var3;
      }

      public int getHash() {
         return this.hash;
      }

      public K getKey() {
         return this.key;
      }

      public ReferenceEntry<K, V> getNext() {
         return this.next;
      }

      public LocalCache.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      public void setValueReference(LocalCache.ValueReference<K, V> var1) {
         this.valueReference = var1;
      }
   }

   static class StrongValueReference<K, V> implements LocalCache.ValueReference<K, V> {
      final V referent;

      StrongValueReference(V var1) {
         this.referent = var1;
      }

      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         return this;
      }

      public V get() {
         return this.referent;
      }

      public ReferenceEntry<K, V> getEntry() {
         return null;
      }

      public int getWeight() {
         return 1;
      }

      public boolean isActive() {
         return true;
      }

      public boolean isLoading() {
         return false;
      }

      public void notifyNewValue(V var1) {
      }

      public V waitForValue() {
         return this.get();
      }
   }

   static final class StrongWriteEntry<K, V> extends LocalCache.StrongEntry<K, V> {
      ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;

      StrongWriteEntry(K var1, int var2, @NullableDecl ReferenceEntry<K, V> var3) {
         super(var1, var2, var3);
      }

      public ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      public ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
         this.nextWrite = var1;
      }

      public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
         this.previousWrite = var1;
      }

      public void setWriteTime(long var1) {
         this.writeTime = var1;
      }
   }

   final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
      ValueIterator() {
         super();
      }

      public V next() {
         return this.nextEntry().getValue();
      }
   }

   interface ValueReference<K, V> {
      LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @NullableDecl V var2, ReferenceEntry<K, V> var3);

      @NullableDecl
      V get();

      @NullableDecl
      ReferenceEntry<K, V> getEntry();

      int getWeight();

      boolean isActive();

      boolean isLoading();

      void notifyNewValue(@NullableDecl V var1);

      V waitForValue() throws ExecutionException;
   }

   final class Values extends AbstractCollection<V> {
      private final ConcurrentMap<?, ?> map;

      Values(ConcurrentMap<?, ?> var2) {
         this.map = var2;
      }

      public void clear() {
         this.map.clear();
      }

      public boolean contains(Object var1) {
         return this.map.containsValue(var1);
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public Iterator<V> iterator() {
         return LocalCache.this.new ValueIterator();
      }

      public int size() {
         return this.map.size();
      }

      public Object[] toArray() {
         return LocalCache.toArrayList(this).toArray();
      }

      public <E> E[] toArray(E[] var1) {
         return LocalCache.toArrayList(this).toArray(var1);
      }
   }

   static final class WeakAccessEntry<K, V> extends LocalCache.WeakEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

      WeakAccessEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
         super(var1, var2, var3, var4);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      public ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public void setAccessTime(long var1) {
         this.accessTime = var1;
      }

      public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
         this.nextAccess = var1;
      }

      public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
         this.previousAccess = var1;
      }
   }

   static final class WeakAccessWriteEntry<K, V> extends LocalCache.WeakEntry<K, V> {
      volatile long accessTime = Long.MAX_VALUE;
      ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
      ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;

      WeakAccessWriteEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
         super(var1, var2, var3, var4);
      }

      public long getAccessTime() {
         return this.accessTime;
      }

      public ReferenceEntry<K, V> getNextInAccessQueue() {
         return this.nextAccess;
      }

      public ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      public ReferenceEntry<K, V> getPreviousInAccessQueue() {
         return this.previousAccess;
      }

      public ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setAccessTime(long var1) {
         this.accessTime = var1;
      }

      public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
         this.nextAccess = var1;
      }

      public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
         this.nextWrite = var1;
      }

      public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
         this.previousAccess = var1;
      }

      public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
         this.previousWrite = var1;
      }

      public void setWriteTime(long var1) {
         this.writeTime = var1;
      }
   }

   static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
      final int hash;
      @NullableDecl
      final ReferenceEntry<K, V> next;
      volatile LocalCache.ValueReference<K, V> valueReference;

      WeakEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
         super(var2, var1);
         this.valueReference = LocalCache.UNSET;
         this.hash = var3;
         this.next = var4;
      }

      public long getAccessTime() {
         throw new UnsupportedOperationException();
      }

      public int getHash() {
         return this.hash;
      }

      public K getKey() {
         return this.get();
      }

      public ReferenceEntry<K, V> getNext() {
         return this.next;
      }

      public ReferenceEntry<K, V> getNextInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getNextInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getPreviousInAccessQueue() {
         throw new UnsupportedOperationException();
      }

      public ReferenceEntry<K, V> getPreviousInWriteQueue() {
         throw new UnsupportedOperationException();
      }

      public LocalCache.ValueReference<K, V> getValueReference() {
         return this.valueReference;
      }

      public long getWriteTime() {
         throw new UnsupportedOperationException();
      }

      public void setAccessTime(long var1) {
         throw new UnsupportedOperationException();
      }

      public void setNextInAccessQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
         throw new UnsupportedOperationException();
      }

      public void setValueReference(LocalCache.ValueReference<K, V> var1) {
         this.valueReference = var1;
      }

      public void setWriteTime(long var1) {
         throw new UnsupportedOperationException();
      }
   }

   static class WeakValueReference<K, V> extends WeakReference<V> implements LocalCache.ValueReference<K, V> {
      final ReferenceEntry<K, V> entry;

      WeakValueReference(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         super(var2, var1);
         this.entry = var3;
      }

      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         return new LocalCache.WeakValueReference(var1, var2, var3);
      }

      public ReferenceEntry<K, V> getEntry() {
         return this.entry;
      }

      public int getWeight() {
         return 1;
      }

      public boolean isActive() {
         return true;
      }

      public boolean isLoading() {
         return false;
      }

      public void notifyNewValue(V var1) {
      }

      public V waitForValue() {
         return this.get();
      }
   }

   static final class WeakWriteEntry<K, V> extends LocalCache.WeakEntry<K, V> {
      ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
      ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
      volatile long writeTime = Long.MAX_VALUE;

      WeakWriteEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl ReferenceEntry<K, V> var4) {
         super(var1, var2, var3, var4);
      }

      public ReferenceEntry<K, V> getNextInWriteQueue() {
         return this.nextWrite;
      }

      public ReferenceEntry<K, V> getPreviousInWriteQueue() {
         return this.previousWrite;
      }

      public long getWriteTime() {
         return this.writeTime;
      }

      public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
         this.nextWrite = var1;
      }

      public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
         this.previousWrite = var1;
      }

      public void setWriteTime(long var1) {
         this.writeTime = var1;
      }
   }

   static final class WeightedSoftValueReference<K, V> extends LocalCache.SoftValueReference<K, V> {
      final int weight;

      WeightedSoftValueReference(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3, int var4) {
         super(var1, var2, var3);
         this.weight = var4;
      }

      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         return new LocalCache.WeightedSoftValueReference(var1, var2, var3, this.weight);
      }

      public int getWeight() {
         return this.weight;
      }
   }

   static final class WeightedStrongValueReference<K, V> extends LocalCache.StrongValueReference<K, V> {
      final int weight;

      WeightedStrongValueReference(V var1, int var2) {
         super(var1);
         this.weight = var2;
      }

      public int getWeight() {
         return this.weight;
      }
   }

   static final class WeightedWeakValueReference<K, V> extends LocalCache.WeakValueReference<K, V> {
      final int weight;

      WeightedWeakValueReference(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3, int var4) {
         super(var1, var2, var3);
         this.weight = var4;
      }

      public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3) {
         return new LocalCache.WeightedWeakValueReference(var1, var2, var3, this.weight);
      }

      public int getWeight() {
         return this.weight;
      }
   }

   static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
      final ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry<K, V>() {
         ReferenceEntry<K, V> nextWrite = this;
         ReferenceEntry<K, V> previousWrite = this;

         public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
         }

         public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
         }

         public long getWriteTime() {
            return Long.MAX_VALUE;
         }

         public void setNextInWriteQueue(ReferenceEntry<K, V> var1) {
            this.nextWrite = var1;
         }

         public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1) {
            this.previousWrite = var1;
         }

         public void setWriteTime(long var1) {
         }
      };

      public void clear() {
         ReferenceEntry var1 = this.head.getNextInWriteQueue();

         while(true) {
            ReferenceEntry var2 = this.head;
            if (var1 == var2) {
               var2.setNextInWriteQueue(var2);
               var1 = this.head;
               var1.setPreviousInWriteQueue(var1);
               return;
            }

            var2 = var1.getNextInWriteQueue();
            LocalCache.nullifyWriteOrder(var1);
            var1 = var2;
         }
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (((ReferenceEntry)var1).getNextInWriteQueue() != LocalCache.NullEntry.INSTANCE) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean isEmpty() {
         boolean var1;
         if (this.head.getNextInWriteQueue() == this.head) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Iterator<ReferenceEntry<K, V>> iterator() {
         return new AbstractSequentialIterator<ReferenceEntry<K, V>>(this.peek()) {
            protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> var1) {
               ReferenceEntry var2 = var1.getNextInWriteQueue();
               var1 = var2;
               if (var2 == WriteQueue.this.head) {
                  var1 = null;
               }

               return var1;
            }
         };
      }

      public boolean offer(ReferenceEntry<K, V> var1) {
         LocalCache.connectWriteOrder(var1.getPreviousInWriteQueue(), var1.getNextInWriteQueue());
         LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), var1);
         LocalCache.connectWriteOrder(var1, this.head);
         return true;
      }

      public ReferenceEntry<K, V> peek() {
         ReferenceEntry var1 = this.head.getNextInWriteQueue();
         ReferenceEntry var2 = var1;
         if (var1 == this.head) {
            var2 = null;
         }

         return var2;
      }

      public ReferenceEntry<K, V> poll() {
         ReferenceEntry var1 = this.head.getNextInWriteQueue();
         if (var1 == this.head) {
            return null;
         } else {
            this.remove(var1);
            return var1;
         }
      }

      public boolean remove(Object var1) {
         ReferenceEntry var2 = (ReferenceEntry)var1;
         ReferenceEntry var5 = var2.getPreviousInWriteQueue();
         ReferenceEntry var3 = var2.getNextInWriteQueue();
         LocalCache.connectWriteOrder(var5, var3);
         LocalCache.nullifyWriteOrder(var2);
         boolean var4;
         if (var3 != LocalCache.NullEntry.INSTANCE) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      public int size() {
         ReferenceEntry var1 = this.head.getNextInWriteQueue();

         int var2;
         for(var2 = 0; var1 != this.head; var1 = var1.getNextInWriteQueue()) {
            ++var2;
         }

         return var2;
      }
   }

   final class WriteThroughEntry implements Entry<K, V> {
      final K key;
      V value;

      WriteThroughEntry(K var2, V var3) {
         this.key = var2;
         this.value = var3;
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Entry var5 = (Entry)var1;
            var4 = var3;
            if (this.key.equals(var5.getKey())) {
               var4 = var3;
               if (this.value.equals(var5.getValue())) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public K getKey() {
         return this.key;
      }

      public V getValue() {
         return this.value;
      }

      public int hashCode() {
         return this.key.hashCode() ^ this.value.hashCode();
      }

      public V setValue(V var1) {
         Object var2 = LocalCache.this.put(this.key, var1);
         this.value = var1;
         return var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.getKey());
         var1.append("=");
         var1.append(this.getValue());
         return var1.toString();
      }
   }
}

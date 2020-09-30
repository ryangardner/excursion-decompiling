package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
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
import java.util.Map.Entry;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MapMakerInternalMap<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>, S extends MapMakerInternalMap.Segment<K, V, E, S>> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
   static final long CLEANUP_EXECUTOR_DELAY_SECS = 60L;
   static final int CONTAINS_VALUE_RETRIES = 3;
   static final int DRAIN_MAX = 16;
   static final int DRAIN_THRESHOLD = 63;
   static final int MAXIMUM_CAPACITY = 1073741824;
   static final int MAX_SEGMENTS = 65536;
   static final MapMakerInternalMap.WeakValueReference<Object, Object, MapMakerInternalMap.DummyInternalEntry> UNSET_WEAK_VALUE_REFERENCE = new MapMakerInternalMap.WeakValueReference<Object, Object, MapMakerInternalMap.DummyInternalEntry>() {
      public void clear() {
      }

      public MapMakerInternalMap.WeakValueReference<Object, Object, MapMakerInternalMap.DummyInternalEntry> copyFor(ReferenceQueue<Object> var1, MapMakerInternalMap.DummyInternalEntry var2) {
         return this;
      }

      public Object get() {
         return null;
      }

      public MapMakerInternalMap.DummyInternalEntry getEntry() {
         return null;
      }
   };
   private static final long serialVersionUID = 5L;
   final int concurrencyLevel;
   final transient MapMakerInternalMap.InternalEntryHelper<K, V, E, S> entryHelper;
   @MonotonicNonNullDecl
   transient Set<Entry<K, V>> entrySet;
   final Equivalence<Object> keyEquivalence;
   @MonotonicNonNullDecl
   transient Set<K> keySet;
   final transient int segmentMask;
   final transient int segmentShift;
   final transient MapMakerInternalMap.Segment<K, V, E, S>[] segments;
   @MonotonicNonNullDecl
   transient Collection<V> values;

   private MapMakerInternalMap(MapMaker var1, MapMakerInternalMap.InternalEntryHelper<K, V, E, S> var2) {
      this.concurrencyLevel = Math.min(var1.getConcurrencyLevel(), 65536);
      this.keyEquivalence = var1.getKeyEquivalence();
      this.entryHelper = var2;
      int var3 = Math.min(var1.getInitialCapacity(), 1073741824);
      byte var4 = 0;
      byte var5 = 1;
      int var6 = 1;

      int var7;
      for(var7 = 0; var6 < this.concurrencyLevel; var6 <<= 1) {
         ++var7;
      }

      this.segmentShift = 32 - var7;
      this.segmentMask = var6 - 1;
      this.segments = this.newSegmentArray(var6);
      int var8 = var3 / var6;
      var7 = var5;
      int var9 = var8;
      if (var6 * var8 < var3) {
         var9 = var8 + 1;
         var7 = var5;
      }

      while(true) {
         var6 = var4;
         if (var7 >= var9) {
            while(true) {
               MapMakerInternalMap.Segment[] var10 = this.segments;
               if (var6 >= var10.length) {
                  return;
               }

               var10[var6] = this.createSegment(var7, -1);
               ++var6;
            }
         }

         var7 <<= 1;
      }
   }

   static <K, V> MapMakerInternalMap<K, V, ? extends MapMakerInternalMap.InternalEntry<K, V, ?>, ?> create(MapMaker var0) {
      if (var0.getKeyStrength() == MapMakerInternalMap.Strength.STRONG && var0.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(var0, MapMakerInternalMap.StrongKeyStrongValueEntry.Helper.INSTANCE);
      } else if (var0.getKeyStrength() == MapMakerInternalMap.Strength.STRONG && var0.getValueStrength() == MapMakerInternalMap.Strength.WEAK) {
         return new MapMakerInternalMap(var0, MapMakerInternalMap.StrongKeyWeakValueEntry.Helper.INSTANCE);
      } else if (var0.getKeyStrength() == MapMakerInternalMap.Strength.WEAK && var0.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(var0, MapMakerInternalMap.WeakKeyStrongValueEntry.Helper.INSTANCE);
      } else if (var0.getKeyStrength() == MapMakerInternalMap.Strength.WEAK && var0.getValueStrength() == MapMakerInternalMap.Strength.WEAK) {
         return new MapMakerInternalMap(var0, MapMakerInternalMap.WeakKeyWeakValueEntry.Helper.INSTANCE);
      } else {
         throw new AssertionError();
      }
   }

   static <K> MapMakerInternalMap<K, MapMaker.Dummy, ? extends MapMakerInternalMap.InternalEntry<K, MapMaker.Dummy, ?>, ?> createWithDummyValues(MapMaker var0) {
      if (var0.getKeyStrength() == MapMakerInternalMap.Strength.STRONG && var0.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(var0, MapMakerInternalMap.StrongKeyDummyValueEntry.Helper.INSTANCE);
      } else if (var0.getKeyStrength() == MapMakerInternalMap.Strength.WEAK && var0.getValueStrength() == MapMakerInternalMap.Strength.STRONG) {
         return new MapMakerInternalMap(var0, MapMakerInternalMap.WeakKeyDummyValueEntry.Helper.INSTANCE);
      } else if (var0.getValueStrength() == MapMakerInternalMap.Strength.WEAK) {
         throw new IllegalArgumentException("Map cannot have both weak and dummy values");
      } else {
         throw new AssertionError();
      }
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

   static <K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> MapMakerInternalMap.WeakValueReference<K, V, E> unsetWeakValueReference() {
      return UNSET_WEAK_VALUE_REFERENCE;
   }

   public void clear() {
      MapMakerInternalMap.Segment[] var1 = this.segments;
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
         MapMakerInternalMap.Segment[] var2 = this.segments;
         long var3 = -1L;

         long var6;
         for(int var5 = 0; var5 < 3; var3 = var6) {
            var6 = 0L;
            int var8 = var2.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               MapMakerInternalMap.Segment var10 = var2[var9];
               int var11 = var10.count;
               AtomicReferenceArray var12 = var10.table;

               for(var11 = 0; var11 < var12.length(); ++var11) {
                  for(MapMakerInternalMap.InternalEntry var13 = (MapMakerInternalMap.InternalEntry)var12.get(var11); var13 != null; var13 = var13.getNext()) {
                     Object var14 = var10.getLiveValue(var13);
                     if (var14 != null && this.valueEquivalence().equivalent(var1, var14)) {
                        return true;
                     }
                  }
               }

               var6 += (long)var10.modCount;
            }

            if (var6 == var3) {
               break;
            }

            ++var5;
         }

         return false;
      }
   }

   E copyEntry(E var1, E var2) {
      return this.segmentFor(var1.getHash()).copyEntry(var1, var2);
   }

   MapMakerInternalMap.Segment<K, V, E, S> createSegment(int var1, int var2) {
      return this.entryHelper.newSegment(this, var1, var2);
   }

   public Set<Entry<K, V>> entrySet() {
      Object var1 = this.entrySet;
      if (var1 == null) {
         var1 = new MapMakerInternalMap.EntrySet();
         this.entrySet = (Set)var1;
      }

      return (Set)var1;
   }

   public V get(@NullableDecl Object var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this.hash(var1);
         return this.segmentFor(var2).get(var1, var2);
      }
   }

   E getEntry(@NullableDecl Object var1) {
      if (var1 == null) {
         return null;
      } else {
         int var2 = this.hash(var1);
         return this.segmentFor(var2).getEntry(var1, var2);
      }
   }

   V getLiveValue(E var1) {
      return var1.getKey() == null ? null : var1.getValue();
   }

   int hash(Object var1) {
      return rehash(this.keyEquivalence.hash(var1));
   }

   public boolean isEmpty() {
      MapMakerInternalMap.Segment[] var1 = this.segments;
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

   boolean isLiveForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
      boolean var2;
      if (this.segmentFor(var1.getHash()).getLiveValueForTesting(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Set<K> keySet() {
      Object var1 = this.keySet;
      if (var1 == null) {
         var1 = new MapMakerInternalMap.KeySet();
         this.keySet = (Set)var1;
      }

      return (Set)var1;
   }

   MapMakerInternalMap.Strength keyStrength() {
      return this.entryHelper.keyStrength();
   }

   final MapMakerInternalMap.Segment<K, V, E, S>[] newSegmentArray(int var1) {
      return new MapMakerInternalMap.Segment[var1];
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

   void reclaimKey(E var1) {
      int var2 = var1.getHash();
      this.segmentFor(var2).reclaimKey(var1, var2);
   }

   void reclaimValue(MapMakerInternalMap.WeakValueReference<K, V, E> var1) {
      MapMakerInternalMap.InternalEntry var2 = var1.getEntry();
      int var3 = var2.getHash();
      this.segmentFor(var3).reclaimValue(var2.getKey(), var3, var1);
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

   MapMakerInternalMap.Segment<K, V, E, S> segmentFor(int var1) {
      return this.segments[var1 >>> this.segmentShift & this.segmentMask];
   }

   public int size() {
      MapMakerInternalMap.Segment[] var1 = this.segments;
      long var2 = 0L;

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var2 += (long)var1[var4].count;
      }

      return Ints.saturatedCast(var2);
   }

   Equivalence<Object> valueEquivalence() {
      return this.entryHelper.valueStrength().defaultEquivalence();
   }

   MapMakerInternalMap.Strength valueStrength() {
      return this.entryHelper.valueStrength();
   }

   public Collection<V> values() {
      Object var1 = this.values;
      if (var1 == null) {
         var1 = new MapMakerInternalMap.Values();
         this.values = (Collection)var1;
      }

      return (Collection)var1;
   }

   Object writeReplace() {
      return new MapMakerInternalMap.SerializationProxy(this.entryHelper.keyStrength(), this.entryHelper.valueStrength(), this.keyEquivalence, this.entryHelper.valueStrength().defaultEquivalence(), this.concurrencyLevel, this);
   }

   abstract static class AbstractSerializationProxy<K, V> extends ForwardingConcurrentMap<K, V> implements Serializable {
      private static final long serialVersionUID = 3L;
      final int concurrencyLevel;
      transient ConcurrentMap<K, V> delegate;
      final Equivalence<Object> keyEquivalence;
      final MapMakerInternalMap.Strength keyStrength;
      final Equivalence<Object> valueEquivalence;
      final MapMakerInternalMap.Strength valueStrength;

      AbstractSerializationProxy(MapMakerInternalMap.Strength var1, MapMakerInternalMap.Strength var2, Equivalence<Object> var3, Equivalence<Object> var4, int var5, ConcurrentMap<K, V> var6) {
         this.keyStrength = var1;
         this.valueStrength = var2;
         this.keyEquivalence = var3;
         this.valueEquivalence = var4;
         this.concurrencyLevel = var5;
         this.delegate = var6;
      }

      protected ConcurrentMap<K, V> delegate() {
         return this.delegate;
      }

      void readEntries(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         while(true) {
            Object var2 = var1.readObject();
            if (var2 == null) {
               return;
            }

            Object var3 = var1.readObject();
            this.delegate.put(var2, var3);
         }
      }

      MapMaker readMapMaker(ObjectInputStream var1) throws IOException {
         int var2 = var1.readInt();
         return (new MapMaker()).initialCapacity(var2).setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).concurrencyLevel(this.concurrencyLevel);
      }

      void writeMapTo(ObjectOutputStream var1) throws IOException {
         var1.writeInt(this.delegate.size());
         Iterator var2 = this.delegate.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.writeObject(var3.getKey());
            var1.writeObject(var3.getValue());
         }

         var1.writeObject((Object)null);
      }
   }

   abstract static class AbstractStrongKeyEntry<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> implements MapMakerInternalMap.InternalEntry<K, V, E> {
      final int hash;
      final K key;
      @NullableDecl
      final E next;

      AbstractStrongKeyEntry(K var1, int var2, @NullableDecl E var3) {
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

      public E getNext() {
         return this.next;
      }
   }

   abstract static class AbstractWeakKeyEntry<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> extends WeakReference<K> implements MapMakerInternalMap.InternalEntry<K, V, E> {
      final int hash;
      @NullableDecl
      final E next;

      AbstractWeakKeyEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl E var4) {
         super(var2, var1);
         this.hash = var3;
         this.next = var4;
      }

      public int getHash() {
         return this.hash;
      }

      public K getKey() {
         return this.get();
      }

      public E getNext() {
         return this.next;
      }
   }

   static final class CleanupMapTask implements Runnable {
      final WeakReference<MapMakerInternalMap<?, ?, ?, ?>> mapReference;

      public CleanupMapTask(MapMakerInternalMap<?, ?, ?, ?> var1) {
         this.mapReference = new WeakReference(var1);
      }

      public void run() {
         MapMakerInternalMap var1 = (MapMakerInternalMap)this.mapReference.get();
         if (var1 == null) {
            throw new CancellationException();
         } else {
            MapMakerInternalMap.Segment[] var4 = var1.segments;
            int var2 = var4.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               var4[var3].runCleanup();
            }

         }
      }
   }

   static final class DummyInternalEntry implements MapMakerInternalMap.InternalEntry<Object, Object, MapMakerInternalMap.DummyInternalEntry> {
      private DummyInternalEntry() {
         throw new AssertionError();
      }

      public int getHash() {
         throw new AssertionError();
      }

      public Object getKey() {
         throw new AssertionError();
      }

      public MapMakerInternalMap.DummyInternalEntry getNext() {
         throw new AssertionError();
      }

      public Object getValue() {
         throw new AssertionError();
      }
   }

   final class EntryIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<Entry<K, V>> {
      EntryIterator() {
         super();
      }

      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   final class EntrySet extends MapMakerInternalMap.SafeToArraySet<Entry<K, V>> {
      EntrySet() {
         super(null);
      }

      public void clear() {
         MapMakerInternalMap.this.clear();
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
               var4 = MapMakerInternalMap.this.get(var4);
               var2 = var3;
               if (var4 != null) {
                  var2 = var3;
                  if (MapMakerInternalMap.this.valueEquivalence().equivalent(var5.getValue(), var4)) {
                     var2 = true;
                  }
               }

               return var2;
            }
         }
      }

      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      public Iterator<Entry<K, V>> iterator() {
         return MapMakerInternalMap.this.new EntryIterator();
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
               if (MapMakerInternalMap.this.remove(var1, var4.getValue())) {
                  var2 = true;
               }
            }

            return var2;
         }
      }

      public int size() {
         return MapMakerInternalMap.this.size();
      }
   }

   abstract class HashIterator<T> implements Iterator<T> {
      @MonotonicNonNullDecl
      MapMakerInternalMap.Segment<K, V, E, S> currentSegment;
      @MonotonicNonNullDecl
      AtomicReferenceArray<E> currentTable;
      @NullableDecl
      MapMakerInternalMap<K, V, E, S>.WriteThroughEntry lastReturned;
      @NullableDecl
      E nextEntry;
      @NullableDecl
      MapMakerInternalMap<K, V, E, S>.WriteThroughEntry nextExternal;
      int nextSegmentIndex;
      int nextTableIndex;

      HashIterator() {
         this.nextSegmentIndex = MapMakerInternalMap.this.segments.length - 1;
         this.nextTableIndex = -1;
         this.advance();
      }

      final void advance() {
         this.nextExternal = null;
         if (!this.nextInChain()) {
            if (!this.nextInTable()) {
               while(this.nextSegmentIndex >= 0) {
                  MapMakerInternalMap.Segment[] var1 = MapMakerInternalMap.this.segments;
                  int var2 = this.nextSegmentIndex--;
                  MapMakerInternalMap.Segment var3 = var1[var2];
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

      boolean advanceTo(E param1) {
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

      MapMakerInternalMap<K, V, E, S>.WriteThroughEntry nextEntry() {
         MapMakerInternalMap.WriteThroughEntry var1 = this.nextExternal;
         if (var1 != null) {
            this.lastReturned = var1;
            this.advance();
            return this.lastReturned;
         } else {
            throw new NoSuchElementException();
         }
      }

      boolean nextInChain() {
         MapMakerInternalMap.InternalEntry var1 = this.nextEntry;
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
               MapMakerInternalMap.InternalEntry var3 = (MapMakerInternalMap.InternalEntry)var2.get(var1);
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

         CollectPreconditions.checkRemove(var1);
         MapMakerInternalMap.this.remove(this.lastReturned.getKey());
         this.lastReturned = null;
      }
   }

   interface InternalEntry<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> {
      int getHash();

      K getKey();

      E getNext();

      V getValue();
   }

   interface InternalEntryHelper<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>, S extends MapMakerInternalMap.Segment<K, V, E, S>> {
      E copy(S var1, E var2, @NullableDecl E var3);

      MapMakerInternalMap.Strength keyStrength();

      E newEntry(S var1, K var2, int var3, @NullableDecl E var4);

      S newSegment(MapMakerInternalMap<K, V, E, S> var1, int var2, int var3);

      void setValue(S var1, E var2, V var3);

      MapMakerInternalMap.Strength valueStrength();
   }

   final class KeyIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<K> {
      KeyIterator() {
         super();
      }

      public K next() {
         return this.nextEntry().getKey();
      }
   }

   final class KeySet extends MapMakerInternalMap.SafeToArraySet<K> {
      KeySet() {
         super(null);
      }

      public void clear() {
         MapMakerInternalMap.this.clear();
      }

      public boolean contains(Object var1) {
         return MapMakerInternalMap.this.containsKey(var1);
      }

      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      public Iterator<K> iterator() {
         return MapMakerInternalMap.this.new KeyIterator();
      }

      public boolean remove(Object var1) {
         boolean var2;
         if (MapMakerInternalMap.this.remove(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public int size() {
         return MapMakerInternalMap.this.size();
      }
   }

   private abstract static class SafeToArraySet<E> extends AbstractSet<E> {
      private SafeToArraySet() {
      }

      // $FF: synthetic method
      SafeToArraySet(Object var1) {
         this();
      }

      public Object[] toArray() {
         return MapMakerInternalMap.toArrayList(this).toArray();
      }

      public <T> T[] toArray(T[] var1) {
         return MapMakerInternalMap.toArrayList(this).toArray(var1);
      }
   }

   abstract static class Segment<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>, S extends MapMakerInternalMap.Segment<K, V, E, S>> extends ReentrantLock {
      volatile int count;
      final MapMakerInternalMap<K, V, E, S> map;
      final int maxSegmentSize;
      int modCount;
      final AtomicInteger readCount = new AtomicInteger();
      @MonotonicNonNullDecl
      volatile AtomicReferenceArray<E> table;
      int threshold;

      Segment(MapMakerInternalMap<K, V, E, S> var1, int var2, int var3) {
         this.map = var1;
         this.maxSegmentSize = var3;
         this.initTable(this.newEntryArray(var2));
      }

      static <K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> boolean isCollected(E var0) {
         boolean var1;
         if (var0.getValue() == null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      abstract E castForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1);

      void clear() {
         if (this.count != 0) {
            this.lock();

            label167: {
               Throwable var10000;
               label172: {
                  AtomicReferenceArray var1;
                  boolean var10001;
                  try {
                     var1 = this.table;
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label172;
                  }

                  int var2 = 0;

                  while(true) {
                     try {
                        if (var2 >= var1.length()) {
                           break;
                        }

                        var1.set(var2, (Object)null);
                     } catch (Throwable var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label172;
                     }

                     ++var2;
                  }

                  label154:
                  try {
                     this.maybeClearReferenceQueues();
                     this.readCount.set(0);
                     ++this.modCount;
                     this.count = 0;
                     break label167;
                  } catch (Throwable var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label154;
                  }
               }

               Throwable var15 = var10000;
               this.unlock();
               throw var15;
            }

            this.unlock();
         }

      }

      <T> void clearReferenceQueue(ReferenceQueue<T> var1) {
         while(var1.poll() != null) {
         }

      }

      boolean clearValueForTesting(K var1, int var2, MapMakerInternalMap.WeakValueReference<K, V, ? extends MapMakerInternalMap.InternalEntry<K, V, ?>> var3) {
         this.lock();

         Throwable var10000;
         label278: {
            AtomicReferenceArray var4;
            int var5;
            MapMakerInternalMap.InternalEntry var6;
            boolean var10001;
            try {
               var4 = this.table;
               var5 = var4.length() - 1 & var2;
               var6 = (MapMakerInternalMap.InternalEntry)var4.get(var5);
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break label278;
            }

            MapMakerInternalMap.InternalEntry var7 = var6;

            while(true) {
               if (var7 == null) {
                  this.unlock();
                  return false;
               }

               label280: {
                  Object var8;
                  try {
                     var8 = var7.getKey();
                     if (var7.getHash() != var2) {
                        break label280;
                     }
                  } catch (Throwable var26) {
                     var10000 = var26;
                     var10001 = false;
                     break;
                  }

                  if (var8 != null) {
                     label282: {
                        label265: {
                           try {
                              if (!this.map.keyEquivalence.equivalent(var1, var8)) {
                                 break label282;
                              }

                              if (((MapMakerInternalMap.WeakValueEntry)var7).getValueReference() == var3) {
                                 var4.set(var5, this.removeFromChain(var6, var7));
                                 break label265;
                              }
                           } catch (Throwable var27) {
                              var10000 = var27;
                              var10001 = false;
                              break;
                           }

                           this.unlock();
                           return false;
                        }

                        this.unlock();
                        return true;
                     }
                  }
               }

               try {
                  var7 = var7.getNext();
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var29 = var10000;
         this.unlock();
         throw var29;
      }

      boolean containsKey(Object var1, int var2) {
         Throwable var10000;
         label132: {
            int var3;
            boolean var10001;
            try {
               var3 = this.count;
            } catch (Throwable var17) {
               var10000 = var17;
               var10001 = false;
               break label132;
            }

            boolean var4 = false;
            if (var3 == 0) {
               this.postReadCleanup();
               return false;
            }

            MapMakerInternalMap.InternalEntry var18;
            try {
               var18 = this.getLiveEntry(var1, var2);
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label132;
            }

            boolean var5 = var4;
            if (var18 != null) {
               try {
                  var1 = var18.getValue();
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label132;
               }

               var5 = var4;
               if (var1 != null) {
                  var5 = true;
               }
            }

            this.postReadCleanup();
            return var5;
         }

         Throwable var19 = var10000;
         this.postReadCleanup();
         throw var19;
      }

      boolean containsValue(Object var1) {
         label328: {
            Throwable var10000;
            label330: {
               AtomicReferenceArray var2;
               int var3;
               boolean var10001;
               try {
                  if (this.count == 0) {
                     break label328;
                  }

                  var2 = this.table;
                  var3 = var2.length();
               } catch (Throwable var37) {
                  var10000 = var37;
                  var10001 = false;
                  break label330;
               }

               int var4 = 0;

               label323:
               while(true) {
                  if (var4 >= var3) {
                     break label328;
                  }

                  MapMakerInternalMap.InternalEntry var5;
                  try {
                     var5 = (MapMakerInternalMap.InternalEntry)var2.get(var4);
                  } catch (Throwable var36) {
                     var10000 = var36;
                     var10001 = false;
                     break;
                  }

                  while(var5 != null) {
                     Object var6;
                     try {
                        var6 = this.getLiveValue(var5);
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label323;
                     }

                     if (var6 != null) {
                        boolean var7;
                        try {
                           var7 = this.map.valueEquivalence().equivalent(var1, var6);
                        } catch (Throwable var34) {
                           var10000 = var34;
                           var10001 = false;
                           break label323;
                        }

                        if (var7) {
                           this.postReadCleanup();
                           return true;
                        }
                     }

                     try {
                        var5 = var5.getNext();
                     } catch (Throwable var33) {
                        var10000 = var33;
                        var10001 = false;
                        break label323;
                     }
                  }

                  ++var4;
               }
            }

            Throwable var38 = var10000;
            this.postReadCleanup();
            throw var38;
         }

         this.postReadCleanup();
         return false;
      }

      E copyEntry(E var1, E var2) {
         return this.map.entryHelper.copy(this.self(), var1, var2);
      }

      E copyForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, @NullableDecl MapMakerInternalMap.InternalEntry<K, V, ?> var2) {
         return this.map.entryHelper.copy(this.self(), this.castForTesting(var1), this.castForTesting(var2));
      }

      void drainKeyReferenceQueue(ReferenceQueue<K> var1) {
         int var2 = 0;

         int var4;
         do {
            Reference var3 = var1.poll();
            if (var3 == null) {
               break;
            }

            MapMakerInternalMap.InternalEntry var5 = (MapMakerInternalMap.InternalEntry)var3;
            this.map.reclaimKey(var5);
            var4 = var2 + 1;
            var2 = var4;
         } while(var4 != 16);

      }

      void drainValueReferenceQueue(ReferenceQueue<V> var1) {
         int var2 = 0;

         int var4;
         do {
            Reference var3 = var1.poll();
            if (var3 == null) {
               break;
            }

            MapMakerInternalMap.WeakValueReference var5 = (MapMakerInternalMap.WeakValueReference)var3;
            this.map.reclaimValue(var5);
            var4 = var2 + 1;
            var2 = var4;
         } while(var4 != 16);

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
               MapMakerInternalMap.InternalEntry var7 = (MapMakerInternalMap.InternalEntry)var1.get(var6);
               var8 = var3;
               if (var7 != null) {
                  MapMakerInternalMap.InternalEntry var9 = var7.getNext();
                  int var10 = var7.getHash() & var5;
                  if (var9 == null) {
                     var4.set(var10, var7);
                     var8 = var3;
                  } else {
                     MapMakerInternalMap.InternalEntry var11;
                     for(var11 = var7; var9 != null; var10 = var8) {
                        int var12 = var9.getHash() & var5;
                        var8 = var10;
                        if (var12 != var10) {
                           var11 = var9;
                           var8 = var12;
                        }

                        var9 = var9.getNext();
                     }

                     var4.set(var10, var11);

                     while(true) {
                        var8 = var3;
                        if (var7 == var11) {
                           break;
                        }

                        var8 = var7.getHash() & var5;
                        var9 = this.copyEntry(var7, (MapMakerInternalMap.InternalEntry)var4.get(var8));
                        if (var9 != null) {
                           var4.set(var8, var9);
                        } else {
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

      V get(Object var1, int var2) {
         Throwable var10000;
         label116: {
            boolean var10001;
            MapMakerInternalMap.InternalEntry var15;
            try {
               var15 = this.getLiveEntry(var1, var2);
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label116;
            }

            if (var15 == null) {
               this.postReadCleanup();
               return null;
            }

            try {
               var1 = var15.getValue();
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label116;
            }

            if (var1 == null) {
               try {
                  this.tryDrainReferenceQueues();
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label116;
               }
            }

            this.postReadCleanup();
            return var1;
         }

         Throwable var16 = var10000;
         this.postReadCleanup();
         throw var16;
      }

      E getEntry(Object var1, int var2) {
         if (this.count != 0) {
            for(MapMakerInternalMap.InternalEntry var3 = this.getFirst(var2); var3 != null; var3 = var3.getNext()) {
               if (var3.getHash() == var2) {
                  Object var4 = var3.getKey();
                  if (var4 == null) {
                     this.tryDrainReferenceQueues();
                  } else if (this.map.keyEquivalence.equivalent(var1, var4)) {
                     return var3;
                  }
               }
            }
         }

         return null;
      }

      E getFirst(int var1) {
         AtomicReferenceArray var2 = this.table;
         return (MapMakerInternalMap.InternalEntry)var2.get(var1 & var2.length() - 1);
      }

      ReferenceQueue<K> getKeyReferenceQueueForTesting() {
         throw new AssertionError();
      }

      E getLiveEntry(Object var1, int var2) {
         return this.getEntry(var1, var2);
      }

      @NullableDecl
      V getLiveValue(E var1) {
         if (var1.getKey() == null) {
            this.tryDrainReferenceQueues();
            return null;
         } else {
            Object var2 = var1.getValue();
            if (var2 == null) {
               this.tryDrainReferenceQueues();
               return null;
            } else {
               return var2;
            }
         }
      }

      @NullableDecl
      V getLiveValueForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return this.getLiveValue(this.castForTesting(var1));
      }

      ReferenceQueue<V> getValueReferenceQueueForTesting() {
         throw new AssertionError();
      }

      MapMakerInternalMap.WeakValueReference<K, V, E> getWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         throw new AssertionError();
      }

      void initTable(AtomicReferenceArray<E> var1) {
         int var2 = var1.length() * 3 / 4;
         this.threshold = var2;
         if (var2 == this.maxSegmentSize) {
            this.threshold = var2 + 1;
         }

         this.table = var1;
      }

      void maybeClearReferenceQueues() {
      }

      void maybeDrainReferenceQueues() {
      }

      AtomicReferenceArray<E> newEntryArray(int var1) {
         return new AtomicReferenceArray(var1);
      }

      E newEntryForTesting(K var1, int var2, @NullableDecl MapMakerInternalMap.InternalEntry<K, V, ?> var3) {
         return this.map.entryHelper.newEntry(this.self(), var1, var2, this.castForTesting(var3));
      }

      MapMakerInternalMap.WeakValueReference<K, V, E> newWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, V var2) {
         throw new AssertionError();
      }

      void postReadCleanup() {
         if ((this.readCount.incrementAndGet() & 63) == 0) {
            this.runCleanup();
         }

      }

      void preWriteCleanup() {
         this.runLockedCleanup();
      }

      V put(K var1, int var2, V var3, boolean var4) {
         this.lock();

         Throwable var10000;
         label839: {
            int var5;
            boolean var10001;
            try {
               this.preWriteCleanup();
               var5 = this.count + 1;
            } catch (Throwable var100) {
               var10000 = var100;
               var10001 = false;
               break label839;
            }

            int var6 = var5;

            try {
               if (var5 > this.threshold) {
                  this.expand();
                  var6 = this.count + 1;
               }
            } catch (Throwable var99) {
               var10000 = var99;
               var10001 = false;
               break label839;
            }

            AtomicReferenceArray var7;
            MapMakerInternalMap.InternalEntry var8;
            try {
               var7 = this.table;
               var5 = var7.length() - 1 & var2;
               var8 = (MapMakerInternalMap.InternalEntry)var7.get(var5);
            } catch (Throwable var98) {
               var10000 = var98;
               var10001 = false;
               break label839;
            }

            MapMakerInternalMap.InternalEntry var9 = var8;

            while(true) {
               if (var9 == null) {
                  try {
                     ++this.modCount;
                     MapMakerInternalMap.InternalEntry var102 = this.map.entryHelper.newEntry(this.self(), var1, var2, var8);
                     this.setValue(var102, var3);
                     var7.set(var5, var102);
                     this.count = var6;
                  } catch (Throwable var94) {
                     var10000 = var94;
                     var10001 = false;
                     break label839;
                  }

                  this.unlock();
                  return null;
               }

               label820: {
                  Object var10;
                  try {
                     var10 = var9.getKey();
                     if (var9.getHash() != var2) {
                        break label820;
                     }
                  } catch (Throwable var97) {
                     var10000 = var97;
                     var10001 = false;
                     break label839;
                  }

                  if (var10 != null) {
                     try {
                        if (this.map.keyEquivalence.equivalent(var1, var10)) {
                           var1 = var9.getValue();
                           break;
                        }
                     } catch (Throwable var96) {
                        var10000 = var96;
                        var10001 = false;
                        break label839;
                     }
                  }
               }

               try {
                  var9 = var9.getNext();
               } catch (Throwable var95) {
                  var10000 = var95;
                  var10001 = false;
                  break label839;
               }
            }

            if (var1 == null) {
               label800: {
                  try {
                     ++this.modCount;
                     this.setValue(var9, var3);
                     this.count = this.count;
                  } catch (Throwable var92) {
                     var10000 = var92;
                     var10001 = false;
                     break label800;
                  }

                  this.unlock();
                  return null;
               }
            } else {
               label841: {
                  if (var4) {
                     this.unlock();
                     return var1;
                  }

                  try {
                     ++this.modCount;
                     this.setValue(var9, var3);
                  } catch (Throwable var93) {
                     var10000 = var93;
                     var10001 = false;
                     break label841;
                  }

                  this.unlock();
                  return var1;
               }
            }
         }

         Throwable var101 = var10000;
         this.unlock();
         throw var101;
      }

      boolean reclaimKey(E var1, int var2) {
         this.lock();

         label144: {
            Throwable var10000;
            label148: {
               AtomicReferenceArray var3;
               MapMakerInternalMap.InternalEntry var4;
               boolean var10001;
               try {
                  var3 = this.table;
                  var2 &= var3.length() - 1;
                  var4 = (MapMakerInternalMap.InternalEntry)var3.get(var2);
               } catch (Throwable var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label148;
               }

               MapMakerInternalMap.InternalEntry var5 = var4;

               while(true) {
                  if (var5 == null) {
                     this.unlock();
                     return false;
                  }

                  if (var5 == var1) {
                     try {
                        ++this.modCount;
                        var1 = this.removeFromChain(var4, var5);
                        int var6 = this.count;
                        var3.set(var2, var1);
                        this.count = var6 - 1;
                        break label144;
                     } catch (Throwable var16) {
                        var10000 = var16;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var5 = var5.getNext();
                  } catch (Throwable var17) {
                     var10000 = var17;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var19 = var10000;
            this.unlock();
            throw var19;
         }

         this.unlock();
         return true;
      }

      boolean reclaimValue(K var1, int var2, MapMakerInternalMap.WeakValueReference<K, V, E> var3) {
         this.lock();

         Throwable var10000;
         label278: {
            AtomicReferenceArray var4;
            int var5;
            MapMakerInternalMap.InternalEntry var6;
            boolean var10001;
            try {
               var4 = this.table;
               var5 = var4.length() - 1 & var2;
               var6 = (MapMakerInternalMap.InternalEntry)var4.get(var5);
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break label278;
            }

            MapMakerInternalMap.InternalEntry var7 = var6;

            while(true) {
               if (var7 == null) {
                  this.unlock();
                  return false;
               }

               label280: {
                  Object var8;
                  try {
                     var8 = var7.getKey();
                     if (var7.getHash() != var2) {
                        break label280;
                     }
                  } catch (Throwable var26) {
                     var10000 = var26;
                     var10001 = false;
                     break;
                  }

                  if (var8 != null) {
                     label282: {
                        label265: {
                           try {
                              if (!this.map.keyEquivalence.equivalent(var1, var8)) {
                                 break label282;
                              }

                              if (((MapMakerInternalMap.WeakValueEntry)var7).getValueReference() == var3) {
                                 ++this.modCount;
                                 MapMakerInternalMap.InternalEntry var29 = this.removeFromChain(var6, var7);
                                 var2 = this.count;
                                 var4.set(var5, var29);
                                 this.count = var2 - 1;
                                 break label265;
                              }
                           } catch (Throwable var27) {
                              var10000 = var27;
                              var10001 = false;
                              break;
                           }

                           this.unlock();
                           return false;
                        }

                        this.unlock();
                        return true;
                     }
                  }
               }

               try {
                  var7 = var7.getNext();
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break;
               }
            }
         }

         Throwable var30 = var10000;
         this.unlock();
         throw var30;
      }

      V remove(Object var1, int var2) {
         this.lock();

         Throwable var10000;
         label437: {
            AtomicReferenceArray var3;
            int var4;
            MapMakerInternalMap.InternalEntry var5;
            boolean var10001;
            try {
               this.preWriteCleanup();
               var3 = this.table;
               var4 = var3.length() - 1 & var2;
               var5 = (MapMakerInternalMap.InternalEntry)var3.get(var4);
            } catch (Throwable var46) {
               var10000 = var46;
               var10001 = false;
               break label437;
            }

            MapMakerInternalMap.InternalEntry var6 = var5;

            while(true) {
               if (var6 == null) {
                  this.unlock();
                  return null;
               }

               label424: {
                  Object var7;
                  try {
                     var7 = var6.getKey();
                     if (var6.getHash() != var2) {
                        break label424;
                     }
                  } catch (Throwable var49) {
                     var10000 = var49;
                     var10001 = false;
                     break label437;
                  }

                  if (var7 != null) {
                     try {
                        if (this.map.keyEquivalence.equivalent(var1, var7)) {
                           var1 = var6.getValue();
                           break;
                        }
                     } catch (Throwable var48) {
                        var10000 = var48;
                        var10001 = false;
                        break label437;
                     }
                  }
               }

               try {
                  var6 = var6.getNext();
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label437;
               }
            }

            if (var1 == null) {
               label436: {
                  try {
                     if (isCollected(var6)) {
                        break label436;
                     }
                  } catch (Throwable var47) {
                     var10000 = var47;
                     var10001 = false;
                     break label437;
                  }

                  this.unlock();
                  return null;
               }
            }

            try {
               ++this.modCount;
               var6 = this.removeFromChain(var5, var6);
               var2 = this.count;
               var3.set(var4, var6);
               this.count = var2 - 1;
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label437;
            }

            this.unlock();
            return var1;
         }

         Throwable var50 = var10000;
         this.unlock();
         throw var50;
      }

      boolean remove(Object var1, int var2, Object var3) {
         this.lock();

         Throwable var10000;
         label460: {
            AtomicReferenceArray var4;
            int var5;
            MapMakerInternalMap.InternalEntry var6;
            boolean var10001;
            try {
               this.preWriteCleanup();
               var4 = this.table;
               var5 = var4.length() - 1 & var2;
               var6 = (MapMakerInternalMap.InternalEntry)var4.get(var5);
            } catch (Throwable var48) {
               var10000 = var48;
               var10001 = false;
               break label460;
            }

            MapMakerInternalMap.InternalEntry var7 = var6;

            while(true) {
               boolean var8 = false;
               if (var7 == null) {
                  this.unlock();
                  return false;
               }

               label462: {
                  label451: {
                     label463: {
                        Object var9;
                        try {
                           var9 = var7.getKey();
                           if (var7.getHash() != var2) {
                              break label463;
                           }
                        } catch (Throwable var51) {
                           var10000 = var51;
                           var10001 = false;
                           break;
                        }

                        if (var9 != null) {
                           label464: {
                              try {
                                 if (!this.map.keyEquivalence.equivalent(var1, var9)) {
                                    break label464;
                                 }

                                 var1 = var7.getValue();
                                 if (!this.map.valueEquivalence().equivalent(var3, var1)) {
                                    break label451;
                                 }
                              } catch (Throwable var50) {
                                 var10000 = var50;
                                 var10001 = false;
                                 break;
                              }

                              var8 = true;
                              break label462;
                           }
                        }
                     }

                     try {
                        var7 = var7.getNext();
                        continue;
                     } catch (Throwable var47) {
                        var10000 = var47;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     if (isCollected(var7)) {
                        break label462;
                     }
                  } catch (Throwable var49) {
                     var10000 = var49;
                     var10001 = false;
                     break;
                  }

                  this.unlock();
                  return false;
               }

               try {
                  ++this.modCount;
                  MapMakerInternalMap.InternalEntry var52 = this.removeFromChain(var6, var7);
                  var2 = this.count;
                  var4.set(var5, var52);
                  this.count = var2 - 1;
               } catch (Throwable var46) {
                  var10000 = var46;
                  var10001 = false;
                  break;
               }

               this.unlock();
               return var8;
            }
         }

         Throwable var53 = var10000;
         this.unlock();
         throw var53;
      }

      boolean removeEntryForTesting(E var1) {
         int var2 = var1.getHash();
         AtomicReferenceArray var3 = this.table;
         var2 &= var3.length() - 1;
         MapMakerInternalMap.InternalEntry var4 = (MapMakerInternalMap.InternalEntry)var3.get(var2);

         for(MapMakerInternalMap.InternalEntry var5 = var4; var5 != null; var5 = var5.getNext()) {
            if (var5 == var1) {
               ++this.modCount;
               var1 = this.removeFromChain(var4, var5);
               int var6 = this.count;
               var3.set(var2, var1);
               this.count = var6 - 1;
               return true;
            }
         }

         return false;
      }

      E removeFromChain(E var1, E var2) {
         int var3 = this.count;

         MapMakerInternalMap.InternalEntry var4;
         for(var4 = var2.getNext(); var1 != var2; var1 = var1.getNext()) {
            MapMakerInternalMap.InternalEntry var5 = this.copyEntry(var1, var4);
            if (var5 != null) {
               var4 = var5;
            } else {
               --var3;
            }
         }

         this.count = var3;
         return var4;
      }

      E removeFromChainForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, MapMakerInternalMap.InternalEntry<K, V, ?> var2) {
         return this.removeFromChain(this.castForTesting(var1), this.castForTesting(var2));
      }

      boolean removeTableEntryForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return this.removeEntryForTesting(this.castForTesting(var1));
      }

      V replace(K var1, int var2, V var3) {
         this.lock();

         Throwable var10000;
         label434: {
            AtomicReferenceArray var4;
            int var5;
            MapMakerInternalMap.InternalEntry var6;
            boolean var10001;
            try {
               this.preWriteCleanup();
               var4 = this.table;
               var5 = var4.length() - 1 & var2;
               var6 = (MapMakerInternalMap.InternalEntry)var4.get(var5);
            } catch (Throwable var50) {
               var10000 = var50;
               var10001 = false;
               break label434;
            }

            MapMakerInternalMap.InternalEntry var7 = var6;

            while(true) {
               if (var7 == null) {
                  this.unlock();
                  return null;
               }

               label422: {
                  Object var8;
                  try {
                     var8 = var7.getKey();
                     if (var7.getHash() != var2) {
                        break label422;
                     }
                  } catch (Throwable var49) {
                     var10000 = var49;
                     var10001 = false;
                     break label434;
                  }

                  if (var8 != null) {
                     try {
                        if (this.map.keyEquivalence.equivalent(var1, var8)) {
                           var1 = var7.getValue();
                           break;
                        }
                     } catch (Throwable var48) {
                        var10000 = var48;
                        var10001 = false;
                        break label434;
                     }
                  }
               }

               try {
                  var7 = var7.getNext();
               } catch (Throwable var47) {
                  var10000 = var47;
                  var10001 = false;
                  break label434;
               }
            }

            if (var1 == null) {
               label408: {
                  try {
                     if (isCollected(var7)) {
                        ++this.modCount;
                        MapMakerInternalMap.InternalEntry var51 = this.removeFromChain(var6, var7);
                        var2 = this.count;
                        var4.set(var5, var51);
                        this.count = var2 - 1;
                     }
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break label408;
                  }

                  this.unlock();
                  return null;
               }
            } else {
               label411: {
                  try {
                     ++this.modCount;
                     this.setValue(var7, var3);
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label411;
                  }

                  this.unlock();
                  return var1;
               }
            }
         }

         Throwable var52 = var10000;
         this.unlock();
         throw var52;
      }

      boolean replace(K var1, int var2, V var3, V var4) {
         this.lock();

         Throwable var10000;
         label476: {
            AtomicReferenceArray var5;
            int var6;
            MapMakerInternalMap.InternalEntry var7;
            boolean var10001;
            try {
               this.preWriteCleanup();
               var5 = this.table;
               var6 = var5.length() - 1 & var2;
               var7 = (MapMakerInternalMap.InternalEntry)var5.get(var6);
            } catch (Throwable var51) {
               var10000 = var51;
               var10001 = false;
               break label476;
            }

            MapMakerInternalMap.InternalEntry var8 = var7;

            while(true) {
               if (var8 == null) {
                  this.unlock();
                  return false;
               }

               label464: {
                  Object var9;
                  try {
                     var9 = var8.getKey();
                     if (var8.getHash() != var2) {
                        break label464;
                     }
                  } catch (Throwable var50) {
                     var10000 = var50;
                     var10001 = false;
                     break label476;
                  }

                  if (var9 != null) {
                     try {
                        if (this.map.keyEquivalence.equivalent(var1, var9)) {
                           var1 = var8.getValue();
                           break;
                        }
                     } catch (Throwable var49) {
                        var10000 = var49;
                        var10001 = false;
                        break label476;
                     }
                  }
               }

               try {
                  var8 = var8.getNext();
               } catch (Throwable var48) {
                  var10000 = var48;
                  var10001 = false;
                  break label476;
               }
            }

            if (var1 == null) {
               label446: {
                  try {
                     if (isCollected(var8)) {
                        ++this.modCount;
                        MapMakerInternalMap.InternalEntry var52 = this.removeFromChain(var7, var8);
                        var2 = this.count;
                        var5.set(var6, var52);
                        this.count = var2 - 1;
                     }
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label446;
                  }

                  this.unlock();
                  return false;
               }
            } else {
               label453: {
                  label452: {
                     try {
                        if (!this.map.valueEquivalence().equivalent(var3, var1)) {
                           break label452;
                        }

                        ++this.modCount;
                        this.setValue(var8, var4);
                     } catch (Throwable var47) {
                        var10000 = var47;
                        var10001 = false;
                        break label453;
                     }

                     this.unlock();
                     return true;
                  }

                  this.unlock();
                  return false;
               }
            }
         }

         Throwable var53 = var10000;
         this.unlock();
         throw var53;
      }

      void runCleanup() {
         this.runLockedCleanup();
      }

      void runLockedCleanup() {
         if (this.tryLock()) {
            try {
               this.maybeDrainReferenceQueues();
               this.readCount.set(0);
            } finally {
               this.unlock();
            }
         }

      }

      abstract S self();

      void setTableEntryForTesting(int var1, MapMakerInternalMap.InternalEntry<K, V, ?> var2) {
         this.table.set(var1, this.castForTesting(var2));
      }

      void setValue(E var1, V var2) {
         this.map.entryHelper.setValue(this.self(), var1, var2);
      }

      void setValueForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, V var2) {
         this.map.entryHelper.setValue(this.self(), this.castForTesting(var1), var2);
      }

      void setWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, MapMakerInternalMap.WeakValueReference<K, V, ? extends MapMakerInternalMap.InternalEntry<K, V, ?>> var2) {
         throw new AssertionError();
      }

      void tryDrainReferenceQueues() {
         if (this.tryLock()) {
            try {
               this.maybeDrainReferenceQueues();
            } finally {
               this.unlock();
            }
         }

      }
   }

   private static final class SerializationProxy<K, V> extends MapMakerInternalMap.AbstractSerializationProxy<K, V> {
      private static final long serialVersionUID = 3L;

      SerializationProxy(MapMakerInternalMap.Strength var1, MapMakerInternalMap.Strength var2, Equivalence<Object> var3, Equivalence<Object> var4, int var5, ConcurrentMap<K, V> var6) {
         super(var1, var2, var3, var4, var5, var6);
      }

      private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
         var1.defaultReadObject();
         this.delegate = this.readMapMaker(var1).makeMap();
         this.readEntries(var1);
      }

      private Object readResolve() {
         return this.delegate;
      }

      private void writeObject(ObjectOutputStream var1) throws IOException {
         var1.defaultWriteObject();
         this.writeMapTo(var1);
      }
   }

   static enum Strength {
      STRONG {
         Equivalence<Object> defaultEquivalence() {
            return Equivalence.equals();
         }
      },
      WEAK;

      static {
         MapMakerInternalMap.Strength var0 = new MapMakerInternalMap.Strength("WEAK", 1) {
            Equivalence<Object> defaultEquivalence() {
               return Equivalence.identity();
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
   }

   static final class StrongKeyDummyValueEntry<K> extends MapMakerInternalMap.AbstractStrongKeyEntry<K, MapMaker.Dummy, MapMakerInternalMap.StrongKeyDummyValueEntry<K>> implements MapMakerInternalMap.StrongValueEntry<K, MapMaker.Dummy, MapMakerInternalMap.StrongKeyDummyValueEntry<K>> {
      StrongKeyDummyValueEntry(K var1, int var2, @NullableDecl MapMakerInternalMap.StrongKeyDummyValueEntry<K> var3) {
         super(var1, var2, var3);
      }

      MapMakerInternalMap.StrongKeyDummyValueEntry<K> copy(MapMakerInternalMap.StrongKeyDummyValueEntry<K> var1) {
         return new MapMakerInternalMap.StrongKeyDummyValueEntry(this.key, this.hash, var1);
      }

      public MapMaker.Dummy getValue() {
         return MapMaker.Dummy.VALUE;
      }

      void setValue(MapMaker.Dummy var1) {
      }

      static final class Helper<K> implements MapMakerInternalMap.InternalEntryHelper<K, MapMaker.Dummy, MapMakerInternalMap.StrongKeyDummyValueEntry<K>, MapMakerInternalMap.StrongKeyDummyValueSegment<K>> {
         private static final MapMakerInternalMap.StrongKeyDummyValueEntry.Helper<?> INSTANCE = new MapMakerInternalMap.StrongKeyDummyValueEntry.Helper();

         static <K> MapMakerInternalMap.StrongKeyDummyValueEntry.Helper<K> instance() {
            return INSTANCE;
         }

         public MapMakerInternalMap.StrongKeyDummyValueEntry<K> copy(MapMakerInternalMap.StrongKeyDummyValueSegment<K> var1, MapMakerInternalMap.StrongKeyDummyValueEntry<K> var2, @NullableDecl MapMakerInternalMap.StrongKeyDummyValueEntry<K> var3) {
            return var2.copy(var3);
         }

         public MapMakerInternalMap.Strength keyStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public MapMakerInternalMap.StrongKeyDummyValueEntry<K> newEntry(MapMakerInternalMap.StrongKeyDummyValueSegment<K> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.StrongKeyDummyValueEntry<K> var4) {
            return new MapMakerInternalMap.StrongKeyDummyValueEntry(var2, var3, var4);
         }

         public MapMakerInternalMap.StrongKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, MapMakerInternalMap.StrongKeyDummyValueEntry<K>, MapMakerInternalMap.StrongKeyDummyValueSegment<K>> var1, int var2, int var3) {
            return new MapMakerInternalMap.StrongKeyDummyValueSegment(var1, var2, var3);
         }

         public void setValue(MapMakerInternalMap.StrongKeyDummyValueSegment<K> var1, MapMakerInternalMap.StrongKeyDummyValueEntry<K> var2, MapMaker.Dummy var3) {
         }

         public MapMakerInternalMap.Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }
      }
   }

   static final class StrongKeyDummyValueSegment<K> extends MapMakerInternalMap.Segment<K, MapMaker.Dummy, MapMakerInternalMap.StrongKeyDummyValueEntry<K>, MapMakerInternalMap.StrongKeyDummyValueSegment<K>> {
      StrongKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, MapMakerInternalMap.StrongKeyDummyValueEntry<K>, MapMakerInternalMap.StrongKeyDummyValueSegment<K>> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public MapMakerInternalMap.StrongKeyDummyValueEntry<K> castForTesting(MapMakerInternalMap.InternalEntry<K, MapMaker.Dummy, ?> var1) {
         return (MapMakerInternalMap.StrongKeyDummyValueEntry)var1;
      }

      MapMakerInternalMap.StrongKeyDummyValueSegment<K> self() {
         return this;
      }
   }

   static final class StrongKeyStrongValueEntry<K, V> extends MapMakerInternalMap.AbstractStrongKeyEntry<K, V, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V>> implements MapMakerInternalMap.StrongValueEntry<K, V, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V>> {
      @NullableDecl
      private volatile V value = null;

      StrongKeyStrongValueEntry(K var1, int var2, @NullableDecl MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> var3) {
         super(var1, var2, var3);
      }

      MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> copy(MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> var1) {
         var1 = new MapMakerInternalMap.StrongKeyStrongValueEntry(this.key, this.hash, var1);
         var1.value = this.value;
         return var1;
      }

      @NullableDecl
      public V getValue() {
         return this.value;
      }

      void setValue(V var1) {
         this.value = var1;
      }

      static final class Helper<K, V> implements MapMakerInternalMap.InternalEntryHelper<K, V, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V>, MapMakerInternalMap.StrongKeyStrongValueSegment<K, V>> {
         private static final MapMakerInternalMap.StrongKeyStrongValueEntry.Helper<?, ?> INSTANCE = new MapMakerInternalMap.StrongKeyStrongValueEntry.Helper();

         static <K, V> MapMakerInternalMap.StrongKeyStrongValueEntry.Helper<K, V> instance() {
            return INSTANCE;
         }

         public MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> copy(MapMakerInternalMap.StrongKeyStrongValueSegment<K, V> var1, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> var2, @NullableDecl MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> var3) {
            return var2.copy(var3);
         }

         public MapMakerInternalMap.Strength keyStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> newEntry(MapMakerInternalMap.StrongKeyStrongValueSegment<K, V> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> var4) {
            return new MapMakerInternalMap.StrongKeyStrongValueEntry(var2, var3, var4);
         }

         public MapMakerInternalMap.StrongKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V>, MapMakerInternalMap.StrongKeyStrongValueSegment<K, V>> var1, int var2, int var3) {
            return new MapMakerInternalMap.StrongKeyStrongValueSegment(var1, var2, var3);
         }

         public void setValue(MapMakerInternalMap.StrongKeyStrongValueSegment<K, V> var1, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> var2, V var3) {
            var2.setValue(var3);
         }

         public MapMakerInternalMap.Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }
      }
   }

   static final class StrongKeyStrongValueSegment<K, V> extends MapMakerInternalMap.Segment<K, V, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V>, MapMakerInternalMap.StrongKeyStrongValueSegment<K, V>> {
      StrongKeyStrongValueSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.StrongKeyStrongValueEntry<K, V>, MapMakerInternalMap.StrongKeyStrongValueSegment<K, V>> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public MapMakerInternalMap.StrongKeyStrongValueEntry<K, V> castForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return (MapMakerInternalMap.StrongKeyStrongValueEntry)var1;
      }

      MapMakerInternalMap.StrongKeyStrongValueSegment<K, V> self() {
         return this;
      }
   }

   static final class StrongKeyWeakValueEntry<K, V> extends MapMakerInternalMap.AbstractStrongKeyEntry<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>> implements MapMakerInternalMap.WeakValueEntry<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>> {
      private volatile MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>> valueReference;

      StrongKeyWeakValueEntry(K var1, int var2, @NullableDecl MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> var3) {
         super(var1, var2, var3);
         this.valueReference = MapMakerInternalMap.UNSET_WEAK_VALUE_REFERENCE;
      }

      public void clearValue() {
         this.valueReference.clear();
      }

      MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> copy(ReferenceQueue<V> var1, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> var2) {
         var2 = new MapMakerInternalMap.StrongKeyWeakValueEntry(this.key, this.hash, var2);
         var2.valueReference = this.valueReference.copyFor(var1, var2);
         return var2;
      }

      public V getValue() {
         return this.valueReference.get();
      }

      public MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>> getValueReference() {
         return this.valueReference;
      }

      void setValue(V var1, ReferenceQueue<V> var2) {
         MapMakerInternalMap.WeakValueReference var3 = this.valueReference;
         this.valueReference = new MapMakerInternalMap.WeakValueReferenceImpl(var2, var1, this);
         var3.clear();
      }

      static final class Helper<K, V> implements MapMakerInternalMap.InternalEntryHelper<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>, MapMakerInternalMap.StrongKeyWeakValueSegment<K, V>> {
         private static final MapMakerInternalMap.StrongKeyWeakValueEntry.Helper<?, ?> INSTANCE = new MapMakerInternalMap.StrongKeyWeakValueEntry.Helper();

         static <K, V> MapMakerInternalMap.StrongKeyWeakValueEntry.Helper<K, V> instance() {
            return INSTANCE;
         }

         public MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> copy(MapMakerInternalMap.StrongKeyWeakValueSegment<K, V> var1, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> var2, @NullableDecl MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> var3) {
            return MapMakerInternalMap.Segment.isCollected(var2) ? null : var2.copy(var1.queueForValues, var3);
         }

         public MapMakerInternalMap.Strength keyStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }

         public MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> newEntry(MapMakerInternalMap.StrongKeyWeakValueSegment<K, V> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> var4) {
            return new MapMakerInternalMap.StrongKeyWeakValueEntry(var2, var3, var4);
         }

         public MapMakerInternalMap.StrongKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>, MapMakerInternalMap.StrongKeyWeakValueSegment<K, V>> var1, int var2, int var3) {
            return new MapMakerInternalMap.StrongKeyWeakValueSegment(var1, var2, var3);
         }

         public void setValue(MapMakerInternalMap.StrongKeyWeakValueSegment<K, V> var1, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> var2, V var3) {
            var2.setValue(var3, var1.queueForValues);
         }

         public MapMakerInternalMap.Strength valueStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }
      }
   }

   static final class StrongKeyWeakValueSegment<K, V> extends MapMakerInternalMap.Segment<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>, MapMakerInternalMap.StrongKeyWeakValueSegment<K, V>> {
      private final ReferenceQueue<V> queueForValues = new ReferenceQueue();

      StrongKeyWeakValueSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>, MapMakerInternalMap.StrongKeyWeakValueSegment<K, V>> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public MapMakerInternalMap.StrongKeyWeakValueEntry<K, V> castForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return (MapMakerInternalMap.StrongKeyWeakValueEntry)var1;
      }

      ReferenceQueue<V> getValueReferenceQueueForTesting() {
         return this.queueForValues;
      }

      public MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return this.castForTesting(var1).getValueReference();
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForValues);
      }

      void maybeDrainReferenceQueues() {
         this.drainValueReferenceQueue(this.queueForValues);
      }

      public MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.StrongKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, V var2) {
         return new MapMakerInternalMap.WeakValueReferenceImpl(this.queueForValues, var2, this.castForTesting(var1));
      }

      MapMakerInternalMap.StrongKeyWeakValueSegment<K, V> self() {
         return this;
      }

      public void setWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, MapMakerInternalMap.WeakValueReference<K, V, ? extends MapMakerInternalMap.InternalEntry<K, V, ?>> var2) {
         MapMakerInternalMap.StrongKeyWeakValueEntry var3 = this.castForTesting(var1);
         MapMakerInternalMap.WeakValueReference var4 = var3.valueReference;
         var3.valueReference = var2;
         var4.clear();
      }
   }

   interface StrongValueEntry<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> extends MapMakerInternalMap.InternalEntry<K, V, E> {
   }

   final class ValueIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<V> {
      ValueIterator() {
         super();
      }

      public V next() {
         return this.nextEntry().getValue();
      }
   }

   final class Values extends AbstractCollection<V> {
      public void clear() {
         MapMakerInternalMap.this.clear();
      }

      public boolean contains(Object var1) {
         return MapMakerInternalMap.this.containsValue(var1);
      }

      public boolean isEmpty() {
         return MapMakerInternalMap.this.isEmpty();
      }

      public Iterator<V> iterator() {
         return MapMakerInternalMap.this.new ValueIterator();
      }

      public int size() {
         return MapMakerInternalMap.this.size();
      }

      public Object[] toArray() {
         return MapMakerInternalMap.toArrayList(this).toArray();
      }

      public <T> T[] toArray(T[] var1) {
         return MapMakerInternalMap.toArrayList(this).toArray(var1);
      }
   }

   static final class WeakKeyDummyValueEntry<K> extends MapMakerInternalMap.AbstractWeakKeyEntry<K, MapMaker.Dummy, MapMakerInternalMap.WeakKeyDummyValueEntry<K>> implements MapMakerInternalMap.StrongValueEntry<K, MapMaker.Dummy, MapMakerInternalMap.WeakKeyDummyValueEntry<K>> {
      WeakKeyDummyValueEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.WeakKeyDummyValueEntry<K> var4) {
         super(var1, var2, var3, var4);
      }

      MapMakerInternalMap.WeakKeyDummyValueEntry<K> copy(ReferenceQueue<K> var1, MapMakerInternalMap.WeakKeyDummyValueEntry<K> var2) {
         return new MapMakerInternalMap.WeakKeyDummyValueEntry(var1, this.getKey(), this.hash, var2);
      }

      public MapMaker.Dummy getValue() {
         return MapMaker.Dummy.VALUE;
      }

      void setValue(MapMaker.Dummy var1) {
      }

      static final class Helper<K> implements MapMakerInternalMap.InternalEntryHelper<K, MapMaker.Dummy, MapMakerInternalMap.WeakKeyDummyValueEntry<K>, MapMakerInternalMap.WeakKeyDummyValueSegment<K>> {
         private static final MapMakerInternalMap.WeakKeyDummyValueEntry.Helper<?> INSTANCE = new MapMakerInternalMap.WeakKeyDummyValueEntry.Helper();

         static <K> MapMakerInternalMap.WeakKeyDummyValueEntry.Helper<K> instance() {
            return INSTANCE;
         }

         public MapMakerInternalMap.WeakKeyDummyValueEntry<K> copy(MapMakerInternalMap.WeakKeyDummyValueSegment<K> var1, MapMakerInternalMap.WeakKeyDummyValueEntry<K> var2, @NullableDecl MapMakerInternalMap.WeakKeyDummyValueEntry<K> var3) {
            return var2.getKey() == null ? null : var2.copy(var1.queueForKeys, var3);
         }

         public MapMakerInternalMap.Strength keyStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public MapMakerInternalMap.WeakKeyDummyValueEntry<K> newEntry(MapMakerInternalMap.WeakKeyDummyValueSegment<K> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.WeakKeyDummyValueEntry<K> var4) {
            return new MapMakerInternalMap.WeakKeyDummyValueEntry(var1.queueForKeys, var2, var3, var4);
         }

         public MapMakerInternalMap.WeakKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, MapMakerInternalMap.WeakKeyDummyValueEntry<K>, MapMakerInternalMap.WeakKeyDummyValueSegment<K>> var1, int var2, int var3) {
            return new MapMakerInternalMap.WeakKeyDummyValueSegment(var1, var2, var3);
         }

         public void setValue(MapMakerInternalMap.WeakKeyDummyValueSegment<K> var1, MapMakerInternalMap.WeakKeyDummyValueEntry<K> var2, MapMaker.Dummy var3) {
         }

         public MapMakerInternalMap.Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }
      }
   }

   static final class WeakKeyDummyValueSegment<K> extends MapMakerInternalMap.Segment<K, MapMaker.Dummy, MapMakerInternalMap.WeakKeyDummyValueEntry<K>, MapMakerInternalMap.WeakKeyDummyValueSegment<K>> {
      private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();

      WeakKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, MapMakerInternalMap.WeakKeyDummyValueEntry<K>, MapMakerInternalMap.WeakKeyDummyValueSegment<K>> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public MapMakerInternalMap.WeakKeyDummyValueEntry<K> castForTesting(MapMakerInternalMap.InternalEntry<K, MapMaker.Dummy, ?> var1) {
         return (MapMakerInternalMap.WeakKeyDummyValueEntry)var1;
      }

      ReferenceQueue<K> getKeyReferenceQueueForTesting() {
         return this.queueForKeys;
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForKeys);
      }

      void maybeDrainReferenceQueues() {
         this.drainKeyReferenceQueue(this.queueForKeys);
      }

      MapMakerInternalMap.WeakKeyDummyValueSegment<K> self() {
         return this;
      }
   }

   static final class WeakKeyStrongValueEntry<K, V> extends MapMakerInternalMap.AbstractWeakKeyEntry<K, V, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V>> implements MapMakerInternalMap.StrongValueEntry<K, V, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V>> {
      @NullableDecl
      private volatile V value = null;

      WeakKeyStrongValueEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> var4) {
         super(var1, var2, var3, var4);
      }

      MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> copy(ReferenceQueue<K> var1, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> var2) {
         MapMakerInternalMap.WeakKeyStrongValueEntry var3 = new MapMakerInternalMap.WeakKeyStrongValueEntry(var1, this.getKey(), this.hash, var2);
         var3.setValue(this.value);
         return var3;
      }

      @NullableDecl
      public V getValue() {
         return this.value;
      }

      void setValue(V var1) {
         this.value = var1;
      }

      static final class Helper<K, V> implements MapMakerInternalMap.InternalEntryHelper<K, V, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V>, MapMakerInternalMap.WeakKeyStrongValueSegment<K, V>> {
         private static final MapMakerInternalMap.WeakKeyStrongValueEntry.Helper<?, ?> INSTANCE = new MapMakerInternalMap.WeakKeyStrongValueEntry.Helper();

         static <K, V> MapMakerInternalMap.WeakKeyStrongValueEntry.Helper<K, V> instance() {
            return INSTANCE;
         }

         public MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> copy(MapMakerInternalMap.WeakKeyStrongValueSegment<K, V> var1, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> var2, @NullableDecl MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> var3) {
            return var2.getKey() == null ? null : var2.copy(var1.queueForKeys, var3);
         }

         public MapMakerInternalMap.Strength keyStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> newEntry(MapMakerInternalMap.WeakKeyStrongValueSegment<K, V> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> var4) {
            return new MapMakerInternalMap.WeakKeyStrongValueEntry(var1.queueForKeys, var2, var3, var4);
         }

         public MapMakerInternalMap.WeakKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V>, MapMakerInternalMap.WeakKeyStrongValueSegment<K, V>> var1, int var2, int var3) {
            return new MapMakerInternalMap.WeakKeyStrongValueSegment(var1, var2, var3);
         }

         public void setValue(MapMakerInternalMap.WeakKeyStrongValueSegment<K, V> var1, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> var2, V var3) {
            var2.setValue(var3);
         }

         public MapMakerInternalMap.Strength valueStrength() {
            return MapMakerInternalMap.Strength.STRONG;
         }
      }
   }

   static final class WeakKeyStrongValueSegment<K, V> extends MapMakerInternalMap.Segment<K, V, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V>, MapMakerInternalMap.WeakKeyStrongValueSegment<K, V>> {
      private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();

      WeakKeyStrongValueSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.WeakKeyStrongValueEntry<K, V>, MapMakerInternalMap.WeakKeyStrongValueSegment<K, V>> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public MapMakerInternalMap.WeakKeyStrongValueEntry<K, V> castForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return (MapMakerInternalMap.WeakKeyStrongValueEntry)var1;
      }

      ReferenceQueue<K> getKeyReferenceQueueForTesting() {
         return this.queueForKeys;
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForKeys);
      }

      void maybeDrainReferenceQueues() {
         this.drainKeyReferenceQueue(this.queueForKeys);
      }

      MapMakerInternalMap.WeakKeyStrongValueSegment<K, V> self() {
         return this;
      }
   }

   static final class WeakKeyWeakValueEntry<K, V> extends MapMakerInternalMap.AbstractWeakKeyEntry<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>> implements MapMakerInternalMap.WeakValueEntry<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>> {
      private volatile MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>> valueReference;

      WeakKeyWeakValueEntry(ReferenceQueue<K> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> var4) {
         super(var1, var2, var3, var4);
         this.valueReference = MapMakerInternalMap.UNSET_WEAK_VALUE_REFERENCE;
      }

      public void clearValue() {
         this.valueReference.clear();
      }

      MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> copy(ReferenceQueue<K> var1, ReferenceQueue<V> var2, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> var3) {
         MapMakerInternalMap.WeakKeyWeakValueEntry var4 = new MapMakerInternalMap.WeakKeyWeakValueEntry(var1, this.getKey(), this.hash, var3);
         var4.valueReference = this.valueReference.copyFor(var2, var4);
         return var4;
      }

      public V getValue() {
         return this.valueReference.get();
      }

      public MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>> getValueReference() {
         return this.valueReference;
      }

      void setValue(V var1, ReferenceQueue<V> var2) {
         MapMakerInternalMap.WeakValueReference var3 = this.valueReference;
         this.valueReference = new MapMakerInternalMap.WeakValueReferenceImpl(var2, var1, this);
         var3.clear();
      }

      static final class Helper<K, V> implements MapMakerInternalMap.InternalEntryHelper<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>, MapMakerInternalMap.WeakKeyWeakValueSegment<K, V>> {
         private static final MapMakerInternalMap.WeakKeyWeakValueEntry.Helper<?, ?> INSTANCE = new MapMakerInternalMap.WeakKeyWeakValueEntry.Helper();

         static <K, V> MapMakerInternalMap.WeakKeyWeakValueEntry.Helper<K, V> instance() {
            return INSTANCE;
         }

         public MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> copy(MapMakerInternalMap.WeakKeyWeakValueSegment<K, V> var1, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> var2, @NullableDecl MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> var3) {
            if (var2.getKey() == null) {
               return null;
            } else {
               return MapMakerInternalMap.Segment.isCollected(var2) ? null : var2.copy(var1.queueForKeys, var1.queueForValues, var3);
            }
         }

         public MapMakerInternalMap.Strength keyStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }

         public MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> newEntry(MapMakerInternalMap.WeakKeyWeakValueSegment<K, V> var1, K var2, int var3, @NullableDecl MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> var4) {
            return new MapMakerInternalMap.WeakKeyWeakValueEntry(var1.queueForKeys, var2, var3, var4);
         }

         public MapMakerInternalMap.WeakKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>, MapMakerInternalMap.WeakKeyWeakValueSegment<K, V>> var1, int var2, int var3) {
            return new MapMakerInternalMap.WeakKeyWeakValueSegment(var1, var2, var3);
         }

         public void setValue(MapMakerInternalMap.WeakKeyWeakValueSegment<K, V> var1, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> var2, V var3) {
            var2.setValue(var3, var1.queueForValues);
         }

         public MapMakerInternalMap.Strength valueStrength() {
            return MapMakerInternalMap.Strength.WEAK;
         }
      }
   }

   static final class WeakKeyWeakValueSegment<K, V> extends MapMakerInternalMap.Segment<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>, MapMakerInternalMap.WeakKeyWeakValueSegment<K, V>> {
      private final ReferenceQueue<K> queueForKeys = new ReferenceQueue();
      private final ReferenceQueue<V> queueForValues = new ReferenceQueue();

      WeakKeyWeakValueSegment(MapMakerInternalMap<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>, MapMakerInternalMap.WeakKeyWeakValueSegment<K, V>> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public MapMakerInternalMap.WeakKeyWeakValueEntry<K, V> castForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return (MapMakerInternalMap.WeakKeyWeakValueEntry)var1;
      }

      ReferenceQueue<K> getKeyReferenceQueueForTesting() {
         return this.queueForKeys;
      }

      ReferenceQueue<V> getValueReferenceQueueForTesting() {
         return this.queueForValues;
      }

      public MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1) {
         return this.castForTesting(var1).getValueReference();
      }

      void maybeClearReferenceQueues() {
         this.clearReferenceQueue(this.queueForKeys);
      }

      void maybeDrainReferenceQueues() {
         this.drainKeyReferenceQueue(this.queueForKeys);
         this.drainValueReferenceQueue(this.queueForValues);
      }

      public MapMakerInternalMap.WeakValueReference<K, V, MapMakerInternalMap.WeakKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, V var2) {
         return new MapMakerInternalMap.WeakValueReferenceImpl(this.queueForValues, var2, this.castForTesting(var1));
      }

      MapMakerInternalMap.WeakKeyWeakValueSegment<K, V> self() {
         return this;
      }

      public void setWeakValueReferenceForTesting(MapMakerInternalMap.InternalEntry<K, V, ?> var1, MapMakerInternalMap.WeakValueReference<K, V, ? extends MapMakerInternalMap.InternalEntry<K, V, ?>> var2) {
         MapMakerInternalMap.WeakKeyWeakValueEntry var4 = this.castForTesting(var1);
         MapMakerInternalMap.WeakValueReference var3 = var4.valueReference;
         var4.valueReference = var2;
         var3.clear();
      }
   }

   interface WeakValueEntry<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> extends MapMakerInternalMap.InternalEntry<K, V, E> {
      void clearValue();

      MapMakerInternalMap.WeakValueReference<K, V, E> getValueReference();
   }

   interface WeakValueReference<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> {
      void clear();

      MapMakerInternalMap.WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> var1, E var2);

      @NullableDecl
      V get();

      E getEntry();
   }

   static final class WeakValueReferenceImpl<K, V, E extends MapMakerInternalMap.InternalEntry<K, V, E>> extends WeakReference<V> implements MapMakerInternalMap.WeakValueReference<K, V, E> {
      final E entry;

      WeakValueReferenceImpl(ReferenceQueue<V> var1, V var2, E var3) {
         super(var2, var1);
         this.entry = var3;
      }

      public MapMakerInternalMap.WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> var1, E var2) {
         return new MapMakerInternalMap.WeakValueReferenceImpl(var1, this.get(), var2);
      }

      public E getEntry() {
         return this.entry;
      }
   }

   final class WriteThroughEntry extends AbstractMapEntry<K, V> {
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
         Object var2 = MapMakerInternalMap.this.put(this.key, var1);
         this.value = var1;
         return var2;
      }
   }
}

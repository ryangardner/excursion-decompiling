package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
   private static final int ABSENT = -1;
   private static final int ENDPOINT = -2;
   private transient Set<Entry<K, V>> entrySet;
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

   private HashBiMap(int var1) {
      this.init(var1);
   }

   private int bucket(int var1) {
      return var1 & this.hashTableKToV.length - 1;
   }

   public static <K, V> HashBiMap<K, V> create() {
      return create(16);
   }

   public static <K, V> HashBiMap<K, V> create(int var0) {
      return new HashBiMap(var0);
   }

   public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> var0) {
      HashBiMap var1 = create(var0.size());
      var1.putAll(var0);
      return var1;
   }

   private static int[] createFilledWithAbsent(int var0) {
      int[] var1 = new int[var0];
      Arrays.fill(var1, -1);
      return var1;
   }

   private void deleteFromTableKToV(int var1, int var2) {
      boolean var3;
      if (var1 != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      var2 = this.bucket(var2);
      int[] var4 = this.hashTableKToV;
      if (var4[var2] == var1) {
         int[] var5 = this.nextInBucketKToV;
         var4[var2] = var5[var1];
         var5[var1] = -1;
      } else {
         int var6 = var4[var2];
         var2 = this.nextInBucketKToV[var6];

         while(true) {
            int var7 = var6;
            var6 = var2;
            if (var2 == -1) {
               StringBuilder var8 = new StringBuilder();
               var8.append("Expected to find entry with key ");
               var8.append(this.keys[var1]);
               throw new AssertionError(var8.toString());
            }

            if (var2 == var1) {
               var4 = this.nextInBucketKToV;
               var4[var7] = var4[var1];
               var4[var1] = -1;
               return;
            }

            var2 = this.nextInBucketKToV[var2];
         }
      }
   }

   private void deleteFromTableVToK(int var1, int var2) {
      boolean var3;
      if (var1 != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      var2 = this.bucket(var2);
      int[] var4 = this.hashTableVToK;
      if (var4[var2] == var1) {
         int[] var5 = this.nextInBucketVToK;
         var4[var2] = var5[var1];
         var5[var1] = -1;
      } else {
         int var6 = var4[var2];
         var2 = this.nextInBucketVToK[var6];

         while(true) {
            int var7 = var6;
            var6 = var2;
            if (var2 == -1) {
               StringBuilder var8 = new StringBuilder();
               var8.append("Expected to find entry with value ");
               var8.append(this.values[var1]);
               throw new AssertionError(var8.toString());
            }

            if (var2 == var1) {
               var4 = this.nextInBucketVToK;
               var4[var7] = var4[var1];
               var4[var1] = -1;
               return;
            }

            var2 = this.nextInBucketVToK[var2];
         }
      }
   }

   private void ensureCapacity(int var1) {
      int[] var2 = this.nextInBucketKToV;
      int var3;
      if (var2.length < var1) {
         var3 = ImmutableCollection.Builder.expandedCapacity(var2.length, var1);
         this.keys = Arrays.copyOf(this.keys, var3);
         this.values = Arrays.copyOf(this.values, var3);
         this.nextInBucketKToV = expandAndFillWithAbsent(this.nextInBucketKToV, var3);
         this.nextInBucketVToK = expandAndFillWithAbsent(this.nextInBucketVToK, var3);
         this.prevInInsertionOrder = expandAndFillWithAbsent(this.prevInInsertionOrder, var3);
         this.nextInInsertionOrder = expandAndFillWithAbsent(this.nextInInsertionOrder, var3);
      }

      if (this.hashTableKToV.length < var1) {
         var1 = Hashing.closedTableSize(var1, 1.0D);
         this.hashTableKToV = createFilledWithAbsent(var1);
         this.hashTableVToK = createFilledWithAbsent(var1);

         for(var1 = 0; var1 < this.size; var2[var3] = var1++) {
            var3 = this.bucket(Hashing.smearedHash(this.keys[var1]));
            var2 = this.nextInBucketKToV;
            int[] var4 = this.hashTableKToV;
            var2[var1] = var4[var3];
            var4[var3] = var1;
            var3 = this.bucket(Hashing.smearedHash(this.values[var1]));
            var4 = this.nextInBucketVToK;
            var2 = this.hashTableVToK;
            var4[var1] = var2[var3];
         }
      }

   }

   private static int[] expandAndFillWithAbsent(int[] var0, int var1) {
      int var2 = var0.length;
      var0 = Arrays.copyOf(var0, var1);
      Arrays.fill(var0, var2, var1, -1);
      return var0;
   }

   private void insertIntoTableKToV(int var1, int var2) {
      boolean var3;
      if (var1 != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      var2 = this.bucket(var2);
      int[] var4 = this.nextInBucketKToV;
      int[] var5 = this.hashTableKToV;
      var4[var1] = var5[var2];
      var5[var2] = var1;
   }

   private void insertIntoTableVToK(int var1, int var2) {
      boolean var3;
      if (var1 != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      var2 = this.bucket(var2);
      int[] var4 = this.nextInBucketVToK;
      int[] var5 = this.hashTableVToK;
      var4[var1] = var5[var2];
      var5[var2] = var1;
   }

   private void moveEntryToIndex(int var1, int var2) {
      if (var1 != var2) {
         int var3 = this.prevInInsertionOrder[var1];
         int var4 = this.nextInInsertionOrder[var1];
         this.setSucceeds(var3, var2);
         this.setSucceeds(var2, var4);
         Object[] var5 = this.keys;
         Object var6 = var5[var1];
         Object[] var7 = this.values;
         Object var8 = var7[var1];
         var5[var2] = var6;
         var7[var2] = var8;
         var3 = this.bucket(Hashing.smearedHash(var6));
         int[] var10 = this.hashTableKToV;
         int var9;
         if (var10[var3] == var1) {
            var10[var3] = var2;
         } else {
            var4 = var10[var3];

            for(var3 = this.nextInBucketKToV[var4]; var3 != var1; var3 = var9) {
               var9 = this.nextInBucketKToV[var3];
               var4 = var3;
            }

            this.nextInBucketKToV[var4] = var2;
         }

         var10 = this.nextInBucketKToV;
         var10[var2] = var10[var1];
         var10[var1] = -1;
         var3 = this.bucket(Hashing.smearedHash(var8));
         int[] var11 = this.hashTableVToK;
         if (var11[var3] == var1) {
            var11[var3] = var2;
         } else {
            var4 = var11[var3];

            for(var3 = this.nextInBucketVToK[var4]; var3 != var1; var3 = var9) {
               var9 = this.nextInBucketVToK[var3];
               var4 = var3;
            }

            this.nextInBucketVToK[var4] = var2;
         }

         var11 = this.nextInBucketVToK;
         var11[var2] = var11[var1];
         var11[var1] = -1;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = Serialization.readCount(var1);
      this.init(16);
      Serialization.populateMap(this, var1, var2);
   }

   private void removeEntry(int var1, int var2, int var3) {
      boolean var4;
      if (var1 != -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.deleteFromTableKToV(var1, var2);
      this.deleteFromTableVToK(var1, var3);
      this.setSucceeds(this.prevInInsertionOrder[var1], this.nextInInsertionOrder[var1]);
      this.moveEntryToIndex(this.size - 1, var1);
      Object[] var5 = this.keys;
      var1 = this.size;
      var5[var1 - 1] = null;
      this.values[var1 - 1] = null;
      this.size = var1 - 1;
      ++this.modCount;
   }

   private void replaceKeyInEntry(int var1, @NullableDecl K var2, boolean var3) {
      boolean var4;
      if (var1 != -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      int var5 = Hashing.smearedHash(var2);
      int var6 = this.findEntryByKey(var2, var5);
      int var7 = this.lastInInsertionOrder;
      int var8 = -2;
      int var9 = var1;
      if (var6 != -1) {
         if (!var3) {
            StringBuilder var12 = new StringBuilder();
            var12.append("Key already present in map: ");
            var12.append(var2);
            throw new IllegalArgumentException(var12.toString());
         }

         int var10 = this.prevInInsertionOrder[var6];
         int var11 = this.nextInInsertionOrder[var6];
         this.removeEntryKeyHashKnown(var6, var5);
         var7 = var10;
         var8 = var11;
         var9 = var1;
         if (var1 == this.size) {
            var9 = var6;
            var7 = var10;
            var8 = var11;
         }
      }

      if (var7 == var9) {
         var1 = this.prevInInsertionOrder[var9];
      } else {
         var1 = var7;
         if (var7 == this.size) {
            var1 = var6;
         }
      }

      if (var8 == var9) {
         var6 = this.nextInInsertionOrder[var9];
      } else if (var8 != this.size) {
         var6 = var8;
      }

      this.setSucceeds(this.prevInInsertionOrder[var9], this.nextInInsertionOrder[var9]);
      this.deleteFromTableKToV(var9, Hashing.smearedHash(this.keys[var9]));
      this.keys[var9] = var2;
      this.insertIntoTableKToV(var9, Hashing.smearedHash(var2));
      this.setSucceeds(var1, var9);
      this.setSucceeds(var9, var6);
   }

   private void replaceValueInEntry(int var1, @NullableDecl V var2, boolean var3) {
      boolean var4;
      if (var1 != -1) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      int var5 = Hashing.smearedHash(var2);
      int var6 = this.findEntryByValue(var2, var5);
      int var7 = var1;
      if (var6 != -1) {
         if (!var3) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Value already present in map: ");
            var8.append(var2);
            throw new IllegalArgumentException(var8.toString());
         }

         this.removeEntryValueHashKnown(var6, var5);
         var7 = var1;
         if (var1 == this.size) {
            var7 = var6;
         }
      }

      this.deleteFromTableVToK(var7, Hashing.smearedHash(this.values[var7]));
      this.values[var7] = var2;
      this.insertIntoTableVToK(var7, var5);
   }

   private void setSucceeds(int var1, int var2) {
      if (var1 == -2) {
         this.firstInInsertionOrder = var2;
      } else {
         this.nextInInsertionOrder[var1] = var2;
      }

      if (var2 == -2) {
         this.lastInInsertionOrder = var1;
      } else {
         this.prevInInsertionOrder[var2] = var1;
      }

   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMap(this, var1);
   }

   public void clear() {
      Arrays.fill(this.keys, 0, this.size, (Object)null);
      Arrays.fill(this.values, 0, this.size, (Object)null);
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

   public boolean containsKey(@NullableDecl Object var1) {
      boolean var2;
      if (this.findEntryByKey(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsValue(@NullableDecl Object var1) {
      boolean var2;
      if (this.findEntryByValue(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Set<Entry<K, V>> entrySet() {
      Set var1 = this.entrySet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new HashBiMap.EntrySet();
         this.entrySet = (Set)var2;
      }

      return (Set)var2;
   }

   int findEntry(@NullableDecl Object var1, int var2, int[] var3, int[] var4, Object[] var5) {
      for(var2 = var3[this.bucket(var2)]; var2 != -1; var2 = var4[var2]) {
         if (Objects.equal(var5[var2], var1)) {
            return var2;
         }
      }

      return -1;
   }

   int findEntryByKey(@NullableDecl Object var1) {
      return this.findEntryByKey(var1, Hashing.smearedHash(var1));
   }

   int findEntryByKey(@NullableDecl Object var1, int var2) {
      return this.findEntry(var1, var2, this.hashTableKToV, this.nextInBucketKToV, this.keys);
   }

   int findEntryByValue(@NullableDecl Object var1) {
      return this.findEntryByValue(var1, Hashing.smearedHash(var1));
   }

   int findEntryByValue(@NullableDecl Object var1, int var2) {
      return this.findEntry(var1, var2, this.hashTableVToK, this.nextInBucketVToK, this.values);
   }

   @NullableDecl
   public V forcePut(@NullableDecl K var1, @NullableDecl V var2) {
      return this.put(var1, var2, true);
   }

   @NullableDecl
   public V get(@NullableDecl Object var1) {
      int var2 = this.findEntryByKey(var1);
      if (var2 == -1) {
         var1 = null;
      } else {
         var1 = this.values[var2];
      }

      return var1;
   }

   @NullableDecl
   K getInverse(@NullableDecl Object var1) {
      int var2 = this.findEntryByValue(var1);
      if (var2 == -1) {
         var1 = null;
      } else {
         var1 = this.keys[var2];
      }

      return var1;
   }

   void init(int var1) {
      CollectPreconditions.checkNonnegative(var1, "expectedSize");
      int var2 = Hashing.closedTableSize(var1, 1.0D);
      this.size = 0;
      this.keys = (Object[])(new Object[var1]);
      this.values = (Object[])(new Object[var1]);
      this.hashTableKToV = createFilledWithAbsent(var2);
      this.hashTableVToK = createFilledWithAbsent(var2);
      this.nextInBucketKToV = createFilledWithAbsent(var1);
      this.nextInBucketVToK = createFilledWithAbsent(var1);
      this.firstInInsertionOrder = -2;
      this.lastInInsertionOrder = -2;
      this.prevInInsertionOrder = createFilledWithAbsent(var1);
      this.nextInInsertionOrder = createFilledWithAbsent(var1);
   }

   public BiMap<V, K> inverse() {
      BiMap var1 = this.inverse;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new HashBiMap.Inverse(this);
         this.inverse = (BiMap)var2;
      }

      return (BiMap)var2;
   }

   public Set<K> keySet() {
      Set var1 = this.keySet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new HashBiMap.KeySet();
         this.keySet = (Set)var2;
      }

      return (Set)var2;
   }

   public V put(@NullableDecl K var1, @NullableDecl V var2) {
      return this.put(var1, var2, false);
   }

   @NullableDecl
   V put(@NullableDecl K var1, @NullableDecl V var2, boolean var3) {
      int var4 = Hashing.smearedHash(var1);
      int var5 = this.findEntryByKey(var1, var4);
      if (var5 != -1) {
         var1 = this.values[var5];
         if (Objects.equal(var1, var2)) {
            return var2;
         } else {
            this.replaceValueInEntry(var5, var2, var3);
            return var1;
         }
      } else {
         var5 = Hashing.smearedHash(var2);
         int var6 = this.findEntryByValue(var2, var5);
         if (var3) {
            if (var6 != -1) {
               this.removeEntryValueHashKnown(var6, var5);
            }
         } else {
            if (var6 == -1) {
               var3 = true;
            } else {
               var3 = false;
            }

            Preconditions.checkArgument(var3, "Value already present: %s", var2);
         }

         this.ensureCapacity(this.size + 1);
         Object[] var7 = this.keys;
         var6 = this.size;
         var7[var6] = var1;
         this.values[var6] = var2;
         this.insertIntoTableKToV(var6, var4);
         this.insertIntoTableVToK(this.size, var5);
         this.setSucceeds(this.lastInInsertionOrder, this.size);
         this.setSucceeds(this.size, -2);
         ++this.size;
         ++this.modCount;
         return null;
      }
   }

   @NullableDecl
   K putInverse(@NullableDecl V var1, @NullableDecl K var2, boolean var3) {
      int var4 = Hashing.smearedHash(var1);
      int var5 = this.findEntryByValue(var1, var4);
      if (var5 != -1) {
         var1 = this.keys[var5];
         if (Objects.equal(var1, var2)) {
            return var2;
         } else {
            this.replaceKeyInEntry(var5, var2, var3);
            return var1;
         }
      } else {
         var5 = this.lastInInsertionOrder;
         int var6 = Hashing.smearedHash(var2);
         int var7 = this.findEntryByKey(var2, var6);
         if (var3) {
            if (var7 != -1) {
               var5 = this.prevInInsertionOrder[var7];
               this.removeEntryKeyHashKnown(var7, var6);
            }
         } else {
            if (var7 == -1) {
               var3 = true;
            } else {
               var3 = false;
            }

            Preconditions.checkArgument(var3, "Key already present: %s", var2);
         }

         this.ensureCapacity(this.size + 1);
         Object[] var8 = this.keys;
         var7 = this.size;
         var8[var7] = var2;
         this.values[var7] = var1;
         this.insertIntoTableKToV(var7, var6);
         this.insertIntoTableVToK(this.size, var4);
         if (var5 == -2) {
            var4 = this.firstInInsertionOrder;
         } else {
            var4 = this.nextInInsertionOrder[var5];
         }

         this.setSucceeds(var5, this.size);
         this.setSucceeds(this.size, var4);
         ++this.size;
         ++this.modCount;
         return null;
      }
   }

   @NullableDecl
   public V remove(@NullableDecl Object var1) {
      int var2 = Hashing.smearedHash(var1);
      int var3 = this.findEntryByKey(var1, var2);
      if (var3 == -1) {
         return null;
      } else {
         var1 = this.values[var3];
         this.removeEntryKeyHashKnown(var3, var2);
         return var1;
      }
   }

   void removeEntry(int var1) {
      this.removeEntryKeyHashKnown(var1, Hashing.smearedHash(this.keys[var1]));
   }

   void removeEntryKeyHashKnown(int var1, int var2) {
      this.removeEntry(var1, var2, Hashing.smearedHash(this.values[var1]));
   }

   void removeEntryValueHashKnown(int var1, int var2) {
      this.removeEntry(var1, Hashing.smearedHash(this.keys[var1]), var2);
   }

   @NullableDecl
   K removeInverse(@NullableDecl Object var1) {
      int var2 = Hashing.smearedHash(var1);
      int var3 = this.findEntryByValue(var1, var2);
      if (var3 == -1) {
         return null;
      } else {
         var1 = this.keys[var3];
         this.removeEntryValueHashKnown(var3, var2);
         return var1;
      }
   }

   public int size() {
      return this.size;
   }

   public Set<V> values() {
      Set var1 = this.valueSet;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new HashBiMap.ValueSet();
         this.valueSet = (Set)var2;
      }

      return (Set)var2;
   }

   final class EntryForKey extends AbstractMapEntry<K, V> {
      int index;
      @NullableDecl
      final K key;

      EntryForKey(int var2) {
         this.key = HashBiMap.this.keys[var2];
         this.index = var2;
      }

      public K getKey() {
         return this.key;
      }

      @NullableDecl
      public V getValue() {
         this.updateIndex();
         Object var1;
         if (this.index == -1) {
            var1 = null;
         } else {
            var1 = HashBiMap.this.values[this.index];
         }

         return var1;
      }

      public V setValue(V var1) {
         this.updateIndex();
         if (this.index == -1) {
            return HashBiMap.this.put(this.key, var1);
         } else {
            Object var2 = HashBiMap.this.values[this.index];
            if (Objects.equal(var2, var1)) {
               return var1;
            } else {
               HashBiMap.this.replaceValueInEntry(this.index, var1, false);
               return var2;
            }
         }
      }

      void updateIndex() {
         int var1 = this.index;
         if (var1 == -1 || var1 > HashBiMap.this.size || !Objects.equal(HashBiMap.this.keys[this.index], this.key)) {
            this.index = HashBiMap.this.findEntryByKey(this.key);
         }

      }
   }

   static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {
      final HashBiMap<K, V> biMap;
      int index;
      final V value;

      EntryForValue(HashBiMap<K, V> var1, int var2) {
         this.biMap = var1;
         this.value = var1.values[var2];
         this.index = var2;
      }

      private void updateIndex() {
         int var1 = this.index;
         if (var1 == -1 || var1 > this.biMap.size || !Objects.equal(this.value, this.biMap.values[this.index])) {
            this.index = this.biMap.findEntryByValue(this.value);
         }

      }

      public V getKey() {
         return this.value;
      }

      public K getValue() {
         this.updateIndex();
         Object var1;
         if (this.index == -1) {
            var1 = null;
         } else {
            var1 = this.biMap.keys[this.index];
         }

         return var1;
      }

      public K setValue(K var1) {
         this.updateIndex();
         if (this.index == -1) {
            return this.biMap.putInverse(this.value, var1, false);
         } else {
            Object var2 = this.biMap.keys[this.index];
            if (Objects.equal(var2, var1)) {
               return var1;
            } else {
               this.biMap.replaceKeyInEntry(this.index, var1, false);
               return var2;
            }
         }
      }
   }

   final class EntrySet extends HashBiMap.View<K, V, Entry<K, V>> {
      EntrySet() {
         super(HashBiMap.this);
      }

      public boolean contains(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Entry var5 = (Entry)var1;
            var1 = var5.getKey();
            Object var7 = var5.getValue();
            int var6 = HashBiMap.this.findEntryByKey(var1);
            var4 = var3;
            if (var6 != -1) {
               var4 = var3;
               if (Objects.equal(var7, HashBiMap.this.values[var6])) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      Entry<K, V> forEntry(int var1) {
         return HashBiMap.this.new EntryForKey(var1);
      }

      public boolean remove(@NullableDecl Object var1) {
         if (var1 instanceof Entry) {
            Entry var2 = (Entry)var1;
            var1 = var2.getKey();
            Object var5 = var2.getValue();
            int var3 = Hashing.smearedHash(var1);
            int var4 = HashBiMap.this.findEntryByKey(var1, var3);
            if (var4 != -1 && Objects.equal(var5, HashBiMap.this.values[var4])) {
               HashBiMap.this.removeEntryKeyHashKnown(var4, var3);
               return true;
            }
         }

         return false;
      }
   }

   static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
      private final HashBiMap<K, V> forward;
      private transient Set<Entry<V, K>> inverseEntrySet;

      Inverse(HashBiMap<K, V> var1) {
         this.forward = var1;
      }

      private void readObject(ObjectInputStream var1) throws ClassNotFoundException, IOException {
         var1.defaultReadObject();
         this.forward.inverse = this;
      }

      public void clear() {
         this.forward.clear();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         return this.forward.containsValue(var1);
      }

      public boolean containsValue(@NullableDecl Object var1) {
         return this.forward.containsKey(var1);
      }

      public Set<Entry<V, K>> entrySet() {
         Set var1 = this.inverseEntrySet;
         Object var2 = var1;
         if (var1 == null) {
            var2 = new HashBiMap.InverseEntrySet(this.forward);
            this.inverseEntrySet = (Set)var2;
         }

         return (Set)var2;
      }

      @NullableDecl
      public K forcePut(@NullableDecl V var1, @NullableDecl K var2) {
         return this.forward.putInverse(var1, var2, true);
      }

      @NullableDecl
      public K get(@NullableDecl Object var1) {
         return this.forward.getInverse(var1);
      }

      public BiMap<K, V> inverse() {
         return this.forward;
      }

      public Set<V> keySet() {
         return this.forward.values();
      }

      @NullableDecl
      public K put(@NullableDecl V var1, @NullableDecl K var2) {
         return this.forward.putInverse(var1, var2, false);
      }

      @NullableDecl
      public K remove(@NullableDecl Object var1) {
         return this.forward.removeInverse(var1);
      }

      public int size() {
         return this.forward.size;
      }

      public Set<K> values() {
         return this.forward.keySet();
      }
   }

   static class InverseEntrySet<K, V> extends HashBiMap.View<K, V, Entry<V, K>> {
      InverseEntrySet(HashBiMap<K, V> var1) {
         super(var1);
      }

      public boolean contains(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Entry var5 = (Entry)var1;
            var1 = var5.getKey();
            Object var7 = var5.getValue();
            int var6 = this.biMap.findEntryByValue(var1);
            var4 = var3;
            if (var6 != -1) {
               var4 = var3;
               if (Objects.equal(this.biMap.keys[var6], var7)) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      Entry<V, K> forEntry(int var1) {
         return new HashBiMap.EntryForValue(this.biMap, var1);
      }

      public boolean remove(Object var1) {
         if (var1 instanceof Entry) {
            Entry var2 = (Entry)var1;
            var1 = var2.getKey();
            Object var5 = var2.getValue();
            int var3 = Hashing.smearedHash(var1);
            int var4 = this.biMap.findEntryByValue(var1, var3);
            if (var4 != -1 && Objects.equal(this.biMap.keys[var4], var5)) {
               this.biMap.removeEntryValueHashKnown(var4, var3);
               return true;
            }
         }

         return false;
      }
   }

   final class KeySet extends HashBiMap.View<K, V, K> {
      KeySet() {
         super(HashBiMap.this);
      }

      public boolean contains(@NullableDecl Object var1) {
         return HashBiMap.this.containsKey(var1);
      }

      K forEntry(int var1) {
         return HashBiMap.this.keys[var1];
      }

      public boolean remove(@NullableDecl Object var1) {
         int var2 = Hashing.smearedHash(var1);
         int var3 = HashBiMap.this.findEntryByKey(var1, var2);
         if (var3 != -1) {
            HashBiMap.this.removeEntryKeyHashKnown(var3, var2);
            return true;
         } else {
            return false;
         }
      }
   }

   final class ValueSet extends HashBiMap.View<K, V, V> {
      ValueSet() {
         super(HashBiMap.this);
      }

      public boolean contains(@NullableDecl Object var1) {
         return HashBiMap.this.containsValue(var1);
      }

      V forEntry(int var1) {
         return HashBiMap.this.values[var1];
      }

      public boolean remove(@NullableDecl Object var1) {
         int var2 = Hashing.smearedHash(var1);
         int var3 = HashBiMap.this.findEntryByValue(var1, var2);
         if (var3 != -1) {
            HashBiMap.this.removeEntryValueHashKnown(var3, var2);
            return true;
         } else {
            return false;
         }
      }
   }

   abstract static class View<K, V, T> extends AbstractSet<T> {
      final HashBiMap<K, V> biMap;

      View(HashBiMap<K, V> var1) {
         this.biMap = var1;
      }

      public void clear() {
         this.biMap.clear();
      }

      abstract T forEntry(int var1);

      public Iterator<T> iterator() {
         return new Iterator<T>() {
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
               if (View.this.biMap.modCount != this.expectedModCount) {
                  throw new ConcurrentModificationException();
               }
            }

            public boolean hasNext() {
               this.checkForComodification();
               boolean var1;
               if (this.index != -2 && this.remaining > 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               return var1;
            }

            public T next() {
               if (this.hasNext()) {
                  Object var1 = View.this.forEntry(this.index);
                  this.indexToRemove = this.index;
                  this.index = View.this.biMap.nextInInsertionOrder[this.index];
                  --this.remaining;
                  return var1;
               } else {
                  throw new NoSuchElementException();
               }
            }

            public void remove() {
               this.checkForComodification();
               boolean var1;
               if (this.indexToRemove != -1) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               CollectPreconditions.checkRemove(var1);
               View.this.biMap.removeEntry(this.indexToRemove);
               if (this.index == View.this.biMap.size) {
                  this.index = this.indexToRemove;
               }

               this.indexToRemove = -1;
               this.expectedModCount = View.this.biMap.modCount;
            }
         };
      }

      public int size() {
         return this.biMap.size;
      }
   }
}

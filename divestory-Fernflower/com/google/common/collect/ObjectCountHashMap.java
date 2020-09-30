package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ObjectCountHashMap<K> {
   static final float DEFAULT_LOAD_FACTOR = 1.0F;
   static final int DEFAULT_SIZE = 3;
   private static final long HASH_MASK = -4294967296L;
   private static final int MAXIMUM_CAPACITY = 1073741824;
   private static final long NEXT_MASK = 4294967295L;
   static final int UNSET = -1;
   transient long[] entries;
   transient Object[] keys;
   private transient float loadFactor;
   transient int modCount;
   transient int size;
   private transient int[] table;
   private transient int threshold;
   transient int[] values;

   ObjectCountHashMap() {
      this.init(3, 1.0F);
   }

   ObjectCountHashMap(int var1) {
      this(var1, 1.0F);
   }

   ObjectCountHashMap(int var1, float var2) {
      this.init(var1, var2);
   }

   ObjectCountHashMap(ObjectCountHashMap<? extends K> var1) {
      this.init(var1.size(), 1.0F);

      for(int var2 = var1.firstIndex(); var2 != -1; var2 = var1.nextIndex(var2)) {
         this.put(var1.getKey(var2), var1.getValue(var2));
      }

   }

   public static <K> ObjectCountHashMap<K> create() {
      return new ObjectCountHashMap();
   }

   public static <K> ObjectCountHashMap<K> createWithExpectedSize(int var0) {
      return new ObjectCountHashMap(var0);
   }

   private static int getHash(long var0) {
      return (int)(var0 >>> 32);
   }

   private static int getNext(long var0) {
      return (int)var0;
   }

   private int hashTableMask() {
      return this.table.length - 1;
   }

   private static long[] newEntries(int var0) {
      long[] var1 = new long[var0];
      Arrays.fill(var1, -1L);
      return var1;
   }

   private static int[] newTable(int var0) {
      int[] var1 = new int[var0];
      Arrays.fill(var1, -1);
      return var1;
   }

   private int remove(@NullableDecl Object var1, int var2) {
      int var3 = this.hashTableMask() & var2;
      int var4 = this.table[var3];
      if (var4 == -1) {
         return 0;
      } else {
         int var5;
         int var6;
         for(var5 = -1; getHash(this.entries[var4]) != var2 || !Objects.equal(var1, this.keys[var4]); var4 = var6) {
            var6 = getNext(this.entries[var4]);
            if (var6 == -1) {
               return 0;
            }

            var5 = var4;
         }

         var2 = this.values[var4];
         if (var5 == -1) {
            this.table[var3] = getNext(this.entries[var4]);
         } else {
            long[] var7 = this.entries;
            var7[var5] = swapNext(var7[var5], getNext(var7[var4]));
         }

         this.moveLastEntry(var4);
         --this.size;
         ++this.modCount;
         return var2;
      }
   }

   private void resizeMeMaybe(int var1) {
      int var2 = this.entries.length;
      if (var1 > var2) {
         int var3 = Math.max(1, var2 >>> 1) + var2;
         var1 = var3;
         if (var3 < 0) {
            var1 = Integer.MAX_VALUE;
         }

         if (var1 != var2) {
            this.resizeEntries(var1);
         }
      }

   }

   private void resizeTable(int var1) {
      if (this.table.length >= 1073741824) {
         this.threshold = Integer.MAX_VALUE;
      } else {
         int var2 = (int)((float)var1 * this.loadFactor);
         int[] var3 = newTable(var1);
         long[] var4 = this.entries;
         int var5 = var3.length;

         for(var1 = 0; var1 < this.size; ++var1) {
            int var6 = getHash(var4[var1]);
            int var7 = var6 & var5 - 1;
            int var8 = var3[var7];
            var3[var7] = var1;
            var4[var1] = (long)var6 << 32 | (long)var8 & 4294967295L;
         }

         this.threshold = var2 + 1;
         this.table = var3;
      }
   }

   private static long swapNext(long var0, int var2) {
      return var0 & -4294967296L | (long)var2 & 4294967295L;
   }

   public void clear() {
      ++this.modCount;
      Arrays.fill(this.keys, 0, this.size, (Object)null);
      Arrays.fill(this.values, 0, this.size, 0);
      Arrays.fill(this.table, -1);
      Arrays.fill(this.entries, -1L);
      this.size = 0;
   }

   public boolean containsKey(@NullableDecl Object var1) {
      boolean var2;
      if (this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   void ensureCapacity(int var1) {
      if (var1 > this.entries.length) {
         this.resizeEntries(var1);
      }

      if (var1 >= this.threshold) {
         this.resizeTable(Math.max(2, Integer.highestOneBit(var1 - 1) << 1));
      }

   }

   int firstIndex() {
      byte var1;
      if (this.size == 0) {
         var1 = -1;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public int get(@NullableDecl Object var1) {
      int var2 = this.indexOf(var1);
      if (var2 == -1) {
         var2 = 0;
      } else {
         var2 = this.values[var2];
      }

      return var2;
   }

   Multiset.Entry<K> getEntry(int var1) {
      Preconditions.checkElementIndex(var1, this.size);
      return new ObjectCountHashMap.MapEntry(var1);
   }

   K getKey(int var1) {
      Preconditions.checkElementIndex(var1, this.size);
      return this.keys[var1];
   }

   int getValue(int var1) {
      Preconditions.checkElementIndex(var1, this.size);
      return this.values[var1];
   }

   int indexOf(@NullableDecl Object var1) {
      int var2 = Hashing.smearedHash(var1);

      long var4;
      for(int var3 = this.table[this.hashTableMask() & var2]; var3 != -1; var3 = getNext(var4)) {
         var4 = this.entries[var3];
         if (getHash(var4) == var2 && Objects.equal(var1, this.keys[var3])) {
            return var3;
         }
      }

      return -1;
   }

   void init(int var1, float var2) {
      boolean var3 = false;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Initial capacity must be non-negative");
      var4 = var3;
      if (var2 > 0.0F) {
         var4 = true;
      }

      Preconditions.checkArgument(var4, "Illegal load factor");
      int var5 = Hashing.closedTableSize(var1, (double)var2);
      this.table = newTable(var5);
      this.loadFactor = var2;
      this.keys = new Object[var1];
      this.values = new int[var1];
      this.entries = newEntries(var1);
      this.threshold = Math.max(1, (int)((float)var5 * var2));
   }

   void insertEntry(int var1, @NullableDecl K var2, int var3, int var4) {
      this.entries[var1] = (long)var4 << 32 | 4294967295L;
      this.keys[var1] = var2;
      this.values[var1] = var3;
   }

   void moveLastEntry(int var1) {
      int var2 = this.size() - 1;
      if (var1 < var2) {
         Object[] var3 = this.keys;
         var3[var1] = var3[var2];
         int[] var4 = this.values;
         var4[var1] = var4[var2];
         var3[var2] = null;
         var4[var2] = 0;
         long[] var10 = this.entries;
         long var5 = var10[var2];
         var10[var1] = var5;
         var10[var2] = -1L;
         int var7 = getHash(var5) & this.hashTableMask();
         int[] var11 = this.table;
         int var8 = var11[var7];
         int var9 = var8;
         if (var8 == var2) {
            var11[var7] = var1;
         } else {
            while(true) {
               var5 = this.entries[var9];
               var8 = getNext(var5);
               if (var8 == var2) {
                  this.entries[var9] = swapNext(var5, var1);
                  break;
               }

               var9 = var8;
            }
         }
      } else {
         this.keys[var1] = null;
         this.values[var1] = 0;
         this.entries[var1] = -1L;
      }

   }

   int nextIndex(int var1) {
      ++var1;
      if (var1 >= this.size) {
         var1 = -1;
      }

      return var1;
   }

   int nextIndexAfterRemove(int var1, int var2) {
      return var1 - 1;
   }

   public int put(@NullableDecl K var1, int var2) {
      CollectPreconditions.checkPositive(var2, "count");
      long[] var3 = this.entries;
      Object[] var4 = this.keys;
      int[] var5 = this.values;
      int var6 = Hashing.smearedHash(var1);
      int var7 = this.hashTableMask() & var6;
      int var8 = this.size;
      int[] var9 = this.table;
      int var10 = var9[var7];
      int var11 = var10;
      if (var10 == -1) {
         var9[var7] = var8;
      } else {
         while(true) {
            long var12 = var3[var11];
            if (getHash(var12) == var6 && Objects.equal(var1, var4[var11])) {
               var10 = var5[var11];
               var5[var11] = var2;
               return var10;
            }

            var10 = getNext(var12);
            if (var10 == -1) {
               var3[var11] = swapNext(var12, var8);
               break;
            }

            var11 = var10;
         }
      }

      if (var8 != Integer.MAX_VALUE) {
         var11 = var8 + 1;
         this.resizeMeMaybe(var11);
         this.insertEntry(var8, var1, var2, var6);
         this.size = var11;
         if (var8 >= this.threshold) {
            this.resizeTable(this.table.length * 2);
         }

         ++this.modCount;
         return 0;
      } else {
         throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
      }
   }

   public int remove(@NullableDecl Object var1) {
      return this.remove(var1, Hashing.smearedHash(var1));
   }

   int removeEntry(int var1) {
      return this.remove(this.keys[var1], getHash(this.entries[var1]));
   }

   void resizeEntries(int var1) {
      this.keys = Arrays.copyOf(this.keys, var1);
      this.values = Arrays.copyOf(this.values, var1);
      long[] var2 = this.entries;
      int var3 = var2.length;
      var2 = Arrays.copyOf(var2, var1);
      if (var1 > var3) {
         Arrays.fill(var2, var3, var1, -1L);
      }

      this.entries = var2;
   }

   void setValue(int var1, int var2) {
      Preconditions.checkElementIndex(var1, this.size);
      this.values[var1] = var2;
   }

   int size() {
      return this.size;
   }

   class MapEntry extends Multisets.AbstractEntry<K> {
      @NullableDecl
      final K key;
      int lastKnownIndex;

      MapEntry(int var2) {
         this.key = ObjectCountHashMap.this.keys[var2];
         this.lastKnownIndex = var2;
      }

      public int getCount() {
         this.updateLastKnownIndex();
         int var1;
         if (this.lastKnownIndex == -1) {
            var1 = 0;
         } else {
            var1 = ObjectCountHashMap.this.values[this.lastKnownIndex];
         }

         return var1;
      }

      public K getElement() {
         return this.key;
      }

      public int setCount(int var1) {
         this.updateLastKnownIndex();
         if (this.lastKnownIndex == -1) {
            ObjectCountHashMap.this.put(this.key, var1);
            return 0;
         } else {
            int var2 = ObjectCountHashMap.this.values[this.lastKnownIndex];
            ObjectCountHashMap.this.values[this.lastKnownIndex] = var1;
            return var2;
         }
      }

      void updateLastKnownIndex() {
         int var1 = this.lastKnownIndex;
         if (var1 == -1 || var1 >= ObjectCountHashMap.this.size() || !Objects.equal(this.key, ObjectCountHashMap.this.keys[this.lastKnownIndex])) {
            this.lastKnownIndex = ObjectCountHashMap.this.indexOf(this.key);
         }

      }
   }
}

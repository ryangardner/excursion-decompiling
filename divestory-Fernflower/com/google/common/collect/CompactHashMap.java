package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
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
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
   static final double HASH_FLOODING_FPP = 0.001D;
   private static final int MAX_HASH_BUCKET_LENGTH = 9;
   private static final Object NOT_FOUND = new Object();
   @NullableDecl
   transient int[] entries;
   @MonotonicNonNullDecl
   private transient Set<Entry<K, V>> entrySetView;
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

   CompactHashMap(int var1) {
      this.init(var1);
   }

   // $FF: synthetic method
   static int access$710(CompactHashMap var0) {
      int var1 = var0.size--;
      return var1;
   }

   public static <K, V> CompactHashMap<K, V> create() {
      return new CompactHashMap();
   }

   public static <K, V> CompactHashMap<K, V> createWithExpectedSize(int var0) {
      return new CompactHashMap(var0);
   }

   private int hashTableMask() {
      return (1 << (this.metadata & 31)) - 1;
   }

   private int indexOf(@NullableDecl Object var1) {
      if (this.needsAllocArrays()) {
         return -1;
      } else {
         int var2 = Hashing.smearedHash(var1);
         int var3 = this.hashTableMask();
         int var4 = CompactHashing.tableGet(this.table, var2 & var3);
         if (var4 == 0) {
            return -1;
         } else {
            int var5 = CompactHashing.getHashPrefix(var2, var3);

            do {
               --var4;
               var2 = this.entries[var4];
               if (CompactHashing.getHashPrefix(var2, var3) == var5 && Objects.equal(var1, this.keys[var4])) {
                  return var4;
               }

               var2 = CompactHashing.getNext(var2, var3);
               var4 = var2;
            } while(var2 != 0);

            return -1;
         }
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = var1.readInt();
      if (var2 < 0) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Invalid size: ");
         var4.append(var2);
         throw new InvalidObjectException(var4.toString());
      } else {
         this.init(var2);

         for(int var3 = 0; var3 < var2; ++var3) {
            this.put(var1.readObject(), var1.readObject());
         }

      }
   }

   @NullableDecl
   private Object removeHelper(@NullableDecl Object var1) {
      if (this.needsAllocArrays()) {
         return NOT_FOUND;
      } else {
         int var2 = this.hashTableMask();
         int var3 = CompactHashing.remove(var1, (Object)null, var2, this.table, this.entries, this.keys, (Object[])null);
         if (var3 == -1) {
            return NOT_FOUND;
         } else {
            var1 = this.values[var3];
            this.moveLastEntry(var3, var2);
            --this.size;
            this.incrementModCount();
            return var1;
         }
      }
   }

   private void resizeMeMaybe(int var1) {
      int var2 = this.entries.length;
      if (var1 > var2) {
         var1 = Math.min(1073741823, Math.max(1, var2 >>> 1) + var2 | 1);
         if (var1 != var2) {
            this.resizeEntries(var1);
         }
      }

   }

   private int resizeTable(int var1, int var2, int var3, int var4) {
      Object var5 = CompactHashing.createTable(var2);
      int var6 = var2 - 1;
      if (var4 != 0) {
         CompactHashing.tableSet(var5, var3 & var6, var4 + 1);
      }

      Object var7 = this.table;
      int[] var8 = this.entries;

      int var10;
      for(var2 = 0; var2 <= var1; ++var2) {
         for(var3 = CompactHashing.tableGet(var7, var2); var3 != 0; var3 = CompactHashing.getNext(var10, var1)) {
            int var9 = var3 - 1;
            var10 = var8[var9];
            var4 = CompactHashing.getHashPrefix(var10, var1) | var2;
            int var11 = var4 & var6;
            int var12 = CompactHashing.tableGet(var5, var11);
            CompactHashing.tableSet(var5, var11, var3);
            var8[var9] = CompactHashing.maskCombine(var4, var12, var6);
         }
      }

      this.table = var5;
      this.setHashTableMask(var6);
      return var6;
   }

   private void setHashTableMask(int var1) {
      var1 = Integer.numberOfLeadingZeros(var1);
      this.metadata = CompactHashing.maskCombine(this.metadata, 32 - var1, 31);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeInt(this.size());
      Iterator var2 = this.entrySetIterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1.writeObject(var3.getKey());
         var1.writeObject(var3.getValue());
      }

   }

   void accessEntry(int var1) {
   }

   int adjustAfterRemove(int var1, int var2) {
      return var1 - 1;
   }

   int allocArrays() {
      Preconditions.checkState(this.needsAllocArrays(), "Arrays already allocated");
      int var1 = this.metadata;
      int var2 = CompactHashing.tableSize(var1);
      this.table = CompactHashing.createTable(var2);
      this.setHashTableMask(var2 - 1);
      this.entries = new int[var1];
      this.keys = new Object[var1];
      this.values = new Object[var1];
      return var1;
   }

   public void clear() {
      if (!this.needsAllocArrays()) {
         this.incrementModCount();
         if (this.delegateOrNull() != null) {
            this.metadata = Ints.constrainToRange(this.size(), 3, 1073741823);
            this.table = null;
            this.size = 0;
         } else {
            Arrays.fill(this.keys, 0, this.size, (Object)null);
            Arrays.fill(this.values, 0, this.size, (Object)null);
            CompactHashing.tableClear(this.table);
            Arrays.fill(this.entries, 0, this.size, 0);
            this.size = 0;
         }

      }
   }

   public boolean containsKey(@NullableDecl Object var1) {
      Map var2 = this.delegateOrNull();
      boolean var3;
      if (var2 != null) {
         var3 = var2.containsKey(var1);
      } else if (this.indexOf(var1) != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsValue(@NullableDecl Object var1) {
      Map var2 = this.delegateOrNull();
      if (var2 != null) {
         return var2.containsValue(var1);
      } else {
         for(int var3 = 0; var3 < this.size; ++var3) {
            if (Objects.equal(var1, this.values[var3])) {
               return true;
            }
         }

         return false;
      }
   }

   Map<K, V> convertToHashFloodingResistantImplementation() {
      Map var1 = this.createHashFloodingResistantDelegate(this.hashTableMask() + 1);

      for(int var2 = this.firstEntryIndex(); var2 >= 0; var2 = this.getSuccessor(var2)) {
         var1.put(this.keys[var2], this.values[var2]);
      }

      this.table = var1;
      this.entries = null;
      this.keys = null;
      this.values = null;
      this.incrementModCount();
      return var1;
   }

   Set<Entry<K, V>> createEntrySet() {
      return new CompactHashMap.EntrySetView();
   }

   Map<K, V> createHashFloodingResistantDelegate(int var1) {
      return new LinkedHashMap(var1, 1.0F);
   }

   Set<K> createKeySet() {
      return new CompactHashMap.KeySetView();
   }

   Collection<V> createValues() {
      return new CompactHashMap.ValuesView();
   }

   @NullableDecl
   Map<K, V> delegateOrNull() {
      Object var1 = this.table;
      return var1 instanceof Map ? (Map)var1 : null;
   }

   public Set<Entry<K, V>> entrySet() {
      Set var1 = this.entrySetView;
      Set var2 = var1;
      if (var1 == null) {
         var2 = this.createEntrySet();
         this.entrySetView = var2;
      }

      return var2;
   }

   Iterator<Entry<K, V>> entrySetIterator() {
      Map var1 = this.delegateOrNull();
      return (Iterator)(var1 != null ? var1.entrySet().iterator() : new CompactHashMap<K, V>.Itr<Entry<K, V>>() {
         Entry<K, V> getOutput(int var1) {
            return CompactHashMap.this.new MapEntry(var1);
         }
      });
   }

   int firstEntryIndex() {
      byte var1;
      if (this.isEmpty()) {
         var1 = -1;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public V get(@NullableDecl Object var1) {
      Map var2 = this.delegateOrNull();
      if (var2 != null) {
         return var2.get(var1);
      } else {
         int var3 = this.indexOf(var1);
         if (var3 == -1) {
            return null;
         } else {
            this.accessEntry(var3);
            return this.values[var3];
         }
      }
   }

   int getSuccessor(int var1) {
      ++var1;
      if (var1 >= this.size) {
         var1 = -1;
      }

      return var1;
   }

   void incrementModCount() {
      this.metadata += 32;
   }

   void init(int var1) {
      boolean var2;
      if (var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Expected size must be >= 0");
      this.metadata = Ints.constrainToRange(var1, 1, 1073741823);
   }

   void insertEntry(int var1, @NullableDecl K var2, @NullableDecl V var3, int var4, int var5) {
      this.entries[var1] = CompactHashing.maskCombine(var4, 0, var5);
      this.keys[var1] = var2;
      this.values[var1] = var3;
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Set<K> keySet() {
      Set var1 = this.keySetView;
      Set var2 = var1;
      if (var1 == null) {
         var2 = this.createKeySet();
         this.keySetView = var2;
      }

      return var2;
   }

   Iterator<K> keySetIterator() {
      Map var1 = this.delegateOrNull();
      return (Iterator)(var1 != null ? var1.keySet().iterator() : new CompactHashMap<K, V>.Itr<K>() {
         K getOutput(int var1) {
            return CompactHashMap.this.keys[var1];
         }
      });
   }

   void moveLastEntry(int var1, int var2) {
      int var3 = this.size() - 1;
      if (var1 < var3) {
         Object[] var4 = this.keys;
         Object var5 = var4[var3];
         var4[var1] = var5;
         Object[] var6 = this.values;
         var6[var1] = var6[var3];
         var4[var3] = null;
         var6[var3] = null;
         int[] var10 = this.entries;
         var10[var1] = var10[var3];
         var10[var3] = 0;
         int var7 = Hashing.smearedHash(var5) & var2;
         int var8 = CompactHashing.tableGet(this.table, var7);
         int var9 = var3 + 1;
         var3 = var8;
         if (var8 == var9) {
            CompactHashing.tableSet(this.table, var7, var1 + 1);
         } else {
            do {
               var7 = var3 - 1;
               var8 = this.entries[var7];
               var3 = CompactHashing.getNext(var8, var2);
            } while(var3 != var9);

            this.entries[var7] = CompactHashing.maskCombine(var8, var1 + 1, var2);
         }
      } else {
         this.keys[var1] = null;
         this.values[var1] = null;
         this.entries[var1] = 0;
      }

   }

   boolean needsAllocArrays() {
      boolean var1;
      if (this.table == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @NullableDecl
   public V put(@NullableDecl K var1, @NullableDecl V var2) {
      if (this.needsAllocArrays()) {
         this.allocArrays();
      }

      Map var3 = this.delegateOrNull();
      if (var3 != null) {
         return var3.put(var1, var2);
      } else {
         int var6;
         int var7;
         int var8;
         int var10;
         label42: {
            int[] var4 = this.entries;
            Object[] var5 = this.keys;
            Object[] var15 = this.values;
            var6 = this.size;
            var7 = var6 + 1;
            var8 = Hashing.smearedHash(var1);
            int var9 = this.hashTableMask();
            var10 = var8 & var9;
            int var11 = CompactHashing.tableGet(this.table, var10);
            if (var11 == 0) {
               if (var7 > var9) {
                  var10 = this.resizeTable(var9, CompactHashing.newCapacity(var9), var8, var6);
                  break label42;
               }

               CompactHashing.tableSet(this.table, var10, var7);
            } else {
               int var12 = CompactHashing.getHashPrefix(var8, var9);
               var10 = 0;

               int var13;
               int var14;
               do {
                  var13 = var11 - 1;
                  var14 = var4[var13];
                  if (CompactHashing.getHashPrefix(var14, var9) == var12 && Objects.equal(var1, var5[var13])) {
                     var1 = var15[var13];
                     var15[var13] = var2;
                     this.accessEntry(var13);
                     return var1;
                  }

                  var11 = CompactHashing.getNext(var14, var9);
                  ++var10;
               } while(var11 != 0);

               if (var10 >= 9) {
                  return this.convertToHashFloodingResistantImplementation().put(var1, var2);
               }

               if (var7 > var9) {
                  var10 = this.resizeTable(var9, CompactHashing.newCapacity(var9), var8, var6);
                  break label42;
               }

               var4[var13] = CompactHashing.maskCombine(var14, var7, var9);
            }

            var10 = var9;
         }

         this.resizeMeMaybe(var7);
         this.insertEntry(var6, var1, var2, var8, var10);
         this.size = var7;
         this.incrementModCount();
         return null;
      }
   }

   @NullableDecl
   public V remove(@NullableDecl Object var1) {
      Map var2 = this.delegateOrNull();
      if (var2 != null) {
         return var2.remove(var1);
      } else {
         Object var3 = this.removeHelper(var1);
         var1 = var3;
         if (var3 == NOT_FOUND) {
            var1 = null;
         }

         return var1;
      }
   }

   void resizeEntries(int var1) {
      this.entries = Arrays.copyOf(this.entries, var1);
      this.keys = Arrays.copyOf(this.keys, var1);
      this.values = Arrays.copyOf(this.values, var1);
   }

   public int size() {
      Map var1 = this.delegateOrNull();
      int var2;
      if (var1 != null) {
         var2 = var1.size();
      } else {
         var2 = this.size;
      }

      return var2;
   }

   public void trimToSize() {
      if (!this.needsAllocArrays()) {
         Map var1 = this.delegateOrNull();
         if (var1 != null) {
            Map var2 = this.createHashFloodingResistantDelegate(this.size());
            var2.putAll(var1);
            this.table = var2;
         } else {
            int var3 = this.size;
            if (var3 < this.entries.length) {
               this.resizeEntries(var3);
            }

            int var4 = CompactHashing.tableSize(var3);
            var3 = this.hashTableMask();
            if (var4 < var3) {
               this.resizeTable(var3, var4, 0, 0);
            }

         }
      }
   }

   public Collection<V> values() {
      Collection var1 = this.valuesView;
      Collection var2 = var1;
      if (var1 == null) {
         var2 = this.createValues();
         this.valuesView = var2;
      }

      return var2;
   }

   Iterator<V> valuesIterator() {
      Map var1 = this.delegateOrNull();
      return (Iterator)(var1 != null ? var1.values().iterator() : new CompactHashMap<K, V>.Itr<V>() {
         V getOutput(int var1) {
            return CompactHashMap.this.values[var1];
         }
      });
   }

   class EntrySetView extends AbstractSet<Entry<K, V>> {
      public void clear() {
         CompactHashMap.this.clear();
      }

      public boolean contains(@NullableDecl Object var1) {
         Map var2 = CompactHashMap.this.delegateOrNull();
         if (var2 != null) {
            return var2.entrySet().contains(var1);
         } else {
            boolean var3 = var1 instanceof Entry;
            boolean var4 = false;
            boolean var5 = var4;
            if (var3) {
               Entry var7 = (Entry)var1;
               int var6 = CompactHashMap.this.indexOf(var7.getKey());
               var5 = var4;
               if (var6 != -1) {
                  var5 = var4;
                  if (Objects.equal(CompactHashMap.this.values[var6], var7.getValue())) {
                     var5 = true;
                  }
               }
            }

            return var5;
         }
      }

      public Iterator<Entry<K, V>> iterator() {
         return CompactHashMap.this.entrySetIterator();
      }

      public boolean remove(@NullableDecl Object var1) {
         Map var2 = CompactHashMap.this.delegateOrNull();
         if (var2 != null) {
            return var2.entrySet().remove(var1);
         } else if (var1 instanceof Entry) {
            Entry var5 = (Entry)var1;
            if (CompactHashMap.this.needsAllocArrays()) {
               return false;
            } else {
               int var3 = CompactHashMap.this.hashTableMask();
               int var4 = CompactHashing.remove(var5.getKey(), var5.getValue(), var3, CompactHashMap.this.table, CompactHashMap.this.entries, CompactHashMap.this.keys, CompactHashMap.this.values);
               if (var4 == -1) {
                  return false;
               } else {
                  CompactHashMap.this.moveLastEntry(var4, var3);
                  CompactHashMap.access$710(CompactHashMap.this);
                  CompactHashMap.this.incrementModCount();
                  return true;
               }
            }
         } else {
            return false;
         }
      }

      public int size() {
         return CompactHashMap.this.size();
      }
   }

   private abstract class Itr<T> implements Iterator<T> {
      int currentIndex;
      int expectedMetadata;
      int indexToRemove;

      private Itr() {
         this.expectedMetadata = CompactHashMap.this.metadata;
         this.currentIndex = CompactHashMap.this.firstEntryIndex();
         this.indexToRemove = -1;
      }

      // $FF: synthetic method
      Itr(Object var2) {
         this();
      }

      private void checkForConcurrentModification() {
         if (CompactHashMap.this.metadata != this.expectedMetadata) {
            throw new ConcurrentModificationException();
         }
      }

      abstract T getOutput(int var1);

      public boolean hasNext() {
         boolean var1;
         if (this.currentIndex >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      void incrementExpectedModCount() {
         this.expectedMetadata += 32;
      }

      public T next() {
         this.checkForConcurrentModification();
         if (this.hasNext()) {
            int var1 = this.currentIndex;
            this.indexToRemove = var1;
            Object var2 = this.getOutput(var1);
            this.currentIndex = CompactHashMap.this.getSuccessor(this.currentIndex);
            return var2;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         this.checkForConcurrentModification();
         boolean var1;
         if (this.indexToRemove >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         CollectPreconditions.checkRemove(var1);
         this.incrementExpectedModCount();
         CompactHashMap var2 = CompactHashMap.this;
         var2.remove(var2.keys[this.indexToRemove]);
         this.currentIndex = CompactHashMap.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
         this.indexToRemove = -1;
      }
   }

   class KeySetView extends AbstractSet<K> {
      public void clear() {
         CompactHashMap.this.clear();
      }

      public boolean contains(Object var1) {
         return CompactHashMap.this.containsKey(var1);
      }

      public Iterator<K> iterator() {
         return CompactHashMap.this.keySetIterator();
      }

      public boolean remove(@NullableDecl Object var1) {
         Map var2 = CompactHashMap.this.delegateOrNull();
         boolean var3;
         if (var2 != null) {
            var3 = var2.keySet().remove(var1);
         } else if (CompactHashMap.this.removeHelper(var1) != CompactHashMap.NOT_FOUND) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public int size() {
         return CompactHashMap.this.size();
      }
   }

   final class MapEntry extends AbstractMapEntry<K, V> {
      @NullableDecl
      private final K key;
      private int lastKnownIndex;

      MapEntry(int var2) {
         this.key = CompactHashMap.this.keys[var2];
         this.lastKnownIndex = var2;
      }

      private void updateLastKnownIndex() {
         int var1 = this.lastKnownIndex;
         if (var1 == -1 || var1 >= CompactHashMap.this.size() || !Objects.equal(this.key, CompactHashMap.this.keys[this.lastKnownIndex])) {
            this.lastKnownIndex = CompactHashMap.this.indexOf(this.key);
         }

      }

      @NullableDecl
      public K getKey() {
         return this.key;
      }

      @NullableDecl
      public V getValue() {
         Map var1 = CompactHashMap.this.delegateOrNull();
         if (var1 != null) {
            return var1.get(this.key);
         } else {
            this.updateLastKnownIndex();
            Object var2;
            if (this.lastKnownIndex == -1) {
               var2 = null;
            } else {
               var2 = CompactHashMap.this.values[this.lastKnownIndex];
            }

            return var2;
         }
      }

      public V setValue(V var1) {
         Map var2 = CompactHashMap.this.delegateOrNull();
         if (var2 != null) {
            return var2.put(this.key, var1);
         } else {
            this.updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
               CompactHashMap.this.put(this.key, var1);
               return null;
            } else {
               Object var3 = CompactHashMap.this.values[this.lastKnownIndex];
               CompactHashMap.this.values[this.lastKnownIndex] = var1;
               return var3;
            }
         }
      }
   }

   class ValuesView extends AbstractCollection<V> {
      public void clear() {
         CompactHashMap.this.clear();
      }

      public Iterator<V> iterator() {
         return CompactHashMap.this.valuesIterator();
      }

      public int size() {
         return CompactHashMap.this.size();
      }
   }
}

package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactHashSet<E> extends AbstractSet<E> implements Serializable {
   @MonotonicNonNullDecl
   transient Object[] elements;
   @MonotonicNonNullDecl
   private transient int[] entries;
   private transient int metadata;
   private transient int size;
   @MonotonicNonNullDecl
   private transient Object table;

   CompactHashSet() {
      this.init(3);
   }

   CompactHashSet(int var1) {
      this.init(var1);
   }

   public static <E> CompactHashSet<E> create() {
      return new CompactHashSet();
   }

   public static <E> CompactHashSet<E> create(Collection<? extends E> var0) {
      CompactHashSet var1 = createWithExpectedSize(var0.size());
      var1.addAll(var0);
      return var1;
   }

   @SafeVarargs
   public static <E> CompactHashSet<E> create(E... var0) {
      CompactHashSet var1 = createWithExpectedSize(var0.length);
      Collections.addAll(var1, var0);
      return var1;
   }

   public static <E> CompactHashSet<E> createWithExpectedSize(int var0) {
      return new CompactHashSet(var0);
   }

   private int hashTableMask() {
      return (1 << (this.metadata & 31)) - 1;
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
            this.add(var1.readObject());
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
      var1.writeInt(this.size);

      for(int var2 = this.firstEntryIndex(); var2 >= 0; var2 = this.getSuccessor(var2)) {
         var1.writeObject(this.elements[var2]);
      }

   }

   public boolean add(@NullableDecl E var1) {
      if (this.needsAllocArrays()) {
         this.allocArrays();
      }

      int[] var2 = this.entries;
      Object[] var3 = this.elements;
      int var4 = this.size;
      int var5 = var4 + 1;
      int var6 = Hashing.smearedHash(var1);
      int var7 = this.hashTableMask();
      int var8 = var6 & var7;
      int var9 = CompactHashing.tableGet(this.table, var8);
      if (var9 == 0) {
         if (var5 > var7) {
            var9 = this.resizeTable(var7, CompactHashing.newCapacity(var7), var6, var4);
         } else {
            CompactHashing.tableSet(this.table, var8, var5);
            var9 = var7;
         }
      } else {
         var8 = CompactHashing.getHashPrefix(var6, var7);

         int var10;
         int var11;
         do {
            var10 = var9 - 1;
            var11 = var2[var10];
            if (CompactHashing.getHashPrefix(var11, var7) == var8 && Objects.equal(var1, var3[var10])) {
               return false;
            }

            var9 = CompactHashing.getNext(var11, var7);
         } while(var9 != 0);

         if (var5 > var7) {
            var9 = this.resizeTable(var7, CompactHashing.newCapacity(var7), var6, var4);
         } else {
            var2[var10] = CompactHashing.maskCombine(var11, var5, var7);
            var9 = var7;
         }
      }

      this.resizeMeMaybe(var5);
      this.insertEntry(var4, var1, var6, var9);
      this.size = var5;
      this.incrementModCount();
      return true;
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
      this.elements = new Object[var1];
      return var1;
   }

   public void clear() {
      if (!this.needsAllocArrays()) {
         this.incrementModCount();
         Arrays.fill(this.elements, 0, this.size, (Object)null);
         CompactHashing.tableClear(this.table);
         Arrays.fill(this.entries, 0, this.size, 0);
         this.size = 0;
      }
   }

   public boolean contains(@NullableDecl Object var1) {
      if (this.needsAllocArrays()) {
         return false;
      } else {
         int var2 = Hashing.smearedHash(var1);
         int var3 = this.hashTableMask();
         int var4 = CompactHashing.tableGet(this.table, var2 & var3);
         if (var4 == 0) {
            return false;
         } else {
            int var5 = CompactHashing.getHashPrefix(var2, var3);

            do {
               var2 = var4 - 1;
               var4 = this.entries[var2];
               if (CompactHashing.getHashPrefix(var4, var3) == var5 && Objects.equal(var1, this.elements[var2])) {
                  return true;
               }

               var2 = CompactHashing.getNext(var4, var3);
               var4 = var2;
            } while(var2 != 0);

            return false;
         }
      }
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
      this.metadata = Math.max(1, Math.min(1073741823, var1));
   }

   void insertEntry(int var1, @NullableDecl E var2, int var3, int var4) {
      this.entries[var1] = CompactHashing.maskCombine(var3, 0, var4);
      this.elements[var1] = var2;
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.size == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Iterator<E> iterator() {
      return new Iterator<E>() {
         int currentIndex;
         int expectedMetadata;
         int indexToRemove;

         {
            this.expectedMetadata = CompactHashSet.this.metadata;
            this.currentIndex = CompactHashSet.this.firstEntryIndex();
            this.indexToRemove = -1;
         }

         private void checkForConcurrentModification() {
            if (CompactHashSet.this.metadata != this.expectedMetadata) {
               throw new ConcurrentModificationException();
            }
         }

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

         public E next() {
            this.checkForConcurrentModification();
            if (this.hasNext()) {
               this.indexToRemove = this.currentIndex;
               Object[] var1 = CompactHashSet.this.elements;
               int var2 = this.currentIndex;
               Object var3 = var1[var2];
               this.currentIndex = CompactHashSet.this.getSuccessor(var2);
               return var3;
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
            CompactHashSet var2 = CompactHashSet.this;
            var2.remove(var2.elements[this.indexToRemove]);
            this.currentIndex = CompactHashSet.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
            this.indexToRemove = -1;
         }
      };
   }

   void moveLastEntry(int var1, int var2) {
      int var3 = this.size() - 1;
      if (var1 < var3) {
         Object[] var4 = this.elements;
         Object var5 = var4[var3];
         var4[var1] = var5;
         var4[var3] = null;
         int[] var9 = this.entries;
         var9[var1] = var9[var3];
         var9[var3] = 0;
         int var6 = Hashing.smearedHash(var5) & var2;
         int var7 = CompactHashing.tableGet(this.table, var6);
         int var8 = var3 + 1;
         var3 = var7;
         if (var7 == var8) {
            CompactHashing.tableSet(this.table, var6, var1 + 1);
         } else {
            do {
               var6 = var3 - 1;
               var7 = this.entries[var6];
               var3 = CompactHashing.getNext(var7, var2);
            } while(var3 != var8);

            this.entries[var6] = CompactHashing.maskCombine(var7, var1 + 1, var2);
         }
      } else {
         this.elements[var1] = null;
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

   public boolean remove(@NullableDecl Object var1) {
      if (this.needsAllocArrays()) {
         return false;
      } else {
         int var2 = this.hashTableMask();
         int var3 = CompactHashing.remove(var1, (Object)null, var2, this.table, this.entries, this.elements, (Object[])null);
         if (var3 == -1) {
            return false;
         } else {
            this.moveLastEntry(var3, var2);
            --this.size;
            this.incrementModCount();
            return true;
         }
      }
   }

   void resizeEntries(int var1) {
      this.entries = Arrays.copyOf(this.entries, var1);
      this.elements = Arrays.copyOf(this.elements, var1);
   }

   public int size() {
      return this.size;
   }

   public Object[] toArray() {
      return this.needsAllocArrays() ? new Object[0] : Arrays.copyOf(this.elements, this.size);
   }

   public <T> T[] toArray(T[] var1) {
      if (this.needsAllocArrays()) {
         if (var1.length > 0) {
            var1[0] = null;
         }

         return var1;
      } else {
         return ObjectArrays.toArrayImpl(this.elements, 0, this.size, var1);
      }
   }

   public void trimToSize() {
      if (!this.needsAllocArrays()) {
         int var1 = this.size;
         if (var1 < this.entries.length) {
            this.resizeEntries(var1);
         }

         int var2 = CompactHashing.tableSize(var1);
         var1 = this.hashTableMask();
         if (var2 < var1) {
            this.resizeTable(var1, var2, 0, 0);
         }

      }
   }
}

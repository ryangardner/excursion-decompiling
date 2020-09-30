package com.google.common.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactLinkedHashSet<E> extends CompactHashSet<E> {
   private static final int ENDPOINT = -2;
   private transient int firstEntry;
   private transient int lastEntry;
   @MonotonicNonNullDecl
   private transient int[] predecessor;
   @MonotonicNonNullDecl
   private transient int[] successor;

   CompactLinkedHashSet() {
   }

   CompactLinkedHashSet(int var1) {
      super(var1);
   }

   public static <E> CompactLinkedHashSet<E> create() {
      return new CompactLinkedHashSet();
   }

   public static <E> CompactLinkedHashSet<E> create(Collection<? extends E> var0) {
      CompactLinkedHashSet var1 = createWithExpectedSize(var0.size());
      var1.addAll(var0);
      return var1;
   }

   @SafeVarargs
   public static <E> CompactLinkedHashSet<E> create(E... var0) {
      CompactLinkedHashSet var1 = createWithExpectedSize(var0.length);
      Collections.addAll(var1, var0);
      return var1;
   }

   public static <E> CompactLinkedHashSet<E> createWithExpectedSize(int var0) {
      return new CompactLinkedHashSet(var0);
   }

   private int getPredecessor(int var1) {
      return this.predecessor[var1] - 1;
   }

   private void setPredecessor(int var1, int var2) {
      this.predecessor[var1] = var2 + 1;
   }

   private void setSucceeds(int var1, int var2) {
      if (var1 == -2) {
         this.firstEntry = var2;
      } else {
         this.setSuccessor(var1, var2);
      }

      if (var2 == -2) {
         this.lastEntry = var1;
      } else {
         this.setPredecessor(var2, var1);
      }

   }

   private void setSuccessor(int var1, int var2) {
      this.successor[var1] = var2 + 1;
   }

   int adjustAfterRemove(int var1, int var2) {
      int var3 = var1;
      if (var1 >= this.size()) {
         var3 = var2;
      }

      return var3;
   }

   int allocArrays() {
      int var1 = super.allocArrays();
      this.predecessor = new int[var1];
      this.successor = new int[var1];
      return var1;
   }

   public void clear() {
      if (!this.needsAllocArrays()) {
         this.firstEntry = -2;
         this.lastEntry = -2;
         Arrays.fill(this.predecessor, 0, this.size(), 0);
         Arrays.fill(this.successor, 0, this.size(), 0);
         super.clear();
      }
   }

   int firstEntryIndex() {
      return this.firstEntry;
   }

   int getSuccessor(int var1) {
      return this.successor[var1] - 1;
   }

   void init(int var1) {
      super.init(var1);
      this.firstEntry = -2;
      this.lastEntry = -2;
   }

   void insertEntry(int var1, @NullableDecl E var2, int var3, int var4) {
      super.insertEntry(var1, var2, var3, var4);
      this.setSucceeds(this.lastEntry, var1);
      this.setSucceeds(var1, -2);
   }

   void moveLastEntry(int var1, int var2) {
      int var3 = this.size() - 1;
      super.moveLastEntry(var1, var2);
      this.setSucceeds(this.getPredecessor(var1), this.getSuccessor(var1));
      if (var1 < var3) {
         this.setSucceeds(this.getPredecessor(var3), var1);
         this.setSucceeds(var1, this.getSuccessor(var3));
      }

      this.predecessor[var3] = 0;
      this.successor[var3] = 0;
   }

   void resizeEntries(int var1) {
      super.resizeEntries(var1);
      this.predecessor = Arrays.copyOf(this.predecessor, var1);
      this.successor = Arrays.copyOf(this.successor, var1);
   }

   public Object[] toArray() {
      return ObjectArrays.toArrayImpl(this);
   }

   public <T> T[] toArray(T[] var1) {
      return ObjectArrays.toArrayImpl(this, var1);
   }
}

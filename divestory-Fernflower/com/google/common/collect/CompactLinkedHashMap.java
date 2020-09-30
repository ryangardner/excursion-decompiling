package com.google.common.collect;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactLinkedHashMap<K, V> extends CompactHashMap<K, V> {
   private static final int ENDPOINT = -2;
   private final boolean accessOrder;
   private transient int firstEntry;
   private transient int lastEntry;
   @NullableDecl
   transient long[] links;

   CompactLinkedHashMap() {
      this(3);
   }

   CompactLinkedHashMap(int var1) {
      this(var1, false);
   }

   CompactLinkedHashMap(int var1, boolean var2) {
      super(var1);
      this.accessOrder = var2;
   }

   public static <K, V> CompactLinkedHashMap<K, V> create() {
      return new CompactLinkedHashMap();
   }

   public static <K, V> CompactLinkedHashMap<K, V> createWithExpectedSize(int var0) {
      return new CompactLinkedHashMap(var0);
   }

   private int getPredecessor(int var1) {
      return (int)(this.links[var1] >>> 32) - 1;
   }

   private void setPredecessor(int var1, int var2) {
      long[] var3 = this.links;
      var3[var1] = var3[var1] & 4294967295L | (long)(var2 + 1) << 32;
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
      long[] var3 = this.links;
      var3[var1] = var3[var1] & -4294967296L | (long)(var2 + 1) & 4294967295L;
   }

   void accessEntry(int var1) {
      if (this.accessOrder) {
         this.setSucceeds(this.getPredecessor(var1), this.getSuccessor(var1));
         this.setSucceeds(this.lastEntry, var1);
         this.setSucceeds(var1, -2);
         this.incrementModCount();
      }

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
      this.links = new long[var1];
      return var1;
   }

   public void clear() {
      if (!this.needsAllocArrays()) {
         this.firstEntry = -2;
         this.lastEntry = -2;
         long[] var1 = this.links;
         if (var1 != null) {
            Arrays.fill(var1, 0, this.size(), 0L);
         }

         super.clear();
      }
   }

   Map<K, V> convertToHashFloodingResistantImplementation() {
      Map var1 = super.convertToHashFloodingResistantImplementation();
      this.links = null;
      return var1;
   }

   Map<K, V> createHashFloodingResistantDelegate(int var1) {
      return new LinkedHashMap(var1, 1.0F, this.accessOrder);
   }

   int firstEntryIndex() {
      return this.firstEntry;
   }

   int getSuccessor(int var1) {
      return (int)this.links[var1] - 1;
   }

   void init(int var1) {
      super.init(var1);
      this.firstEntry = -2;
      this.lastEntry = -2;
   }

   void insertEntry(int var1, @NullableDecl K var2, @NullableDecl V var3, int var4, int var5) {
      super.insertEntry(var1, var2, var3, var4, var5);
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

      this.links[var3] = 0L;
   }

   void resizeEntries(int var1) {
      super.resizeEntries(var1);
      this.links = Arrays.copyOf(this.links, var1);
   }
}

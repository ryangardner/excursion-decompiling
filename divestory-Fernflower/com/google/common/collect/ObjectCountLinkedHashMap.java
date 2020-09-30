package com.google.common.collect;

import java.util.Arrays;

class ObjectCountLinkedHashMap<K> extends ObjectCountHashMap<K> {
   private static final int ENDPOINT = -2;
   private transient int firstEntry;
   private transient int lastEntry;
   transient long[] links;

   ObjectCountLinkedHashMap() {
      this(3);
   }

   ObjectCountLinkedHashMap(int var1) {
      this(var1, 1.0F);
   }

   ObjectCountLinkedHashMap(int var1, float var2) {
      super(var1, var2);
   }

   ObjectCountLinkedHashMap(ObjectCountHashMap<K> var1) {
      this.init(var1.size(), 1.0F);

      for(int var2 = var1.firstIndex(); var2 != -1; var2 = var1.nextIndex(var2)) {
         this.put(var1.getKey(var2), var1.getValue(var2));
      }

   }

   public static <K> ObjectCountLinkedHashMap<K> create() {
      return new ObjectCountLinkedHashMap();
   }

   public static <K> ObjectCountLinkedHashMap<K> createWithExpectedSize(int var0) {
      return new ObjectCountLinkedHashMap(var0);
   }

   private int getPredecessor(int var1) {
      return (int)(this.links[var1] >>> 32);
   }

   private int getSuccessor(int var1) {
      return (int)this.links[var1];
   }

   private void setPredecessor(int var1, int var2) {
      long[] var3 = this.links;
      var3[var1] = var3[var1] & 4294967295L | (long)var2 << 32;
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
      var3[var1] = var3[var1] & -4294967296L | (long)var2 & 4294967295L;
   }

   public void clear() {
      super.clear();
      this.firstEntry = -2;
      this.lastEntry = -2;
   }

   int firstIndex() {
      int var1 = this.firstEntry;
      int var2 = var1;
      if (var1 == -2) {
         var2 = -1;
      }

      return var2;
   }

   void init(int var1, float var2) {
      super.init(var1, var2);
      this.firstEntry = -2;
      this.lastEntry = -2;
      long[] var3 = new long[var1];
      this.links = var3;
      Arrays.fill(var3, -1L);
   }

   void insertEntry(int var1, K var2, int var3, int var4) {
      super.insertEntry(var1, var2, var3, var4);
      this.setSucceeds(this.lastEntry, var1);
      this.setSucceeds(var1, -2);
   }

   void moveLastEntry(int var1) {
      int var2 = this.size() - 1;
      this.setSucceeds(this.getPredecessor(var1), this.getSuccessor(var1));
      if (var1 < var2) {
         this.setSucceeds(this.getPredecessor(var2), var1);
         this.setSucceeds(var1, this.getSuccessor(var2));
      }

      super.moveLastEntry(var1);
   }

   int nextIndex(int var1) {
      int var2 = this.getSuccessor(var1);
      var1 = var2;
      if (var2 == -2) {
         var1 = -1;
      }

      return var1;
   }

   int nextIndexAfterRemove(int var1, int var2) {
      int var3 = var1;
      if (var1 == this.size()) {
         var3 = var2;
      }

      return var3;
   }

   void resizeEntries(int var1) {
      super.resizeEntries(var1);
      long[] var2 = this.links;
      int var3 = var2.length;
      var2 = Arrays.copyOf(var2, var1);
      this.links = var2;
      Arrays.fill(var2, var3, var1, -1L);
   }
}

package io.grpc;

import java.util.Arrays;

final class PersistentHashArrayMappedTrie<K, V> {
   private final PersistentHashArrayMappedTrie.Node<K, V> root;

   PersistentHashArrayMappedTrie() {
      this((PersistentHashArrayMappedTrie.Node)null);
   }

   private PersistentHashArrayMappedTrie(PersistentHashArrayMappedTrie.Node<K, V> var1) {
      this.root = var1;
   }

   public V get(K var1) {
      PersistentHashArrayMappedTrie.Node var2 = this.root;
      return var2 == null ? null : var2.get(var1, var1.hashCode(), 0);
   }

   public PersistentHashArrayMappedTrie<K, V> put(K var1, V var2) {
      return this.root == null ? new PersistentHashArrayMappedTrie(new PersistentHashArrayMappedTrie.Leaf(var1, var2)) : new PersistentHashArrayMappedTrie(this.root.put(var1, var2, var1.hashCode(), 0));
   }

   public int size() {
      PersistentHashArrayMappedTrie.Node var1 = this.root;
      return var1 == null ? 0 : var1.size();
   }

   static final class CollisionLeaf<K, V> implements PersistentHashArrayMappedTrie.Node<K, V> {
      // $FF: synthetic field
      static final boolean $assertionsDisabled = false;
      private final K[] keys;
      private final V[] values;

      CollisionLeaf(K var1, V var2, K var3, V var4) {
         this(new Object[]{var1, var3}, new Object[]{var2, var4});
      }

      private CollisionLeaf(K[] var1, V[] var2) {
         this.keys = var1;
         this.values = var2;
      }

      private int indexOfKey(K var1) {
         int var2 = 0;

         while(true) {
            Object[] var3 = this.keys;
            if (var2 >= var3.length) {
               return -1;
            }

            if (var3[var2] == var1) {
               return var2;
            }

            ++var2;
         }
      }

      public V get(K var1, int var2, int var3) {
         var2 = 0;

         while(true) {
            Object[] var4 = this.keys;
            if (var2 >= var4.length) {
               return null;
            }

            if (var4[var2] == var1) {
               return this.values[var2];
            }

            ++var2;
         }
      }

      public PersistentHashArrayMappedTrie.Node<K, V> put(K var1, V var2, int var3, int var4) {
         int var5 = this.keys[0].hashCode();
         if (var5 != var3) {
            return PersistentHashArrayMappedTrie.CompressedIndex.combine(new PersistentHashArrayMappedTrie.Leaf(var1, var2), var3, this, var5, var4);
         } else {
            var3 = this.indexOfKey(var1);
            Object[] var6;
            Object[] var7;
            if (var3 != -1) {
               var6 = this.keys;
               var6 = Arrays.copyOf(var6, var6.length);
               var7 = Arrays.copyOf(this.values, this.keys.length);
               var6[var3] = var1;
               var7[var3] = var2;
               return new PersistentHashArrayMappedTrie.CollisionLeaf(var6, var7);
            } else {
               var6 = this.keys;
               var6 = Arrays.copyOf(var6, var6.length + 1);
               var7 = Arrays.copyOf(this.values, this.keys.length + 1);
               Object[] var8 = this.keys;
               var6[var8.length] = var1;
               var7[var8.length] = var2;
               return new PersistentHashArrayMappedTrie.CollisionLeaf(var6, var7);
            }
         }
      }

      public int size() {
         return this.values.length;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CollisionLeaf(");

         for(int var2 = 0; var2 < this.values.length; ++var2) {
            var1.append("(key=");
            var1.append(this.keys[var2]);
            var1.append(" value=");
            var1.append(this.values[var2]);
            var1.append(") ");
         }

         var1.append(")");
         return var1.toString();
      }
   }

   static final class CompressedIndex<K, V> implements PersistentHashArrayMappedTrie.Node<K, V> {
      // $FF: synthetic field
      static final boolean $assertionsDisabled = false;
      private static final int BITS = 5;
      private static final int BITS_MASK = 31;
      final int bitmap;
      private final int size;
      final PersistentHashArrayMappedTrie.Node<K, V>[] values;

      private CompressedIndex(int var1, PersistentHashArrayMappedTrie.Node<K, V>[] var2, int var3) {
         this.bitmap = var1;
         this.values = var2;
         this.size = var3;
      }

      static <K, V> PersistentHashArrayMappedTrie.Node<K, V> combine(PersistentHashArrayMappedTrie.Node<K, V> var0, int var1, PersistentHashArrayMappedTrie.Node<K, V> var2, int var3, int var4) {
         int var5 = indexBit(var1, var4);
         int var6 = indexBit(var3, var4);
         if (var5 == var6) {
            var0 = combine(var0, var1, var2, var3, var4 + 5);
            var1 = var0.size();
            return new PersistentHashArrayMappedTrie.CompressedIndex(var5, new PersistentHashArrayMappedTrie.Node[]{var0}, var1);
         } else {
            PersistentHashArrayMappedTrie.Node var7 = var0;
            PersistentHashArrayMappedTrie.Node var8 = var2;
            if (uncompressedIndex(var1, var4) > uncompressedIndex(var3, var4)) {
               var8 = var0;
               var7 = var2;
            }

            var3 = var7.size();
            var1 = var8.size();
            return new PersistentHashArrayMappedTrie.CompressedIndex(var5 | var6, new PersistentHashArrayMappedTrie.Node[]{var7, var8}, var3 + var1);
         }
      }

      private int compressedIndex(int var1) {
         return Integer.bitCount(var1 - 1 & this.bitmap);
      }

      private static int indexBit(int var0, int var1) {
         return 1 << uncompressedIndex(var0, var1);
      }

      private static int uncompressedIndex(int var0, int var1) {
         return var0 >>> var1 & 31;
      }

      public V get(K var1, int var2, int var3) {
         int var4 = indexBit(var2, var3);
         if ((this.bitmap & var4) == 0) {
            return null;
         } else {
            var4 = this.compressedIndex(var4);
            return this.values[var4].get(var1, var2, var3 + 5);
         }
      }

      public PersistentHashArrayMappedTrie.Node<K, V> put(K var1, V var2, int var3, int var4) {
         int var5 = indexBit(var3, var4);
         int var6 = this.compressedIndex(var5);
         int var7 = this.bitmap;
         PersistentHashArrayMappedTrie.Node[] var9;
         if ((var7 & var5) == 0) {
            PersistentHashArrayMappedTrie.Node[] var8 = this.values;
            var9 = new PersistentHashArrayMappedTrie.Node[var8.length + 1];
            System.arraycopy(var8, 0, var9, 0, var6);
            var9[var6] = new PersistentHashArrayMappedTrie.Leaf(var1, var2);
            PersistentHashArrayMappedTrie.Node[] var10 = this.values;
            System.arraycopy(var10, var6, var9, var6 + 1, var10.length - var6);
            return new PersistentHashArrayMappedTrie.CompressedIndex(var7 | var5, var9, this.size() + 1);
         } else {
            var9 = this.values;
            var9 = (PersistentHashArrayMappedTrie.Node[])Arrays.copyOf(var9, var9.length);
            var9[var6] = this.values[var6].put(var1, var2, var3, var4 + 5);
            var3 = this.size();
            var4 = var9[var6].size();
            var6 = this.values[var6].size();
            return new PersistentHashArrayMappedTrie.CompressedIndex(this.bitmap, var9, var3 + var4 - var6);
         }
      }

      public int size() {
         return this.size;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("CompressedIndex(");
         String var2 = Integer.toBinaryString(this.bitmap);
         int var3 = 0;
         var1.append(String.format("bitmap=%s ", var2));
         PersistentHashArrayMappedTrie.Node[] var5 = this.values;

         for(int var4 = var5.length; var3 < var4; ++var3) {
            var1.append(var5[var3]);
            var1.append(" ");
         }

         var1.append(")");
         return var1.toString();
      }
   }

   static final class Leaf<K, V> implements PersistentHashArrayMappedTrie.Node<K, V> {
      private final K key;
      private final V value;

      public Leaf(K var1, V var2) {
         this.key = var1;
         this.value = var2;
      }

      public V get(K var1, int var2, int var3) {
         return this.key == var1 ? this.value : null;
      }

      public PersistentHashArrayMappedTrie.Node<K, V> put(K var1, V var2, int var3, int var4) {
         int var5 = this.key.hashCode();
         if (var5 != var3) {
            return PersistentHashArrayMappedTrie.CompressedIndex.combine(new PersistentHashArrayMappedTrie.Leaf(var1, var2), var3, this, var5, var4);
         } else {
            return (PersistentHashArrayMappedTrie.Node)(this.key == var1 ? new PersistentHashArrayMappedTrie.Leaf(var1, var2) : new PersistentHashArrayMappedTrie.CollisionLeaf(this.key, this.value, var1, var2));
         }
      }

      public int size() {
         return 1;
      }

      public String toString() {
         return String.format("Leaf(key=%s value=%s)", this.key, this.value);
      }
   }

   interface Node<K, V> {
      V get(K var1, int var2, int var3);

      PersistentHashArrayMappedTrie.Node<K, V> put(K var1, V var2, int var3, int var4);

      int size();
   }
}

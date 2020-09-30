package com.google.api.client.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArrayMap<K, V> extends AbstractMap<K, V> implements Cloneable {
   private Object[] data;
   int size;

   public static <K, V> ArrayMap<K, V> create() {
      return new ArrayMap();
   }

   public static <K, V> ArrayMap<K, V> create(int var0) {
      ArrayMap var1 = create();
      var1.ensureCapacity(var0);
      return var1;
   }

   private int getDataIndexOfKey(Object var1) {
      int var2 = this.size;
      Object[] var3 = this.data;
      int var4 = 0;

      while(true) {
         if (var4 >= var2 << 1) {
            return -2;
         }

         Object var5 = var3[var4];
         if (var1 == null) {
            if (var5 == null) {
               break;
            }
         } else if (var1.equals(var5)) {
            break;
         }

         var4 += 2;
      }

      return var4;
   }

   public static <K, V> ArrayMap<K, V> of(Object... var0) {
      ArrayMap var1 = create(1);
      int var2 = var0.length;
      if (1 != var2 % 2) {
         var1.size = var0.length / 2;
         Object[] var3 = new Object[var2];
         var1.data = var3;
         System.arraycopy(var0, 0, var3, 0, var2);
         return var1;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("missing value for last key: ");
         var4.append(var0[var2 - 1]);
         throw new IllegalArgumentException(var4.toString());
      }
   }

   private V removeFromDataIndexOfKey(int var1) {
      int var2 = this.size << 1;
      if (var1 >= 0 && var1 < var2) {
         Object var3 = this.valueAtDataIndex(var1 + 1);
         Object[] var4 = this.data;
         int var5 = var2 - var1 - 2;
         if (var5 != 0) {
            System.arraycopy(var4, var1 + 2, var4, var1, var5);
         }

         --this.size;
         this.setData(var2 - 2, (Object)null, (Object)null);
         return var3;
      } else {
         return null;
      }
   }

   private void setData(int var1, K var2, V var3) {
      Object[] var4 = this.data;
      var4[var1] = var2;
      var4[var1 + 1] = var3;
   }

   private void setDataCapacity(int var1) {
      if (var1 == 0) {
         this.data = null;
      } else {
         int var2 = this.size;
         Object[] var3 = this.data;
         if (var2 == 0 || var1 != var3.length) {
            Object[] var4 = new Object[var1];
            this.data = var4;
            if (var2 != 0) {
               System.arraycopy(var3, 0, var4, 0, var2 << 1);
            }
         }

      }
   }

   private V valueAtDataIndex(int var1) {
      return var1 < 0 ? null : this.data[var1];
   }

   public final void add(K var1, V var2) {
      this.set(this.size, var1, var2);
   }

   public void clear() {
      this.size = 0;
      this.data = null;
   }

   public ArrayMap<K, V> clone() {
      // $FF: Couldn't be decompiled
   }

   public final boolean containsKey(Object var1) {
      boolean var2;
      if (-2 != this.getDataIndexOfKey(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final boolean containsValue(Object var1) {
      int var2 = this.size;
      Object[] var3 = this.data;
      int var4 = 1;

      while(true) {
         if (var4 >= var2 << 1) {
            return false;
         }

         Object var5 = var3[var4];
         if (var1 == null) {
            if (var5 == null) {
               break;
            }
         } else if (var1.equals(var5)) {
            break;
         }

         var4 += 2;
      }

      return true;
   }

   public final void ensureCapacity(int var1) {
      if (var1 >= 0) {
         Object[] var2 = this.data;
         int var3 = var1 << 1;
         if (var2 == null) {
            var1 = 0;
         } else {
            var1 = var2.length;
         }

         if (var3 > var1) {
            int var4 = var1 / 2 * 3 + 1;
            var1 = var4;
            if (var4 % 2 != 0) {
               var1 = var4 + 1;
            }

            if (var1 < var3) {
               var1 = var3;
            }

            this.setDataCapacity(var1);
         }

      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final Set<java.util.Map.Entry<K, V>> entrySet() {
      return new ArrayMap.EntrySet();
   }

   public final V get(Object var1) {
      return this.valueAtDataIndex(this.getDataIndexOfKey(var1) + 1);
   }

   public final int getIndexOfKey(K var1) {
      return this.getDataIndexOfKey(var1) >> 1;
   }

   public final K getKey(int var1) {
      return var1 >= 0 && var1 < this.size ? this.data[var1 << 1] : null;
   }

   public final V getValue(int var1) {
      return var1 >= 0 && var1 < this.size ? this.valueAtDataIndex((var1 << 1) + 1) : null;
   }

   public final V put(K var1, V var2) {
      int var3 = this.getIndexOfKey(var1);
      int var4 = var3;
      if (var3 == -1) {
         var4 = this.size;
      }

      return this.set(var4, var1, var2);
   }

   public final V remove(int var1) {
      return this.removeFromDataIndexOfKey(var1 << 1);
   }

   public final V remove(Object var1) {
      return this.removeFromDataIndexOfKey(this.getDataIndexOfKey(var1));
   }

   public final V set(int var1, V var2) {
      int var3 = this.size;
      if (var1 >= 0 && var1 < var3) {
         var1 = (var1 << 1) + 1;
         Object var4 = this.valueAtDataIndex(var1);
         this.data[var1] = var2;
         return var4;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final V set(int var1, K var2, V var3) {
      if (var1 >= 0) {
         int var4 = var1 + 1;
         this.ensureCapacity(var4);
         var1 <<= 1;
         Object var5 = this.valueAtDataIndex(var1 + 1);
         this.setData(var1, var2, var3);
         if (var4 > this.size) {
            this.size = var4;
         }

         return var5;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final int size() {
      return this.size;
   }

   public final void trim() {
      this.setDataCapacity(this.size << 1);
   }

   final class Entry implements java.util.Map.Entry<K, V> {
      private int index;

      Entry(int var2) {
         this.index = var2;
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof java.util.Map.Entry)) {
            return false;
         } else {
            java.util.Map.Entry var3 = (java.util.Map.Entry)var1;
            if (!Objects.equal(this.getKey(), var3.getKey()) || !Objects.equal(this.getValue(), var3.getValue())) {
               var2 = false;
            }

            return var2;
         }
      }

      public K getKey() {
         return ArrayMap.this.getKey(this.index);
      }

      public V getValue() {
         return ArrayMap.this.getValue(this.index);
      }

      public int hashCode() {
         Object var1 = this.getKey();
         Object var2 = this.getValue();
         int var3 = 0;
         int var4;
         if (var1 != null) {
            var4 = var1.hashCode();
         } else {
            var4 = 0;
         }

         if (var2 != null) {
            var3 = var2.hashCode();
         }

         return var4 ^ var3;
      }

      public V setValue(V var1) {
         return ArrayMap.this.set(this.index, var1);
      }
   }

   final class EntryIterator implements Iterator<java.util.Map.Entry<K, V>> {
      private int nextIndex;
      private boolean removed;

      public boolean hasNext() {
         boolean var1;
         if (this.nextIndex < ArrayMap.this.size) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public java.util.Map.Entry<K, V> next() {
         int var1 = this.nextIndex;
         if (var1 != ArrayMap.this.size) {
            ++this.nextIndex;
            this.removed = false;
            return ArrayMap.this.new Entry(var1);
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         int var1 = this.nextIndex - 1;
         if (!this.removed && var1 >= 0) {
            ArrayMap.this.remove(var1);
            --this.nextIndex;
            this.removed = true;
         } else {
            throw new IllegalArgumentException();
         }
      }
   }

   final class EntrySet extends AbstractSet<java.util.Map.Entry<K, V>> {
      public Iterator<java.util.Map.Entry<K, V>> iterator() {
         return ArrayMap.this.new EntryIterator();
      }

      public int size() {
         return ArrayMap.this.size;
      }
   }
}

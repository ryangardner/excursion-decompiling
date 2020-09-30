package androidx.collection;

public class LongSparseArray<E> implements Cloneable {
   private static final Object DELETED = new Object();
   private boolean mGarbage;
   private long[] mKeys;
   private int mSize;
   private Object[] mValues;

   public LongSparseArray() {
      this(10);
   }

   public LongSparseArray(int var1) {
      this.mGarbage = false;
      if (var1 == 0) {
         this.mKeys = ContainerHelpers.EMPTY_LONGS;
         this.mValues = ContainerHelpers.EMPTY_OBJECTS;
      } else {
         var1 = ContainerHelpers.idealLongArraySize(var1);
         this.mKeys = new long[var1];
         this.mValues = new Object[var1];
      }

   }

   private void gc() {
      int var1 = this.mSize;
      long[] var2 = this.mKeys;
      Object[] var3 = this.mValues;
      int var4 = 0;

      int var5;
      int var7;
      for(var5 = 0; var4 < var1; var5 = var7) {
         Object var6 = var3[var4];
         var7 = var5;
         if (var6 != DELETED) {
            if (var4 != var5) {
               var2[var5] = var2[var4];
               var3[var5] = var6;
               var3[var4] = null;
            }

            var7 = var5 + 1;
         }

         ++var4;
      }

      this.mGarbage = false;
      this.mSize = var5;
   }

   public void append(long var1, E var3) {
      int var4 = this.mSize;
      if (var4 != 0 && var1 <= this.mKeys[var4 - 1]) {
         this.put(var1, var3);
      } else {
         if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
         }

         var4 = this.mSize;
         if (var4 >= this.mKeys.length) {
            int var5 = ContainerHelpers.idealLongArraySize(var4 + 1);
            long[] var6 = new long[var5];
            Object[] var7 = new Object[var5];
            long[] var8 = this.mKeys;
            System.arraycopy(var8, 0, var6, 0, var8.length);
            Object[] var9 = this.mValues;
            System.arraycopy(var9, 0, var7, 0, var9.length);
            this.mKeys = var6;
            this.mValues = var7;
         }

         this.mKeys[var4] = var1;
         this.mValues[var4] = var3;
         this.mSize = var4 + 1;
      }
   }

   public void clear() {
      int var1 = this.mSize;
      Object[] var2 = this.mValues;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = null;
      }

      this.mSize = 0;
      this.mGarbage = false;
   }

   public LongSparseArray<E> clone() {
      try {
         LongSparseArray var1 = (LongSparseArray)super.clone();
         var1.mKeys = (long[])this.mKeys.clone();
         var1.mValues = (Object[])this.mValues.clone();
         return var1;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   public boolean containsKey(long var1) {
      boolean var3;
      if (this.indexOfKey(var1) >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsValue(E var1) {
      boolean var2;
      if (this.indexOfValue(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Deprecated
   public void delete(long var1) {
      this.remove(var1);
   }

   public E get(long var1) {
      return this.get(var1, (Object)null);
   }

   public E get(long var1, E var3) {
      int var4 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
      if (var4 >= 0) {
         Object[] var5 = this.mValues;
         if (var5[var4] != DELETED) {
            return var5[var4];
         }
      }

      return var3;
   }

   public int indexOfKey(long var1) {
      if (this.mGarbage) {
         this.gc();
      }

      return ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
   }

   public int indexOfValue(E var1) {
      if (this.mGarbage) {
         this.gc();
      }

      for(int var2 = 0; var2 < this.mSize; ++var2) {
         if (this.mValues[var2] == var1) {
            return var2;
         }
      }

      return -1;
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

   public long keyAt(int var1) {
      if (this.mGarbage) {
         this.gc();
      }

      return this.mKeys[var1];
   }

   public void put(long var1, E var3) {
      int var4 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
      if (var4 >= 0) {
         this.mValues[var4] = var3;
      } else {
         int var5 = var4;
         Object[] var6;
         if (var4 < this.mSize) {
            var6 = this.mValues;
            if (var6[var4] == DELETED) {
               this.mKeys[var4] = var1;
               var6[var4] = var3;
               return;
            }
         }

         var4 = var4;
         if (this.mGarbage) {
            var4 = var5;
            if (this.mSize >= this.mKeys.length) {
               this.gc();
               var4 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
            }
         }

         var5 = this.mSize;
         if (var5 >= this.mKeys.length) {
            var5 = ContainerHelpers.idealLongArraySize(var5 + 1);
            long[] var7 = new long[var5];
            var6 = new Object[var5];
            long[] var8 = this.mKeys;
            System.arraycopy(var8, 0, var7, 0, var8.length);
            Object[] var11 = this.mValues;
            System.arraycopy(var11, 0, var6, 0, var11.length);
            this.mKeys = var7;
            this.mValues = var6;
         }

         var5 = this.mSize;
         if (var5 - var4 != 0) {
            long[] var10 = this.mKeys;
            int var9 = var4 + 1;
            System.arraycopy(var10, var4, var10, var9, var5 - var4);
            var6 = this.mValues;
            System.arraycopy(var6, var4, var6, var9, this.mSize - var4);
         }

         this.mKeys[var4] = var1;
         this.mValues[var4] = var3;
         ++this.mSize;
      }

   }

   public void putAll(LongSparseArray<? extends E> var1) {
      int var2 = var1.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.put(var1.keyAt(var3), var1.valueAt(var3));
      }

   }

   public E putIfAbsent(long var1, E var3) {
      Object var4 = this.get(var1);
      if (var4 == null) {
         this.put(var1, var3);
      }

      return var4;
   }

   public void remove(long var1) {
      int var3 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, var1);
      if (var3 >= 0) {
         Object[] var4 = this.mValues;
         Object var5 = var4[var3];
         Object var6 = DELETED;
         if (var5 != var6) {
            var4[var3] = var6;
            this.mGarbage = true;
         }
      }

   }

   public boolean remove(long var1, Object var3) {
      int var4 = this.indexOfKey(var1);
      if (var4 >= 0) {
         Object var5 = this.valueAt(var4);
         if (var3 == var5 || var3 != null && var3.equals(var5)) {
            this.removeAt(var4);
            return true;
         }
      }

      return false;
   }

   public void removeAt(int var1) {
      Object[] var2 = this.mValues;
      Object var3 = var2[var1];
      Object var4 = DELETED;
      if (var3 != var4) {
         var2[var1] = var4;
         this.mGarbage = true;
      }

   }

   public E replace(long var1, E var3) {
      int var4 = this.indexOfKey(var1);
      if (var4 >= 0) {
         Object[] var5 = this.mValues;
         Object var6 = var5[var4];
         var5[var4] = var3;
         return var6;
      } else {
         return null;
      }
   }

   public boolean replace(long var1, E var3, E var4) {
      int var5 = this.indexOfKey(var1);
      if (var5 >= 0) {
         Object var6 = this.mValues[var5];
         if (var6 == var3 || var3 != null && var3.equals(var6)) {
            this.mValues[var5] = var4;
            return true;
         }
      }

      return false;
   }

   public void setValueAt(int var1, E var2) {
      if (this.mGarbage) {
         this.gc();
      }

      this.mValues[var1] = var2;
   }

   public int size() {
      if (this.mGarbage) {
         this.gc();
      }

      return this.mSize;
   }

   public String toString() {
      if (this.size() <= 0) {
         return "{}";
      } else {
         StringBuilder var1 = new StringBuilder(this.mSize * 28);
         var1.append('{');

         for(int var2 = 0; var2 < this.mSize; ++var2) {
            if (var2 > 0) {
               var1.append(", ");
            }

            var1.append(this.keyAt(var2));
            var1.append('=');
            Object var3 = this.valueAt(var2);
            if (var3 != this) {
               var1.append(var3);
            } else {
               var1.append("(this Map)");
            }
         }

         var1.append('}');
         return var1.toString();
      }
   }

   public E valueAt(int var1) {
      if (this.mGarbage) {
         this.gc();
      }

      return this.mValues[var1];
   }
}

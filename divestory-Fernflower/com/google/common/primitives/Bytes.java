package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Bytes {
   private Bytes() {
   }

   public static List<Byte> asList(byte... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Bytes.ByteArrayAsList(var0));
   }

   public static byte[] concat(byte[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      byte[] var4 = new byte[var3];
      var1 = var0.length;
      var3 = 0;

      for(var2 = 0; var3 < var1; ++var3) {
         byte[] var5 = var0[var3];
         System.arraycopy(var5, 0, var4, var2, var5.length);
         var2 += var5.length;
      }

      return var4;
   }

   public static boolean contains(byte[] var0, byte var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            return true;
         }
      }

      return false;
   }

   public static byte[] ensureCapacity(byte[] var0, int var1, int var2) {
      boolean var3 = true;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Invalid minLength: %s", var1);
      if (var2 >= 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Invalid padding: %s", var2);
      byte[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static int hashCode(byte var0) {
      return var0;
   }

   public static int indexOf(byte[] var0, byte var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(byte[] var0, byte var1, int var2, int var3) {
      while(var2 < var3) {
         if (var0[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public static int indexOf(byte[] var0, byte[] var1) {
      Preconditions.checkNotNull(var0, "array");
      Preconditions.checkNotNull(var1, "target");
      if (var1.length == 0) {
         return 0;
      } else {
         label27:
         for(int var2 = 0; var2 < var0.length - var1.length + 1; ++var2) {
            for(int var3 = 0; var3 < var1.length; ++var3) {
               if (var0[var2 + var3] != var1[var3]) {
                  continue label27;
               }
            }

            return var2;
         }

         return -1;
      }
   }

   public static int lastIndexOf(byte[] var0, byte var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(byte[] var0, byte var1, int var2, int var3) {
      --var3;

      while(var3 >= var2) {
         if (var0[var3] == var1) {
            return var3;
         }

         --var3;
      }

      return -1;
   }

   public static void reverse(byte[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(byte[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         byte var3 = var0[var1];
         var0[var1] = (byte)var0[var2];
         var0[var2] = (byte)var3;
         ++var1;
         --var2;
      }

   }

   public static byte[] toArray(Collection<? extends Number> var0) {
      if (var0 instanceof Bytes.ByteArrayAsList) {
         return ((Bytes.ByteArrayAsList)var0).toByteArray();
      } else {
         Object[] var4 = var0.toArray();
         int var1 = var4.length;
         byte[] var2 = new byte[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = ((Number)Preconditions.checkNotNull(var4[var3])).byteValue();
         }

         return var2;
      }
   }

   private static class ByteArrayAsList extends AbstractList<Byte> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final byte[] array;
      final int end;
      final int start;

      ByteArrayAsList(byte[] var1) {
         this(var1, 0, var1.length);
      }

      ByteArrayAsList(byte[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Byte && Bytes.indexOf(this.array, (Byte)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Bytes.ByteArrayAsList) {
            Bytes.ByteArrayAsList var4 = (Bytes.ByteArrayAsList)var1;
            int var2 = this.size();
            if (var4.size() != var2) {
               return false;
            } else {
               for(int var3 = 0; var3 < var2; ++var3) {
                  if (this.array[this.start + var3] != var4.array[var4.start + var3]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return super.equals(var1);
         }
      }

      public Byte get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Bytes.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Byte) {
            int var2 = Bytes.indexOf(this.array, (Byte)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public boolean isEmpty() {
         return false;
      }

      public int lastIndexOf(Object var1) {
         if (var1 instanceof Byte) {
            int var2 = Bytes.lastIndexOf(this.array, (Byte)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Byte set(int var1, Byte var2) {
         Preconditions.checkElementIndex(var1, this.size());
         byte[] var3 = this.array;
         int var4 = this.start;
         byte var5 = var3[var4 + var1];
         var3[var4 + var1] = (Byte)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Byte> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            byte[] var3 = this.array;
            int var4 = this.start;
            return new Bytes.ByteArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      byte[] toByteArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(this.size() * 5);
         var1.append('[');
         var1.append(this.array[this.start]);
         int var2 = this.start;

         while(true) {
            ++var2;
            if (var2 >= this.end) {
               var1.append(']');
               return var1.toString();
            }

            var1.append(", ");
            var1.append(this.array[var2]);
         }
      }
   }
}

package com.google.common.primitives;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Shorts {
   public static final int BYTES = 2;
   public static final short MAX_POWER_OF_TWO = 16384;

   private Shorts() {
   }

   public static List<Short> asList(short... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Shorts.ShortArrayAsList(var0));
   }

   public static short checkedCast(long var0) {
      short var2 = (short)((int)var0);
      boolean var3;
      if ((long)var2 == var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Out of range: %s", var0);
      return var2;
   }

   public static int compare(short var0, short var1) {
      return var0 - var1;
   }

   public static short[] concat(short[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      short[] var4 = new short[var3];
      var1 = var0.length;
      var2 = 0;

      for(var3 = 0; var2 < var1; ++var2) {
         short[] var5 = var0[var2];
         System.arraycopy(var5, 0, var4, var3, var5.length);
         var3 += var5.length;
      }

      return var4;
   }

   public static short constrainToRange(short var0, short var1, short var2) {
      boolean var3;
      if (var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "min (%s) must be less than or equal to max (%s)", (int)var1, (int)var2);
      short var4;
      if (var0 < var1) {
         var4 = var1;
      } else if (var0 < var2) {
         var4 = var0;
      } else {
         var4 = var2;
      }

      return var4;
   }

   public static boolean contains(short[] var0, short var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            return true;
         }
      }

      return false;
   }

   public static short[] ensureCapacity(short[] var0, int var1, int var2) {
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
      short[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static short fromByteArray(byte[] var0) {
      boolean var1;
      if (var0.length >= 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "array too small: %s < %s", (int)var0.length, (int)2);
      return fromBytes(var0[0], var0[1]);
   }

   public static short fromBytes(byte var0, byte var1) {
      return (short)(var0 << 8 | var1 & 255);
   }

   public static int hashCode(short var0) {
      return var0;
   }

   public static int indexOf(short[] var0, short var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(short[] var0, short var1, int var2, int var3) {
      while(var2 < var3) {
         if (var0[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public static int indexOf(short[] var0, short[] var1) {
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

   public static String join(String var0, short... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * 6);
         var2.append(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(var1[var3]);
         }

         return var2.toString();
      }
   }

   public static int lastIndexOf(short[] var0, short var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(short[] var0, short var1, int var2, int var3) {
      --var3;

      while(var3 >= var2) {
         if (var0[var3] == var1) {
            return var3;
         }

         --var3;
      }

      return -1;
   }

   public static Comparator<short[]> lexicographicalComparator() {
      return Shorts.LexicographicalComparator.INSTANCE;
   }

   public static short max(short... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      short var5 = var0[0];

      short var4;
      for(var4 = var5; var2 < var0.length; var4 = var5) {
         var5 = var4;
         if (var0[var2] > var4) {
            var5 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static short min(short... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      short var5 = var0[0];

      short var4;
      for(var4 = var5; var2 < var0.length; var4 = var5) {
         var5 = var4;
         if (var0[var2] < var4) {
            var5 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static void reverse(short[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(short[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         short var3 = var0[var1];
         var0[var1] = (short)var0[var2];
         var0[var2] = (short)var3;
         ++var1;
         --var2;
      }

   }

   public static short saturatedCast(long var0) {
      if (var0 > 32767L) {
         return 32767;
      } else {
         return var0 < -32768L ? -32768 : (short)((int)var0);
      }
   }

   public static void sortDescending(short[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(short[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      reverse(var0, var1, var2);
   }

   public static Converter<String, Short> stringConverter() {
      return Shorts.ShortConverter.INSTANCE;
   }

   public static short[] toArray(Collection<? extends Number> var0) {
      if (var0 instanceof Shorts.ShortArrayAsList) {
         return ((Shorts.ShortArrayAsList)var0).toShortArray();
      } else {
         Object[] var4 = var0.toArray();
         int var1 = var4.length;
         short[] var2 = new short[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = ((Number)Preconditions.checkNotNull(var4[var3])).shortValue();
         }

         return var2;
      }
   }

   public static byte[] toByteArray(short var0) {
      return new byte[]{(byte)(var0 >> 8), (byte)var0};
   }

   private static enum LexicographicalComparator implements Comparator<short[]> {
      INSTANCE;

      static {
         Shorts.LexicographicalComparator var0 = new Shorts.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(short[] var1, short[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Shorts.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Shorts.lexicographicalComparator()";
      }
   }

   private static class ShortArrayAsList extends AbstractList<Short> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final short[] array;
      final int end;
      final int start;

      ShortArrayAsList(short[] var1) {
         this(var1, 0, var1.length);
      }

      ShortArrayAsList(short[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(@NullableDecl Object var1) {
         boolean var2;
         if (var1 instanceof Short && Shorts.indexOf(this.array, (Short)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Shorts.ShortArrayAsList) {
            Shorts.ShortArrayAsList var4 = (Shorts.ShortArrayAsList)var1;
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

      public Short get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Shorts.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(@NullableDecl Object var1) {
         if (var1 instanceof Short) {
            int var2 = Shorts.indexOf(this.array, (Short)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public boolean isEmpty() {
         return false;
      }

      public int lastIndexOf(@NullableDecl Object var1) {
         if (var1 instanceof Short) {
            int var2 = Shorts.lastIndexOf(this.array, (Short)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Short set(int var1, Short var2) {
         Preconditions.checkElementIndex(var1, this.size());
         short[] var3 = this.array;
         int var4 = this.start;
         short var5 = var3[var4 + var1];
         var3[var4 + var1] = (Short)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Short> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            short[] var3 = this.array;
            int var4 = this.start;
            return new Shorts.ShortArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      short[] toShortArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(this.size() * 6);
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

   private static final class ShortConverter extends Converter<String, Short> implements Serializable {
      static final Shorts.ShortConverter INSTANCE = new Shorts.ShortConverter();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected String doBackward(Short var1) {
         return var1.toString();
      }

      protected Short doForward(String var1) {
         return Short.decode(var1);
      }

      public String toString() {
         return "Shorts.stringConverter()";
      }
   }
}

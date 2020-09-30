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

public final class Ints {
   public static final int BYTES = 4;
   public static final int MAX_POWER_OF_TWO = 1073741824;

   private Ints() {
   }

   public static List<Integer> asList(int... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Ints.IntArrayAsList(var0));
   }

   public static int checkedCast(long var0) {
      int var2 = (int)var0;
      boolean var3;
      if ((long)var2 == var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Out of range: %s", var0);
      return var2;
   }

   public static int compare(int var0, int var1) {
      byte var2;
      if (var0 < var1) {
         var2 = -1;
      } else if (var0 > var1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      return var2;
   }

   public static int[] concat(int[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      int[] var4 = new int[var3];
      var1 = var0.length;
      var2 = 0;

      for(var3 = 0; var2 < var1; ++var2) {
         int[] var5 = var0[var2];
         System.arraycopy(var5, 0, var4, var3, var5.length);
         var3 += var5.length;
      }

      return var4;
   }

   public static int constrainToRange(int var0, int var1, int var2) {
      boolean var3;
      if (var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "min (%s) must be less than or equal to max (%s)", var1, var2);
      return Math.min(Math.max(var0, var1), var2);
   }

   public static boolean contains(int[] var0, int var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            return true;
         }
      }

      return false;
   }

   public static int[] ensureCapacity(int[] var0, int var1, int var2) {
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
      int[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static int fromByteArray(byte[] var0) {
      boolean var1;
      if (var0.length >= 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "array too small: %s < %s", (int)var0.length, (int)4);
      return fromBytes(var0[0], var0[1], var0[2], var0[3]);
   }

   public static int fromBytes(byte var0, byte var1, byte var2, byte var3) {
      return var0 << 24 | (var1 & 255) << 16 | (var2 & 255) << 8 | var3 & 255;
   }

   public static int hashCode(int var0) {
      return var0;
   }

   public static int indexOf(int[] var0, int var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(int[] var0, int var1, int var2, int var3) {
      while(var2 < var3) {
         if (var0[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public static int indexOf(int[] var0, int[] var1) {
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

   public static String join(String var0, int... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * 5);
         var2.append(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(var1[var3]);
         }

         return var2.toString();
      }
   }

   public static int lastIndexOf(int[] var0, int var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(int[] var0, int var1, int var2, int var3) {
      --var3;

      while(var3 >= var2) {
         if (var0[var3] == var1) {
            return var3;
         }

         --var3;
      }

      return -1;
   }

   public static Comparator<int[]> lexicographicalComparator() {
      return Ints.LexicographicalComparator.INSTANCE;
   }

   public static int max(int... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      int var4;
      for(var4 = var0[0]; var2 < var0.length; var4 = var1) {
         var1 = var4;
         if (var0[var2] > var4) {
            var1 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static int min(int... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      int var4;
      for(var4 = var0[0]; var2 < var0.length; var4 = var1) {
         var1 = var4;
         if (var0[var2] < var4) {
            var1 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static void reverse(int[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(int[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         int var3 = var0[var1];
         var0[var1] = var0[var2];
         var0[var2] = var3;
         ++var1;
         --var2;
      }

   }

   public static int saturatedCast(long var0) {
      if (var0 > 2147483647L) {
         return Integer.MAX_VALUE;
      } else {
         return var0 < -2147483648L ? Integer.MIN_VALUE : (int)var0;
      }
   }

   public static void sortDescending(int[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(int[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      reverse(var0, var1, var2);
   }

   public static Converter<String, Integer> stringConverter() {
      return Ints.IntConverter.INSTANCE;
   }

   public static int[] toArray(Collection<? extends Number> var0) {
      if (var0 instanceof Ints.IntArrayAsList) {
         return ((Ints.IntArrayAsList)var0).toIntArray();
      } else {
         Object[] var1 = var0.toArray();
         int var2 = var1.length;
         int[] var4 = new int[var2];

         for(int var3 = 0; var3 < var2; ++var3) {
            var4[var3] = ((Number)Preconditions.checkNotNull(var1[var3])).intValue();
         }

         return var4;
      }
   }

   public static byte[] toByteArray(int var0) {
      return new byte[]{(byte)(var0 >> 24), (byte)(var0 >> 16), (byte)(var0 >> 8), (byte)var0};
   }

   @NullableDecl
   public static Integer tryParse(String var0) {
      return tryParse(var0, 10);
   }

   @NullableDecl
   public static Integer tryParse(String var0, int var1) {
      Long var2 = Longs.tryParse(var0, var1);
      return var2 != null && var2 == (long)var2.intValue() ? var2.intValue() : null;
   }

   private static class IntArrayAsList extends AbstractList<Integer> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final int[] array;
      final int end;
      final int start;

      IntArrayAsList(int[] var1) {
         this(var1, 0, var1.length);
      }

      IntArrayAsList(int[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Integer && Ints.indexOf(this.array, (Integer)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Ints.IntArrayAsList) {
            Ints.IntArrayAsList var4 = (Ints.IntArrayAsList)var1;
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

      public Integer get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Ints.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Integer) {
            int var2 = Ints.indexOf(this.array, (Integer)var1, this.start, this.end);
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
         if (var1 instanceof Integer) {
            int var2 = Ints.lastIndexOf(this.array, (Integer)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Integer set(int var1, Integer var2) {
         Preconditions.checkElementIndex(var1, this.size());
         int[] var3 = this.array;
         int var4 = this.start;
         int var5 = var3[var4 + var1];
         var3[var4 + var1] = (Integer)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Integer> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            int[] var3 = this.array;
            int var4 = this.start;
            return new Ints.IntArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      int[] toIntArray() {
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

   private static final class IntConverter extends Converter<String, Integer> implements Serializable {
      static final Ints.IntConverter INSTANCE = new Ints.IntConverter();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected String doBackward(Integer var1) {
         return var1.toString();
      }

      protected Integer doForward(String var1) {
         return Integer.decode(var1);
      }

      public String toString() {
         return "Ints.stringConverter()";
      }
   }

   private static enum LexicographicalComparator implements Comparator<int[]> {
      INSTANCE;

      static {
         Ints.LexicographicalComparator var0 = new Ints.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(int[] var1, int[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Ints.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Ints.lexicographicalComparator()";
      }
   }
}

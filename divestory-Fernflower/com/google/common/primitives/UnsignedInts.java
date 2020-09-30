package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;

public final class UnsignedInts {
   static final long INT_MASK = 4294967295L;

   private UnsignedInts() {
   }

   public static int checkedCast(long var0) {
      boolean var2;
      if (var0 >> 32 == 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "out of range: %s", var0);
      return (int)var0;
   }

   public static int compare(int var0, int var1) {
      return Ints.compare(flip(var0), flip(var1));
   }

   public static int decode(String var0) {
      ParseRequest var1 = ParseRequest.fromString(var0);

      try {
         int var2 = parseUnsignedInt(var1.rawValue, var1.radix);
         return var2;
      } catch (NumberFormatException var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Error parsing value: ");
         var3.append(var0);
         NumberFormatException var5 = new NumberFormatException(var3.toString());
         var5.initCause(var4);
         throw var5;
      }
   }

   public static int divide(int var0, int var1) {
      return (int)(toLong(var0) / toLong(var1));
   }

   static int flip(int var0) {
      return var0 ^ Integer.MIN_VALUE;
   }

   public static String join(String var0, int... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * 5);
         var2.append(toString(var1[0]));

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(toString(var1[var3]));
         }

         return var2.toString();
      }
   }

   public static Comparator<int[]> lexicographicalComparator() {
      return UnsignedInts.LexicographicalComparator.INSTANCE;
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
      for(var4 = flip(var0[0]); var2 < var0.length; var4 = var1) {
         int var5 = flip(var0[var2]);
         var1 = var4;
         if (var5 > var4) {
            var1 = var5;
         }

         ++var2;
      }

      return flip(var4);
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
      for(var4 = flip(var0[0]); var2 < var0.length; var4 = var1) {
         int var5 = flip(var0[var2]);
         var1 = var4;
         if (var5 < var4) {
            var1 = var5;
         }

         ++var2;
      }

      return flip(var4);
   }

   public static int parseUnsignedInt(String var0) {
      return parseUnsignedInt(var0, 10);
   }

   public static int parseUnsignedInt(String var0, int var1) {
      Preconditions.checkNotNull(var0);
      long var2 = Long.parseLong(var0, var1);
      if ((4294967295L & var2) == var2) {
         return (int)var2;
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Input ");
         var4.append(var0);
         var4.append(" in base ");
         var4.append(var1);
         var4.append(" is not in the range of an unsigned integer");
         throw new NumberFormatException(var4.toString());
      }
   }

   public static int remainder(int var0, int var1) {
      return (int)(toLong(var0) % toLong(var1));
   }

   public static int saturatedCast(long var0) {
      if (var0 <= 0L) {
         return 0;
      } else {
         return var0 >= 4294967296L ? -1 : (int)var0;
      }
   }

   public static void sort(int[] var0) {
      Preconditions.checkNotNull(var0);
      sort(var0, 0, var0.length);
   }

   public static void sort(int[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);

      for(int var3 = var1; var3 < var2; ++var3) {
         var0[var3] = flip(var0[var3]);
      }

      Arrays.sort(var0, var1, var2);

      while(var1 < var2) {
         var0[var1] = flip(var0[var1]);
         ++var1;
      }

   }

   public static void sortDescending(int[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(int[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);

      for(int var3 = var1; var3 < var2; ++var3) {
         var0[var3] ^= Integer.MAX_VALUE;
      }

      Arrays.sort(var0, var1, var2);

      while(var1 < var2) {
         var0[var1] ^= Integer.MAX_VALUE;
         ++var1;
      }

   }

   public static long toLong(int var0) {
      return (long)var0 & 4294967295L;
   }

   public static String toString(int var0) {
      return toString(var0, 10);
   }

   public static String toString(int var0, int var1) {
      return Long.toString((long)var0 & 4294967295L, var1);
   }

   static enum LexicographicalComparator implements Comparator<int[]> {
      INSTANCE;

      static {
         UnsignedInts.LexicographicalComparator var0 = new UnsignedInts.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(int[] var1, int[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            if (var1[var4] != var2[var4]) {
               return UnsignedInts.compare(var1[var4], var2[var4]);
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "UnsignedInts.lexicographicalComparator()";
      }
   }
}

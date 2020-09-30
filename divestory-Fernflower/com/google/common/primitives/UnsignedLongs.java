package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

public final class UnsignedLongs {
   public static final long MAX_VALUE = -1L;

   private UnsignedLongs() {
   }

   public static int compare(long var0, long var2) {
      return Longs.compare(flip(var0), flip(var2));
   }

   public static long decode(String var0) {
      ParseRequest var1 = ParseRequest.fromString(var0);

      try {
         long var2 = parseUnsignedLong(var1.rawValue, var1.radix);
         return var2;
      } catch (NumberFormatException var5) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Error parsing value: ");
         var4.append(var0);
         NumberFormatException var6 = new NumberFormatException(var4.toString());
         var6.initCause(var5);
         throw var6;
      }
   }

   public static long divide(long var0, long var2) {
      if (var2 < 0L) {
         return compare(var0, var2) < 0 ? 0L : 1L;
      } else if (var0 >= 0L) {
         return var0 / var2;
      } else {
         byte var4 = 1;
         long var5 = (var0 >>> 1) / var2 << 1;
         if (compare(var0 - var5 * var2, var2) < 0) {
            var4 = 0;
         }

         return var5 + (long)var4;
      }
   }

   private static long flip(long var0) {
      return var0 ^ Long.MIN_VALUE;
   }

   public static String join(String var0, long... var1) {
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

   public static Comparator<long[]> lexicographicalComparator() {
      return UnsignedLongs.LexicographicalComparator.INSTANCE;
   }

   public static long max(long... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      long var4;
      long var8;
      for(var4 = flip(var0[0]); var2 < var0.length; var4 = var8) {
         long var6 = flip(var0[var2]);
         var8 = var4;
         if (var6 > var4) {
            var8 = var6;
         }

         ++var2;
      }

      return flip(var4);
   }

   public static long min(long... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      long var4;
      long var8;
      for(var4 = flip(var0[0]); var2 < var0.length; var4 = var8) {
         long var6 = flip(var0[var2]);
         var8 = var4;
         if (var6 < var4) {
            var8 = var6;
         }

         ++var2;
      }

      return flip(var4);
   }

   public static long parseUnsignedLong(String var0) {
      return parseUnsignedLong(var0, 10);
   }

   public static long parseUnsignedLong(String var0, int var1) {
      Preconditions.checkNotNull(var0);
      if (var0.length() != 0) {
         if (var1 >= 2 && var1 <= 36) {
            int var2 = UnsignedLongs.ParseOverflowDetection.maxSafeDigits[var1];
            long var3 = 0L;

            for(int var5 = 0; var5 < var0.length(); ++var5) {
               int var6 = Character.digit(var0.charAt(var5), var1);
               if (var6 == -1) {
                  throw new NumberFormatException(var0);
               }

               if (var5 > var2 - 1 && UnsignedLongs.ParseOverflowDetection.overflowInParse(var3, var6, var1)) {
                  StringBuilder var7 = new StringBuilder();
                  var7.append("Too large for unsigned long: ");
                  var7.append(var0);
                  throw new NumberFormatException(var7.toString());
               }

               var3 = var3 * (long)var1 + (long)var6;
            }

            return var3;
         } else {
            StringBuilder var8 = new StringBuilder();
            var8.append("illegal radix: ");
            var8.append(var1);
            throw new NumberFormatException(var8.toString());
         }
      } else {
         throw new NumberFormatException("empty string");
      }
   }

   public static long remainder(long var0, long var2) {
      if (var2 < 0L) {
         return compare(var0, var2) < 0 ? var0 : var0 - var2;
      } else if (var0 >= 0L) {
         return var0 % var2;
      } else {
         var0 -= ((var0 >>> 1) / var2 << 1) * var2;
         if (compare(var0, var2) < 0) {
            var2 = 0L;
         }

         return var0 - var2;
      }
   }

   public static void sort(long[] var0) {
      Preconditions.checkNotNull(var0);
      sort(var0, 0, var0.length);
   }

   public static void sort(long[] var0, int var1, int var2) {
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

   public static void sortDescending(long[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(long[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);

      for(int var3 = var1; var3 < var2; ++var3) {
         var0[var3] ^= Long.MAX_VALUE;
      }

      Arrays.sort(var0, var1, var2);

      while(var1 < var2) {
         var0[var1] ^= Long.MAX_VALUE;
         ++var1;
      }

   }

   public static String toString(long var0) {
      return toString(var0, 10);
   }

   public static String toString(long var0, int var2) {
      boolean var3;
      if (var2 >= 2 && var2 <= 36) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", var2);
      long var13;
      int var4 = (var13 = var0 - 0L) == 0L ? 0 : (var13 < 0L ? -1 : 1);
      if (var4 == 0) {
         return "0";
      } else if (var4 > 0) {
         return Long.toString(var0, var2);
      } else {
         var4 = 64;
         char[] var5 = new char[64];
         int var6 = var2 - 1;
         int var8;
         long var9;
         if ((var2 & var6) == 0) {
            int var7 = Integer.numberOfTrailingZeros(var2);

            do {
               var8 = var4 - 1;
               var5[var8] = Character.forDigit((int)var0 & var6, var2);
               var9 = var0 >>> var7;
               var4 = var8;
               var0 = var9;
            } while(var9 != 0L);
         } else {
            if ((var2 & 1) == 0) {
               var9 = (var0 >>> 1) / (long)(var2 >>> 1);
            } else {
               var9 = divide(var0, (long)var2);
            }

            long var11 = (long)var2;
            var5[63] = Character.forDigit((int)(var0 - var9 * var11), var2);
            var4 = 63;

            while(true) {
               var8 = var4;
               if (var9 <= 0L) {
                  break;
               }

               --var4;
               var5[var4] = Character.forDigit((int)(var9 % var11), var2);
               var9 /= var11;
            }
         }

         return new String(var5, var8, 64 - var8);
      }
   }

   static enum LexicographicalComparator implements Comparator<long[]> {
      INSTANCE;

      static {
         UnsignedLongs.LexicographicalComparator var0 = new UnsignedLongs.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(long[] var1, long[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            if (var1[var4] != var2[var4]) {
               return UnsignedLongs.compare(var1[var4], var2[var4]);
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "UnsignedLongs.lexicographicalComparator()";
      }
   }

   private static final class ParseOverflowDetection {
      static final int[] maxSafeDigits = new int[37];
      static final long[] maxValueDivs = new long[37];
      static final int[] maxValueMods = new int[37];

      static {
         BigInteger var0 = new BigInteger("10000000000000000", 16);

         for(int var1 = 2; var1 <= 36; ++var1) {
            long[] var2 = maxValueDivs;
            long var3 = (long)var1;
            var2[var1] = UnsignedLongs.divide(-1L, var3);
            maxValueMods[var1] = (int)UnsignedLongs.remainder(-1L, var3);
            maxSafeDigits[var1] = var0.toString(var1).length() - 1;
         }

      }

      static boolean overflowInParse(long var0, int var2, int var3) {
         boolean var4 = true;
         boolean var5 = var4;
         if (var0 >= 0L) {
            long[] var6 = maxValueDivs;
            if (var0 < var6[var3]) {
               return false;
            }

            if (var0 > var6[var3]) {
               return true;
            }

            if (var2 > maxValueMods[var3]) {
               var5 = var4;
            } else {
               var5 = false;
            }
         }

         return var5;
      }
   }
}

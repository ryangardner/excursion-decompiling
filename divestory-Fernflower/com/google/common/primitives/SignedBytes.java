package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;

public final class SignedBytes {
   public static final byte MAX_POWER_OF_TWO = 64;

   private SignedBytes() {
   }

   public static byte checkedCast(long var0) {
      byte var2 = (byte)((int)var0);
      boolean var3;
      if ((long)var2 == var0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Out of range: %s", var0);
      return var2;
   }

   public static int compare(byte var0, byte var1) {
      return var0 - var1;
   }

   public static String join(String var0, byte... var1) {
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

   public static Comparator<byte[]> lexicographicalComparator() {
      return SignedBytes.LexicographicalComparator.INSTANCE;
   }

   public static byte max(byte... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      byte var5 = var0[0];

      byte var4;
      for(var4 = var5; var2 < var0.length; var4 = var5) {
         var5 = var4;
         if (var0[var2] > var4) {
            var5 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static byte min(byte... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      byte var5 = var0[0];

      byte var4;
      for(var4 = var5; var2 < var0.length; var4 = var5) {
         var5 = var4;
         if (var0[var2] < var4) {
            var5 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static byte saturatedCast(long var0) {
      if (var0 > 127L) {
         return 127;
      } else {
         return var0 < -128L ? -128 : (byte)((int)var0);
      }
   }

   public static void sortDescending(byte[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(byte[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      Bytes.reverse(var0, var1, var2);
   }

   private static enum LexicographicalComparator implements Comparator<byte[]> {
      INSTANCE;

      static {
         SignedBytes.LexicographicalComparator var0 = new SignedBytes.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(byte[] var1, byte[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = SignedBytes.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "SignedBytes.lexicographicalComparator()";
      }
   }
}

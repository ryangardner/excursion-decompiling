package com.google.common.math;

import java.math.BigInteger;
import java.math.RoundingMode;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class MathPreconditions {
   private MathPreconditions() {
   }

   static void checkInRangeForRoundingInputs(boolean var0, double var1, RoundingMode var3) {
      if (!var0) {
         StringBuilder var4 = new StringBuilder();
         var4.append("rounded value is out of range for input ");
         var4.append(var1);
         var4.append(" and rounding mode ");
         var4.append(var3);
         throw new ArithmeticException(var4.toString());
      }
   }

   static void checkNoOverflow(boolean var0, String var1, int var2, int var3) {
      if (!var0) {
         StringBuilder var4 = new StringBuilder();
         var4.append("overflow: ");
         var4.append(var1);
         var4.append("(");
         var4.append(var2);
         var4.append(", ");
         var4.append(var3);
         var4.append(")");
         throw new ArithmeticException(var4.toString());
      }
   }

   static void checkNoOverflow(boolean var0, String var1, long var2, long var4) {
      if (!var0) {
         StringBuilder var6 = new StringBuilder();
         var6.append("overflow: ");
         var6.append(var1);
         var6.append("(");
         var6.append(var2);
         var6.append(", ");
         var6.append(var4);
         var6.append(")");
         throw new ArithmeticException(var6.toString());
      }
   }

   static double checkNonNegative(@NullableDecl String var0, double var1) {
      if (var1 >= 0.0D) {
         return var1;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" (");
         var3.append(var1);
         var3.append(") must be >= 0");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   static int checkNonNegative(@NullableDecl String var0, int var1) {
      if (var1 >= 0) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(" (");
         var2.append(var1);
         var2.append(") must be >= 0");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static long checkNonNegative(@NullableDecl String var0, long var1) {
      if (var1 >= 0L) {
         return var1;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" (");
         var3.append(var1);
         var3.append(") must be >= 0");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   static BigInteger checkNonNegative(@NullableDecl String var0, BigInteger var1) {
      if (var1.signum() >= 0) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(" (");
         var2.append(var1);
         var2.append(") must be >= 0");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static int checkPositive(@NullableDecl String var0, int var1) {
      if (var1 > 0) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(" (");
         var2.append(var1);
         var2.append(") must be > 0");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static long checkPositive(@NullableDecl String var0, long var1) {
      if (var1 > 0L) {
         return var1;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0);
         var3.append(" (");
         var3.append(var1);
         var3.append(") must be > 0");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   static BigInteger checkPositive(@NullableDecl String var0, BigInteger var1) {
      if (var1.signum() > 0) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0);
         var2.append(" (");
         var2.append(var1);
         var2.append(") must be > 0");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static void checkRoundingUnnecessary(boolean var0) {
      if (!var0) {
         throw new ArithmeticException("mode was UNNECESSARY, but rounding was necessary");
      }
   }
}

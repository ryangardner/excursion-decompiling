package io.opencensus.common;

import java.math.BigInteger;

final class TimeUtils {
   private static final BigInteger MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
   static final int MAX_NANOS = 999999999;
   static final long MAX_SECONDS = 315576000000L;
   static final long MILLIS_PER_SECOND = 1000L;
   private static final BigInteger MIN_LONG_VALUE = BigInteger.valueOf(Long.MIN_VALUE);
   static final long NANOS_PER_MILLI = 1000000L;
   static final long NANOS_PER_SECOND = 1000000000L;

   private TimeUtils() {
   }

   static long checkedAdd(long var0, long var2) {
      BigInteger var4 = BigInteger.valueOf(var0).add(BigInteger.valueOf(var2));
      if (var4.compareTo(MAX_LONG_VALUE) <= 0 && var4.compareTo(MIN_LONG_VALUE) >= 0) {
         return var0 + var2;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Long sum overflow: x=");
         var5.append(var0);
         var5.append(", y=");
         var5.append(var2);
         throw new ArithmeticException(var5.toString());
      }
   }

   static int compareLongs(long var0, long var2) {
      long var5;
      int var4 = (var5 = var0 - var2) == 0L ? 0 : (var5 < 0L ? -1 : 1);
      if (var4 < 0) {
         return -1;
      } else {
         return var4 == 0 ? 0 : 1;
      }
   }
}

package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public abstract class DiscreteDomain<C extends Comparable> {
   final boolean supportsFastOffset;

   protected DiscreteDomain() {
      this(false);
   }

   private DiscreteDomain(boolean var1) {
      this.supportsFastOffset = var1;
   }

   // $FF: synthetic method
   DiscreteDomain(boolean var1, Object var2) {
      this(var1);
   }

   public static DiscreteDomain<BigInteger> bigIntegers() {
      return DiscreteDomain.BigIntegerDomain.INSTANCE;
   }

   public static DiscreteDomain<Integer> integers() {
      return DiscreteDomain.IntegerDomain.INSTANCE;
   }

   public static DiscreteDomain<Long> longs() {
      return DiscreteDomain.LongDomain.INSTANCE;
   }

   public abstract long distance(C var1, C var2);

   public C maxValue() {
      throw new NoSuchElementException();
   }

   public C minValue() {
      throw new NoSuchElementException();
   }

   public abstract C next(C var1);

   C offset(C var1, long var2) {
      CollectPreconditions.checkNonnegative(var2, "distance");

      for(long var4 = 0L; var4 < var2; ++var4) {
         var1 = this.next(var1);
      }

      return var1;
   }

   public abstract C previous(C var1);

   private static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable {
      private static final DiscreteDomain.BigIntegerDomain INSTANCE = new DiscreteDomain.BigIntegerDomain();
      private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
      private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
      private static final long serialVersionUID = 0L;

      BigIntegerDomain() {
         super(true, null);
      }

      private Object readResolve() {
         return INSTANCE;
      }

      public long distance(BigInteger var1, BigInteger var2) {
         return var2.subtract(var1).max(MIN_LONG).min(MAX_LONG).longValue();
      }

      public BigInteger next(BigInteger var1) {
         return var1.add(BigInteger.ONE);
      }

      BigInteger offset(BigInteger var1, long var2) {
         CollectPreconditions.checkNonnegative(var2, "distance");
         return var1.add(BigInteger.valueOf(var2));
      }

      public BigInteger previous(BigInteger var1) {
         return var1.subtract(BigInteger.ONE);
      }

      public String toString() {
         return "DiscreteDomain.bigIntegers()";
      }
   }

   private static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable {
      private static final DiscreteDomain.IntegerDomain INSTANCE = new DiscreteDomain.IntegerDomain();
      private static final long serialVersionUID = 0L;

      IntegerDomain() {
         super(true, null);
      }

      private Object readResolve() {
         return INSTANCE;
      }

      public long distance(Integer var1, Integer var2) {
         return (long)var2 - (long)var1;
      }

      public Integer maxValue() {
         return Integer.MAX_VALUE;
      }

      public Integer minValue() {
         return Integer.MIN_VALUE;
      }

      public Integer next(Integer var1) {
         int var2 = var1;
         if (var2 == Integer.MAX_VALUE) {
            var1 = null;
         } else {
            var1 = var2 + 1;
         }

         return var1;
      }

      Integer offset(Integer var1, long var2) {
         CollectPreconditions.checkNonnegative(var2, "distance");
         return Ints.checkedCast(var1.longValue() + var2);
      }

      public Integer previous(Integer var1) {
         int var2 = var1;
         if (var2 == Integer.MIN_VALUE) {
            var1 = null;
         } else {
            var1 = var2 - 1;
         }

         return var1;
      }

      public String toString() {
         return "DiscreteDomain.integers()";
      }
   }

   private static final class LongDomain extends DiscreteDomain<Long> implements Serializable {
      private static final DiscreteDomain.LongDomain INSTANCE = new DiscreteDomain.LongDomain();
      private static final long serialVersionUID = 0L;

      LongDomain() {
         super(true, null);
      }

      private Object readResolve() {
         return INSTANCE;
      }

      public long distance(Long var1, Long var2) {
         long var3 = var2 - var1;
         if (var2 > var1 && var3 < 0L) {
            return Long.MAX_VALUE;
         } else {
            return var2 < var1 && var3 > 0L ? Long.MIN_VALUE : var3;
         }
      }

      public Long maxValue() {
         return Long.MAX_VALUE;
      }

      public Long minValue() {
         return Long.MIN_VALUE;
      }

      public Long next(Long var1) {
         long var2 = var1;
         if (var2 == Long.MAX_VALUE) {
            var1 = null;
         } else {
            var1 = var2 + 1L;
         }

         return var1;
      }

      Long offset(Long var1, long var2) {
         CollectPreconditions.checkNonnegative(var2, "distance");
         var2 += var1;
         if (var2 < 0L) {
            boolean var4;
            if (var1 < 0L) {
               var4 = true;
            } else {
               var4 = false;
            }

            Preconditions.checkArgument(var4, "overflow");
         }

         return var2;
      }

      public Long previous(Long var1) {
         long var2 = var1;
         if (var2 == Long.MIN_VALUE) {
            var1 = null;
         } else {
            var1 = var2 - 1L;
         }

         return var1;
      }

      public String toString() {
         return "DiscreteDomain.longs()";
      }
   }
}

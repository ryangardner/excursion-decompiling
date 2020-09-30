package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.math.BigInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class UnsignedLong extends Number implements Comparable<UnsignedLong>, Serializable {
   public static final UnsignedLong MAX_VALUE = new UnsignedLong(-1L);
   public static final UnsignedLong ONE = new UnsignedLong(1L);
   private static final long UNSIGNED_MASK = Long.MAX_VALUE;
   public static final UnsignedLong ZERO = new UnsignedLong(0L);
   private final long value;

   private UnsignedLong(long var1) {
      this.value = var1;
   }

   public static UnsignedLong fromLongBits(long var0) {
      return new UnsignedLong(var0);
   }

   public static UnsignedLong valueOf(long var0) {
      boolean var2;
      if (var0 >= 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "value (%s) is outside the range for an unsigned long value", var0);
      return fromLongBits(var0);
   }

   public static UnsignedLong valueOf(String var0) {
      return valueOf(var0, 10);
   }

   public static UnsignedLong valueOf(String var0, int var1) {
      return fromLongBits(UnsignedLongs.parseUnsignedLong(var0, var1));
   }

   public static UnsignedLong valueOf(BigInteger var0) {
      Preconditions.checkNotNull(var0);
      boolean var1;
      if (var0.signum() >= 0 && var0.bitLength() <= 64) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "value (%s) is outside the range for an unsigned long value", (Object)var0);
      return fromLongBits(var0.longValue());
   }

   public BigInteger bigIntegerValue() {
      BigInteger var1 = BigInteger.valueOf(this.value & Long.MAX_VALUE);
      BigInteger var2 = var1;
      if (this.value < 0L) {
         var2 = var1.setBit(63);
      }

      return var2;
   }

   public int compareTo(UnsignedLong var1) {
      Preconditions.checkNotNull(var1);
      return UnsignedLongs.compare(this.value, var1.value);
   }

   public UnsignedLong dividedBy(UnsignedLong var1) {
      return fromLongBits(UnsignedLongs.divide(this.value, ((UnsignedLong)Preconditions.checkNotNull(var1)).value));
   }

   public double doubleValue() {
      long var1 = this.value;
      double var3 = (double)(Long.MAX_VALUE & var1);
      double var5 = var3;
      if (var1 < 0L) {
         var5 = var3 + 9.223372036854776E18D;
      }

      return var5;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof UnsignedLong;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         UnsignedLong var5 = (UnsignedLong)var1;
         var4 = var3;
         if (this.value == var5.value) {
            var4 = true;
         }
      }

      return var4;
   }

   public float floatValue() {
      long var1 = this.value;
      float var3 = (float)(Long.MAX_VALUE & var1);
      float var4 = var3;
      if (var1 < 0L) {
         var4 = var3 + 9.223372E18F;
      }

      return var4;
   }

   public int hashCode() {
      return Longs.hashCode(this.value);
   }

   public int intValue() {
      return (int)this.value;
   }

   public long longValue() {
      return this.value;
   }

   public UnsignedLong minus(UnsignedLong var1) {
      return fromLongBits(this.value - ((UnsignedLong)Preconditions.checkNotNull(var1)).value);
   }

   public UnsignedLong mod(UnsignedLong var1) {
      return fromLongBits(UnsignedLongs.remainder(this.value, ((UnsignedLong)Preconditions.checkNotNull(var1)).value));
   }

   public UnsignedLong plus(UnsignedLong var1) {
      return fromLongBits(this.value + ((UnsignedLong)Preconditions.checkNotNull(var1)).value);
   }

   public UnsignedLong times(UnsignedLong var1) {
      return fromLongBits(this.value * ((UnsignedLong)Preconditions.checkNotNull(var1)).value);
   }

   public String toString() {
      return UnsignedLongs.toString(this.value);
   }

   public String toString(int var1) {
      return UnsignedLongs.toString(this.value, var1);
   }
}

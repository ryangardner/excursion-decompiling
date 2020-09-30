package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.math.BigInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class UnsignedInteger extends Number implements Comparable<UnsignedInteger> {
   public static final UnsignedInteger MAX_VALUE = fromIntBits(-1);
   public static final UnsignedInteger ONE = fromIntBits(1);
   public static final UnsignedInteger ZERO = fromIntBits(0);
   private final int value;

   private UnsignedInteger(int var1) {
      this.value = var1 & -1;
   }

   public static UnsignedInteger fromIntBits(int var0) {
      return new UnsignedInteger(var0);
   }

   public static UnsignedInteger valueOf(long var0) {
      boolean var2;
      if ((4294967295L & var0) == var0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "value (%s) is outside the range for an unsigned integer value", var0);
      return fromIntBits((int)var0);
   }

   public static UnsignedInteger valueOf(String var0) {
      return valueOf(var0, 10);
   }

   public static UnsignedInteger valueOf(String var0, int var1) {
      return fromIntBits(UnsignedInts.parseUnsignedInt(var0, var1));
   }

   public static UnsignedInteger valueOf(BigInteger var0) {
      Preconditions.checkNotNull(var0);
      boolean var1;
      if (var0.signum() >= 0 && var0.bitLength() <= 32) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "value (%s) is outside the range for an unsigned integer value", (Object)var0);
      return fromIntBits(var0.intValue());
   }

   public BigInteger bigIntegerValue() {
      return BigInteger.valueOf(this.longValue());
   }

   public int compareTo(UnsignedInteger var1) {
      Preconditions.checkNotNull(var1);
      return UnsignedInts.compare(this.value, var1.value);
   }

   public UnsignedInteger dividedBy(UnsignedInteger var1) {
      return fromIntBits(UnsignedInts.divide(this.value, ((UnsignedInteger)Preconditions.checkNotNull(var1)).value));
   }

   public double doubleValue() {
      return (double)this.longValue();
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof UnsignedInteger;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         UnsignedInteger var5 = (UnsignedInteger)var1;
         var4 = var3;
         if (this.value == var5.value) {
            var4 = true;
         }
      }

      return var4;
   }

   public float floatValue() {
      return (float)this.longValue();
   }

   public int hashCode() {
      return this.value;
   }

   public int intValue() {
      return this.value;
   }

   public long longValue() {
      return UnsignedInts.toLong(this.value);
   }

   public UnsignedInteger minus(UnsignedInteger var1) {
      return fromIntBits(this.value - ((UnsignedInteger)Preconditions.checkNotNull(var1)).value);
   }

   public UnsignedInteger mod(UnsignedInteger var1) {
      return fromIntBits(UnsignedInts.remainder(this.value, ((UnsignedInteger)Preconditions.checkNotNull(var1)).value));
   }

   public UnsignedInteger plus(UnsignedInteger var1) {
      return fromIntBits(this.value + ((UnsignedInteger)Preconditions.checkNotNull(var1)).value);
   }

   public UnsignedInteger times(UnsignedInteger var1) {
      return fromIntBits(this.value * ((UnsignedInteger)Preconditions.checkNotNull(var1)).value);
   }

   public String toString() {
      return this.toString(10);
   }

   public String toString(int var1) {
      return UnsignedInts.toString(this.value, var1);
   }
}

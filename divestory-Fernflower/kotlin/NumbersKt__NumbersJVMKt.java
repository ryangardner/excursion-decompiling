package kotlin;

import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0000\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\u0087\b\u001a\u0015\u0010\u0005\u001a\u00020\t*\u00020\n2\u0006\u0010\b\u001a\u00020\u0001H\u0087\b\u001a\r\u0010\u000b\u001a\u00020\f*\u00020\u0006H\u0087\b\u001a\r\u0010\u000b\u001a\u00020\f*\u00020\tH\u0087\b\u001a\r\u0010\r\u001a\u00020\f*\u00020\u0006H\u0087\b\u001a\r\u0010\r\u001a\u00020\f*\u00020\tH\u0087\b\u001a\r\u0010\u000e\u001a\u00020\f*\u00020\u0006H\u0087\b\u001a\r\u0010\u000e\u001a\u00020\f*\u00020\tH\u0087\b\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0001H\u0087\b\u001a\r\u0010\u0012\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0012\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010\u0013\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\r\u0010\u0013\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0002*\u00020\u0006H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0001*\u00020\tH\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0002*\u00020\u0006H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\tH\u0087\b¨\u0006\u0016"},
   d2 = {"countLeadingZeroBits", "", "", "countOneBits", "countTrailingZeroBits", "fromBits", "", "Lkotlin/Double$Companion;", "bits", "", "Lkotlin/Float$Companion;", "isFinite", "", "isInfinite", "isNaN", "rotateLeft", "bitCount", "rotateRight", "takeHighestOneBit", "takeLowestOneBit", "toBits", "toRawBits", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/NumbersKt"
)
class NumbersKt__NumbersJVMKt extends NumbersKt__BigIntegersKt {
   public NumbersKt__NumbersJVMKt() {
   }

   private static final int countLeadingZeroBits(int var0) {
      return Integer.numberOfLeadingZeros(var0);
   }

   private static final int countLeadingZeroBits(long var0) {
      return Long.numberOfLeadingZeros(var0);
   }

   private static final int countOneBits(int var0) {
      return Integer.bitCount(var0);
   }

   private static final int countOneBits(long var0) {
      return Long.bitCount(var0);
   }

   private static final int countTrailingZeroBits(int var0) {
      return Integer.numberOfTrailingZeros(var0);
   }

   private static final int countTrailingZeroBits(long var0) {
      return Long.numberOfTrailingZeros(var0);
   }

   private static final double fromBits(DoubleCompanionObject var0, long var1) {
      return Double.longBitsToDouble(var1);
   }

   private static final float fromBits(FloatCompanionObject var0, int var1) {
      return Float.intBitsToFloat(var1);
   }

   private static final boolean isFinite(double var0) {
      boolean var2;
      if (!Double.isInfinite(var0) && !Double.isNaN(var0)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final boolean isFinite(float var0) {
      boolean var1;
      if (!Float.isInfinite(var0) && !Float.isNaN(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final boolean isInfinite(double var0) {
      return Double.isInfinite(var0);
   }

   private static final boolean isInfinite(float var0) {
      return Float.isInfinite(var0);
   }

   private static final boolean isNaN(double var0) {
      return Double.isNaN(var0);
   }

   private static final boolean isNaN(float var0) {
      return Float.isNaN(var0);
   }

   private static final int rotateLeft(int var0, int var1) {
      return Integer.rotateLeft(var0, var1);
   }

   private static final long rotateLeft(long var0, int var2) {
      return Long.rotateLeft(var0, var2);
   }

   private static final int rotateRight(int var0, int var1) {
      return Integer.rotateRight(var0, var1);
   }

   private static final long rotateRight(long var0, int var2) {
      return Long.rotateRight(var0, var2);
   }

   private static final int takeHighestOneBit(int var0) {
      return Integer.highestOneBit(var0);
   }

   private static final long takeHighestOneBit(long var0) {
      return Long.highestOneBit(var0);
   }

   private static final int takeLowestOneBit(int var0) {
      return Integer.lowestOneBit(var0);
   }

   private static final long takeLowestOneBit(long var0) {
      return Long.lowestOneBit(var0);
   }

   private static final int toBits(float var0) {
      return Float.floatToIntBits(var0);
   }

   private static final long toBits(double var0) {
      return Double.doubleToLongBits(var0);
   }

   private static final int toRawBits(float var0) {
      return Float.floatToRawIntBits(var0);
   }

   private static final long toRawBits(double var0) {
      return Double.doubleToRawLongBits(var0);
   }
}

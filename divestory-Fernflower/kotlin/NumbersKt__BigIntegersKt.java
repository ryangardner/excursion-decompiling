package kotlin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0001H\u0087\b\u001a!\u0010\u0010\u001a\u00020\u0011*\u00020\u00012\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\rH\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0016H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f¨\u0006\u0019"},
   d2 = {"and", "Ljava/math/BigInteger;", "other", "dec", "div", "inc", "inv", "minus", "or", "plus", "rem", "shl", "n", "", "shr", "times", "toBigDecimal", "Ljava/math/BigDecimal;", "scale", "mathContext", "Ljava/math/MathContext;", "toBigInteger", "", "unaryMinus", "xor", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/NumbersKt"
)
class NumbersKt__BigIntegersKt extends NumbersKt__BigDecimalsKt {
   public NumbersKt__BigIntegersKt() {
   }

   private static final BigInteger and(BigInteger var0, BigInteger var1) {
      var0 = var0.and(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.and(other)");
      return var0;
   }

   private static final BigInteger dec(BigInteger var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$dec");
      var0 = var0.subtract(BigInteger.ONE);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.subtract(BigInteger.ONE)");
      return var0;
   }

   private static final BigInteger div(BigInteger var0, BigInteger var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$div");
      var0 = var0.divide(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.divide(other)");
      return var0;
   }

   private static final BigInteger inc(BigInteger var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$inc");
      var0 = var0.add(BigInteger.ONE);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.add(BigInteger.ONE)");
      return var0;
   }

   private static final BigInteger inv(BigInteger var0) {
      var0 = var0.not();
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.not()");
      return var0;
   }

   private static final BigInteger minus(BigInteger var0, BigInteger var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minus");
      var0 = var0.subtract(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.subtract(other)");
      return var0;
   }

   private static final BigInteger or(BigInteger var0, BigInteger var1) {
      var0 = var0.or(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.or(other)");
      return var0;
   }

   private static final BigInteger plus(BigInteger var0, BigInteger var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$plus");
      var0 = var0.add(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.add(other)");
      return var0;
   }

   private static final BigInteger rem(BigInteger var0, BigInteger var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$rem");
      var0 = var0.remainder(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.remainder(other)");
      return var0;
   }

   private static final BigInteger shl(BigInteger var0, int var1) {
      var0 = var0.shiftLeft(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.shiftLeft(n)");
      return var0;
   }

   private static final BigInteger shr(BigInteger var0, int var1) {
      var0 = var0.shiftRight(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.shiftRight(n)");
      return var0;
   }

   private static final BigInteger times(BigInteger var0, BigInteger var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$times");
      var0 = var0.multiply(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.multiply(other)");
      return var0;
   }

   private static final BigDecimal toBigDecimal(BigInteger var0) {
      return new BigDecimal(var0);
   }

   private static final BigDecimal toBigDecimal(BigInteger var0, int var1, MathContext var2) {
      return new BigDecimal(var0, var1, var2);
   }

   // $FF: synthetic method
   static BigDecimal toBigDecimal$default(BigInteger var0, int var1, MathContext var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = MathContext.UNLIMITED;
         Intrinsics.checkExpressionValueIsNotNull(var2, "MathContext.UNLIMITED");
      }

      return new BigDecimal(var0, var1, var2);
   }

   private static final BigInteger toBigInteger(int var0) {
      BigInteger var1 = BigInteger.valueOf((long)var0);
      Intrinsics.checkExpressionValueIsNotNull(var1, "BigInteger.valueOf(this.toLong())");
      return var1;
   }

   private static final BigInteger toBigInteger(long var0) {
      BigInteger var2 = BigInteger.valueOf(var0);
      Intrinsics.checkExpressionValueIsNotNull(var2, "BigInteger.valueOf(this)");
      return var2;
   }

   private static final BigInteger unaryMinus(BigInteger var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$unaryMinus");
      var0 = var0.negate();
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.negate()");
      return var0;
   }

   private static final BigInteger xor(BigInteger var0, BigInteger var1) {
      var0 = var0.xor(var1);
      Intrinsics.checkExpressionValueIsNotNull(var0, "this.xor(other)");
      return var0;
   }
}

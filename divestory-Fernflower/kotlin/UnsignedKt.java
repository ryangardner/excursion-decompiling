package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"},
   d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class UnsignedKt {
   public static final int doubleToUInt(double var0) {
      boolean var2 = Double.isNaN(var0);
      int var3 = -1;
      if (!var2 && var0 > uintToDouble(0)) {
         if (var0 < uintToDouble(-1)) {
            double var4 = (double)Integer.MAX_VALUE;
            if (var0 <= var4) {
               var3 = UInt.constructor-impl((int)var0);
            } else {
               var3 = UInt.constructor-impl(UInt.constructor-impl((int)(var0 - var4)) + UInt.constructor-impl(Integer.MAX_VALUE));
            }
         }
      } else {
         var3 = 0;
      }

      return var3;
   }

   public static final long doubleToULong(double var0) {
      boolean var2 = Double.isNaN(var0);
      long var3 = -1L;
      if (!var2 && var0 > ulongToDouble(0L)) {
         if (var0 < ulongToDouble(-1L)) {
            if (var0 < (double)Long.MAX_VALUE) {
               var3 = ULong.constructor-impl((long)var0);
            } else {
               var3 = ULong.constructor-impl(ULong.constructor-impl((long)(var0 - 9.223372036854776E18D)) - Long.MIN_VALUE);
            }
         }
      } else {
         var3 = 0L;
      }

      return var3;
   }

   public static final int uintCompare(int var0, int var1) {
      return Intrinsics.compare(var0 ^ Integer.MIN_VALUE, var1 ^ Integer.MIN_VALUE);
   }

   public static final int uintDivide_J1ME1BU/* $FF was: uintDivide-J1ME1BU*/(int var0, int var1) {
      return UInt.constructor-impl((int)(((long)var0 & 4294967295L) / ((long)var1 & 4294967295L)));
   }

   public static final int uintRemainder_J1ME1BU/* $FF was: uintRemainder-J1ME1BU*/(int var0, int var1) {
      return UInt.constructor-impl((int)(((long)var0 & 4294967295L) % ((long)var1 & 4294967295L)));
   }

   public static final double uintToDouble(int var0) {
      return (double)(Integer.MAX_VALUE & var0) + (double)(var0 >>> 31 << 30) * (double)2;
   }

   public static final int ulongCompare(long var0, long var2) {
      long var4;
      return (var4 = (var0 ^ Long.MIN_VALUE) - (var2 ^ Long.MIN_VALUE)) == 0L ? 0 : (var4 < 0L ? -1 : 1);
   }

   public static final long ulongDivide_eb3DHEI/* $FF was: ulongDivide-eb3DHEI*/(long var0, long var2) {
      if (var2 < 0L) {
         if (ulongCompare(var0, var2) < 0) {
            var0 = ULong.constructor-impl(0L);
         } else {
            var0 = ULong.constructor-impl(1L);
         }

         return var0;
      } else if (var0 >= 0L) {
         return ULong.constructor-impl(var0 / var2);
      } else {
         byte var4 = 1;
         long var5 = (var0 >>> 1) / var2 << 1;
         if (ulongCompare(ULong.constructor-impl(var0 - var5 * var2), ULong.constructor-impl(var2)) < 0) {
            var4 = 0;
         }

         return ULong.constructor-impl(var5 + (long)var4);
      }
   }

   public static final long ulongRemainder_eb3DHEI/* $FF was: ulongRemainder-eb3DHEI*/(long var0, long var2) {
      if (var2 < 0L) {
         if (ulongCompare(var0, var2) >= 0) {
            var0 = ULong.constructor-impl(var0 - var2);
         }

         return var0;
      } else if (var0 >= 0L) {
         return ULong.constructor-impl(var0 % var2);
      } else {
         var0 -= ((var0 >>> 1) / var2 << 1) * var2;
         if (ulongCompare(ULong.constructor-impl(var0), ULong.constructor-impl(var2)) < 0) {
            var2 = 0L;
         }

         return ULong.constructor-impl(var0 - var2);
      }
   }

   public static final double ulongToDouble(long var0) {
      return (double)(var0 >>> 11) * (double)2048 + (double)(var0 & 2047L);
   }

   public static final String ulongToString(long var0) {
      return ulongToString(var0, 10);
   }

   public static final String ulongToString(long var0, int var2) {
      if (var0 >= 0L) {
         String var13 = Long.toString(var0, CharsKt.checkRadix(var2));
         Intrinsics.checkExpressionValueIsNotNull(var13, "java.lang.Long.toString(this, checkRadix(radix))");
         return var13;
      } else {
         long var4 = (long)var2;
         long var6 = (var0 >>> 1) / var4 << 1;
         long var8 = var0 - var6 * var4;
         long var10 = var6;
         var0 = var8;
         if (var8 >= var4) {
            var0 = var8 - var4;
            var10 = var6 + 1L;
         }

         StringBuilder var3 = new StringBuilder();
         String var12 = Long.toString(var10, CharsKt.checkRadix(var2));
         Intrinsics.checkExpressionValueIsNotNull(var12, "java.lang.Long.toString(this, checkRadix(radix))");
         var3.append(var12);
         var12 = Long.toString(var0, CharsKt.checkRadix(var2));
         Intrinsics.checkExpressionValueIsNotNull(var12, "java.lang.Long.toString(this, checkRadix(radix))");
         var3.append(var12);
         return var3.toString();
      }
   }
}

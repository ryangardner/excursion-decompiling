package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u000b\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\f\u001a\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u000f\u001a\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0010\u001a\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0014¨\u0006\u0015"},
   d2 = {"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringNumberConversionsKt extends StringsKt__StringNumberConversionsJVMKt {
   public StringsKt__StringNumberConversionsKt() {
   }

   public static final Void numberFormatError(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "input");
      StringBuilder var1 = new StringBuilder();
      var1.append("Invalid number format: '");
      var1.append(var0);
      var1.append('\'');
      throw (Throwable)(new NumberFormatException(var1.toString()));
   }

   public static final Byte toByteOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toByteOrNull");
      return StringsKt.toByteOrNull(var0, 10);
   }

   public static final Byte toByteOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toByteOrNull");
      Integer var2 = StringsKt.toIntOrNull(var0, var1);
      if (var2 != null) {
         var1 = var2;
         if (var1 >= -128 && var1 <= 127) {
            return (byte)var1;
         }
      }

      return null;
   }

   public static final Integer toIntOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toIntOrNull");
      return StringsKt.toIntOrNull(var0, 10);
   }

   public static final Integer toIntOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toIntOrNull");
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         int var3 = 0;
         char var4 = var0.charAt(0);
         int var5 = -2147483647;
         int var6 = 1;
         boolean var11;
         if (var4 < '0') {
            if (var2 == 1) {
               return null;
            }

            if (var4 == '-') {
               var5 = Integer.MIN_VALUE;
               var11 = true;
            } else {
               if (var4 != '+') {
                  return null;
               }

               var11 = false;
            }
         } else {
            var11 = false;
            var6 = 0;
         }

         int var7 = -59652323;

         while(true) {
            if (var6 >= var2) {
               Integer var10;
               if (var11) {
                  var10 = var3;
               } else {
                  var10 = -var3;
               }

               return var10;
            }

            int var8 = CharsKt.digitOf(var0.charAt(var6), var1);
            if (var8 < 0) {
               return null;
            }

            int var9 = var7;
            if (var3 < var7) {
               if (var7 != -59652323) {
                  break;
               }

               var7 = var5 / var1;
               var9 = var7;
               if (var3 < var7) {
                  break;
               }
            }

            var7 = var3 * var1;
            if (var7 < var5 + var8) {
               return null;
            }

            var3 = var7 - var8;
            ++var6;
            var7 = var9;
         }

         return null;
      }
   }

   public static final Long toLongOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toLongOrNull");
      return StringsKt.toLongOrNull(var0, 10);
   }

   public static final Long toLongOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toLongOrNull");
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         int var3;
         long var5;
         boolean var7;
         label56: {
            var3 = 0;
            char var4 = var0.charAt(0);
            var5 = -9223372036854775807L;
            var7 = true;
            if (var4 < '0') {
               if (var2 == 1) {
                  return null;
               }

               if (var4 == '-') {
                  var5 = Long.MIN_VALUE;
                  var3 = 1;
                  break label56;
               }

               if (var4 != '+') {
                  return null;
               }

               var3 = 1;
            }

            var7 = false;
         }

         long var8 = 0L;
         long var10 = -256204778801521550L;

         while(true) {
            if (var3 >= var2) {
               Long var14;
               if (var7) {
                  var14 = var8;
               } else {
                  var14 = -var8;
               }

               return var14;
            }

            int var15 = CharsKt.digitOf(var0.charAt(var3), var1);
            if (var15 < 0) {
               return null;
            }

            long var12 = var10;
            if (var8 < var10) {
               if (var10 != -256204778801521550L) {
                  break;
               }

               var10 = var5 / (long)var1;
               var12 = var10;
               if (var8 < var10) {
                  break;
               }
            }

            var8 *= (long)var1;
            var10 = (long)var15;
            if (var8 < var5 + var10) {
               return null;
            }

            var8 -= var10;
            ++var3;
            var10 = var12;
         }

         return null;
      }
   }

   public static final Short toShortOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toShortOrNull");
      return StringsKt.toShortOrNull(var0, 10);
   }

   public static final Short toShortOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toShortOrNull");
      Integer var2 = StringsKt.toIntOrNull(var0, var1);
      if (var2 != null) {
         var1 = var2;
         if (var1 >= -32768 && var1 <= 32767) {
            return (short)var1;
         }
      }

      return null;
   }
}

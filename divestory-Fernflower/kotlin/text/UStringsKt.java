package kotlin.text;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "},
   d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class UStringsKt {
   public static final String toString_JSWoG40/* $FF was: toString-JSWoG40*/(long var0, int var2) {
      return UnsignedKt.ulongToString(var0, CharsKt.checkRadix(var2));
   }

   public static final String toString_LxnNnR4/* $FF was: toString-LxnNnR4*/(byte var0, int var1) {
      String var2 = Integer.toString(var0 & 255, CharsKt.checkRadix(var1));
      Intrinsics.checkExpressionValueIsNotNull(var2, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var2;
   }

   public static final String toString_V7xB4Y4/* $FF was: toString-V7xB4Y4*/(int var0, int var1) {
      String var2 = Long.toString((long)var0 & 4294967295L, CharsKt.checkRadix(var1));
      Intrinsics.checkExpressionValueIsNotNull(var2, "java.lang.Long.toString(this, checkRadix(radix))");
      return var2;
   }

   public static final String toString_olVBNx4/* $FF was: toString-olVBNx4*/(short var0, int var1) {
      String var2 = Integer.toString(var0 & '\uffff', CharsKt.checkRadix(var1));
      Intrinsics.checkExpressionValueIsNotNull(var2, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var2;
   }

   public static final byte toUByte(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUByte");
      UByte var1 = toUByteOrNull(var0);
      if (var1 != null) {
         return var1.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final byte toUByte(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUByte");
      UByte var2 = toUByteOrNull(var0, var1);
      if (var2 != null) {
         return var2.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final UByte toUByteOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUByteOrNull");
      return toUByteOrNull(var0, 10);
   }

   public static final UByte toUByteOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUByteOrNull");
      UInt var2 = toUIntOrNull(var0, var1);
      if (var2 != null) {
         var1 = var2.unbox-impl();
         return UnsignedKt.uintCompare(var1, UInt.constructor-impl(255)) > 0 ? null : UByte.box-impl(UByte.constructor-impl((byte)var1));
      } else {
         return null;
      }
   }

   public static final int toUInt(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUInt");
      UInt var1 = toUIntOrNull(var0);
      if (var1 != null) {
         return var1.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final int toUInt(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUInt");
      UInt var2 = toUIntOrNull(var0, var1);
      if (var2 != null) {
         return var2.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final UInt toUIntOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUIntOrNull");
      return toUIntOrNull(var0, 10);
   }

   public static final UInt toUIntOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUIntOrNull");
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         int var3 = 0;
         char var4 = var0.charAt(0);
         byte var5 = 1;
         if (var4 < '0') {
            if (var2 == 1 || var4 != '+') {
               return null;
            }
         } else {
            var5 = 0;
         }

         int var6 = UInt.constructor-impl(var1);
         int var7 = 119304647;
         int var9 = var5;

         while(true) {
            if (var9 >= var2) {
               return UInt.box-impl(var3);
            }

            int var8 = CharsKt.digitOf(var0.charAt(var9), var1);
            if (var8 < 0) {
               return null;
            }

            int var10 = var7;
            if (UnsignedKt.uintCompare(var3, var7) > 0) {
               if (var7 != 119304647) {
                  break;
               }

               var7 = UnsignedKt.uintDivide-J1ME1BU(-1, var6);
               var10 = var7;
               if (UnsignedKt.uintCompare(var3, var7) > 0) {
                  break;
               }
            }

            var7 = UInt.constructor-impl(var3 * var6);
            var3 = UInt.constructor-impl(UInt.constructor-impl(var8) + var7);
            if (UnsignedKt.uintCompare(var3, var7) < 0) {
               return null;
            }

            ++var9;
            var7 = var10;
         }

         return null;
      }
   }

   public static final long toULong(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toULong");
      ULong var1 = toULongOrNull(var0);
      if (var1 != null) {
         return var1.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final long toULong(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toULong");
      ULong var2 = toULongOrNull(var0, var1);
      if (var2 != null) {
         return var2.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final ULong toULongOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toULongOrNull");
      return toULongOrNull(var0, 10);
   }

   public static final ULong toULongOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toULongOrNull");
      CharsKt.checkRadix(var1);
      int var2 = var0.length();
      if (var2 == 0) {
         return null;
      } else {
         int var3 = 0;
         char var4 = var0.charAt(0);
         if (var4 < '0') {
            if (var2 == 1 || var4 != '+') {
               return null;
            }

            var3 = 1;
         }

         long var5 = ULong.constructor-impl((long)var1);
         long var7 = 0L;
         long var9 = 512409557603043100L;

         while(true) {
            if (var3 >= var2) {
               return ULong.box-impl(var7);
            }

            int var13 = CharsKt.digitOf(var0.charAt(var3), var1);
            if (var13 < 0) {
               return null;
            }

            long var11 = var9;
            if (UnsignedKt.ulongCompare(var7, var9) > 0) {
               if (var9 != 512409557603043100L) {
                  break;
               }

               var9 = UnsignedKt.ulongDivide-eb3DHEI(-1L, var5);
               var11 = var9;
               if (UnsignedKt.ulongCompare(var7, var9) > 0) {
                  break;
               }
            }

            var9 = ULong.constructor-impl(var7 * var5);
            var7 = ULong.constructor-impl(ULong.constructor-impl((long)UInt.constructor-impl(var13) & 4294967295L) + var9);
            if (UnsignedKt.ulongCompare(var7, var9) < 0) {
               return null;
            }

            ++var3;
            var9 = var11;
         }

         return null;
      }
   }

   public static final short toUShort(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUShort");
      UShort var1 = toUShortOrNull(var0);
      if (var1 != null) {
         return var1.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final short toUShort(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUShort");
      UShort var2 = toUShortOrNull(var0, var1);
      if (var2 != null) {
         return var2.unbox-impl();
      } else {
         StringsKt.numberFormatError(var0);
         throw null;
      }
   }

   public static final UShort toUShortOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUShortOrNull");
      return toUShortOrNull(var0, 10);
   }

   public static final UShort toUShortOrNull(String var0, int var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toUShortOrNull");
      UInt var2 = toUIntOrNull(var0, var1);
      if (var2 != null) {
         var1 = var2.unbox-impl();
         return UnsignedKt.uintCompare(var1, UInt.constructor-impl(65535)) > 0 ? null : UShort.box-impl(UShort.constructor-impl((short)var1));
      } else {
         return null;
      }
   }
}

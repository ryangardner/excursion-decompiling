package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a8\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\b¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!¨\u0006\""},
   d2 = {"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/ArraysKt"
)
class ArraysKt__ArraysKt extends ArraysKt__ArraysJVMKt {
   public ArraysKt__ArraysKt() {
   }

   public static final <T> boolean contentDeepEquals(T[] var0, T[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contentDeepEqualsImpl");
      Intrinsics.checkParameterIsNotNull(var1, "other");
      if (var0 == var1) {
         return true;
      } else if (var0.length != var1.length) {
         return false;
      } else {
         int var2 = var0.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Object var4 = var0[var3];
            Object var5 = var1[var3];
            if (var4 != var5) {
               if (var4 == null || var5 == null) {
                  return false;
               }

               if (var4 instanceof Object[] && var5 instanceof Object[]) {
                  if (!ArraysKt.contentDeepEquals((Object[])var4, (Object[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof byte[] && var5 instanceof byte[]) {
                  if (!Arrays.equals((byte[])var4, (byte[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof short[] && var5 instanceof short[]) {
                  if (!Arrays.equals((short[])var4, (short[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof int[] && var5 instanceof int[]) {
                  if (!Arrays.equals((int[])var4, (int[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof long[] && var5 instanceof long[]) {
                  if (!Arrays.equals((long[])var4, (long[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof float[] && var5 instanceof float[]) {
                  if (!Arrays.equals((float[])var4, (float[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof double[] && var5 instanceof double[]) {
                  if (!Arrays.equals((double[])var4, (double[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof char[] && var5 instanceof char[]) {
                  if (!Arrays.equals((char[])var4, (char[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof boolean[] && var5 instanceof boolean[]) {
                  if (!Arrays.equals((boolean[])var4, (boolean[])var5)) {
                     return false;
                  }
               } else if (var4 instanceof UByteArray && var5 instanceof UByteArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-kdPth3s(((UByteArray)var4).unbox-impl(), ((UByteArray)var5).unbox-impl())) {
                     return false;
                  }
               } else if (var4 instanceof UShortArray && var5 instanceof UShortArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-mazbYpA(((UShortArray)var4).unbox-impl(), ((UShortArray)var5).unbox-impl())) {
                     return false;
                  }
               } else if (var4 instanceof UIntArray && var5 instanceof UIntArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-ctEhBpI(((UIntArray)var4).unbox-impl(), ((UIntArray)var5).unbox-impl())) {
                     return false;
                  }
               } else if (var4 instanceof ULongArray && var5 instanceof ULongArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-us8wMrg(((ULongArray)var4).unbox-impl(), ((ULongArray)var5).unbox-impl())) {
                     return false;
                  }
               } else if (Intrinsics.areEqual(var4, var5) ^ true) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static final <T> String contentDeepToString(T[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$contentDeepToStringImpl");
      StringBuilder var1 = new StringBuilder(RangesKt.coerceAtMost(var0.length, 429496729) * 5 + 2);
      contentDeepToStringInternal$ArraysKt__ArraysKt(var0, var1, (List)(new ArrayList()));
      String var2 = var1.toString();
      Intrinsics.checkExpressionValueIsNotNull(var2, "StringBuilder(capacity).…builderAction).toString()");
      return var2;
   }

   private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] var0, StringBuilder var1, List<Object[]> var2) {
      if (var2.contains(var0)) {
         var1.append("[...]");
      } else {
         var2.add(var0);
         var1.append('[');
         int var3 = 0;

         for(int var4 = var0.length; var3 < var4; ++var3) {
            if (var3 != 0) {
               var1.append(", ");
            }

            Object var5 = var0[var3];
            if (var5 == null) {
               var1.append("null");
            } else if (var5 instanceof Object[]) {
               contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])var5, var1, var2);
            } else {
               String var6;
               if (var5 instanceof byte[]) {
                  var6 = Arrays.toString((byte[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof short[]) {
                  var6 = Arrays.toString((short[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof int[]) {
                  var6 = Arrays.toString((int[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof long[]) {
                  var6 = Arrays.toString((long[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof float[]) {
                  var6 = Arrays.toString((float[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof double[]) {
                  var6 = Arrays.toString((double[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof char[]) {
                  var6 = Arrays.toString((char[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof boolean[]) {
                  var6 = Arrays.toString((boolean[])var5);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "java.util.Arrays.toString(this)");
                  var1.append(var6);
               } else if (var5 instanceof UByteArray) {
                  var1.append(kotlin.collections.unsigned.UArraysKt.contentToString-GBYM_sE(((UByteArray)var5).unbox-impl()));
               } else if (var5 instanceof UShortArray) {
                  var1.append(kotlin.collections.unsigned.UArraysKt.contentToString-rL5Bavg(((UShortArray)var5).unbox-impl()));
               } else if (var5 instanceof UIntArray) {
                  var1.append(kotlin.collections.unsigned.UArraysKt.contentToString--ajY-9A(((UIntArray)var5).unbox-impl()));
               } else if (var5 instanceof ULongArray) {
                  var1.append(kotlin.collections.unsigned.UArraysKt.contentToString-QwZRm1k(((ULongArray)var5).unbox-impl()));
               } else {
                  var1.append(var5.toString());
               }
            }
         }

         var1.append(']');
         var2.remove(CollectionsKt.getLastIndex(var2));
      }
   }

   public static final <T> List<T> flatten(T[][] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$flatten");
      Object[] var1 = (Object[])var0;
      int var2 = var1.length;
      byte var3 = 0;
      int var4 = 0;

      int var5;
      for(var5 = 0; var4 < var2; ++var4) {
         var5 += ((Object[])var1[var4]).length;
      }

      ArrayList var7 = new ArrayList(var5);
      var5 = var0.length;

      for(var4 = var3; var4 < var5; ++var4) {
         Object[] var6 = var0[var4];
         CollectionsKt.addAll((Collection)var7, var6);
      }

      return (List)var7;
   }

   private static final <C extends Object[] & R, R> R ifEmpty(C var0, Function0<? extends R> var1) {
      boolean var2;
      if (((Object[])var0).length == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0 = var1.invoke();
      }

      return var0;
   }

   private static final boolean isNullOrEmpty(Object[] var0) {
      boolean var1 = false;
      if (var0 != null) {
         boolean var2;
         if (var0.length == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            return var1;
         }
      }

      var1 = true;
      return var1;
   }

   public static final <T, R> Pair<List<T>, List<R>> unzip(Pair<? extends T, ? extends R>[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$unzip");
      ArrayList var1 = new ArrayList(var0.length);
      ArrayList var2 = new ArrayList(var0.length);
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Pair var5 = var0[var4];
         var1.add(var5.getFirst());
         var2.add(var5.getSecond());
      }

      return TuplesKt.to(var1, var2);
   }
}

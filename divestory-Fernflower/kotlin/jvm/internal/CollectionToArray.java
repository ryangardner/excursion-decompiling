package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007¢\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007¢\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b¢\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class CollectionToArray {
   private static final Object[] EMPTY = new Object[0];
   private static final int MAX_SIZE = 2147483645;

   public static final Object[] toArray(Collection<?> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "collection");
      int var1 = var0.size();
      Object[] var6;
      if (var1 != 0) {
         Iterator var2 = var0.iterator();
         if (var2.hasNext()) {
            var6 = new Object[var1];
            var1 = 0;

            while(true) {
               int var3 = var1 + 1;
               var6[var1] = var2.next();
               Object[] var5;
               if (var3 >= var6.length) {
                  if (!var2.hasNext()) {
                     return var6;
                  }

                  int var4 = var3 * 3 + 1 >>> 1;
                  var1 = var4;
                  if (var4 <= var3) {
                     if (var3 >= 2147483645) {
                        throw (Throwable)(new OutOfMemoryError());
                     }

                     var1 = 2147483645;
                  }

                  var5 = Arrays.copyOf(var6, var1);
                  Intrinsics.checkExpressionValueIsNotNull(var5, "Arrays.copyOf(result, newSize)");
               } else {
                  var5 = var6;
                  if (!var2.hasNext()) {
                     var6 = Arrays.copyOf(var6, var3);
                     Intrinsics.checkExpressionValueIsNotNull(var6, "Arrays.copyOf(result, size)");
                     return var6;
                  }
               }

               var1 = var3;
               var6 = var5;
            }
         }
      }

      var6 = EMPTY;
      return var6;
   }

   public static final Object[] toArray(Collection<?> var0, Object[] var1) {
      Intrinsics.checkParameterIsNotNull(var0, "collection");
      if (var1 == null) {
         throw (Throwable)(new NullPointerException());
      } else {
         int var2 = var0.size();
         int var3 = 0;
         Object[] var7;
         if (var2 == 0) {
            var7 = var1;
            if (var1.length > 0) {
               var1[0] = null;
               var7 = var1;
            }
         } else {
            Iterator var4 = var0.iterator();
            if (!var4.hasNext()) {
               var7 = var1;
               if (var1.length > 0) {
                  var1[0] = null;
                  var7 = var1;
               }
            } else {
               if (var2 <= var1.length) {
                  var7 = var1;
               } else {
                  Object var8 = Array.newInstance(var1.getClass().getComponentType(), var2);
                  if (var8 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                  }

                  var7 = (Object[])var8;
               }

               while(true) {
                  var2 = var3 + 1;
                  var7[var3] = var4.next();
                  Object[] var6;
                  if (var2 >= var7.length) {
                     if (!var4.hasNext()) {
                        break;
                     }

                     int var5 = var2 * 3 + 1 >>> 1;
                     var3 = var5;
                     if (var5 <= var2) {
                        if (var2 >= 2147483645) {
                           throw (Throwable)(new OutOfMemoryError());
                        }

                        var3 = 2147483645;
                     }

                     var6 = Arrays.copyOf(var7, var3);
                     Intrinsics.checkExpressionValueIsNotNull(var6, "Arrays.copyOf(result, newSize)");
                  } else {
                     var6 = var7;
                     if (!var4.hasNext()) {
                        if (var7 == var1) {
                           var1[var2] = null;
                           var7 = var1;
                        } else {
                           var7 = Arrays.copyOf(var7, var2);
                           Intrinsics.checkExpressionValueIsNotNull(var7, "Arrays.copyOf(result, size)");
                        }
                        break;
                     }
                  }

                  var3 = var2;
                  var7 = var6;
               }
            }
         }

         return var7;
      }
   }

   private static final Object[] toArrayImpl(Collection<?> var0, Function0<Object[]> var1, Function1<? super Integer, Object[]> var2, Function2<? super Object[], ? super Integer, Object[]> var3) {
      int var4 = var0.size();
      if (var4 == 0) {
         return (Object[])var1.invoke();
      } else {
         Iterator var5 = var0.iterator();
         if (!var5.hasNext()) {
            return (Object[])var1.invoke();
         } else {
            Object[] var8 = (Object[])var2.invoke(var4);
            var4 = 0;

            while(true) {
               int var6 = var4 + 1;
               var8[var4] = var5.next();
               Object[] var9;
               if (var6 >= var8.length) {
                  if (!var5.hasNext()) {
                     return var8;
                  }

                  int var7 = var6 * 3 + 1 >>> 1;
                  var4 = var7;
                  if (var7 <= var6) {
                     if (var6 >= 2147483645) {
                        throw (Throwable)(new OutOfMemoryError());
                     }

                     var4 = 2147483645;
                  }

                  var9 = Arrays.copyOf(var8, var4);
                  Intrinsics.checkExpressionValueIsNotNull(var9, "Arrays.copyOf(result, newSize)");
               } else {
                  var9 = var8;
                  if (!var5.hasNext()) {
                     return (Object[])var3.invoke(var8, var6);
                  }
               }

               var4 = var6;
               var8 = var9;
            }
         }
      }
   }
}

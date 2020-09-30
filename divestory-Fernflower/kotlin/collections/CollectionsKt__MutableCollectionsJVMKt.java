package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ReplaceWith;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0006\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u001a!\u0010\u0006\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0087\b\u001a\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0007\u001a&\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a \u0010\f\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a3\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000fH\u0087\b\u001a5\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0013H\u0087\b\u001a2\u0010\u0014\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0013¨\u0006\u0015"},
   d2 = {"fill", "", "T", "", "value", "(Ljava/util/List;Ljava/lang/Object;)V", "shuffle", "random", "Ljava/util/Random;", "shuffled", "", "", "sort", "", "comparison", "Lkotlin/Function2;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "sortWith", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__MutableCollectionsJVMKt extends CollectionsKt__IteratorsKt {
   public CollectionsKt__MutableCollectionsJVMKt() {
   }

   private static final <T> void fill(List<T> var0, T var1) {
      Collections.fill(var0, var1);
   }

   private static final <T> void shuffle(List<T> var0) {
      Collections.shuffle(var0);
   }

   private static final <T> void shuffle(List<T> var0, Random var1) {
      Collections.shuffle(var0, var1);
   }

   public static final <T> List<T> shuffled(Iterable<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$shuffled");
      List var1 = CollectionsKt.toMutableList(var0);
      Collections.shuffle(var1);
      return var1;
   }

   public static final <T> List<T> shuffled(Iterable<? extends T> var0, Random var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$shuffled");
      Intrinsics.checkParameterIsNotNull(var1, "random");
      List var2 = CollectionsKt.toMutableList(var0);
      Collections.shuffle(var2, var1);
      return var2;
   }

   public static final <T extends Comparable<? super T>> void sort(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sort");
      if (var0.size() > 1) {
         Collections.sort(var0);
      }

   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "Use sortWith(comparator) instead.",
      replaceWith = @ReplaceWith(
   expression = "this.sortWith(comparator)",
   imports = {}
)
   )
   private static final <T> void sort(List<T> var0, Comparator<? super T> var1) {
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "Use sortWith(Comparator(comparison)) instead.",
      replaceWith = @ReplaceWith(
   expression = "this.sortWith(Comparator(comparison))",
   imports = {}
)
   )
   private static final <T> void sort(List<T> var0, Function2<? super T, ? super T, Integer> var1) {
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   public static final <T> void sortWith(List<T> var0, Comparator<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$sortWith");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      if (var0.size() > 1) {
         Collections.sort(var0, var1);
      }

   }
}

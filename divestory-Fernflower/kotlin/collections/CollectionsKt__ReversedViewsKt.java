package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007¢\u0006\u0002\b\u0004\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\b\u001a\u001d\u0010\t\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\n¨\u0006\u000b"},
   d2 = {"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__ReversedViewsKt extends CollectionsKt__MutableCollectionsKt {
   public CollectionsKt__ReversedViewsKt() {
   }

   // $FF: synthetic method
   public static final int access$reverseElementIndex(List var0, int var1) {
      return reverseElementIndex$CollectionsKt__ReversedViewsKt(var0, var1);
   }

   // $FF: synthetic method
   public static final int access$reversePositionIndex(List var0, int var1) {
      return reversePositionIndex$CollectionsKt__ReversedViewsKt(var0, var1);
   }

   public static final <T> List<T> asReversed(List<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asReversed");
      return (List)(new ReversedListReadOnly(var0));
   }

   public static final <T> List<T> asReversedMutable(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$asReversed");
      return (List)(new ReversedList(var0));
   }

   private static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(List<?> var0, int var1) {
      int var2 = CollectionsKt.getLastIndex(var0);
      if (var1 >= 0 && var2 >= var1) {
         return CollectionsKt.getLastIndex(var0) - var1;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Element index ");
         var3.append(var1);
         var3.append(" must be in range [");
         var3.append(new IntRange(0, CollectionsKt.getLastIndex(var0)));
         var3.append("].");
         throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
      }
   }

   private static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(List<?> var0, int var1) {
      int var2 = var0.size();
      if (var1 >= 0 && var2 >= var1) {
         return var0.size() - var1;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Position index ");
         var3.append(var1);
         var3.append(" must be in range [");
         var3.append(new IntRange(0, var0.size()));
         var3.append("].");
         throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
      }
   }
}

package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000>\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001aA\u0010\u0006\u001a\u0002H\u0007\"\u0010\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\t\u001a\u0002H\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005¢\u0006\u0002\u0010\n\u001a\u0016\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u000e\u001a&\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\r0\u0010\"\u000e\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u0011*\b\u0012\u0004\u0012\u0002H\r0\u0003\u001a8\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\r0\u0010\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u00032\u001a\u0010\u0012\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\r0\u0013j\n\u0012\u0006\b\u0000\u0012\u0002H\r`\u0014¨\u0006\u0015"},
   d2 = {"filterIsInstance", "", "R", "", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Ljava/lang/Iterable;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "reverse", "", "T", "", "toSortedSet", "Ljava/util/SortedSet;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt___CollectionsJvmKt extends CollectionsKt__ReversedViewsKt {
   public CollectionsKt___CollectionsJvmKt() {
   }

   public static final <R> List<R> filterIsInstance(Iterable<?> var0, Class<R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterIsInstance");
      Intrinsics.checkParameterIsNotNull(var1, "klass");
      return (List)CollectionsKt.filterIsInstanceTo(var0, (Collection)(new ArrayList()), var1);
   }

   public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(Iterable<?> var0, C var1, Class<R> var2) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterIsInstanceTo");
      Intrinsics.checkParameterIsNotNull(var1, "destination");
      Intrinsics.checkParameterIsNotNull(var2, "klass");
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (var2.isInstance(var4)) {
            var1.add(var4);
         }
      }

      return var1;
   }

   public static final <T> void reverse(List<T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$reverse");
      Collections.reverse(var0);
   }

   public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(Iterable<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSortedSet");
      return (SortedSet)CollectionsKt.toCollection(var0, (Collection)(new TreeSet()));
   }

   public static final <T> SortedSet<T> toSortedSet(Iterable<? extends T> var0, Comparator<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSortedSet");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      return (SortedSet)CollectionsKt.toCollection(var0, (Collection)(new TreeSet(var1)));
   }
}

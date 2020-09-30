package kotlin.sequences;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001aA\u0010\u0005\u001a\u0002H\u0006\"\u0010\b\u0000\u0010\u0006*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0007\"\u0004\b\u0001\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\b\u001a\u0002H\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004¢\u0006\u0002\u0010\t\u001a&\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u000e\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\r*\b\u0012\u0004\u0012\u0002H\f0\u0001\u001a8\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\u00012\u001a\u0010\u000e\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\f0\u000fj\n\u0012\u0006\b\u0000\u0012\u0002H\f`\u0010¨\u0006\u0011"},
   d2 = {"filterIsInstance", "Lkotlin/sequences/Sequence;", "R", "klass", "Ljava/lang/Class;", "filterIsInstanceTo", "C", "", "destination", "(Lkotlin/sequences/Sequence;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "toSortedSet", "Ljava/util/SortedSet;", "T", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/sequences/SequencesKt"
)
class SequencesKt___SequencesJvmKt extends SequencesKt__SequencesKt {
   public SequencesKt___SequencesJvmKt() {
   }

   public static final <R> Sequence<R> filterIsInstance(Sequence<?> var0, final Class<R> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$filterIsInstance");
      Intrinsics.checkParameterIsNotNull(var1, "klass");
      var0 = SequencesKt.filter(var0, (Function1)(new Function1<Object, Boolean>() {
         public final boolean invoke(Object var1x) {
            return var1.isInstance(var1x);
         }
      }));
      if (var0 != null) {
         return var0;
      } else {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.sequences.Sequence<R>");
      }
   }

   public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(Sequence<?> var0, C var1, Class<R> var2) {
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

   public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(Sequence<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSortedSet");
      return (SortedSet)SequencesKt.toCollection(var0, (Collection)(new TreeSet()));
   }

   public static final <T> SortedSet<T> toSortedSet(Sequence<? extends T> var0, Comparator<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toSortedSet");
      Intrinsics.checkParameterIsNotNull(var1, "comparator");
      return (SortedSet)SequencesKt.toCollection(var0, (Collection)(new TreeSet(var1)));
   }
}

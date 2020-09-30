package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.SortedSet;

final class SortedIterables {
   private SortedIterables() {
   }

   public static <E> Comparator<? super E> comparator(SortedSet<E> var0) {
      Comparator var1 = var0.comparator();
      Object var2 = var1;
      if (var1 == null) {
         var2 = Ordering.natural();
      }

      return (Comparator)var2;
   }

   public static boolean hasSameComparator(Comparator<?> var0, Iterable<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Comparator var2;
      if (var1 instanceof SortedSet) {
         var2 = comparator((SortedSet)var1);
      } else {
         if (!(var1 instanceof SortedIterable)) {
            return false;
         }

         var2 = ((SortedIterable)var1).comparator();
      }

      return var0.equals(var2);
   }
}

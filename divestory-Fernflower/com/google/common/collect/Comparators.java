package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Iterator;

public final class Comparators {
   private Comparators() {
   }

   public static <T> boolean isInOrder(Iterable<? extends T> var0, Comparator<T> var1) {
      Preconditions.checkNotNull(var1);
      Iterator var2 = var0.iterator();
      Object var3;
      if (var2.hasNext()) {
         for(Object var4 = var2.next(); var2.hasNext(); var4 = var3) {
            var3 = var2.next();
            if (var1.compare(var4, var3) > 0) {
               return false;
            }
         }
      }

      return true;
   }

   public static <T> boolean isInStrictOrder(Iterable<? extends T> var0, Comparator<T> var1) {
      Preconditions.checkNotNull(var1);
      Iterator var2 = var0.iterator();
      Object var3;
      if (var2.hasNext()) {
         for(Object var4 = var2.next(); var2.hasNext(); var4 = var3) {
            var3 = var2.next();
            if (var1.compare(var4, var3) >= 0) {
               return false;
            }
         }
      }

      return true;
   }

   public static <T, S extends T> Comparator<Iterable<S>> lexicographical(Comparator<T> var0) {
      return new LexicographicalOrdering((Comparator)Preconditions.checkNotNull(var0));
   }
}

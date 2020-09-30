package com.google.common.collect;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class LexicographicalOrdering<T> extends Ordering<Iterable<T>> implements Serializable {
   private static final long serialVersionUID = 0L;
   final Comparator<? super T> elementOrder;

   LexicographicalOrdering(Comparator<? super T> var1) {
      this.elementOrder = var1;
   }

   public int compare(Iterable<T> var1, Iterable<T> var2) {
      Iterator var4 = var1.iterator();
      Iterator var5 = var2.iterator();

      int var3;
      do {
         if (!var4.hasNext()) {
            if (var5.hasNext()) {
               return -1;
            }

            return 0;
         }

         if (!var5.hasNext()) {
            return 1;
         }

         var3 = this.elementOrder.compare(var4.next(), var5.next());
      } while(var3 == 0);

      return var3;
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof LexicographicalOrdering) {
         LexicographicalOrdering var2 = (LexicographicalOrdering)var1;
         return this.elementOrder.equals(var2.elementOrder);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.elementOrder.hashCode() ^ 2075626741;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.elementOrder);
      var1.append(".lexicographical()");
      return var1.toString();
   }
}

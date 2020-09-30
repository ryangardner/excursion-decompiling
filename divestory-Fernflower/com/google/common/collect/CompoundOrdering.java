package com.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

final class CompoundOrdering<T> extends Ordering<T> implements Serializable {
   private static final long serialVersionUID = 0L;
   final Comparator<? super T>[] comparators;

   CompoundOrdering(Iterable<? extends Comparator<? super T>> var1) {
      this.comparators = (Comparator[])Iterables.toArray(var1, (Object[])(new Comparator[0]));
   }

   CompoundOrdering(Comparator<? super T> var1, Comparator<? super T> var2) {
      this.comparators = (Comparator[])(new Comparator[]{var1, var2});
   }

   public int compare(T var1, T var2) {
      int var3 = 0;

      while(true) {
         Comparator[] var4 = this.comparators;
         if (var3 >= var4.length) {
            return 0;
         }

         int var5 = var4[var3].compare(var1, var2);
         if (var5 != 0) {
            return var5;
         }

         ++var3;
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof CompoundOrdering) {
         CompoundOrdering var2 = (CompoundOrdering)var1;
         return Arrays.equals(this.comparators, var2.comparators);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.comparators);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Ordering.compound(");
      var1.append(Arrays.toString(this.comparators));
      var1.append(")");
      return var1.toString();
   }
}

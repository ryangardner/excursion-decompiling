package com.google.common.collect;

import java.io.Serializable;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ExplicitOrdering<T> extends Ordering<T> implements Serializable {
   private static final long serialVersionUID = 0L;
   final ImmutableMap<T, Integer> rankMap;

   ExplicitOrdering(ImmutableMap<T, Integer> var1) {
      this.rankMap = var1;
   }

   ExplicitOrdering(List<T> var1) {
      this(Maps.indexMap(var1));
   }

   private int rank(T var1) {
      Integer var2 = (Integer)this.rankMap.get(var1);
      if (var2 != null) {
         return var2;
      } else {
         throw new Ordering.IncomparableValueException(var1);
      }
   }

   public int compare(T var1, T var2) {
      return this.rank(var1) - this.rank(var2);
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 instanceof ExplicitOrdering) {
         ExplicitOrdering var2 = (ExplicitOrdering)var1;
         return this.rankMap.equals(var2.rankMap);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.rankMap.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Ordering.explicit(");
      var1.append(this.rankMap.keySet());
      var1.append(")");
      return var1.toString();
   }
}

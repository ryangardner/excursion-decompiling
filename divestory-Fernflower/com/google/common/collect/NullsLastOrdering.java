package com.google.common.collect;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class NullsLastOrdering<T> extends Ordering<T> implements Serializable {
   private static final long serialVersionUID = 0L;
   final Ordering<? super T> ordering;

   NullsLastOrdering(Ordering<? super T> var1) {
      this.ordering = var1;
   }

   public int compare(@NullableDecl T var1, @NullableDecl T var2) {
      if (var1 == var2) {
         return 0;
      } else if (var1 == null) {
         return 1;
      } else {
         return var2 == null ? -1 : this.ordering.compare(var1, var2);
      }
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof NullsLastOrdering) {
         NullsLastOrdering var2 = (NullsLastOrdering)var1;
         return this.ordering.equals(var2.ordering);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.ordering.hashCode() ^ -921210296;
   }

   public <S extends T> Ordering<S> nullsFirst() {
      return this.ordering.nullsFirst();
   }

   public <S extends T> Ordering<S> nullsLast() {
      return this;
   }

   public <S extends T> Ordering<S> reverse() {
      return this.ordering.reverse().nullsFirst();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.ordering);
      var1.append(".nullsLast()");
      return var1.toString();
   }
}

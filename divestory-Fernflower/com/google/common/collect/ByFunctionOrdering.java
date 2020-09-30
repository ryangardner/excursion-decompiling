package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ByFunctionOrdering<F, T> extends Ordering<F> implements Serializable {
   private static final long serialVersionUID = 0L;
   final Function<F, ? extends T> function;
   final Ordering<T> ordering;

   ByFunctionOrdering(Function<F, ? extends T> var1, Ordering<T> var2) {
      this.function = (Function)Preconditions.checkNotNull(var1);
      this.ordering = (Ordering)Preconditions.checkNotNull(var2);
   }

   public int compare(F var1, F var2) {
      return this.ordering.compare(this.function.apply(var1), this.function.apply(var2));
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ByFunctionOrdering)) {
         return false;
      } else {
         ByFunctionOrdering var3 = (ByFunctionOrdering)var1;
         if (!this.function.equals(var3.function) || !this.ordering.equals(var3.ordering)) {
            var2 = false;
         }

         return var2;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.function, this.ordering);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.ordering);
      var1.append(".onResultOf(");
      var1.append(this.function);
      var1.append(")");
      return var1.toString();
   }
}

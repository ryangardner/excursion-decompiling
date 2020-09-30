package com.google.common.base;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class FunctionalEquivalence<F, T> extends Equivalence<F> implements Serializable {
   private static final long serialVersionUID = 0L;
   private final Function<F, ? extends T> function;
   private final Equivalence<T> resultEquivalence;

   FunctionalEquivalence(Function<F, ? extends T> var1, Equivalence<T> var2) {
      this.function = (Function)Preconditions.checkNotNull(var1);
      this.resultEquivalence = (Equivalence)Preconditions.checkNotNull(var2);
   }

   protected boolean doEquivalent(F var1, F var2) {
      return this.resultEquivalence.equivalent(this.function.apply(var1), this.function.apply(var2));
   }

   protected int doHash(F var1) {
      return this.resultEquivalence.hash(this.function.apply(var1));
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof FunctionalEquivalence)) {
         return false;
      } else {
         FunctionalEquivalence var3 = (FunctionalEquivalence)var1;
         if (!this.function.equals(var3.function) || !this.resultEquivalence.equals(var3.resultEquivalence)) {
            var2 = false;
         }

         return var2;
      }
   }

   public int hashCode() {
      return Objects.hashCode(this.function, this.resultEquivalence);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.resultEquivalence);
      var1.append(".onResultOf(");
      var1.append(this.function);
      var1.append(")");
      return var1.toString();
   }
}

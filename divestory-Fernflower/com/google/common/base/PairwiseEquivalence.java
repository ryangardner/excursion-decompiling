package com.google.common.base;

import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class PairwiseEquivalence<T> extends Equivalence<Iterable<T>> implements Serializable {
   private static final long serialVersionUID = 1L;
   final Equivalence<? super T> elementEquivalence;

   PairwiseEquivalence(Equivalence<? super T> var1) {
      this.elementEquivalence = (Equivalence)Preconditions.checkNotNull(var1);
   }

   protected boolean doEquivalent(Iterable<T> var1, Iterable<T> var2) {
      Iterator var5 = var1.iterator();
      Iterator var6 = var2.iterator();

      do {
         boolean var3 = var5.hasNext();
         boolean var4 = false;
         if (!var3 || !var6.hasNext()) {
            var3 = var4;
            if (!var5.hasNext()) {
               var3 = var4;
               if (!var6.hasNext()) {
                  var3 = true;
               }
            }

            return var3;
         }
      } while(this.elementEquivalence.equivalent(var5.next(), var6.next()));

      return false;
   }

   protected int doHash(Iterable<T> var1) {
      Iterator var2 = var1.iterator();

      int var3;
      Object var4;
      for(var3 = 78721; var2.hasNext(); var3 = var3 * 24943 + this.elementEquivalence.hash(var4)) {
         var4 = var2.next();
      }

      return var3;
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 instanceof PairwiseEquivalence) {
         PairwiseEquivalence var2 = (PairwiseEquivalence)var1;
         return this.elementEquivalence.equals(var2.elementEquivalence);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.elementEquivalence.hashCode() ^ 1185147655;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.elementEquivalence);
      var1.append(".pairwise()");
      return var1.toString();
   }
}

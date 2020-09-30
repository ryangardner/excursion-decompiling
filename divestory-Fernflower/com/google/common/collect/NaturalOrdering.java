package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

final class NaturalOrdering extends Ordering<Comparable> implements Serializable {
   static final NaturalOrdering INSTANCE = new NaturalOrdering();
   private static final long serialVersionUID = 0L;
   @MonotonicNonNullDecl
   private transient Ordering<Comparable> nullsFirst;
   @MonotonicNonNullDecl
   private transient Ordering<Comparable> nullsLast;

   private NaturalOrdering() {
   }

   private Object readResolve() {
      return INSTANCE;
   }

   public int compare(Comparable var1, Comparable var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      return var1.compareTo(var2);
   }

   public <S extends Comparable> Ordering<S> nullsFirst() {
      Ordering var1 = this.nullsFirst;
      Ordering var2 = var1;
      if (var1 == null) {
         var2 = super.nullsFirst();
         this.nullsFirst = var2;
      }

      return var2;
   }

   public <S extends Comparable> Ordering<S> nullsLast() {
      Ordering var1 = this.nullsLast;
      Ordering var2 = var1;
      if (var1 == null) {
         var2 = super.nullsLast();
         this.nullsLast = var2;
      }

      return var2;
   }

   public <S extends Comparable> Ordering<S> reverse() {
      return ReverseNaturalOrdering.INSTANCE;
   }

   public String toString() {
      return "Ordering.natural()";
   }
}

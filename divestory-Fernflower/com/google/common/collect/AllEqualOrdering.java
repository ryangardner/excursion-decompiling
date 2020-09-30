package com.google.common.collect;

import java.io.Serializable;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class AllEqualOrdering extends Ordering<Object> implements Serializable {
   static final AllEqualOrdering INSTANCE = new AllEqualOrdering();
   private static final long serialVersionUID = 0L;

   private Object readResolve() {
      return INSTANCE;
   }

   public int compare(@NullableDecl Object var1, @NullableDecl Object var2) {
      return 0;
   }

   public <E> ImmutableList<E> immutableSortedCopy(Iterable<E> var1) {
      return ImmutableList.copyOf(var1);
   }

   public <S> Ordering<S> reverse() {
      return this;
   }

   public <E> List<E> sortedCopy(Iterable<E> var1) {
      return Lists.newArrayList(var1);
   }

   public String toString() {
      return "Ordering.allEqual()";
   }
}

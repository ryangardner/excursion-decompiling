package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E> {
   protected ForwardingSet() {
   }

   protected abstract Set<E> delegate();

   public boolean equals(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != this && !this.delegate().equals(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   protected boolean standardEquals(@NullableDecl Object var1) {
      return Sets.equalsImpl(this, var1);
   }

   protected int standardHashCode() {
      return Sets.hashCodeImpl(this);
   }

   protected boolean standardRemoveAll(Collection<?> var1) {
      return Sets.removeAllImpl(this, (Collection)((Collection)Preconditions.checkNotNull(var1)));
   }
}

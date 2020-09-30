package com.google.common.collect;

import java.util.ListIterator;

public abstract class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E> {
   protected UnmodifiableListIterator() {
   }

   @Deprecated
   public final void add(E var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final void set(E var1) {
      throw new UnsupportedOperationException();
   }
}

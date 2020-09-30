package com.google.common.base;

import java.lang.ref.PhantomReference;

public abstract class FinalizablePhantomReference<T> extends PhantomReference<T> implements FinalizableReference {
   protected FinalizablePhantomReference(T var1, FinalizableReferenceQueue var2) {
      super(var1, var2.queue);
      var2.cleanUp();
   }
}

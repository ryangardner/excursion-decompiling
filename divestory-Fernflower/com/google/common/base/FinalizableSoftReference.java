package com.google.common.base;

import java.lang.ref.SoftReference;

public abstract class FinalizableSoftReference<T> extends SoftReference<T> implements FinalizableReference {
   protected FinalizableSoftReference(T var1, FinalizableReferenceQueue var2) {
      super(var1, var2.queue);
      var2.cleanUp();
   }
}

package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface FutureCallback<V> {
   void onFailure(Throwable var1);

   void onSuccess(@NullableDecl V var1);
}

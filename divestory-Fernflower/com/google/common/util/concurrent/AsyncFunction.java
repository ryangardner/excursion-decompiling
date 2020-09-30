package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface AsyncFunction<I, O> {
   ListenableFuture<O> apply(@NullableDecl I var1) throws Exception;
}

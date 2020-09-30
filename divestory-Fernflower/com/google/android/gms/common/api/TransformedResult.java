package com.google.android.gms.common.api;

public abstract class TransformedResult<R extends Result> {
   public abstract void andFinally(ResultCallbacks<? super R> var1);

   public abstract <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> var1);
}

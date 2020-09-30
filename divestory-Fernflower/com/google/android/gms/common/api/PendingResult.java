package com.google.android.gms.common.api;

import java.util.concurrent.TimeUnit;

public abstract class PendingResult<R extends Result> {
   public void addStatusListener(PendingResult.StatusListener var1) {
      throw new UnsupportedOperationException();
   }

   public abstract R await();

   public abstract R await(long var1, TimeUnit var3);

   public abstract void cancel();

   public abstract boolean isCanceled();

   public abstract void setResultCallback(ResultCallback<? super R> var1);

   public abstract void setResultCallback(ResultCallback<? super R> var1, long var2, TimeUnit var4);

   public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> var1) {
      throw new UnsupportedOperationException();
   }

   public interface StatusListener {
      void onComplete(Status var1);
   }
}

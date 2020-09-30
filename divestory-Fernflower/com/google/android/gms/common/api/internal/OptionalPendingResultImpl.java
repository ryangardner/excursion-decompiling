package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import java.util.concurrent.TimeUnit;

public final class OptionalPendingResultImpl<R extends Result> extends OptionalPendingResult<R> {
   private final BasePendingResult<R> zaa;

   public OptionalPendingResultImpl(PendingResult<R> var1) {
      this.zaa = (BasePendingResult)var1;
   }

   public final void addStatusListener(PendingResult.StatusListener var1) {
      this.zaa.addStatusListener(var1);
   }

   public final R await() {
      return this.zaa.await();
   }

   public final R await(long var1, TimeUnit var3) {
      return this.zaa.await(var1, var3);
   }

   public final void cancel() {
      this.zaa.cancel();
   }

   public final R get() {
      if (this.isDone()) {
         return this.await(0L, TimeUnit.MILLISECONDS);
      } else {
         throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
      }
   }

   public final boolean isCanceled() {
      return this.zaa.isCanceled();
   }

   public final boolean isDone() {
      return this.zaa.isReady();
   }

   public final void setResultCallback(ResultCallback<? super R> var1) {
      this.zaa.setResultCallback(var1);
   }

   public final void setResultCallback(ResultCallback<? super R> var1, long var2, TimeUnit var4) {
      this.zaa.setResultCallback(var1, var2, var4);
   }

   public final <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> var1) {
      return this.zaa.then(var1);
   }
}

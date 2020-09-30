package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.zabz;

public abstract class ResultTransform<R extends Result, S extends Result> {
   public final PendingResult<S> createFailedResult(Status var1) {
      return new zabz(var1);
   }

   public Status onFailure(Status var1) {
      return var1;
   }

   public abstract PendingResult<S> onSuccess(R var1);
}

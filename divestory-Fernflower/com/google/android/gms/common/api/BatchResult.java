package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {
   private final Status zaa;
   private final PendingResult<?>[] zab;

   BatchResult(Status var1, PendingResult<?>[] var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public final Status getStatus() {
      return this.zaa;
   }

   public final <R extends Result> R take(BatchResultToken<R> var1) {
      boolean var2;
      if (var1.mId < this.zab.length) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "The result token does not belong to this batch");
      return this.zab[var1.mId].await(0L, TimeUnit.MILLISECONDS);
   }
}

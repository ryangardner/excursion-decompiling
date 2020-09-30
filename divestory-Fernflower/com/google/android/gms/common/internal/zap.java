package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.TimeUnit;

final class zap implements PendingResult.StatusListener {
   // $FF: synthetic field
   private final PendingResult zaa;
   // $FF: synthetic field
   private final TaskCompletionSource zab;
   // $FF: synthetic field
   private final PendingResultUtil.ResultConverter zac;
   // $FF: synthetic field
   private final PendingResultUtil.zaa zad;

   zap(PendingResult var1, TaskCompletionSource var2, PendingResultUtil.ResultConverter var3, PendingResultUtil.zaa var4) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
      this.zad = var4;
   }

   public final void onComplete(Status var1) {
      if (var1.isSuccess()) {
         Result var2 = this.zaa.await(0L, TimeUnit.MILLISECONDS);
         this.zab.setResult(this.zac.convert(var2));
      } else {
         this.zab.setException(this.zad.zaa(var1));
      }
   }
}

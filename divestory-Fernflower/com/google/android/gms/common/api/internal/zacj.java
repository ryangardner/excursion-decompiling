package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.internal.Preconditions;

final class zacj implements Runnable {
   // $FF: synthetic field
   private final Result zaa;
   // $FF: synthetic field
   private final zack zab;

   zacj(zack var1, Result var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final void run() {
      try {
         BasePendingResult.zaa.set(true);
         PendingResult var1 = ((ResultTransform)Preconditions.checkNotNull(zack.zaa(this.zab))).onSuccess(this.zaa);
         zack.zab(this.zab).sendMessage(zack.zab(this.zab).obtainMessage(0, var1));
         return;
      } catch (RuntimeException var5) {
         zack.zab(this.zab).sendMessage(zack.zab(this.zab).obtainMessage(1, var5));
      } finally {
         BasePendingResult.zaa.set(false);
         zack.zaa(this.zab, this.zaa);
         GoogleApiClient var7 = (GoogleApiClient)zack.zac(this.zab).get();
         if (var7 != null) {
            var7.zab(this.zab);
         }

      }

   }
}

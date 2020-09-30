package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbl implements ResultCallback<Status> {
   zzbl(zzbi var1) {
   }

   // $FF: synthetic method
   public final void onResult(Result var1) {
      Status var2 = (Status)var1;
      if (!var2.isSuccess()) {
         zzbi.zzx().efmt("DriveContentsImpl", "Error discarding contents, status: %s", var2);
      }

   }
}

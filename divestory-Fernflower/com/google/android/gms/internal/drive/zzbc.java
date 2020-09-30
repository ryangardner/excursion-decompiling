package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbc extends TaskApiCall<zzaw, DriveId> {
   // $FF: synthetic field
   private final String zzdw;

   zzbc(zzbb var1, String var2) {
      this.zzdw = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzek)(new zzek(DriveId.zza(this.zzdw), false)), new zzhl(var2));
   }
}

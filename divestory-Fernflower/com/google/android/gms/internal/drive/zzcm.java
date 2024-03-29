package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcm extends TaskApiCall<zzaw, Void> {
   // $FF: synthetic field
   private final DriveResource zzfq;

   zzcm(zzch var1, DriveResource var2) {
      this.zzfq = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzhb)(new zzhb(this.zzfq.getDriveId())), new zzhr(var2));
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcw extends TaskApiCall<zzaw, DriveContents> {
   // $FF: synthetic field
   private final int zzdv;

   zzcw(zzch var1, int var2) {
      this.zzdv = 536870912;
      super();
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzr)(new zzr(this.zzdv)), new zzhi(var2));
   }
}

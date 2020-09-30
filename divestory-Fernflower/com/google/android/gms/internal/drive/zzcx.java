package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcx extends TaskApiCall<zzaw, DriveContents> {
   // $FF: synthetic field
   private final DriveContents zzfx;

   zzcx(zzch var1, DriveContents var2) {
      this.zzfx = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgj)(new zzgj(this.zzfx.getDriveId(), 536870912, this.zzfx.zzi().getRequestId())), new zzhi(var2));
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzda extends TaskApiCall<zzaw, Void> {
   // $FF: synthetic field
   private final DriveContents zzfx;

   zzda(zzch var1, DriveContents var2) {
      this.zzfx = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzo)(new zzo(this.zzfx.zzi().getRequestId(), false)), new zzhr(var2));
   }
}

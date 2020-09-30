package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcr extends TaskApiCall<zzaw, Void> {
   // $FF: synthetic field
   private final DriveResource zzfq;

   zzcr(zzch var1, DriveResource var2) {
      this.zzfq = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var3 = (zzaw)var1;
      if (var3.zzec) {
         ((zzeo)var3.getService()).zza((zzj)(new zzj(1, this.zzfq.getDriveId())), (zzes)null, (String)null, new zzhr(var2));
      } else {
         throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to add event subscriptions");
      }
   }
}

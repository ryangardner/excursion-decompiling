package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdc extends TaskApiCall<zzaw, Metadata> {
   // $FF: synthetic field
   private final DriveResource zzfq;
   // $FF: synthetic field
   private final boolean zzga;

   zzdc(zzch var1, DriveResource var2, boolean var3) {
      this.zzfq = var2;
      this.zzga = false;
      super();
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzek)(new zzek(this.zzfq.getDriveId(), this.zzga)), new zzhp(var2));
   }
}

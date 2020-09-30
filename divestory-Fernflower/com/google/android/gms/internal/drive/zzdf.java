package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

final class zzdf extends TaskApiCall<zzaw, Void> {
   // $FF: synthetic field
   private final DriveResource zzfq;
   // $FF: synthetic field
   private final List zzgb;

   zzdf(zzch var1, DriveResource var2, List var3) {
      this.zzfq = var2;
      this.zzgb = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgw)(new zzgw(this.zzfq.getDriveId(), this.zzgb)), new zzhr(var2));
   }
}

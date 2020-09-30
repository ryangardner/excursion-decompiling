package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcz extends TaskApiCall<zzaw, MetadataBuffer> {
   // $FF: synthetic field
   private final Query zzdu;

   zzcz(zzch var1, Query var2) {
      this.zzdu = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgq)(new zzgq(this.zzdu)), new zzhn(var2));
   }
}

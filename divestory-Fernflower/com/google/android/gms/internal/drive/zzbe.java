package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbe extends TaskApiCall<zzaw, Void> {
   // $FF: synthetic field
   private final TransferPreferences zzep;

   zzbe(zzbb var1, TransferPreferences var2) {
      this.zzep = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgu)(new zzgu(new zzei(this.zzep))), new zzhr(var2));
   }
}

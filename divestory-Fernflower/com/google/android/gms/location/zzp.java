package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzp extends com.google.android.gms.internal.location.zzak {
   // $FF: synthetic field
   private final TaskCompletionSource zzab;

   zzp(FusedLocationProviderClient var1, TaskCompletionSource var2) {
      this.zzab = var2;
   }

   public final void zza(com.google.android.gms.internal.location.zzad var1) throws RemoteException {
      Status var2 = var1.getStatus();
      if (var2 == null) {
         this.zzab.trySetException(new ApiException(new Status(8, "Got null status from location service")));
      } else if (var2.getStatusCode() == 0) {
         this.zzab.setResult(true);
      } else {
         this.zzab.trySetException(ApiExceptionUtil.fromStatus(var2));
      }
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzck extends TaskApiCall<zzaw, DriveFolder> {
   zzck(zzch var1) {
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var3 = (zzaw)var1;
      if (var3.zzae() == null) {
         var2.setException(new ApiException(new Status(10, "Drive#SCOPE_FILE must be requested")));
      } else {
         var2.setResult(new zzbs(var3.zzae()));
      }
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public class zzhh<T> extends zzl {
   private TaskCompletionSource<T> zziv;

   zzhh(TaskCompletionSource<T> var1) {
      this.zziv = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zziv.setException(new ApiException(var1));
   }

   public final TaskCompletionSource<T> zzay() {
      return this.zziv;
   }
}

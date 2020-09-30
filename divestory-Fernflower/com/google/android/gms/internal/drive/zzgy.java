package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

public final class zzgy extends zzl {
   private final BaseImplementation.ResultHolder<Status> zzdx;

   public zzgy(BaseImplementation.ResultHolder<Status> var1) {
      this.zzdx = var1;
   }

   public final void onSuccess() throws RemoteException {
      this.zzdx.setResult(Status.RESULT_SUCCESS);
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(var1);
   }
}

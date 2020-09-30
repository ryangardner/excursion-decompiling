package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;

final class zzba extends zzav {
   // $FF: synthetic field
   private final DriveId zzen;
   // $FF: synthetic field
   private final int zzeo;

   zzba(zzaw var1, GoogleApiClient var2, DriveId var3, int var4) {
      this.zzen = var3;
      this.zzeo = 1;
      super(var2);
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgs)(new zzgs(this.zzen, this.zzeo)), (zzes)null, (String)null, new zzgy(this));
   }
}

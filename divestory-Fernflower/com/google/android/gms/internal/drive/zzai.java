package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;

final class zzai extends zzap {
   // $FF: synthetic field
   private final String zzdw;

   zzai(zzaf var1, GoogleApiClient var2, String var3) {
      super(var2);
      this.zzdw = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzek)(new zzek(DriveId.zza(this.zzdw), false)), new zzan(this));
   }
}

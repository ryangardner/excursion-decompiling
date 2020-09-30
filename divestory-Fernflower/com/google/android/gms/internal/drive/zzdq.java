package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzdq extends zzea {
   // $FF: synthetic field
   private final boolean zzga;
   // $FF: synthetic field
   private final zzdp zzgq;

   zzdq(zzdp var1, GoogleApiClient var2, boolean var3) {
      this.zzgq = var1;
      this.zzga = false;
      super(var1, var2, (zzdq)null);
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzek)(new zzek(this.zzgq.zzk, this.zzga)), new zzdy(this));
   }
}

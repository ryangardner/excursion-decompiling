package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.MetadataChangeSet;

final class zzbk extends zzav {
   // $FF: synthetic field
   private final zzbi zzev;
   // $FF: synthetic field
   private final MetadataChangeSet zzew;
   // $FF: synthetic field
   private final com.google.android.gms.drive.zzn zzex;

   zzbk(zzbi var1, GoogleApiClient var2, MetadataChangeSet var3, com.google.android.gms.drive.zzn var4) {
      super(var2);
      this.zzev = var1;
      this.zzew = var3;
      this.zzex = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaw var2 = (zzaw)var1;
      this.zzew.zzq().zza(var2.getContext());
      ((zzeo)var2.getService()).zza((zzm)(new zzm(zzbi.zza(this.zzev).getDriveId(), this.zzew.zzq(), zzbi.zza(this.zzev).getRequestId(), zzbi.zza(this.zzev).zzb(), this.zzex)), new zzgy(this));
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.MetadataChangeSet;

final class zzbu extends zzca {
   // $FF: synthetic field
   private final MetadataChangeSet zzfd;
   // $FF: synthetic field
   private final zzbs zzfh;

   zzbu(zzbs var1, GoogleApiClient var2, MetadataChangeSet var3) {
      super(var2);
      this.zzfh = var1;
      this.zzfd = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaw var2 = (zzaw)var1;
      this.zzfd.zzq().zza(var2.getContext());
      ((zzeo)var2.getService()).zza((zzy)(new zzy(this.zzfh.getDriveId(), this.zzfd.zzq())), new zzbw(this));
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;

final class zzbt extends zzby {
   // $FF: synthetic field
   private final MetadataChangeSet zzfd;
   // $FF: synthetic field
   private final int zzfe;
   // $FF: synthetic field
   private final int zzff;
   // $FF: synthetic field
   private final ExecutionOptions zzfg;
   // $FF: synthetic field
   private final zzbs zzfh;

   zzbt(zzbs var1, GoogleApiClient var2, MetadataChangeSet var3, int var4, int var5, ExecutionOptions var6) {
      super(var2);
      this.zzfh = var1;
      this.zzfd = var3;
      this.zzfe = var4;
      this.zzff = var5;
      this.zzfg = var6;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaw var3 = (zzaw)var1;
      this.zzfd.zzq().zza(var3.getContext());
      zzw var2 = new zzw(this.zzfh.getDriveId(), this.zzfd.zzq(), this.zzfe, this.zzff, this.zzfg);
      ((zzeo)var3.getService()).zza((zzw)var2, new zzbv(this));
   }
}

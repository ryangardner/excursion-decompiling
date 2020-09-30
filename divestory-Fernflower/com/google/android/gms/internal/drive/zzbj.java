package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveFile;

final class zzbj extends zzam {
   // $FF: synthetic field
   private final zzbi zzev;

   zzbj(zzbi var1, GoogleApiClient var2) {
      super(var2);
      this.zzev = var1;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgj)(new zzgj(this.zzev.getDriveId(), 536870912, zzbi.zza(this.zzev).getRequestId())), new zzgl(this, (DriveFile.DownloadProgressListener)null));
   }
}

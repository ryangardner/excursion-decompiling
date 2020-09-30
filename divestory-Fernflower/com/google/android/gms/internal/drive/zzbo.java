package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.DriveFile;

final class zzbo extends zzam {
   // $FF: synthetic field
   private final int zzdv;
   // $FF: synthetic field
   private final DriveFile.DownloadProgressListener zzey;
   // $FF: synthetic field
   private final zzbn zzez;

   zzbo(zzbn var1, GoogleApiClient var2, int var3, DriveFile.DownloadProgressListener var4) {
      super(var2);
      this.zzez = var1;
      this.zzdv = var3;
      this.zzey = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      this.setCancelToken(ICancelToken.Stub.asInterface(((zzeo)((zzaw)var1).getService()).zza((zzgj)(new zzgj(this.zzez.getDriveId(), this.zzdv, 0)), new zzgl(this, this.zzey)).zzgs));
   }
}

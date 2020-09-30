package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;

final class zzbv extends zzl {
   private final BaseImplementation.ResultHolder<DriveFolder.DriveFileResult> zzdx;

   public zzbv(BaseImplementation.ResultHolder<DriveFolder.DriveFileResult> var1) {
      this.zzdx = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzbx(var1, (DriveFile)null));
   }

   public final void zza(zzfn var1) throws RemoteException {
      this.zzdx.setResult(new zzbx(Status.RESULT_SUCCESS, new zzbn(var1.zzdd)));
   }
}

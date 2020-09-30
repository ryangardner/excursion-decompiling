package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveFolder;

final class zzbw extends zzl {
   private final BaseImplementation.ResultHolder<DriveFolder.DriveFolderResult> zzdx;

   public zzbw(BaseImplementation.ResultHolder<DriveFolder.DriveFolderResult> var1) {
      this.zzdx = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzbz(var1, (DriveFolder)null));
   }

   public final void zza(zzfn var1) throws RemoteException {
      this.zzdx.setResult(new zzbz(Status.RESULT_SUCCESS, new zzbs(var1.zzdd)));
   }
}

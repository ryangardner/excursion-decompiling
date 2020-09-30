package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;

final class zzan extends zzl {
   private final BaseImplementation.ResultHolder<DriveApi.DriveIdResult> zzdx;

   public zzan(BaseImplementation.ResultHolder<DriveApi.DriveIdResult> var1) {
      this.zzdx = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzao(var1, (DriveId)null));
   }

   public final void zza(zzfn var1) throws RemoteException {
      this.zzdx.setResult(new zzao(Status.RESULT_SUCCESS, var1.zzdd));
   }

   public final void zza(zzfy var1) throws RemoteException {
      this.zzdx.setResult(new zzao(Status.RESULT_SUCCESS, (new zzaa(var1.zzdn)).getDriveId()));
   }
}

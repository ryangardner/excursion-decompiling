package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;

final class zzak extends zzl {
   private final BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> zzdx;

   zzak(BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> var1) {
      this.zzdx = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzal(var1, (DriveContents)null));
   }

   public final void zza(zzfh var1) throws RemoteException {
      this.zzdx.setResult(new zzal(Status.RESULT_SUCCESS, new zzbi(var1.zzes)));
   }
}

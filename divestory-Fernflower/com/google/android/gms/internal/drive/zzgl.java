package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;

final class zzgl extends zzl {
   private final BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> zzdx;
   private final DriveFile.DownloadProgressListener zziq;

   zzgl(BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> var1, DriveFile.DownloadProgressListener var2) {
      this.zzdx = var1;
      this.zziq = var2;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzal(var1, (DriveContents)null));
   }

   public final void zza(zzfh var1) throws RemoteException {
      Status var2;
      if (var1.zzhv) {
         var2 = new Status(-1);
      } else {
         var2 = Status.RESULT_SUCCESS;
      }

      this.zzdx.setResult(new zzal(var2, new zzbi(var1.zzes)));
   }

   public final void zza(zzfl var1) throws RemoteException {
      DriveFile.DownloadProgressListener var2 = this.zziq;
      if (var2 != null) {
         var2.onProgress(var1.zzhy, var1.zzhz);
      }

   }
}

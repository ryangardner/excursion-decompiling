package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhj extends zzhh<DriveFile> {
   public zzhj(TaskCompletionSource<DriveFile> var1) {
      super(var1);
   }

   public final void zza(zzfn var1) throws RemoteException {
      this.zzay().setResult(var1.getDriveId().asDriveFile());
   }
}

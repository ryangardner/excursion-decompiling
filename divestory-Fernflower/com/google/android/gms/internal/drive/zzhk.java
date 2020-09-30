package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhk extends zzhh<DriveFolder> {
   public zzhk(TaskCompletionSource<DriveFolder> var1) {
      super(var1);
   }

   public final void zza(zzfn var1) throws RemoteException {
      this.zzay().setResult(var1.getDriveId().asDriveFolder());
   }
}

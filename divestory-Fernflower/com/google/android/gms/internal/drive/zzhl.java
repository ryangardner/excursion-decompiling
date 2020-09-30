package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhl extends zzhh<DriveId> {
   public zzhl(TaskCompletionSource<DriveId> var1) {
      super(var1);
   }

   public final void zza(zzfn var1) throws RemoteException {
      this.zzay().setResult(var1.getDriveId());
   }

   public final void zza(zzfy var1) throws RemoteException {
      this.zzay().setResult((new zzaa(var1.zzaw())).getDriveId());
   }
}

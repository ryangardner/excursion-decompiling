package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhi extends zzhh<DriveContents> {
   public zzhi(TaskCompletionSource<DriveContents> var1) {
      super(var1);
   }

   public final void zza(zzfh var1) throws RemoteException {
      this.zzay().setResult(new zzbi(var1.zzar()));
   }
}

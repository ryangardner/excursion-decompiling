package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzct extends TaskApiCall<zzaw, DriveContents> {
   // $FF: synthetic field
   private final DriveFile zzfs;
   // $FF: synthetic field
   private final int zzft;

   zzct(zzch var1, DriveFile var2, int var3) {
      this.zzfs = var2;
      this.zzft = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgj)(new zzgj(this.zzfs.getDriveId(), this.zzft, 0)), new zzhi(var2));
   }
}

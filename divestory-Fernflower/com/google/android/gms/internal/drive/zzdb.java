package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdb extends TaskApiCall<zzaw, DriveFolder> {
   // $FF: synthetic field
   private final MetadataChangeSet zzfd;
   // $FF: synthetic field
   private final DriveFolder zzfz;

   zzdb(zzch var1, MetadataChangeSet var2, DriveFolder var3) {
      this.zzfd = var2;
      this.zzfz = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var3 = (zzaw)var1;
      this.zzfd.zzq().zza(var3.getContext());
      ((zzeo)var3.getService()).zza((zzy)(new zzy(this.zzfz.getDriveId(), this.zzfd.zzq())), new zzhk(var2));
   }
}

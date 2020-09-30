package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdd extends TaskApiCall<zzaw, Metadata> {
   // $FF: synthetic field
   private final MetadataChangeSet zzfd;
   // $FF: synthetic field
   private final DriveResource zzfq;

   zzdd(zzch var1, MetadataChangeSet var2, DriveResource var3) {
      this.zzfd = var2;
      this.zzfq = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var3 = (zzaw)var1;
      this.zzfd.zzq().zza(var3.getContext());
      ((zzeo)var3.getService()).zza((zzhf)(new zzhf(this.zzfq.getDriveId(), this.zzfd.zzq())), new zzhp(var2));
   }
}

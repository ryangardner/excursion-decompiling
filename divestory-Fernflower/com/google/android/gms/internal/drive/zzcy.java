package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcy extends TaskApiCall<zzaw, Void> {
   // $FF: synthetic field
   private final MetadataChangeSet zzew;
   // $FF: synthetic field
   private final DriveContents zzfx;
   // $FF: synthetic field
   private final com.google.android.gms.drive.zzn zzfy;

   zzcy(zzch var1, com.google.android.gms.drive.zzn var2, DriveContents var3, MetadataChangeSet var4) {
      this.zzfy = var2;
      this.zzfx = var3;
      this.zzew = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var5 = (zzaw)var1;

      try {
         this.zzfy.zza(var5);
      } catch (IllegalStateException var4) {
         var2.setException(var4);
      }

      this.zzfx.zzj();
      this.zzew.zzq().zza(var5.getContext());
      ((zzeo)var5.getService()).zza((zzm)(new zzm(this.zzfx.getDriveId(), this.zzew.zzq(), this.zzfx.zzi().getRequestId(), this.zzfx.zzi().zzb(), this.zzfy)), new zzhr(var2));
   }
}

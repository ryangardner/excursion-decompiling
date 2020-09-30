package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbg extends TaskApiCall<zzaw, IntentSender> {
   // $FF: synthetic field
   private final CreateFileActivityOptions zzer;

   zzbg(zzbb var1, CreateFileActivityOptions var2) {
      this.zzer = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var3 = (zzaw)var1;
      zzeo var4 = (zzeo)var3.getService();
      this.zzer.zzde.zza(var3.getContext());
      var2.setResult(var4.zza(new zzu(this.zzer.zzde, this.zzer.zzdk, this.zzer.zzba, this.zzer.zzbd, this.zzer.zzdl)));
   }
}

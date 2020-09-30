package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbf extends TaskApiCall<zzaw, IntentSender> {
   // $FF: synthetic field
   private final OpenFileActivityOptions zzeq;

   zzbf(zzbb var1, OpenFileActivityOptions var2) {
      this.zzeq = var2;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      var2.setResult(((zzeo)((zzaw)var1).getService()).zza(new zzgm(this.zzeq.zzba, this.zzeq.zzbb, this.zzeq.zzbd, this.zzeq.zzbe)));
   }
}

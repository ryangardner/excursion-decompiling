package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcp extends RegisterListenerMethod<zzaw, zzdi> {
   // $FF: synthetic field
   private final DriveResource zzfq;
   // $FF: synthetic field
   private final zzdi zzfr;

   zzcp(zzch var1, ListenerHolder var2, DriveResource var3, zzdi var4) {
      super(var2);
      this.zzfq = var3;
      this.zzfr = var4;
   }

   // $FF: synthetic method
   protected final void registerListener(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzj)(new zzj(1, this.zzfq.getDriveId())), zzdi.zza(this.zzfr), (String)null, new zzhr(var2));
   }
}

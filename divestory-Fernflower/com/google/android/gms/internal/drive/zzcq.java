package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcq extends UnregisterListenerMethod<zzaw, zzdi> {
   // $FF: synthetic field
   private final DriveResource zzfq;
   // $FF: synthetic field
   private final zzdi zzfr;

   zzcq(zzch var1, ListenerHolder.ListenerKey var2, DriveResource var3, zzdi var4) {
      super(var2);
      this.zzfq = var3;
      this.zzfr = var4;
   }

   // $FF: synthetic method
   protected final void unregisterListener(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgs)(new zzgs(this.zzfq.getDriveId(), 1)), zzdi.zza(this.zzfr), (String)null, new zzhq(var2));
   }
}

package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcv extends UnregisterListenerMethod<zzaw, OpenFileCallback> {
   // $FF: synthetic field
   private final zzg zzfu;

   zzcv(zzch var1, ListenerHolder.ListenerKey var2, zzg var3) {
      super(var2);
      this.zzfu = var3;
   }

   // $FF: synthetic method
   protected final void unregisterListener(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      var2.setResult(this.zzfu.cancel());
   }
}

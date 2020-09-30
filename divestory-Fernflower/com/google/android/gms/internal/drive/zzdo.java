package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.events.OpenFileCallback;

final class zzdo implements ListenerHolder.Notifier<OpenFileCallback> {
   // $FF: synthetic field
   private final zzdg zzgp;

   zzdo(zzdk var1, zzdg var2) {
      this.zzgp = var2;
   }

   // $FF: synthetic method
   public final void notifyListener(Object var1) {
      OpenFileCallback var2 = (OpenFileCallback)var1;
      this.zzgp.accept(var2);
   }

   public final void onNotifyListenerFailed() {
   }
}

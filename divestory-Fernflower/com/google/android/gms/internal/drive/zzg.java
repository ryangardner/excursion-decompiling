package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.events.ListenerToken;

public final class zzg implements ListenerToken {
   private final ListenerHolder.ListenerKey zzcy;
   private ICancelToken zzcz = null;

   public zzg(ListenerHolder.ListenerKey var1) {
      this.zzcy = var1;
   }

   public final boolean cancel() {
      ICancelToken var1 = this.zzcz;
      if (var1 != null) {
         try {
            var1.cancel();
            return true;
         } catch (RemoteException var2) {
         }
      }

      return false;
   }

   public final void setCancelToken(ICancelToken var1) {
      this.zzcz = var1;
   }

   public final ListenerHolder.ListenerKey zzad() {
      return this.zzcy;
   }
}

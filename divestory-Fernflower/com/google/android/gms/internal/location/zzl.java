package com.google.android.gms.internal.location;

import android.os.DeadObjectException;
import android.os.IInterface;

final class zzl implements zzbj<zzao> {
   // $FF: synthetic field
   private final zzk zzcc;

   zzl(zzk var1) {
      this.zzcc = var1;
   }

   public final void checkConnected() {
      zzk.zza(this.zzcc);
   }

   // $FF: synthetic method
   public final IInterface getService() throws DeadObjectException {
      return (zzao)this.zzcc.getService();
   }
}

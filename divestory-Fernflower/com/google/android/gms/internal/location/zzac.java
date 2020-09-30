package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

final class zzac extends zzak {
   private final BaseImplementation.ResultHolder<Status> zzcq;

   public zzac(BaseImplementation.ResultHolder<Status> var1) {
      this.zzcq = var1;
   }

   public final void zza(zzad var1) {
      this.zzcq.setResult(var1.getStatus());
   }
}

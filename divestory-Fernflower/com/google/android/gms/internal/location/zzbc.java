package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.LocationSettingsResult;

final class zzbc extends zzar {
   private BaseImplementation.ResultHolder<LocationSettingsResult> zzdf;

   public zzbc(BaseImplementation.ResultHolder<LocationSettingsResult> var1) {
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "listener can't be null.");
      this.zzdf = var1;
   }

   public final void zza(LocationSettingsResult var1) throws RemoteException {
      this.zzdf.setResult(var1);
      this.zzdf = null;
   }
}

package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.LocationStatusCodes;

final class zzbb extends zzan {
   private BaseImplementation.ResultHolder<Status> zzdf;

   public zzbb(BaseImplementation.ResultHolder<Status> var1) {
      this.zzdf = var1;
   }

   private final void zze(int var1) {
      if (this.zzdf == null) {
         Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
      } else {
         Status var2 = LocationStatusCodes.zzd(LocationStatusCodes.zzc(var1));
         this.zzdf.setResult(var2);
         this.zzdf = null;
      }
   }

   public final void zza(int var1, PendingIntent var2) {
      this.zze(var1);
   }

   public final void zza(int var1, String[] var2) {
      Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
   }

   public final void zzb(int var1, String[] var2) {
      this.zze(var1);
   }
}

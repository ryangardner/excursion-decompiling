package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.LocationStatusCodes;

final class zzba extends zzan {
   private BaseImplementation.ResultHolder<Status> zzdf;

   public zzba(BaseImplementation.ResultHolder<Status> var1) {
      this.zzdf = var1;
   }

   public final void zza(int var1, PendingIntent var2) {
      Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByPendingIntentResult");
   }

   public final void zza(int var1, String[] var2) {
      if (this.zzdf == null) {
         Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
      } else {
         Status var3 = LocationStatusCodes.zzd(LocationStatusCodes.zzc(var1));
         this.zzdf.setResult(var3);
         this.zzdf = null;
      }
   }

   public final void zzb(int var1, String[] var2) {
      Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByRequestIdsResult");
   }
}

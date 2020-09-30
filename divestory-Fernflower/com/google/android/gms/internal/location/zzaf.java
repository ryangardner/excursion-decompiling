package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import java.util.List;

public final class zzaf implements GeofencingApi {
   private final PendingResult<Status> zza(GoogleApiClient var1, com.google.android.gms.location.zzal var2) {
      return var1.execute(new zzah(this, var1, var2));
   }

   public final PendingResult<Status> addGeofences(GoogleApiClient var1, GeofencingRequest var2, PendingIntent var3) {
      return var1.execute(new zzag(this, var1, var2, var3));
   }

   @Deprecated
   public final PendingResult<Status> addGeofences(GoogleApiClient var1, List<Geofence> var2, PendingIntent var3) {
      GeofencingRequest.Builder var4 = new GeofencingRequest.Builder();
      var4.addGeofences(var2);
      var4.setInitialTrigger(5);
      return this.addGeofences(var1, var4.build(), var3);
   }

   public final PendingResult<Status> removeGeofences(GoogleApiClient var1, PendingIntent var2) {
      return this.zza(var1, com.google.android.gms.location.zzal.zza(var2));
   }

   public final PendingResult<Status> removeGeofences(GoogleApiClient var1, List<String> var2) {
      return this.zza(var1, com.google.android.gms.location.zzal.zza(var2));
   }
}

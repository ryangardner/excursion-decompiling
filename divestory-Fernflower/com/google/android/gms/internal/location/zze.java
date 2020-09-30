package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityTransitionRequest;

public final class zze implements ActivityRecognitionApi {
   public final PendingResult<Status> removeActivityUpdates(GoogleApiClient var1, PendingIntent var2) {
      return var1.execute(new zzg(this, var1, var2));
   }

   public final PendingResult<Status> requestActivityUpdates(GoogleApiClient var1, long var2, PendingIntent var4) {
      return var1.execute(new zzf(this, var1, var2, var4));
   }

   public final PendingResult<Status> zza(GoogleApiClient var1, PendingIntent var2) {
      return var1.execute(new zzi(this, var1, var2));
   }

   public final PendingResult<Status> zza(GoogleApiClient var1, ActivityTransitionRequest var2, PendingIntent var3) {
      return var1.execute(new zzh(this, var1, var2, var3));
   }
}

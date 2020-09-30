package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

public final class zzbk implements SettingsApi {
   public final PendingResult<LocationSettingsResult> checkLocationSettings(GoogleApiClient var1, LocationSettingsRequest var2) {
      return var1.enqueue(new zzbl(this, var1, var2, (String)null));
   }
}

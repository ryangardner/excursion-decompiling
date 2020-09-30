package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

abstract class zzab extends LocationServices.zza<Status> {
   public zzab(GoogleApiClient var1) {
      super(var1);
   }

   // $FF: synthetic method
   public Result createFailedResult(Status var1) {
      return var1;
   }
}

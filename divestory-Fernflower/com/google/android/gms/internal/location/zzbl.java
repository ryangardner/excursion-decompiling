package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

final class zzbl extends LocationServices.zza<LocationSettingsResult> {
   // $FF: synthetic field
   private final LocationSettingsRequest zzdp;
   // $FF: synthetic field
   private final String zzdq;

   zzbl(zzbk var1, GoogleApiClient var2, LocationSettingsRequest var3, String var4) {
      this.zzdp = var3;
      this.zzdq = null;
      super(var2);
   }

   // $FF: synthetic method
   public final Result createFailedResult(Status var1) {
      return new LocationSettingsResult(var1);
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza((LocationSettingsRequest)this.zzdp, (BaseImplementation.ResultHolder)this, (String)this.zzdq);
   }
}

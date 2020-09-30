package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.tasks.Task;

public class SettingsClient extends GoogleApi<Api.ApiOptions.NoOptions> {
   public SettingsClient(Activity var1) {
      super((Activity)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public SettingsClient(Context var1) {
      super((Context)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public Task<LocationSettingsResponse> checkLocationSettings(LocationSettingsRequest var1) {
      return PendingResultUtil.toResponseTask(LocationServices.SettingsApi.checkLocationSettings(this.asGoogleApiClient(), var1), new LocationSettingsResponse());
   }
}

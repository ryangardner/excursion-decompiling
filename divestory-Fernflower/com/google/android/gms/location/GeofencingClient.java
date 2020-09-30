package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class GeofencingClient extends GoogleApi<Api.ApiOptions.NoOptions> {
   public GeofencingClient(Activity var1) {
      super((Activity)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public GeofencingClient(Context var1) {
      super((Context)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public Task<Void> addGeofences(GeofencingRequest var1, PendingIntent var2) {
      return PendingResultUtil.toVoidTask(LocationServices.GeofencingApi.addGeofences(this.asGoogleApiClient(), var1, var2));
   }

   public Task<Void> removeGeofences(PendingIntent var1) {
      return PendingResultUtil.toVoidTask(LocationServices.GeofencingApi.removeGeofences(this.asGoogleApiClient(), var1));
   }

   public Task<Void> removeGeofences(List<String> var1) {
      return PendingResultUtil.toVoidTask(LocationServices.GeofencingApi.removeGeofences(this.asGoogleApiClient(), var1));
   }
}

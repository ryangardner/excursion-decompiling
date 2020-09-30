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

public class ActivityRecognitionClient extends GoogleApi<Api.ApiOptions.NoOptions> {
   public ActivityRecognitionClient(Activity var1) {
      super((Activity)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public ActivityRecognitionClient(Context var1) {
      super((Context)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public Task<Void> removeActivityTransitionUpdates(PendingIntent var1) {
      return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.zza(this.asGoogleApiClient(), var1));
   }

   public Task<Void> removeActivityUpdates(PendingIntent var1) {
      return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(this.asGoogleApiClient(), var1));
   }

   public Task<Void> requestActivityTransitionUpdates(ActivityTransitionRequest var1, PendingIntent var2) {
      return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.zza(this.asGoogleApiClient(), var1, var2));
   }

   public Task<Void> requestActivityUpdates(long var1, PendingIntent var3) {
      return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(this.asGoogleApiClient(), var1, var3));
   }
}

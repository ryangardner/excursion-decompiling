package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.location.zzaz;

public class ActivityRecognition {
   public static final Api<Api.ApiOptions.NoOptions> API;
   @Deprecated
   public static final ActivityRecognitionApi ActivityRecognitionApi;
   private static final Api.AbstractClientBuilder<zzaz, Api.ApiOptions.NoOptions> CLIENT_BUILDER = new com.google.android.gms.location.zza();
   private static final Api.ClientKey<zzaz> CLIENT_KEY = new Api.ClientKey();
   public static final String CLIENT_NAME = "activity_recognition";

   static {
      API = new Api("ActivityRecognition.API", CLIENT_BUILDER, CLIENT_KEY);
      ActivityRecognitionApi = new com.google.android.gms.internal.location.zze();
   }

   private ActivityRecognition() {
   }

   public static ActivityRecognitionClient getClient(Activity var0) {
      return new ActivityRecognitionClient(var0);
   }

   public static ActivityRecognitionClient getClient(Context var0) {
      return new ActivityRecognitionClient(var0);
   }

   public abstract static class zza<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zzaz> {
      public zza(GoogleApiClient var1) {
         super(ActivityRecognition.API, var1);
      }
   }
}

package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzbk;

public class LocationServices {
   public static final Api<Api.ApiOptions.NoOptions> API;
   private static final Api.AbstractClientBuilder<zzaz, Api.ApiOptions.NoOptions> CLIENT_BUILDER = new zzad();
   private static final Api.ClientKey<zzaz> CLIENT_KEY = new Api.ClientKey();
   @Deprecated
   public static final FusedLocationProviderApi FusedLocationApi;
   @Deprecated
   public static final GeofencingApi GeofencingApi;
   @Deprecated
   public static final SettingsApi SettingsApi;

   static {
      API = new Api("LocationServices.API", CLIENT_BUILDER, CLIENT_KEY);
      FusedLocationApi = new com.google.android.gms.internal.location.zzq();
      GeofencingApi = new com.google.android.gms.internal.location.zzaf();
      SettingsApi = new zzbk();
   }

   private LocationServices() {
   }

   public static FusedLocationProviderClient getFusedLocationProviderClient(Activity var0) {
      return new FusedLocationProviderClient(var0);
   }

   public static FusedLocationProviderClient getFusedLocationProviderClient(Context var0) {
      return new FusedLocationProviderClient(var0);
   }

   public static GeofencingClient getGeofencingClient(Activity var0) {
      return new GeofencingClient(var0);
   }

   public static GeofencingClient getGeofencingClient(Context var0) {
      return new GeofencingClient(var0);
   }

   public static SettingsClient getSettingsClient(Activity var0) {
      return new SettingsClient(var0);
   }

   public static SettingsClient getSettingsClient(Context var0) {
      return new SettingsClient(var0);
   }

   public static zzaz zza(GoogleApiClient var0) {
      boolean var1 = true;
      boolean var2;
      if (var0 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "GoogleApiClient parameter is required.");
      zzaz var3 = (zzaz)var0.getClient(CLIENT_KEY);
      if (var3 != null) {
         var2 = var1;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2, "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
      return var3;
   }

   public abstract static class zza<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zzaz> {
      public zza(GoogleApiClient var1) {
         super(LocationServices.API, var1);
      }
   }
}

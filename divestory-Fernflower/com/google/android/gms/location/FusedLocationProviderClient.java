package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.internal.location.zzbm;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class FusedLocationProviderClient extends GoogleApi<Api.ApiOptions.NoOptions> {
   public static final String KEY_VERTICAL_ACCURACY = "verticalAccuracy";

   public FusedLocationProviderClient(Activity var1) {
      super((Activity)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   public FusedLocationProviderClient(Context var1) {
      super((Context)var1, LocationServices.API, (Api.ApiOptions)null, (StatusExceptionMapper)(new ApiExceptionMapper()));
   }

   // $FF: synthetic method
   static com.google.android.gms.internal.location.zzaj zza(FusedLocationProviderClient var0, TaskCompletionSource var1) {
      return var0.zza(var1);
   }

   private final com.google.android.gms.internal.location.zzaj zza(TaskCompletionSource<Boolean> var1) {
      return new zzp(this, var1);
   }

   public Task<Void> flushLocations() {
      return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.flushLocations(this.asGoogleApiClient()));
   }

   public Task<Location> getLastLocation() {
      return this.doRead(new zzl(this));
   }

   public Task<LocationAvailability> getLocationAvailability() {
      return this.doRead(new zzm(this));
   }

   public Task<Void> removeLocationUpdates(PendingIntent var1) {
      return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.removeLocationUpdates(this.asGoogleApiClient(), var1));
   }

   public Task<Void> removeLocationUpdates(LocationCallback var1) {
      return TaskUtil.toVoidTaskThatFailsOnFalse(this.doUnregisterEventListener(ListenerHolders.createListenerKey(var1, LocationCallback.class.getSimpleName())));
   }

   public Task<Void> requestLocationUpdates(LocationRequest var1, PendingIntent var2) {
      return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.requestLocationUpdates(this.asGoogleApiClient(), var1, var2));
   }

   public Task<Void> requestLocationUpdates(LocationRequest var1, LocationCallback var2, Looper var3) {
      zzbd var4 = zzbd.zza(var1);
      ListenerHolder var5 = ListenerHolders.createListenerHolder(var2, zzbm.zza(var3), LocationCallback.class.getSimpleName());
      return this.doRegisterEventListener(new zzn(this, var5, var4, var5), new zzo(this, var5.getListenerKey()));
   }

   public Task<Void> setMockLocation(Location var1) {
      return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.setMockLocation(this.asGoogleApiClient(), var1));
   }

   public Task<Void> setMockMode(boolean var1) {
      return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.setMockMode(this.asGoogleApiClient(), var1));
   }

   private static final class zza extends com.google.android.gms.internal.location.zzak {
      private final TaskCompletionSource<Void> zzac;

      public zza(TaskCompletionSource<Void> var1) {
         this.zzac = var1;
      }

      public final void zza(com.google.android.gms.internal.location.zzad var1) {
         TaskUtil.setResultOrApiException(var1.getStatus(), this.zzac);
      }
   }
}

package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public final class zzq implements FusedLocationProviderApi {
   public final PendingResult<Status> flushLocations(GoogleApiClient var1) {
      return var1.execute(new zzv(this, var1));
   }

   public final Location getLastLocation(GoogleApiClient var1) {
      zzaz var3 = LocationServices.zza(var1);

      try {
         Location var4 = var3.getLastLocation();
         return var4;
      } catch (Exception var2) {
         return null;
      }
   }

   public final LocationAvailability getLocationAvailability(GoogleApiClient var1) {
      zzaz var3 = LocationServices.zza(var1);

      try {
         LocationAvailability var4 = var3.zza();
         return var4;
      } catch (Exception var2) {
         return null;
      }
   }

   public final PendingResult<Status> removeLocationUpdates(GoogleApiClient var1, PendingIntent var2) {
      return var1.execute(new zzaa(this, var1, var2));
   }

   public final PendingResult<Status> removeLocationUpdates(GoogleApiClient var1, LocationCallback var2) {
      return var1.execute(new zzs(this, var1, var2));
   }

   public final PendingResult<Status> removeLocationUpdates(GoogleApiClient var1, LocationListener var2) {
      return var1.execute(new zzz(this, var1, var2));
   }

   public final PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, PendingIntent var3) {
      return var1.execute(new zzy(this, var1, var2, var3));
   }

   public final PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationCallback var3, Looper var4) {
      return var1.execute(new zzx(this, var1, var2, var3, var4));
   }

   public final PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationListener var3) {
      Preconditions.checkNotNull(Looper.myLooper(), "Calling thread must be a prepared Looper thread.");
      return var1.execute(new zzr(this, var1, var2, var3));
   }

   public final PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationListener var3, Looper var4) {
      return var1.execute(new zzw(this, var1, var2, var3, var4));
   }

   public final PendingResult<Status> setMockLocation(GoogleApiClient var1, Location var2) {
      return var1.execute(new zzu(this, var1, var2));
   }

   public final PendingResult<Status> setMockMode(GoogleApiClient var1, boolean var2) {
      return var1.execute(new zzt(this, var1, var2));
   }
}

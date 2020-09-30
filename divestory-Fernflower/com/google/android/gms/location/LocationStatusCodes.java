package com.google.android.gms.location;

import com.google.android.gms.common.api.Status;

@Deprecated
public final class LocationStatusCodes {
   public static final int ERROR = 1;
   public static final int GEOFENCE_NOT_AVAILABLE = 1000;
   public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
   public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;
   public static final int SUCCESS = 0;

   private LocationStatusCodes() {
   }

   public static int zzc(int var0) {
      return (var0 < 0 || var0 > 1) && (1000 > var0 || var0 > 1002) ? 1 : var0;
   }

   public static Status zzd(int var0) {
      if (var0 == 1) {
         var0 = 13;
      }

      return new Status(var0);
   }
}

package com.google.android.gms.location;

import android.os.SystemClock;
import com.google.android.gms.internal.location.zzbh;

public interface Geofence {
   int GEOFENCE_TRANSITION_DWELL = 4;
   int GEOFENCE_TRANSITION_ENTER = 1;
   int GEOFENCE_TRANSITION_EXIT = 2;
   long NEVER_EXPIRE = -1L;

   String getRequestId();

   public static final class Builder {
      private String zzad = null;
      private int zzae = 0;
      private long zzaf = Long.MIN_VALUE;
      private short zzag = (short)-1;
      private double zzah;
      private double zzai;
      private float zzaj;
      private int zzak = 0;
      private int zzal = -1;

      public final Geofence build() {
         if (this.zzad != null) {
            int var1 = this.zzae;
            if (var1 != 0) {
               if ((var1 & 4) != 0 && this.zzal < 0) {
                  throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELLING.");
               } else if (this.zzaf != Long.MIN_VALUE) {
                  if (this.zzag != -1) {
                     if (this.zzak >= 0) {
                        return new zzbh(this.zzad, this.zzae, (short)1, this.zzah, this.zzai, this.zzaj, this.zzaf, this.zzak, this.zzal);
                     } else {
                        throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
                     }
                  } else {
                     throw new IllegalArgumentException("Geofence region not set.");
                  }
               } else {
                  throw new IllegalArgumentException("Expiration not set.");
               }
            } else {
               throw new IllegalArgumentException("Transitions types not set.");
            }
         } else {
            throw new IllegalArgumentException("Request ID not set.");
         }
      }

      public final Geofence.Builder setCircularRegion(double var1, double var3, float var5) {
         this.zzag = (short)1;
         this.zzah = var1;
         this.zzai = var3;
         this.zzaj = var5;
         return this;
      }

      public final Geofence.Builder setExpirationDuration(long var1) {
         if (var1 < 0L) {
            this.zzaf = -1L;
         } else {
            this.zzaf = SystemClock.elapsedRealtime() + var1;
         }

         return this;
      }

      public final Geofence.Builder setLoiteringDelay(int var1) {
         this.zzal = var1;
         return this;
      }

      public final Geofence.Builder setNotificationResponsiveness(int var1) {
         this.zzak = var1;
         return this;
      }

      public final Geofence.Builder setRequestId(String var1) {
         this.zzad = var1;
         return this;
      }

      public final Geofence.Builder setTransitionTypes(int var1) {
         this.zzae = var1;
         return this;
      }
   }
}

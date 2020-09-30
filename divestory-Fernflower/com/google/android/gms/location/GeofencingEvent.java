package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import com.google.android.gms.internal.location.zzbh;
import java.util.ArrayList;
import java.util.List;

public class GeofencingEvent {
   private final int errorCode;
   private final int zzam;
   private final List<Geofence> zzan;
   private final Location zzao;

   private GeofencingEvent(int var1, int var2, List<Geofence> var3, Location var4) {
      this.errorCode = var1;
      this.zzam = var2;
      this.zzan = var3;
      this.zzao = var4;
   }

   public static GeofencingEvent fromIntent(Intent var0) {
      ArrayList var1 = null;
      if (var0 == null) {
         return null;
      } else {
         byte var2 = -1;
         int var3 = var0.getIntExtra("gms_error_code", -1);
         int var4 = var0.getIntExtra("com.google.android.location.intent.extra.transition", -1);
         int var5 = var2;
         if (var4 != -1) {
            label29: {
               if (var4 != 1 && var4 != 2) {
                  var5 = var2;
                  if (var4 != 4) {
                     break label29;
                  }
               }

               var5 = var4;
            }
         }

         ArrayList var6 = (ArrayList)var0.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
         if (var6 != null) {
            ArrayList var7 = new ArrayList(var6.size());
            var6 = (ArrayList)var6;
            int var9 = var6.size();
            var4 = 0;

            while(true) {
               var1 = var7;
               if (var4 >= var9) {
                  break;
               }

               Object var8 = var6.get(var4);
               ++var4;
               var7.add(zzbh.zza((byte[])var8));
            }
         }

         return new GeofencingEvent(var3, var5, var1, (Location)var0.getParcelableExtra("com.google.android.location.intent.extra.triggering_location"));
      }
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public int getGeofenceTransition() {
      return this.zzam;
   }

   public List<Geofence> getTriggeringGeofences() {
      return this.zzan;
   }

   public Location getTriggeringLocation() {
      return this.zzao;
   }

   public boolean hasError() {
      return this.errorCode != -1;
   }
}

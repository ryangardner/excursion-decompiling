package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.SparseIntArray;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;

public final class zaj {
   private final SparseIntArray zaa;
   private GoogleApiAvailabilityLight zab;

   public zaj() {
      this(GoogleApiAvailability.getInstance());
   }

   public zaj(GoogleApiAvailabilityLight var1) {
      this.zaa = new SparseIntArray();
      Preconditions.checkNotNull(var1);
      this.zab = var1;
   }

   public final int zaa(Context var1, Api.Client var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      boolean var3 = var2.requiresGooglePlayServices();
      byte var4 = 0;
      if (!var3) {
         return 0;
      } else {
         int var5 = var2.getMinApkVersion();
         int var6 = this.zaa.get(var5, -1);
         if (var6 != -1) {
            return var6;
         } else {
            int var7 = 0;

            while(true) {
               if (var7 >= this.zaa.size()) {
                  var7 = var6;
                  break;
               }

               int var8 = this.zaa.keyAt(var7);
               if (var8 > var5 && this.zaa.get(var8) == 0) {
                  var7 = var4;
                  break;
               }

               ++var7;
            }

            int var9 = var7;
            if (var7 == -1) {
               var9 = this.zab.isGooglePlayServicesAvailable(var1, var5);
            }

            this.zaa.put(var5, var9);
            return var9;
         }
      }
   }

   public final void zaa() {
      this.zaa.clear();
   }
}

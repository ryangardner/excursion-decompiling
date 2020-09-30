package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

final class zaai extends zaan {
   // $FF: synthetic field
   final zaad zaa;
   private final Map<Api.Client, zaaf> zab;

   public zaai(zaad var1, Map var2) {
      super(var1, (zaag)null);
      this.zaa = var1;
      this.zab = var2;
   }

   public final void zaa() {
      com.google.android.gms.common.internal.zaj var1 = new com.google.android.gms.common.internal.zaj(zaad.zab(this.zaa));
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      Iterator var4 = this.zab.keySet().iterator();

      while(true) {
         while(var4.hasNext()) {
            Api.Client var5 = (Api.Client)var4.next();
            if (var5.requiresGooglePlayServices() && !zaaf.zaa((zaaf)this.zab.get(var5))) {
               var2.add(var5);
            } else {
               var3.add(var5);
            }
         }

         int var6 = -1;
         boolean var7 = var2.isEmpty();
         byte var8 = 0;
         int var9 = 0;
         int var10;
         Object var12;
         Api.Client var13;
         ArrayList var15;
         int var18;
         if (var7) {
            var15 = (ArrayList)var3;
            var10 = var15.size();

            while(var9 < var10) {
               var12 = var15.get(var9);
               ++var9;
               var13 = (Api.Client)var12;
               var18 = var1.zaa(zaad.zaa(this.zaa), var13);
               var6 = var18;
               if (var18 == 0) {
                  var6 = var18;
                  break;
               }
            }
         } else {
            var15 = (ArrayList)var2;
            var10 = var15.size();
            var9 = var8;

            while(var9 < var10) {
               var12 = var15.get(var9);
               ++var9;
               var13 = (Api.Client)var12;
               var18 = var1.zaa(zaad.zaa(this.zaa), var13);
               var6 = var18;
               if (var18 != 0) {
                  var6 = var18;
                  break;
               }
            }
         }

         if (var6 != 0) {
            ConnectionResult var11 = new ConnectionResult(var6, (PendingIntent)null);
            zaad.zad(this.zaa).zaa((zaba)(new zaah(this, this.zaa, var11)));
            return;
         }

         if (zaad.zae(this.zaa) && zaad.zaf(this.zaa) != null) {
            zaad.zaf(this.zaa).zab();
         }

         Iterator var17 = this.zab.keySet().iterator();

         while(true) {
            while(var17.hasNext()) {
               Api.Client var16 = (Api.Client)var17.next();
               BaseGmsClient.ConnectionProgressReportCallbacks var14 = (BaseGmsClient.ConnectionProgressReportCallbacks)this.zab.get(var16);
               if (var16.requiresGooglePlayServices() && var1.zaa(zaad.zaa(this.zaa), var16) != 0) {
                  zaad.zad(this.zaa).zaa((zaba)(new zaak(this, this.zaa, var14)));
               } else {
                  var16.connect(var14);
               }
            }

            return;
         }
      }
   }
}

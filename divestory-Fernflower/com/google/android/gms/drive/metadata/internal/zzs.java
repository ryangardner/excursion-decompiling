package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;

public final class zzs extends com.google.android.gms.drive.metadata.zzb<String> {
   public zzs(String var1, int var2) {
      super(var1, Collections.singleton(var1), Collections.emptySet(), 4300000);
   }

   // $FF: synthetic method
   protected final void zza(Bundle var1, Object var2) {
      Collection var3 = (Collection)var2;
      var1.putStringArrayList(this.getName(), new ArrayList(var3));
   }

   // $FF: synthetic method
   protected final Object zzb(Bundle var1) {
      return var1.getStringArrayList(this.getName());
   }

   // $FF: synthetic method
   protected final Object zzc(DataHolder var1, int var2, int var3) {
      return this.zzd(var1, var2, var3);
   }

   protected final Collection<String> zzd(DataHolder var1, int var2, int var3) {
      JSONException var10000;
      label44: {
         String var4;
         boolean var10001;
         try {
            var4 = var1.getString(this.getName(), var2, var3);
         } catch (JSONException var9) {
            var10000 = var9;
            var10001 = false;
            break label44;
         }

         if (var4 == null) {
            return null;
         }

         JSONArray var5;
         ArrayList var10;
         try {
            var10 = new ArrayList();
            var5 = new JSONArray(var4);
         } catch (JSONException var8) {
            var10000 = var8;
            var10001 = false;
            break label44;
         }

         var2 = 0;

         while(true) {
            try {
               if (var2 >= var5.length()) {
                  break;
               }

               var10.add(var5.getString(var2));
            } catch (JSONException var7) {
               var10000 = var7;
               var10001 = false;
               break label44;
            }

            ++var2;
         }

         try {
            Collection var12 = Collections.unmodifiableCollection(var10);
            return var12;
         } catch (JSONException var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      JSONException var11 = var10000;
      throw new IllegalStateException("DataHolder supplied invalid JSON", var11);
   }
}

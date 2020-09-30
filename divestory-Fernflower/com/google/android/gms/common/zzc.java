package com.google.android.gms.common;

import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import com.google.android.gms.common.internal.zzr;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
final class zzc {
   private static volatile zzr zza;
   private static final Object zzb = new Object();
   private static Context zzc;

   static zzl zza(String var0, zzd var1, boolean var2, boolean var3) {
      ThreadPolicy var4 = StrictMode.allowThreadDiskReads();

      zzl var7;
      try {
         var7 = zzb(var0, var1, var2, var3);
      } finally {
         StrictMode.setThreadPolicy(var4);
      }

      return var7;
   }

   // $FF: synthetic method
   static final String zza(boolean var0, String var1, zzd var2) throws Exception {
      boolean var3 = true;
      if (var0 || !zzb(var1, var2, true, false).zza) {
         var3 = false;
      }

      return zzl.zza(var1, var2, var0, var3);
   }

   static void zza(Context var0) {
      synchronized(zzc.class){}

      label136: {
         Throwable var10000;
         label135: {
            boolean var10001;
            label140: {
               try {
                  if (zzc != null) {
                     break label140;
                  }
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label135;
               }

               if (var0 == null) {
                  break label136;
               }

               try {
                  zzc = var0.getApplicationContext();
               } catch (Throwable var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label135;
               }

               return;
            }

            label129:
            try {
               Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
               break label136;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               break label129;
            }
         }

         Throwable var13 = var10000;
         throw var13;
      }

   }

   private static zzl zzb(String param0, zzd param1, boolean param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }
}

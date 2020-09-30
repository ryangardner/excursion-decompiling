package com.google.android.gms.common.wrappers;

import android.content.Context;

public class Wrappers {
   private static Wrappers zzb = new Wrappers();
   private PackageManagerWrapper zza = null;

   public static PackageManagerWrapper packageManager(Context var0) {
      return zzb.zza(var0);
   }

   private final PackageManagerWrapper zza(Context var1) {
      synchronized(this){}

      Throwable var10000;
      label186: {
         boolean var10001;
         label191: {
            label183: {
               try {
                  if (this.zza != null) {
                     break label191;
                  }

                  if (var1.getApplicationContext() == null) {
                     break label183;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label186;
               }

               try {
                  var1 = var1.getApplicationContext();
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label186;
               }
            }

            try {
               PackageManagerWrapper var2 = new PackageManagerWrapper(var1);
               this.zza = var2;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label186;
            }
         }

         label172:
         try {
            PackageManagerWrapper var24 = this.zza;
            return var24;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            break label172;
         }
      }

      Throwable var23 = var10000;
      throw var23;
   }
}

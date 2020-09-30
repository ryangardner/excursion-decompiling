package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import java.util.HashMap;

final class zzh implements Callback {
   // $FF: synthetic field
   private final zzg zza;

   private zzh(zzg var1) {
      this.zza = var1;
   }

   // $FF: synthetic method
   zzh(zzg var1, zzf var2) {
      this(var1);
   }

   public final boolean handleMessage(Message var1) {
      int var2 = var1.what;
      Throwable var10000;
      boolean var10001;
      Throwable var164;
      if (var2 != 0) {
         if (var2 != 1) {
            return false;
         } else {
            HashMap var168 = zzg.zza(this.zza);
            synchronized(var168){}

            label1390: {
               GmsClientSupervisor.zza var4;
               zzi var5;
               try {
                  var4 = (GmsClientSupervisor.zza)var1.obj;
                  var5 = (zzi)zzg.zza(this.zza).get(var4);
               } catch (Throwable var158) {
                  var10000 = var158;
                  var10001 = false;
                  break label1390;
               }

               if (var5 != null) {
                  label1391: {
                     ComponentName var171;
                     try {
                        if (var5.zzb() != 3) {
                           break label1391;
                        }

                        String var165 = String.valueOf(var4);
                        var2 = String.valueOf(var165).length();
                        StringBuilder var169 = new StringBuilder(var2 + 47);
                        var169.append("Timeout waiting for ServiceConnection callback ");
                        var169.append(var165);
                        String var170 = var169.toString();
                        Exception var166 = new Exception();
                        Log.e("GmsClientSupervisor", var170, var166);
                        var171 = var5.zze();
                     } catch (Throwable var157) {
                        var10000 = var157;
                        var10001 = false;
                        break label1390;
                     }

                     ComponentName var167 = var171;
                     if (var171 == null) {
                        try {
                           var167 = var4.zzb();
                        } catch (Throwable var156) {
                           var10000 = var156;
                           var10001 = false;
                           break label1390;
                        }
                     }

                     var171 = var167;
                     if (var167 == null) {
                        try {
                           var171 = new ComponentName((String)Preconditions.checkNotNull(var4.zza()), "unknown");
                        } catch (Throwable var155) {
                           var10000 = var155;
                           var10001 = false;
                           break label1390;
                        }
                     }

                     try {
                        var5.onServiceDisconnected(var171);
                     } catch (Throwable var154) {
                        var10000 = var154;
                        var10001 = false;
                        break label1390;
                     }
                  }
               }

               label1343:
               try {
                  return true;
               } catch (Throwable var153) {
                  var10000 = var153;
                  var10001 = false;
                  break label1343;
               }
            }

            while(true) {
               var164 = var10000;

               try {
                  throw var164;
               } catch (Throwable var152) {
                  var10000 = var152;
                  var10001 = false;
                  continue;
               }
            }
         }
      } else {
         HashMap var6 = zzg.zza(this.zza);
         synchronized(var6){}

         label1392: {
            zzi var3;
            GmsClientSupervisor.zza var163;
            try {
               var163 = (GmsClientSupervisor.zza)var1.obj;
               var3 = (zzi)zzg.zza(this.zza).get(var163);
            } catch (Throwable var162) {
               var10000 = var162;
               var10001 = false;
               break label1392;
            }

            if (var3 != null) {
               label1378: {
                  try {
                     if (!var3.zzc()) {
                        break label1378;
                     }

                     if (var3.zza()) {
                        var3.zzb("GmsClientSupervisor");
                     }
                  } catch (Throwable var161) {
                     var10000 = var161;
                     var10001 = false;
                     break label1392;
                  }

                  try {
                     zzg.zza(this.zza).remove(var163);
                  } catch (Throwable var160) {
                     var10000 = var160;
                     var10001 = false;
                     break label1392;
                  }
               }
            }

            label1371:
            try {
               return true;
            } catch (Throwable var159) {
               var10000 = var159;
               var10001 = false;
               break label1371;
            }
         }

         while(true) {
            var164 = var10000;

            try {
               throw var164;
            } catch (Throwable var151) {
               var10000 = var151;
               var10001 = false;
               continue;
            }
         }
      }
   }
}

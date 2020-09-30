package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.HashMap;

final class zzg extends GmsClientSupervisor {
   private final HashMap<GmsClientSupervisor.zza, zzi> zza = new HashMap();
   private final Context zzb;
   private final Handler zzc;
   private final ConnectionTracker zzd;
   private final long zze;
   private final long zzf;

   zzg(Context var1) {
      this.zzb = var1.getApplicationContext();
      this.zzc = new com.google.android.gms.internal.common.zzi(var1.getMainLooper(), new zzh(this, (zzf)null));
      this.zzd = ConnectionTracker.getInstance();
      this.zze = 5000L;
      this.zzf = 300000L;
   }

   // $FF: synthetic method
   static HashMap zza(zzg var0) {
      return var0.zza;
   }

   // $FF: synthetic method
   static Handler zzb(zzg var0) {
      return var0.zzc;
   }

   // $FF: synthetic method
   static Context zzc(zzg var0) {
      return var0.zzb;
   }

   // $FF: synthetic method
   static ConnectionTracker zzd(zzg var0) {
      return var0.zzd;
   }

   // $FF: synthetic method
   static long zze(zzg var0) {
      return var0.zzf;
   }

   protected final boolean zza(GmsClientSupervisor.zza var1, ServiceConnection var2, String var3) {
      Preconditions.checkNotNull(var2, "ServiceConnection must not be null");
      HashMap var4 = this.zza;
      synchronized(var4){}

      Throwable var10000;
      boolean var10001;
      label617: {
         zzi var5;
         try {
            var5 = (zzi)this.zza.get(var1);
         } catch (Throwable var79) {
            var10000 = var79;
            var10001 = false;
            break label617;
         }

         int var6;
         label609: {
            zzi var80;
            if (var5 == null) {
               try {
                  var5 = new zzi(this, var1);
                  var5.zza(var2, var2, var3);
                  var5.zza(var3);
                  this.zza.put(var1, var5);
               } catch (Throwable var77) {
                  var10000 = var77;
                  var10001 = false;
                  break label617;
               }

               var80 = var5;
            } else {
               try {
                  this.zzc.removeMessages(0, var1);
                  if (var5.zza(var2)) {
                     break label609;
                  }

                  var5.zza(var2, var2, var3);
                  var6 = var5.zzb();
               } catch (Throwable var78) {
                  var10000 = var78;
                  var10001 = false;
                  break label617;
               }

               if (var6 != 1) {
                  if (var6 != 2) {
                     var80 = var5;
                  } else {
                     try {
                        var5.zza(var3);
                     } catch (Throwable var76) {
                        var10000 = var76;
                        var10001 = false;
                        break label617;
                     }

                     var80 = var5;
                  }
               } else {
                  try {
                     var2.onServiceConnected(var5.zze(), var5.zzd());
                  } catch (Throwable var75) {
                     var10000 = var75;
                     var10001 = false;
                     break label617;
                  }

                  var80 = var5;
               }
            }

            try {
               boolean var7 = var80.zza();
               return var7;
            } catch (Throwable var73) {
               var10000 = var73;
               var10001 = false;
               break label617;
            }
         }

         label593:
         try {
            var3 = String.valueOf(var1);
            var6 = String.valueOf(var3).length();
            StringBuilder var83 = new StringBuilder(var6 + 81);
            var83.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
            var83.append(var3);
            IllegalStateException var81 = new IllegalStateException(var83.toString());
            throw var81;
         } catch (Throwable var74) {
            var10000 = var74;
            var10001 = false;
            break label593;
         }
      }

      while(true) {
         Throwable var82 = var10000;

         try {
            throw var82;
         } catch (Throwable var72) {
            var10000 = var72;
            var10001 = false;
            continue;
         }
      }
   }

   protected final void zzb(GmsClientSupervisor.zza var1, ServiceConnection var2, String var3) {
      Preconditions.checkNotNull(var2, "ServiceConnection must not be null");
      HashMap var4 = this.zza;
      synchronized(var4){}

      Throwable var10000;
      boolean var10001;
      label368: {
         zzi var5;
         try {
            var5 = (zzi)this.zza.get(var1);
         } catch (Throwable var48) {
            var10000 = var48;
            var10001 = false;
            break label368;
         }

         int var6;
         IllegalStateException var53;
         if (var5 != null) {
            label362: {
               label361: {
                  try {
                     if (var5.zza(var2)) {
                        var5.zza(var2, var3);
                        if (var5.zzc()) {
                           Message var50 = this.zzc.obtainMessage(0, var1);
                           this.zzc.sendMessageDelayed(var50, this.zze);
                        }
                        break label361;
                     }
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label362;
                  }

                  try {
                     var3 = String.valueOf(var1);
                     var6 = String.valueOf(var3).length();
                     StringBuilder var49 = new StringBuilder(var6 + 76);
                     var49.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                     var49.append(var3);
                     var53 = new IllegalStateException(var49.toString());
                     throw var53;
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break label362;
                  }
               }

               label352:
               try {
                  return;
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label352;
               }
            }
         } else {
            label364:
            try {
               String var52 = String.valueOf(var1);
               var6 = String.valueOf(var52).length();
               StringBuilder var54 = new StringBuilder(var6 + 50);
               var54.append("Nonexistent connection status for service config: ");
               var54.append(var52);
               var53 = new IllegalStateException(var54.toString());
               throw var53;
            } catch (Throwable var47) {
               var10000 = var47;
               var10001 = false;
               break label364;
            }
         }
      }

      while(true) {
         Throwable var51 = var10000;

         try {
            throw var51;
         } catch (Throwable var43) {
            var10000 = var43;
            var10001 = false;
            continue;
         }
      }
   }
}

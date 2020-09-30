package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class zzi implements ServiceConnection, zzk {
   private final Map<ServiceConnection, ServiceConnection> zza;
   private int zzb;
   private boolean zzc;
   private IBinder zzd;
   private final GmsClientSupervisor.zza zze;
   private ComponentName zzf;
   // $FF: synthetic field
   private final zzg zzg;

   public zzi(zzg var1, GmsClientSupervisor.zza var2) {
      this.zzg = var1;
      this.zze = var2;
      this.zza = new HashMap();
      this.zzb = 2;
   }

   public final void onServiceConnected(ComponentName var1, IBinder var2) {
      HashMap var3 = com.google.android.gms.common.internal.zzg.zza(this.zzg);
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label198: {
         Iterator var4;
         try {
            com.google.android.gms.common.internal.zzg.zzb(this.zzg).removeMessages(1, this.zze);
            this.zzd = var2;
            this.zzf = var1;
            var4 = this.zza.values().iterator();
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label198;
         }

         while(true) {
            try {
               if (var4.hasNext()) {
                  ((ServiceConnection)var4.next()).onServiceConnected(var1, var2);
                  continue;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break;
            }

            try {
               this.zzb = 1;
               return;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break;
            }
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   public final void onServiceDisconnected(ComponentName var1) {
      HashMap var2 = com.google.android.gms.common.internal.zzg.zza(this.zzg);
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label198: {
         Iterator var3;
         try {
            com.google.android.gms.common.internal.zzg.zzb(this.zzg).removeMessages(1, this.zze);
            this.zzd = null;
            this.zzf = var1;
            var3 = this.zza.values().iterator();
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label198;
         }

         while(true) {
            try {
               if (var3.hasNext()) {
                  ((ServiceConnection)var3.next()).onServiceDisconnected(var1);
                  continue;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break;
            }

            try {
               this.zzb = 2;
               return;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break;
            }
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zza(ServiceConnection var1, ServiceConnection var2, String var3) {
      this.zza.put(var1, var2);
   }

   public final void zza(ServiceConnection var1, String var2) {
      this.zza.remove(var1);
   }

   public final void zza(String var1) {
      this.zzb = 3;
      boolean var2 = com.google.android.gms.common.internal.zzg.zzd(this.zzg).zza(com.google.android.gms.common.internal.zzg.zzc(this.zzg), var1, this.zze.zza(com.google.android.gms.common.internal.zzg.zzc(this.zzg)), this, this.zze.zzc());
      this.zzc = var2;
      if (var2) {
         Message var4 = com.google.android.gms.common.internal.zzg.zzb(this.zzg).obtainMessage(1, this.zze);
         com.google.android.gms.common.internal.zzg.zzb(this.zzg).sendMessageDelayed(var4, com.google.android.gms.common.internal.zzg.zze(this.zzg));
      } else {
         this.zzb = 2;

         try {
            com.google.android.gms.common.internal.zzg.zzd(this.zzg).unbindService(com.google.android.gms.common.internal.zzg.zzc(this.zzg), this);
         } catch (IllegalArgumentException var3) {
         }

      }
   }

   public final boolean zza() {
      return this.zzc;
   }

   public final boolean zza(ServiceConnection var1) {
      return this.zza.containsKey(var1);
   }

   public final int zzb() {
      return this.zzb;
   }

   public final void zzb(String var1) {
      com.google.android.gms.common.internal.zzg.zzb(this.zzg).removeMessages(1, this.zze);
      com.google.android.gms.common.internal.zzg.zzd(this.zzg).unbindService(com.google.android.gms.common.internal.zzg.zzc(this.zzg), this);
      this.zzc = false;
      this.zzb = 2;
   }

   public final boolean zzc() {
      return this.zza.isEmpty();
   }

   public final IBinder zzd() {
      return this.zzd;
   }

   public final ComponentName zze() {
      return this.zzf;
   }
}

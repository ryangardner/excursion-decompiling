package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.util.Log;

public abstract class GmsClientSupervisor {
   private static int zza;
   private static final Object zzb = new Object();
   private static GmsClientSupervisor zzc;

   public static int getDefaultBindFlags() {
      return zza;
   }

   public static GmsClientSupervisor getInstance(Context var0) {
      Object var1 = zzb;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label131: {
         try {
            if (zzc == null) {
               zzg var2 = new zzg(var0.getApplicationContext());
               zzc = var2;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label131;
         }

         label128:
         try {
            return zzc;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label128;
         }
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public boolean bindService(ComponentName var1, ServiceConnection var2, String var3) {
      return this.zza(new GmsClientSupervisor.zza(var1, getDefaultBindFlags()), var2, var3);
   }

   public boolean bindService(String var1, ServiceConnection var2, String var3) {
      return this.zza(new GmsClientSupervisor.zza(var1, getDefaultBindFlags()), var2, var3);
   }

   public void unbindService(ComponentName var1, ServiceConnection var2, String var3) {
      this.zzb(new GmsClientSupervisor.zza(var1, getDefaultBindFlags()), var2, var3);
   }

   public void unbindService(String var1, ServiceConnection var2, String var3) {
      this.zzb(new GmsClientSupervisor.zza(var1, getDefaultBindFlags()), var2, var3);
   }

   public final void zza(String var1, String var2, int var3, ServiceConnection var4, String var5, boolean var6) {
      this.zzb(new GmsClientSupervisor.zza(var1, var2, var3, var6), var4, var5);
   }

   protected abstract boolean zza(GmsClientSupervisor.zza var1, ServiceConnection var2, String var3);

   protected abstract void zzb(GmsClientSupervisor.zza var1, ServiceConnection var2, String var3);

   protected static final class zza {
      private static final Uri zzf = (new Builder()).scheme("content").authority("com.google.android.gms.chimera").build();
      private final String zza;
      private final String zzb;
      private final ComponentName zzc;
      private final int zzd;
      private final boolean zze;

      public zza(ComponentName var1, int var2) {
         this.zza = null;
         this.zzb = null;
         this.zzc = (ComponentName)Preconditions.checkNotNull(var1);
         this.zzd = var2;
         this.zze = false;
      }

      public zza(String var1, int var2) {
         this(var1, "com.google.android.gms", var2);
      }

      private zza(String var1, String var2, int var3) {
         this(var1, var2, var3, false);
      }

      public zza(String var1, String var2, int var3, boolean var4) {
         this.zza = Preconditions.checkNotEmpty(var1);
         this.zzb = Preconditions.checkNotEmpty(var2);
         this.zzc = null;
         this.zzd = var3;
         this.zze = var4;
      }

      private final Intent zzb(Context var1) {
         Bundle var2 = new Bundle();
         var2.putString("serviceActionBundleKey", this.zza);
         String var3 = null;

         Bundle var6;
         try {
            var6 = var1.getContentResolver().call(zzf, "serviceIntentCall", (String)null, var2);
         } catch (IllegalArgumentException var4) {
            String var7 = String.valueOf(var4);
            StringBuilder var5 = new StringBuilder(String.valueOf(var7).length() + 34);
            var5.append("Dynamic intent resolution failed: ");
            var5.append(var7);
            Log.w("ConnectionStatusConfig", var5.toString());
            var6 = null;
         }

         Intent var8;
         if (var6 == null) {
            var8 = var3;
         } else {
            var8 = (Intent)var6.getParcelable("serviceResponseIntentKey");
         }

         if (var8 == null) {
            var3 = String.valueOf(this.zza);
            if (var3.length() != 0) {
               var3 = "Dynamic lookup for intent failed for action: ".concat(var3);
            } else {
               var3 = new String("Dynamic lookup for intent failed for action: ");
            }

            Log.w("ConnectionStatusConfig", var3);
         }

         return var8;
      }

      public final boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GmsClientSupervisor.zza)) {
            return false;
         } else {
            GmsClientSupervisor.zza var2 = (GmsClientSupervisor.zza)var1;
            return Objects.equal(this.zza, var2.zza) && Objects.equal(this.zzb, var2.zzb) && Objects.equal(this.zzc, var2.zzc) && this.zzd == var2.zzd && this.zze == var2.zze;
         }
      }

      public final int hashCode() {
         return Objects.hashCode(this.zza, this.zzb, this.zzc, this.zzd, this.zze);
      }

      public final String toString() {
         String var1 = this.zza;
         String var2 = var1;
         if (var1 == null) {
            Preconditions.checkNotNull(this.zzc);
            var2 = this.zzc.flattenToString();
         }

         return var2;
      }

      public final Intent zza(Context var1) {
         Intent var3;
         if (this.zza != null) {
            Intent var2;
            if (this.zze) {
               var2 = this.zzb(var1);
            } else {
               var2 = null;
            }

            var3 = var2;
            if (var2 == null) {
               var3 = (new Intent(this.zza)).setPackage(this.zzb);
            }
         } else {
            var3 = (new Intent()).setComponent(this.zzc);
         }

         return var3;
      }

      public final String zza() {
         return this.zzb;
      }

      public final ComponentName zzb() {
         return this.zzc;
      }

      public final int zzc() {
         return this.zzd;
      }
   }
}

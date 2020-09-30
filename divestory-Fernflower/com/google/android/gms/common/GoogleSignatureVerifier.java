package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
public class GoogleSignatureVerifier {
   @Nullable
   private static GoogleSignatureVerifier zza;
   private final Context zzb;
   private volatile String zzc;

   private GoogleSignatureVerifier(Context var1) {
      this.zzb = var1.getApplicationContext();
   }

   public static GoogleSignatureVerifier getInstance(Context var0) {
      Preconditions.checkNotNull(var0);
      synchronized(GoogleSignatureVerifier.class){}

      Throwable var10000;
      boolean var10001;
      label131: {
         try {
            if (zza == null) {
               com.google.android.gms.common.zzc.zza(var0);
               GoogleSignatureVerifier var1 = new GoogleSignatureVerifier(var0);
               zza = var1;
            }
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label131;
         }

         label128:
         try {
            return zza;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label128;
         }
      }

      while(true) {
         Throwable var14 = var10000;

         try {
            throw var14;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            continue;
         }
      }
   }

   @Nullable
   private static zzd zza(PackageInfo var0, zzd... var1) {
      if (var0.signatures == null) {
         return null;
      } else if (var0.signatures.length != 1) {
         Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
         return null;
      } else {
         Signature[] var3 = var0.signatures;
         int var2 = 0;

         for(zzg var4 = new zzg(var3[0].toByteArray()); var2 < var1.length; ++var2) {
            if (var1[var2].equals(var4)) {
               return var1[var2];
            }
         }

         return null;
      }
   }

   private final zzl zza(String var1, boolean var2, boolean var3) {
      if (var1 == null) {
         return zzl.zza("null pkg");
      } else if (var1.equals(this.zzc)) {
         return zzl.zza();
      } else {
         PackageInfo var4;
         try {
            var4 = this.zzb.getPackageManager().getPackageInfo(var1, 64);
         } catch (NameNotFoundException var8) {
            var1 = String.valueOf(var1);
            if (var1.length() != 0) {
               var1 = "no pkg ".concat(var1);
            } else {
               var1 = new String("no pkg ");
            }

            return zzl.zza(var1, var8);
         }

         var2 = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb);
         zzl var5;
         if (var4 == null) {
            var5 = zzl.zza("null pkg");
         } else if (var4.signatures != null && var4.signatures.length == 1) {
            zzg var6 = new zzg(var4.signatures[0].toByteArray());
            String var7 = var4.packageName;
            var5 = com.google.android.gms.common.zzc.zza(var7, var6, var2, false);
            if (var5.zza && var4.applicationInfo != null && (var4.applicationInfo.flags & 2) != 0 && com.google.android.gms.common.zzc.zza(var7, var6, false, true).zza) {
               var5 = zzl.zza("debuggable release cert app rejected");
            }
         } else {
            var5 = zzl.zza("single cert required");
         }

         if (var5.zza) {
            this.zzc = var1;
         }

         return var5;
      }
   }

   public static boolean zza(PackageInfo var0, boolean var1) {
      if (var0 != null && var0.signatures != null) {
         zzd var2;
         if (var1) {
            var2 = zza(var0, zzi.zza);
         } else {
            var2 = zza(var0, zzi.zza[0]);
         }

         if (var2 != null) {
            return true;
         }
      }

      return false;
   }

   public boolean isGooglePublicSignedPackage(PackageInfo var1) {
      if (var1 == null) {
         return false;
      } else if (zza(var1, false)) {
         return true;
      } else {
         if (zza(var1, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb)) {
               return true;
            }

            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
         }

         return false;
      }
   }

   public boolean isPackageGoogleSigned(String var1) {
      zzl var2 = this.zza(var1, false, false);
      var2.zzc();
      return var2.zza;
   }

   public boolean isUidGoogleSigned(int var1) {
      String[] var2 = this.zzb.getPackageManager().getPackagesForUid(var1);
      zzl var3;
      if (var2 != null && var2.length != 0) {
         var3 = null;
         int var4 = var2.length;
         var1 = 0;

         while(true) {
            if (var1 >= var4) {
               var3 = (zzl)Preconditions.checkNotNull(var3);
               break;
            }

            var3 = this.zza(var2[var1], false, false);
            if (var3.zza) {
               break;
            }

            ++var1;
         }
      } else {
         var3 = zzl.zza("no pkgs");
      }

      var3.zzc();
      return var3.zza;
   }
}

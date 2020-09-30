package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.wrappers.Wrappers;

public final class UidVerifier {
   private UidVerifier() {
   }

   public static boolean isGooglePlayServicesUid(Context var0, int var1) {
      if (!uidHasPackageName(var0, var1, "com.google.android.gms")) {
         return false;
      } else {
         PackageManager var2 = var0.getPackageManager();

         PackageInfo var4;
         try {
            var4 = var2.getPackageInfo("com.google.android.gms", 64);
         } catch (NameNotFoundException var3) {
            if (Log.isLoggable("UidVerifier", 3)) {
               Log.d("UidVerifier", "Package manager can't find google play services package, defaulting to false");
            }

            return false;
         }

         return GoogleSignatureVerifier.getInstance(var0).isGooglePublicSignedPackage(var4);
      }
   }

   public static boolean uidHasPackageName(Context var0, int var1, String var2) {
      return Wrappers.packageManager(var0).zza(var1, var2);
   }
}

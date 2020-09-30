package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.common.zzl;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AndroidUtilsLight {
   private static volatile int zza;

   @Deprecated
   public static Context getDeviceProtectedStorageContext(Context var0) {
      Context var1 = var0;
      if (zzl.zza()) {
         var1 = zzl.zza(var0);
      }

      return var1;
   }

   @Deprecated
   public static byte[] getPackageCertificateHashBytes(Context var0, String var1) throws NameNotFoundException {
      PackageInfo var2 = Wrappers.packageManager(var0).getPackageInfo(var1, 64);
      if (var2.signatures != null && var2.signatures.length == 1) {
         MessageDigest var3 = zza("SHA1");
         if (var3 != null) {
            return var3.digest(var2.signatures[0].toByteArray());
         }
      }

      return null;
   }

   public static MessageDigest zza(String var0) {
      for(int var1 = 0; var1 < 2; ++var1) {
         MessageDigest var2;
         try {
            var2 = MessageDigest.getInstance(var0);
         } catch (NoSuchAlgorithmException var3) {
            continue;
         }

         if (var2 != null) {
            return var2;
         }
      }

      return null;
   }
}

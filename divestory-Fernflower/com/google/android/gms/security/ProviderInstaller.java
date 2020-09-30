package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.dynamite.DynamiteModule;
import java.lang.reflect.Method;

public class ProviderInstaller {
   public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
   private static final GoogleApiAvailabilityLight zza = GoogleApiAvailabilityLight.getInstance();
   private static final Object zzb = new Object();
   private static Method zzc = null;

   public static void installIfNeeded(Context param0) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
      // $FF: Couldn't be decompiled
   }

   public static void installIfNeededAsync(Context var0, ProviderInstaller.ProviderInstallListener var1) {
      Preconditions.checkNotNull(var0, "Context must not be null");
      Preconditions.checkNotNull(var1, "Listener must not be null");
      Preconditions.checkMainThread("Must be called on the UI thread");
      (new zza(var0, var1)).execute(new Void[0]);
   }

   private static Context zza(Context var0) {
      try {
         var0 = DynamiteModule.load(var0, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.providerinstaller").getModuleContext();
         return var0;
      } catch (DynamiteModule.LoadingException var1) {
         String var2 = String.valueOf(var1.getMessage());
         if (var2.length() != 0) {
            var2 = "Failed to load providerinstaller module: ".concat(var2);
         } else {
            var2 = new String("Failed to load providerinstaller module: ");
         }

         Log.w("ProviderInstaller", var2);
         return null;
      }
   }

   // $FF: synthetic method
   static GoogleApiAvailabilityLight zza() {
      return zza;
   }

   private static Context zzb(Context var0) {
      try {
         Context var4 = GooglePlayServicesUtilLight.getRemoteContext(var0);
         return var4;
      } catch (NotFoundException var3) {
         String var1 = String.valueOf(var3.getMessage());
         if (var1.length() != 0) {
            var1 = "Failed to load GMS Core context for providerinstaller: ".concat(var1);
         } else {
            var1 = new String("Failed to load GMS Core context for providerinstaller: ");
         }

         Log.w("ProviderInstaller", var1);
         CrashUtils.addDynamiteErrorToDropBox(var0, var3);
         return null;
      }
   }

   public interface ProviderInstallListener {
      void onProviderInstallFailed(int var1, Intent var2);

      void onProviderInstalled();
   }
}

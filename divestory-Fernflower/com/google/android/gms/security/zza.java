package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

final class zza extends AsyncTask<Void, Void, Integer> {
   // $FF: synthetic field
   private final Context zza;
   // $FF: synthetic field
   private final ProviderInstaller.ProviderInstallListener zzb;

   zza(Context var1, ProviderInstaller.ProviderInstallListener var2) {
      this.zza = var1;
      this.zzb = var2;
   }

   private final Integer zza(Void... var1) {
      try {
         ProviderInstaller.installIfNeeded(this.zza);
      } catch (GooglePlayServicesRepairableException var2) {
         return var2.getConnectionStatusCode();
      } catch (GooglePlayServicesNotAvailableException var3) {
         return var3.errorCode;
      }

      return 0;
   }

   // $FF: synthetic method
   protected final Object doInBackground(Object[] var1) {
      return this.zza((Void[])var1);
   }

   // $FF: synthetic method
   protected final void onPostExecute(Object var1) {
      Integer var3 = (Integer)var1;
      if (var3 == 0) {
         this.zzb.onProviderInstalled();
      } else {
         Intent var2 = ProviderInstaller.zza().getErrorResolutionIntent(this.zza, var3, "pi");
         this.zzb.onProviderInstallFailed(var3, var2);
      }
   }
}

package com.google.android.gms.drive;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.drive.zzaw;

final class zzf extends Api.AbstractClientBuilder<zzaw, Drive.zzb> {
   // $FF: synthetic method
   public final Api.Client buildClient(Context var1, Looper var2, ClientSettings var3, Object var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
      if ((Drive.zzb)var4 == null) {
         return new zzaw(var1, var2, var3, var5, var6, new Bundle());
      } else {
         throw new NoSuchMethodError();
      }
   }
}

package com.google.android.gms.location;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.location.zzaz;

final class zza extends Api.AbstractClientBuilder<zzaz, Api.ApiOptions.NoOptions> {
   // $FF: synthetic method
   public final Api.Client buildClient(Context var1, Looper var2, ClientSettings var3, Object var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
      return new zzaz(var1, var2, var5, var6, "activity_recognition");
   }
}

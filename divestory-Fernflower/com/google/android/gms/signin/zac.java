package com.google.android.gms.signin;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.internal.SignInClientImpl;

final class zac extends Api.AbstractClientBuilder<SignInClientImpl, SignInOptions> {
   // $FF: synthetic method
   public final Api.Client buildClient(Context var1, Looper var2, ClientSettings var3, Object var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
      SignInOptions var7 = (SignInOptions)var4;
      SignInOptions var8 = var7;
      if (var7 == null) {
         var8 = SignInOptions.zaa;
      }

      return new SignInClientImpl(var1, var2, true, var3, var8, var5, var6);
   }
}

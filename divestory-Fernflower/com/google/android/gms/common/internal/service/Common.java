package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.Api;

public final class Common {
   public static final Api<Api.ApiOptions.NoOptions> API;
   public static final Api.ClientKey<zaj> CLIENT_KEY = new Api.ClientKey();
   public static final zad zaa;
   private static final Api.AbstractClientBuilder<zaj, Api.ApiOptions.NoOptions> zab = new zab();

   static {
      API = new Api("Common.API", zab, CLIENT_KEY);
      zaa = new zac();
   }
}

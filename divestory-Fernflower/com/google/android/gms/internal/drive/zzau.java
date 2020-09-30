package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.Drive;

public abstract class zzau<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zzaw> {
   public zzau(GoogleApiClient var1) {
      super((Api.AnyClientKey)Drive.CLIENT_KEY, var1);
   }
}

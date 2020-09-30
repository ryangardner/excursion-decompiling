package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;

public class ApiExceptionUtil {
   public static ApiException fromStatus(Status var0) {
      return (ApiException)(var0.hasResolution() ? new ResolvableApiException(var0) : new ApiException(var0));
   }
}

package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;

public class ApiExceptionMapper implements StatusExceptionMapper {
   public Exception getException(Status var1) {
      return ApiExceptionUtil.fromStatus(var1);
   }
}

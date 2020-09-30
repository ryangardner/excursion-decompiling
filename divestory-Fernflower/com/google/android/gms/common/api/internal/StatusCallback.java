package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Status;

public class StatusCallback extends IStatusCallback.Stub {
   private final BaseImplementation.ResultHolder<Status> mResultHolder;

   public StatusCallback(BaseImplementation.ResultHolder<Status> var1) {
      this.mResultHolder = var1;
   }

   public void onResult(Status var1) {
      this.mResultHolder.setResult(var1);
   }
}

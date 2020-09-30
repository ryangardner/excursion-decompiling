package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public class StatusPendingResult extends BasePendingResult<Status> {
   @Deprecated
   public StatusPendingResult(Looper var1) {
      super(var1);
   }

   public StatusPendingResult(GoogleApiClient var1) {
      super(var1);
   }

   // $FF: synthetic method
   protected Result createFailedResult(Status var1) {
      return var1;
   }
}

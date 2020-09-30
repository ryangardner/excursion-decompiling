package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final class zaci implements Continuation<Boolean, Void> {
   // $FF: synthetic method
   public final Object then(Task var1) throws Exception {
      if ((Boolean)var1.getResult()) {
         return null;
      } else {
         throw new ApiException(new Status(13, "listener already unregistered"));
      }
   }
}

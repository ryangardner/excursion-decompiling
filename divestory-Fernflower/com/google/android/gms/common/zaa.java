package com.google.android.gms.common;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

// $FF: synthetic class
final class zaa implements SuccessContinuation {
   static final SuccessContinuation zaa = new zaa();

   private zaa() {
   }

   public final Task then(Object var1) {
      return GoogleApiAvailability.zaa((Map)var1);
   }
}

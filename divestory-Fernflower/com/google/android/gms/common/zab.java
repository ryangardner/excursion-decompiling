package com.google.android.gms.common;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

// $FF: synthetic class
final class zab implements SuccessContinuation {
   static final SuccessContinuation zaa = new zab();

   private zab() {
   }

   public final Task then(Object var1) {
      return GoogleApiAvailability.zab((Map)var1);
   }
}

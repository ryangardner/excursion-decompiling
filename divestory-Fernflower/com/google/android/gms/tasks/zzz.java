package com.google.android.gms.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class zzz implements Continuation<Void, Task<List<Task<?>>>> {
   // $FF: synthetic field
   private final Collection zza;

   zzz(Collection var1) {
      this.zza = var1;
   }

   // $FF: synthetic method
   public final Object then(Task var1) throws Exception {
      ArrayList var2 = new ArrayList();
      var2.addAll(this.zza);
      return Tasks.forResult(var2);
   }
}

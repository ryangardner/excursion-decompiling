package com.google.android.gms.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class zzaa implements Continuation<Void, List<TResult>> {
   // $FF: synthetic field
   private final Collection zza;

   zzaa(Collection var1) {
      this.zza = var1;
   }

   // $FF: synthetic method
   public final Object then(Task var1) throws Exception {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.zza.iterator();

      while(var3.hasNext()) {
         var2.add(((Task)var3.next()).getResult());
      }

      return var2;
   }
}

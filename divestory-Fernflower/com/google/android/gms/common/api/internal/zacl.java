package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class zacl {
   public static final Status zaa = new Status(8, "The connection to Google Play services was lost");
   final Set<BasePendingResult<?>> zab = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
   private final zacn zac = new zaco(this);

   public final void zaa() {
      Set var1 = this.zab;
      int var2 = 0;
      BasePendingResult[] var3 = (BasePendingResult[])var1.toArray(new BasePendingResult[0]);

      for(int var4 = var3.length; var2 < var4; ++var2) {
         BasePendingResult var5 = var3[var2];
         var5.zaa((zacn)null);
         if (var5.zaa()) {
            this.zab.remove(var5);
         }
      }

   }

   final void zaa(BasePendingResult<? extends Result> var1) {
      this.zab.add(var1);
      var1.zaa(this.zac);
   }
}

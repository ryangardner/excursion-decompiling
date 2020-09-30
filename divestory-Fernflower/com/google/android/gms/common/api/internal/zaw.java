package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class zaw {
   private final Map<BasePendingResult<?>, Boolean> zaa = Collections.synchronizedMap(new WeakHashMap());
   private final Map<TaskCompletionSource<?>, Boolean> zab = Collections.synchronizedMap(new WeakHashMap());

   // $FF: synthetic method
   static Map zaa(zaw var0) {
      return var0.zaa;
   }

   private final void zaa(boolean param1, Status param2) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static Map zab(zaw var0) {
      return var0.zab;
   }

   final void zaa(int var1, String var2) {
      StringBuilder var3 = new StringBuilder("The connection to Google Play services was lost");
      if (var1 == 1) {
         var3.append(" due to service disconnection.");
      } else if (var1 == 3) {
         var3.append(" due to dead object exception.");
      }

      if (var2 != null) {
         var3.append(" Last reason for disconnect: ");
         var3.append(var2);
      }

      this.zaa(true, new Status(20, var3.toString()));
   }

   final void zaa(BasePendingResult<? extends Result> var1, boolean var2) {
      this.zaa.put(var1, var2);
      var1.addStatusListener(new zav(this, var1));
   }

   final <TResult> void zaa(TaskCompletionSource<TResult> var1, boolean var2) {
      this.zab.put(var1, var2);
      var1.getTask().addOnCompleteListener(new zay(this, var1));
   }

   final boolean zaa() {
      return !this.zaa.isEmpty() || !this.zab.isEmpty();
   }

   public final void zab() {
      this.zaa(false, GoogleApiManager.zaa);
   }
}

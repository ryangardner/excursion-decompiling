package com.google.android.gms.common.api.internal;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zay implements OnCompleteListener<TResult> {
   // $FF: synthetic field
   private final TaskCompletionSource zaa;
   // $FF: synthetic field
   private final zaw zab;

   zay(zaw var1, TaskCompletionSource var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final void onComplete(Task<TResult> var1) {
      zaw.zab(this.zab).remove(this.zaa);
   }
}

package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zacg extends TaskApiCall<A, ResultT> {
   // $FF: synthetic field
   private final TaskApiCall.Builder zaa;

   zacg(TaskApiCall.Builder var1, Feature[] var2, boolean var3, int var4) {
      super(var2, var3, var4, (zacf)null);
      this.zaa = var1;
   }

   protected final void doExecute(A var1, TaskCompletionSource<ResultT> var2) throws RemoteException {
      TaskApiCall.Builder.zaa(this.zaa).accept(var1, var2);
   }
}

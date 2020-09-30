package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class UnregisterListenerMethod<A extends Api.AnyClient, L> {
   private final ListenerHolder.ListenerKey<L> zaa;

   protected UnregisterListenerMethod(ListenerHolder.ListenerKey<L> var1) {
      this.zaa = var1;
   }

   public ListenerHolder.ListenerKey<L> getListenerKey() {
      return this.zaa;
   }

   protected abstract void unregisterListener(A var1, TaskCompletionSource<Boolean> var2) throws RemoteException;
}

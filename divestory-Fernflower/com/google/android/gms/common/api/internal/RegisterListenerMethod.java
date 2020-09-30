package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class RegisterListenerMethod<A extends Api.AnyClient, L> {
   private final ListenerHolder<L> zaa;
   private final Feature[] zab;
   private final boolean zac;

   protected RegisterListenerMethod(ListenerHolder<L> var1) {
      this.zaa = var1;
      this.zab = null;
      this.zac = false;
   }

   protected RegisterListenerMethod(ListenerHolder<L> var1, Feature[] var2, boolean var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   public void clearListener() {
      this.zaa.clear();
   }

   public ListenerHolder.ListenerKey<L> getListenerKey() {
      return this.zaa.getListenerKey();
   }

   public Feature[] getRequiredFeatures() {
      return this.zab;
   }

   protected abstract void registerListener(A var1, TaskCompletionSource<Void> var2) throws RemoteException;

   public final boolean zaa() {
      return this.zac;
   }
}

package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zabw extends RegisterListenerMethod<A, L> {
   // $FF: synthetic field
   private final RegistrationMethods.Builder zaa;

   zabw(RegistrationMethods.Builder var1, ListenerHolder var2, Feature[] var3, boolean var4) {
      super(var2, var3, var4);
      this.zaa = var1;
   }

   protected final void registerListener(A var1, TaskCompletionSource<Void> var2) throws RemoteException {
      RegistrationMethods.Builder.zaa(this.zaa).accept(var1, var2);
   }
}

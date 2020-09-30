package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zaby extends UnregisterListenerMethod<A, L> {
   // $FF: synthetic field
   private final RegistrationMethods.Builder zaa;

   zaby(RegistrationMethods.Builder var1, ListenerHolder.ListenerKey var2) {
      super(var2);
      this.zaa = var1;
   }

   protected final void unregisterListener(A var1, TaskCompletionSource<Boolean> var2) throws RemoteException {
      RegistrationMethods.Builder.zab(this.zaa).accept(var1, var2);
   }
}

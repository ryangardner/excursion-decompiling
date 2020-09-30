package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhq extends zzhh<Boolean> {
   public zzhq(TaskCompletionSource<Boolean> var1) {
      super(var1);
   }

   public final void onSuccess() throws RemoteException {
      this.zzay().setResult(true);
   }
}

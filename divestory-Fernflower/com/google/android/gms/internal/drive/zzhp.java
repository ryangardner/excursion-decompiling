package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhp extends zzhh<Metadata> {
   public zzhp(TaskCompletionSource<Metadata> var1) {
      super(var1);
   }

   public final void zza(zzfy var1) throws RemoteException {
      this.zzay().setResult(new zzaa(var1.zzaw()));
   }
}

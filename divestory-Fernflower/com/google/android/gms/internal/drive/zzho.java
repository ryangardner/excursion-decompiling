package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzho extends zzhh<MetadataBuffer> {
   public zzho(TaskCompletionSource<MetadataBuffer> var1) {
      super(var1);
   }

   public final void zza(zzfv var1) throws RemoteException {
      this.zzay().setResult(new MetadataBuffer(var1.zzav()));
   }
}

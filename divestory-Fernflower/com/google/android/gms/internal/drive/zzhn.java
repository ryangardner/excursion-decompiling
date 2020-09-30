package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhn extends zzhh<MetadataBuffer> {
   public zzhn(TaskCompletionSource<MetadataBuffer> var1) {
      super(var1);
   }

   public final void zza(zzft var1) throws RemoteException {
      this.zzay().setResult(new MetadataBuffer(var1.zzau()));
   }
}

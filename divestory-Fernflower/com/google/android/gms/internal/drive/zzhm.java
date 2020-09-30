package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.drive.TransferPreferencesBuilder;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhm extends zzhh<TransferPreferences> {
   public zzhm(TaskCompletionSource<TransferPreferences> var1) {
      super(var1);
   }

   public final void zza(zzfj var1) throws RemoteException {
      this.zzay().setResult((new TransferPreferencesBuilder(var1.zzas())).build());
   }

   public final void zza(zzga var1) throws RemoteException {
      this.zzay().setResult(var1.zzax());
   }
}

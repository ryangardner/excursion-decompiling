package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

final class zzce extends zzl {
   private final BaseImplementation.ResultHolder<DrivePreferencesApi.FileUploadPreferencesResult> zzdx;
   // $FF: synthetic field
   private final zzcb zzfk;

   private zzce(zzcb var1, BaseImplementation.ResultHolder var2) {
      this.zzfk = var1;
      this.zzdx = var2;
   }

   // $FF: synthetic method
   zzce(zzcb var1, BaseImplementation.ResultHolder var2, zzcc var3) {
      this(var1, var2);
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzcf(this.zzfk, var1, (FileUploadPreferences)null, (zzcc)null));
   }

   public final void zza(zzfj var1) throws RemoteException {
      this.zzdx.setResult(new zzcf(this.zzfk, Status.RESULT_SUCCESS, var1.zzhw, (zzcc)null));
   }
}

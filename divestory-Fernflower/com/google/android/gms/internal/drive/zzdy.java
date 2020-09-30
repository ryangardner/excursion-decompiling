package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

final class zzdy extends zzl {
   private final BaseImplementation.ResultHolder<DriveResource.MetadataResult> zzdx;

   public zzdy(BaseImplementation.ResultHolder<DriveResource.MetadataResult> var1) {
      this.zzdx = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzdz(var1, (Metadata)null));
   }

   public final void zza(zzfy var1) throws RemoteException {
      this.zzdx.setResult(new zzdz(Status.RESULT_SUCCESS, new zzaa(var1.zzdn)));
   }
}

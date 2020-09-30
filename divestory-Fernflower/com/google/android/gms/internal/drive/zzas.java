package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;

final class zzas extends zzl {
   private final BaseImplementation.ResultHolder<DriveApi.MetadataBufferResult> zzdx;

   zzas(BaseImplementation.ResultHolder<DriveApi.MetadataBufferResult> var1) {
      this.zzdx = var1;
   }

   public final void zza(Status var1) throws RemoteException {
      this.zzdx.setResult(new zzaq(var1, (MetadataBuffer)null, false));
   }

   public final void zza(zzft var1) throws RemoteException {
      MetadataBuffer var2 = new MetadataBuffer(var1.zzii);
      this.zzdx.setResult(new zzaq(Status.RESULT_SUCCESS, var2, var1.zzea));
   }
}

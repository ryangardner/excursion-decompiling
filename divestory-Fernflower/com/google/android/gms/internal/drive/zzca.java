package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFolder;

abstract class zzca extends zzau<DriveFolder.DriveFolderResult> {
   zzca(GoogleApiClient var1) {
      super(var1);
   }

   // $FF: synthetic method
   public Result createFailedResult(Status var1) {
      return new zzbz(var1, (DriveFolder)null);
   }
}

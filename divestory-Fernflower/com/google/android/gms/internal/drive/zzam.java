package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;

abstract class zzam extends zzau<DriveApi.DriveContentsResult> {
   zzam(GoogleApiClient var1) {
      super(var1);
   }

   // $FF: synthetic method
   public Result createFailedResult(Status var1) {
      return new zzal(var1, (DriveContents)null);
   }
}

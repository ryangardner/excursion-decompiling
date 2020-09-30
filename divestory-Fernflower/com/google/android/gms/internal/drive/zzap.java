package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;

abstract class zzap extends zzau<DriveApi.DriveIdResult> {
   zzap(GoogleApiClient var1) {
      super(var1);
   }

   // $FF: synthetic method
   public Result createFailedResult(Status var1) {
      return new zzao(var1, (DriveId)null);
   }
}

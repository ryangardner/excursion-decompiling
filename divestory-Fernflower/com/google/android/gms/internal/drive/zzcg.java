package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

abstract class zzcg extends zzau<DrivePreferencesApi.FileUploadPreferencesResult> {
   // $FF: synthetic field
   private final zzcb zzfk;

   public zzcg(zzcb var1, GoogleApiClient var2) {
      super(var2);
      this.zzfk = var1;
   }

   // $FF: synthetic method
   protected Result createFailedResult(Status var1) {
      return new zzcf(this.zzfk, var1, (FileUploadPreferences)null, (zzcc)null);
   }
}

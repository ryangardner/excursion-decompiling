package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

abstract class zzea extends zzau<DriveResource.MetadataResult> {
   private zzea(zzdp var1, GoogleApiClient var2) {
      super(var2);
   }

   // $FF: synthetic method
   zzea(zzdp var1, GoogleApiClient var2, zzdq var3) {
      this(var1, var2);
   }

   // $FF: synthetic method
   public Result createFailedResult(Status var1) {
      return new zzdz(var1, (Metadata)null);
   }
}

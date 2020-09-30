package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

final class zzcf implements DrivePreferencesApi.FileUploadPreferencesResult {
   private final Status zzdy;
   private final FileUploadPreferences zzfm;

   private zzcf(zzcb var1, Status var2, FileUploadPreferences var3) {
      this.zzdy = var2;
      this.zzfm = var3;
   }

   // $FF: synthetic method
   zzcf(zzcb var1, Status var2, FileUploadPreferences var3, zzcc var4) {
      this(var1, var2, var3);
   }

   public final FileUploadPreferences getFileUploadPreferences() {
      return this.zzfm;
   }

   public final Status getStatus() {
      return this.zzdy;
   }
}

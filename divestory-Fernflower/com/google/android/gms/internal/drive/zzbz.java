package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFolder;

final class zzbz implements DriveFolder.DriveFolderResult {
   private final Status zzdy;
   private final DriveFolder zzfj;

   public zzbz(Status var1, DriveFolder var2) {
      this.zzdy = var1;
      this.zzfj = var2;
   }

   public final DriveFolder getDriveFolder() {
      return this.zzfj;
   }

   public final Status getStatus() {
      return this.zzdy;
   }
}

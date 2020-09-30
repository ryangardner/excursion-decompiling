package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;

final class zzbx implements DriveFolder.DriveFileResult {
   private final Status zzdy;
   private final DriveFile zzfi;

   public zzbx(Status var1, DriveFile var2) {
      this.zzdy = var1;
      this.zzfi = var2;
   }

   public final DriveFile getDriveFile() {
      return this.zzfi;
   }

   public final Status getStatus() {
      return this.zzdy;
   }
}

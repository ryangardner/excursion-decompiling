package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;

final class zzal implements Releasable, DriveApi.DriveContentsResult {
   private final Status zzdy;
   private final DriveContents zzo;

   public zzal(Status var1, DriveContents var2) {
      this.zzdy = var1;
      this.zzo = var2;
   }

   public final DriveContents getDriveContents() {
      return this.zzo;
   }

   public final Status getStatus() {
      return this.zzdy;
   }

   public final void release() {
      DriveContents var1 = this.zzo;
      if (var1 != null) {
         var1.zzj();
      }

   }
}

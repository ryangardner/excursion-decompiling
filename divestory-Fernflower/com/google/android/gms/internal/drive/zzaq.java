package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;

public final class zzaq implements DriveApi.MetadataBufferResult {
   private final Status zzdy;
   private final MetadataBuffer zzdz;
   private final boolean zzea;

   public zzaq(Status var1, MetadataBuffer var2, boolean var3) {
      this.zzdy = var1;
      this.zzdz = var2;
      this.zzea = var3;
   }

   public final MetadataBuffer getMetadataBuffer() {
      return this.zzdz;
   }

   public final Status getStatus() {
      return this.zzdy;
   }

   public final void release() {
      MetadataBuffer var1 = this.zzdz;
      if (var1 != null) {
         var1.release();
      }

   }
}

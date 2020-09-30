package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;

public final class zzbn extends zzdp implements DriveFile {
   public zzbn(DriveId var1) {
      super(var1);
   }

   public final PendingResult<DriveApi.DriveContentsResult> open(GoogleApiClient var1, int var2, DriveFile.DownloadProgressListener var3) {
      if (var2 != 268435456 && var2 != 536870912 && var2 != 805306368) {
         throw new IllegalArgumentException("Invalid mode provided.");
      } else {
         zzbp var4;
         if (var3 == null) {
            var4 = null;
         } else {
            var4 = new zzbp(var1.registerListener(var3));
         }

         return var1.enqueue(new zzbo(this, var1, var2, var4));
      }
   }
}

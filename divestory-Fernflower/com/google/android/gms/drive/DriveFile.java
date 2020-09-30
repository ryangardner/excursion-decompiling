package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DriveFile extends DriveResource {
   int MODE_READ_ONLY = 268435456;
   int MODE_READ_WRITE = 805306368;
   int MODE_WRITE_ONLY = 536870912;

   @Deprecated
   PendingResult<DriveApi.DriveContentsResult> open(GoogleApiClient var1, int var2, DriveFile.DownloadProgressListener var3);

   @Deprecated
   public interface DownloadProgressListener {
      void onProgress(long var1, long var3);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface OpenMode {
   }
}

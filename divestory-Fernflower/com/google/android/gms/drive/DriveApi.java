package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.query.Query;

@Deprecated
public interface DriveApi {
   @Deprecated
   PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient var1, String var2);

   @Deprecated
   DriveFolder getAppFolder(GoogleApiClient var1);

   @Deprecated
   DriveFolder getRootFolder(GoogleApiClient var1);

   @Deprecated
   CreateFileActivityBuilder newCreateFileActivityBuilder();

   @Deprecated
   PendingResult<DriveApi.DriveContentsResult> newDriveContents(GoogleApiClient var1);

   @Deprecated
   OpenFileActivityBuilder newOpenFileActivityBuilder();

   @Deprecated
   PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient var1, Query var2);

   @Deprecated
   PendingResult<Status> requestSync(GoogleApiClient var1);

   @Deprecated
   public interface DriveContentsResult extends Result {
      DriveContents getDriveContents();
   }

   @Deprecated
   public interface DriveIdResult extends Result {
      DriveId getDriveId();
   }

   @Deprecated
   public interface MetadataBufferResult extends Releasable, Result {
      MetadataBuffer getMetadataBuffer();
   }
}

package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.drive.query.Query;

public interface DriveFolder extends DriveResource {
   String MIME_TYPE = "application/vnd.google-apps.folder";

   @Deprecated
   PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient var1, MetadataChangeSet var2, DriveContents var3);

   @Deprecated
   PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient var1, MetadataChangeSet var2, DriveContents var3, ExecutionOptions var4);

   @Deprecated
   PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient var1, MetadataChangeSet var2);

   @Deprecated
   PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient var1);

   @Deprecated
   PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient var1, Query var2);

   @Deprecated
   public interface DriveFileResult extends Result {
      DriveFile getDriveFile();
   }

   @Deprecated
   public interface DriveFolderResult extends Result {
      DriveFolder getDriveFolder();
   }
}

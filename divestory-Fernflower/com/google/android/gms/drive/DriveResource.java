package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.events.ChangeListener;
import java.util.Set;

public interface DriveResource {
   @Deprecated
   PendingResult<Status> addChangeListener(GoogleApiClient var1, ChangeListener var2);

   @Deprecated
   PendingResult<Status> addChangeSubscription(GoogleApiClient var1);

   @Deprecated
   PendingResult<Status> delete(GoogleApiClient var1);

   DriveId getDriveId();

   @Deprecated
   PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient var1);

   @Deprecated
   PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient var1);

   @Deprecated
   PendingResult<Status> removeChangeListener(GoogleApiClient var1, ChangeListener var2);

   @Deprecated
   PendingResult<Status> removeChangeSubscription(GoogleApiClient var1);

   @Deprecated
   PendingResult<Status> setParents(GoogleApiClient var1, Set<DriveId> var2);

   @Deprecated
   PendingResult<Status> trash(GoogleApiClient var1);

   @Deprecated
   PendingResult<Status> untrash(GoogleApiClient var1);

   @Deprecated
   PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient var1, MetadataChangeSet var2);

   @Deprecated
   public interface MetadataResult extends Result {
      Metadata getMetadata();
   }
}

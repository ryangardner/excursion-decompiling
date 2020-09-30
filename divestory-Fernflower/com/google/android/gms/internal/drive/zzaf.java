package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

@Deprecated
public final class zzaf implements DriveApi {
   public final PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient var1, String var2) {
      return var1.enqueue(new zzai(this, var1, var2));
   }

   public final DriveFolder getAppFolder(GoogleApiClient var1) {
      zzaw var2 = (zzaw)var1.getClient(Drive.CLIENT_KEY);
      if (var2.zzag()) {
         DriveId var3 = var2.zzaf();
         return var3 != null ? new zzbs(var3) : null;
      } else {
         throw new IllegalStateException("Client is not yet connected");
      }
   }

   public final DriveFolder getRootFolder(GoogleApiClient var1) {
      zzaw var2 = (zzaw)var1.getClient(Drive.CLIENT_KEY);
      if (var2.zzag()) {
         DriveId var3 = var2.zzae();
         return var3 != null ? new zzbs(var3) : null;
      } else {
         throw new IllegalStateException("Client is not yet connected");
      }
   }

   public final CreateFileActivityBuilder newCreateFileActivityBuilder() {
      return new CreateFileActivityBuilder();
   }

   public final PendingResult<DriveApi.DriveContentsResult> newDriveContents(GoogleApiClient var1) {
      return var1.enqueue(new zzah(this, var1, 536870912));
   }

   public final OpenFileActivityBuilder newOpenFileActivityBuilder() {
      return new OpenFileActivityBuilder();
   }

   public final PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient var1, Query var2) {
      if (var2 != null) {
         return var1.enqueue(new zzag(this, var1, var2));
      } else {
         throw new IllegalArgumentException("Query must be provided.");
      }
   }

   public final PendingResult<Status> requestSync(GoogleApiClient var1) {
      return var1.execute(new zzaj(this, var1));
   }
}

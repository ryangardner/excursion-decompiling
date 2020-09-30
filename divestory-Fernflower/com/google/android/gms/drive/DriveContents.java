package com.google.android.gms.drive;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.InputStream;
import java.io.OutputStream;

public interface DriveContents {
   @Deprecated
   PendingResult<Status> commit(GoogleApiClient var1, MetadataChangeSet var2);

   @Deprecated
   PendingResult<Status> commit(GoogleApiClient var1, MetadataChangeSet var2, ExecutionOptions var3);

   @Deprecated
   void discard(GoogleApiClient var1);

   DriveId getDriveId();

   InputStream getInputStream();

   int getMode();

   OutputStream getOutputStream();

   ParcelFileDescriptor getParcelFileDescriptor();

   @Deprecated
   PendingResult<DriveApi.DriveContentsResult> reopenForWrite(GoogleApiClient var1);

   Contents zzi();

   void zzj();

   boolean zzk();
}

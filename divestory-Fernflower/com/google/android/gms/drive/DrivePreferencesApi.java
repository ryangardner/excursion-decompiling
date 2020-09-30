package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

@Deprecated
public interface DrivePreferencesApi {
   @Deprecated
   PendingResult<DrivePreferencesApi.FileUploadPreferencesResult> getFileUploadPreferences(GoogleApiClient var1);

   @Deprecated
   PendingResult<Status> setFileUploadPreferences(GoogleApiClient var1, FileUploadPreferences var2);

   @Deprecated
   public interface FileUploadPreferencesResult extends Result {
      FileUploadPreferences getFileUploadPreferences();
   }
}

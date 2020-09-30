package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

@Deprecated
public final class zzcb implements DrivePreferencesApi {
   public final PendingResult<DrivePreferencesApi.FileUploadPreferencesResult> getFileUploadPreferences(GoogleApiClient var1) {
      return var1.enqueue(new zzcc(this, var1));
   }

   public final PendingResult<Status> setFileUploadPreferences(GoogleApiClient var1, FileUploadPreferences var2) {
      if (var2 instanceof zzei) {
         return var1.execute(new zzcd(this, var1, (zzei)var2));
      } else {
         throw new IllegalArgumentException("Invalid preference value");
      }
   }
}

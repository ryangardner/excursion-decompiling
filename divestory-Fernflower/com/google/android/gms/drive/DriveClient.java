package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.Task;

@Deprecated
public abstract class DriveClient extends GoogleApi<Drive.zza> {
   public DriveClient(Activity var1, Drive.zza var2) {
      super((Activity)var1, Drive.zzw, var2, (GoogleApi.Settings)GoogleApi.Settings.DEFAULT_SETTINGS);
   }

   public DriveClient(Context var1, Drive.zza var2) {
      super((Context)var1, Drive.zzw, var2, (GoogleApi.Settings)GoogleApi.Settings.DEFAULT_SETTINGS);
   }

   @Deprecated
   public abstract Task<DriveId> getDriveId(String var1);

   @Deprecated
   public abstract Task<TransferPreferences> getUploadPreferences();

   @Deprecated
   public abstract Task<IntentSender> newCreateFileActivityIntentSender(CreateFileActivityOptions var1);

   @Deprecated
   public abstract Task<IntentSender> newOpenFileActivityIntentSender(OpenFileActivityOptions var1);

   @Deprecated
   public abstract Task<Void> requestSync();

   @Deprecated
   public abstract Task<Void> setUploadPreferences(TransferPreferences var1);
}

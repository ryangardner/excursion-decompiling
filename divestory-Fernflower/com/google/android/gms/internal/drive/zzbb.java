package com.google.android.gms.internal.drive;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.tasks.Task;

public final class zzbb extends DriveClient {
   public zzbb(Activity var1, Drive.zza var2) {
      super(var1, var2);
   }

   public zzbb(Context var1, Drive.zza var2) {
      super(var1, var2);
   }

   public final Task<DriveId> getDriveId(String var1) {
      Preconditions.checkNotNull(var1, "resourceId must not be null");
      return this.doRead(new zzbc(this, var1));
   }

   public final Task<TransferPreferences> getUploadPreferences() {
      return this.doRead(new zzbd(this));
   }

   public final Task<IntentSender> newCreateFileActivityIntentSender(CreateFileActivityOptions var1) {
      return this.doRead(new zzbg(this, var1));
   }

   public final Task<IntentSender> newOpenFileActivityIntentSender(OpenFileActivityOptions var1) {
      return this.doRead(new zzbf(this, var1));
   }

   public final Task<Void> requestSync() {
      return this.doWrite(new zzbh(this));
   }

   public final Task<Void> setUploadPreferences(TransferPreferences var1) {
      Preconditions.checkNotNull(var1, "transferPreferences cannot be null.");
      return this.doWrite(new zzbe(this, var1));
   }
}

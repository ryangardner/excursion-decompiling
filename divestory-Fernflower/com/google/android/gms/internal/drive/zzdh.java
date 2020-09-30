package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdh extends TaskApiCall<zzaw, DriveFile> {
   private final DriveFolder zzfj;
   private final MetadataChangeSet zzgc;
   private ExecutionOptions zzgd;
   private String zzge;
   private com.google.android.gms.drive.metadata.internal.zzk zzgf;
   private final DriveContents zzo;

   zzdh(DriveFolder var1, MetadataChangeSet var2, DriveContents var3, ExecutionOptions var4, String var5) {
      this.zzfj = var1;
      this.zzgc = var2;
      this.zzo = var3;
      this.zzgd = var4;
      this.zzge = null;
      Preconditions.checkNotNull(var1, "DriveFolder must not be null");
      Preconditions.checkNotNull(var1.getDriveId(), "Folder's DriveId must not be null");
      Preconditions.checkNotNull(var2, "MetadataChangeSet must not be null");
      Preconditions.checkNotNull(var4, "ExecutionOptions must not be null");
      com.google.android.gms.drive.metadata.internal.zzk var6 = com.google.android.gms.drive.metadata.internal.zzk.zzg(var2.getMimeType());
      this.zzgf = var6;
      if (var6 != null && var6.isFolder()) {
         throw new IllegalArgumentException("May not create folders using this method. Use DriveFolderManagerClient#createFolder() instead of mime type application/vnd.google-apps.folder");
      } else {
         if (var3 != null) {
            if (!(var3 instanceof zzbi)) {
               throw new IllegalArgumentException("Only DriveContents obtained from the Drive API are accepted.");
            }

            if (var3.getDriveId() != null) {
               throw new IllegalArgumentException("Only DriveContents obtained through DriveApi.newDriveContents are accepted for file creation.");
            }

            if (var3.zzk()) {
               throw new IllegalArgumentException("DriveContents are already closed.");
            }
         }

      }
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaw var7 = (zzaw)var1;
      this.zzgd.zza(var7);
      MetadataChangeSet var3 = this.zzgc;
      var3.zzq().zza(var7.getContext());
      int var4 = zzbs.zza(this.zzo, this.zzgf);
      com.google.android.gms.drive.metadata.internal.zzk var5 = this.zzgf;
      byte var6;
      if (var5 != null && var5.zzbh()) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      zzw var8 = new zzw(this.zzfj.getDriveId(), var3.zzq(), var4, var6, this.zzgd);
      ((zzeo)var7.getService()).zza((zzw)var8, new zzhj(var2));
   }
}

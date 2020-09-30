package com.google.android.gms.drive;

import android.content.IntentSender;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.drive.zzbi;

@Deprecated
public class CreateFileActivityBuilder {
   public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
   private final com.google.android.gms.internal.drive.zzt zzn = new com.google.android.gms.internal.drive.zzt(0);
   private DriveContents zzo;
   private boolean zzp;

   public IntentSender build(GoogleApiClient var1) {
      Preconditions.checkState(var1.isConnected(), "Client must be connected");
      this.zzg();
      return this.zzn.build(var1);
   }

   final int getRequestId() {
      return this.zzn.getRequestId();
   }

   public CreateFileActivityBuilder setActivityStartFolder(DriveId var1) {
      this.zzn.zza(var1);
      return this;
   }

   public CreateFileActivityBuilder setActivityTitle(String var1) {
      this.zzn.zzc(var1);
      return this;
   }

   public CreateFileActivityBuilder setInitialDriveContents(DriveContents var1) {
      if (var1 != null) {
         if (!(var1 instanceof zzbi)) {
            throw new IllegalArgumentException("Only DriveContents obtained from the Drive API are accepted.");
         }

         if (var1.getDriveId() != null) {
            throw new IllegalArgumentException("Only DriveContents obtained through DriveApi.newDriveContents are accepted for file creation.");
         }

         if (var1.zzk()) {
            throw new IllegalArgumentException("DriveContents are already closed.");
         }

         this.zzn.zzd(var1.zzi().zzj);
         this.zzo = var1;
      } else {
         this.zzn.zzd(1);
      }

      this.zzp = true;
      return this;
   }

   public CreateFileActivityBuilder setInitialMetadata(MetadataChangeSet var1) {
      this.zzn.zza(var1);
      return this;
   }

   final MetadataChangeSet zzc() {
      return this.zzn.zzc();
   }

   final DriveId zzd() {
      return this.zzn.zzd();
   }

   final String zze() {
      return this.zzn.zze();
   }

   final int zzf() {
      return 0;
   }

   final void zzg() {
      Preconditions.checkState(this.zzp, "Must call setInitialDriveContents.");
      DriveContents var1 = this.zzo;
      if (var1 != null) {
         var1.zzj();
      }

      this.zzn.zzg();
   }
}

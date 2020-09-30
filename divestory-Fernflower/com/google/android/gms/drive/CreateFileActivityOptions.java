package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class CreateFileActivityOptions extends com.google.android.gms.internal.drive.zzq {
   public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";

   private CreateFileActivityOptions(MetadataBundle var1, Integer var2, String var3, DriveId var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   // $FF: synthetic method
   CreateFileActivityOptions(MetadataBundle var1, Integer var2, String var3, DriveId var4, int var5, zzd var6) {
      this(var1, var2, var3, var4, var5);
   }

   public static class Builder {
      protected final CreateFileActivityBuilder builder = new CreateFileActivityBuilder();

      public CreateFileActivityOptions build() {
         this.builder.zzg();
         return new CreateFileActivityOptions(this.builder.zzc().zzq(), this.builder.getRequestId(), this.builder.zze(), this.builder.zzd(), this.builder.zzf(), (zzd)null);
      }

      public CreateFileActivityOptions.Builder setActivityStartFolder(DriveId var1) {
         this.builder.setActivityStartFolder(var1);
         return this;
      }

      public CreateFileActivityOptions.Builder setActivityTitle(String var1) {
         this.builder.setActivityTitle(var1);
         return this;
      }

      public CreateFileActivityOptions.Builder setInitialDriveContents(DriveContents var1) {
         this.builder.setInitialDriveContents(var1);
         return this;
      }

      public CreateFileActivityOptions.Builder setInitialMetadata(MetadataChangeSet var1) {
         this.builder.setInitialMetadata(var1);
         return this;
      }
   }
}

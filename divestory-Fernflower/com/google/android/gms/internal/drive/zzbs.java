package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

public final class zzbs extends zzdp implements DriveFolder {
   public zzbs(DriveId var1) {
      super(var1);
   }

   static int zza(DriveContents var0, com.google.android.gms.drive.metadata.internal.zzk var1) {
      int var2;
      if (var0 == null) {
         if (var1 != null && var1.zzbh()) {
            var2 = 0;
         } else {
            var2 = 1;
         }
      } else {
         var2 = var0.zzi().getRequestId();
         var0.zzj();
      }

      return var2;
   }

   static Query zza(Query var0, DriveId var1) {
      Query.Builder var2 = (new Query.Builder()).addFilter(Filters.in(SearchableField.PARENTS, var1));
      if (var0 != null) {
         if (var0.getFilter() != null) {
            var2.addFilter(var0.getFilter());
         }

         var2.setPageToken(var0.getPageToken());
         var2.setSortOrder(var0.getSortOrder());
      }

      return var2.build();
   }

   static void zzb(MetadataChangeSet var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("MetadataChangeSet must be provided.");
      } else {
         com.google.android.gms.drive.metadata.internal.zzk var2 = com.google.android.gms.drive.metadata.internal.zzk.zzg(var0.getMimeType());
         if (var2 != null) {
            boolean var1;
            if (!var2.zzbh() && !var2.isFolder()) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (!var1) {
               throw new IllegalArgumentException("May not create shortcut files using this method. Use DriveFolder.createShortcutFile() instead.");
            }
         }

      }
   }

   public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient var1, MetadataChangeSet var2, DriveContents var3) {
      return this.createFile(var1, var2, var3, (ExecutionOptions)null);
   }

   public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient var1, MetadataChangeSet var2, DriveContents var3, ExecutionOptions var4) {
      ExecutionOptions var5 = var4;
      if (var4 == null) {
         var5 = (new ExecutionOptions.Builder()).build();
      }

      if (var5.zzn() != 0) {
         throw new IllegalStateException("May not set a conflict strategy for new file creation.");
      } else if (var2 != null) {
         com.google.android.gms.drive.metadata.internal.zzk var9 = com.google.android.gms.drive.metadata.internal.zzk.zzg(var2.getMimeType());
         if (var9 != null && var9.isFolder()) {
            throw new IllegalArgumentException("May not create folders using this method. Use DriveFolder.createFolder() instead of mime type application/vnd.google-apps.folder");
         } else if (var5 != null) {
            var5.zza(var1);
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

            zzb(var2);
            int var6 = zza(var3, com.google.android.gms.drive.metadata.internal.zzk.zzg(var2.getMimeType()));
            com.google.android.gms.drive.metadata.internal.zzk var8 = com.google.android.gms.drive.metadata.internal.zzk.zzg(var2.getMimeType());
            byte var7;
            if (var8 != null && var8.zzbh()) {
               var7 = 1;
            } else {
               var7 = 0;
            }

            return var1.execute(new zzbt(this, var1, var2, var6, var7, var5));
         } else {
            throw new IllegalArgumentException("ExecutionOptions must be provided");
         }
      } else {
         throw new IllegalArgumentException("MetadataChangeSet must be provided.");
      }
   }

   public final PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient var1, MetadataChangeSet var2) {
      if (var2 != null) {
         if (var2.getMimeType() != null && !var2.getMimeType().equals("application/vnd.google-apps.folder")) {
            throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
         } else {
            return var1.execute(new zzbu(this, var1, var2));
         }
      } else {
         throw new IllegalArgumentException("MetadataChangeSet must be provided.");
      }
   }

   public final PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient var1) {
      return this.queryChildren(var1, (Query)null);
   }

   public final PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient var1, Query var2) {
      return (new zzaf()).query(var1, zza(var2, this.getDriveId()));
   }
}

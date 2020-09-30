package com.syntak.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class GoogleDriveOp {
   public DriveFolder appFolder;
   boolean connected = false;
   Context context;
   GoogleApiClient googleApiClient = null;
   CustomPropertyKey lastModifiedTime;
   public DriveFolder rootFolder;
   private final ResultCallback<DriveFolder.DriveFileResult> uploadFileCallback = new ResultCallback<DriveFolder.DriveFileResult>() {
      public void onResult(DriveFolder.DriveFileResult var1) {
         boolean var2 = var1.getStatus().isSuccess();
         String var3;
         if (var2) {
            var3 = var1.getDriveFile().getDriveId().getResourceId();
         } else {
            var3 = null;
         }

         GoogleDriveOp.this.OnFileUploadResult(var2, var3);
      }
   };

   public GoogleDriveOp(Context var1) {
      this.context = var1;
      GoogleApiClient.ConnectionCallbacks var2 = new GoogleApiClient.ConnectionCallbacks() {
         public void onConnected(Bundle var1) {
            GoogleDriveOp.this.connected = true;
            Drive.DriveApi.requestSync(GoogleDriveOp.this.googleApiClient);
            GoogleDriveOp.this.rootFolder = Drive.DriveApi.getRootFolder(GoogleDriveOp.this.googleApiClient);
            GoogleDriveOp.this.appFolder = Drive.DriveApi.getAppFolder(GoogleDriveOp.this.googleApiClient);
            GoogleDriveOp.this.lastModifiedTime = new CustomPropertyKey("lastModifiedTime", 1);
            GoogleDriveOp.this.OnGoogleDriveServiceConnected(var1);
         }

         public void onConnectionSuspended(int var1) {
            GoogleDriveOp.this.OnGoogleDriveServiceSuspended(var1);
         }
      };
      GoogleApiClient.OnConnectionFailedListener var3 = new GoogleApiClient.OnConnectionFailedListener() {
         public void onConnectionFailed(ConnectionResult var1) {
            GoogleDriveOp.this.OnGoogleDriveServiceFailed(var1);
         }
      };
      this.googleApiClient = (new GoogleApiClient.Builder(var1)).addApi(Drive.API).addScope(Drive.SCOPE_FILE).addScope(Drive.SCOPE_APPFOLDER).addConnectionCallbacks(var2).addOnConnectionFailedListener(var3).useDefaultAccount().build();
   }

   public void OnDriveIdDeleteResult(boolean var1) {
   }

   public void OnDriveIdsQueryResult(ArrayList<DriveId> var1) {
   }

   public void OnFileDownloadResult(boolean var1) {
   }

   public void OnFileQueryResult(boolean var1, DriveId var2, long var3) {
   }

   public void OnFileUploadResult(boolean var1, String var2) {
   }

   public void OnFolderCreationResult(boolean var1, DriveFolder var2) {
   }

   public void OnFolderQueryResult(boolean var1, DriveFolder var2) {
   }

   public void OnGoogleDriveServiceConnected(Bundle var1) {
   }

   public void OnGoogleDriveServiceFailed(ConnectionResult var1) {
   }

   public void OnGoogleDriveServiceSuspended(int var1) {
   }

   public void connect() {
      GoogleApiClient var1 = this.googleApiClient;
      if (var1 != null) {
         var1.connect();
      }

   }

   public void createFolder(DriveFolder var1, String var2) {
      MetadataChangeSet var3 = (new MetadataChangeSet.Builder()).setTitle(var2).build();
      var1.createFolder(this.googleApiClient, var3).setResultCallback(new ResultCallback<DriveFolder.DriveFolderResult>() {
         public void onResult(DriveFolder.DriveFolderResult var1) {
            GoogleDriveOp.this.OnFolderCreationResult(var1.getStatus().isSuccess(), var1.getDriveFolder());
         }
      });
   }

   public void deleteDriveId(DriveId var1) {
      var1.asDriveFile().delete(this.googleApiClient).setResultCallback(new ResultCallback<Status>() {
         public void onResult(Status var1) {
            boolean var2 = var1.isSuccess();
            GoogleDriveOp.this.OnDriveIdDeleteResult(var2);
         }
      });
   }

   public void disconnect() {
      GoogleApiClient var1 = this.googleApiClient;
      if (var1 != null) {
         var1.disconnect();
      }

   }

   public void downloadFile(DriveId var1, final long var2, final String var4) {
      var1.asDriveFile().open(this.googleApiClient, 268435456, (DriveFile.DownloadProgressListener)null).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
         public void onResult(DriveApi.DriveContentsResult var1) {
            boolean var2x = var1.getStatus().isSuccess();
            boolean var3 = false;
            boolean var4x = false;
            if (var2x) {
               DriveContents var8 = var1.getDriveContents();
               InputStream var5 = var8.getInputStream();

               label15: {
                  try {
                     FileOutputStream var6 = new FileOutputStream(var4);
                     FileOp.copyStream(var5, var6);
                     var6.flush();
                     var6.close();
                     var5.close();
                  } catch (IOException var7) {
                     var3 = var4x;
                     break label15;
                  }

                  var3 = true;
               }

               FileOp.setFileLastModified(var4, var2);
               var8.discard(GoogleDriveOp.this.googleApiClient);
            }

            GoogleDriveOp.this.OnFileDownloadResult(var3);
         }
      });
   }

   public void downloadFile(String var1, final String var2) {
      Drive.DriveApi.fetchDriveId(this.googleApiClient, var1).setResultCallback(new ResultCallback<DriveApi.DriveIdResult>() {
         public void onResult(final DriveApi.DriveIdResult var1) {
            if (var1.getStatus().isSuccess()) {
               Thread var10001 = new Thread() {
                  public void run() {
                     DriveApi.DriveContentsResult var1x = (DriveApi.DriveContentsResult)var1.getDriveId().asDriveFile().open(GoogleDriveOp.this.googleApiClient, 268435456, (DriveFile.DownloadProgressListener)null).await();
                     boolean var2x = var1x.getStatus().isSuccess();
                     boolean var3 = false;
                     boolean var4 = false;
                     if (var2x) {
                        DriveContents var8 = var1x.getDriveContents();
                        InputStream var5 = var8.getInputStream();

                        label15: {
                           try {
                              FileOutputStream var6 = new FileOutputStream(var2);
                              FileOp.copyStream(var5, var6);
                              var6.flush();
                              var6.close();
                              var5.close();
                           } catch (IOException var7) {
                              var3 = var4;
                              break label15;
                           }

                           var3 = true;
                        }

                        var8.discard(GoogleDriveOp.this.googleApiClient);
                     }

                     GoogleDriveOp.this.OnFileDownloadResult(var3);
                  }
               };
            }

         }
      });
   }

   public DriveFolder getAppFolder() {
      return this.appFolder;
   }

   public DriveFolder getRootFolder() {
      return this.rootFolder;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public void queryDriveIds(DriveFolder var1, final String var2) {
      if (this.connected) {
         var1.listChildren(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
            public void onResult(DriveApi.MetadataBufferResult var1) {
               ArrayList var4;
               if (var1.getStatus().isSuccess()) {
                  ArrayList var2x = new ArrayList();
                  Iterator var3 = var1.getMetadataBuffer().iterator();

                  while(true) {
                     var4 = var2x;
                     if (!var3.hasNext()) {
                        break;
                     }

                     Metadata var5 = (Metadata)var3.next();
                     if (!var5.isTrashed() && var5.getTitle().equals(var2)) {
                        var2x.add(var5.getDriveId());
                     }
                  }
               } else {
                  var4 = null;
               }

               GoogleDriveOp.this.OnDriveIdsQueryResult(var4);
            }
         });
      }
   }

   public void queryFile(DriveFolder var1, final String var2) {
      if (this.connected) {
         var1.listChildren(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
            public void onResult(DriveApi.MetadataBufferResult var1) {
               boolean var2x = var1.getStatus().isSuccess();
               long var3 = 0L;
               boolean var5 = false;
               DriveId var6 = null;
               long var7 = var3;
               if (var2x) {
                  Iterator var9 = var1.getMetadataBuffer().iterator();
                  DriveId var13 = null;
                  var5 = false;
                  long var10 = 0L;

                  while(var9.hasNext()) {
                     Metadata var12 = (Metadata)var9.next();
                     if (!var12.isTrashed() && var12.getTitle().equals(var2)) {
                        var2x = true;
                        var6 = var12.getDriveId();
                        String var14 = (String)var12.getCustomProperties().get(GoogleDriveOp.this.lastModifiedTime);
                        var7 = var10;
                        if (var14 != null) {
                           var7 = Long.parseLong(var14);
                        }

                        var5 = var2x;
                        var10 = var7;
                        if (var7 > var3) {
                           var3 = var7;
                           var13 = var6;
                           var5 = var2x;
                           var10 = var7;
                        }
                     }
                  }

                  var6 = var13;
                  var7 = var3;
               }

               GoogleDriveOp.this.OnFileQueryResult(var5, var6, var7);
            }
         });
      }
   }

   public void queryFolder(DriveFolder var1, final String var2) {
      var1.listChildren(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
         public void onResult(DriveApi.MetadataBufferResult var1) {
            boolean var3;
            DriveFolder var5;
            label19: {
               if (var1.getStatus().isSuccess()) {
                  Iterator var4 = var1.getMetadataBuffer().iterator();

                  while(var4.hasNext()) {
                     Metadata var2x = (Metadata)var4.next();
                     if (var2x.getTitle().equals(var2)) {
                        var3 = true;
                        var5 = var2x.getDriveId().asDriveFolder();
                        break label19;
                     }
                  }
               }

               var3 = false;
               var5 = null;
            }

            GoogleDriveOp.this.OnFolderQueryResult(var3, var5);
         }
      });
   }

   public void stop() {
      this.disconnect();
   }

   public void uploadFile(final DriveFolder var1, final String var2, final String var3, final String var4) {
      Drive.DriveApi.newDriveContents(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
         public void onResult(DriveApi.DriveContentsResult var1x) {
            if (var1x.getStatus().isSuccess()) {
               (new Thread(var1x.getDriveContents()) {
                  // $FF: synthetic field
                  final DriveContents val$driveContents;

                  {
                     this.val$driveContents = var2x;
                  }

                  public void run() {
                     OutputStream var1x = this.val$driveContents.getOutputStream();

                     try {
                        FileInputStream var2x = new FileInputStream(var4);
                        FileOp.copyStream(var2x, var1x);
                        var1x.flush();
                        var1x.close();
                        var2x.close();
                     } catch (IOException var3x) {
                        Log.e("G-drive upload file", var3x.getMessage());
                     }

                     MetadataChangeSet var4x = (new MetadataChangeSet.Builder()).setTitle(var3).setMimeType(var2).setCustomProperty(GoogleDriveOp.this.lastModifiedTime, String.valueOf(FileOp.getFileModifiedTime(var4))).build();
                     var1.createFile(GoogleDriveOp.this.googleApiClient, var4x, this.val$driveContents).setResultCallback(GoogleDriveOp.this.uploadFileCallback);
                  }
               }).start();
            }

         }
      });
   }
}

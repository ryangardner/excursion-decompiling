/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.util.Log
 */
package com.syntak.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.syntak.library.FileOp;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class GoogleDriveOp {
    public DriveFolder appFolder;
    boolean connected = false;
    Context context;
    GoogleApiClient googleApiClient = null;
    CustomPropertyKey lastModifiedTime;
    public DriveFolder rootFolder;
    private final ResultCallback<DriveFolder.DriveFileResult> uploadFileCallback = new ResultCallback<DriveFolder.DriveFileResult>(){

        @Override
        public void onResult(DriveFolder.DriveFileResult object) {
            boolean bl = object.getStatus().isSuccess();
            object = bl ? object.getDriveFile().getDriveId().getResourceId() : null;
            GoogleDriveOp.this.OnFileUploadResult(bl, (String)object);
        }
    };

    public GoogleDriveOp(Context context) {
        this.context = context;
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle bundle) {
                GoogleDriveOp.this.connected = true;
                Drive.DriveApi.requestSync(GoogleDriveOp.this.googleApiClient);
                GoogleDriveOp.this.rootFolder = Drive.DriveApi.getRootFolder(GoogleDriveOp.this.googleApiClient);
                GoogleDriveOp.this.appFolder = Drive.DriveApi.getAppFolder(GoogleDriveOp.this.googleApiClient);
                GoogleDriveOp.this.lastModifiedTime = new CustomPropertyKey("lastModifiedTime", 1);
                GoogleDriveOp.this.OnGoogleDriveServiceConnected(bundle);
            }

            @Override
            public void onConnectionSuspended(int n) {
                GoogleDriveOp.this.OnGoogleDriveServiceSuspended(n);
            }
        };
        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener(){

            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                GoogleDriveOp.this.OnGoogleDriveServiceFailed(connectionResult);
            }
        };
        this.googleApiClient = new GoogleApiClient.Builder(context).addApi(Drive.API).addScope(Drive.SCOPE_FILE).addScope(Drive.SCOPE_APPFOLDER).addConnectionCallbacks(connectionCallbacks).addOnConnectionFailedListener(onConnectionFailedListener).useDefaultAccount().build();
    }

    public void OnDriveIdDeleteResult(boolean bl) {
    }

    public void OnDriveIdsQueryResult(ArrayList<DriveId> arrayList) {
    }

    public void OnFileDownloadResult(boolean bl) {
    }

    public void OnFileQueryResult(boolean bl, DriveId driveId, long l) {
    }

    public void OnFileUploadResult(boolean bl, String string2) {
    }

    public void OnFolderCreationResult(boolean bl, DriveFolder driveFolder) {
    }

    public void OnFolderQueryResult(boolean bl, DriveFolder driveFolder) {
    }

    public void OnGoogleDriveServiceConnected(Bundle bundle) {
    }

    public void OnGoogleDriveServiceFailed(ConnectionResult connectionResult) {
    }

    public void OnGoogleDriveServiceSuspended(int n) {
    }

    public void connect() {
        GoogleApiClient googleApiClient = this.googleApiClient;
        if (googleApiClient == null) return;
        googleApiClient.connect();
    }

    public void createFolder(DriveFolder driveFolder, String object) {
        object = new MetadataChangeSet.Builder().setTitle((String)object).build();
        driveFolder.createFolder(this.googleApiClient, (MetadataChangeSet)object).setResultCallback(new ResultCallback<DriveFolder.DriveFolderResult>(){

            @Override
            public void onResult(DriveFolder.DriveFolderResult driveFolderResult) {
                GoogleDriveOp.this.OnFolderCreationResult(driveFolderResult.getStatus().isSuccess(), driveFolderResult.getDriveFolder());
            }
        });
    }

    public void deleteDriveId(DriveId driveId) {
        driveId.asDriveFile().delete(this.googleApiClient).setResultCallback(new ResultCallback<Status>(){

            @Override
            public void onResult(Status status) {
                boolean bl = status.isSuccess();
                GoogleDriveOp.this.OnDriveIdDeleteResult(bl);
            }
        });
    }

    public void disconnect() {
        GoogleApiClient googleApiClient = this.googleApiClient;
        if (googleApiClient == null) return;
        googleApiClient.disconnect();
    }

    public void downloadFile(DriveId driveId, final long l, final String string2) {
        driveId.asDriveFile().open(this.googleApiClient, 268435456, null).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>(){

            @Override
            public void onResult(DriveApi.DriveContentsResult object) {
                boolean bl = object.getStatus().isSuccess();
                boolean bl2 = false;
                boolean bl3 = false;
                if (bl) {
                    object = object.getDriveContents();
                    InputStream inputStream2 = object.getInputStream();
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(string2);
                        FileOp.copyStream(inputStream2, fileOutputStream);
                        fileOutputStream.flush();
                        ((OutputStream)fileOutputStream).close();
                        inputStream2.close();
                        bl2 = true;
                    }
                    catch (IOException iOException) {
                        bl2 = bl3;
                    }
                    FileOp.setFileLastModified(string2, l);
                    object.discard(GoogleDriveOp.this.googleApiClient);
                }
                GoogleDriveOp.this.OnFileDownloadResult(bl2);
            }
        });
    }

    public void downloadFile(String string2, final String string3) {
        Drive.DriveApi.fetchDriveId(this.googleApiClient, string2).setResultCallback(new ResultCallback<DriveApi.DriveIdResult>(){

            @Override
            public void onResult(final DriveApi.DriveIdResult driveIdResult) {
                if (!driveIdResult.getStatus().isSuccess()) return;
                new Thread(){

                    /*
                     * Unable to fully structure code
                     * Enabled unnecessary exception pruning
                     */
                    @Override
                    public void run() {
                        var1_1 = driveIdResult.getDriveId().asDriveFile().open(GoogleDriveOp.this.googleApiClient, 268435456, null).await();
                        var2_2 = var1_1.getStatus().isSuccess();
                        var3_3 = false;
                        var4_4 = false;
                        if (var2_2) {
                            var1_1 = var1_1.getDriveContents();
                            var5_5 = var1_1.getInputStream();
                            try {
                                var6_7 = new FileOutputStream(string3);
                                FileOp.copyStream(var5_5, var6_7);
                                var6_7.flush();
                                var6_7.close();
                                var5_5.close();
                                var3_3 = true;
lbl15: // 2 sources:
                                do {
                                    var1_1.discard(GoogleDriveOp.this.googleApiClient);
                                    break;
                                } while (true);
                            }
                            catch (IOException var5_6) {
                                var3_3 = var4_4;
                                ** continue;
                            }
                        }
                        GoogleDriveOp.this.OnFileDownloadResult(var3_3);
                    }
                };
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

    public void queryDriveIds(DriveFolder driveFolder, final String string2) {
        if (!this.connected) {
            return;
        }
        driveFolder.listChildren(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>(){

            @Override
            public void onResult(DriveApi.MetadataBufferResult object) {
                if (object.getStatus().isSuccess()) {
                    ArrayList<DriveId> arrayList = new ArrayList<DriveId>();
                    Iterator iterator2 = object.getMetadataBuffer().iterator();
                    do {
                        object = arrayList;
                        if (iterator2.hasNext()) {
                            object = (Metadata)iterator2.next();
                            if (((Metadata)object).isTrashed() || !((Metadata)object).getTitle().equals(string2)) continue;
                            arrayList.add(((Metadata)object).getDriveId());
                            continue;
                        }
                        break;
                    } while (true);
                } else {
                    object = null;
                }
                GoogleDriveOp.this.OnDriveIdsQueryResult((ArrayList<DriveId>)object);
            }
        });
    }

    public void queryFile(DriveFolder driveFolder, final String string2) {
        if (!this.connected) {
            return;
        }
        driveFolder.listChildren(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>(){

            @Override
            public void onResult(DriveApi.MetadataBufferResult object) {
                boolean bl = object.getStatus().isSuccess();
                long l = 0L;
                boolean bl2 = false;
                Object object2 = null;
                long l2 = l;
                if (bl) {
                    Iterator iterator2 = object.getMetadataBuffer().iterator();
                    object = null;
                    bl2 = false;
                    long l3 = 0L;
                    while (iterator2.hasNext()) {
                        Object object3 = (Metadata)iterator2.next();
                        if (((Metadata)object3).isTrashed() || !((Metadata)object3).getTitle().equals(string2)) continue;
                        bl = true;
                        object2 = ((Metadata)object3).getDriveId();
                        object3 = ((Metadata)object3).getCustomProperties().get(GoogleDriveOp.this.lastModifiedTime);
                        l2 = l3;
                        if (object3 != null) {
                            l2 = Long.parseLong((String)object3);
                        }
                        bl2 = bl;
                        l3 = l2;
                        if (l2 <= l) continue;
                        l = l2;
                        object = object2;
                        bl2 = bl;
                        l3 = l2;
                    }
                    object2 = object;
                    l2 = l;
                }
                GoogleDriveOp.this.OnFileQueryResult(bl2, (DriveId)object2, l2);
            }
        });
    }

    public void queryFolder(DriveFolder driveFolder, final String string2) {
        driveFolder.listChildren(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>(){

            @Override
            public void onResult(DriveApi.MetadataBufferResult object) {
                boolean bl;
                if (object.getStatus().isSuccess()) {
                    object = object.getMetadataBuffer().iterator();
                    while (object.hasNext()) {
                        Metadata metadata = (Metadata)object.next();
                        if (!metadata.getTitle().equals(string2)) continue;
                        bl = true;
                        object = metadata.getDriveId().asDriveFolder();
                        break;
                    }
                } else {
                    bl = false;
                    object = null;
                }
                GoogleDriveOp.this.OnFolderQueryResult(bl, (DriveFolder)object);
            }
        });
    }

    public void stop() {
        this.disconnect();
    }

    public void uploadFile(final DriveFolder driveFolder, final String string2, final String string3, final String string4) {
        Drive.DriveApi.newDriveContents(this.googleApiClient).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>(){

            @Override
            public void onResult(DriveApi.DriveContentsResult driveContentsResult) {
                if (!driveContentsResult.getStatus().isSuccess()) return;
                new Thread(driveContentsResult.getDriveContents()){
                    final /* synthetic */ DriveContents val$driveContents;
                    {
                        this.val$driveContents = driveContents;
                    }

                    @Override
                    public void run() {
                        Object object;
                        OutputStream outputStream2 = this.val$driveContents.getOutputStream();
                        try {
                            object = new FileInputStream(string4);
                            FileOp.copyStream((InputStream)object, outputStream2);
                            outputStream2.flush();
                            outputStream2.close();
                            ((InputStream)object).close();
                        }
                        catch (IOException iOException) {
                            Log.e((String)"G-drive upload file", (String)iOException.getMessage());
                        }
                        object = new MetadataChangeSet.Builder().setTitle(string3).setMimeType(string2).setCustomProperty(GoogleDriveOp.this.lastModifiedTime, String.valueOf(FileOp.getFileModifiedTime(string4))).build();
                        driveFolder.createFile(GoogleDriveOp.this.googleApiClient, (MetadataChangeSet)object, this.val$driveContents).setResultCallback(GoogleDriveOp.this.uploadFileCallback);
                    }
                }.start();
            }

        });
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

@Deprecated
public interface DriveApi {
    @Deprecated
    public PendingResult<DriveIdResult> fetchDriveId(GoogleApiClient var1, String var2);

    @Deprecated
    public DriveFolder getAppFolder(GoogleApiClient var1);

    @Deprecated
    public DriveFolder getRootFolder(GoogleApiClient var1);

    @Deprecated
    public CreateFileActivityBuilder newCreateFileActivityBuilder();

    @Deprecated
    public PendingResult<DriveContentsResult> newDriveContents(GoogleApiClient var1);

    @Deprecated
    public OpenFileActivityBuilder newOpenFileActivityBuilder();

    @Deprecated
    public PendingResult<MetadataBufferResult> query(GoogleApiClient var1, Query var2);

    @Deprecated
    public PendingResult<Status> requestSync(GoogleApiClient var1);

    @Deprecated
    public static interface DriveContentsResult
    extends Result {
        public DriveContents getDriveContents();
    }

    @Deprecated
    public static interface DriveIdResult
    extends Result {
        public DriveId getDriveId();
    }

    @Deprecated
    public static interface MetadataBufferResult
    extends Releasable,
    Result {
        public MetadataBuffer getMetadataBuffer();
    }

}


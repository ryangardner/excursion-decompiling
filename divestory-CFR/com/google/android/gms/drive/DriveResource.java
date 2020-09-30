/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeListener;
import java.util.Set;

public interface DriveResource {
    @Deprecated
    public PendingResult<Status> addChangeListener(GoogleApiClient var1, ChangeListener var2);

    @Deprecated
    public PendingResult<Status> addChangeSubscription(GoogleApiClient var1);

    @Deprecated
    public PendingResult<Status> delete(GoogleApiClient var1);

    public DriveId getDriveId();

    @Deprecated
    public PendingResult<MetadataResult> getMetadata(GoogleApiClient var1);

    @Deprecated
    public PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient var1);

    @Deprecated
    public PendingResult<Status> removeChangeListener(GoogleApiClient var1, ChangeListener var2);

    @Deprecated
    public PendingResult<Status> removeChangeSubscription(GoogleApiClient var1);

    @Deprecated
    public PendingResult<Status> setParents(GoogleApiClient var1, Set<DriveId> var2);

    @Deprecated
    public PendingResult<Status> trash(GoogleApiClient var1);

    @Deprecated
    public PendingResult<Status> untrash(GoogleApiClient var1);

    @Deprecated
    public PendingResult<MetadataResult> updateMetadata(GoogleApiClient var1, MetadataChangeSet var2);

    @Deprecated
    public static interface MetadataResult
    extends Result {
        public Metadata getMetadata();
    }

}


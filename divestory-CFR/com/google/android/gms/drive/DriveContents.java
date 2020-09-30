/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.drive;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import java.io.InputStream;
import java.io.OutputStream;

public interface DriveContents {
    @Deprecated
    public PendingResult<Status> commit(GoogleApiClient var1, MetadataChangeSet var2);

    @Deprecated
    public PendingResult<Status> commit(GoogleApiClient var1, MetadataChangeSet var2, ExecutionOptions var3);

    @Deprecated
    public void discard(GoogleApiClient var1);

    public DriveId getDriveId();

    public InputStream getInputStream();

    public int getMode();

    public OutputStream getOutputStream();

    public ParcelFileDescriptor getParcelFileDescriptor();

    @Deprecated
    public PendingResult<DriveApi.DriveContentsResult> reopenForWrite(GoogleApiClient var1);

    public Contents zzi();

    public void zzj();

    public boolean zzk();
}


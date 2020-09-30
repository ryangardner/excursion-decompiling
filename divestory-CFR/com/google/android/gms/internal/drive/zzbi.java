/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.internal.drive;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.zzn;
import com.google.android.gms.drive.zzp;
import com.google.android.gms.internal.drive.zzbj;
import com.google.android.gms.internal.drive.zzbk;
import com.google.android.gms.internal.drive.zzbl;
import com.google.android.gms.internal.drive.zzbm;
import java.io.InputStream;
import java.io.OutputStream;

public final class zzbi
implements DriveContents {
    private static final GmsLogger zzbz = new GmsLogger("DriveContentsImpl", "");
    private boolean closed = false;
    private final Contents zzes;
    private boolean zzet = false;
    private boolean zzeu = false;

    public zzbi(Contents contents) {
        this.zzes = Preconditions.checkNotNull(contents);
    }

    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, zzn zzn2) {
        zzn zzn3 = zzn2;
        if (zzn2 == null) {
            zzn3 = (zzn)((ExecutionOptions.Builder)new zzp()).build();
        }
        if (this.zzes.getMode() == 268435456) throw new IllegalStateException("Cannot commit contents opened with MODE_READ_ONLY");
        if (ExecutionOptions.zza(zzn3.zzn())) {
            if (!this.zzes.zzb()) throw new IllegalStateException("DriveContents must be valid for conflict detection.");
        }
        zzn3.zza(googleApiClient);
        if (this.closed) throw new IllegalStateException("DriveContents already closed.");
        if (this.getDriveId() == null) throw new IllegalStateException("Only DriveContents obtained through DriveFile.open can be committed.");
        if (metadataChangeSet == null) {
            metadataChangeSet = MetadataChangeSet.zzax;
        }
        this.zzj();
        return googleApiClient.execute(new zzbk(this, googleApiClient, metadataChangeSet, zzn3));
    }

    static /* synthetic */ Contents zza(zzbi zzbi2) {
        return zzbi2.zzes;
    }

    static /* synthetic */ GmsLogger zzx() {
        return zzbz;
    }

    @Override
    public final PendingResult<Status> commit(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        return this.zza(googleApiClient, metadataChangeSet, null);
    }

    @Override
    public final PendingResult<Status> commit(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, ExecutionOptions executionOptions) {
        if (executionOptions == null) {
            executionOptions = null;
            return this.zza(googleApiClient, metadataChangeSet, (zzn)executionOptions);
        }
        executionOptions = zzn.zza(executionOptions);
        return this.zza(googleApiClient, metadataChangeSet, (zzn)executionOptions);
    }

    @Override
    public final void discard(GoogleApiClient googleApiClient) {
        if (this.closed) throw new IllegalStateException("DriveContents already closed.");
        this.zzj();
        googleApiClient.execute(new zzbm(this, googleApiClient)).setResultCallback(new zzbl(this));
    }

    @Override
    public final DriveId getDriveId() {
        return this.zzes.getDriveId();
    }

    @Override
    public final InputStream getInputStream() {
        if (this.closed) throw new IllegalStateException("Contents have been closed, cannot access the input stream.");
        if (this.zzes.getMode() != 268435456) throw new IllegalStateException("getInputStream() can only be used with contents opened with MODE_READ_ONLY.");
        if (this.zzet) throw new IllegalStateException("getInputStream() can only be called once per Contents instance.");
        this.zzet = true;
        return this.zzes.getInputStream();
    }

    @Override
    public final int getMode() {
        return this.zzes.getMode();
    }

    @Override
    public final OutputStream getOutputStream() {
        if (this.closed) throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        if (this.zzes.getMode() != 536870912) throw new IllegalStateException("getOutputStream() can only be used with contents opened with MODE_WRITE_ONLY.");
        if (this.zzeu) throw new IllegalStateException("getOutputStream() can only be called once per Contents instance.");
        this.zzeu = true;
        return this.zzes.getOutputStream();
    }

    @Override
    public final ParcelFileDescriptor getParcelFileDescriptor() {
        if (this.closed) throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        return this.zzes.getParcelFileDescriptor();
    }

    @Override
    public final PendingResult<DriveApi.DriveContentsResult> reopenForWrite(GoogleApiClient googleApiClient) {
        if (this.closed) throw new IllegalStateException("DriveContents already closed.");
        if (this.zzes.getMode() != 268435456) throw new IllegalStateException("reopenForWrite can only be used with DriveContents opened with MODE_READ_ONLY.");
        this.zzj();
        return googleApiClient.enqueue(new zzbj(this, googleApiClient));
    }

    @Override
    public final Contents zzi() {
        return this.zzes;
    }

    @Override
    public final void zzj() {
        IOUtils.closeQuietly(this.zzes.getParcelFileDescriptor());
        this.closed = true;
    }

    @Override
    public final boolean zzk() {
        return this.closed;
    }
}


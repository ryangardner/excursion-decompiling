/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzaz;
import com.google.android.gms.internal.drive.zzba;
import com.google.android.gms.internal.drive.zzdq;
import com.google.android.gms.internal.drive.zzdr;
import com.google.android.gms.internal.drive.zzds;
import com.google.android.gms.internal.drive.zzdt;
import com.google.android.gms.internal.drive.zzdu;
import com.google.android.gms.internal.drive.zzdv;
import com.google.android.gms.internal.drive.zzdw;
import com.google.android.gms.internal.drive.zzj;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class zzdp
implements DriveResource {
    protected final DriveId zzk;

    public zzdp(DriveId driveId) {
        this.zzk = driveId;
    }

    @Override
    public PendingResult<Status> addChangeListener(GoogleApiClient googleApiClient, ChangeListener changeListener) {
        return googleApiClient.getClient(Drive.CLIENT_KEY).zza(googleApiClient, this.zzk, changeListener);
    }

    @Override
    public PendingResult<Status> addChangeSubscription(GoogleApiClient googleApiClient) {
        zzaw zzaw2 = googleApiClient.getClient(Drive.CLIENT_KEY);
        zzj zzj2 = new zzj(1, this.zzk);
        Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(zzj2.zzda, zzj2.zzk));
        Preconditions.checkState(zzaw2.isConnected(), "Client must be connected");
        if (!zzaw2.zzec) throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to add event subscriptions");
        return googleApiClient.execute(new zzaz(zzaw2, googleApiClient, zzj2));
    }

    @Override
    public PendingResult<Status> delete(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdu(this, googleApiClient));
    }

    @Override
    public DriveId getDriveId() {
        return this.zzk;
    }

    @Override
    public PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzdq(this, googleApiClient, false));
    }

    @Override
    public PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzdr(this, googleApiClient));
    }

    @Override
    public PendingResult<Status> removeChangeListener(GoogleApiClient googleApiClient, ChangeListener changeListener) {
        return googleApiClient.getClient(Drive.CLIENT_KEY).zzb(googleApiClient, this.zzk, changeListener);
    }

    @Override
    public PendingResult<Status> removeChangeSubscription(GoogleApiClient googleApiClient) {
        zzaw zzaw2 = googleApiClient.getClient(Drive.CLIENT_KEY);
        DriveId driveId = this.zzk;
        Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, driveId));
        Preconditions.checkState(zzaw2.isConnected(), "Client must be connected");
        return googleApiClient.execute(new zzba(zzaw2, googleApiClient, driveId, 1));
    }

    @Override
    public PendingResult<Status> setParents(GoogleApiClient googleApiClient, Set<DriveId> set) {
        if (set == null) throw new IllegalArgumentException("ParentIds must be provided.");
        return googleApiClient.execute(new zzds(this, googleApiClient, new ArrayList<DriveId>(set)));
    }

    @Override
    public PendingResult<Status> trash(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdv(this, googleApiClient));
    }

    @Override
    public PendingResult<Status> untrash(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdw(this, googleApiClient));
    }

    @Override
    public PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) throw new IllegalArgumentException("ChangeSet must be provided.");
        return googleApiClient.execute(new zzdt(this, googleApiClient, metadataChangeSet));
    }
}


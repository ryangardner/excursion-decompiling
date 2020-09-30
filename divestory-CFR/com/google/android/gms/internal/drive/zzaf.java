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
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.internal.drive.zzag;
import com.google.android.gms.internal.drive.zzah;
import com.google.android.gms.internal.drive.zzai;
import com.google.android.gms.internal.drive.zzaj;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbs;

@Deprecated
public final class zzaf
implements DriveApi {
    @Override
    public final PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient googleApiClient, String string2) {
        return googleApiClient.enqueue(new zzai(this, googleApiClient, string2));
    }

    @Override
    public final DriveFolder getAppFolder(GoogleApiClient object) {
        if (!((zzaw)(object = ((GoogleApiClient)object).getClient(Drive.CLIENT_KEY))).zzag()) throw new IllegalStateException("Client is not yet connected");
        if ((object = ((zzaw)object).zzaf()) == null) return null;
        return new zzbs((DriveId)object);
    }

    @Override
    public final DriveFolder getRootFolder(GoogleApiClient object) {
        if (!((zzaw)(object = ((GoogleApiClient)object).getClient(Drive.CLIENT_KEY))).zzag()) throw new IllegalStateException("Client is not yet connected");
        if ((object = ((zzaw)object).zzae()) == null) return null;
        return new zzbs((DriveId)object);
    }

    @Override
    public final CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    @Override
    public final PendingResult<DriveApi.DriveContentsResult> newDriveContents(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzah(this, googleApiClient, 536870912));
    }

    @Override
    public final OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    @Override
    public final PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient googleApiClient, Query query) {
        if (query == null) throw new IllegalArgumentException("Query must be provided.");
        return googleApiClient.enqueue(new zzag(this, googleApiClient, query));
    }

    @Override
    public final PendingResult<Status> requestSync(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzaj(this, googleApiClient));
    }
}


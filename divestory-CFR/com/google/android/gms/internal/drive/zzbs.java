/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.internal.zzk;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.internal.drive.zzaf;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzbt;
import com.google.android.gms.internal.drive.zzbu;
import com.google.android.gms.internal.drive.zzdp;

public final class zzbs
extends zzdp
implements DriveFolder {
    public zzbs(DriveId driveId) {
        super(driveId);
    }

    static int zza(DriveContents driveContents, zzk zzk2) {
        if (driveContents != null) {
            int n = driveContents.zzi().getRequestId();
            driveContents.zzj();
            return n;
        }
        if (zzk2 == null) return 1;
        if (!zzk2.zzbh()) return 1;
        return 0;
    }

    static Query zza(Query query, DriveId object) {
        object = new Query.Builder().addFilter(Filters.in(SearchableField.PARENTS, object));
        if (query == null) return ((Query.Builder)object).build();
        if (query.getFilter() != null) {
            ((Query.Builder)object).addFilter(query.getFilter());
        }
        ((Query.Builder)object).setPageToken(query.getPageToken());
        ((Query.Builder)object).setSortOrder(query.getSortOrder());
        return ((Query.Builder)object).build();
    }

    static void zzb(MetadataChangeSet object) {
        if (object == null) throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        if ((object = zzk.zzg(((MetadataChangeSet)object).getMimeType())) == null) return;
        boolean bl = !((zzk)object).zzbh() && !((zzk)object).isFolder();
        if (!bl) throw new IllegalArgumentException("May not create shortcut files using this method. Use DriveFolder.createShortcutFile() instead.");
    }

    @Override
    public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents driveContents) {
        return this.createFile(googleApiClient, metadataChangeSet, driveContents, null);
    }

    @Override
    public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents object, ExecutionOptions object2) {
        int n;
        ExecutionOptions executionOptions = object2;
        if (object2 == null) {
            executionOptions = new ExecutionOptions.Builder().build();
        }
        if (executionOptions.zzn() != 0) throw new IllegalStateException("May not set a conflict strategy for new file creation.");
        if (metadataChangeSet == null) throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        object2 = zzk.zzg(metadataChangeSet.getMimeType());
        if (object2 != null) {
            if (((zzk)object2).isFolder()) throw new IllegalArgumentException("May not create folders using this method. Use DriveFolder.createFolder() instead of mime type application/vnd.google-apps.folder");
        }
        if (executionOptions == null) throw new IllegalArgumentException("ExecutionOptions must be provided");
        executionOptions.zza(googleApiClient);
        if (object != null) {
            if (!(object instanceof zzbi)) throw new IllegalArgumentException("Only DriveContents obtained from the Drive API are accepted.");
            if (object.getDriveId() != null) throw new IllegalArgumentException("Only DriveContents obtained through DriveApi.newDriveContents are accepted for file creation.");
            if (object.zzk()) throw new IllegalArgumentException("DriveContents are already closed.");
        }
        zzbs.zzb(metadataChangeSet);
        int n2 = zzbs.zza((DriveContents)object, zzk.zzg(metadataChangeSet.getMimeType()));
        object = zzk.zzg(metadataChangeSet.getMimeType());
        if (object != null && ((zzk)object).zzbh()) {
            n = 1;
            return googleApiClient.execute(new zzbt(this, googleApiClient, metadataChangeSet, n2, n, executionOptions));
        }
        n = 0;
        return googleApiClient.execute(new zzbt(this, googleApiClient, metadataChangeSet, n2, n, executionOptions));
    }

    @Override
    public final PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        if (metadataChangeSet.getMimeType() == null) return googleApiClient.execute(new zzbu(this, googleApiClient, metadataChangeSet));
        if (!metadataChangeSet.getMimeType().equals("application/vnd.google-apps.folder")) throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        return googleApiClient.execute(new zzbu(this, googleApiClient, metadataChangeSet));
    }

    @Override
    public final PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient googleApiClient) {
        return this.queryChildren(googleApiClient, null);
    }

    @Override
    public final PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient googleApiClient, Query query) {
        return new zzaf().query(googleApiClient, zzbs.zza(query, this.getDriveId()));
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.metadata.internal.zzk;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhj;
import com.google.android.gms.internal.drive.zzw;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdh
extends TaskApiCall<zzaw, DriveFile> {
    private final DriveFolder zzfj;
    private final MetadataChangeSet zzgc;
    private ExecutionOptions zzgd;
    private String zzge;
    private zzk zzgf;
    private final DriveContents zzo;

    zzdh(DriveFolder object, MetadataChangeSet metadataChangeSet, DriveContents driveContents, ExecutionOptions executionOptions, String string2) {
        this.zzfj = object;
        this.zzgc = metadataChangeSet;
        this.zzo = driveContents;
        this.zzgd = executionOptions;
        this.zzge = null;
        Preconditions.checkNotNull(object, "DriveFolder must not be null");
        Preconditions.checkNotNull(object.getDriveId(), "Folder's DriveId must not be null");
        Preconditions.checkNotNull(metadataChangeSet, "MetadataChangeSet must not be null");
        Preconditions.checkNotNull(executionOptions, "ExecutionOptions must not be null");
        this.zzgf = object = zzk.zzg(metadataChangeSet.getMimeType());
        if (object != null) {
            if (((zzk)object).isFolder()) throw new IllegalArgumentException("May not create folders using this method. Use DriveFolderManagerClient#createFolder() instead of mime type application/vnd.google-apps.folder");
        }
        if (driveContents == null) return;
        if (!(driveContents instanceof zzbi)) throw new IllegalArgumentException("Only DriveContents obtained from the Drive API are accepted.");
        if (driveContents.getDriveId() != null) throw new IllegalArgumentException("Only DriveContents obtained through DriveApi.newDriveContents are accepted for file creation.");
        if (driveContents.zzk()) throw new IllegalArgumentException("DriveContents are already closed.");
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        anyClient = (zzaw)anyClient;
        this.zzgd.zza((zzaw)anyClient);
        Object object = this.zzgc;
        ((MetadataChangeSet)object).zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        int n = zzbs.zza(this.zzo, this.zzgf);
        zzk zzk2 = this.zzgf;
        int n2 = zzk2 != null && zzk2.zzbh() ? 1 : 0;
        object = new zzw(this.zzfj.getDriveId(), ((MetadataChangeSet)object).zzq(), n, n2, this.zzgd);
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza((zzw)object, (zzeq)new zzhj(taskCompletionSource));
    }
}


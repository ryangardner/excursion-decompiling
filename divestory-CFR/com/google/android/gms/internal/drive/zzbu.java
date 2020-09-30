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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzbw;
import com.google.android.gms.internal.drive.zzca;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzy;

final class zzbu
extends zzca {
    private final /* synthetic */ MetadataChangeSet zzfd;
    private final /* synthetic */ zzbs zzfh;

    zzbu(zzbs zzbs2, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        this.zzfh = zzbs2;
        this.zzfd = metadataChangeSet;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        anyClient = (zzaw)anyClient;
        this.zzfd.zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(new zzy(this.zzfh.getDriveId(), this.zzfd.zzq()), (zzeq)new zzbw(this));
    }
}


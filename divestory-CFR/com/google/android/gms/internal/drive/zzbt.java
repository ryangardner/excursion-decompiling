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
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzbv;
import com.google.android.gms.internal.drive.zzby;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzw;

final class zzbt
extends zzby {
    private final /* synthetic */ MetadataChangeSet zzfd;
    private final /* synthetic */ int zzfe;
    private final /* synthetic */ int zzff;
    private final /* synthetic */ ExecutionOptions zzfg;
    private final /* synthetic */ zzbs zzfh;

    zzbt(zzbs zzbs2, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, int n, int n2, ExecutionOptions executionOptions) {
        this.zzfh = zzbs2;
        this.zzfd = metadataChangeSet;
        this.zzfe = n;
        this.zzff = n2;
        this.zzfg = executionOptions;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        anyClient = (zzaw)anyClient;
        this.zzfd.zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        zzw zzw2 = new zzw(this.zzfh.getDriveId(), this.zzfd.zzq(), this.zzfe, this.zzff, this.zzfg);
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(zzw2, (zzeq)new zzbv(this));
    }
}


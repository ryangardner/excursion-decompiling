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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.zzn;
import com.google.android.gms.internal.drive.zzav;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgy;
import com.google.android.gms.internal.drive.zzm;

final class zzbk
extends zzav {
    private final /* synthetic */ zzbi zzev;
    private final /* synthetic */ MetadataChangeSet zzew;
    private final /* synthetic */ zzn zzex;

    zzbk(zzbi zzbi2, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, zzn zzn2) {
        this.zzev = zzbi2;
        this.zzew = metadataChangeSet;
        this.zzex = zzn2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        anyClient = (zzaw)anyClient;
        this.zzew.zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(new zzm(zzbi.zza(this.zzev).getDriveId(), this.zzew.zzq(), zzbi.zza(this.zzev).getRequestId(), zzbi.zza(this.zzev).zzb(), this.zzex), (zzeq)new zzgy(this));
    }
}


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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzdq;
import com.google.android.gms.internal.drive.zzdy;
import com.google.android.gms.internal.drive.zzea;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhf;

final class zzdt
extends zzea {
    private final /* synthetic */ MetadataChangeSet zzfd;
    private final /* synthetic */ zzdp zzgq;

    zzdt(zzdp zzdp2, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        this.zzgq = zzdp2;
        this.zzfd = metadataChangeSet;
        super(zzdp2, googleApiClient, null);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        anyClient = (zzaw)anyClient;
        this.zzfd.zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(new zzhf(this.zzgq.zzk, this.zzfd.zzq()), (zzeq)new zzdy(this));
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzdz;
import com.google.android.gms.internal.drive.zzfy;
import com.google.android.gms.internal.drive.zzl;

final class zzdy
extends zzl {
    private final BaseImplementation.ResultHolder<DriveResource.MetadataResult> zzdx;

    public zzdy(BaseImplementation.ResultHolder<DriveResource.MetadataResult> resultHolder) {
        this.zzdx = resultHolder;
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzdz(status, null));
    }

    @Override
    public final void zza(zzfy zzfy2) throws RemoteException {
        this.zzdx.setResult(new zzdz(Status.RESULT_SUCCESS, new zzaa(zzfy2.zzdn)));
    }
}


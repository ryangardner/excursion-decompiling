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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.internal.drive.zzaq;
import com.google.android.gms.internal.drive.zzfv;
import com.google.android.gms.internal.drive.zzl;

final class zzdx
extends zzl {
    private final BaseImplementation.ResultHolder<DriveApi.MetadataBufferResult> zzdx;

    public zzdx(BaseImplementation.ResultHolder<DriveApi.MetadataBufferResult> resultHolder) {
        this.zzdx = resultHolder;
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzaq(status, null, false));
    }

    @Override
    public final void zza(zzfv object) throws RemoteException {
        object = new MetadataBuffer(((zzfv)object).zzij);
        this.zzdx.setResult(new zzaq(Status.RESULT_SUCCESS, (MetadataBuffer)object, false));
    }
}


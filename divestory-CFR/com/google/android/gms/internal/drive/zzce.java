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
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.internal.drive.zzcb;
import com.google.android.gms.internal.drive.zzcc;
import com.google.android.gms.internal.drive.zzcf;
import com.google.android.gms.internal.drive.zzei;
import com.google.android.gms.internal.drive.zzfj;
import com.google.android.gms.internal.drive.zzl;

final class zzce
extends zzl {
    private final BaseImplementation.ResultHolder<DrivePreferencesApi.FileUploadPreferencesResult> zzdx;
    private final /* synthetic */ zzcb zzfk;

    private zzce(BaseImplementation.ResultHolder<DrivePreferencesApi.FileUploadPreferencesResult> resultHolder) {
        this.zzfk = var1_1;
        this.zzdx = resultHolder;
    }

    /* synthetic */ zzce(zzcb zzcb2, BaseImplementation.ResultHolder resultHolder, zzcc zzcc2) {
        this(zzcb2, resultHolder);
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzcf(this.zzfk, status, null, null));
    }

    @Override
    public final void zza(zzfj zzfj2) throws RemoteException {
        this.zzdx.setResult(new zzcf(this.zzfk, Status.RESULT_SUCCESS, zzfj2.zzhw, null));
    }
}


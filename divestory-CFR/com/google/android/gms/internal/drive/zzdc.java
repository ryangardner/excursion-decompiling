/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzek;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhp;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdc
extends TaskApiCall<zzaw, Metadata> {
    private final /* synthetic */ DriveResource zzfq;
    private final /* synthetic */ boolean zzga;

    zzdc(zzch zzch2, DriveResource driveResource, boolean bl) {
        this.zzfq = driveResource;
        this.zzga = false;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzek(this.zzfq.getDriveId(), this.zzga), (zzeq)new zzhp(taskCompletionSource));
    }
}


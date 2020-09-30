/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzdi;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.internal.drive.zzj;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcp
extends RegisterListenerMethod<zzaw, zzdi> {
    private final /* synthetic */ DriveResource zzfq;
    private final /* synthetic */ zzdi zzfr;

    zzcp(zzch zzch2, ListenerHolder listenerHolder, DriveResource driveResource, zzdi zzdi2) {
        this.zzfq = driveResource;
        this.zzfr = zzdi2;
        super(listenerHolder);
    }

    @Override
    protected final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzj(1, this.zzfq.getDriveId()), (zzes)zzdi.zza(this.zzfr), null, (zzeq)new zzhr(taskCompletionSource));
    }
}


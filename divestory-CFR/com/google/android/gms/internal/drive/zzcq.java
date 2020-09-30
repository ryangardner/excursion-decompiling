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
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzdi;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzgs;
import com.google.android.gms.internal.drive.zzhq;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcq
extends UnregisterListenerMethod<zzaw, zzdi> {
    private final /* synthetic */ DriveResource zzfq;
    private final /* synthetic */ zzdi zzfr;

    zzcq(zzch zzch2, ListenerHolder.ListenerKey listenerKey, DriveResource driveResource, zzdi zzdi2) {
        this.zzfq = driveResource;
        this.zzfr = zzdi2;
        super(listenerKey);
    }

    @Override
    protected final /* synthetic */ void unregisterListener(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgs(this.zzfq.getDriveId(), 1), (zzes)zzdi.zza(this.zzfr), null, (zzeq)new zzhq(taskCompletionSource));
    }
}


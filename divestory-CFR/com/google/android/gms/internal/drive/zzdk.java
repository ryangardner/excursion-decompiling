/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.events.ListenerToken;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzdg;
import com.google.android.gms.internal.drive.zzdl;
import com.google.android.gms.internal.drive.zzdm;
import com.google.android.gms.internal.drive.zzdn;
import com.google.android.gms.internal.drive.zzdo;
import com.google.android.gms.internal.drive.zzfh;
import com.google.android.gms.internal.drive.zzfl;
import com.google.android.gms.internal.drive.zzl;
import com.google.android.gms.tasks.Task;

final class zzdk
extends zzl {
    private final /* synthetic */ zzch zzfw;
    private final ListenerToken zzgj;
    private final ListenerHolder<OpenFileCallback> zzgk;

    zzdk(ListenerToken listenerToken, ListenerHolder<OpenFileCallback> listenerHolder) {
        this.zzfw = var1_1;
        this.zzgj = listenerToken;
        this.zzgk = listenerHolder;
    }

    private final void zza(zzdg<OpenFileCallback> zzdg2) {
        this.zzgk.notifyListener(new zzdo(this, zzdg2));
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zza(new zzdl(this, status));
    }

    final /* synthetic */ void zza(Status status, OpenFileCallback openFileCallback) {
        openFileCallback.onError(ApiExceptionUtil.fromStatus(status));
        ((DriveResourceClient)this.zzfw).cancelOpenFileCallback(this.zzgj);
    }

    @Override
    public final void zza(zzfh zzfh2) throws RemoteException {
        this.zza(new zzdn(this, zzfh2));
    }

    final /* synthetic */ void zza(zzfh zzfh2, OpenFileCallback openFileCallback) {
        openFileCallback.onContents(new zzbi(zzfh2.zzes));
        ((DriveResourceClient)this.zzfw).cancelOpenFileCallback(this.zzgj);
    }

    @Override
    public final void zza(zzfl zzfl2) throws RemoteException {
        this.zza(new zzdm(zzfl2));
    }
}


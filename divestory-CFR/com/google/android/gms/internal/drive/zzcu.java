/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ListenerToken;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzdk;
import com.google.android.gms.internal.drive.zzec;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzg;
import com.google.android.gms.internal.drive.zzgj;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcu
extends RegisterListenerMethod<zzaw, OpenFileCallback> {
    private final /* synthetic */ DriveFile zzfs;
    private final /* synthetic */ int zzft;
    private final /* synthetic */ zzg zzfu;
    private final /* synthetic */ ListenerHolder zzfv;
    private final /* synthetic */ zzch zzfw;

    zzcu(zzch zzch2, ListenerHolder listenerHolder, DriveFile driveFile, int n, zzg zzg2, ListenerHolder listenerHolder2) {
        this.zzfw = zzch2;
        this.zzfs = driveFile;
        this.zzft = n;
        this.zzfu = zzg2;
        this.zzfv = listenerHolder2;
        super(listenerHolder);
    }

    @Override
    protected final /* synthetic */ void registerListener(Api.AnyClient object, TaskCompletionSource taskCompletionSource) throws RemoteException {
        object = (zzaw)object;
        zzgj zzgj2 = new zzgj(this.zzfs.getDriveId(), this.zzft, 0);
        object = ((zzeo)((BaseGmsClient)object).getService()).zza(zzgj2, (zzeq)new zzdk(this.zzfw, (ListenerToken)this.zzfu, this.zzfv));
        this.zzfu.setCancelToken(ICancelToken.Stub.asInterface(((zzec)object).zzgs));
        taskCompletionSource.setResult(null);
    }
}


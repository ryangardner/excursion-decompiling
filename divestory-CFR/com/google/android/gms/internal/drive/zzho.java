/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.internal.drive.zzfv;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzho
extends zzhh<MetadataBuffer> {
    public zzho(TaskCompletionSource<MetadataBuffer> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void zza(zzfv zzfv2) throws RemoteException {
        this.zzay().setResult(new MetadataBuffer(zzfv2.zzav()));
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzfh;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhi
extends zzhh<DriveContents> {
    public zzhi(TaskCompletionSource<DriveContents> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void zza(zzfh zzfh2) throws RemoteException {
        this.zzay().setResult(new zzbi(zzfh2.zzar()));
    }
}


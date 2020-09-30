/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzfn;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhk
extends zzhh<DriveFolder> {
    public zzhk(TaskCompletionSource<DriveFolder> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void zza(zzfn zzfn2) throws RemoteException {
        this.zzay().setResult(zzfn2.getDriveId().asDriveFolder());
    }
}


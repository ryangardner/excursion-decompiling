/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzfn;
import com.google.android.gms.internal.drive.zzfy;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhl
extends zzhh<DriveId> {
    public zzhl(TaskCompletionSource<DriveId> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void zza(zzfn zzfn2) throws RemoteException {
        this.zzay().setResult(zzfn2.getDriveId());
    }

    @Override
    public final void zza(zzfy zzfy2) throws RemoteException {
        this.zzay().setResult(new zzaa(zzfy2.zzaw()).getDriveId());
    }
}


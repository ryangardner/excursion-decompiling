/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.drive.TransferPreferencesBuilder;
import com.google.android.gms.internal.drive.zzei;
import com.google.android.gms.internal.drive.zzfj;
import com.google.android.gms.internal.drive.zzga;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhm
extends zzhh<TransferPreferences> {
    public zzhm(TaskCompletionSource<TransferPreferences> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void zza(zzfj zzfj2) throws RemoteException {
        this.zzay().setResult(new TransferPreferencesBuilder(zzfj2.zzas()).build());
    }

    @Override
    public final void zza(zzga zzga2) throws RemoteException {
        this.zzay().setResult(zzga2.zzax());
    }
}


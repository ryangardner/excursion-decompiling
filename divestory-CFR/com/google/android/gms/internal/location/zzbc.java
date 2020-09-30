/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.location.zzar;
import com.google.android.gms.location.LocationSettingsResult;

final class zzbc
extends zzar {
    private BaseImplementation.ResultHolder<LocationSettingsResult> zzdf;

    public zzbc(BaseImplementation.ResultHolder<LocationSettingsResult> resultHolder) {
        boolean bl = resultHolder != null;
        Preconditions.checkArgument(bl, "listener can't be null.");
        this.zzdf = resultHolder;
    }

    @Override
    public final void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
        this.zzdf.setResult(locationSettingsResult);
        this.zzdf = null;
    }
}


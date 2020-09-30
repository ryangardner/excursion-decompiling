/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.IInterface
 */
package com.google.android.gms.internal.location;

import android.os.DeadObjectException;
import android.os.IInterface;
import com.google.android.gms.internal.location.zzao;
import com.google.android.gms.internal.location.zzbj;
import com.google.android.gms.internal.location.zzk;

final class zzl
implements zzbj<zzao> {
    private final /* synthetic */ zzk zzcc;

    zzl(zzk zzk2) {
        this.zzcc = zzk2;
    }

    @Override
    public final void checkConnected() {
        zzk.zza(this.zzcc);
    }

    @Override
    public final /* synthetic */ IInterface getService() throws DeadObjectException {
        return (zzao)this.zzcc.getService();
    }
}


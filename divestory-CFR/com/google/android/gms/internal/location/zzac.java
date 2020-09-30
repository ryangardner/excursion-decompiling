/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.location.zzad;
import com.google.android.gms.internal.location.zzak;

final class zzac
extends zzak {
    private final BaseImplementation.ResultHolder<Status> zzcq;

    public zzac(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.zzcq = resultHolder;
    }

    @Override
    public final void zza(zzad zzad2) {
        this.zzcq.setResult(zzad2.getStatus());
    }
}


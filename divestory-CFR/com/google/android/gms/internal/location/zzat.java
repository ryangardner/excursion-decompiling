/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.location.zzau;
import com.google.android.gms.internal.location.zzav;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzv;

final class zzat
extends zzv {
    private final ListenerHolder<LocationCallback> zzda;

    zzat(ListenerHolder<LocationCallback> listenerHolder) {
        this.zzda = listenerHolder;
    }

    @Override
    public final void onLocationAvailability(LocationAvailability locationAvailability) {
        this.zzda.notifyListener(new zzav(this, locationAvailability));
    }

    @Override
    public final void onLocationResult(LocationResult locationResult) {
        this.zzda.notifyListener(new zzau(this, locationResult));
    }

    public final void release() {
        synchronized (this) {
            this.zzda.clear();
            return;
        }
    }
}


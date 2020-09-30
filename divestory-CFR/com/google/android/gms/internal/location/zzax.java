/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.location.zzay;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.zzy;

final class zzax
extends zzy {
    private final ListenerHolder<LocationListener> zzda;

    zzax(ListenerHolder<LocationListener> listenerHolder) {
        this.zzda = listenerHolder;
    }

    @Override
    public final void onLocationChanged(Location location) {
        synchronized (this) {
            ListenerHolder<LocationListener> listenerHolder = this.zzda;
            zzay zzay2 = new zzay(this, location);
            listenerHolder.notifyListener(zzay2);
            return;
        }
    }

    public final void release() {
        synchronized (this) {
            this.zzda.clear();
            return;
        }
    }
}


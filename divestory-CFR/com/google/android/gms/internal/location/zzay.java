/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.location.zzax;
import com.google.android.gms.location.LocationListener;

final class zzay
implements ListenerHolder.Notifier<LocationListener> {
    private final /* synthetic */ Location zzdd;

    zzay(zzax zzax2, Location location) {
        this.zzdd = location;
    }

    @Override
    public final /* synthetic */ void notifyListener(Object object) {
        ((LocationListener)object).onLocationChanged(this.zzdd);
    }

    @Override
    public final void onNotifyListenerFailed() {
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.location.zzat;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;

final class zzav
implements ListenerHolder.Notifier<LocationCallback> {
    private final /* synthetic */ LocationAvailability zzdc;

    zzav(zzat zzat2, LocationAvailability locationAvailability) {
        this.zzdc = locationAvailability;
    }

    @Override
    public final /* synthetic */ void notifyListener(Object object) {
        ((LocationCallback)object).onLocationAvailability(this.zzdc);
    }

    @Override
    public final void onNotifyListenerFailed() {
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.location.zzat;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

final class zzau
implements ListenerHolder.Notifier<LocationCallback> {
    private final /* synthetic */ LocationResult zzdb;

    zzau(zzat zzat2, LocationResult locationResult) {
        this.zzdb = locationResult;
    }

    @Override
    public final /* synthetic */ void notifyListener(Object object) {
        ((LocationCallback)object).onLocationResult(this.zzdb);
    }

    @Override
    public final void onNotifyListenerFailed() {
    }
}


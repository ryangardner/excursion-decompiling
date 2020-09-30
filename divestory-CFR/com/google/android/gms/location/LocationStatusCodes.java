/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.location;

import com.google.android.gms.common.api.Status;

@Deprecated
public final class LocationStatusCodes {
    public static final int ERROR = 1;
    public static final int GEOFENCE_NOT_AVAILABLE = 1000;
    public static final int GEOFENCE_TOO_MANY_GEOFENCES = 1001;
    public static final int GEOFENCE_TOO_MANY_PENDING_INTENTS = 1002;
    public static final int SUCCESS = 0;

    private LocationStatusCodes() {
    }

    public static int zzc(int n) {
        if (n >= 0) {
            if (n <= 1) return n;
        }
        if (1000 > n) return 1;
        if (n > 1002) return 1;
        return n;
    }

    public static Status zzd(int n) {
        if (n != 1) {
            return new Status(n);
        }
        n = 13;
        return new Status(n);
    }
}


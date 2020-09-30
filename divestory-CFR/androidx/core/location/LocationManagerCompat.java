/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.location.LocationManager
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.location;

import android.location.LocationManager;
import android.os.Build;

public final class LocationManagerCompat {
    private LocationManagerCompat() {
    }

    public static boolean isLocationEnabled(LocationManager locationManager) {
        if (Build.VERSION.SDK_INT >= 28) {
            return locationManager.isLocationEnabled();
        }
        if (locationManager.isProviderEnabled("network")) return true;
        if (locationManager.isProviderEnabled("gps")) return true;
        return false;
    }
}


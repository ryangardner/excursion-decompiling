/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Looper
 */
package com.google.android.gms.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

@Deprecated
public interface FusedLocationProviderApi {
    @Deprecated
    public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
    public static final String KEY_MOCK_LOCATION = "mockLocation";

    public PendingResult<Status> flushLocations(GoogleApiClient var1);

    public Location getLastLocation(GoogleApiClient var1);

    public LocationAvailability getLocationAvailability(GoogleApiClient var1);

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient var1, PendingIntent var2);

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient var1, LocationCallback var2);

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient var1, LocationListener var2);

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, PendingIntent var3);

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationCallback var3, Looper var4);

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationListener var3);

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient var1, LocationRequest var2, LocationListener var3, Looper var4);

    public PendingResult<Status> setMockLocation(GoogleApiClient var1, Location var2);

    public PendingResult<Status> setMockMode(GoogleApiClient var1, boolean var2);
}


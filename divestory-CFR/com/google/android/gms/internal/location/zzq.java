/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Looper
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.location.zzaa;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzr;
import com.google.android.gms.internal.location.zzs;
import com.google.android.gms.internal.location.zzt;
import com.google.android.gms.internal.location.zzu;
import com.google.android.gms.internal.location.zzv;
import com.google.android.gms.internal.location.zzw;
import com.google.android.gms.internal.location.zzx;
import com.google.android.gms.internal.location.zzy;
import com.google.android.gms.internal.location.zzz;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public final class zzq
implements FusedLocationProviderApi {
    @Override
    public final PendingResult<Status> flushLocations(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzv(this, googleApiClient));
    }

    @Override
    public final Location getLastLocation(GoogleApiClient object) {
        object = LocationServices.zza((GoogleApiClient)object);
        try {
            return ((zzaz)object).getLastLocation();
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public final LocationAvailability getLocationAvailability(GoogleApiClient object) {
        object = LocationServices.zza((GoogleApiClient)object);
        try {
            return ((zzaz)object).zza();
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzaa(this, googleApiClient, pendingIntent));
    }

    @Override
    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        return googleApiClient.execute(new zzs(this, googleApiClient, locationCallback));
    }

    @Override
    public final PendingResult<Status> removeLocationUpdates(GoogleApiClient googleApiClient, LocationListener locationListener) {
        return googleApiClient.execute(new zzz(this, googleApiClient, locationListener));
    }

    @Override
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzy(this, googleApiClient, locationRequest, pendingIntent));
    }

    @Override
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        return googleApiClient.execute(new zzx(this, googleApiClient, locationRequest, locationCallback, looper));
    }

    @Override
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
        Preconditions.checkNotNull(Looper.myLooper(), "Calling thread must be a prepared Looper thread.");
        return googleApiClient.execute(new zzr(this, googleApiClient, locationRequest, locationListener));
    }

    @Override
    public final PendingResult<Status> requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        return googleApiClient.execute(new zzw(this, googleApiClient, locationRequest, locationListener, looper));
    }

    @Override
    public final PendingResult<Status> setMockLocation(GoogleApiClient googleApiClient, Location location) {
        return googleApiClient.execute(new zzu(this, googleApiClient, location));
    }

    @Override
    public final PendingResult<Status> setMockMode(GoogleApiClient googleApiClient, boolean bl) {
        return googleApiClient.execute(new zzt(this, googleApiClient, bl));
    }
}


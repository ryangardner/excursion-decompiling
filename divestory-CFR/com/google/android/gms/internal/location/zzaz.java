/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.location.Location
 *  android.os.Looper
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.StatusCallback;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzam;
import com.google.android.gms.internal.location.zzao;
import com.google.android.gms.internal.location.zzaq;
import com.google.android.gms.internal.location.zzas;
import com.google.android.gms.internal.location.zzba;
import com.google.android.gms.internal.location.zzbb;
import com.google.android.gms.internal.location.zzbc;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.internal.location.zzbj;
import com.google.android.gms.internal.location.zzk;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.zzal;
import javax.annotation.Nullable;

public final class zzaz
extends zzk {
    private final zzas zzde;

    public zzaz(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String string2) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, string2, ClientSettings.createDefault(context));
    }

    public zzaz(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, String string2, @Nullable ClientSettings clientSettings) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, string2, clientSettings);
        this.zzde = new zzas(context, this.zzcb);
    }

    @Override
    public final void disconnect() {
        zzas zzas2 = this.zzde;
        synchronized (zzas2) {
            boolean bl = this.isConnected();
            if (bl) {
                try {
                    this.zzde.removeAllListeners();
                    this.zzde.zzb();
                }
                catch (Exception exception) {
                    Log.e((String)"LocationClientImpl", (String)"Client disconnected before listeners could be cleaned up", (Throwable)exception);
                }
            }
            super.disconnect();
            return;
        }
    }

    public final Location getLastLocation() throws RemoteException {
        return this.zzde.getLastLocation();
    }

    public final LocationAvailability zza() throws RemoteException {
        return this.zzde.zza();
    }

    public final void zza(long l, PendingIntent pendingIntent) throws RemoteException {
        this.checkConnected();
        Preconditions.checkNotNull(pendingIntent);
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl, "detectionIntervalMillis must be >= 0");
        ((zzao)this.getService()).zza(l, true, pendingIntent);
    }

    public final void zza(PendingIntent pendingIntent, BaseImplementation.ResultHolder<Status> object) throws RemoteException {
        this.checkConnected();
        Preconditions.checkNotNull(object, "ResultHolder not provided.");
        object = new StatusCallback((BaseImplementation.ResultHolder<Status>)object);
        ((zzao)this.getService()).zza(pendingIntent, (IStatusCallback)object);
    }

    public final void zza(PendingIntent pendingIntent, zzaj zzaj2) throws RemoteException {
        this.zzde.zza(pendingIntent, zzaj2);
    }

    public final void zza(Location location) throws RemoteException {
        this.zzde.zza(location);
    }

    public final void zza(ListenerHolder.ListenerKey<LocationListener> listenerKey, zzaj zzaj2) throws RemoteException {
        this.zzde.zza(listenerKey, zzaj2);
    }

    public final void zza(zzaj zzaj2) throws RemoteException {
        this.zzde.zza(zzaj2);
    }

    public final void zza(zzbd zzbd2, ListenerHolder<LocationCallback> listenerHolder, zzaj zzaj2) throws RemoteException {
        zzas zzas2 = this.zzde;
        synchronized (zzas2) {
            this.zzde.zza(zzbd2, listenerHolder, zzaj2);
            return;
        }
    }

    public final void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, BaseImplementation.ResultHolder<Status> object) throws RemoteException {
        this.checkConnected();
        Preconditions.checkNotNull(object, "ResultHolder not provided.");
        object = new StatusCallback((BaseImplementation.ResultHolder<Status>)object);
        ((zzao)this.getService()).zza(activityTransitionRequest, pendingIntent, (IStatusCallback)object);
    }

    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, BaseImplementation.ResultHolder<Status> object) throws RemoteException {
        this.checkConnected();
        Preconditions.checkNotNull(geofencingRequest, "geofencingRequest can't be null.");
        Preconditions.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        Preconditions.checkNotNull(object, "ResultHolder not provided.");
        object = new zzba((BaseImplementation.ResultHolder<Status>)object);
        ((zzao)this.getService()).zza(geofencingRequest, pendingIntent, (zzam)object);
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzaj zzaj2) throws RemoteException {
        this.zzde.zza(locationRequest, pendingIntent, zzaj2);
    }

    public final void zza(LocationRequest locationRequest, ListenerHolder<LocationListener> listenerHolder, zzaj zzaj2) throws RemoteException {
        zzas zzas2 = this.zzde;
        synchronized (zzas2) {
            this.zzde.zza(locationRequest, listenerHolder, zzaj2);
            return;
        }
    }

    public final void zza(LocationSettingsRequest locationSettingsRequest, BaseImplementation.ResultHolder<LocationSettingsResult> object, @Nullable String string2) throws RemoteException {
        this.checkConnected();
        boolean bl = true;
        boolean bl2 = locationSettingsRequest != null;
        Preconditions.checkArgument(bl2, "locationSettingsRequest can't be null nor empty.");
        bl2 = object != null ? bl : false;
        Preconditions.checkArgument(bl2, "listener can't be null.");
        object = new zzbc((BaseImplementation.ResultHolder<LocationSettingsResult>)object);
        ((zzao)this.getService()).zza(locationSettingsRequest, (zzaq)object, string2);
    }

    public final void zza(zzal zzal2, BaseImplementation.ResultHolder<Status> object) throws RemoteException {
        this.checkConnected();
        Preconditions.checkNotNull(zzal2, "removeGeofencingRequest can't be null.");
        Preconditions.checkNotNull(object, "ResultHolder not provided.");
        object = new zzbb((BaseImplementation.ResultHolder<Status>)object);
        ((zzao)this.getService()).zza(zzal2, (zzam)object);
    }

    public final void zza(boolean bl) throws RemoteException {
        this.zzde.zza(bl);
    }

    public final void zzb(PendingIntent pendingIntent) throws RemoteException {
        this.checkConnected();
        Preconditions.checkNotNull(pendingIntent);
        ((zzao)this.getService()).zzb(pendingIntent);
    }

    public final void zzb(ListenerHolder.ListenerKey<LocationCallback> listenerKey, zzaj zzaj2) throws RemoteException {
        this.zzde.zzb(listenerKey, zzaj2);
    }
}


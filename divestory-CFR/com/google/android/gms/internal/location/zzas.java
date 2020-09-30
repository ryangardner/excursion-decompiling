/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ContentProviderClient
 *  android.content.Context
 *  android.location.Location
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzao;
import com.google.android.gms.internal.location.zzat;
import com.google.android.gms.internal.location.zzaw;
import com.google.android.gms.internal.location.zzax;
import com.google.android.gms.internal.location.zzb;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.internal.location.zzbf;
import com.google.android.gms.internal.location.zzbj;
import com.google.android.gms.internal.location.zzm;
import com.google.android.gms.internal.location.zzo;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzx;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class zzas {
    private final zzbj<zzao> zzcb;
    private final Context zzcu;
    private ContentProviderClient zzcv = null;
    private boolean zzcw = false;
    private final Map<ListenerHolder.ListenerKey<LocationListener>, zzax> zzcx = new HashMap<ListenerHolder.ListenerKey<LocationListener>, zzax>();
    private final Map<ListenerHolder.ListenerKey<Object>, zzaw> zzcy = new HashMap<ListenerHolder.ListenerKey<Object>, zzaw>();
    private final Map<ListenerHolder.ListenerKey<LocationCallback>, zzat> zzcz = new HashMap<ListenerHolder.ListenerKey<LocationCallback>, zzat>();

    public zzas(Context context, zzbj<zzao> zzbj2) {
        this.zzcu = context;
        this.zzcb = zzbj2;
    }

    private final zzax zza(ListenerHolder<LocationListener> listenerHolder) {
        Map<ListenerHolder.ListenerKey<LocationListener>, zzax> map = this.zzcx;
        synchronized (map) {
            zzax zzax2;
            zzax zzax3 = zzax2 = this.zzcx.get(listenerHolder.getListenerKey());
            if (zzax2 == null) {
                zzax3 = new zzax(listenerHolder);
            }
            this.zzcx.put(listenerHolder.getListenerKey(), zzax3);
            return zzax3;
        }
    }

    private final zzat zzb(ListenerHolder<LocationCallback> listenerHolder) {
        Map<ListenerHolder.ListenerKey<LocationCallback>, zzat> map = this.zzcz;
        synchronized (map) {
            zzat zzat2;
            zzat zzat3 = zzat2 = this.zzcz.get(listenerHolder.getListenerKey());
            if (zzat2 == null) {
                zzat3 = new zzat(listenerHolder);
            }
            this.zzcz.put(listenerHolder.getListenerKey(), zzat3);
            return zzat3;
        }
    }

    public final Location getLastLocation() throws RemoteException {
        this.zzcb.checkConnected();
        return this.zzcb.getService().zza(this.zzcu.getPackageName());
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void removeAllListeners() throws RemoteException {
        Map<ListenerHolder.ListenerKey<Object>, zzb> map = this.zzcx;
        synchronized (map) {
            for (Object object : this.zzcx.values()) {
                if (object == null) continue;
                this.zzcb.getService().zza(zzbf.zza((zzx)object, null));
            }
            this.zzcx.clear();
        }
        map = this.zzcz;
        synchronized (map) {
            for (Object object2 : this.zzcz.values()) {
                if (object2 == null) continue;
                this.zzcb.getService().zza(zzbf.zza((zzu)object2, null));
            }
            this.zzcz.clear();
        }
        map = this.zzcy;
        synchronized (map) {
            Object object;
            object = this.zzcy.values().iterator();
            do {
                Object object2;
                if (!object.hasNext()) {
                    this.zzcy.clear();
                    return;
                }
                zzaw zzaw2 = object.next();
                if (zzaw2 == null) continue;
                zzao zzao2 = this.zzcb.getService();
                object2 = new zzo(2, null, zzaw2.asBinder(), null);
                zzao2.zza((zzo)object2);
            } while (true);
        }
    }

    public final LocationAvailability zza() throws RemoteException {
        this.zzcb.checkConnected();
        return this.zzcb.getService().zzb(this.zzcu.getPackageName());
    }

    public final void zza(PendingIntent pendingIntent, zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        zzao zzao2 = this.zzcb.getService();
        zzaj2 = zzaj2 != null ? zzaj2.asBinder() : null;
        zzao2.zza(new zzbf(2, null, null, pendingIntent, null, (IBinder)zzaj2));
    }

    public final void zza(Location location) throws RemoteException {
        this.zzcb.checkConnected();
        this.zzcb.getService().zza(location);
    }

    public final void zza(ListenerHolder.ListenerKey<LocationListener> object, zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        Preconditions.checkNotNull(object, "Invalid null listener key");
        Map<ListenerHolder.ListenerKey<LocationListener>, zzax> map = this.zzcx;
        synchronized (map) {
            object = this.zzcx.remove(object);
            if (object == null) return;
            ((zzax)object).release();
            this.zzcb.getService().zza(zzbf.zza((zzx)object, zzaj2));
            return;
        }
    }

    public final void zza(zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        this.zzcb.getService().zza(zzaj2);
    }

    public final void zza(zzbd zzbd2, ListenerHolder<LocationCallback> object, zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        object = this.zzb((ListenerHolder<LocationCallback>)object);
        zzao zzao2 = this.zzcb.getService();
        IBinder iBinder = object.asBinder();
        object = zzaj2 != null ? zzaj2.asBinder() : null;
        zzao2.zza(new zzbf(1, zzbd2, null, null, iBinder, (IBinder)object));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        zzao zzao2 = this.zzcb.getService();
        zzbd zzbd2 = zzbd.zza(locationRequest);
        locationRequest = zzaj2 != null ? zzaj2.asBinder() : null;
        zzao2.zza(new zzbf(1, zzbd2, null, pendingIntent, null, (IBinder)locationRequest));
    }

    public final void zza(LocationRequest locationRequest, ListenerHolder<LocationListener> object, zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        zzax zzax2 = this.zza((ListenerHolder<LocationListener>)object);
        object = this.zzcb.getService();
        zzbd zzbd2 = zzbd.zza(locationRequest);
        zzax2 = zzax2.asBinder();
        locationRequest = zzaj2 != null ? zzaj2.asBinder() : null;
        object.zza(new zzbf(1, zzbd2, (IBinder)zzax2, null, null, (IBinder)locationRequest));
    }

    public final void zza(boolean bl) throws RemoteException {
        this.zzcb.checkConnected();
        this.zzcb.getService().zza(bl);
        this.zzcw = bl;
    }

    public final void zzb() throws RemoteException {
        if (!this.zzcw) return;
        this.zza(false);
    }

    public final void zzb(ListenerHolder.ListenerKey<LocationCallback> object, zzaj zzaj2) throws RemoteException {
        this.zzcb.checkConnected();
        Preconditions.checkNotNull(object, "Invalid null listener key");
        Map<ListenerHolder.ListenerKey<LocationCallback>, zzat> map = this.zzcz;
        synchronized (map) {
            object = this.zzcz.remove(object);
            if (object == null) return;
            ((zzat)object).release();
            this.zzcb.getService().zza(zzbf.zza((zzu)object, zzaj2));
            return;
        }
    }
}


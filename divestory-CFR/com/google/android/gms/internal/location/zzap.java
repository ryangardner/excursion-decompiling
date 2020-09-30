/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.internal.location.zza;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzam;
import com.google.android.gms.internal.location.zzao;
import com.google.android.gms.internal.location.zzaq;
import com.google.android.gms.internal.location.zzbf;
import com.google.android.gms.internal.location.zzc;
import com.google.android.gms.internal.location.zzo;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzal;

public final class zzap
extends zza
implements zzao {
    zzap(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    @Override
    public final Location zza(String string2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        parcel.writeString(string2);
        parcel = this.transactAndReadException(21, parcel);
        string2 = (Location)zzc.zza(parcel, Location.CREATOR);
        parcel.recycle();
        return string2;
    }

    @Override
    public final void zza(long l, boolean bl, PendingIntent pendingIntent) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        parcel.writeLong(l);
        zzc.zza(parcel, true);
        zzc.zza(parcel, (Parcelable)pendingIntent);
        this.transactAndReadExceptionReturnVoid(5, parcel);
    }

    @Override
    public final void zza(PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, (Parcelable)pendingIntent);
        zzc.zza(parcel, iStatusCallback);
        this.transactAndReadExceptionReturnVoid(73, parcel);
    }

    @Override
    public final void zza(Location location) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, (Parcelable)location);
        this.transactAndReadExceptionReturnVoid(13, parcel);
    }

    @Override
    public final void zza(zzaj zzaj2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, zzaj2);
        this.transactAndReadExceptionReturnVoid(67, parcel);
    }

    @Override
    public final void zza(zzbf zzbf2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, zzbf2);
        this.transactAndReadExceptionReturnVoid(59, parcel);
    }

    @Override
    public final void zza(zzo zzo2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, zzo2);
        this.transactAndReadExceptionReturnVoid(75, parcel);
    }

    @Override
    public final void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, activityTransitionRequest);
        zzc.zza(parcel, (Parcelable)pendingIntent);
        zzc.zza(parcel, iStatusCallback);
        this.transactAndReadExceptionReturnVoid(72, parcel);
    }

    @Override
    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzam zzam2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, geofencingRequest);
        zzc.zza(parcel, (Parcelable)pendingIntent);
        zzc.zza(parcel, zzam2);
        this.transactAndReadExceptionReturnVoid(57, parcel);
    }

    @Override
    public final void zza(LocationSettingsRequest locationSettingsRequest, zzaq zzaq2, String string2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, locationSettingsRequest);
        zzc.zza(parcel, zzaq2);
        parcel.writeString(string2);
        this.transactAndReadExceptionReturnVoid(63, parcel);
    }

    @Override
    public final void zza(zzal zzal2, zzam zzam2) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, zzal2);
        zzc.zza(parcel, zzam2);
        this.transactAndReadExceptionReturnVoid(74, parcel);
    }

    @Override
    public final void zza(boolean bl) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, bl);
        this.transactAndReadExceptionReturnVoid(12, parcel);
    }

    @Override
    public final LocationAvailability zzb(String string2) throws RemoteException {
        Object object = this.obtainAndWriteInterfaceToken();
        object.writeString(string2);
        string2 = this.transactAndReadException(34, (Parcel)object);
        object = zzc.zza((Parcel)string2, LocationAvailability.CREATOR);
        string2.recycle();
        return object;
    }

    @Override
    public final void zzb(PendingIntent pendingIntent) throws RemoteException {
        Parcel parcel = this.obtainAndWriteInterfaceToken();
        zzc.zza(parcel, (Parcelable)pendingIntent);
        this.transactAndReadExceptionReturnVoid(6, parcel);
    }
}


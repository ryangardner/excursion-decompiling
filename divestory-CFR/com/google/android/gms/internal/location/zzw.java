/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.internal.location.zzab;
import com.google.android.gms.internal.location.zzac;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzbm;
import com.google.android.gms.internal.location.zzq;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzw
extends zzab {
    private final /* synthetic */ LocationRequest zzck;
    private final /* synthetic */ LocationListener zzcl;
    private final /* synthetic */ Looper zzcp;

    zzw(zzq zzq2, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        this.zzck = locationRequest;
        this.zzcl = locationListener;
        this.zzcp = looper;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        anyClient = (zzaz)anyClient;
        zzac zzac2 = new zzac(this);
        ((zzaz)anyClient).zza(this.zzck, ListenerHolders.createListenerHolder(this.zzcl, zzbm.zza(this.zzcp), LocationListener.class.getSimpleName()), (zzaj)zzac2);
    }
}


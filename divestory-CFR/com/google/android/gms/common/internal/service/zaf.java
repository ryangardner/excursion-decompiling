/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.service.zac;
import com.google.android.gms.common.internal.service.zae;
import com.google.android.gms.common.internal.service.zag;
import com.google.android.gms.common.internal.service.zai;
import com.google.android.gms.common.internal.service.zaj;
import com.google.android.gms.common.internal.service.zak;

final class zaf
extends zag {
    zaf(zac zac2, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zak)((zaj)anyClient).getService()).zaa(new zae(this));
    }
}


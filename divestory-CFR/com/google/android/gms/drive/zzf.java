/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.drive;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.internal.drive.zzaw;

final class zzf
extends Api.AbstractClientBuilder<zzaw, Drive.zzb> {
    zzf() {
    }

    @Override
    public final /* synthetic */ Api.Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if ((Drive.zzb)object != null) throw new NoSuchMethodError();
        return new zzaw(context, looper, clientSettings, connectionCallbacks, onConnectionFailedListener, new Bundle());
    }
}


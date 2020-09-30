/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.signin;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.SignInClientImpl;

final class zac
extends Api.AbstractClientBuilder<SignInClientImpl, SignInOptions> {
    zac() {
    }

    @Override
    public final /* synthetic */ Api.Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        SignInOptions signInOptions = (SignInOptions)object;
        object = signInOptions;
        if (signInOptions != null) return new SignInClientImpl(context, looper, true, clientSettings, (SignInOptions)object, connectionCallbacks, onConnectionFailedListener);
        object = SignInOptions.zaa;
        return new SignInClientImpl(context, looper, true, clientSettings, (SignInOptions)object, connectionCallbacks, onConnectionFailedListener);
    }
}


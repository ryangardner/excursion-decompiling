/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zacc;
import com.google.android.gms.common.api.internal.zacd;
import com.google.android.gms.common.api.internal.zace;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zas;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zad;
import com.google.android.gms.signin.internal.zam;
import com.google.android.gms.signin.zaa;
import java.util.Set;

public final class zacb
extends zad
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private static Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zaa = zaa.zaa;
    private final Context zab;
    private final Handler zac;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zad;
    private Set<Scope> zae;
    private ClientSettings zaf;
    private com.google.android.gms.signin.zad zag;
    private zace zah;

    public zacb(Context context, Handler handler, ClientSettings clientSettings) {
        this(context, handler, clientSettings, zaa);
    }

    private zacb(Context context, Handler handler, ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder) {
        this.zab = context;
        this.zac = handler;
        this.zaf = Preconditions.checkNotNull(clientSettings, "ClientSettings must not be null");
        this.zae = clientSettings.getRequiredScopes();
        this.zad = abstractClientBuilder;
    }

    static /* synthetic */ zace zaa(zacb zacb2) {
        return zacb2.zah;
    }

    static /* synthetic */ void zaa(zacb zacb2, zam zam2) {
        zacb2.zab(zam2);
    }

    private final void zab(zam abstractSafeParcelable) {
        Object object = ((zam)abstractSafeParcelable).zaa();
        if (((ConnectionResult)object).isSuccess()) {
            object = Preconditions.checkNotNull(((zam)abstractSafeParcelable).zab());
            abstractSafeParcelable = ((zas)object).zab();
            if (!((ConnectionResult)abstractSafeParcelable).isSuccess()) {
                String string2 = String.valueOf(abstractSafeParcelable);
                object = new StringBuilder(String.valueOf(string2).length() + 48);
                ((StringBuilder)object).append("Sign-in succeeded with resolve account failure: ");
                ((StringBuilder)object).append(string2);
                Log.wtf((String)"SignInCoordinator", (String)((StringBuilder)object).toString(), (Throwable)new Exception());
                this.zah.zaa((ConnectionResult)abstractSafeParcelable);
                this.zag.disconnect();
                return;
            }
            this.zah.zaa(((zas)object).zaa(), this.zae);
        } else {
            this.zah.zaa((ConnectionResult)object);
        }
        this.zag.disconnect();
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zag.zaa(this);
    }

    @Override
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.zah.zaa(connectionResult);
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zag.disconnect();
    }

    public final void zaa() {
        com.google.android.gms.signin.zad zad2 = this.zag;
        if (zad2 == null) return;
        zad2.disconnect();
    }

    public final void zaa(zace object) {
        com.google.android.gms.signin.zad zad2 = this.zag;
        if (zad2 != null) {
            zad2.disconnect();
        }
        this.zaf.zaa(System.identityHashCode(this));
        Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder = this.zad;
        Context context = this.zab;
        zad2 = this.zac.getLooper();
        ClientSettings clientSettings = this.zaf;
        this.zag = abstractClientBuilder.buildClient(context, (Looper)zad2, clientSettings, clientSettings.zac(), this, this);
        this.zah = object;
        object = this.zae;
        if (object != null && !object.isEmpty()) {
            this.zag.zab();
            return;
        }
        this.zac.post((Runnable)new zacd(this));
    }

    @Override
    public final void zaa(zam zam2) {
        this.zac.post((Runnable)new zacc(this, zam2));
    }
}


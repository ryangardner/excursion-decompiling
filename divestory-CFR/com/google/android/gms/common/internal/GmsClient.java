/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Handler
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zag;
import com.google.android.gms.common.internal.zai;
import com.google.android.gms.common.internal.zak;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public abstract class GmsClient<T extends IInterface>
extends BaseGmsClient<T>
implements Api.Client,
zak {
    private final ClientSettings zaa;
    private final Set<Scope> zab;
    private final Account zac;

    protected GmsClient(Context context, Handler handler, int n, ClientSettings clientSettings) {
        this(context, handler, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), n, clientSettings, null, null);
    }

    @Deprecated
    private GmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailability googleApiAvailability, int n, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, handler, gmsClientSupervisor, googleApiAvailability, n, clientSettings, (ConnectionCallbacks)null, (OnConnectionFailedListener)null);
    }

    private GmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailability googleApiAvailability, int n, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, handler, gmsClientSupervisor, googleApiAvailability, n, GmsClient.zaa(null), GmsClient.zaa(null));
        this.zaa = Preconditions.checkNotNull(clientSettings);
        this.zac = clientSettings.getAccount();
        this.zab = this.zaa(clientSettings.getAllRequestedScopes());
    }

    protected GmsClient(Context context, Looper looper, int n, ClientSettings clientSettings) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), n, clientSettings, null, null);
    }

    @Deprecated
    protected GmsClient(Context context, Looper looper, int n, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, n, clientSettings, (ConnectionCallbacks)connectionCallbacks, (OnConnectionFailedListener)onConnectionFailedListener);
    }

    protected GmsClient(Context context, Looper looper, int n, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailability.getInstance(), n, clientSettings, Preconditions.checkNotNull(connectionCallbacks), Preconditions.checkNotNull(onConnectionFailedListener));
    }

    private GmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailability googleApiAvailability, int n, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, gmsClientSupervisor, googleApiAvailability, n, clientSettings, (ConnectionCallbacks)null, (OnConnectionFailedListener)null);
    }

    private GmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailability googleApiAvailability, int n, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, gmsClientSupervisor, googleApiAvailability, n, GmsClient.zaa(connectionCallbacks), GmsClient.zaa(onConnectionFailedListener), clientSettings.zab());
        this.zaa = clientSettings;
        this.zac = clientSettings.getAccount();
        this.zab = this.zaa(clientSettings.getAllRequestedScopes());
    }

    private static BaseGmsClient.BaseConnectionCallbacks zaa(ConnectionCallbacks connectionCallbacks) {
        if (connectionCallbacks != null) return new zag(connectionCallbacks);
        return null;
    }

    private static BaseGmsClient.BaseOnConnectionFailedListener zaa(OnConnectionFailedListener onConnectionFailedListener) {
        if (onConnectionFailedListener != null) return new zai(onConnectionFailedListener);
        return null;
    }

    private final Set<Scope> zaa(Set<Scope> set) {
        Set<Scope> set2 = this.validateScopes(set);
        Iterator<Scope> iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            if (!set.contains(iterator2.next())) throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
        }
        return set2;
    }

    @Override
    public final Account getAccount() {
        return this.zac;
    }

    protected final ClientSettings getClientSettings() {
        return this.zaa;
    }

    @Override
    public Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    @Override
    protected final Set<Scope> getScopes() {
        return this.zab;
    }

    @Override
    public Set<Scope> getScopesForConnectionlessNonSignIn() {
        if (!this.requiresSignIn()) return Collections.emptySet();
        return this.zab;
    }

    protected Set<Scope> validateScopes(Set<Scope> set) {
        return set;
    }
}


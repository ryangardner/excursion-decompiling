/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.api.internal.zabp;
import com.google.android.gms.common.api.internal.zabq;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;

public final class NonGmsServiceBrokerClient
implements ServiceConnection,
Api.Client {
    private static final String zaa = NonGmsServiceBrokerClient.class.getSimpleName();
    private final String zab;
    private final String zac;
    private final ComponentName zad;
    private final Context zae;
    private final ConnectionCallbacks zaf;
    private final Handler zag;
    private final OnConnectionFailedListener zah;
    private IBinder zai;
    private boolean zaj;
    private String zak;

    public NonGmsServiceBrokerClient(Context context, Looper looper, ComponentName componentName, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, null, null, componentName, connectionCallbacks, onConnectionFailedListener);
    }

    private NonGmsServiceBrokerClient(Context context, Looper looper, String string2, String string3, ComponentName componentName, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        boolean bl = false;
        this.zaj = false;
        this.zak = null;
        this.zae = context;
        this.zag = new zap(looper);
        this.zaf = connectionCallbacks;
        this.zah = onConnectionFailedListener;
        boolean bl2 = string2 != null && string3 != null;
        if (componentName != null) {
            bl = true;
        }
        if (bl2) {
            if (bl) throw new AssertionError((Object)"Must specify either package or component, but not both");
        } else if (!bl) throw new AssertionError((Object)"Must specify either package or component, but not both");
        this.zab = string2;
        this.zac = string3;
        this.zad = componentName;
    }

    public NonGmsServiceBrokerClient(Context context, Looper looper, String string2, String string3, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, string2, string3, null, connectionCallbacks, onConnectionFailedListener);
    }

    private final void zaa(String string2) {
        String string3 = String.valueOf((Object)this.zai);
        boolean bl = this.zaj;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 30 + String.valueOf(string3).length());
        stringBuilder.append(string2);
        stringBuilder.append(" binder: ");
        stringBuilder.append(string3);
        stringBuilder.append(", isConnecting: ");
        stringBuilder.append(bl);
    }

    private final void zab() {
        if (Thread.currentThread() != this.zag.getLooper().getThread()) throw new IllegalStateException("This method should only run on the NonGmsServiceBrokerClient's handler thread.");
    }

    @Override
    public final void connect(BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        block8 : {
            this.zab();
            this.zaa("Connect started.");
            if (this.isConnected()) {
                try {
                    this.disconnect("connect() called when already connected");
                }
                catch (Exception exception) {}
            }
            try {
                boolean bl;
                connectionProgressReportCallbacks = new Intent();
                if (this.zad != null) {
                    connectionProgressReportCallbacks.setComponent(this.zad);
                } else {
                    connectionProgressReportCallbacks.setPackage(this.zab).setAction(this.zac);
                }
                this.zaj = bl = this.zae.bindService((Intent)connectionProgressReportCallbacks, (ServiceConnection)this, GmsClientSupervisor.getDefaultBindFlags());
                if (bl) break block8;
            }
            catch (SecurityException securityException) {
                this.zaj = false;
                this.zai = null;
                throw securityException;
            }
            this.zai = null;
            this.zah.onConnectionFailed(new ConnectionResult(16));
        }
        this.zaa("Finished connect.");
    }

    @Override
    public final void disconnect() {
        this.zab();
        this.zaa("Disconnect called.");
        this.zae.unbindService((ServiceConnection)this);
        this.zaj = false;
        this.zai = null;
    }

    @Override
    public final void disconnect(String string2) {
        this.zab();
        this.zak = string2;
        this.disconnect();
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    @Override
    public final Feature[] getAvailableFeatures() {
        return new Feature[0];
    }

    public final IBinder getBinder() {
        this.zab();
        return this.zai;
    }

    @Override
    public final String getEndpointPackageName() {
        String string2 = this.zab;
        if (string2 != null) {
            return string2;
        }
        Preconditions.checkNotNull(this.zad);
        return this.zad.getPackageName();
    }

    @Override
    public final String getLastDisconnectMessage() {
        return this.zak;
    }

    @Override
    public final int getMinApkVersion() {
        return 0;
    }

    @Override
    public final void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set) {
    }

    @Override
    public final Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    @Override
    public final Set<Scope> getScopesForConnectionlessNonSignIn() {
        return Collections.emptySet();
    }

    @Override
    public final IBinder getServiceBrokerBinder() {
        return null;
    }

    @Override
    public final Intent getSignInIntent() {
        return new Intent();
    }

    @Override
    public final boolean isConnected() {
        this.zab();
        if (this.zai == null) return false;
        return true;
    }

    @Override
    public final boolean isConnecting() {
        this.zab();
        return this.zaj;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zag.post((Runnable)new zabp(this, iBinder));
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.zag.post((Runnable)new zabq(this));
    }

    @Override
    public final void onUserSignOut(BaseGmsClient.SignOutCallbacks signOutCallbacks) {
    }

    @Override
    public final boolean providesSignIn() {
        return false;
    }

    @Override
    public final boolean requiresAccount() {
        return false;
    }

    @Override
    public final boolean requiresGooglePlayServices() {
        return false;
    }

    @Override
    public final boolean requiresSignIn() {
        return false;
    }

    final /* synthetic */ void zaa() {
        this.zaj = false;
        this.zai = null;
        this.zaa("Disconnected.");
        this.zaf.onConnectionSuspended(1);
    }

    final /* synthetic */ void zaa(IBinder iBinder) {
        this.zaj = false;
        this.zai = iBinder;
        this.zaa("Connected.");
        this.zaf.onConnected(new Bundle());
    }
}


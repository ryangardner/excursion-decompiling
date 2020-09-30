/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 *  org.checkerframework.checker.initialization.qual.NotOnlyInitialized
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zak;
import com.google.android.gms.internal.base.zap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zah
implements Handler.Callback {
    @NotOnlyInitialized
    private final zak zaa;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zab = new ArrayList();
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zac = new ArrayList();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zad = new ArrayList();
    private volatile boolean zae = false;
    private final AtomicInteger zaf = new AtomicInteger(0);
    private boolean zag = false;
    private final Handler zah;
    private final Object zai = new Object();

    public zah(Looper looper, zak zak2) {
        this.zaa = zak2;
        this.zah = new zap(looper, this);
    }

    public final boolean handleMessage(Message object) {
        if (((Message)object).what != 1) {
            int n = ((Message)object).what;
            object = new StringBuilder(45);
            ((StringBuilder)object).append("Don't know how to handle message: ");
            ((StringBuilder)object).append(n);
            Log.wtf((String)"GmsClientEvents", (String)((StringBuilder)object).toString(), (Throwable)new Exception());
            return false;
        }
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)((Message)object).obj;
        object = this.zai;
        synchronized (object) {
            if (!this.zae) return true;
            if (!this.zaa.isConnected()) return true;
            if (!this.zab.contains(connectionCallbacks)) return true;
            connectionCallbacks.onConnected(this.zaa.getConnectionHint());
            return true;
        }
    }

    public final void zaa() {
        this.zae = false;
        this.zaf.incrementAndGet();
    }

    public final void zaa(int n) {
        Preconditions.checkHandlerThread(this.zah, "onUnintentionalDisconnection must only be called on the Handler thread");
        this.zah.removeMessages(1);
        Object object = this.zai;
        synchronized (object) {
            this.zag = true;
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zab);
            int n2 = this.zaf.get();
            int n3 = arrayList.size();
            int n4 = 0;
            while (n4 < n3) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = arrayList.get(n4);
                int n5 = n4 + 1;
                if (!this.zae || this.zaf.get() != n2) break;
                n4 = n5;
                if (!this.zab.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnectionSuspended(n);
                n4 = n5;
            }
            this.zac.clear();
            this.zag = false;
            return;
        }
    }

    public final void zaa(Bundle bundle) {
        Preconditions.checkHandlerThread(this.zah, "onConnectionSuccess must only be called on the Handler thread");
        Object object = this.zai;
        synchronized (object) {
            boolean bl = this.zag;
            boolean bl2 = true;
            bl = !bl;
            Preconditions.checkState(bl);
            this.zah.removeMessages(1);
            this.zag = true;
            bl = this.zac.size() == 0 ? bl2 : false;
            Preconditions.checkState(bl);
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zab);
            int n = this.zaf.get();
            int n2 = arrayList.size();
            int n3 = 0;
            while (n3 < n2) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = arrayList.get(n3);
                int n4 = n3 + 1;
                if (!this.zae || !this.zaa.isConnected() || this.zaf.get() != n) break;
                n3 = n4;
                if (this.zac.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnected(bundle);
                n3 = n4;
            }
            this.zac.clear();
            this.zag = false;
            return;
        }
    }

    public final void zaa(ConnectionResult connectionResult) {
        Preconditions.checkHandlerThread(this.zah, "onConnectionFailure must only be called on the Handler thread");
        this.zah.removeMessages(1);
        Object object = this.zai;
        synchronized (object) {
            ArrayList<GoogleApiClient.OnConnectionFailedListener> arrayList = new ArrayList<GoogleApiClient.OnConnectionFailedListener>(this.zad);
            int n = this.zaf.get();
            int n2 = arrayList.size();
            int n3 = 0;
            while (n3 < n2) {
                GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = arrayList.get(n3);
                int n4 = n3 + 1;
                if (!this.zae) return;
                if (this.zaf.get() != n) {
                    return;
                }
                n3 = n4;
                if (!this.zad.contains(onConnectionFailedListener)) continue;
                onConnectionFailedListener.onConnectionFailed(connectionResult);
                n3 = n4;
            }
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void zaa(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        Preconditions.checkNotNull(connectionCallbacks);
        Object object = this.zai;
        synchronized (object) {
            if (this.zab.contains(connectionCallbacks)) {
                String string2 = String.valueOf(connectionCallbacks);
                int n = String.valueOf(string2).length();
                StringBuilder stringBuilder = new StringBuilder(n + 62);
                stringBuilder.append("registerConnectionCallbacks(): listener ");
                stringBuilder.append(string2);
                stringBuilder.append(" is already registered");
                Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
            } else {
                this.zab.add(connectionCallbacks);
            }
        }
        if (!this.zaa.isConnected()) return;
        object = this.zah;
        object.sendMessage(object.obtainMessage(1, (Object)connectionCallbacks));
    }

    public final void zaa(GoogleApiClient.OnConnectionFailedListener object) {
        Preconditions.checkNotNull(object);
        Object object2 = this.zai;
        synchronized (object2) {
            if (this.zad.contains(object)) {
                String string2 = String.valueOf(object);
                int n = String.valueOf(string2).length();
                object = new StringBuilder(n + 67);
                ((StringBuilder)object).append("registerConnectionFailedListener(): listener ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" is already registered");
                Log.w((String)"GmsClientEvents", (String)((StringBuilder)object).toString());
            } else {
                this.zad.add((GoogleApiClient.OnConnectionFailedListener)object);
            }
            return;
        }
    }

    public final void zab() {
        this.zae = true;
    }

    public final boolean zab(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        Preconditions.checkNotNull(connectionCallbacks);
        Object object = this.zai;
        synchronized (object) {
            return this.zab.contains(connectionCallbacks);
        }
    }

    public final boolean zab(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(onConnectionFailedListener);
        Object object = this.zai;
        synchronized (object) {
            return this.zad.contains(onConnectionFailedListener);
        }
    }

    public final void zac(GoogleApiClient.ConnectionCallbacks object) {
        Preconditions.checkNotNull(object);
        Object object2 = this.zai;
        synchronized (object2) {
            if (!this.zab.remove(object)) {
                object = String.valueOf(object);
                int n = String.valueOf(object).length();
                StringBuilder stringBuilder = new StringBuilder(n + 52);
                stringBuilder.append("unregisterConnectionCallbacks(): listener ");
                stringBuilder.append((String)object);
                stringBuilder.append(" not found");
                Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
            } else {
                if (!this.zag) return;
                this.zac.add((GoogleApiClient.ConnectionCallbacks)object);
            }
            return;
        }
    }

    public final void zac(GoogleApiClient.OnConnectionFailedListener object) {
        Preconditions.checkNotNull(object);
        Object object2 = this.zai;
        synchronized (object2) {
            if (this.zad.remove(object)) return;
            object = String.valueOf(object);
            int n = String.valueOf(object).length();
            StringBuilder stringBuilder = new StringBuilder(n + 57);
            stringBuilder.append("unregisterConnectionFailedListener(): listener ");
            stringBuilder.append((String)object);
            stringBuilder.append(" not found");
            Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
            return;
        }
    }
}


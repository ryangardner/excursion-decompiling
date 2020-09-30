/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseArray
 */
package com.google.android.gms.common.api.internal;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zak;
import com.google.android.gms.common.api.internal.zam;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

public class zai
extends zak {
    private final SparseArray<zaa> zad = new SparseArray();

    private zai(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.mLifecycleFragment.addCallback("AutoManageHelper", this);
    }

    public static zai zaa(LifecycleActivity object) {
        LifecycleFragment lifecycleFragment = zai.getFragment((LifecycleActivity)object);
        object = lifecycleFragment.getCallbackOrNull("AutoManageHelper", zai.class);
        if (object == null) return new zai(lifecycleFragment);
        return object;
    }

    private final zaa zab(int n) {
        if (this.zad.size() <= n) {
            return null;
        }
        SparseArray<zaa> sparseArray = this.zad;
        return (zaa)sparseArray.get(sparseArray.keyAt(n));
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        int n = 0;
        while (n < this.zad.size()) {
            zaa zaa2 = this.zab(n);
            if (zaa2 != null) {
                printWriter.append(string2).append("GoogleApiClient #").print(zaa2.zaa);
                printWriter.println(":");
                zaa2.zab.dump(String.valueOf(string2).concat("  "), fileDescriptor, printWriter, arrstring);
            }
            ++n;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean bl = this.zaa;
        String string2 = String.valueOf(this.zad);
        Object object = new StringBuilder(String.valueOf(string2).length() + 14);
        ((StringBuilder)object).append("onStart ");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(string2);
        Log.d((String)"AutoManageHelper", (String)((StringBuilder)object).toString());
        if (this.zab.get() != null) return;
        int n = 0;
        while (n < this.zad.size()) {
            object = this.zab(n);
            if (object != null) {
                ((zaa)object).zab.connect();
            }
            ++n;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        int n = 0;
        while (n < this.zad.size()) {
            zaa zaa2 = this.zab(n);
            if (zaa2 != null) {
                zaa2.zab.disconnect();
            }
            ++n;
        }
    }

    @Override
    protected final void zaa() {
        int n = 0;
        while (n < this.zad.size()) {
            zaa zaa2 = this.zab(n);
            if (zaa2 != null) {
                zaa2.zab.connect();
            }
            ++n;
        }
    }

    public final void zaa(int n) {
        zaa zaa2 = (zaa)this.zad.get(n);
        this.zad.remove(n);
        if (zaa2 == null) return;
        zaa2.zab.unregisterConnectionFailedListener(zaa2);
        zaa2.zab.disconnect();
    }

    public final void zaa(int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener object) {
        Preconditions.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean bl = this.zad.indexOfKey(n) < 0;
        Object object2 = new StringBuilder(54);
        ((StringBuilder)object2).append("Already managing a GoogleApiClient with id ");
        ((StringBuilder)object2).append(n);
        Preconditions.checkState(bl, ((StringBuilder)object2).toString());
        object2 = (zam)this.zab.get();
        bl = this.zaa;
        String string2 = String.valueOf(object2);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 49);
        stringBuilder.append("starting AutoManage for client ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(bl);
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        Log.d((String)"AutoManageHelper", (String)stringBuilder.toString());
        object = new zaa(n, googleApiClient, (GoogleApiClient.OnConnectionFailedListener)object);
        googleApiClient.registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)object);
        this.zad.put(n, object);
        if (!this.zaa) return;
        if (object2 != null) return;
        object2 = String.valueOf(googleApiClient);
        object = new StringBuilder(String.valueOf(object2).length() + 11);
        ((StringBuilder)object).append("connecting ");
        ((StringBuilder)object).append((String)object2);
        Log.d((String)"AutoManageHelper", (String)((StringBuilder)object).toString());
        googleApiClient.connect();
    }

    @Override
    protected final void zaa(ConnectionResult connectionResult, int n) {
        Log.w((String)"AutoManageHelper", (String)"Unresolved error while connecting client. Stopping auto-manage.");
        if (n < 0) {
            Log.wtf((String)"AutoManageHelper", (String)"AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", (Throwable)new Exception());
            return;
        }
        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (zaa)this.zad.get(n);
        if (onConnectionFailedListener == null) return;
        this.zaa(n);
        onConnectionFailedListener = ((zaa)onConnectionFailedListener).zac;
        if (onConnectionFailedListener == null) return;
        onConnectionFailedListener.onConnectionFailed(connectionResult);
    }

    private final class zaa
    implements GoogleApiClient.OnConnectionFailedListener {
        public final int zaa;
        public final GoogleApiClient zab;
        public final GoogleApiClient.OnConnectionFailedListener zac;

        public zaa(int n, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zaa = n;
            this.zab = googleApiClient;
            this.zac = onConnectionFailedListener;
        }

        @Override
        public final void onConnectionFailed(ConnectionResult connectionResult) {
            String string2 = String.valueOf(connectionResult);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 27);
            stringBuilder.append("beginFailureResolution for ");
            stringBuilder.append(string2);
            Log.d((String)"AutoManageHelper", (String)stringBuilder.toString());
            zai.this.zab(connectionResult, this.zaa);
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 *  android.os.Message
 *  org.checkerframework.checker.initialization.qual.NotOnlyInitialized
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaac;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaaq;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaaz;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.common.api.internal.zabm;
import com.google.android.gms.common.api.internal.zabn;
import com.google.android.gms.common.api.internal.zap;
import com.google.android.gms.common.api.internal.zar;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zad;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zaax
implements zabn,
zar {
    final Map<Api.AnyClientKey<?>, Api.Client> zaa;
    final Map<Api.AnyClientKey<?>, ConnectionResult> zab = new HashMap();
    int zac;
    final zaap zad;
    final zabm zae;
    private final Lock zaf;
    private final Condition zag;
    private final Context zah;
    private final GoogleApiAvailabilityLight zai;
    private final zaaz zaj;
    private final ClientSettings zak;
    private final Map<Api<?>, Boolean> zal;
    private final Api.AbstractClientBuilder<? extends zad, SignInOptions> zam;
    @NotOnlyInitialized
    private volatile zaay zan;
    private ConnectionResult zao = null;

    public zaax(Context object, zaap zaap2, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends zad, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList, zabm zabm2) {
        this.zah = object;
        this.zaf = lock;
        this.zai = googleApiAvailabilityLight;
        this.zaa = map;
        this.zak = clientSettings;
        this.zal = map2;
        this.zam = abstractClientBuilder;
        this.zad = zaap2;
        this.zae = zabm2;
        object = arrayList;
        int n = ((ArrayList)object).size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.zaj = new zaaz(this, looper);
                this.zag = lock.newCondition();
                this.zan = new zaaq(this);
                return;
            }
            zaap2 = ((ArrayList)object).get(n2);
            ++n2;
            ((zap)((Object)zaap2)).zaa(this);
        } while (true);
    }

    static /* synthetic */ Lock zaa(zaax zaax2) {
        return zaax2.zaf;
    }

    static /* synthetic */ zaay zab(zaax zaax2) {
        return zaax2.zan;
    }

    @Override
    public final void onConnected(Bundle bundle) {
        this.zaf.lock();
        try {
            this.zan.zaa(bundle);
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final void onConnectionSuspended(int n) {
        this.zaf.lock();
        try {
            this.zan.zaa(n);
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public final ConnectionResult zaa(long var1_1, TimeUnit var3_2) {
        this.zaa();
        var1_1 = var3_2.toNanos(var1_1);
        while (this.zae()) {
            if (var1_1 > 0L) ** GOTO lbl8
            try {
                this.zac();
                return new ConnectionResult(14, null);
lbl8: // 1 sources:
                var1_1 = this.zag.awaitNanos(var1_1);
            }
            catch (InterruptedException var3_3) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.zad()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        var3_4 = this.zao;
        if (var3_4 == null) return new ConnectionResult(13, null);
        return var3_4;
    }

    @Override
    public final ConnectionResult zaa(Api<?> object) {
        if (!this.zaa.containsKey(object = ((Api)object).zac())) return null;
        if (this.zaa.get(object).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        if (!this.zab.containsKey(object)) return null;
        return this.zab.get(object);
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T t) {
        ((BasePendingResult)t).zab();
        return this.zan.zaa(t);
    }

    @Override
    public final void zaa() {
        this.zan.zac();
    }

    final void zaa(ConnectionResult object) {
        this.zaf.lock();
        try {
            this.zao = object;
            this.zan = object = new zaaq(this);
            this.zan.zaa();
            this.zag.signalAll();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean bl) {
        this.zaf.lock();
        try {
            this.zan.zaa(connectionResult, api, bl);
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    final void zaa(zaba zaba2) {
        zaba2 = this.zaj.obtainMessage(1, (Object)zaba2);
        this.zaj.sendMessage((Message)zaba2);
    }

    final void zaa(RuntimeException runtimeException) {
        runtimeException = this.zaj.obtainMessage(2, (Object)runtimeException);
        this.zaj.sendMessage((Message)runtimeException);
    }

    @Override
    public final void zaa(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        String string3 = String.valueOf(string2).concat("  ");
        printWriter.append(string2).append("mState=").println(this.zan);
        Iterator<Api<?>> iterator2 = this.zal.keySet().iterator();
        while (iterator2.hasNext()) {
            Api<?> api = iterator2.next();
            printWriter.append(string2).append(api.zad()).println(":");
            Preconditions.checkNotNull(this.zaa.get(api.zac())).dump(string3, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final boolean zaa(SignInConnectionListener signInConnectionListener) {
        return false;
    }

    @Override
    public final ConnectionResult zab() {
        this.zaa();
        while (this.zae()) {
            try {
                this.zag.await();
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.zad()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zao;
        if (connectionResult == null) return new ConnectionResult(13, null);
        return connectionResult;
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T t) {
        ((BasePendingResult)t).zab();
        return this.zan.zab(t);
    }

    @Override
    public final void zac() {
        if (!this.zan.zab()) return;
        this.zab.clear();
    }

    @Override
    public final boolean zad() {
        return this.zan instanceof zaac;
    }

    @Override
    public final boolean zae() {
        return this.zan instanceof zaad;
    }

    @Override
    public final void zaf() {
        if (!this.zad()) return;
        ((zaac)this.zan).zad();
    }

    @Override
    public final void zag() {
    }

    final void zah() {
        this.zaf.lock();
        try {
            zaad zaad2 = new zaad(this, this.zak, this.zal, this.zai, this.zam, this.zaf, this.zah);
            this.zan = zaad2;
            this.zan.zaa();
            this.zag.signalAll();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    final void zai() {
        this.zaf.lock();
        try {
            this.zad.zab();
            zaac zaac2 = new zaac(this);
            this.zan = zaac2;
            this.zan.zaa();
            this.zag.signalAll();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }
}


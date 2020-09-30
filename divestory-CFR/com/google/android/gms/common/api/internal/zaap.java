/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.api.internal.zaar;
import com.google.android.gms.common.api.internal.zaas;
import com.google.android.gms.common.api.internal.zaat;
import com.google.android.gms.common.api.internal.zaau;
import com.google.android.gms.common.api.internal.zaav;
import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zabj;
import com.google.android.gms.common.api.internal.zabl;
import com.google.android.gms.common.api.internal.zabm;
import com.google.android.gms.common.api.internal.zabn;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.api.internal.zacl;
import com.google.android.gms.common.api.internal.zacn;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.api.internal.zap;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.internal.service.zad;
import com.google.android.gms.common.internal.service.zaj;
import com.google.android.gms.common.internal.zah;
import com.google.android.gms.common.internal.zak;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zaap
extends GoogleApiClient
implements zabm {
    final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zaa = new LinkedList();
    final Map<Api.AnyClientKey<?>, Api.Client> zab;
    Set<Scope> zac;
    Set<zack> zad;
    final zacl zae;
    private final Lock zaf;
    private final zah zag;
    private zabn zah = null;
    private final int zai;
    private final Context zaj;
    private final Looper zak;
    private volatile boolean zal;
    private long zam;
    private long zan;
    private final zaaw zao;
    private final GoogleApiAvailability zap;
    private zabj zaq;
    private final ClientSettings zar;
    private final Map<Api<?>, Boolean> zas;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zat;
    private final ListenerHolders zau;
    private final ArrayList<zap> zav;
    private Integer zaw;
    private final zak zax;

    /*
     * WARNING - void declaration
     */
    public zaap(Context object, Lock object22, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> abstractClientBuilder, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.AnyClientKey<?>, Api.Client> map2, int n, int n2, ArrayList<zap> arrayList) {
        void var11_15;
        void var10_14;
        void var7_11;
        void var8_12;
        void var13_17;
        void var5_9;
        void var9_13;
        void var3_7;
        long l = ClientLibraryUtils.isPackageSide() ? 10000L : 120000L;
        this.zam = l;
        this.zan = 5000L;
        this.zac = new HashSet<Scope>();
        this.zau = new ListenerHolders();
        this.zaw = null;
        this.zad = null;
        this.zax = new zaas(this);
        this.zaj = object;
        this.zaf = object22;
        this.zag = new zah((Looper)var3_7, this.zax);
        this.zak = var3_7;
        this.zao = new zaaw(this, (Looper)var3_7);
        this.zap = var5_9;
        this.zai = var11_15;
        if (var11_15 >= 0) {
            void var12_16;
            this.zaw = (int)var12_16;
        }
        this.zas = var7_11;
        this.zab = var10_14;
        this.zav = var13_17;
        this.zae = new zacl();
        for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : var8_12) {
            this.zag.zaa(connectionCallbacks);
        }
        object = var9_13.iterator();
        do {
            if (!object.hasNext()) {
                void var6_10;
                void var4_8;
                this.zar = var4_8;
                this.zat = var6_10;
                return;
            }
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)object.next();
            this.zag.zaa(onConnectionFailedListener);
        } while (true);
    }

    public static int zaa(Iterable<Api.Client> object, boolean bl) {
        object = object.iterator();
        boolean bl2 = false;
        boolean bl3 = false;
        do {
            if (!object.hasNext()) {
                if (!bl2) return 3;
                if (!bl3) return 1;
                if (!bl) return 1;
                return 2;
            }
            Api.Client client = (Api.Client)object.next();
            boolean bl4 = bl2;
            if (client.requiresSignIn()) {
                bl4 = true;
            }
            bl2 = bl4;
            if (!client.providesSignIn()) continue;
            bl3 = true;
            bl2 = bl4;
        } while (true);
    }

    private final void zaa(int n) {
        int n2;
        Object object = this.zaw;
        if (object == null) {
            this.zaw = n;
        } else if ((Integer)object != n) {
            String string2 = zaap.zab(n);
            object = zaap.zab(this.zaw);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 51 + String.valueOf(object).length());
            stringBuilder.append("Cannot use sign-in mode: ");
            stringBuilder.append(string2);
            stringBuilder.append(". Mode was already set to ");
            stringBuilder.append((String)object);
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.zah != null) {
            return;
        }
        object = this.zab.values().iterator();
        int n3 = 0;
        n = 0;
        while (object.hasNext()) {
            Api.Client client = (Api.Client)object.next();
            n2 = n3;
            if (client.requiresSignIn()) {
                n2 = 1;
            }
            n3 = n2;
            if (!client.providesSignIn()) continue;
            n = 1;
            n3 = n2;
        }
        n2 = this.zaw;
        if (n2 != 1) {
            if (n2 == 2 && n3 != 0) {
                this.zah = zaq.zaa(this.zaj, this, this.zaf, this.zak, this.zap, this.zab, this.zar, this.zas, this.zat, this.zav);
                return;
            }
        } else {
            if (n3 == 0) throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            if (n != 0) throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
        }
        this.zah = new zaax(this.zaj, this, this.zaf, this.zak, this.zap, this.zab, this.zar, this.zas, this.zat, this.zav, this);
    }

    private final void zaa(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean bl) {
        Common.zaa.zaa(googleApiClient).setResultCallback(new zaat(this, statusPendingResult, bl, googleApiClient));
    }

    static /* synthetic */ void zaa(zaap zaap2) {
        zaap2.zae();
    }

    static /* synthetic */ void zaa(zaap zaap2, GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean bl) {
        zaap2.zaa(googleApiClient, statusPendingResult, true);
    }

    private static String zab(int n) {
        if (n == 1) return "SIGN_IN_MODE_REQUIRED";
        if (n == 2) return "SIGN_IN_MODE_OPTIONAL";
        if (n == 3) return "SIGN_IN_MODE_NONE";
        return "UNKNOWN";
    }

    static /* synthetic */ void zab(zaap zaap2) {
        zaap2.zaf();
    }

    static /* synthetic */ Context zac(zaap zaap2) {
        return zaap2.zaj;
    }

    private final void zad() {
        this.zag.zab();
        Preconditions.checkNotNull(this.zah).zaa();
    }

    private final void zae() {
        this.zaf.lock();
        try {
            if (!this.zal) return;
            this.zad();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    private final void zaf() {
        this.zaf.lock();
        try {
            if (!this.zab()) return;
            this.zad();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    private final boolean zag() {
        this.zaf.lock();
        Set<zack> set = this.zad;
        if (set == null) {
            this.zaf.unlock();
            return false;
        }
        boolean bl = this.zad.isEmpty();
        return bl ^ true;
    }

    @Override
    public final ConnectionResult blockingConnect() {
        Looper looper = Looper.myLooper();
        Object object = Looper.getMainLooper();
        boolean bl = true;
        boolean bl2 = looper != object;
        Preconditions.checkState(bl2, "blockingConnect must not be called on the UI thread");
        this.zaf.lock();
        try {
            block9 : {
                block7 : {
                    block8 : {
                        block6 : {
                            if (this.zai < 0) break block6;
                            bl2 = this.zaw != null ? bl : false;
                            Preconditions.checkState(bl2, "Sign-in mode should have been set explicitly by auto-manage.");
                            break block7;
                        }
                        if (this.zaw != null) break block8;
                        this.zaw = zaap.zaa(this.zab.values(), false);
                        break block7;
                    }
                    if (this.zaw == 2) break block9;
                }
                this.zaa(Preconditions.checkNotNull(this.zaw));
                this.zag.zab();
                object = Preconditions.checkNotNull(this.zah).zab();
                return object;
            }
            object = new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            throw object;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final ConnectionResult blockingConnect(long l, TimeUnit object) {
        boolean bl = Looper.myLooper() != Looper.getMainLooper();
        Preconditions.checkState(bl, "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(object, "TimeUnit must not be null");
        this.zaf.lock();
        try {
            block8 : {
                block7 : {
                    block6 : {
                        if (this.zaw != null) break block6;
                        this.zaw = zaap.zaa(this.zab.values(), false);
                        break block7;
                    }
                    if (this.zaw == 2) break block8;
                }
                this.zaa(Preconditions.checkNotNull(this.zaw));
                this.zag.zab();
                object = Preconditions.checkNotNull(this.zah).zaa(l, (TimeUnit)((Object)object));
                return object;
            }
            object = new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            throw object;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        Preconditions.checkState(((GoogleApiClient)this).isConnected(), "GoogleApiClient is not connected yet.");
        Object object = this.zaw;
        boolean bl = object == null || (Integer)object != 2;
        Preconditions.checkState(bl, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        object = new StatusPendingResult(this);
        if (this.zab.containsKey(Common.CLIENT_KEY)) {
            this.zaa(this, (StatusPendingResult)object, false);
            return object;
        }
        AtomicReference<Object> atomicReference = new AtomicReference<Object>();
        Object object2 = new zaar(this, atomicReference, (StatusPendingResult)object);
        zaau zaau2 = new zaau(this, (StatusPendingResult)object);
        object2 = new GoogleApiClient.Builder(this.zaj).addApi(Common.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object2).addOnConnectionFailedListener(zaau2).setHandler(this.zao).build();
        atomicReference.set(object2);
        ((GoogleApiClient)object2).connect();
        return object;
    }

    @Override
    public final void connect() {
        this.zaf.lock();
        try {
            block11 : {
                block9 : {
                    block10 : {
                        block8 : {
                            int n = this.zai;
                            boolean bl = false;
                            if (n < 0) break block8;
                            if (this.zaw != null) {
                                bl = true;
                            }
                            Preconditions.checkState(bl, "Sign-in mode should have been set explicitly by auto-manage.");
                            break block9;
                        }
                        if (this.zaw != null) break block10;
                        this.zaw = zaap.zaa(this.zab.values(), false);
                        break block9;
                    }
                    if (this.zaw == 2) break block11;
                }
                ((GoogleApiClient)this).connect(Preconditions.checkNotNull(this.zaw));
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            throw illegalStateException;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final void connect(int n) {
        boolean bl;
        this.zaf.lock();
        boolean bl2 = bl = true;
        if (n != 3) {
            bl2 = bl;
            if (n != 1) {
                bl2 = n == 2 ? bl : false;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder(33);
            stringBuilder.append("Illegal sign-in mode: ");
            stringBuilder.append(n);
            Preconditions.checkArgument(bl2, stringBuilder.toString());
            this.zaa(n);
            this.zad();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final void disconnect() {
        this.zaf.lock();
        this.zae.zaa();
        if (this.zah != null) {
            this.zah.zac();
        }
        this.zau.zaa();
        for (BaseImplementation.ApiMethodImpl apiMethodImpl : this.zaa) {
            apiMethodImpl.zaa(null);
            ((PendingResult)apiMethodImpl).cancel();
        }
        this.zaa.clear();
        zabn zabn2 = this.zah;
        if (zabn2 == null) {
            this.zaf.unlock();
            return;
        }
        this.zab();
        this.zag.zaa();
        return;
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string2).append("mContext=").println((Object)this.zaj);
        printWriter.append(string2).append("mResuming=").print(this.zal);
        printWriter.append(" mWorkQueue.size()=").print(this.zaa.size());
        Object object = this.zae;
        printWriter.append(" mUnconsumedApiCalls.size()=").println(((zacl)object).zab.size());
        object = this.zah;
        if (object == null) return;
        object.zaa(string2, fileDescriptor, printWriter, arrstring);
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T t) {
        Object object = ((BaseImplementation.ApiMethodImpl)t).getApi();
        boolean bl = this.zab.containsKey(((BaseImplementation.ApiMethodImpl)t).getClientKey());
        object = object != null ? ((Api)object).zad() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(object).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append((String)object);
        stringBuilder.append(" required for this call.");
        Preconditions.checkArgument(bl, stringBuilder.toString());
        this.zaf.lock();
        try {
            if (this.zah == null) {
                this.zaa.add(t);
                return t;
            }
            t = this.zah.zaa(t);
            return t;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T object) {
        Object object2 = ((BaseImplementation.ApiMethodImpl)object).getApi();
        boolean bl = this.zab.containsKey(((BaseImplementation.ApiMethodImpl)object).getClientKey());
        object2 = object2 != null ? ((Api)object2).zad() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(object2).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append((String)object2);
        stringBuilder.append(" required for this call.");
        Preconditions.checkArgument(bl, stringBuilder.toString());
        this.zaf.lock();
        try {
            object2 = this.zah;
            if (object2 != null) {
                if (!this.zal) {
                    object = object2.zab(object);
                    return object;
                }
                this.zaa.add((BaseImplementation.ApiMethodImpl<?, ?>)object);
                while (!this.zaa.isEmpty()) {
                    object2 = this.zaa.remove();
                    this.zae.zaa((BasePendingResult<? extends Result>)object2);
                    ((BaseImplementation.ApiMethodImpl)object2).setFailedResult(Status.RESULT_INTERNAL_ERROR);
                }
                return object;
            }
            object = new Object("GoogleApiClient is not connected yet.");
            throw object;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final <C extends Api.Client> C getClient(Api.AnyClientKey<C> object) {
        object = this.zab.get(object);
        Preconditions.checkNotNull(object, "Appropriate Api was not requested.");
        return (C)object;
    }

    @Override
    public final ConnectionResult getConnectionResult(Api<?> object) {
        this.zaf.lock();
        try {
            if (!((GoogleApiClient)this).isConnected() && !this.zal) {
                object = new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
                throw object;
            }
            if (this.zab.containsKey(((Api)object).zac())) {
                Object object2 = Preconditions.checkNotNull(this.zah).zaa(object);
                if (object2 != null) return object2;
                if (this.zal) {
                    object = ConnectionResult.RESULT_SUCCESS;
                    return object;
                }
                Log.w((String)"GoogleApiClientImpl", (String)this.zac());
                object = String.valueOf(((Api)object).zad()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map");
                object2 = new Exception();
                Log.wtf((String)"GoogleApiClientImpl", (String)object, (Throwable)object2);
                object = new ConnectionResult(8, null);
                return object;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(String.valueOf(((Api)object).zad()).concat(" was never registered with GoogleApiClient"));
            throw illegalArgumentException;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final Context getContext() {
        return this.zaj;
    }

    @Override
    public final Looper getLooper() {
        return this.zak;
    }

    @Override
    public final boolean hasApi(Api<?> api) {
        return this.zab.containsKey(api.zac());
    }

    @Override
    public final boolean hasConnectedApi(Api<?> object) {
        if (!((GoogleApiClient)this).isConnected()) {
            return false;
        }
        if ((object = this.zab.get(((Api)object).zac())) == null) return false;
        if (!object.isConnected()) return false;
        return true;
    }

    @Override
    public final boolean isConnected() {
        zabn zabn2 = this.zah;
        if (zabn2 == null) return false;
        if (!zabn2.zad()) return false;
        return true;
    }

    @Override
    public final boolean isConnecting() {
        zabn zabn2 = this.zah;
        if (zabn2 == null) return false;
        if (!zabn2.zae()) return false;
        return true;
    }

    @Override
    public final boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zag.zab(connectionCallbacks);
    }

    @Override
    public final boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zag.zab(onConnectionFailedListener);
    }

    @Override
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        zabn zabn2 = this.zah;
        if (zabn2 == null) return false;
        if (!zabn2.zaa(signInConnectionListener)) return false;
        return true;
    }

    @Override
    public final void maybeSignOut() {
        zabn zabn2 = this.zah;
        if (zabn2 == null) return;
        zabn2.zag();
    }

    @Override
    public final void reconnect() {
        ((GoogleApiClient)this).disconnect();
        ((GoogleApiClient)this).connect();
    }

    @Override
    public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zag.zaa(connectionCallbacks);
    }

    @Override
    public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zag.zaa(onConnectionFailedListener);
    }

    @Override
    public final <L> ListenerHolder<L> registerListener(L object) {
        this.zaf.lock();
        try {
            object = this.zau.zaa(object, this.zak, "NO_TYPE");
            return object;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final void stopAutoManage(FragmentActivity object) {
        object = new LifecycleActivity((Activity)object);
        if (this.zai < 0) throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
        zai.zaa((LifecycleActivity)object).zaa(this.zai);
    }

    @Override
    public final void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zag.zac(connectionCallbacks);
    }

    @Override
    public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zag.zac(onConnectionFailedListener);
    }

    @Override
    public final void zaa(int n, boolean bl) {
        Object object;
        if (n == 1 && !bl && !this.zal) {
            this.zal = true;
            if (this.zaq == null && !ClientLibraryUtils.isPackageSide()) {
                try {
                    GoogleApiAvailability googleApiAvailability = this.zap;
                    Context context = this.zaj.getApplicationContext();
                    object = new zaav(this);
                    this.zaq = googleApiAvailability.zaa(context, (zabl)object);
                }
                catch (SecurityException securityException) {}
            }
            object = this.zao;
            object.sendMessageDelayed(object.obtainMessage(1), this.zam);
            object = this.zao;
            object.sendMessageDelayed(object.obtainMessage(2), this.zan);
        }
        object = this.zae.zab;
        int n2 = 0;
        object = object.toArray(new BasePendingResult[0]);
        int n3 = ((BasePendingResult[])object).length;
        do {
            if (n2 >= n3) {
                this.zag.zaa(n);
                this.zag.zaa();
                if (n != 2) return;
                this.zad();
                return;
            }
            object[n2].forceFailureUnlessReady(zacl.zaa);
            ++n2;
        } while (true);
    }

    @Override
    public final void zaa(Bundle bundle) {
        do {
            if (this.zaa.isEmpty()) {
                this.zag.zaa(bundle);
                return;
            }
            ((GoogleApiClient)this).execute(this.zaa.remove());
        } while (true);
    }

    @Override
    public final void zaa(ConnectionResult connectionResult) {
        if (!this.zap.isPlayServicesPossiblyUpdating(this.zaj, connectionResult.getErrorCode())) {
            this.zab();
        }
        if (this.zal) return;
        this.zag.zaa(connectionResult);
        this.zag.zaa();
    }

    @Override
    public final void zaa(zack zack2) {
        this.zaf.lock();
        try {
            if (this.zad == null) {
                HashSet<zack> hashSet = new HashSet<zack>();
                this.zad = hashSet;
            }
            this.zad.add(zack2);
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    @Override
    public final void zab(zack object) {
        this.zaf.lock();
        try {
            Set<zack> set = this.zad;
            if (set == null) {
                object = new Exception();
                Log.wtf((String)"GoogleApiClientImpl", (String)"Attempted to remove pending transform when no transforms are registered.", (Throwable)object);
                return;
            }
            if (!this.zad.remove(object)) {
                object = new Exception();
                Log.wtf((String)"GoogleApiClientImpl", (String)"Failed to remove pending transform - this may lead to memory leaks!", (Throwable)object);
                return;
            }
            if (this.zag()) return;
            if (this.zah == null) return;
            this.zah.zaf();
            return;
        }
        finally {
            this.zaf.unlock();
        }
    }

    final boolean zab() {
        if (!this.zal) {
            return false;
        }
        this.zal = false;
        this.zao.removeMessages(2);
        this.zao.removeMessages(1);
        zabj zabj2 = this.zaq;
        if (zabj2 == null) return true;
        zabj2.zaa();
        this.zaq = null;
        return true;
    }

    final String zac() {
        StringWriter stringWriter = new StringWriter();
        ((GoogleApiClient)this).dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }
}


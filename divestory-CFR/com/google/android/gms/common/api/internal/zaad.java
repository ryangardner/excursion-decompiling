/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zaaf;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.common.api.internal.zaai;
import com.google.android.gms.common.api.internal.zaaj;
import com.google.android.gms.common.api.internal.zaao;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zabb;
import com.google.android.gms.common.api.internal.zabm;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zas;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.zam;
import com.google.android.gms.signin.zad;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zaad
implements zaay {
    private final zaax zaa;
    private final Lock zab;
    private final Context zac;
    private final GoogleApiAvailabilityLight zad;
    private ConnectionResult zae;
    private int zaf;
    private int zag = 0;
    private int zah;
    private final Bundle zai = new Bundle();
    private final Set<Api.AnyClientKey> zaj = new HashSet<Api.AnyClientKey>();
    private zad zak;
    private boolean zal;
    private boolean zam;
    private boolean zan;
    private IAccountAccessor zao;
    private boolean zap;
    private boolean zaq;
    private final ClientSettings zar;
    private final Map<Api<?>, Boolean> zas;
    private final Api.AbstractClientBuilder<? extends zad, SignInOptions> zat;
    private ArrayList<Future<?>> zau = new ArrayList();

    public zaad(zaax zaax2, ClientSettings clientSettings, Map<Api<?>, Boolean> map, GoogleApiAvailabilityLight googleApiAvailabilityLight, Api.AbstractClientBuilder<? extends zad, SignInOptions> abstractClientBuilder, Lock lock, Context context) {
        this.zaa = zaax2;
        this.zar = clientSettings;
        this.zas = map;
        this.zad = googleApiAvailabilityLight;
        this.zat = abstractClientBuilder;
        this.zab = lock;
        this.zac = context;
    }

    static /* synthetic */ Context zaa(zaad zaad2) {
        return zaad2.zac;
    }

    static /* synthetic */ void zaa(zaad zaad2, ConnectionResult connectionResult) {
        zaad2.zab(connectionResult);
    }

    static /* synthetic */ void zaa(zaad zaad2, ConnectionResult connectionResult, Api api, boolean bl) {
        zaad2.zab(connectionResult, api, bl);
    }

    static /* synthetic */ void zaa(zaad zaad2, zam zam2) {
        zaad2.zaa(zam2);
    }

    private final void zaa(zam abstractSafeParcelable) {
        if (!this.zab(0)) {
            return;
        }
        Object object = ((zam)abstractSafeParcelable).zaa();
        if (((ConnectionResult)object).isSuccess()) {
            object = Preconditions.checkNotNull(((zam)abstractSafeParcelable).zab());
            abstractSafeParcelable = ((zas)object).zab();
            if (!((ConnectionResult)abstractSafeParcelable).isSuccess()) {
                object = String.valueOf(abstractSafeParcelable);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(object).length() + 48);
                stringBuilder.append("Sign-in succeeded with resolve account failure: ");
                stringBuilder.append((String)object);
                Log.wtf((String)"GACConnecting", (String)stringBuilder.toString(), (Throwable)new Exception());
                this.zab((ConnectionResult)abstractSafeParcelable);
                return;
            }
            this.zan = true;
            this.zao = Preconditions.checkNotNull(((zas)object).zaa());
            this.zap = ((zas)object).zac();
            this.zaq = ((zas)object).zad();
            this.zae();
            return;
        }
        if (this.zaa((ConnectionResult)object)) {
            this.zag();
            this.zae();
            return;
        }
        this.zab((ConnectionResult)object);
    }

    private final void zaa(boolean bl) {
        Object object = this.zak;
        if (object == null) return;
        if (object.isConnected() && bl) {
            object.zaa();
        }
        object.disconnect();
        object = Preconditions.checkNotNull(this.zar);
        this.zao = null;
    }

    private final boolean zaa(ConnectionResult connectionResult) {
        if (!this.zal) return false;
        if (connectionResult.hasResolution()) return false;
        return true;
    }

    static /* synthetic */ boolean zaa(zaad zaad2, int n) {
        return zaad2.zab(0);
    }

    static /* synthetic */ GoogleApiAvailabilityLight zab(zaad zaad2) {
        return zaad2.zad;
    }

    private final void zab(ConnectionResult connectionResult) {
        this.zah();
        this.zaa(connectionResult.hasResolution() ^ true);
        this.zaa.zaa(connectionResult);
        this.zaa.zae.zaa(connectionResult);
    }

    private final void zab(ConnectionResult connectionResult, Api<?> api, boolean bl) {
        int n;
        boolean bl2;
        block5 : {
            block6 : {
                boolean bl3;
                block4 : {
                    n = api.zaa().getPriority();
                    bl3 = false;
                    if (!bl) break block4;
                    boolean bl4 = connectionResult.hasResolution() || this.zad.getErrorResolutionIntent(connectionResult.getErrorCode()) != null;
                    bl2 = bl3;
                    if (!bl4) break block5;
                }
                if (this.zae == null) break block6;
                bl2 = bl3;
                if (n >= this.zaf) break block5;
            }
            bl2 = true;
        }
        if (bl2) {
            this.zae = connectionResult;
            this.zaf = n;
        }
        this.zaa.zab.put(api.zac(), connectionResult);
    }

    private final boolean zab(int n) {
        if (this.zag == n) return true;
        Log.w((String)"GACConnecting", (String)this.zaa.zad.zac());
        String string2 = String.valueOf(this);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 23);
        stringBuilder.append("Unexpected callback in ");
        stringBuilder.append(string2);
        Log.w((String)"GACConnecting", (String)stringBuilder.toString());
        int n2 = this.zah;
        stringBuilder = new StringBuilder(33);
        stringBuilder.append("mRemainingConnections=");
        stringBuilder.append(n2);
        Log.w((String)"GACConnecting", (String)stringBuilder.toString());
        String string3 = zaad.zac(this.zag);
        string2 = zaad.zac(n);
        stringBuilder = new StringBuilder(String.valueOf(string3).length() + 70 + String.valueOf(string2).length());
        stringBuilder.append("GoogleApiClient connecting is in step ");
        stringBuilder.append(string3);
        stringBuilder.append(" but received callback for step ");
        stringBuilder.append(string2);
        Log.e((String)"GACConnecting", (String)stringBuilder.toString(), (Throwable)new Exception());
        this.zab(new ConnectionResult(8, null));
        return false;
    }

    static /* synthetic */ boolean zab(zaad zaad2, ConnectionResult connectionResult) {
        return zaad2.zaa(connectionResult);
    }

    private static String zac(int n) {
        if (n == 0) return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
        if (n == 1) return "STEP_GETTING_REMOTE_SERVICE";
        return "UNKNOWN";
    }

    static /* synthetic */ Lock zac(zaad zaad2) {
        return zaad2.zab;
    }

    static /* synthetic */ zaax zad(zaad zaad2) {
        return zaad2.zaa;
    }

    private final boolean zad() {
        int n;
        this.zah = n = this.zah - 1;
        if (n > 0) {
            return false;
        }
        if (n < 0) {
            Log.w((String)"GACConnecting", (String)this.zaa.zad.zac());
            Log.wtf((String)"GACConnecting", (String)"GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", (Throwable)new Exception());
            this.zab(new ConnectionResult(8, null));
            return false;
        }
        if (this.zae == null) return true;
        this.zaa.zac = this.zaf;
        this.zab(this.zae);
        return false;
    }

    private final void zae() {
        if (this.zah != 0) {
            return;
        }
        if (this.zam) {
            if (!this.zan) return;
        }
        ArrayList<Api.Client> arrayList = new ArrayList<Api.Client>();
        this.zag = 1;
        this.zah = this.zaa.zaa.size();
        Iterator<Api.AnyClientKey<?>> iterator2 = this.zaa.zaa.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                if (arrayList.isEmpty()) return;
                this.zau.add(zabb.zaa().submit(new zaaj(this, arrayList)));
                return;
            }
            Api.AnyClientKey<?> anyClientKey = iterator2.next();
            if (this.zaa.zab.containsKey(anyClientKey)) {
                if (!this.zad()) continue;
                this.zaf();
                continue;
            }
            arrayList.add(this.zaa.zaa.get(anyClientKey));
        } while (true);
    }

    static /* synthetic */ boolean zae(zaad zaad2) {
        return zaad2.zam;
    }

    static /* synthetic */ zad zaf(zaad zaad2) {
        return zaad2.zak;
    }

    private final void zaf() {
        this.zaa.zai();
        zabb.zaa().execute(new zaag(this));
        Object object = this.zak;
        if (object != null) {
            if (this.zap) {
                object.zaa(Preconditions.checkNotNull(this.zao), this.zaq);
            }
            this.zaa(false);
        }
        for (Api.AnyClientKey anyClientKey : this.zaa.zab.keySet()) {
            Preconditions.checkNotNull(this.zaa.zaa.get(anyClientKey)).disconnect();
        }
        object = this.zai.isEmpty() ? null : this.zai;
        this.zaa.zae.zaa((Bundle)object);
    }

    static /* synthetic */ Set zag(zaad zaad2) {
        return zaad2.zai();
    }

    private final void zag() {
        this.zam = false;
        this.zaa.zad.zac = Collections.emptySet();
        Iterator<Api.AnyClientKey> iterator2 = this.zaj.iterator();
        while (iterator2.hasNext()) {
            Api.AnyClientKey anyClientKey = iterator2.next();
            if (this.zaa.zab.containsKey(anyClientKey)) continue;
            this.zaa.zab.put(anyClientKey, new ConnectionResult(17, null));
        }
    }

    static /* synthetic */ IAccountAccessor zah(zaad zaad2) {
        return zaad2.zao;
    }

    private final void zah() {
        ArrayList<Future<?>> arrayList = this.zau;
        int n = arrayList.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.zau.clear();
                return;
            }
            Future<?> future = arrayList.get(n2);
            ++n2;
            future.cancel(true);
        } while (true);
    }

    static /* synthetic */ ClientSettings zai(zaad zaad2) {
        return zaad2.zar;
    }

    private final Set<Scope> zai() {
        if (this.zar == null) {
            return Collections.emptySet();
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zar.getRequiredScopes());
        Map<Api<?>, ClientSettings.zaa> map = this.zar.zaa();
        Iterator<Api<?>> iterator2 = map.keySet().iterator();
        while (iterator2.hasNext()) {
            Api<?> api = iterator2.next();
            if (this.zaa.zab.containsKey(api.zac())) continue;
            hashSet.addAll(map.get(api).zaa);
        }
        return hashSet;
    }

    static /* synthetic */ void zaj(zaad zaad2) {
        zaad2.zag();
    }

    static /* synthetic */ void zak(zaad zaad2) {
        zaad2.zae();
    }

    static /* synthetic */ boolean zal(zaad zaad2) {
        return zaad2.zad();
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T t) {
        this.zaa.zad.zaa.add(t);
        return t;
    }

    @Override
    public final void zaa() {
        Object object;
        Api.Client client;
        this.zaa.zab.clear();
        this.zam = false;
        this.zae = null;
        this.zag = 0;
        this.zal = true;
        this.zan = false;
        this.zap = false;
        HashMap<Api.Client, zaaf> hashMap = new HashMap<Api.Client, zaaf>();
        Object object2 = this.zas.keySet().iterator();
        boolean bl = false;
        while (object2.hasNext()) {
            object = object2.next();
            client = Preconditions.checkNotNull(this.zaa.zaa.get(((Api)object).zac()));
            boolean bl2 = ((Api)object).zaa().getPriority() == 1;
            bl |= bl2;
            boolean bl3 = this.zas.get(object);
            if (client.requiresSignIn()) {
                this.zam = true;
                if (bl3) {
                    this.zaj.add(((Api)object).zac());
                } else {
                    this.zal = false;
                }
            }
            hashMap.put(client, new zaaf(this, (Api<?>)object, bl3));
        }
        if (bl) {
            this.zam = false;
        }
        if (this.zam) {
            Preconditions.checkNotNull(this.zar);
            Preconditions.checkNotNull(this.zat);
            this.zar.zaa(System.identityHashCode(this.zaa.zad));
            object = new zaao(this, null);
            object2 = this.zat;
            Context context = this.zac;
            client = ((GoogleApiClient)this.zaa.zad).getLooper();
            ClientSettings clientSettings = this.zar;
            this.zak = (zad)((Api.AbstractClientBuilder)object2).buildClient(context, (Looper)client, clientSettings, clientSettings.zac(), (GoogleApiClient.ConnectionCallbacks)object, (GoogleApiClient.OnConnectionFailedListener)object);
        }
        this.zah = this.zaa.zaa.size();
        this.zau.add(zabb.zaa().submit(new zaai(this, hashMap)));
    }

    @Override
    public final void zaa(int n) {
        this.zab(new ConnectionResult(8, null));
    }

    @Override
    public final void zaa(Bundle bundle) {
        if (!this.zab(1)) {
            return;
        }
        if (bundle != null) {
            this.zai.putAll(bundle);
        }
        if (!this.zad()) return;
        this.zaf();
    }

    @Override
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean bl) {
        if (!this.zab(1)) {
            return;
        }
        this.zab(connectionResult, api, bl);
        if (!this.zad()) return;
        this.zaf();
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override
    public final boolean zab() {
        this.zah();
        this.zaa(true);
        this.zaa.zaa((ConnectionResult)null);
        return true;
    }

    @Override
    public final void zac() {
    }
}


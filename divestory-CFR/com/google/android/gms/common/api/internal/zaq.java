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
import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zabm;
import com.google.android.gms.common.api.internal.zabn;
import com.google.android.gms.common.api.internal.zap;
import com.google.android.gms.common.api.internal.zas;
import com.google.android.gms.common.api.internal.zat;
import com.google.android.gms.common.api.internal.zau;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zad;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zaq
implements zabn {
    private final Context zaa;
    private final zaap zab;
    private final Looper zac;
    private final zaax zad;
    private final zaax zae;
    private final Map<Api.AnyClientKey<?>, zaax> zaf;
    private final Set<SignInConnectionListener> zag = Collections.newSetFromMap(new WeakHashMap());
    private final Api.Client zah;
    private Bundle zai;
    private ConnectionResult zaj = null;
    private ConnectionResult zak = null;
    private boolean zal = false;
    private final Lock zam;
    private int zan = 0;

    private zaq(Context object, zaap iterator2, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, Map<Api.AnyClientKey<?>, Api.Client> map2, ClientSettings clientSettings, Api.AbstractClientBuilder<? extends zad, SignInOptions> abstractClientBuilder, Api.Client client, ArrayList<zap> arrayList, ArrayList<zap> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.zaa = object;
        this.zab = iterator2;
        this.zam = lock;
        this.zac = looper;
        this.zah = client;
        this.zad = new zaax((Context)object, this.zab, lock, looper, googleApiAvailabilityLight, map2, null, map4, null, arrayList2, new zas(this, null));
        this.zae = new zaax((Context)object, this.zab, lock, looper, googleApiAvailabilityLight, map, clientSettings, map3, abstractClientBuilder, arrayList, new zau(this, null));
        object = new ArrayMap();
        iterator2 = map2.keySet().iterator();
        while (iterator2.hasNext()) {
            ((SimpleArrayMap)object).put((Api.AnyClientKey)iterator2.next(), this.zad);
        }
        iterator2 = map.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.zaf = Collections.unmodifiableMap(object);
                return;
            }
            ((SimpleArrayMap)object).put(iterator2.next(), this.zae);
        } while (true);
    }

    static /* synthetic */ ConnectionResult zaa(zaq zaq2, ConnectionResult connectionResult) {
        zaq2.zaj = connectionResult;
        return connectionResult;
    }

    public static zaq zaa(Context context, zaap zaap2, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> object, ClientSettings clientSettings, Map<Api<?>, Boolean> object2, Api.AbstractClientBuilder<? extends zad, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList) {
        Api.AnyClientKey<?> anyClientKey;
        Object object4;
        Object object3;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Object object5 = object.entrySet().iterator();
        object = null;
        while (object5.hasNext()) {
            object4 = object5.next();
            object3 = (Api.Client)object4.getValue();
            if (object3.providesSignIn()) {
                object = object3;
            }
            if (object3.requiresSignIn()) {
                arrayMap.put((Api.AnyClientKey)object4.getKey(), (Api.Client)object3);
                continue;
            }
            arrayMap2.put((Api.AnyClientKey)object4.getKey(), (Api.Client)object3);
        }
        Preconditions.checkState(arrayMap.isEmpty() ^ true, "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        object3 = new ArrayMap();
        object5 = new ArrayMap();
        for (Api api : object2.keySet()) {
            anyClientKey = api.zac();
            if (arrayMap.containsKey(anyClientKey)) {
                object3.put(api, (Boolean)object2.get(api));
                continue;
            }
            if (!arrayMap2.containsKey(anyClientKey)) throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            object5.put(api, (Boolean)object2.get(api));
        }
        object2 = new ArrayList<zap>();
        object4 = new ArrayList<zap>();
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            anyClientKey = arrayList.get(n2);
            ++n2;
            anyClientKey = (zap)((Object)anyClientKey);
            if (object3.containsKey(((zap)anyClientKey).zaa)) {
                object2.add((zap)((Object)anyClientKey));
                continue;
            }
            if (!object5.containsKey(((zap)anyClientKey).zaa)) throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            ((ArrayList)object4).add((zap)((Object)anyClientKey));
        }
        return new zaq(context, zaap2, lock, looper, googleApiAvailabilityLight, arrayMap, arrayMap2, clientSettings, abstractClientBuilder, (Api.Client)object, object2, (ArrayList<zap>)object4, (Map<Api<?>, Boolean>)object3, (Map<Api<?>, Boolean>)object5);
    }

    static /* synthetic */ Lock zaa(zaq zaq2) {
        return zaq2.zam;
    }

    private final void zaa(int n, boolean bl) {
        this.zab.zaa(n, bl);
        this.zak = null;
        this.zaj = null;
    }

    private final void zaa(Bundle bundle) {
        Bundle bundle2 = this.zai;
        if (bundle2 == null) {
            this.zai = bundle;
            return;
        }
        if (bundle == null) return;
        bundle2.putAll(bundle);
    }

    /*
     * Unable to fully structure code
     */
    private final void zaa(ConnectionResult var1_1) {
        var2_2 = this.zan;
        if (var2_2 == 1) ** GOTO lbl8
        if (var2_2 != 2) {
            Log.wtf((String)"CompositeGAC", (String)"Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)new Exception());
        } else {
            this.zab.zaa(var1_1);
lbl8: // 2 sources:
            this.zai();
        }
        this.zan = 0;
    }

    static /* synthetic */ void zaa(zaq zaq2, int n, boolean bl) {
        zaq2.zaa(n, bl);
    }

    static /* synthetic */ void zaa(zaq zaq2, Bundle bundle) {
        zaq2.zaa(bundle);
    }

    static /* synthetic */ boolean zaa(zaq zaq2, boolean bl) {
        zaq2.zal = bl;
        return bl;
    }

    static /* synthetic */ ConnectionResult zab(zaq zaq2, ConnectionResult connectionResult) {
        zaq2.zak = connectionResult;
        return connectionResult;
    }

    static /* synthetic */ void zab(zaq zaq2) {
        zaq2.zah();
    }

    private static boolean zab(ConnectionResult connectionResult) {
        if (connectionResult == null) return false;
        if (!connectionResult.isSuccess()) return false;
        return true;
    }

    private final boolean zac(BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient> object) {
        object = ((BaseImplementation.ApiMethodImpl)object).getClientKey();
        object = this.zaf.get(object);
        Preconditions.checkNotNull(object, "GoogleApiClient is not configured to use the API required for this call.");
        return object.equals(this.zae);
    }

    static /* synthetic */ boolean zac(zaq zaq2) {
        return zaq2.zal;
    }

    static /* synthetic */ ConnectionResult zad(zaq zaq2) {
        return zaq2.zak;
    }

    static /* synthetic */ zaax zae(zaq zaq2) {
        return zaq2.zae;
    }

    static /* synthetic */ zaax zaf(zaq zaq2) {
        return zaq2.zad;
    }

    /*
     * Unable to fully structure code
     */
    private final void zah() {
        block6 : {
            if (!zaq.zab(this.zaj)) break block6;
            if (!zaq.zab(this.zak) && !this.zaj()) {
                var1_1 = this.zak;
                if (var1_1 == null) return;
                if (this.zan == 1) {
                    this.zai();
                    return;
                }
                this.zaa(var1_1);
                this.zad.zac();
                return;
            }
            var2_3 = this.zan;
            if (var2_3 == 1) ** GOTO lbl18
            if (var2_3 != 2) {
                Log.wtf((String)"CompositeGAC", (String)"Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)new AssertionError());
            } else {
                Preconditions.checkNotNull(this.zab).zaa(this.zai);
lbl18: // 2 sources:
                this.zai();
            }
            this.zan = 0;
            return;
        }
        if (this.zaj != null && zaq.zab(this.zak)) {
            this.zae.zac();
            this.zaa(Preconditions.checkNotNull(this.zaj));
            return;
        }
        var1_2 = this.zaj;
        if (var1_2 == null) return;
        if (this.zak == null) return;
        if (this.zae.zac < this.zad.zac) {
            var1_2 = this.zak;
        }
        this.zaa(var1_2);
    }

    private final void zai() {
        Iterator<SignInConnectionListener> iterator2 = this.zag.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.zag.clear();
                return;
            }
            iterator2.next().onComplete();
        } while (true);
    }

    private final boolean zaj() {
        ConnectionResult connectionResult = this.zak;
        if (connectionResult == null) return false;
        if (connectionResult.getErrorCode() != 4) return false;
        return true;
    }

    private final PendingIntent zak() {
        if (this.zah != null) return PendingIntent.getActivity((Context)this.zaa, (int)System.identityHashCode(this.zab), (Intent)this.zah.getSignInIntent(), (int)134217728);
        return null;
    }

    @Override
    public final ConnectionResult zaa(long l, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final ConnectionResult zaa(Api<?> api) {
        if (!Objects.equal(this.zaf.get(api.zac()), this.zae)) return this.zad.zaa(api);
        if (!this.zaj()) return this.zae.zaa(api);
        return new ConnectionResult(4, this.zak());
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T t) {
        if (!this.zac((BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>)t)) return this.zad.zaa(t);
        if (!this.zaj()) return this.zae.zaa(t);
        ((BaseImplementation.ApiMethodImpl)t).setFailedResult(new Status(4, null, this.zak()));
        return t;
    }

    @Override
    public final void zaa() {
        this.zan = 2;
        this.zal = false;
        this.zak = null;
        this.zaj = null;
        this.zad.zaa();
        this.zae.zaa();
    }

    @Override
    public final void zaa(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string2).append("authClient").println(":");
        this.zae.zaa(String.valueOf(string2).concat("  "), fileDescriptor, printWriter, arrstring);
        printWriter.append(string2).append("anonClient").println(":");
        this.zad.zaa(String.valueOf(string2).concat("  "), fileDescriptor, printWriter, arrstring);
    }

    @Override
    public final boolean zaa(SignInConnectionListener signInConnectionListener) {
        this.zam.lock();
        try {
            if (!this.zae()) {
                if (!this.zad()) return false;
            }
            if (this.zae.zad()) return false;
            this.zag.add(signInConnectionListener);
            if (this.zan == 0) {
                this.zan = 1;
            }
            this.zak = null;
            this.zae.zaa();
            return true;
        }
        finally {
            this.zam.unlock();
        }
    }

    @Override
    public final ConnectionResult zab() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T t) {
        if (!this.zac((BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>)t)) return this.zad.zab(t);
        if (!this.zaj()) return this.zae.zab(t);
        ((BaseImplementation.ApiMethodImpl)t).setFailedResult(new Status(4, null, this.zak()));
        return t;
    }

    @Override
    public final void zac() {
        this.zak = null;
        this.zaj = null;
        this.zan = 0;
        this.zad.zac();
        this.zae.zac();
        this.zai();
    }

    /*
     * Exception decompiling
     */
    @Override
    public final boolean zad() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:414)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:226)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:646)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:52)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:580)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public final boolean zae() {
        this.zam.lock();
        try {
            int n = this.zan;
            boolean bl = n == 2;
            this.zam.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            this.zam.unlock();
            throw throwable;
        }
    }

    @Override
    public final void zaf() {
        this.zad.zaf();
        this.zae.zaf();
    }

    @Override
    public final void zag() {
        this.zam.lock();
        try {
            Object object;
            boolean bl = this.zae();
            this.zae.zac();
            this.zak = object = new ConnectionResult(4);
            if (bl) {
                object = new com.google.android.gms.internal.base.zap(this.zac);
                zat zat2 = new zat(this);
                object.post((Runnable)zat2);
                return;
            }
            this.zai();
            return;
        }
        finally {
            this.zam.unlock();
        }
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  org.checkerframework.checker.initialization.qual.NotOnlyInitialized
 */
package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zaaa;
import com.google.android.gms.common.api.internal.zabc;
import com.google.android.gms.common.api.internal.zabd;
import com.google.android.gms.common.api.internal.zabe;
import com.google.android.gms.common.api.internal.zabf;
import com.google.android.gms.common.api.internal.zabg;
import com.google.android.gms.common.api.internal.zabi;
import com.google.android.gms.common.api.internal.zabr;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zacb;
import com.google.android.gms.common.api.internal.zace;
import com.google.android.gms.common.api.internal.zad;
import com.google.android.gms.common.api.internal.zaf;
import com.google.android.gms.common.api.internal.zag;
import com.google.android.gms.common.api.internal.zah;
import com.google.android.gms.common.api.internal.zaj;
import com.google.android.gms.common.api.internal.zar;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.common.api.internal.zax;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.internal.base.zap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public class GoogleApiManager
implements Handler.Callback {
    public static final Status zaa = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zab = new Status(4, "The user must be signed in to make this API call.");
    private static final Object zaf = new Object();
    private static GoogleApiManager zag;
    private long zac = 5000L;
    private long zad = 120000L;
    private long zae = 10000L;
    private final Context zah;
    private final GoogleApiAvailability zai;
    private final com.google.android.gms.common.internal.zaj zaj;
    private final AtomicInteger zak = new AtomicInteger(1);
    private final AtomicInteger zal = new AtomicInteger(0);
    private final Map<ApiKey<?>, zaa<?>> zam = new ConcurrentHashMap(5, 0.75f, 1);
    private zax zan = null;
    private final Set<ApiKey<?>> zao = new ArraySet();
    private final Set<ApiKey<?>> zap = new ArraySet();
    @NotOnlyInitialized
    private final Handler zaq;
    private volatile boolean zar = true;

    private GoogleApiManager(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zah = context;
        this.zaq = new zap(looper, this);
        this.zai = googleApiAvailability;
        this.zaj = new com.google.android.gms.common.internal.zaj(googleApiAvailability);
        if (DeviceProperties.isAuto(context)) {
            this.zar = false;
        }
        context = this.zaq;
        context.sendMessage(context.obtainMessage(6));
    }

    public static void reportSignOut() {
        Object object = zaf;
        synchronized (object) {
            if (zag == null) return;
            GoogleApiManager googleApiManager = zag;
            googleApiManager.zal.incrementAndGet();
            googleApiManager.zaq.sendMessageAtFrontOfQueue(googleApiManager.zaq.obtainMessage(10));
            return;
        }
    }

    public static GoogleApiManager zaa() {
        Object object = zaf;
        synchronized (object) {
            Preconditions.checkNotNull(zag, "Must guarantee manager is non-null before using getInstance");
            return zag;
        }
    }

    public static GoogleApiManager zaa(Context object) {
        Object object2 = zaf;
        synchronized (object2) {
            GoogleApiManager googleApiManager;
            if (zag != null) return zag;
            HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
            handlerThread.start();
            handlerThread = handlerThread.getLooper();
            zag = googleApiManager = new GoogleApiManager(object.getApplicationContext(), (Looper)handlerThread, GoogleApiAvailability.getInstance());
            return zag;
        }
    }

    private static Status zab(ApiKey<?> object, ConnectionResult connectionResult) {
        String string2 = ((ApiKey)object).getApiName();
        object = String.valueOf(connectionResult);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 63 + String.valueOf(object).length());
        stringBuilder.append("API: ");
        stringBuilder.append(string2);
        stringBuilder.append(" is not available on this device. Connection failed with: ");
        stringBuilder.append((String)object);
        return new Status(connectionResult, stringBuilder.toString());
    }

    private final zaa<?> zac(GoogleApi<?> googleApi) {
        zaa<?> zaa2;
        ApiKey<?> apiKey = googleApi.getApiKey();
        zaa<?> zaa3 = zaa2 = this.zam.get(apiKey);
        if (zaa2 == null) {
            zaa3 = new zaa(googleApi);
            this.zam.put(apiKey, zaa3);
        }
        if (zaa3.zak()) {
            this.zap.add(apiKey);
        }
        zaa3.zai();
        return zaa3;
    }

    public boolean handleMessage(Message object) {
        int n = ((Message)object).what;
        long l = 300000L;
        Object object2 = null;
        switch (n) {
            default: {
                n = ((Message)object).what;
                object = new StringBuilder(31);
                ((StringBuilder)object).append("Unknown message id: ");
                ((StringBuilder)object).append(n);
                Log.w((String)"GoogleApiManager", (String)((StringBuilder)object).toString());
                return false;
            }
            case 16: {
                object = (zab)((Message)object).obj;
                if (!this.zam.containsKey(((zab)object).zaa)) return true;
                this.zam.get(((zab)object).zaa).zab((zab)object);
                return true;
            }
            case 15: {
                object = (zab)((Message)object).obj;
                if (!this.zam.containsKey(((zab)object).zaa)) return true;
                this.zam.get(((zab)object).zaa).zaa((zab)object);
                return true;
            }
            case 14: {
                object2 = (zaaa)((Message)object).obj;
                object = ((zaaa)object2).zaa();
                if (!this.zam.containsKey(object)) {
                    ((zaaa)object2).zab().setResult(false);
                    return true;
                }
                boolean bl = this.zam.get(object).zaa(false);
                ((zaaa)object2).zab().setResult(bl);
                return true;
            }
            case 12: {
                if (!this.zam.containsKey(((Message)object).obj)) return true;
                this.zam.get(((Message)object).obj).zah();
                return true;
            }
            case 11: {
                if (!this.zam.containsKey(((Message)object).obj)) return true;
                this.zam.get(((Message)object).obj).zag();
                return true;
            }
            case 10: {
                object = this.zap.iterator();
                do {
                    if (!object.hasNext()) {
                        this.zap.clear();
                        return true;
                    }
                    object2 = (ApiKey)object.next();
                    if ((object2 = this.zam.remove(object2)) == null) continue;
                    ((zaa)object2).zaa();
                } while (true);
            }
            case 9: {
                if (!this.zam.containsKey(((Message)object).obj)) return true;
                this.zam.get(((Message)object).obj).zaf();
                return true;
            }
            case 7: {
                this.zac((GoogleApi)((Message)object).obj);
                return true;
            }
            case 6: {
                if (!(this.zah.getApplicationContext() instanceof Application)) return true;
                BackgroundDetector.initialize((Application)this.zah.getApplicationContext());
                BackgroundDetector.getInstance().addListener(new zabc(this));
                if (BackgroundDetector.getInstance().readCurrentStateIfPossible(true)) return true;
                this.zae = 300000L;
                return true;
            }
            case 5: {
                n = ((Message)object).arg1;
                Object object3 = (ConnectionResult)((Message)object).obj;
                Object object4 = this.zam.values().iterator();
                do {
                    object = object2;
                } while (object4.hasNext() && ((zaa)(object = object4.next())).zal() != n);
                if (object == null) {
                    object = new StringBuilder(76);
                    ((StringBuilder)object).append("Could not find API instance ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" while trying to fail enqueued calls.");
                    Log.wtf((String)"GoogleApiManager", (String)((StringBuilder)object).toString(), (Throwable)new Exception());
                    return true;
                }
                if (((ConnectionResult)object3).getErrorCode() == 13) {
                    object2 = this.zai.getErrorString(((ConnectionResult)object3).getErrorCode());
                    object4 = ((ConnectionResult)object3).getErrorMessage();
                    object3 = new StringBuilder(String.valueOf(object2).length() + 69 + String.valueOf(object4).length());
                    ((StringBuilder)object3).append("Error resolution was canceled by the user, original error message: ");
                    ((StringBuilder)object3).append((String)object2);
                    ((StringBuilder)object3).append(": ");
                    ((StringBuilder)object3).append((String)object4);
                    ((zaa)object).zaa(new Status(17, ((StringBuilder)object3).toString()));
                    return true;
                }
                ((zaa)object).zaa(GoogleApiManager.zab(((zaa)object).zad, (ConnectionResult)object3));
                return true;
            }
            case 4: 
            case 8: 
            case 13: {
                zabr zabr2 = (zabr)((Message)object).obj;
                object = object2 = this.zam.get(zabr2.zac.getApiKey());
                if (object2 == null) {
                    object = this.zac(zabr2.zac);
                }
                if (((zaa)object).zak() && this.zal.get() != zabr2.zab) {
                    zabr2.zaa.zaa(zaa);
                    ((zaa)object).zaa();
                    return true;
                }
                ((zaa)object).zaa(zabr2.zaa);
                return true;
            }
            case 3: {
                object = this.zam.values().iterator();
                while (object.hasNext()) {
                    object2 = (zaa<?>)object.next();
                    ((zaa)object2).zad();
                    ((zaa)object2).zai();
                }
                return true;
            }
            case 2: {
                object2 = (zaj)((Message)object).obj;
                Iterator<ApiKey<?>> iterator2 = ((zaj)object2).zaa().iterator();
                while (iterator2.hasNext()) {
                    ApiKey<?> apiKey = iterator2.next();
                    object = this.zam.get(apiKey);
                    if (object == null) {
                        ((zaj)object2).zaa(apiKey, new ConnectionResult(13), null);
                        return true;
                    }
                    if (((zaa)object).zaj()) {
                        ((zaj)object2).zaa(apiKey, ConnectionResult.RESULT_SUCCESS, ((zaa)object).zab().getEndpointPackageName());
                        continue;
                    }
                    ConnectionResult connectionResult = ((zaa)object).zae();
                    if (connectionResult != null) {
                        ((zaj)object2).zaa(apiKey, connectionResult, null);
                        continue;
                    }
                    ((zaa)object).zaa((zaj)object2);
                    ((zaa)object).zai();
                }
                return true;
            }
            case 1: 
        }
        if (((Boolean)((Message)object).obj).booleanValue()) {
            l = 10000L;
        }
        this.zae = l;
        this.zaq.removeMessages(12);
        Iterator<ApiKey<?>> iterator3 = this.zam.keySet().iterator();
        while (iterator3.hasNext()) {
            object2 = iterator3.next();
            object = this.zaq;
            object.sendMessageDelayed(object.obtainMessage(12, object2), this.zae);
        }
        return true;
    }

    public final <O extends Api.ApiOptions> Task<Boolean> zaa(GoogleApi<O> googleApi, ListenerHolder.ListenerKey<?> handler) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<Boolean>();
        zah zah2 = new zah((ListenerHolder.ListenerKey<?>)handler, taskCompletionSource);
        handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(13, (Object)new zabr(zah2, this.zal.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends Api.ApiOptions> Task<Void> zaa(GoogleApi<O> googleApi, RegisterListenerMethod<Api.AnyClient, ?> object, UnregisterListenerMethod<Api.AnyClient, ?> handler, Runnable runnable2) {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<Void>();
        object = new zag(new zabs((RegisterListenerMethod<Api.AnyClient, ?>)object, (UnregisterListenerMethod<Api.AnyClient, ?>)handler, runnable2), taskCompletionSource);
        handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(8, (Object)new zabr((com.google.android.gms.common.api.internal.zac)object, this.zal.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final Task<Map<ApiKey<?>, String>> zaa(Iterable<? extends HasApiKey<?>> object) {
        object = new zaj((Iterable<? extends HasApiKey<?>>)object);
        Handler handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(2, object));
        return ((zaj)object).zab();
    }

    public final void zaa(GoogleApi<?> googleApi) {
        Handler handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(7, googleApi));
    }

    public final <O extends Api.ApiOptions> void zaa(GoogleApi<O> googleApi, int n, BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient> handler) {
        zad<BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>> zad2 = new zad<BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>>(n, (BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>)handler);
        handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(4, (Object)new zabr(zad2, this.zal.get(), googleApi)));
    }

    public final <O extends Api.ApiOptions, ResultT> void zaa(GoogleApi<O> googleApi, int n, TaskApiCall<Api.AnyClient, ResultT> handler, TaskCompletionSource<ResultT> object, StatusExceptionMapper statusExceptionMapper) {
        object = new zaf<ResultT>(n, (TaskApiCall<Api.AnyClient, ResultT>)handler, (TaskCompletionSource<ResultT>)object, statusExceptionMapper);
        handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(4, (Object)new zabr((com.google.android.gms.common.api.internal.zac)object, this.zal.get(), googleApi)));
    }

    public final void zaa(zax zax2) {
        Object object = zaf;
        synchronized (object) {
            if (this.zan != zax2) {
                this.zan = zax2;
                this.zao.clear();
            }
            this.zao.addAll(zax2.zac());
            return;
        }
    }

    final boolean zaa(ConnectionResult connectionResult, int n) {
        return this.zai.zaa(this.zah, connectionResult, n);
    }

    public final int zab() {
        return this.zak.getAndIncrement();
    }

    public final Task<Boolean> zab(GoogleApi<?> object) {
        object = new zaaa(((GoogleApi)object).getApiKey());
        Handler handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(14, object));
        return ((zaaa)object).zab().getTask();
    }

    public final void zab(ConnectionResult connectionResult, int n) {
        if (this.zaa(connectionResult, n)) return;
        Handler handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(5, n, 0, (Object)connectionResult));
    }

    final void zab(zax zax2) {
        Object object = zaf;
        synchronized (object) {
            if (this.zan != zax2) return;
            this.zan = null;
            this.zao.clear();
            return;
        }
    }

    public final void zac() {
        Handler handler = this.zaq;
        handler.sendMessage(handler.obtainMessage(3));
    }

    public final class zaa<O extends Api.ApiOptions>
    implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    zar {
        private final Queue<com.google.android.gms.common.api.internal.zac> zab = new LinkedList<com.google.android.gms.common.api.internal.zac>();
        @NotOnlyInitialized
        private final Api.Client zac;
        private final ApiKey<O> zad;
        private final zaw zae;
        private final Set<zaj> zaf = new HashSet<zaj>();
        private final Map<ListenerHolder.ListenerKey<?>, zabs> zag = new HashMap();
        private final int zah;
        private final zacb zai;
        private boolean zaj;
        private final List<zab> zak = new ArrayList<zab>();
        private ConnectionResult zal = null;

        public zaa(GoogleApi<O> googleApi) {
            this.zac = googleApi.zaa(GoogleApiManager.this.zaq.getLooper(), this);
            this.zad = googleApi.getApiKey();
            this.zae = new zaw();
            this.zah = googleApi.zaa();
            if (this.zac.requiresSignIn()) {
                this.zai = googleApi.zaa(GoogleApiManager.this.zah, GoogleApiManager.this.zaq);
                return;
            }
            this.zai = null;
        }

        private final Feature zaa(Feature[] arrfeature) {
            if (arrfeature == null) return null;
            if (arrfeature.length == 0) {
                return null;
            }
            Object object = this.zac.getAvailableFeatures();
            int n = 0;
            Object object2 = object;
            if (object == null) {
                object2 = new Feature[]{};
            }
            object = new ArrayMap(((Feature[])object2).length);
            for (Object object3 : object2) {
                object.put(((Feature)object3).getName(), ((Feature)object3).getVersion());
            }
            int n2 = arrfeature.length;
            int n3 = n;
            while (n3 < n2) {
                Object object3;
                object2 = arrfeature[n3];
                object3 = (Long)object.get(((Feature)object2).getName());
                if (object3 == null) return object2;
                if ((Long)object3 < ((Feature)object2).getVersion()) {
                    return object2;
                }
                ++n3;
            }
            return null;
        }

        private final void zaa(int n) {
            this.zad();
            this.zaj = true;
            this.zae.zaa(n, this.zac.getLastDisconnectMessage());
            GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain((Handler)GoogleApiManager.this.zaq, (int)9, this.zad), GoogleApiManager.this.zac);
            GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain((Handler)GoogleApiManager.this.zaq, (int)11, this.zad), GoogleApiManager.this.zad);
            GoogleApiManager.this.zaj.zaa();
            Iterator<zabs> iterator2 = this.zag.values().iterator();
            while (iterator2.hasNext()) {
                iterator2.next().zac.run();
            }
        }

        private final void zaa(ConnectionResult connectionResult, Exception exception) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            zacb zacb2 = this.zai;
            if (zacb2 != null) {
                zacb2.zaa();
            }
            this.zad();
            GoogleApiManager.this.zaj.zaa();
            this.zac(connectionResult);
            if (connectionResult.getErrorCode() == 4) {
                this.zaa(zab);
                return;
            }
            if (this.zab.isEmpty()) {
                this.zal = connectionResult;
                return;
            }
            if (exception != null) {
                Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
                this.zaa(null, exception, false);
                return;
            }
            if (!GoogleApiManager.this.zar) {
                this.zaa(this.zad(connectionResult));
                return;
            }
            this.zaa(this.zad(connectionResult), null, true);
            if (this.zab.isEmpty()) {
                return;
            }
            if (this.zab(connectionResult)) {
                return;
            }
            if (GoogleApiManager.this.zaa(connectionResult, this.zah)) return;
            if (connectionResult.getErrorCode() == 18) {
                this.zaj = true;
            }
            if (this.zaj) {
                GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain((Handler)GoogleApiManager.this.zaq, (int)9, this.zad), GoogleApiManager.this.zac);
                return;
            }
            this.zaa(this.zad(connectionResult));
        }

        private final void zaa(Status status) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            this.zaa(status, null, false);
        }

        private final void zaa(Status status, Exception exception, boolean bl) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            boolean bl2 = true;
            boolean bl3 = status == null;
            if (exception != null) {
                bl2 = false;
            }
            if (bl3 == bl2) throw new IllegalArgumentException("Status XOR exception should be null");
            Iterator iterator2 = this.zab.iterator();
            while (iterator2.hasNext()) {
                com.google.android.gms.common.api.internal.zac zac2 = (com.google.android.gms.common.api.internal.zac)iterator2.next();
                if (bl && zac2.zaa != 2) continue;
                if (status != null) {
                    zac2.zaa(status);
                } else {
                    zac2.zaa(exception);
                }
                iterator2.remove();
            }
        }

        static /* synthetic */ void zaa(zaa zaa2, int n) {
            zaa2.zaa(n);
        }

        private final void zaa(zab zab2) {
            if (!this.zak.contains(zab2)) {
                return;
            }
            if (this.zaj) return;
            if (!this.zac.isConnected()) {
                this.zai();
                return;
            }
            this.zan();
        }

        private final boolean zaa(boolean bl) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            if (!this.zac.isConnected()) return false;
            if (this.zag.size() != 0) return false;
            if (this.zae.zaa()) {
                if (!bl) return false;
                this.zap();
                return false;
            }
            this.zac.disconnect("Timing out service connection.");
            return true;
        }

        static /* synthetic */ void zab(zaa zaa2) {
            zaa2.zam();
        }

        private final void zab(zab object) {
            if (!this.zak.remove(object)) return;
            GoogleApiManager.this.zaq.removeMessages(15, object);
            GoogleApiManager.this.zaq.removeMessages(16, object);
            object = ((zab)object).zab;
            ArrayList<com.google.android.gms.common.api.internal.zac> arrayList = new ArrayList<com.google.android.gms.common.api.internal.zac>(this.zab.size());
            for (com.google.android.gms.common.api.internal.zac zac2 : this.zab) {
                Feature[] arrfeature;
                if (!(zac2 instanceof com.google.android.gms.common.api.internal.zab) || (arrfeature = ((com.google.android.gms.common.api.internal.zab)zac2).zaa(this)) == null || !ArrayUtils.contains(arrfeature, object)) continue;
                arrayList.add(zac2);
            }
            ArrayList<com.google.android.gms.common.api.internal.zac> arrayList2 = arrayList;
            int n = arrayList2.size();
            int n2 = 0;
            while (n2 < n) {
                com.google.android.gms.common.api.internal.zac zac2;
                zac2 = arrayList2.get(n2);
                ++n2;
                this.zab.remove(zac2);
                zac2.zaa(new UnsupportedApiCallException((Feature)object));
            }
        }

        private final boolean zab(ConnectionResult connectionResult) {
            Object object = zaf;
            synchronized (object) {
                if (GoogleApiManager.this.zan == null) return false;
                if (!GoogleApiManager.this.zao.contains(this.zad)) return false;
                GoogleApiManager.this.zan.zab(connectionResult, this.zah);
                return true;
            }
        }

        private final boolean zab(com.google.android.gms.common.api.internal.zac object) {
            if (!(object instanceof com.google.android.gms.common.api.internal.zab)) {
                this.zac((com.google.android.gms.common.api.internal.zac)object);
                return true;
            }
            com.google.android.gms.common.api.internal.zab zab2 = (com.google.android.gms.common.api.internal.zab)object;
            Feature feature = this.zaa(zab2.zaa(this));
            if (feature == null) {
                this.zac((com.google.android.gms.common.api.internal.zac)object);
                return true;
            }
            String string2 = this.zac.getClass().getName();
            String string3 = feature.getName();
            long l = feature.getVersion();
            object = new StringBuilder(String.valueOf(string2).length() + 77 + String.valueOf(string3).length());
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" could not execute call because it requires feature (");
            ((StringBuilder)object).append(string3);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(").");
            Log.w((String)"GoogleApiManager", (String)((StringBuilder)object).toString());
            if (GoogleApiManager.this.zar && zab2.zab(this)) {
                object = new zab(this.zad, feature, null);
                int n = this.zak.indexOf(object);
                if (n >= 0) {
                    object = this.zak.get(n);
                    GoogleApiManager.this.zaq.removeMessages(15, object);
                    GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain((Handler)GoogleApiManager.this.zaq, (int)15, (Object)object), GoogleApiManager.this.zac);
                    return false;
                }
                this.zak.add((zab)object);
                GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain((Handler)GoogleApiManager.this.zaq, (int)15, (Object)object), GoogleApiManager.this.zac);
                GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain((Handler)GoogleApiManager.this.zaq, (int)16, (Object)object), GoogleApiManager.this.zad);
                object = new ConnectionResult(2, null);
                if (this.zab((ConnectionResult)object)) return false;
                GoogleApiManager.this.zaa((ConnectionResult)object, this.zah);
                return false;
            }
            zab2.zaa(new UnsupportedApiCallException(feature));
            return true;
        }

        static /* synthetic */ Api.Client zac(zaa zaa2) {
            return zaa2.zac;
        }

        private final void zac(ConnectionResult connectionResult) {
            Iterator<zaj> iterator2 = this.zaf.iterator();
            do {
                if (!iterator2.hasNext()) {
                    this.zaf.clear();
                    return;
                }
                zaj zaj2 = iterator2.next();
                String string2 = null;
                if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
                    string2 = this.zac.getEndpointPackageName();
                }
                zaj2.zaa(this.zad, connectionResult, string2);
            } while (true);
        }

        private final void zac(com.google.android.gms.common.api.internal.zac zac2) {
            zac2.zaa(this.zae, this.zak());
            try {
                zac2.zac(this);
                return;
            }
            catch (Throwable throwable) {
                throw new IllegalStateException(String.format("Error in GoogleApi implementation for client %s.", this.zac.getClass().getName()), throwable);
            }
            catch (DeadObjectException deadObjectException) {
                this.onConnectionSuspended(1);
                this.zac.disconnect("DeadObjectException thrown while running ApiCallRunner.");
                return;
            }
        }

        private final Status zad(ConnectionResult connectionResult) {
            return GoogleApiManager.zab(this.zad, connectionResult);
        }

        private final void zam() {
            this.zad();
            this.zac(ConnectionResult.RESULT_SUCCESS);
            this.zao();
            Iterator<zabs> iterator2 = this.zag.values().iterator();
            while (iterator2.hasNext()) {
                Object object = iterator2.next();
                if (this.zaa(((zabs)object).zaa.getRequiredFeatures()) != null) {
                    iterator2.remove();
                    continue;
                }
                try {
                    RegisterListenerMethod<Api.AnyClient, ?> registerListenerMethod = ((zabs)object).zaa;
                    object = this.zac;
                    TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<Void>();
                    registerListenerMethod.registerListener((Api.AnyClient)object, taskCompletionSource);
                }
                catch (RemoteException remoteException) {
                    iterator2.remove();
                }
                catch (DeadObjectException deadObjectException) {
                    this.onConnectionSuspended(3);
                    this.zac.disconnect("DeadObjectException thrown while calling register listener method.");
                    break;
                }
            }
            this.zan();
            this.zap();
        }

        private final void zan() {
            ArrayList<com.google.android.gms.common.api.internal.zac> arrayList = new ArrayList<com.google.android.gms.common.api.internal.zac>(this.zab);
            int n = arrayList.size();
            int n2 = 0;
            while (n2 < n) {
                com.google.android.gms.common.api.internal.zac zac2 = arrayList.get(n2);
                int n3 = n2 + 1;
                if (!this.zac.isConnected()) return;
                n2 = n3;
                if (!this.zab(zac2)) continue;
                this.zab.remove(zac2);
                n2 = n3;
            }
        }

        private final void zao() {
            if (!this.zaj) return;
            GoogleApiManager.this.zaq.removeMessages(11, this.zad);
            GoogleApiManager.this.zaq.removeMessages(9, this.zad);
            this.zaj = false;
        }

        private final void zap() {
            GoogleApiManager.this.zaq.removeMessages(12, this.zad);
            GoogleApiManager.this.zaq.sendMessageDelayed(GoogleApiManager.this.zaq.obtainMessage(12, this.zad), GoogleApiManager.this.zae);
        }

        @Override
        public final void onConnected(Bundle bundle) {
            if (Looper.myLooper() == GoogleApiManager.this.zaq.getLooper()) {
                this.zam();
                return;
            }
            GoogleApiManager.this.zaq.post((Runnable)new zabe(this));
        }

        @Override
        public final void onConnectionFailed(ConnectionResult connectionResult) {
            this.zaa(connectionResult, null);
        }

        @Override
        public final void onConnectionSuspended(int n) {
            if (Looper.myLooper() == GoogleApiManager.this.zaq.getLooper()) {
                this.zaa(n);
                return;
            }
            GoogleApiManager.this.zaq.post((Runnable)new zabd(this, n));
        }

        public final void zaa() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            this.zaa(zaa);
            this.zae.zab();
            ListenerHolder.ListenerKey[] arrlistenerKey = this.zag.keySet();
            int n = 0;
            arrlistenerKey = arrlistenerKey.toArray(new ListenerHolder.ListenerKey[0]);
            int n2 = arrlistenerKey.length;
            do {
                if (n >= n2) {
                    this.zac(new ConnectionResult(4));
                    if (!this.zac.isConnected()) return;
                    this.zac.onUserSignOut(new zabf(this));
                    return;
                }
                this.zaa(new zah(arrlistenerKey[n], new TaskCompletionSource<Boolean>()));
                ++n;
            } while (true);
        }

        public final void zaa(ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            Api.Client client = this.zac;
            String string2 = client.getClass().getName();
            String string3 = String.valueOf(connectionResult);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 25 + String.valueOf(string3).length());
            stringBuilder.append("onSignInFailed for ");
            stringBuilder.append(string2);
            stringBuilder.append(" with ");
            stringBuilder.append(string3);
            client.disconnect(stringBuilder.toString());
            this.onConnectionFailed(connectionResult);
        }

        @Override
        public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean bl) {
            if (Looper.myLooper() == GoogleApiManager.this.zaq.getLooper()) {
                this.onConnectionFailed(connectionResult);
                return;
            }
            GoogleApiManager.this.zaq.post((Runnable)new zabg(this, connectionResult));
        }

        public final void zaa(com.google.android.gms.common.api.internal.zac object) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            if (this.zac.isConnected()) {
                if (this.zab((com.google.android.gms.common.api.internal.zac)object)) {
                    this.zap();
                    return;
                }
                this.zab.add((com.google.android.gms.common.api.internal.zac)object);
                return;
            }
            this.zab.add((com.google.android.gms.common.api.internal.zac)object);
            object = this.zal;
            if (object != null && ((ConnectionResult)object).hasResolution()) {
                this.onConnectionFailed(this.zal);
                return;
            }
            this.zai();
        }

        public final void zaa(zaj zaj2) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            this.zaf.add(zaj2);
        }

        public final Api.Client zab() {
            return this.zac;
        }

        public final Map<ListenerHolder.ListenerKey<?>, zabs> zac() {
            return this.zag;
        }

        public final void zad() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            this.zal = null;
        }

        public final ConnectionResult zae() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            return this.zal;
        }

        public final void zaf() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            if (!this.zaj) return;
            this.zai();
        }

        public final void zag() {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            if (!this.zaj) return;
            this.zao();
            Status status = GoogleApiManager.this.zai.isGooglePlayServicesAvailable(GoogleApiManager.this.zah) == 18 ? new Status(21, "Connection timed out waiting for Google Play services update to complete.") : new Status(22, "API failed to connect while resuming due to an unknown error.");
            this.zaa(status);
            this.zac.disconnect("Timing out connection while resuming.");
        }

        public final boolean zah() {
            return this.zaa(true);
        }

        /*
         * Loose catch block
         * Enabled unnecessary exception pruning
         */
        public final void zai() {
            block6 : {
                Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
                if (this.zac.isConnected()) return;
                if (this.zac.isConnecting()) {
                    return;
                }
                int n = GoogleApiManager.this.zaj.zaa(GoogleApiManager.this.zah, this.zac);
                if (n == 0) break block6;
                ConnectionResult connectionResult = new ConnectionResult(n, null);
                String string2 = this.zac.getClass().getName();
                String string3 = String.valueOf(connectionResult);
                n = String.valueOf(string2).length();
                int n2 = String.valueOf(string3).length();
                StringBuilder stringBuilder = new StringBuilder(n + 35 + n2);
                stringBuilder.append("The service for ");
                stringBuilder.append(string2);
                stringBuilder.append(" is not available: ");
                stringBuilder.append(string3);
                Log.w((String)"GoogleApiManager", (String)stringBuilder.toString());
                this.onConnectionFailed(connectionResult);
                return;
            }
            zac zac2 = new zac(this.zac, this.zad);
            if (this.zac.requiresSignIn()) {
                Preconditions.checkNotNull(this.zai).zaa(zac2);
            }
            try {
                this.zac.connect(zac2);
                return;
            }
            catch (SecurityException securityException) {
                this.zaa(new ConnectionResult(10), securityException);
                return;
            }
            catch (IllegalStateException illegalStateException) {
                this.zaa(new ConnectionResult(10), illegalStateException);
            }
        }

        final boolean zaj() {
            return this.zac.isConnected();
        }

        public final boolean zak() {
            return this.zac.requiresSignIn();
        }

        public final int zal() {
            return this.zah;
        }
    }

    private static final class zab {
        private final ApiKey<?> zaa;
        private final Feature zab;

        private zab(ApiKey<?> apiKey, Feature feature) {
            this.zaa = apiKey;
            this.zab = feature;
        }

        /* synthetic */ zab(ApiKey apiKey, Feature feature, zabc zabc2) {
            this(apiKey, feature);
        }

        public final boolean equals(Object object) {
            if (object == null) return false;
            if (!(object instanceof zab)) return false;
            object = (zab)object;
            if (!Objects.equal(this.zaa, ((zab)object).zaa)) return false;
            if (!Objects.equal(this.zab, ((zab)object).zab)) return false;
            return true;
        }

        public final int hashCode() {
            return Objects.hashCode(this.zaa, this.zab);
        }

        public final String toString() {
            return Objects.toStringHelper(this).add("key", this.zaa).add("feature", this.zab).toString();
        }
    }

    private final class zac
    implements zace,
    BaseGmsClient.ConnectionProgressReportCallbacks {
        private final Api.Client zab;
        private final ApiKey<?> zac;
        private IAccountAccessor zad = null;
        private Set<Scope> zae = null;
        private boolean zaf = false;

        public zac(Api.Client client, ApiKey<?> apiKey) {
            this.zab = client;
            this.zac = apiKey;
        }

        static /* synthetic */ ApiKey zaa(zac zac2) {
            return zac2.zac;
        }

        private final void zaa() {
            if (!this.zaf) return;
            IAccountAccessor iAccountAccessor = this.zad;
            if (iAccountAccessor == null) return;
            this.zab.getRemoteService(iAccountAccessor, this.zae);
        }

        static /* synthetic */ boolean zaa(zac zac2, boolean bl) {
            zac2.zaf = true;
            return true;
        }

        static /* synthetic */ Api.Client zab(zac zac2) {
            return zac2.zab;
        }

        static /* synthetic */ void zac(zac zac2) {
            zac2.zaa();
        }

        @Override
        public final void onReportServiceBinding(ConnectionResult connectionResult) {
            GoogleApiManager.this.zaq.post((Runnable)new zabi(this, connectionResult));
        }

        @Override
        public final void zaa(ConnectionResult connectionResult) {
            zaa zaa2 = (zaa)GoogleApiManager.this.zam.get(this.zac);
            if (zaa2 == null) return;
            zaa2.zaa(connectionResult);
        }

        @Override
        public final void zaa(IAccountAccessor iAccountAccessor, Set<Scope> set) {
            if (iAccountAccessor != null && set != null) {
                this.zad = iAccountAccessor;
                this.zae = set;
                this.zaa();
                return;
            }
            Log.wtf((String)"GoogleApiManager", (String)"Received null response from onSignInSuccess", (Throwable)new Exception());
            this.zaa(new ConnectionResult(4));
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.api.internal.zai;
import com.google.android.gms.common.api.internal.zap;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.signin.zaa;
import com.google.android.gms.signin.zad;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Deprecated
public abstract class GoogleApiClient {
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    private static final Set<GoogleApiClient> zaa = Collections.newSetFromMap(new WeakHashMap());

    public static void dumpAll(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Set<GoogleApiClient> set = zaa;
        synchronized (set) {
            int n = 0;
            String string3 = String.valueOf(string2).concat("  ");
            Iterator<GoogleApiClient> iterator2 = zaa.iterator();
            while (iterator2.hasNext()) {
                GoogleApiClient googleApiClient = iterator2.next();
                printWriter.append(string2).append("GoogleApiClient#").println(n);
                googleApiClient.dump(string3, fileDescriptor, printWriter, arrstring);
                ++n;
            }
            return;
        }
    }

    public static Set<GoogleApiClient> getAllClients() {
        Set<GoogleApiClient> set = zaa;
        synchronized (set) {
            return zaa;
        }
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int n) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T t) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T t) {
        throw new UnsupportedOperationException();
    }

    public <C extends Api.Client> C getClient(Api.AnyClientKey<C> anyClientKey) {
        throw new UnsupportedOperationException();
    }

    public abstract ConnectionResult getConnectionResult(Api<?> var1);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public boolean hasApi(Api<?> api) {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(Api<?> var1);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(ConnectionCallbacks var1);

    public abstract boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener var1);

    public boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        throw new UnsupportedOperationException();
    }

    public void maybeSignOut() {
        throw new UnsupportedOperationException();
    }

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(ConnectionCallbacks var1);

    public abstract void registerConnectionFailedListener(OnConnectionFailedListener var1);

    public <L> ListenerHolder<L> registerListener(L l) {
        throw new UnsupportedOperationException();
    }

    public abstract void stopAutoManage(FragmentActivity var1);

    public abstract void unregisterConnectionCallbacks(ConnectionCallbacks var1);

    public abstract void unregisterConnectionFailedListener(OnConnectionFailedListener var1);

    public void zaa(zack zack2) {
        throw new UnsupportedOperationException();
    }

    public void zab(zack zack2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static final class Builder {
        private Account zaa;
        private final Set<Scope> zab = new HashSet<Scope>();
        private final Set<Scope> zac = new HashSet<Scope>();
        private int zad;
        private View zae;
        private String zaf;
        private String zag;
        private final Map<Api<?>, ClientSettings.zaa> zah = new ArrayMap();
        private boolean zai = false;
        private final Context zaj;
        private final Map<Api<?>, Api.ApiOptions> zak = new ArrayMap();
        private LifecycleActivity zal;
        private int zam = -1;
        private OnConnectionFailedListener zan;
        private Looper zao;
        private GoogleApiAvailability zap = GoogleApiAvailability.getInstance();
        private Api.AbstractClientBuilder<? extends zad, SignInOptions> zaq = zaa.zaa;
        private final ArrayList<ConnectionCallbacks> zar = new ArrayList();
        private final ArrayList<OnConnectionFailedListener> zas = new ArrayList();

        public Builder(Context context) {
            this.zaj = context;
            this.zao = context.getMainLooper();
            this.zaf = context.getPackageName();
            this.zag = context.getClass().getName();
        }

        public Builder(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            Preconditions.checkNotNull(connectionCallbacks, "Must provide a connected listener");
            this.zar.add(connectionCallbacks);
            Preconditions.checkNotNull(onConnectionFailedListener, "Must provide a connection failed listener");
            this.zas.add(onConnectionFailedListener);
        }

        private final <O extends Api.ApiOptions> void zaa(Api<O> api, O object, Scope ... arrscope) {
            object = new HashSet<Scope>(Preconditions.checkNotNull(api.zaa(), "Base client builder must not be null").getImpliedScopes(object));
            int n = arrscope.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.zah.put(api, new ClientSettings.zaa((Set<Scope>)object));
                    return;
                }
                object.add((Scope)arrscope[n2]);
                ++n2;
            } while (true);
        }

        public final Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> object) {
            Preconditions.checkNotNull(object, "Api must not be null");
            this.zak.put((Api<?>)object, (Api.ApiOptions)null);
            object = Preconditions.checkNotNull(((Api)object).zaa(), "Base client builder must not be null").getImpliedScopes(null);
            this.zac.addAll((Collection<Scope>)object);
            this.zab.addAll((Collection<Scope>)object);
            return this;
        }

        public final <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> object, O o) {
            Preconditions.checkNotNull(object, "Api must not be null");
            Preconditions.checkNotNull(o, "Null options are not permitted for this Api");
            this.zak.put((Api<?>)object, o);
            object = Preconditions.checkNotNull(((Api)object).zaa(), "Base client builder must not be null").getImpliedScopes(o);
            this.zac.addAll((Collection<Scope>)object);
            this.zab.addAll((Collection<Scope>)object);
            return this;
        }

        public final <O extends Api.ApiOptions.HasOptions> Builder addApiIfAvailable(Api<O> api, O o, Scope ... arrscope) {
            Preconditions.checkNotNull(api, "Api must not be null");
            Preconditions.checkNotNull(o, "Null options are not permitted for this Api");
            this.zak.put(api, o);
            this.zaa(api, o, arrscope);
            return this;
        }

        public final <T extends Api.ApiOptions.NotRequiredOptions> Builder addApiIfAvailable(Api<? extends Api.ApiOptions.NotRequiredOptions> api, Scope ... arrscope) {
            Preconditions.checkNotNull(api, "Api must not be null");
            this.zak.put(api, null);
            this.zaa(api, null, arrscope);
            return this;
        }

        public final Builder addConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            Preconditions.checkNotNull(connectionCallbacks, "Listener must not be null");
            this.zar.add(connectionCallbacks);
            return this;
        }

        public final Builder addOnConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
            Preconditions.checkNotNull(onConnectionFailedListener, "Listener must not be null");
            this.zas.add(onConnectionFailedListener);
            return this;
        }

        public final Builder addScope(Scope scope) {
            Preconditions.checkNotNull(scope, "Scope must not be null");
            this.zab.add(scope);
            return this;
        }

        public final Builder addScopeNames(String[] arrstring) {
            int n = 0;
            while (n < arrstring.length) {
                this.zab.add(new Scope(arrstring[n]));
                ++n;
            }
            return this;
        }

        /*
         * Enabled unnecessary exception pruning
         */
        public final GoogleApiClient build() {
            int n;
            Object object;
            boolean bl;
            Preconditions.checkArgument(this.zak.isEmpty() ^ true, "must call addApi() to add at least one API");
            ClientSettings clientSettings = this.buildClientSettings();
            Object object2 = null;
            Map<Api<?>, ClientSettings.zaa> map = clientSettings.zaa();
            Object object3 = new ArrayMap();
            ArrayMap arrayMap = new ArrayMap();
            ArrayList<zap> arrayList = new ArrayList<zap>();
            Iterator<Api<?>> iterator2 = this.zak.keySet().iterator();
            int n2 = 0;
            while (iterator2.hasNext()) {
                object = iterator2.next();
                Api.ApiOptions apiOptions = this.zak.get(object);
                bl = map.get(object) != null;
                object3.put(object, bl);
                zap zap2 = new zap((Api<?>)object, bl);
                arrayList.add(zap2);
                Api.AbstractClientBuilder<?, Api.ApiOptions> abstractClientBuilder = Preconditions.checkNotNull(((Api)object).zab());
                zap2 = abstractClientBuilder.buildClient(this.zaj, this.zao, clientSettings, apiOptions, zap2, zap2);
                arrayMap.put(((Api)object).zac(), (Api.Client)((Object)zap2));
                n = n2;
                if (abstractClientBuilder.getPriority() == 1) {
                    n = apiOptions != null ? 1 : 0;
                }
                n2 = n;
                if (!zap2.providesSignIn()) continue;
                if (object2 != null) {
                    object = ((Api)object).zad();
                    object2 = ((Api)object2).zad();
                    object3 = new StringBuilder(String.valueOf(object).length() + 21 + String.valueOf(object2).length());
                    ((StringBuilder)object3).append((String)object);
                    ((StringBuilder)object3).append(" cannot be used with ");
                    ((StringBuilder)object3).append((String)object2);
                    throw new IllegalStateException(((StringBuilder)object3).toString());
                }
                object2 = object;
                n2 = n;
            }
            if (object2 != null) {
                if (n2 != 0) {
                    object2 = ((Api)object2).zad();
                    object = new StringBuilder(String.valueOf(object2).length() + 82);
                    ((StringBuilder)object).append("With using ");
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append(", GamesOptions can only be specified within GoogleSignInOptions.Builder");
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                bl = this.zaa == null;
                Preconditions.checkState(bl, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", ((Api)object2).zad());
                Preconditions.checkState(this.zab.equals(this.zac), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", ((Api)object2).zad());
            }
            n = zaap.zaa(arrayMap.values(), true);
            object = new zaap(this.zaj, new ReentrantLock(), this.zao, clientSettings, this.zap, this.zaq, (Map<Api<?>, Boolean>)object3, (List<ConnectionCallbacks>)this.zar, (List<OnConnectionFailedListener>)this.zas, arrayMap, this.zam, n, arrayList);
            object2 = zaa;
            synchronized (object2) {
                zaa.add(object);
            }
            if (this.zam < 0) return object;
            zai.zaa(this.zal).zaa(this.zam, (GoogleApiClient)object, this.zan);
            return object;
        }

        public final ClientSettings buildClientSettings() {
            SignInOptions signInOptions = SignInOptions.zaa;
            if (!this.zak.containsKey(zaa.zab)) return new ClientSettings(this.zaa, this.zab, this.zah, this.zad, this.zae, this.zaf, this.zag, signInOptions, false);
            signInOptions = (SignInOptions)this.zak.get(zaa.zab);
            return new ClientSettings(this.zaa, this.zab, this.zah, this.zad, this.zae, this.zaf, this.zag, signInOptions, false);
        }

        public final Builder enableAutoManage(FragmentActivity object, int n, OnConnectionFailedListener onConnectionFailedListener) {
            object = new LifecycleActivity((Activity)object);
            boolean bl = n >= 0;
            Preconditions.checkArgument(bl, "clientId must be non-negative");
            this.zam = n;
            this.zan = onConnectionFailedListener;
            this.zal = object;
            return this;
        }

        public final Builder enableAutoManage(FragmentActivity fragmentActivity, OnConnectionFailedListener onConnectionFailedListener) {
            return this.enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        public final Builder setAccountName(String string2) {
            string2 = string2 == null ? null : new Account(string2, "com.google");
            this.zaa = string2;
            return this;
        }

        public final Builder setGravityForPopups(int n) {
            this.zad = n;
            return this;
        }

        public final Builder setHandler(Handler handler) {
            Preconditions.checkNotNull(handler, "Handler must not be null");
            this.zao = handler.getLooper();
            return this;
        }

        public final Builder setViewForPopups(View view) {
            Preconditions.checkNotNull(view, "View must not be null");
            this.zae = view;
            return this;
        }

        public final Builder useDefaultAccount() {
            return this.setAccountName(GoogleApiClient.DEFAULT_ACCOUNT);
        }
    }

    @Deprecated
    public static interface ConnectionCallbacks
    extends com.google.android.gms.common.api.internal.ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;
    }

    @Deprecated
    public static interface OnConnectionFailedListener
    extends com.google.android.gms.common.api.internal.OnConnectionFailedListener {
    }

}


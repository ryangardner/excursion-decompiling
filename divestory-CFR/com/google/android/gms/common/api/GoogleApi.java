/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  org.checkerframework.checker.initialization.qual.NotOnlyInitialized
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zabk;
import com.google.android.gms.common.api.internal.zacb;
import com.google.android.gms.common.api.internal.zax;
import com.google.android.gms.common.api.zac;
import com.google.android.gms.common.api.zad;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public class GoogleApi<O extends Api.ApiOptions>
implements HasApiKey<O> {
    private final Context zaa;
    private final String zab;
    private final Api<O> zac;
    private final O zad;
    private final ApiKey<O> zae;
    private final Looper zaf;
    private final int zag;
    @NotOnlyInitialized
    private final GoogleApiClient zah;
    private final StatusExceptionMapper zai;
    private final GoogleApiManager zaj;

    public GoogleApi(Activity activity, Api<O> object, O o, Settings settings) {
        Preconditions.checkNotNull(activity, "Null activity is not permitted.");
        Preconditions.checkNotNull(object, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zaa = activity.getApplicationContext();
        this.zab = GoogleApi.zaa((Object)activity);
        this.zac = object;
        this.zad = o;
        this.zaf = settings.zab;
        this.zae = ApiKey.getSharedApiKey(this.zac, this.zad);
        this.zah = new zabk(this);
        this.zaj = object = GoogleApiManager.zaa(this.zaa);
        this.zag = ((GoogleApiManager)object).zab();
        this.zai = settings.zaa;
        if (!(activity instanceof GoogleApiActivity) && Looper.myLooper() == Looper.getMainLooper()) {
            zax.zaa(activity, this.zaj, this.zae);
        }
        this.zaj.zaa(this);
    }

    @Deprecated
    public GoogleApi(Activity activity, Api<O> api, O o, StatusExceptionMapper statusExceptionMapper) {
        this(activity, api, o, new Settings.Builder().setMapper(statusExceptionMapper).setLooper(activity.getMainLooper()).build());
    }

    @Deprecated
    public GoogleApi(Context context, Api<O> api, O o, Looper looper, StatusExceptionMapper statusExceptionMapper) {
        this(context, api, o, new Settings.Builder().setLooper(looper).setMapper(statusExceptionMapper).build());
    }

    public GoogleApi(Context object, Api<O> api, O o, Settings settings) {
        Preconditions.checkNotNull(object, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zaa = object.getApplicationContext();
        this.zab = GoogleApi.zaa(object);
        this.zac = api;
        this.zad = o;
        this.zaf = settings.zab;
        this.zae = ApiKey.getSharedApiKey(this.zac, this.zad);
        this.zah = new zabk(this);
        object = GoogleApiManager.zaa(this.zaa);
        this.zaj = object;
        this.zag = ((GoogleApiManager)object).zab();
        this.zai = settings.zaa;
        this.zaj.zaa(this);
    }

    @Deprecated
    public GoogleApi(Context context, Api<O> api, O o, StatusExceptionMapper statusExceptionMapper) {
        this(context, api, o, new Settings.Builder().setMapper(statusExceptionMapper).build());
    }

    private final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zaa(int n, T t) {
        ((BasePendingResult)t).zab();
        this.zaj.zaa(this, n, (BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>)t);
        return t;
    }

    private final <TResult, A extends Api.AnyClient> Task<TResult> zaa(int n, TaskApiCall<A, TResult> taskApiCall) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zaj.zaa(this, n, taskApiCall, taskCompletionSource, this.zai);
        return taskCompletionSource.getTask();
    }

    private static String zaa(Object object) {
        if (!PlatformVersion.isAtLeastR()) return null;
        try {
            return (String)Context.class.getMethod("getAttributionTag", new Class[0]).invoke(object, new Object[0]);
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            return null;
        }
    }

    public GoogleApiClient asGoogleApiClient() {
        return this.zah;
    }

    protected ClientSettings.Builder createClientSettingsBuilder() {
        ClientSettings.Builder builder = new ClientSettings.Builder();
        Object object = this.zad;
        object = object instanceof Api.ApiOptions.HasGoogleSignInAccountOptions && (object = ((Api.ApiOptions.HasGoogleSignInAccountOptions)object).getGoogleSignInAccount()) != null ? ((GoogleSignInAccount)object).getAccount() : ((object = this.zad) instanceof Api.ApiOptions.HasAccountOptions ? ((Api.ApiOptions.HasAccountOptions)object).getAccount() : null);
        builder = builder.zaa((Account)object);
        object = this.zad;
        if (object instanceof Api.ApiOptions.HasGoogleSignInAccountOptions && (object = ((Api.ApiOptions.HasGoogleSignInAccountOptions)object).getGoogleSignInAccount()) != null) {
            object = ((GoogleSignInAccount)object).getRequestedScopes();
            return builder.zaa((Collection<Scope>)object).zaa(this.zaa.getClass().getName()).setRealClientPackageName(this.zaa.getPackageName());
        }
        object = Collections.emptySet();
        return builder.zaa((Collection<Scope>)object).zaa(this.zaa.getClass().getName()).setRealClientPackageName(this.zaa.getPackageName());
    }

    protected Task<Boolean> disconnectService() {
        return this.zaj.zab(this);
    }

    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doBestEffortWrite(T t) {
        return this.zaa(2, t);
    }

    public <TResult, A extends Api.AnyClient> Task<TResult> doBestEffortWrite(TaskApiCall<A, TResult> taskApiCall) {
        return this.zaa(2, taskApiCall);
    }

    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doRead(T t) {
        return this.zaa(0, t);
    }

    public <TResult, A extends Api.AnyClient> Task<TResult> doRead(TaskApiCall<A, TResult> taskApiCall) {
        return this.zaa(0, taskApiCall);
    }

    @Deprecated
    public <A extends Api.AnyClient, T extends RegisterListenerMethod<A, ?>, U extends UnregisterListenerMethod<A, ?>> Task<Void> doRegisterEventListener(T t, U u) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(u);
        Preconditions.checkNotNull(((RegisterListenerMethod)t).getListenerKey(), "Listener has already been released.");
        Preconditions.checkNotNull(((UnregisterListenerMethod)u).getListenerKey(), "Listener has already been released.");
        Preconditions.checkArgument(Objects.equal(((RegisterListenerMethod)t).getListenerKey(), ((UnregisterListenerMethod)u).getListenerKey()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
        return this.zaj.zaa(this, (RegisterListenerMethod<Api.AnyClient, ?>)t, (UnregisterListenerMethod<Api.AnyClient, ?>)u, zad.zaa);
    }

    public <A extends Api.AnyClient> Task<Void> doRegisterEventListener(RegistrationMethods<A, ?> registrationMethods) {
        Preconditions.checkNotNull(registrationMethods);
        Preconditions.checkNotNull(registrationMethods.zaa.getListenerKey(), "Listener has already been released.");
        Preconditions.checkNotNull(registrationMethods.zab.getListenerKey(), "Listener has already been released.");
        return this.zaj.zaa(this, registrationMethods.zaa, registrationMethods.zab, registrationMethods.zac);
    }

    public Task<Boolean> doUnregisterEventListener(ListenerHolder.ListenerKey<?> listenerKey) {
        Preconditions.checkNotNull(listenerKey, "Listener key cannot be null.");
        return this.zaj.zaa(this, listenerKey);
    }

    public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doWrite(T t) {
        return this.zaa(1, t);
    }

    public <TResult, A extends Api.AnyClient> Task<TResult> doWrite(TaskApiCall<A, TResult> taskApiCall) {
        return this.zaa(1, taskApiCall);
    }

    @Override
    public ApiKey<O> getApiKey() {
        return this.zae;
    }

    public O getApiOptions() {
        return this.zad;
    }

    public Context getApplicationContext() {
        return this.zaa;
    }

    protected String getContextAttributionTag() {
        return this.zab;
    }

    @Deprecated
    protected String getContextFeatureId() {
        return this.zab;
    }

    public Looper getLooper() {
        return this.zaf;
    }

    public <L> ListenerHolder<L> registerListener(L l, String string2) {
        return ListenerHolders.createListenerHolder(l, this.zaf, string2);
    }

    public final int zaa() {
        return this.zag;
    }

    public final Api.Client zaa(Looper looper, GoogleApiManager.zaa<O> zaa2) {
        ClientSettings clientSettings = this.createClientSettingsBuilder().build();
        return Preconditions.checkNotNull(this.zac.zab()).buildClient(this.zaa, looper, clientSettings, this.zad, zaa2, zaa2);
    }

    public final zacb zaa(Context context, Handler handler) {
        return new zacb(context, handler, this.createClientSettingsBuilder().build());
    }

    public static class Settings {
        public static final Settings DEFAULT_SETTINGS = new Builder().build();
        public final StatusExceptionMapper zaa;
        public final Looper zab;

        private Settings(StatusExceptionMapper statusExceptionMapper, Account account, Looper looper) {
            this.zaa = statusExceptionMapper;
            this.zab = looper;
        }

        /* synthetic */ Settings(StatusExceptionMapper statusExceptionMapper, Account account, Looper looper, zac zac2) {
            this(statusExceptionMapper, null, looper);
        }

        public static class Builder {
            private StatusExceptionMapper zaa;
            private Looper zab;

            public Settings build() {
                if (this.zaa == null) {
                    this.zaa = new ApiExceptionMapper();
                }
                if (this.zab != null) return new Settings(this.zaa, null, this.zab, null);
                this.zab = Looper.getMainLooper();
                return new Settings(this.zaa, null, this.zab, null);
            }

            public Builder setLooper(Looper looper) {
                Preconditions.checkNotNull(looper, "Looper must not be null.");
                this.zab = looper;
                return this;
            }

            public Builder setMapper(StatusExceptionMapper statusExceptionMapper) {
                Preconditions.checkNotNull(statusExceptionMapper, "StatusExceptionMapper must not be null.");
                this.zaa = statusExceptionMapper;
                return this;
            }
        }

    }

}

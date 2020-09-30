package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.BaseImplementation;
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
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public class GoogleApi<O extends Api.ApiOptions> implements HasApiKey<O> {
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

   public GoogleApi(Activity var1, Api<O> var2, O var3, GoogleApi.Settings var4) {
      Preconditions.checkNotNull(var1, "Null activity is not permitted.");
      Preconditions.checkNotNull(var2, "Api must not be null.");
      Preconditions.checkNotNull(var4, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
      this.zaa = var1.getApplicationContext();
      this.zab = zaa(var1);
      this.zac = var2;
      this.zad = var3;
      this.zaf = var4.zab;
      this.zae = ApiKey.getSharedApiKey(this.zac, this.zad);
      this.zah = new zabk(this);
      GoogleApiManager var5 = GoogleApiManager.zaa(this.zaa);
      this.zaj = var5;
      this.zag = var5.zab();
      this.zai = var4.zaa;
      if (!(var1 instanceof GoogleApiActivity) && Looper.myLooper() == Looper.getMainLooper()) {
         zax.zaa(var1, this.zaj, this.zae);
      }

      this.zaj.zaa(this);
   }

   @Deprecated
   public GoogleApi(Activity var1, Api<O> var2, O var3, StatusExceptionMapper var4) {
      this(var1, var2, var3, (new GoogleApi.Settings.Builder()).setMapper(var4).setLooper(var1.getMainLooper()).build());
   }

   @Deprecated
   public GoogleApi(Context var1, Api<O> var2, O var3, Looper var4, StatusExceptionMapper var5) {
      this(var1, var2, var3, (new GoogleApi.Settings.Builder()).setLooper(var4).setMapper(var5).build());
   }

   public GoogleApi(Context var1, Api<O> var2, O var3, GoogleApi.Settings var4) {
      Preconditions.checkNotNull(var1, "Null context is not permitted.");
      Preconditions.checkNotNull(var2, "Api must not be null.");
      Preconditions.checkNotNull(var4, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
      this.zaa = var1.getApplicationContext();
      this.zab = zaa(var1);
      this.zac = var2;
      this.zad = var3;
      this.zaf = var4.zab;
      this.zae = ApiKey.getSharedApiKey(this.zac, this.zad);
      this.zah = new zabk(this);
      GoogleApiManager var5 = GoogleApiManager.zaa(this.zaa);
      this.zaj = var5;
      this.zag = var5.zab();
      this.zai = var4.zaa;
      this.zaj.zaa(this);
   }

   @Deprecated
   public GoogleApi(Context var1, Api<O> var2, O var3, StatusExceptionMapper var4) {
      this(var1, var2, var3, (new GoogleApi.Settings.Builder()).setMapper(var4).build());
   }

   private final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zaa(int var1, T var2) {
      var2.zab();
      this.zaj.zaa(this, var1, var2);
      return var2;
   }

   private final <TResult, A extends Api.AnyClient> Task<TResult> zaa(int var1, TaskApiCall<A, TResult> var2) {
      TaskCompletionSource var3 = new TaskCompletionSource();
      this.zaj.zaa(this, var1, var2, var3, this.zai);
      return var3.getTask();
   }

   private static String zaa(Object var0) {
      if (PlatformVersion.isAtLeastR()) {
         try {
            String var2 = (String)Context.class.getMethod("getAttributionTag").invoke(var0);
            return var2;
         } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var1) {
         }
      }

      return null;
   }

   public GoogleApiClient asGoogleApiClient() {
      return this.zah;
   }

   protected ClientSettings.Builder createClientSettingsBuilder() {
      ClientSettings.Builder var1;
      Api.ApiOptions var2;
      GoogleSignInAccount var3;
      Account var4;
      label24: {
         var1 = new ClientSettings.Builder();
         var2 = this.zad;
         if (var2 instanceof Api.ApiOptions.HasGoogleSignInAccountOptions) {
            var3 = ((Api.ApiOptions.HasGoogleSignInAccountOptions)var2).getGoogleSignInAccount();
            if (var3 != null) {
               var4 = var3.getAccount();
               break label24;
            }
         }

         var2 = this.zad;
         if (var2 instanceof Api.ApiOptions.HasAccountOptions) {
            var4 = ((Api.ApiOptions.HasAccountOptions)var2).getAccount();
         } else {
            var4 = null;
         }
      }

      var1 = var1.zaa(var4);
      var2 = this.zad;
      Set var5;
      if (var2 instanceof Api.ApiOptions.HasGoogleSignInAccountOptions) {
         var3 = ((Api.ApiOptions.HasGoogleSignInAccountOptions)var2).getGoogleSignInAccount();
         if (var3 != null) {
            var5 = var3.getRequestedScopes();
            return var1.zaa((Collection)var5).zaa(this.zaa.getClass().getName()).setRealClientPackageName(this.zaa.getPackageName());
         }
      }

      var5 = Collections.emptySet();
      return var1.zaa((Collection)var5).zaa(this.zaa.getClass().getName()).setRealClientPackageName(this.zaa.getPackageName());
   }

   protected Task<Boolean> disconnectService() {
      return this.zaj.zab(this);
   }

   public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doBestEffortWrite(T var1) {
      return this.zaa(2, (BaseImplementation.ApiMethodImpl)var1);
   }

   public <TResult, A extends Api.AnyClient> Task<TResult> doBestEffortWrite(TaskApiCall<A, TResult> var1) {
      return this.zaa(2, (TaskApiCall)var1);
   }

   public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doRead(T var1) {
      return this.zaa(0, (BaseImplementation.ApiMethodImpl)var1);
   }

   public <TResult, A extends Api.AnyClient> Task<TResult> doRead(TaskApiCall<A, TResult> var1) {
      return this.zaa(0, (TaskApiCall)var1);
   }

   @Deprecated
   public <A extends Api.AnyClient, T extends RegisterListenerMethod<A, ?>, U extends UnregisterListenerMethod<A, ?>> Task<Void> doRegisterEventListener(T var1, U var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var1.getListenerKey(), "Listener has already been released.");
      Preconditions.checkNotNull(var2.getListenerKey(), "Listener has already been released.");
      Preconditions.checkArgument(Objects.equal(var1.getListenerKey(), var2.getListenerKey()), "Listener registration and unregistration methods must be constructed with the same ListenerHolder.");
      return this.zaj.zaa(this, var1, var2, com.google.android.gms.common.api.zad.zaa);
   }

   public <A extends Api.AnyClient> Task<Void> doRegisterEventListener(RegistrationMethods<A, ?> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var1.zaa.getListenerKey(), "Listener has already been released.");
      Preconditions.checkNotNull(var1.zab.getListenerKey(), "Listener has already been released.");
      return this.zaj.zaa(this, var1.zaa, var1.zab, var1.zac);
   }

   public Task<Boolean> doUnregisterEventListener(ListenerHolder.ListenerKey<?> var1) {
      Preconditions.checkNotNull(var1, "Listener key cannot be null.");
      return this.zaj.zaa(this, var1);
   }

   public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T doWrite(T var1) {
      return this.zaa(1, (BaseImplementation.ApiMethodImpl)var1);
   }

   public <TResult, A extends Api.AnyClient> Task<TResult> doWrite(TaskApiCall<A, TResult> var1) {
      return this.zaa(1, (TaskApiCall)var1);
   }

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

   public <L> ListenerHolder<L> registerListener(L var1, String var2) {
      return ListenerHolders.createListenerHolder(var1, this.zaf, var2);
   }

   public final int zaa() {
      return this.zag;
   }

   public final Api.Client zaa(Looper var1, GoogleApiManager.zaa<O> var2) {
      ClientSettings var3 = this.createClientSettingsBuilder().build();
      return ((Api.AbstractClientBuilder)Preconditions.checkNotNull(this.zac.zab())).buildClient(this.zaa, var1, var3, this.zad, (GoogleApiClient.ConnectionCallbacks)var2, (GoogleApiClient.OnConnectionFailedListener)var2);
   }

   public final zacb zaa(Context var1, Handler var2) {
      return new zacb(var1, var2, this.createClientSettingsBuilder().build());
   }

   public static class Settings {
      public static final GoogleApi.Settings DEFAULT_SETTINGS = (new GoogleApi.Settings.Builder()).build();
      public final StatusExceptionMapper zaa;
      public final Looper zab;

      private Settings(StatusExceptionMapper var1, Account var2, Looper var3) {
         this.zaa = var1;
         this.zab = var3;
      }

      // $FF: synthetic method
      Settings(StatusExceptionMapper var1, Account var2, Looper var3, zac var4) {
         this(var1, (Account)null, var3);
      }

      public static class Builder {
         private StatusExceptionMapper zaa;
         private Looper zab;

         public GoogleApi.Settings build() {
            if (this.zaa == null) {
               this.zaa = new ApiExceptionMapper();
            }

            if (this.zab == null) {
               this.zab = Looper.getMainLooper();
            }

            return new GoogleApi.Settings(this.zaa, (Account)null, this.zab, (zac)null);
         }

         public GoogleApi.Settings.Builder setLooper(Looper var1) {
            Preconditions.checkNotNull(var1, "Looper must not be null.");
            this.zab = var1;
            return this;
         }

         public GoogleApi.Settings.Builder setMapper(StatusExceptionMapper var1) {
            Preconditions.checkNotNull(var1, "StatusExceptionMapper must not be null.");
            this.zaa = var1;
            return this;
         }
      }
   }
}

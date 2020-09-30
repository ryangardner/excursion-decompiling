package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends Api.ApiOptions> {
   private final Api.AbstractClientBuilder<?, O> zaa;
   private final Api.ClientKey<?> zab;
   private final String zac;

   public <C extends Api.Client> Api(String var1, Api.AbstractClientBuilder<C, O> var2, Api.ClientKey<C> var3) {
      Preconditions.checkNotNull(var2, "Cannot construct an Api with a null ClientBuilder");
      Preconditions.checkNotNull(var3, "Cannot construct an Api with a null ClientKey");
      this.zac = var1;
      this.zaa = var2;
      this.zab = var3;
   }

   public final Api.BaseClientBuilder<?, O> zaa() {
      return this.zaa;
   }

   public final Api.AbstractClientBuilder<?, O> zab() {
      return this.zaa;
   }

   public final Api.AnyClientKey<?> zac() {
      return this.zab;
   }

   public final String zad() {
      return this.zac;
   }

   public static class AbstractClientBuilder<T extends Api.Client, O> extends Api.BaseClientBuilder<T, O> {
      @Deprecated
      public T buildClient(Context var1, Looper var2, ClientSettings var3, O var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
         return this.buildClient(var1, var2, var3, var4, (ConnectionCallbacks)var5, (OnConnectionFailedListener)var6);
      }

      public T buildClient(Context var1, Looper var2, ClientSettings var3, O var4, ConnectionCallbacks var5, OnConnectionFailedListener var6) {
         throw new UnsupportedOperationException("buildClient must be implemented");
      }
   }

   public interface AnyClient {
   }

   public static class AnyClientKey<C extends Api.AnyClient> {
   }

   public interface ApiOptions {
      Api.ApiOptions.NoOptions NO_OPTIONS = new Api.ApiOptions.NoOptions((zaa)null);

      public interface HasAccountOptions extends Api.ApiOptions.HasOptions, Api.ApiOptions.NotRequiredOptions {
         Account getAccount();
      }

      public interface HasGoogleSignInAccountOptions extends Api.ApiOptions.HasOptions {
         GoogleSignInAccount getGoogleSignInAccount();
      }

      public interface HasOptions extends Api.ApiOptions {
      }

      public static final class NoOptions implements Api.ApiOptions.NotRequiredOptions {
         private NoOptions() {
         }

         // $FF: synthetic method
         NoOptions(zaa var1) {
            this();
         }
      }

      public interface NotRequiredOptions extends Api.ApiOptions {
      }

      public interface Optional extends Api.ApiOptions.HasOptions, Api.ApiOptions.NotRequiredOptions {
      }
   }

   public static class BaseClientBuilder<T extends Api.AnyClient, O> {
      public static final int API_PRIORITY_GAMES = 1;
      public static final int API_PRIORITY_OTHER = Integer.MAX_VALUE;
      public static final int API_PRIORITY_PLUS = 2;

      public List<Scope> getImpliedScopes(O var1) {
         return Collections.emptyList();
      }

      public int getPriority() {
         return Integer.MAX_VALUE;
      }
   }

   public interface Client extends Api.AnyClient {
      void connect(BaseGmsClient.ConnectionProgressReportCallbacks var1);

      void disconnect();

      void disconnect(String var1);

      void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

      Feature[] getAvailableFeatures();

      String getEndpointPackageName();

      String getLastDisconnectMessage();

      int getMinApkVersion();

      void getRemoteService(IAccountAccessor var1, Set<Scope> var2);

      Feature[] getRequiredFeatures();

      Set<Scope> getScopesForConnectionlessNonSignIn();

      IBinder getServiceBrokerBinder();

      Intent getSignInIntent();

      boolean isConnected();

      boolean isConnecting();

      void onUserSignOut(BaseGmsClient.SignOutCallbacks var1);

      boolean providesSignIn();

      boolean requiresAccount();

      boolean requiresGooglePlayServices();

      boolean requiresSignIn();
   }

   public static final class ClientKey<C extends Api.Client> extends Api.AnyClientKey<C> {
   }
}

package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

@Deprecated
public abstract class GoogleApiClient {
   public static final String DEFAULT_ACCOUNT = "<<default account>>";
   public static final int SIGN_IN_MODE_OPTIONAL = 2;
   public static final int SIGN_IN_MODE_REQUIRED = 1;
   private static final Set<GoogleApiClient> zaa = Collections.newSetFromMap(new WeakHashMap());

   public static void dumpAll(String var0, FileDescriptor var1, PrintWriter var2, String[] var3) {
      Set var4 = zaa;
      synchronized(var4){}
      int var5 = 0;

      Throwable var10000;
      boolean var10001;
      label198: {
         String var6;
         Iterator var7;
         try {
            var6 = String.valueOf(var0).concat("  ");
            var7 = zaa.iterator();
         } catch (Throwable var27) {
            var10000 = var27;
            var10001 = false;
            break label198;
         }

         while(true) {
            label195: {
               try {
                  if (var7.hasNext()) {
                     GoogleApiClient var8 = (GoogleApiClient)var7.next();
                     var2.append(var0).append("GoogleApiClient#").println(var5);
                     var8.dump(var6, var1, var2, var3);
                     break label195;
                  }
               } catch (Throwable var28) {
                  var10000 = var28;
                  var10001 = false;
                  break;
               }

               try {
                  return;
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  break;
               }
            }

            ++var5;
         }
      }

      while(true) {
         Throwable var29 = var10000;

         try {
            throw var29;
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            continue;
         }
      }
   }

   public static Set<GoogleApiClient> getAllClients() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static Set zaa() {
      return zaa;
   }

   public abstract ConnectionResult blockingConnect();

   public abstract ConnectionResult blockingConnect(long var1, TimeUnit var3);

   public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

   public abstract void connect();

   public void connect(int var1) {
      throw new UnsupportedOperationException();
   }

   public abstract void disconnect();

   public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   public <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1) {
      throw new UnsupportedOperationException();
   }

   public <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T var1) {
      throw new UnsupportedOperationException();
   }

   public <C extends Api.Client> C getClient(Api.AnyClientKey<C> var1) {
      throw new UnsupportedOperationException();
   }

   public abstract ConnectionResult getConnectionResult(Api<?> var1);

   public Context getContext() {
      throw new UnsupportedOperationException();
   }

   public Looper getLooper() {
      throw new UnsupportedOperationException();
   }

   public boolean hasApi(Api<?> var1) {
      throw new UnsupportedOperationException();
   }

   public abstract boolean hasConnectedApi(Api<?> var1);

   public abstract boolean isConnected();

   public abstract boolean isConnecting();

   public abstract boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks var1);

   public abstract boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener var1);

   public boolean maybeSignIn(SignInConnectionListener var1) {
      throw new UnsupportedOperationException();
   }

   public void maybeSignOut() {
      throw new UnsupportedOperationException();
   }

   public abstract void reconnect();

   public abstract void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1);

   public abstract void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1);

   public <L> ListenerHolder<L> registerListener(L var1) {
      throw new UnsupportedOperationException();
   }

   public abstract void stopAutoManage(FragmentActivity var1);

   public abstract void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1);

   public abstract void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1);

   public void zaa(zack var1) {
      throw new UnsupportedOperationException();
   }

   public void zab(zack var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public static final class Builder {
      private Account zaa;
      private final Set<Scope> zab;
      private final Set<Scope> zac;
      private int zad;
      private View zae;
      private String zaf;
      private String zag;
      private final Map<Api<?>, ClientSettings.zaa> zah;
      private boolean zai;
      private final Context zaj;
      private final Map<Api<?>, Api.ApiOptions> zak;
      private LifecycleActivity zal;
      private int zam;
      private GoogleApiClient.OnConnectionFailedListener zan;
      private Looper zao;
      private GoogleApiAvailability zap;
      private Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zaq;
      private final ArrayList<GoogleApiClient.ConnectionCallbacks> zar;
      private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zas;

      public Builder(Context var1) {
         this.zab = new HashSet();
         this.zac = new HashSet();
         this.zah = new ArrayMap();
         this.zai = false;
         this.zak = new ArrayMap();
         this.zam = -1;
         this.zap = GoogleApiAvailability.getInstance();
         this.zaq = com.google.android.gms.signin.zaa.zaa;
         this.zar = new ArrayList();
         this.zas = new ArrayList();
         this.zaj = var1;
         this.zao = var1.getMainLooper();
         this.zaf = var1.getPackageName();
         this.zag = var1.getClass().getName();
      }

      public Builder(Context var1, GoogleApiClient.ConnectionCallbacks var2, GoogleApiClient.OnConnectionFailedListener var3) {
         this(var1);
         Preconditions.checkNotNull(var2, "Must provide a connected listener");
         this.zar.add(var2);
         Preconditions.checkNotNull(var3, "Must provide a connection failed listener");
         this.zas.add(var3);
      }

      private final <O extends Api.ApiOptions> void zaa(Api<O> var1, O var2, Scope... var3) {
         HashSet var6 = new HashSet(((Api.BaseClientBuilder)Preconditions.checkNotNull(var1.zaa(), "Base client builder must not be null")).getImpliedScopes(var2));
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            var6.add(var3[var5]);
         }

         this.zah.put(var1, new ClientSettings.zaa(var6));
      }

      public final GoogleApiClient.Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> var1) {
         Preconditions.checkNotNull(var1, "Api must not be null");
         this.zak.put(var1, (Object)null);
         List var2 = ((Api.BaseClientBuilder)Preconditions.checkNotNull(var1.zaa(), "Base client builder must not be null")).getImpliedScopes((Object)null);
         this.zac.addAll(var2);
         this.zab.addAll(var2);
         return this;
      }

      public final <O extends Api.ApiOptions.HasOptions> GoogleApiClient.Builder addApi(Api<O> var1, O var2) {
         Preconditions.checkNotNull(var1, "Api must not be null");
         Preconditions.checkNotNull(var2, "Null options are not permitted for this Api");
         this.zak.put(var1, var2);
         List var3 = ((Api.BaseClientBuilder)Preconditions.checkNotNull(var1.zaa(), "Base client builder must not be null")).getImpliedScopes(var2);
         this.zac.addAll(var3);
         this.zab.addAll(var3);
         return this;
      }

      public final <O extends Api.ApiOptions.HasOptions> GoogleApiClient.Builder addApiIfAvailable(Api<O> var1, O var2, Scope... var3) {
         Preconditions.checkNotNull(var1, "Api must not be null");
         Preconditions.checkNotNull(var2, "Null options are not permitted for this Api");
         this.zak.put(var1, var2);
         this.zaa(var1, var2, var3);
         return this;
      }

      public final <T extends Api.ApiOptions.NotRequiredOptions> GoogleApiClient.Builder addApiIfAvailable(Api<? extends Api.ApiOptions.NotRequiredOptions> var1, Scope... var2) {
         Preconditions.checkNotNull(var1, "Api must not be null");
         this.zak.put(var1, (Object)null);
         this.zaa(var1, (Api.ApiOptions)null, var2);
         return this;
      }

      public final GoogleApiClient.Builder addConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
         Preconditions.checkNotNull(var1, "Listener must not be null");
         this.zar.add(var1);
         return this;
      }

      public final GoogleApiClient.Builder addOnConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
         Preconditions.checkNotNull(var1, "Listener must not be null");
         this.zas.add(var1);
         return this;
      }

      public final GoogleApiClient.Builder addScope(Scope var1) {
         Preconditions.checkNotNull(var1, "Scope must not be null");
         this.zab.add(var1);
         return this;
      }

      public final GoogleApiClient.Builder addScopeNames(String[] var1) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            this.zab.add(new Scope(var1[var2]));
         }

         return this;
      }

      public final GoogleApiClient build() {
         // $FF: Couldn't be decompiled
      }

      public final ClientSettings buildClientSettings() {
         SignInOptions var1 = SignInOptions.zaa;
         if (this.zak.containsKey(com.google.android.gms.signin.zaa.zab)) {
            var1 = (SignInOptions)this.zak.get(com.google.android.gms.signin.zaa.zab);
         }

         return new ClientSettings(this.zaa, this.zab, this.zah, this.zad, this.zae, this.zaf, this.zag, var1, false);
      }

      public final GoogleApiClient.Builder enableAutoManage(FragmentActivity var1, int var2, GoogleApiClient.OnConnectionFailedListener var3) {
         LifecycleActivity var5 = new LifecycleActivity(var1);
         boolean var4;
         if (var2 >= 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "clientId must be non-negative");
         this.zam = var2;
         this.zan = var3;
         this.zal = var5;
         return this;
      }

      public final GoogleApiClient.Builder enableAutoManage(FragmentActivity var1, GoogleApiClient.OnConnectionFailedListener var2) {
         return this.enableAutoManage(var1, 0, var2);
      }

      public final GoogleApiClient.Builder setAccountName(String var1) {
         Account var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = new Account(var1, "com.google");
         }

         this.zaa = var2;
         return this;
      }

      public final GoogleApiClient.Builder setGravityForPopups(int var1) {
         this.zad = var1;
         return this;
      }

      public final GoogleApiClient.Builder setHandler(Handler var1) {
         Preconditions.checkNotNull(var1, "Handler must not be null");
         this.zao = var1.getLooper();
         return this;
      }

      public final GoogleApiClient.Builder setViewForPopups(View var1) {
         Preconditions.checkNotNull(var1, "View must not be null");
         this.zae = var1;
         return this;
      }

      public final GoogleApiClient.Builder useDefaultAccount() {
         return this.setAccountName("<<default account>>");
      }
   }

   @Deprecated
   public interface ConnectionCallbacks extends com.google.android.gms.common.api.internal.ConnectionCallbacks {
      int CAUSE_NETWORK_LOST = 2;
      int CAUSE_SERVICE_DISCONNECTED = 1;
   }

   @Deprecated
   public interface OnConnectionFailedListener extends com.google.android.gms.common.api.internal.OnConnectionFailedListener {
   }
}

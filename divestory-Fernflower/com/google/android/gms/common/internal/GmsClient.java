package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public abstract class GmsClient<T extends IInterface> extends BaseGmsClient<T> implements Api.Client, zak {
   private final ClientSettings zaa;
   private final Set<Scope> zab;
   private final Account zac;

   protected GmsClient(Context var1, Handler var2, int var3, ClientSettings var4) {
      this(var1, (Handler)var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailability.getInstance(), var3, var4, (GoogleApiClient.ConnectionCallbacks)null, (GoogleApiClient.OnConnectionFailedListener)null);
   }

   @Deprecated
   private GmsClient(Context var1, Handler var2, GmsClientSupervisor var3, GoogleApiAvailability var4, int var5, ClientSettings var6, GoogleApiClient.ConnectionCallbacks var7, GoogleApiClient.OnConnectionFailedListener var8) {
      this(var1, (Handler)var2, var3, var4, var5, var6, (ConnectionCallbacks)null, (OnConnectionFailedListener)null);
   }

   private GmsClient(Context var1, Handler var2, GmsClientSupervisor var3, GoogleApiAvailability var4, int var5, ClientSettings var6, ConnectionCallbacks var7, OnConnectionFailedListener var8) {
      super(var1, var2, var3, var4, var5, zaa((ConnectionCallbacks)null), zaa((OnConnectionFailedListener)null));
      this.zaa = (ClientSettings)Preconditions.checkNotNull(var6);
      this.zac = var6.getAccount();
      this.zab = this.zaa(var6.getAllRequestedScopes());
   }

   protected GmsClient(Context var1, Looper var2, int var3, ClientSettings var4) {
      this(var1, (Looper)var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailability.getInstance(), var3, var4, (GoogleApiClient.ConnectionCallbacks)null, (GoogleApiClient.OnConnectionFailedListener)null);
   }

   @Deprecated
   protected GmsClient(Context var1, Looper var2, int var3, ClientSettings var4, GoogleApiClient.ConnectionCallbacks var5, GoogleApiClient.OnConnectionFailedListener var6) {
      this(var1, var2, var3, var4, (ConnectionCallbacks)var5, (OnConnectionFailedListener)var6);
   }

   protected GmsClient(Context var1, Looper var2, int var3, ClientSettings var4, ConnectionCallbacks var5, OnConnectionFailedListener var6) {
      this(var1, var2, GmsClientSupervisor.getInstance(var1), GoogleApiAvailability.getInstance(), var3, var4, (ConnectionCallbacks)Preconditions.checkNotNull(var5), (OnConnectionFailedListener)Preconditions.checkNotNull(var6));
   }

   private GmsClient(Context var1, Looper var2, GmsClientSupervisor var3, GoogleApiAvailability var4, int var5, ClientSettings var6, GoogleApiClient.ConnectionCallbacks var7, GoogleApiClient.OnConnectionFailedListener var8) {
      this(var1, (Looper)var2, var3, var4, var5, var6, (ConnectionCallbacks)null, (OnConnectionFailedListener)null);
   }

   private GmsClient(Context var1, Looper var2, GmsClientSupervisor var3, GoogleApiAvailability var4, int var5, ClientSettings var6, ConnectionCallbacks var7, OnConnectionFailedListener var8) {
      super(var1, var2, var3, var4, var5, zaa(var7), zaa(var8), var6.zab());
      this.zaa = var6;
      this.zac = var6.getAccount();
      this.zab = this.zaa(var6.getAllRequestedScopes());
   }

   private static BaseGmsClient.BaseConnectionCallbacks zaa(ConnectionCallbacks var0) {
      return var0 == null ? null : new zag(var0);
   }

   private static BaseGmsClient.BaseOnConnectionFailedListener zaa(OnConnectionFailedListener var0) {
      return var0 == null ? null : new zai(var0);
   }

   private final Set<Scope> zaa(Set<Scope> var1) {
      Set var2 = this.validateScopes(var1);
      Iterator var3 = var2.iterator();

      do {
         if (!var3.hasNext()) {
            return var2;
         }
      } while(var1.contains((Scope)var3.next()));

      throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
   }

   public final Account getAccount() {
      return this.zac;
   }

   protected final ClientSettings getClientSettings() {
      return this.zaa;
   }

   public Feature[] getRequiredFeatures() {
      return new Feature[0];
   }

   protected final Set<Scope> getScopes() {
      return this.zab;
   }

   public Set<Scope> getScopesForConnectionlessNonSignIn() {
      return this.requiresSignIn() ? this.zab : Collections.emptySet();
   }

   protected Set<Scope> validateScopes(Set<Scope> var1) {
      return var1;
   }
}

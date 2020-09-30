package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.util.Set;

public final class zacb extends com.google.android.gms.signin.internal.zad implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
   private static Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zaa;
   private final Context zab;
   private final Handler zac;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zad;
   private Set<Scope> zae;
   private ClientSettings zaf;
   private com.google.android.gms.signin.zad zag;
   private zace zah;

   static {
      zaa = com.google.android.gms.signin.zaa.zaa;
   }

   public zacb(Context var1, Handler var2, ClientSettings var3) {
      this(var1, var2, var3, zaa);
   }

   private zacb(Context var1, Handler var2, ClientSettings var3, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> var4) {
      this.zab = var1;
      this.zac = var2;
      this.zaf = (ClientSettings)Preconditions.checkNotNull(var3, "ClientSettings must not be null");
      this.zae = var3.getRequiredScopes();
      this.zad = var4;
   }

   // $FF: synthetic method
   static zace zaa(zacb var0) {
      return var0.zah;
   }

   // $FF: synthetic method
   static void zaa(zacb var0, com.google.android.gms.signin.internal.zam var1) {
      var0.zab(var1);
   }

   private final void zab(com.google.android.gms.signin.internal.zam var1) {
      ConnectionResult var2 = var1.zaa();
      if (var2.isSuccess()) {
         com.google.android.gms.common.internal.zas var5 = (com.google.android.gms.common.internal.zas)Preconditions.checkNotNull(var1.zab());
         ConnectionResult var4 = var5.zab();
         if (!var4.isSuccess()) {
            String var3 = String.valueOf(var4);
            StringBuilder var6 = new StringBuilder(String.valueOf(var3).length() + 48);
            var6.append("Sign-in succeeded with resolve account failure: ");
            var6.append(var3);
            Log.wtf("SignInCoordinator", var6.toString(), new Exception());
            this.zah.zaa(var4);
            this.zag.disconnect();
            return;
         }

         this.zah.zaa(var5.zaa(), this.zae);
      } else {
         this.zah.zaa(var2);
      }

      this.zag.disconnect();
   }

   public final void onConnected(Bundle var1) {
      this.zag.zaa(this);
   }

   public final void onConnectionFailed(ConnectionResult var1) {
      this.zah.zaa(var1);
   }

   public final void onConnectionSuspended(int var1) {
      this.zag.disconnect();
   }

   public final void zaa() {
      com.google.android.gms.signin.zad var1 = this.zag;
      if (var1 != null) {
         var1.disconnect();
      }

   }

   public final void zaa(zace var1) {
      com.google.android.gms.signin.zad var2 = this.zag;
      if (var2 != null) {
         var2.disconnect();
      }

      this.zaf.zaa(System.identityHashCode(this));
      Api.AbstractClientBuilder var3 = this.zad;
      Context var4 = this.zab;
      Looper var7 = this.zac.getLooper();
      ClientSettings var5 = this.zaf;
      this.zag = (com.google.android.gms.signin.zad)var3.buildClient(var4, var7, var5, var5.zac(), (GoogleApiClient.ConnectionCallbacks)this, (GoogleApiClient.OnConnectionFailedListener)this);
      this.zah = var1;
      Set var6 = this.zae;
      if (var6 != null && !var6.isEmpty()) {
         this.zag.zab();
      } else {
         this.zac.post(new zacd(this));
      }
   }

   public final void zaa(com.google.android.gms.signin.internal.zam var1) {
      this.zac.post(new zacc(this, var1));
   }
}

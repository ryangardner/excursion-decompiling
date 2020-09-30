package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zar;
import com.google.android.gms.signin.SignInOptions;

public class SignInClientImpl extends GmsClient<zae> implements com.google.android.gms.signin.zad {
   private final boolean zaa;
   private final ClientSettings zab;
   private final Bundle zac;
   private final Integer zad;

   public SignInClientImpl(Context var1, Looper var2, boolean var3, ClientSettings var4, Bundle var5, GoogleApiClient.ConnectionCallbacks var6, GoogleApiClient.OnConnectionFailedListener var7) {
      super(var1, var2, 44, var4, (GoogleApiClient.ConnectionCallbacks)var6, (GoogleApiClient.OnConnectionFailedListener)var7);
      this.zaa = var3;
      this.zab = var4;
      this.zac = var5;
      this.zad = var4.zad();
   }

   public SignInClientImpl(Context var1, Looper var2, boolean var3, ClientSettings var4, SignInOptions var5, GoogleApiClient.ConnectionCallbacks var6, GoogleApiClient.OnConnectionFailedListener var7) {
      this(var1, var2, true, var4, createBundleFromClientSettings(var4), var6, var7);
   }

   public static Bundle createBundleFromClientSettings(ClientSettings var0) {
      SignInOptions var1 = var0.zac();
      Integer var2 = var0.zad();
      Bundle var3 = new Bundle();
      var3.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", var0.getAccount());
      if (var2 != null) {
         var3.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", var2);
      }

      if (var1 != null) {
         var3.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", false);
         var3.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", false);
         var3.putString("com.google.android.gms.signin.internal.serverClientId", (String)null);
         var3.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
         var3.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", false);
         var3.putString("com.google.android.gms.signin.internal.hostedDomain", (String)null);
         var3.putString("com.google.android.gms.signin.internal.logSessionId", (String)null);
         var3.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", false);
      }

      return var3;
   }

   // $FF: synthetic method
   protected IInterface createServiceInterface(IBinder var1) {
      if (var1 == null) {
         return null;
      } else {
         IInterface var2 = var1.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
         return (IInterface)(var2 instanceof zae ? (zae)var2 : new zah(var1));
      }
   }

   protected Bundle getGetServiceRequestExtraArgs() {
      String var1 = this.zab.getRealClientPackageName();
      if (!this.getContext().getPackageName().equals(var1)) {
         this.zac.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zab.getRealClientPackageName());
      }

      return this.zac;
   }

   public int getMinApkVersion() {
      return 12451000;
   }

   protected String getServiceDescriptor() {
      return "com.google.android.gms.signin.internal.ISignInService";
   }

   protected String getStartServiceAction() {
      return "com.google.android.gms.signin.service.START";
   }

   public boolean requiresSignIn() {
      return this.zaa;
   }

   public final void zaa() {
      try {
         ((zae)this.getService()).zaa((Integer)Preconditions.checkNotNull(this.zad));
      } catch (RemoteException var2) {
         Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
      }
   }

   public final void zaa(IAccountAccessor var1, boolean var2) {
      try {
         ((zae)this.getService()).zaa(var1, (Integer)Preconditions.checkNotNull(this.zad), var2);
      } catch (RemoteException var3) {
         Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
      }
   }

   public final void zaa(zac var1) {
      Preconditions.checkNotNull(var1, "Expecting a valid ISignInCallbacks");

      RemoteException var10000;
      label41: {
         Account var2;
         boolean var10001;
         try {
            var2 = this.zab.getAccountOrDefault();
         } catch (RemoteException var8) {
            var10000 = var8;
            var10001 = false;
            break label41;
         }

         GoogleSignInAccount var3 = null;

         try {
            if ("<<default account>>".equals(var2.name)) {
               var3 = Storage.getInstance(this.getContext()).getSavedDefaultGoogleSignInAccount();
            }
         } catch (RemoteException var7) {
            var10000 = var7;
            var10001 = false;
            break label41;
         }

         try {
            zar var4 = new zar(var2, (Integer)Preconditions.checkNotNull(this.zad), var3);
            zae var10 = (zae)this.getService();
            zak var12 = new zak(var4);
            var10.zaa(var12, var1);
            return;
         } catch (RemoteException var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      RemoteException var11 = var10000;
      Log.w("SignInClientImpl", "Remote service probably died when signIn is called");

      try {
         zam var9 = new zam(8);
         var1.zaa(var9);
      } catch (RemoteException var5) {
         Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", var11);
      }
   }

   public final void zab() {
      this.connect(new BaseGmsClient.LegacyClientCallbackAdapter());
   }
}

package com.google.android.gms.common.api.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Set;

public final class NonGmsServiceBrokerClient implements ServiceConnection, Api.Client {
   private static final String zaa = NonGmsServiceBrokerClient.class.getSimpleName();
   private final String zab;
   private final String zac;
   private final ComponentName zad;
   private final Context zae;
   private final ConnectionCallbacks zaf;
   private final Handler zag;
   private final OnConnectionFailedListener zah;
   private IBinder zai;
   private boolean zaj;
   private String zak;

   public NonGmsServiceBrokerClient(Context var1, Looper var2, ComponentName var3, ConnectionCallbacks var4, OnConnectionFailedListener var5) {
      this(var1, var2, (String)null, (String)null, var3, var4, var5);
   }

   private NonGmsServiceBrokerClient(Context var1, Looper var2, String var3, String var4, ComponentName var5, ConnectionCallbacks var6, OnConnectionFailedListener var7) {
      boolean var8 = false;
      this.zaj = false;
      this.zak = null;
      this.zae = var1;
      this.zag = new com.google.android.gms.internal.base.zap(var2);
      this.zaf = var6;
      this.zah = var7;
      boolean var9;
      if (var3 != null && var4 != null) {
         var9 = true;
      } else {
         var9 = false;
      }

      if (var5 != null) {
         var8 = true;
      }

      label22: {
         if (var9) {
            if (!var8) {
               break label22;
            }
         } else if (var8) {
            break label22;
         }

         throw new AssertionError("Must specify either package or component, but not both");
      }

      this.zab = var3;
      this.zac = var4;
      this.zad = var5;
   }

   public NonGmsServiceBrokerClient(Context var1, Looper var2, String var3, String var4, ConnectionCallbacks var5, OnConnectionFailedListener var6) {
      this(var1, var2, var3, var4, (ComponentName)null, var5, var6);
   }

   private final void zaa(String var1) {
      String var2 = String.valueOf(this.zai);
      boolean var3 = this.zaj;
      StringBuilder var4 = new StringBuilder(String.valueOf(var1).length() + 30 + String.valueOf(var2).length());
      var4.append(var1);
      var4.append(" binder: ");
      var4.append(var2);
      var4.append(", isConnecting: ");
      var4.append(var3);
   }

   private final void zab() {
      if (Thread.currentThread() != this.zag.getLooper().getThread()) {
         throw new IllegalStateException("This method should only run on the NonGmsServiceBrokerClient's handler thread.");
      }
   }

   public final void connect(BaseGmsClient.ConnectionProgressReportCallbacks var1) {
      this.zab();
      this.zaa("Connect started.");
      if (this.isConnected()) {
         try {
            this.disconnect("connect() called when already connected");
         } catch (Exception var3) {
         }
      }

      boolean var2;
      label41: {
         SecurityException var10000;
         label40: {
            boolean var10001;
            Intent var7;
            label45: {
               try {
                  var7 = new Intent();
                  if (this.zad != null) {
                     var7.setComponent(this.zad);
                     break label45;
                  }
               } catch (SecurityException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break label40;
               }

               try {
                  var7.setPackage(this.zab).setAction(this.zac);
               } catch (SecurityException var5) {
                  var10000 = var5;
                  var10001 = false;
                  break label40;
               }
            }

            try {
               var2 = this.zae.bindService(var7, this, GmsClientSupervisor.getDefaultBindFlags());
               this.zaj = var2;
               break label41;
            } catch (SecurityException var4) {
               var10000 = var4;
               var10001 = false;
            }
         }

         SecurityException var8 = var10000;
         this.zaj = false;
         this.zai = null;
         throw var8;
      }

      if (!var2) {
         this.zai = null;
         this.zah.onConnectionFailed(new ConnectionResult(16));
      }

      this.zaa("Finished connect.");
   }

   public final void disconnect() {
      this.zab();
      this.zaa("Disconnect called.");
      this.zae.unbindService(this);
      this.zaj = false;
      this.zai = null;
   }

   public final void disconnect(String var1) {
      this.zab();
      this.zak = var1;
      this.disconnect();
   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
   }

   public final Feature[] getAvailableFeatures() {
      return new Feature[0];
   }

   public final IBinder getBinder() {
      this.zab();
      return this.zai;
   }

   public final String getEndpointPackageName() {
      String var1 = this.zab;
      if (var1 != null) {
         return var1;
      } else {
         Preconditions.checkNotNull(this.zad);
         return this.zad.getPackageName();
      }
   }

   public final String getLastDisconnectMessage() {
      return this.zak;
   }

   public final int getMinApkVersion() {
      return 0;
   }

   public final void getRemoteService(IAccountAccessor var1, Set<Scope> var2) {
   }

   public final Feature[] getRequiredFeatures() {
      return new Feature[0];
   }

   public final Set<Scope> getScopesForConnectionlessNonSignIn() {
      return Collections.emptySet();
   }

   public final IBinder getServiceBrokerBinder() {
      return null;
   }

   public final Intent getSignInIntent() {
      return new Intent();
   }

   public final boolean isConnected() {
      this.zab();
      return this.zai != null;
   }

   public final boolean isConnecting() {
      this.zab();
      return this.zaj;
   }

   public final void onServiceConnected(ComponentName var1, IBinder var2) {
      this.zag.post(new zabp(this, var2));
   }

   public final void onServiceDisconnected(ComponentName var1) {
      this.zag.post(new zabq(this));
   }

   public final void onUserSignOut(BaseGmsClient.SignOutCallbacks var1) {
   }

   public final boolean providesSignIn() {
      return false;
   }

   public final boolean requiresAccount() {
      return false;
   }

   public final boolean requiresGooglePlayServices() {
      return false;
   }

   public final boolean requiresSignIn() {
      return false;
   }

   // $FF: synthetic method
   final void zaa() {
      this.zaj = false;
      this.zai = null;
      this.zaa("Disconnected.");
      this.zaf.onConnectionSuspended(1);
   }

   // $FF: synthetic method
   final void zaa(IBinder var1) {
      this.zaj = false;
      this.zai = var1;
      this.zaa("Connected.");
      this.zaf.onConnected(new Bundle());
   }
}

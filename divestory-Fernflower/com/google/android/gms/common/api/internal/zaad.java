package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public final class zaad implements zaay {
   private final zaax zaa;
   private final Lock zab;
   private final Context zac;
   private final GoogleApiAvailabilityLight zad;
   private ConnectionResult zae;
   private int zaf;
   private int zag = 0;
   private int zah;
   private final Bundle zai = new Bundle();
   private final Set<Api.AnyClientKey> zaj = new HashSet();
   private com.google.android.gms.signin.zad zak;
   private boolean zal;
   private boolean zam;
   private boolean zan;
   private IAccountAccessor zao;
   private boolean zap;
   private boolean zaq;
   private final ClientSettings zar;
   private final Map<Api<?>, Boolean> zas;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zat;
   private ArrayList<Future<?>> zau = new ArrayList();

   public zaad(zaax var1, ClientSettings var2, Map<Api<?>, Boolean> var3, GoogleApiAvailabilityLight var4, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> var5, Lock var6, Context var7) {
      this.zaa = var1;
      this.zar = var2;
      this.zas = var3;
      this.zad = var4;
      this.zat = var5;
      this.zab = var6;
      this.zac = var7;
   }

   // $FF: synthetic method
   static Context zaa(zaad var0) {
      return var0.zac;
   }

   // $FF: synthetic method
   static void zaa(zaad var0, ConnectionResult var1) {
      var0.zab(var1);
   }

   // $FF: synthetic method
   static void zaa(zaad var0, ConnectionResult var1, Api var2, boolean var3) {
      var0.zab(var1, var2, var3);
   }

   // $FF: synthetic method
   static void zaa(zaad var0, com.google.android.gms.signin.internal.zam var1) {
      var0.zaa(var1);
   }

   private final void zaa(com.google.android.gms.signin.internal.zam var1) {
      if (this.zab(0)) {
         ConnectionResult var2 = var1.zaa();
         if (var2.isSuccess()) {
            com.google.android.gms.common.internal.zas var5 = (com.google.android.gms.common.internal.zas)Preconditions.checkNotNull(var1.zab());
            ConnectionResult var4 = var5.zab();
            if (!var4.isSuccess()) {
               String var6 = String.valueOf(var4);
               StringBuilder var3 = new StringBuilder(String.valueOf(var6).length() + 48);
               var3.append("Sign-in succeeded with resolve account failure: ");
               var3.append(var6);
               Log.wtf("GACConnecting", var3.toString(), new Exception());
               this.zab(var4);
            } else {
               this.zan = true;
               this.zao = (IAccountAccessor)Preconditions.checkNotNull(var5.zaa());
               this.zap = var5.zac();
               this.zaq = var5.zad();
               this.zae();
            }
         } else if (this.zaa(var2)) {
            this.zag();
            this.zae();
         } else {
            this.zab(var2);
         }
      }
   }

   private final void zaa(boolean var1) {
      com.google.android.gms.signin.zad var2 = this.zak;
      if (var2 != null) {
         if (var2.isConnected() && var1) {
            var2.zaa();
         }

         var2.disconnect();
         ClientSettings var3 = (ClientSettings)Preconditions.checkNotNull(this.zar);
         this.zao = null;
      }

   }

   private final boolean zaa(ConnectionResult var1) {
      return this.zal && !var1.hasResolution();
   }

   // $FF: synthetic method
   static boolean zaa(zaad var0, int var1) {
      return var0.zab(0);
   }

   // $FF: synthetic method
   static GoogleApiAvailabilityLight zab(zaad var0) {
      return var0.zad;
   }

   private final void zab(ConnectionResult var1) {
      this.zah();
      this.zaa(var1.hasResolution() ^ true);
      this.zaa.zaa(var1);
      this.zaa.zae.zaa(var1);
   }

   private final void zab(ConnectionResult var1, Api<?> var2, boolean var3) {
      int var4;
      boolean var7;
      label34: {
         var4 = var2.zaa().getPriority();
         boolean var5 = false;
         if (var3) {
            boolean var6;
            if (!var1.hasResolution() && this.zad.getErrorResolutionIntent(var1.getErrorCode()) == null) {
               var6 = false;
            } else {
               var6 = true;
            }

            var7 = var5;
            if (!var6) {
               break label34;
            }
         }

         if (this.zae != null) {
            var7 = var5;
            if (var4 >= this.zaf) {
               break label34;
            }
         }

         var7 = true;
      }

      if (var7) {
         this.zae = var1;
         this.zaf = var4;
      }

      this.zaa.zab.put(var2.zac(), var1);
   }

   private final boolean zab(int var1) {
      if (this.zag != var1) {
         Log.w("GACConnecting", this.zaa.zad.zac());
         String var2 = String.valueOf(this);
         StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 23);
         var3.append("Unexpected callback in ");
         var3.append(var2);
         Log.w("GACConnecting", var3.toString());
         int var4 = this.zah;
         var3 = new StringBuilder(33);
         var3.append("mRemainingConnections=");
         var3.append(var4);
         Log.w("GACConnecting", var3.toString());
         String var5 = zac(this.zag);
         var2 = zac(var1);
         var3 = new StringBuilder(String.valueOf(var5).length() + 70 + String.valueOf(var2).length());
         var3.append("GoogleApiClient connecting is in step ");
         var3.append(var5);
         var3.append(" but received callback for step ");
         var3.append(var2);
         Log.e("GACConnecting", var3.toString(), new Exception());
         this.zab(new ConnectionResult(8, (PendingIntent)null));
         return false;
      } else {
         return true;
      }
   }

   // $FF: synthetic method
   static boolean zab(zaad var0, ConnectionResult var1) {
      return var0.zaa(var1);
   }

   private static String zac(int var0) {
      if (var0 != 0) {
         return var0 != 1 ? "UNKNOWN" : "STEP_GETTING_REMOTE_SERVICE";
      } else {
         return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
      }
   }

   // $FF: synthetic method
   static Lock zac(zaad var0) {
      return var0.zab;
   }

   // $FF: synthetic method
   static zaax zad(zaad var0) {
      return var0.zaa;
   }

   private final boolean zad() {
      int var1 = this.zah - 1;
      this.zah = var1;
      if (var1 > 0) {
         return false;
      } else if (var1 < 0) {
         Log.w("GACConnecting", this.zaa.zad.zac());
         Log.wtf("GACConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
         this.zab(new ConnectionResult(8, (PendingIntent)null));
         return false;
      } else if (this.zae != null) {
         this.zaa.zac = this.zaf;
         this.zab(this.zae);
         return false;
      } else {
         return true;
      }
   }

   private final void zae() {
      if (this.zah == 0) {
         if (!this.zam || this.zan) {
            ArrayList var1 = new ArrayList();
            this.zag = 1;
            this.zah = this.zaa.zaa.size();
            Iterator var2 = this.zaa.zaa.keySet().iterator();

            while(var2.hasNext()) {
               Api.AnyClientKey var3 = (Api.AnyClientKey)var2.next();
               if (this.zaa.zab.containsKey(var3)) {
                  if (this.zad()) {
                     this.zaf();
                  }
               } else {
                  var1.add((Api.Client)this.zaa.zaa.get(var3));
               }
            }

            if (!var1.isEmpty()) {
               this.zau.add(zabb.zaa().submit(new zaaj(this, var1)));
            }
         }

      }
   }

   // $FF: synthetic method
   static boolean zae(zaad var0) {
      return var0.zam;
   }

   // $FF: synthetic method
   static com.google.android.gms.signin.zad zaf(zaad var0) {
      return var0.zak;
   }

   private final void zaf() {
      this.zaa.zai();
      zabb.zaa().execute(new zaag(this));
      com.google.android.gms.signin.zad var1 = this.zak;
      if (var1 != null) {
         if (this.zap) {
            var1.zaa((IAccountAccessor)Preconditions.checkNotNull(this.zao), this.zaq);
         }

         this.zaa(false);
      }

      Iterator var3 = this.zaa.zab.keySet().iterator();

      while(var3.hasNext()) {
         Api.AnyClientKey var2 = (Api.AnyClientKey)var3.next();
         ((Api.Client)Preconditions.checkNotNull((Api.Client)this.zaa.zaa.get(var2))).disconnect();
      }

      Bundle var4;
      if (this.zai.isEmpty()) {
         var4 = null;
      } else {
         var4 = this.zai;
      }

      this.zaa.zae.zaa(var4);
   }

   // $FF: synthetic method
   static Set zag(zaad var0) {
      return var0.zai();
   }

   private final void zag() {
      this.zam = false;
      this.zaa.zad.zac = Collections.emptySet();
      Iterator var1 = this.zaj.iterator();

      while(var1.hasNext()) {
         Api.AnyClientKey var2 = (Api.AnyClientKey)var1.next();
         if (!this.zaa.zab.containsKey(var2)) {
            this.zaa.zab.put(var2, new ConnectionResult(17, (PendingIntent)null));
         }
      }

   }

   // $FF: synthetic method
   static IAccountAccessor zah(zaad var0) {
      return var0.zao;
   }

   private final void zah() {
      ArrayList var1 = (ArrayList)this.zau;
      int var2 = var1.size();
      int var3 = 0;

      while(var3 < var2) {
         Object var4 = var1.get(var3);
         ++var3;
         ((Future)var4).cancel(true);
      }

      this.zau.clear();
   }

   // $FF: synthetic method
   static ClientSettings zai(zaad var0) {
      return var0.zar;
   }

   private final Set<Scope> zai() {
      if (this.zar == null) {
         return Collections.emptySet();
      } else {
         HashSet var1 = new HashSet(this.zar.getRequiredScopes());
         Map var2 = this.zar.zaa();
         Iterator var3 = var2.keySet().iterator();

         while(var3.hasNext()) {
            Api var4 = (Api)var3.next();
            if (!this.zaa.zab.containsKey(var4.zac())) {
               var1.addAll(((ClientSettings.zaa)var2.get(var4)).zaa);
            }
         }

         return var1;
      }
   }

   // $FF: synthetic method
   static void zaj(zaad var0) {
      var0.zag();
   }

   // $FF: synthetic method
   static void zak(zaad var0) {
      var0.zae();
   }

   // $FF: synthetic method
   static boolean zal(zaad var0) {
      return var0.zad();
   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1) {
      this.zaa.zad.zaa.add(var1);
      return var1;
   }

   public final void zaa() {
      this.zaa.zab.clear();
      this.zam = false;
      this.zae = null;
      this.zag = 0;
      this.zal = true;
      this.zan = false;
      this.zap = false;
      HashMap var1 = new HashMap();
      Iterator var2 = this.zas.keySet().iterator();

      boolean var3;
      Api var4;
      Api.Client var5;
      boolean var7;
      for(var3 = false; var2.hasNext(); var1.put(var5, new zaaf(this, var4, var7))) {
         var4 = (Api)var2.next();
         var5 = (Api.Client)Preconditions.checkNotNull((Api.Client)this.zaa.zaa.get(var4.zac()));
         boolean var6;
         if (var4.zaa().getPriority() == 1) {
            var6 = true;
         } else {
            var6 = false;
         }

         var3 |= var6;
         var7 = (Boolean)this.zas.get(var4);
         if (var5.requiresSignIn()) {
            this.zam = true;
            if (var7) {
               this.zaj.add(var4.zac());
            } else {
               this.zal = false;
            }
         }
      }

      if (var3) {
         this.zam = false;
      }

      if (this.zam) {
         Preconditions.checkNotNull(this.zar);
         Preconditions.checkNotNull(this.zat);
         this.zar.zaa(System.identityHashCode(this.zaa.zad));
         zaao var11 = new zaao(this, (zaag)null);
         Api.AbstractClientBuilder var10 = this.zat;
         Context var8 = this.zac;
         Looper var12 = this.zaa.zad.getLooper();
         ClientSettings var9 = this.zar;
         this.zak = (com.google.android.gms.signin.zad)var10.buildClient(var8, var12, var9, var9.zac(), (GoogleApiClient.ConnectionCallbacks)var11, (GoogleApiClient.OnConnectionFailedListener)var11);
      }

      this.zah = this.zaa.zaa.size();
      this.zau.add(zabb.zaa().submit(new zaai(this, var1)));
   }

   public final void zaa(int var1) {
      this.zab(new ConnectionResult(8, (PendingIntent)null));
   }

   public final void zaa(Bundle var1) {
      if (this.zab(1)) {
         if (var1 != null) {
            this.zai.putAll(var1);
         }

         if (this.zad()) {
            this.zaf();
         }

      }
   }

   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {
      if (this.zab(1)) {
         this.zab(var1, var2, var3);
         if (this.zad()) {
            this.zaf();
         }

      }
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1) {
      throw new IllegalStateException("GoogleApiClient is not connected yet.");
   }

   public final boolean zab() {
      this.zah();
      this.zaa(true);
      this.zaa.zaa((ConnectionResult)null);
      return true;
   }

   public final void zac() {
   }
}

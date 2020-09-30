package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zaq implements zabn {
   private final Context zaa;
   private final zaap zab;
   private final Looper zac;
   private final zaax zad;
   private final zaax zae;
   private final Map<Api.AnyClientKey<?>, zaax> zaf;
   private final Set<SignInConnectionListener> zag = Collections.newSetFromMap(new WeakHashMap());
   private final Api.Client zah;
   private Bundle zai;
   private ConnectionResult zaj = null;
   private ConnectionResult zak = null;
   private boolean zal = false;
   private final Lock zam;
   private int zan = 0;

   private zaq(Context var1, zaap var2, Lock var3, Looper var4, GoogleApiAvailabilityLight var5, Map<Api.AnyClientKey<?>, Api.Client> var6, Map<Api.AnyClientKey<?>, Api.Client> var7, ClientSettings var8, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> var9, Api.Client var10, ArrayList<zap> var11, ArrayList<zap> var12, Map<Api<?>, Boolean> var13, Map<Api<?>, Boolean> var14) {
      this.zaa = var1;
      this.zab = var2;
      this.zam = var3;
      this.zac = var4;
      this.zah = var10;
      this.zad = new zaax(var1, this.zab, var3, var4, var5, var7, (ClientSettings)null, var14, (Api.AbstractClientBuilder)null, var12, new zas(this, (zat)null));
      this.zae = new zaax(var1, this.zab, var3, var4, var5, var6, var8, var13, var9, var11, new zau(this, (zat)null));
      ArrayMap var15 = new ArrayMap();
      Iterator var16 = var7.keySet().iterator();

      while(var16.hasNext()) {
         var15.put((Api.AnyClientKey)var16.next(), this.zad);
      }

      var16 = var6.keySet().iterator();

      while(var16.hasNext()) {
         var15.put((Api.AnyClientKey)var16.next(), this.zae);
      }

      this.zaf = Collections.unmodifiableMap(var15);
   }

   // $FF: synthetic method
   static ConnectionResult zaa(zaq var0, ConnectionResult var1) {
      var0.zaj = var1;
      return var1;
   }

   public static zaq zaa(Context var0, zaap var1, Lock var2, Looper var3, GoogleApiAvailabilityLight var4, Map<Api.AnyClientKey<?>, Api.Client> var5, ClientSettings var6, Map<Api<?>, Boolean> var7, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> var8, ArrayList<zap> var9) {
      ArrayMap var10 = new ArrayMap();
      ArrayMap var11 = new ArrayMap();
      Iterator var12 = var5.entrySet().iterator();
      Api.Client var19 = null;

      while(var12.hasNext()) {
         Entry var13 = (Entry)var12.next();
         Api.Client var14 = (Api.Client)var13.getValue();
         if (var14.providesSignIn()) {
            var19 = var14;
         }

         if (var14.requiresSignIn()) {
            var10.put((Api.AnyClientKey)var13.getKey(), var14);
         } else {
            var11.put((Api.AnyClientKey)var13.getKey(), var14);
         }
      }

      Preconditions.checkState(var10.isEmpty() ^ true, "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
      ArrayMap var24 = new ArrayMap();
      ArrayMap var21 = new ArrayMap();
      Iterator var22 = var7.keySet().iterator();

      while(var22.hasNext()) {
         Api var15 = (Api)var22.next();
         Api.AnyClientKey var16 = var15.zac();
         if (var10.containsKey(var16)) {
            var24.put(var15, (Boolean)var7.get(var15));
         } else {
            if (!var11.containsKey(var16)) {
               throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
            }

            var21.put(var15, (Boolean)var7.get(var15));
         }
      }

      ArrayList var20 = new ArrayList();
      ArrayList var23 = new ArrayList();
      var9 = (ArrayList)var9;
      int var17 = var9.size();
      int var18 = 0;

      while(var18 < var17) {
         Object var25 = var9.get(var18);
         ++var18;
         zap var26 = (zap)var25;
         if (var24.containsKey(var26.zaa)) {
            var20.add(var26);
         } else {
            if (!var21.containsKey(var26.zaa)) {
               throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
            }

            var23.add(var26);
         }
      }

      return new zaq(var0, var1, var2, var3, var4, var10, var11, var6, var8, var19, var20, var23, var24, var21);
   }

   // $FF: synthetic method
   static Lock zaa(zaq var0) {
      return var0.zam;
   }

   private final void zaa(int var1, boolean var2) {
      this.zab.zaa(var1, var2);
      this.zak = null;
      this.zaj = null;
   }

   private final void zaa(Bundle var1) {
      Bundle var2 = this.zai;
      if (var2 == null) {
         this.zai = var1;
      } else {
         if (var1 != null) {
            var2.putAll(var1);
         }

      }
   }

   private final void zaa(ConnectionResult var1) {
      label14: {
         int var2 = this.zan;
         if (var2 != 1) {
            if (var2 != 2) {
               Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
               break label14;
            }

            this.zab.zaa(var1);
         }

         this.zai();
      }

      this.zan = 0;
   }

   // $FF: synthetic method
   static void zaa(zaq var0, int var1, boolean var2) {
      var0.zaa(var1, var2);
   }

   // $FF: synthetic method
   static void zaa(zaq var0, Bundle var1) {
      var0.zaa(var1);
   }

   // $FF: synthetic method
   static boolean zaa(zaq var0, boolean var1) {
      var0.zal = var1;
      return var1;
   }

   // $FF: synthetic method
   static ConnectionResult zab(zaq var0, ConnectionResult var1) {
      var0.zak = var1;
      return var1;
   }

   // $FF: synthetic method
   static void zab(zaq var0) {
      var0.zah();
   }

   private static boolean zab(ConnectionResult var0) {
      return var0 != null && var0.isSuccess();
   }

   private final boolean zac(BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient> var1) {
      Api.AnyClientKey var2 = var1.getClientKey();
      zaax var3 = (zaax)this.zaf.get(var2);
      Preconditions.checkNotNull(var3, "GoogleApiClient is not configured to use the API required for this call.");
      return var3.equals(this.zae);
   }

   // $FF: synthetic method
   static boolean zac(zaq var0) {
      return var0.zal;
   }

   // $FF: synthetic method
   static ConnectionResult zad(zaq var0) {
      return var0.zak;
   }

   // $FF: synthetic method
   static zaax zae(zaq var0) {
      return var0.zae;
   }

   // $FF: synthetic method
   static zaax zaf(zaq var0) {
      return var0.zad;
   }

   private final void zah() {
      ConnectionResult var1;
      if (zab(this.zaj)) {
         if (zab(this.zak) || this.zaj()) {
            label41: {
               int var2 = this.zan;
               if (var2 != 1) {
                  if (var2 != 2) {
                     Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                     break label41;
                  }

                  ((zaap)Preconditions.checkNotNull(this.zab)).zaa(this.zai);
               }

               this.zai();
            }

            this.zan = 0;
            return;
         }

         var1 = this.zak;
         if (var1 != null) {
            if (this.zan == 1) {
               this.zai();
               return;
            }

            this.zaa(var1);
            this.zad.zac();
            return;
         }
      } else {
         if (this.zaj != null && zab(this.zak)) {
            this.zae.zac();
            this.zaa((ConnectionResult)Preconditions.checkNotNull(this.zaj));
            return;
         }

         var1 = this.zaj;
         if (var1 != null && this.zak != null) {
            if (this.zae.zac < this.zad.zac) {
               var1 = this.zak;
            }

            this.zaa(var1);
         }
      }

   }

   private final void zai() {
      Iterator var1 = this.zag.iterator();

      while(var1.hasNext()) {
         ((SignInConnectionListener)var1.next()).onComplete();
      }

      this.zag.clear();
   }

   private final boolean zaj() {
      ConnectionResult var1 = this.zak;
      return var1 != null && var1.getErrorCode() == 4;
   }

   private final PendingIntent zak() {
      return this.zah == null ? null : PendingIntent.getActivity(this.zaa, System.identityHashCode(this.zab), this.zah.getSignInIntent(), 134217728);
   }

   public final ConnectionResult zaa(long var1, TimeUnit var3) {
      throw new UnsupportedOperationException();
   }

   public final ConnectionResult zaa(Api<?> var1) {
      if (Objects.equal(this.zaf.get(var1.zac()), this.zae)) {
         return this.zaj() ? new ConnectionResult(4, this.zak()) : this.zae.zaa(var1);
      } else {
         return this.zad.zaa(var1);
      }
   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1) {
      if (this.zac(var1)) {
         if (this.zaj()) {
            var1.setFailedResult(new Status(4, (String)null, this.zak()));
            return var1;
         } else {
            return this.zae.zaa(var1);
         }
      } else {
         return this.zad.zaa(var1);
      }
   }

   public final void zaa() {
      this.zan = 2;
      this.zal = false;
      this.zak = null;
      this.zaj = null;
      this.zad.zaa();
      this.zae.zaa();
   }

   public final void zaa(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.append(var1).append("authClient").println(":");
      this.zae.zaa(String.valueOf(var1).concat("  "), var2, var3, var4);
      var3.append(var1).append("anonClient").println(":");
      this.zad.zaa(String.valueOf(var1).concat("  "), var2, var3, var4);
   }

   public final boolean zaa(SignInConnectionListener var1) {
      this.zam.lock();

      try {
         if (!this.zae() && !this.zad() || this.zae.zad()) {
            return false;
         }

         this.zag.add(var1);
         if (this.zan == 0) {
            this.zan = 1;
         }

         this.zak = null;
         this.zae.zaa();
      } finally {
         this.zam.unlock();
      }

      return true;
   }

   public final ConnectionResult zab() {
      throw new UnsupportedOperationException();
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1) {
      if (this.zac(var1)) {
         if (this.zaj()) {
            var1.setFailedResult(new Status(4, (String)null, this.zak()));
            return var1;
         } else {
            return this.zae.zab(var1);
         }
      } else {
         return this.zad.zab(var1);
      }
   }

   public final void zac() {
      this.zak = null;
      this.zaj = null;
      this.zan = 0;
      this.zad.zac();
      this.zae.zac();
      this.zai();
   }

   public final boolean zad() {
      this.zam.lock();

      boolean var1;
      label176: {
         label175: {
            Throwable var10000;
            label180: {
               boolean var10001;
               try {
                  var1 = this.zad.zad();
               } catch (Throwable var16) {
                  var10000 = var16;
                  var10001 = false;
                  break label180;
               }

               boolean var2 = true;
               if (!var1) {
                  break label175;
               }

               var1 = var2;

               try {
                  if (this.zae.zad()) {
                     break label176;
                  }
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label180;
               }

               var1 = var2;

               int var3;
               try {
                  if (this.zaj()) {
                     break label176;
                  }

                  var3 = this.zan;
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label180;
               }

               if (var3 == 1) {
                  var1 = var2;
                  break label176;
               }
               break label175;
            }

            Throwable var4 = var10000;
            this.zam.unlock();
            throw var4;
         }

         var1 = false;
      }

      this.zam.unlock();
      return var1;
   }

   public final boolean zae() {
      this.zam.lock();
      boolean var5 = false;

      int var1;
      try {
         var5 = true;
         var1 = this.zan;
         var5 = false;
      } finally {
         if (var5) {
            this.zam.unlock();
         }
      }

      boolean var2;
      if (var1 == 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.zam.unlock();
      return var2;
   }

   public final void zaf() {
      this.zad.zaf();
      this.zae.zaf();
   }

   public final void zag() {
      this.zam.lock();

      label106: {
         Throwable var10000;
         label105: {
            boolean var1;
            boolean var10001;
            try {
               var1 = this.zae();
               this.zae.zac();
               ConnectionResult var2 = new ConnectionResult(4);
               this.zak = var2;
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label105;
            }

            if (var1) {
               label99:
               try {
                  com.google.android.gms.internal.base.zap var16 = new com.google.android.gms.internal.base.zap(this.zac);
                  zat var3 = new zat(this);
                  var16.post(var3);
               } catch (Throwable var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label99;
               }
            } else {
               label101:
               try {
                  this.zai();
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label101;
               }
            }
            break label106;
         }

         Throwable var17 = var10000;
         this.zam.unlock();
         throw var17;
      }

      this.zam.unlock();
   }
}

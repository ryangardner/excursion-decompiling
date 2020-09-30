package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zaax implements zabn, zar {
   final Map<Api.AnyClientKey<?>, Api.Client> zaa;
   final Map<Api.AnyClientKey<?>, ConnectionResult> zab = new HashMap();
   int zac;
   final zaap zad;
   final zabm zae;
   private final Lock zaf;
   private final Condition zag;
   private final Context zah;
   private final GoogleApiAvailabilityLight zai;
   private final zaaz zaj;
   private final ClientSettings zak;
   private final Map<Api<?>, Boolean> zal;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zam;
   @NotOnlyInitialized
   private volatile zaay zan;
   private ConnectionResult zao = null;

   public zaax(Context var1, zaap var2, Lock var3, Looper var4, GoogleApiAvailabilityLight var5, Map<Api.AnyClientKey<?>, Api.Client> var6, ClientSettings var7, Map<Api<?>, Boolean> var8, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> var9, ArrayList<zap> var10, zabm var11) {
      this.zah = var1;
      this.zaf = var3;
      this.zai = var5;
      this.zaa = var6;
      this.zak = var7;
      this.zal = var8;
      this.zam = var9;
      this.zad = var2;
      this.zae = var11;
      ArrayList var14 = (ArrayList)var10;
      int var12 = var14.size();
      int var13 = 0;

      while(var13 < var12) {
         Object var15 = var14.get(var13);
         ++var13;
         ((zap)var15).zaa(this);
      }

      this.zaj = new zaaz(this, var4);
      this.zag = var3.newCondition();
      this.zan = new zaaq(this);
   }

   // $FF: synthetic method
   static Lock zaa(zaax var0) {
      return var0.zaf;
   }

   // $FF: synthetic method
   static zaay zab(zaax var0) {
      return var0.zan;
   }

   public final void onConnected(Bundle var1) {
      this.zaf.lock();

      try {
         this.zan.zaa(var1);
      } finally {
         this.zaf.unlock();
      }

   }

   public final void onConnectionSuspended(int var1) {
      this.zaf.lock();

      try {
         this.zan.zaa(var1);
      } finally {
         this.zaf.unlock();
      }

   }

   public final ConnectionResult zaa(long var1, TimeUnit var3) {
      this.zaa();
      var1 = var3.toNanos(var1);

      while(this.zae()) {
         boolean var10001;
         if (var1 <= 0L) {
            try {
               this.zac();
               return new ConnectionResult(14, (PendingIntent)null);
            } catch (InterruptedException var4) {
               var10001 = false;
            }
         } else {
            try {
               var1 = this.zag.awaitNanos(var1);
               continue;
            } catch (InterruptedException var5) {
               var10001 = false;
            }
         }

         Thread.currentThread().interrupt();
         return new ConnectionResult(15, (PendingIntent)null);
      }

      if (this.zad()) {
         return ConnectionResult.RESULT_SUCCESS;
      } else {
         ConnectionResult var6 = this.zao;
         if (var6 != null) {
            return var6;
         } else {
            return new ConnectionResult(13, (PendingIntent)null);
         }
      }
   }

   public final ConnectionResult zaa(Api<?> var1) {
      Api.AnyClientKey var2 = var1.zac();
      if (this.zaa.containsKey(var2)) {
         if (((Api.Client)this.zaa.get(var2)).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
         }

         if (this.zab.containsKey(var2)) {
            return (ConnectionResult)this.zab.get(var2);
         }
      }

      return null;
   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1) {
      var1.zab();
      return this.zan.zaa(var1);
   }

   public final void zaa() {
      this.zan.zac();
   }

   final void zaa(ConnectionResult var1) {
      this.zaf.lock();

      try {
         this.zao = var1;
         zaaq var4 = new zaaq(this);
         this.zan = var4;
         this.zan.zaa();
         this.zag.signalAll();
      } finally {
         this.zaf.unlock();
      }

   }

   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {
      this.zaf.lock();

      try {
         this.zan.zaa(var1, var2, var3);
      } finally {
         this.zaf.unlock();
      }

   }

   final void zaa(zaba var1) {
      Message var2 = this.zaj.obtainMessage(1, var1);
      this.zaj.sendMessage(var2);
   }

   final void zaa(RuntimeException var1) {
      Message var2 = this.zaj.obtainMessage(2, var1);
      this.zaj.sendMessage(var2);
   }

   public final void zaa(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      String var5 = String.valueOf(var1).concat("  ");
      var3.append(var1).append("mState=").println(this.zan);
      Iterator var6 = this.zal.keySet().iterator();

      while(var6.hasNext()) {
         Api var7 = (Api)var6.next();
         var3.append(var1).append(var7.zad()).println(":");
         ((Api.Client)Preconditions.checkNotNull((Api.Client)this.zaa.get(var7.zac()))).dump(var5, var2, var3, var4);
      }

   }

   public final boolean zaa(SignInConnectionListener var1) {
      return false;
   }

   public final ConnectionResult zab() {
      this.zaa();

      while(this.zae()) {
         try {
            this.zag.await();
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, (PendingIntent)null);
         }
      }

      if (this.zad()) {
         return ConnectionResult.RESULT_SUCCESS;
      } else {
         ConnectionResult var1 = this.zao;
         return var1 != null ? var1 : new ConnectionResult(13, (PendingIntent)null);
      }
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1) {
      var1.zab();
      return this.zan.zab(var1);
   }

   public final void zac() {
      if (this.zan.zab()) {
         this.zab.clear();
      }

   }

   public final boolean zad() {
      return this.zan instanceof zaac;
   }

   public final boolean zae() {
      return this.zan instanceof zaad;
   }

   public final void zaf() {
      if (this.zad()) {
         ((zaac)this.zan).zad();
      }

   }

   public final void zag() {
   }

   final void zah() {
      this.zaf.lock();

      try {
         zaad var1 = new zaad(this, this.zak, this.zal, this.zai, this.zam, this.zaf, this.zah);
         this.zan = var1;
         this.zan.zaa();
         this.zag.signalAll();
      } finally {
         this.zaf.unlock();
      }

   }

   final void zai() {
      this.zaf.lock();

      try {
         this.zad.zab();
         zaac var1 = new zaac(this);
         this.zan = var1;
         this.zan.zaa();
         this.zag.signalAll();
      } finally {
         this.zaf.unlock();
      }

   }
}

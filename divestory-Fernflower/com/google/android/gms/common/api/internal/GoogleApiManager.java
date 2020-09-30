package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.Handler.Callback;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public class GoogleApiManager implements Callback {
   public static final Status zaa = new Status(4, "Sign-out occurred while this API call was in progress.");
   private static final Status zab = new Status(4, "The user must be signed in to make this API call.");
   private static final Object zaf = new Object();
   private static GoogleApiManager zag;
   private long zac = 5000L;
   private long zad = 120000L;
   private long zae = 10000L;
   private final Context zah;
   private final GoogleApiAvailability zai;
   private final com.google.android.gms.common.internal.zaj zaj;
   private final AtomicInteger zak = new AtomicInteger(1);
   private final AtomicInteger zal = new AtomicInteger(0);
   private final Map<ApiKey<?>, GoogleApiManager.zaa<?>> zam = new ConcurrentHashMap(5, 0.75F, 1);
   private zax zan = null;
   private final Set<ApiKey<?>> zao = new ArraySet();
   private final Set<ApiKey<?>> zap = new ArraySet();
   @NotOnlyInitialized
   private final Handler zaq;
   private volatile boolean zar = true;

   private GoogleApiManager(Context var1, Looper var2, GoogleApiAvailability var3) {
      this.zah = var1;
      this.zaq = new com.google.android.gms.internal.base.zap(var2, this);
      this.zai = var3;
      this.zaj = new com.google.android.gms.common.internal.zaj(var3);
      if (DeviceProperties.isAuto(var1)) {
         this.zar = false;
      }

      Handler var4 = this.zaq;
      var4.sendMessage(var4.obtainMessage(6));
   }

   public static void reportSignOut() {
      Object var0 = zaf;
      synchronized(var0){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zag != null) {
               GoogleApiManager var1 = zag;
               var1.zal.incrementAndGet();
               var1.zaq.sendMessageAtFrontOfQueue(var1.zaq.obtainMessage(10));
            }
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var14 = var10000;

         try {
            throw var14;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            continue;
         }
      }
   }

   public static GoogleApiManager zaa() {
      // $FF: Couldn't be decompiled
   }

   public static GoogleApiManager zaa(Context var0) {
      Object var1 = zaf;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zag == null) {
               HandlerThread var2 = new HandlerThread("GoogleApiHandler", 9);
               var2.start();
               Looper var18 = var2.getLooper();
               GoogleApiManager var3 = new GoogleApiManager(var0.getApplicationContext(), var18, GoogleApiAvailability.getInstance());
               zag = var3;
            }
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            GoogleApiManager var17 = zag;
            return var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var16 = var10000;

         try {
            throw var16;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   private static Status zab(ApiKey<?> var0, ConnectionResult var1) {
      String var2 = var0.getApiName();
      String var4 = String.valueOf(var1);
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 63 + String.valueOf(var4).length());
      var3.append("API: ");
      var3.append(var2);
      var3.append(" is not available on this device. Connection failed with: ");
      var3.append(var4);
      return new Status(var1, var3.toString());
   }

   private final GoogleApiManager.zaa<?> zac(GoogleApi<?> var1) {
      ApiKey var2 = var1.getApiKey();
      GoogleApiManager.zaa var3 = (GoogleApiManager.zaa)this.zam.get(var2);
      GoogleApiManager.zaa var4 = var3;
      if (var3 == null) {
         var4 = new GoogleApiManager.zaa(var1);
         this.zam.put(var2, var4);
      }

      if (var4.zak()) {
         this.zap.add(var2);
      }

      var4.zai();
      return var4;
   }

   public boolean handleMessage(Message var1) {
      int var2 = var1.what;
      long var3 = 300000L;
      zaaa var5 = null;
      GoogleApiManager.zab var10;
      Iterator var12;
      GoogleApiManager.zaa var13;
      StringBuilder var14;
      ApiKey var15;
      GoogleApiManager.zaa var17;
      Iterator var22;
      switch(var2) {
      case 1:
         if ((Boolean)var1.obj) {
            var3 = 10000L;
         }

         this.zae = var3;
         this.zaq.removeMessages(12);
         var22 = this.zam.keySet().iterator();

         while(var22.hasNext()) {
            var15 = (ApiKey)var22.next();
            Handler var16 = this.zaq;
            var16.sendMessageDelayed(var16.obtainMessage(12, var15), this.zae);
         }

         return true;
      case 2:
         zaj var19 = (zaj)var1.obj;
         var22 = var19.zaa().iterator();

         while(var22.hasNext()) {
            ApiKey var24 = (ApiKey)var22.next();
            var13 = (GoogleApiManager.zaa)this.zam.get(var24);
            if (var13 == null) {
               var19.zaa(var24, new ConnectionResult(13), (String)null);
               return true;
            }

            if (var13.zaj()) {
               var19.zaa(var24, ConnectionResult.RESULT_SUCCESS, var13.zab().getEndpointPackageName());
            } else {
               ConnectionResult var9 = var13.zae();
               if (var9 != null) {
                  var19.zaa(var24, var9, (String)null);
               } else {
                  var13.zaa(var19);
                  var13.zai();
               }
            }
         }

         return true;
      case 3:
         var12 = this.zam.values().iterator();

         while(var12.hasNext()) {
            var17 = (GoogleApiManager.zaa)var12.next();
            var17.zad();
            var17.zai();
         }

         return true;
      case 4:
      case 8:
      case 13:
         zabr var21 = (zabr)var1.obj;
         var17 = (GoogleApiManager.zaa)this.zam.get(var21.zac.getApiKey());
         var13 = var17;
         if (var17 == null) {
            var13 = this.zac(var21.zac);
         }

         if (var13.zak() && this.zal.get() != var21.zab) {
            var21.zaa.zaa(zaa);
            var13.zaa();
         } else {
            var13.zaa(var21.zaa);
         }
         break;
      case 5:
         var2 = var1.arg1;
         ConnectionResult var7 = (ConnectionResult)var1.obj;
         Iterator var8 = this.zam.values().iterator();

         do {
            var13 = var5;
            if (!var8.hasNext()) {
               break;
            }

            var13 = (GoogleApiManager.zaa)var8.next();
         } while(var13.zal() != var2);

         if (var13 != null) {
            if (var7.getErrorCode() == 13) {
               String var18 = this.zai.getErrorString(var7.getErrorCode());
               String var23 = var7.getErrorMessage();
               StringBuilder var20 = new StringBuilder(String.valueOf(var18).length() + 69 + String.valueOf(var23).length());
               var20.append("Error resolution was canceled by the user, original error message: ");
               var20.append(var18);
               var20.append(": ");
               var20.append(var23);
               var13.zaa(new Status(17, var20.toString()));
            } else {
               var13.zaa(zab(var13.zad, var7));
            }
         } else {
            var14 = new StringBuilder(76);
            var14.append("Could not find API instance ");
            var14.append(var2);
            var14.append(" while trying to fail enqueued calls.");
            Log.wtf("GoogleApiManager", var14.toString(), new Exception());
         }
         break;
      case 6:
         if (this.zah.getApplicationContext() instanceof Application) {
            BackgroundDetector.initialize((Application)this.zah.getApplicationContext());
            BackgroundDetector.getInstance().addListener(new zabc(this));
            if (!BackgroundDetector.getInstance().readCurrentStateIfPossible(true)) {
               this.zae = 300000L;
            }
         }
         break;
      case 7:
         this.zac((GoogleApi)var1.obj);
         break;
      case 9:
         if (this.zam.containsKey(var1.obj)) {
            ((GoogleApiManager.zaa)this.zam.get(var1.obj)).zaf();
         }
         break;
      case 10:
         var12 = this.zap.iterator();

         while(var12.hasNext()) {
            var15 = (ApiKey)var12.next();
            var17 = (GoogleApiManager.zaa)this.zam.remove(var15);
            if (var17 != null) {
               var17.zaa();
            }
         }

         this.zap.clear();
         break;
      case 11:
         if (this.zam.containsKey(var1.obj)) {
            ((GoogleApiManager.zaa)this.zam.get(var1.obj)).zag();
         }
         break;
      case 12:
         if (this.zam.containsKey(var1.obj)) {
            ((GoogleApiManager.zaa)this.zam.get(var1.obj)).zah();
         }
         break;
      case 14:
         var5 = (zaaa)var1.obj;
         ApiKey var11 = var5.zaa();
         if (!this.zam.containsKey(var11)) {
            var5.zab().setResult(false);
         } else {
            boolean var6 = GoogleApiManager.zaa.zaa((GoogleApiManager.zaa)this.zam.get(var11), false);
            var5.zab().setResult(var6);
         }
         break;
      case 15:
         var10 = (GoogleApiManager.zab)var1.obj;
         if (this.zam.containsKey(var10.zaa)) {
            ((GoogleApiManager.zaa)this.zam.get(var10.zaa)).zaa(var10);
         }
         break;
      case 16:
         var10 = (GoogleApiManager.zab)var1.obj;
         if (this.zam.containsKey(var10.zaa)) {
            ((GoogleApiManager.zaa)this.zam.get(var10.zaa)).zab(var10);
         }
         break;
      default:
         var2 = var1.what;
         var14 = new StringBuilder(31);
         var14.append("Unknown message id: ");
         var14.append(var2);
         Log.w("GoogleApiManager", var14.toString());
         return false;
      }

      return true;
   }

   public final <O extends Api.ApiOptions> Task<Boolean> zaa(GoogleApi<O> var1, ListenerHolder.ListenerKey<?> var2) {
      TaskCompletionSource var3 = new TaskCompletionSource();
      zah var4 = new zah(var2, var3);
      Handler var5 = this.zaq;
      var5.sendMessage(var5.obtainMessage(13, new zabr(var4, this.zal.get(), var1)));
      return var3.getTask();
   }

   public final <O extends Api.ApiOptions> Task<Void> zaa(GoogleApi<O> var1, RegisterListenerMethod<Api.AnyClient, ?> var2, UnregisterListenerMethod<Api.AnyClient, ?> var3, Runnable var4) {
      TaskCompletionSource var5 = new TaskCompletionSource();
      zag var6 = new zag(new zabs(var2, var3, var4), var5);
      Handler var7 = this.zaq;
      var7.sendMessage(var7.obtainMessage(8, new zabr(var6, this.zal.get(), var1)));
      return var5.getTask();
   }

   public final Task<Map<ApiKey<?>, String>> zaa(Iterable<? extends HasApiKey<?>> var1) {
      zaj var3 = new zaj(var1);
      Handler var2 = this.zaq;
      var2.sendMessage(var2.obtainMessage(2, var3));
      return var3.zab();
   }

   public final void zaa(GoogleApi<?> var1) {
      Handler var2 = this.zaq;
      var2.sendMessage(var2.obtainMessage(7, var1));
   }

   public final <O extends Api.ApiOptions> void zaa(GoogleApi<O> var1, int var2, BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient> var3) {
      zad var4 = new zad(var2, var3);
      Handler var5 = this.zaq;
      var5.sendMessage(var5.obtainMessage(4, new zabr(var4, this.zal.get(), var1)));
   }

   public final <O extends Api.ApiOptions, ResultT> void zaa(GoogleApi<O> var1, int var2, TaskApiCall<Api.AnyClient, ResultT> var3, TaskCompletionSource<ResultT> var4, StatusExceptionMapper var5) {
      zaf var7 = new zaf(var2, var3, var4, var5);
      Handler var6 = this.zaq;
      var6.sendMessage(var6.obtainMessage(4, new zabr(var7, this.zal.get(), var1)));
   }

   public final void zaa(zax var1) {
      Object var2 = zaf;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (this.zan != var1) {
               this.zan = var1;
               this.zao.clear();
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            this.zao.addAll(var1.zac());
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   final boolean zaa(ConnectionResult var1, int var2) {
      return this.zai.zaa(this.zah, var1, var2);
   }

   public final int zab() {
      return this.zak.getAndIncrement();
   }

   public final Task<Boolean> zab(GoogleApi<?> var1) {
      zaaa var3 = new zaaa(var1.getApiKey());
      Handler var2 = this.zaq;
      var2.sendMessage(var2.obtainMessage(14, var3));
      return var3.zab().getTask();
   }

   public final void zab(ConnectionResult var1, int var2) {
      if (!this.zaa(var1, var2)) {
         Handler var3 = this.zaq;
         var3.sendMessage(var3.obtainMessage(5, var2, 0, var1));
      }

   }

   final void zab(zax var1) {
      Object var2 = zaf;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (this.zan == var1) {
               this.zan = null;
               this.zao.clear();
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zac() {
      Handler var1 = this.zaq;
      var1.sendMessage(var1.obtainMessage(3));
   }

   public final class zaa<O extends Api.ApiOptions> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, zar {
      private final Queue<com.google.android.gms.common.api.internal.zac> zab = new LinkedList();
      @NotOnlyInitialized
      private final Api.Client zac;
      private final ApiKey<O> zad;
      private final zaw zae;
      private final Set<zaj> zaf = new HashSet();
      private final Map<ListenerHolder.ListenerKey<?>, zabs> zag = new HashMap();
      private final int zah;
      private final zacb zai;
      private boolean zaj;
      private final List<GoogleApiManager.zab> zak = new ArrayList();
      private ConnectionResult zal = null;

      public zaa(GoogleApi<O> var2) {
         this.zac = var2.zaa(GoogleApiManager.this.zaq.getLooper(), this);
         this.zad = var2.getApiKey();
         this.zae = new zaw();
         this.zah = var2.zaa();
         if (this.zac.requiresSignIn()) {
            this.zai = var2.zaa(GoogleApiManager.this.zah, GoogleApiManager.this.zaq);
         } else {
            this.zai = null;
         }
      }

      private final Feature zaa(Feature[] var1) {
         if (var1 != null && var1.length != 0) {
            Feature[] var2 = this.zac.getAvailableFeatures();
            byte var3 = 0;
            Feature[] var4 = var2;
            if (var2 == null) {
               var4 = new Feature[0];
            }

            ArrayMap var8 = new ArrayMap(var4.length);
            int var5 = var4.length;

            int var6;
            for(var6 = 0; var6 < var5; ++var6) {
               Feature var7 = var4[var6];
               var8.put(var7.getName(), var7.getVersion());
            }

            var5 = var1.length;

            for(var6 = var3; var6 < var5; ++var6) {
               Feature var9 = var1[var6];
               Long var10 = (Long)var8.get(var9.getName());
               if (var10 == null || var10 < var9.getVersion()) {
                  return var9;
               }
            }
         }

         return null;
      }

      private final void zaa(int var1) {
         this.zad();
         this.zaj = true;
         this.zae.zaa(var1, this.zac.getLastDisconnectMessage());
         GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain(GoogleApiManager.this.zaq, 9, this.zad), GoogleApiManager.this.zac);
         GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain(GoogleApiManager.this.zaq, 11, this.zad), GoogleApiManager.this.zad);
         GoogleApiManager.this.zaj.zaa();
         Iterator var2 = this.zag.values().iterator();

         while(var2.hasNext()) {
            ((zabs)var2.next()).zac.run();
         }

      }

      private final void zaa(ConnectionResult var1, Exception var2) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         zacb var3 = this.zai;
         if (var3 != null) {
            var3.zaa();
         }

         this.zad();
         GoogleApiManager.this.zaj.zaa();
         this.zac(var1);
         if (var1.getErrorCode() == 4) {
            this.zaa(GoogleApiManager.zab);
         } else if (this.zab.isEmpty()) {
            this.zal = var1;
         } else if (var2 != null) {
            Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
            this.zaa((Status)null, (Exception)var2, false);
         } else if (!GoogleApiManager.this.zar) {
            this.zaa(this.zad(var1));
         } else {
            this.zaa((Status)this.zad(var1), (Exception)null, true);
            if (!this.zab.isEmpty()) {
               if (!this.zab(var1)) {
                  if (!GoogleApiManager.this.zaa(var1, this.zah)) {
                     if (var1.getErrorCode() == 18) {
                        this.zaj = true;
                     }

                     if (this.zaj) {
                        GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain(GoogleApiManager.this.zaq, 9, this.zad), GoogleApiManager.this.zac);
                        return;
                     }

                     this.zaa(this.zad(var1));
                  }

               }
            }
         }
      }

      private final void zaa(Status var1) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         this.zaa((Status)var1, (Exception)null, false);
      }

      private final void zaa(Status var1, Exception var2, boolean var3) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         boolean var4 = true;
         boolean var5;
         if (var1 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var2 != null) {
            var4 = false;
         }

         if (var5 == var4) {
            throw new IllegalArgumentException("Status XOR exception should be null");
         } else {
            Iterator var6 = this.zab.iterator();

            while(true) {
               com.google.android.gms.common.api.internal.zac var7;
               do {
                  if (!var6.hasNext()) {
                     return;
                  }

                  var7 = (com.google.android.gms.common.api.internal.zac)var6.next();
               } while(var3 && var7.zaa != 2);

               if (var1 != null) {
                  var7.zaa(var1);
               } else {
                  var7.zaa(var2);
               }

               var6.remove();
            }
         }
      }

      // $FF: synthetic method
      static void zaa(GoogleApiManager.zaa var0, int var1) {
         var0.zaa(var1);
      }

      private final void zaa(GoogleApiManager.zab var1) {
         if (this.zak.contains(var1)) {
            if (!this.zaj) {
               if (!this.zac.isConnected()) {
                  this.zai();
                  return;
               }

               this.zan();
            }

         }
      }

      // $FF: synthetic method
      static boolean zaa(GoogleApiManager.zaa var0, boolean var1) {
         return var0.zaa(false);
      }

      private final boolean zaa(boolean var1) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         if (this.zac.isConnected() && this.zag.size() == 0) {
            if (this.zae.zaa()) {
               if (var1) {
                  this.zap();
               }

               return false;
            } else {
               this.zac.disconnect("Timing out service connection.");
               return true;
            }
         } else {
            return false;
         }
      }

      // $FF: synthetic method
      static void zab(GoogleApiManager.zaa var0) {
         var0.zam();
      }

      private final void zab(GoogleApiManager.zab var1) {
         if (this.zak.remove(var1)) {
            GoogleApiManager.this.zaq.removeMessages(15, var1);
            GoogleApiManager.this.zaq.removeMessages(16, var1);
            Feature var8 = var1.zab;
            ArrayList var2 = new ArrayList(this.zab.size());
            Iterator var3 = this.zab.iterator();

            com.google.android.gms.common.api.internal.zac var4;
            while(var3.hasNext()) {
               var4 = (com.google.android.gms.common.api.internal.zac)var3.next();
               if (var4 instanceof com.google.android.gms.common.api.internal.zab) {
                  Feature[] var5 = ((com.google.android.gms.common.api.internal.zab)var4).zaa(this);
                  if (var5 != null && ArrayUtils.contains(var5, var8)) {
                     var2.add(var4);
                  }
               }
            }

            ArrayList var9 = (ArrayList)var2;
            int var6 = var9.size();
            int var7 = 0;

            while(var7 < var6) {
               Object var10 = var9.get(var7);
               ++var7;
               var4 = (com.google.android.gms.common.api.internal.zac)var10;
               this.zab.remove(var4);
               var4.zaa((Exception)(new UnsupportedApiCallException(var8)));
            }
         }

      }

      private final boolean zab(ConnectionResult var1) {
         Object var2 = GoogleApiManager.zaf;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label131: {
            try {
               if (GoogleApiManager.this.zan != null && GoogleApiManager.this.zao.contains(this.zad)) {
                  GoogleApiManager.this.zan.zab(var1, this.zah);
                  return true;
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label131;
            }

            label128:
            try {
               return false;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label128;
            }
         }

         while(true) {
            Throwable var15 = var10000;

            try {
               throw var15;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               continue;
            }
         }
      }

      private final boolean zab(com.google.android.gms.common.api.internal.zac var1) {
         if (!(var1 instanceof com.google.android.gms.common.api.internal.zab)) {
            this.zac(var1);
            return true;
         } else {
            com.google.android.gms.common.api.internal.zab var2 = (com.google.android.gms.common.api.internal.zab)var1;
            Feature var3 = this.zaa(var2.zaa(this));
            if (var3 == null) {
               this.zac(var1);
               return true;
            } else {
               String var4 = this.zac.getClass().getName();
               String var5 = var3.getName();
               long var6 = var3.getVersion();
               StringBuilder var9 = new StringBuilder(String.valueOf(var4).length() + 77 + String.valueOf(var5).length());
               var9.append(var4);
               var9.append(" could not execute call because it requires feature (");
               var9.append(var5);
               var9.append(", ");
               var9.append(var6);
               var9.append(").");
               Log.w("GoogleApiManager", var9.toString());
               if (GoogleApiManager.this.zar && var2.zab(this)) {
                  GoogleApiManager.zab var10 = new GoogleApiManager.zab(this.zad, var3, (zabc)null);
                  int var8 = this.zak.indexOf(var10);
                  if (var8 >= 0) {
                     var10 = (GoogleApiManager.zab)this.zak.get(var8);
                     GoogleApiManager.this.zaq.removeMessages(15, var10);
                     GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain(GoogleApiManager.this.zaq, 15, var10), GoogleApiManager.this.zac);
                  } else {
                     this.zak.add(var10);
                     GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain(GoogleApiManager.this.zaq, 15, var10), GoogleApiManager.this.zac);
                     GoogleApiManager.this.zaq.sendMessageDelayed(Message.obtain(GoogleApiManager.this.zaq, 16, var10), GoogleApiManager.this.zad);
                     ConnectionResult var11 = new ConnectionResult(2, (PendingIntent)null);
                     if (!this.zab(var11)) {
                        GoogleApiManager.this.zaa(var11, this.zah);
                     }
                  }

                  return false;
               } else {
                  var2.zaa((Exception)(new UnsupportedApiCallException(var3)));
                  return true;
               }
            }
         }
      }

      // $FF: synthetic method
      static Api.Client zac(GoogleApiManager.zaa var0) {
         return var0.zac;
      }

      private final void zac(ConnectionResult var1) {
         zaj var3;
         String var4;
         for(Iterator var2 = this.zaf.iterator(); var2.hasNext(); var3.zaa(this.zad, var1, var4)) {
            var3 = (zaj)var2.next();
            var4 = null;
            if (Objects.equal(var1, ConnectionResult.RESULT_SUCCESS)) {
               var4 = this.zac.getEndpointPackageName();
            }
         }

         this.zaf.clear();
      }

      private final void zac(com.google.android.gms.common.api.internal.zac var1) {
         var1.zaa(this.zae, this.zak());

         try {
            try {
               var1.zac(this);
               return;
            } catch (DeadObjectException var4) {
            }
         } catch (Throwable var5) {
            throw new IllegalStateException(String.format("Error in GoogleApi implementation for client %s.", this.zac.getClass().getName()), var5);
         }

         this.onConnectionSuspended(1);
         this.zac.disconnect("DeadObjectException thrown while running ApiCallRunner.");
      }

      private final Status zad(ConnectionResult var1) {
         return GoogleApiManager.zab(this.zad, var1);
      }

      private final void zam() {
         this.zad();
         this.zac(ConnectionResult.RESULT_SUCCESS);
         this.zao();
         Iterator var1 = this.zag.values().iterator();

         while(var1.hasNext()) {
            zabs var2 = (zabs)var1.next();
            if (this.zaa(var2.zaa.getRequiredFeatures()) != null) {
               var1.remove();
            } else {
               try {
                  RegisterListenerMethod var3 = var2.zaa;
                  Api.Client var7 = this.zac;
                  TaskCompletionSource var4 = new TaskCompletionSource();
                  var3.registerListener(var7, var4);
               } catch (DeadObjectException var5) {
                  this.onConnectionSuspended(3);
                  this.zac.disconnect("DeadObjectException thrown while calling register listener method.");
                  break;
               } catch (RemoteException var6) {
                  var1.remove();
               }
            }
         }

         this.zan();
         this.zap();
      }

      private final void zan() {
         ArrayList var1 = (ArrayList)(new ArrayList(this.zab));
         int var2 = var1.size();
         int var3 = 0;

         while(var3 < var2) {
            Object var4 = var1.get(var3);
            int var5 = var3 + 1;
            com.google.android.gms.common.api.internal.zac var6 = (com.google.android.gms.common.api.internal.zac)var4;
            if (!this.zac.isConnected()) {
               break;
            }

            var3 = var5;
            if (this.zab(var6)) {
               this.zab.remove(var6);
               var3 = var5;
            }
         }

      }

      private final void zao() {
         if (this.zaj) {
            GoogleApiManager.this.zaq.removeMessages(11, this.zad);
            GoogleApiManager.this.zaq.removeMessages(9, this.zad);
            this.zaj = false;
         }

      }

      private final void zap() {
         GoogleApiManager.this.zaq.removeMessages(12, this.zad);
         GoogleApiManager.this.zaq.sendMessageDelayed(GoogleApiManager.this.zaq.obtainMessage(12, this.zad), GoogleApiManager.this.zae);
      }

      public final void onConnected(Bundle var1) {
         if (Looper.myLooper() == GoogleApiManager.this.zaq.getLooper()) {
            this.zam();
         } else {
            GoogleApiManager.this.zaq.post(new zabe(this));
         }
      }

      public final void onConnectionFailed(ConnectionResult var1) {
         this.zaa((ConnectionResult)var1, (Exception)null);
      }

      public final void onConnectionSuspended(int var1) {
         if (Looper.myLooper() == GoogleApiManager.this.zaq.getLooper()) {
            this.zaa(var1);
         } else {
            GoogleApiManager.this.zaq.post(new zabd(this, var1));
         }
      }

      public final void zaa() {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         this.zaa(GoogleApiManager.zaa);
         this.zae.zab();
         Set var1 = this.zag.keySet();
         int var2 = 0;
         ListenerHolder.ListenerKey[] var4 = (ListenerHolder.ListenerKey[])var1.toArray(new ListenerHolder.ListenerKey[0]);

         for(int var3 = var4.length; var2 < var3; ++var2) {
            this.zaa((com.google.android.gms.common.api.internal.zac)(new zah(var4[var2], new TaskCompletionSource())));
         }

         this.zac(new ConnectionResult(4));
         if (this.zac.isConnected()) {
            this.zac.onUserSignOut(new zabf(this));
         }

      }

      public final void zaa(ConnectionResult var1) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         Api.Client var2 = this.zac;
         String var3 = var2.getClass().getName();
         String var4 = String.valueOf(var1);
         StringBuilder var5 = new StringBuilder(String.valueOf(var3).length() + 25 + String.valueOf(var4).length());
         var5.append("onSignInFailed for ");
         var5.append(var3);
         var5.append(" with ");
         var5.append(var4);
         var2.disconnect(var5.toString());
         this.onConnectionFailed(var1);
      }

      public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {
         if (Looper.myLooper() == GoogleApiManager.this.zaq.getLooper()) {
            this.onConnectionFailed(var1);
         } else {
            GoogleApiManager.this.zaq.post(new zabg(this, var1));
         }
      }

      public final void zaa(com.google.android.gms.common.api.internal.zac var1) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         if (this.zac.isConnected()) {
            if (this.zab(var1)) {
               this.zap();
            } else {
               this.zab.add(var1);
            }
         } else {
            this.zab.add(var1);
            ConnectionResult var2 = this.zal;
            if (var2 != null && var2.hasResolution()) {
               this.onConnectionFailed(this.zal);
            } else {
               this.zai();
            }
         }
      }

      public final void zaa(zaj var1) {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         this.zaf.add(var1);
      }

      public final Api.Client zab() {
         return this.zac;
      }

      public final Map<ListenerHolder.ListenerKey<?>, zabs> zac() {
         return this.zag;
      }

      public final void zad() {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         this.zal = null;
      }

      public final ConnectionResult zae() {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         return this.zal;
      }

      public final void zaf() {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         if (this.zaj) {
            this.zai();
         }

      }

      public final void zag() {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         if (this.zaj) {
            this.zao();
            Status var1;
            if (GoogleApiManager.this.zai.isGooglePlayServicesAvailable(GoogleApiManager.this.zah) == 18) {
               var1 = new Status(21, "Connection timed out waiting for Google Play services update to complete.");
            } else {
               var1 = new Status(22, "API failed to connect while resuming due to an unknown error.");
            }

            this.zaa(var1);
            this.zac.disconnect("Timing out connection while resuming.");
         }

      }

      public final boolean zah() {
         return this.zaa(true);
      }

      public final void zai() {
         Preconditions.checkHandlerThread(GoogleApiManager.this.zaq);
         if (!this.zac.isConnected() && !this.zac.isConnecting()) {
            IllegalStateException var10000;
            label38: {
               int var1;
               boolean var10001;
               try {
                  var1 = GoogleApiManager.this.zaj.zaa(GoogleApiManager.this.zah, this.zac);
               } catch (IllegalStateException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label38;
               }

               if (var1 == 0) {
                  GoogleApiManager.zac var11 = GoogleApiManager.this.new zac(this.zac, this.zad);
                  if (this.zac.requiresSignIn()) {
                     ((zacb)Preconditions.checkNotNull(this.zai)).zaa((zace)var11);
                  }

                  try {
                     this.zac.connect(var11);
                     return;
                  } catch (SecurityException var7) {
                     this.zaa((ConnectionResult)(new ConnectionResult(10)), (Exception)var7);
                     return;
                  }
               }

               try {
                  ConnectionResult var10 = new ConnectionResult(var1, (PendingIntent)null);
                  String var3 = this.zac.getClass().getName();
                  String var4 = String.valueOf(var10);
                  var1 = String.valueOf(var3).length();
                  int var5 = String.valueOf(var4).length();
                  StringBuilder var6 = new StringBuilder(var1 + 35 + var5);
                  var6.append("The service for ");
                  var6.append(var3);
                  var6.append(" is not available: ");
                  var6.append(var4);
                  Log.w("GoogleApiManager", var6.toString());
                  this.onConnectionFailed(var10);
                  return;
               } catch (IllegalStateException var8) {
                  var10000 = var8;
                  var10001 = false;
               }
            }

            IllegalStateException var2 = var10000;
            this.zaa((ConnectionResult)(new ConnectionResult(10)), (Exception)var2);
         }

      }

      final boolean zaj() {
         return this.zac.isConnected();
      }

      public final boolean zak() {
         return this.zac.requiresSignIn();
      }

      public final int zal() {
         return this.zah;
      }
   }

   private static final class zab {
      private final ApiKey<?> zaa;
      private final Feature zab;

      private zab(ApiKey<?> var1, Feature var2) {
         this.zaa = var1;
         this.zab = var2;
      }

      // $FF: synthetic method
      zab(ApiKey var1, Feature var2, zabc var3) {
         this(var1, var2);
      }

      public final boolean equals(Object var1) {
         if (var1 != null && var1 instanceof GoogleApiManager.zab) {
            GoogleApiManager.zab var2 = (GoogleApiManager.zab)var1;
            if (Objects.equal(this.zaa, var2.zaa) && Objects.equal(this.zab, var2.zab)) {
               return true;
            }
         }

         return false;
      }

      public final int hashCode() {
         return Objects.hashCode(this.zaa, this.zab);
      }

      public final String toString() {
         return Objects.toStringHelper(this).add("key", this.zaa).add("feature", this.zab).toString();
      }
   }

   private final class zac implements zace, BaseGmsClient.ConnectionProgressReportCallbacks {
      private final Api.Client zab;
      private final ApiKey<?> zac;
      private IAccountAccessor zad = null;
      private Set<Scope> zae = null;
      private boolean zaf = false;

      public zac(Api.Client var2, ApiKey<?> var3) {
         this.zab = var2;
         this.zac = var3;
      }

      // $FF: synthetic method
      static ApiKey zaa(GoogleApiManager.zac var0) {
         return var0.zac;
      }

      private final void zaa() {
         if (this.zaf) {
            IAccountAccessor var1 = this.zad;
            if (var1 != null) {
               this.zab.getRemoteService(var1, this.zae);
            }
         }

      }

      // $FF: synthetic method
      static boolean zaa(GoogleApiManager.zac var0, boolean var1) {
         var0.zaf = true;
         return true;
      }

      // $FF: synthetic method
      static Api.Client zab(GoogleApiManager.zac var0) {
         return var0.zab;
      }

      // $FF: synthetic method
      static void zac(GoogleApiManager.zac var0) {
         var0.zaa();
      }

      public final void onReportServiceBinding(ConnectionResult var1) {
         GoogleApiManager.this.zaq.post(new zabi(this, var1));
      }

      public final void zaa(ConnectionResult var1) {
         GoogleApiManager.zaa var2 = (GoogleApiManager.zaa)GoogleApiManager.this.zam.get(this.zac);
         if (var2 != null) {
            var2.zaa(var1);
         }

      }

      public final void zaa(IAccountAccessor var1, Set<Scope> var2) {
         if (var1 != null && var2 != null) {
            this.zad = var1;
            this.zae = var2;
            this.zaa();
         } else {
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            this.zaa(new ConnectionResult(4));
         }
      }
   }
}

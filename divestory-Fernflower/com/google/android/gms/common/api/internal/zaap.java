package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zaap extends GoogleApiClient implements zabm {
   final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zaa = new LinkedList();
   final Map<Api.AnyClientKey<?>, Api.Client> zab;
   Set<Scope> zac;
   Set<zack> zad;
   final zacl zae;
   private final Lock zaf;
   private final com.google.android.gms.common.internal.zah zag;
   private zabn zah = null;
   private final int zai;
   private final Context zaj;
   private final Looper zak;
   private volatile boolean zal;
   private long zam;
   private long zan;
   private final zaaw zao;
   private final GoogleApiAvailability zap;
   private zabj zaq;
   private final ClientSettings zar;
   private final Map<Api<?>, Boolean> zas;
   private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> zat;
   private final ListenerHolders zau;
   private final ArrayList<zap> zav;
   private Integer zaw;
   private final com.google.android.gms.common.internal.zak zax;

   public zaap(Context var1, Lock var2, Looper var3, ClientSettings var4, GoogleApiAvailability var5, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zad, SignInOptions> var6, Map<Api<?>, Boolean> var7, List<GoogleApiClient.ConnectionCallbacks> var8, List<GoogleApiClient.OnConnectionFailedListener> var9, Map<Api.AnyClientKey<?>, Api.Client> var10, int var11, int var12, ArrayList<zap> var13) {
      long var14;
      if (ClientLibraryUtils.isPackageSide()) {
         var14 = 10000L;
      } else {
         var14 = 120000L;
      }

      this.zam = var14;
      this.zan = 5000L;
      this.zac = new HashSet();
      this.zau = new ListenerHolders();
      this.zaw = null;
      this.zad = null;
      this.zax = new zaas(this);
      this.zaj = var1;
      this.zaf = var2;
      this.zag = new com.google.android.gms.common.internal.zah(var3, this.zax);
      this.zak = var3;
      this.zao = new zaaw(this, var3);
      this.zap = var5;
      this.zai = var11;
      if (var11 >= 0) {
         this.zaw = var12;
      }

      this.zas = var7;
      this.zab = var10;
      this.zav = var13;
      this.zae = new zacl();
      Iterator var16 = var8.iterator();

      while(var16.hasNext()) {
         GoogleApiClient.ConnectionCallbacks var17 = (GoogleApiClient.ConnectionCallbacks)var16.next();
         this.zag.zaa(var17);
      }

      var16 = var9.iterator();

      while(var16.hasNext()) {
         GoogleApiClient.OnConnectionFailedListener var18 = (GoogleApiClient.OnConnectionFailedListener)var16.next();
         this.zag.zaa(var18);
      }

      this.zar = var4;
      this.zat = var6;
   }

   public static int zaa(Iterable<Api.Client> var0, boolean var1) {
      Iterator var6 = var0.iterator();
      boolean var2 = false;
      boolean var3 = false;

      while(var6.hasNext()) {
         Api.Client var4 = (Api.Client)var6.next();
         boolean var5 = var2;
         if (var4.requiresSignIn()) {
            var5 = true;
         }

         var2 = var5;
         if (var4.providesSignIn()) {
            var3 = true;
            var2 = var5;
         }
      }

      if (var2) {
         if (var3 && var1) {
            return 2;
         } else {
            return 1;
         }
      } else {
         return 3;
      }
   }

   private final void zaa(int var1) {
      Integer var2 = this.zaw;
      if (var2 == null) {
         this.zaw = var1;
      } else if (var2 != var1) {
         String var6 = zab(var1);
         String var9 = zab(this.zaw);
         StringBuilder var10 = new StringBuilder(String.valueOf(var6).length() + 51 + String.valueOf(var9).length());
         var10.append("Cannot use sign-in mode: ");
         var10.append(var6);
         var10.append(". Mode was already set to ");
         var10.append(var9);
         throw new IllegalStateException(var10.toString());
      }

      if (this.zah == null) {
         Iterator var8 = this.zab.values().iterator();
         boolean var3 = false;
         boolean var7 = false;

         while(var8.hasNext()) {
            Api.Client var4 = (Api.Client)var8.next();
            boolean var5 = var3;
            if (var4.requiresSignIn()) {
               var5 = true;
            }

            var3 = var5;
            if (var4.providesSignIn()) {
               var7 = true;
               var3 = var5;
            }
         }

         int var11 = this.zaw;
         if (var11 != 1) {
            if (var11 == 2 && var3) {
               this.zah = com.google.android.gms.common.api.internal.zaq.zaa(this.zaj, this, this.zaf, this.zak, this.zap, this.zab, this.zar, this.zas, this.zat, this.zav);
               return;
            }
         } else {
            if (!var3) {
               throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            }

            if (var7) {
               throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
         }

         this.zah = new zaax(this.zaj, this, this.zaf, this.zak, this.zap, this.zab, this.zar, this.zas, this.zat, this.zav, this);
      }
   }

   private final void zaa(GoogleApiClient var1, StatusPendingResult var2, boolean var3) {
      Common.zaa.zaa(var1).setResultCallback(new zaat(this, var2, var3, var1));
   }

   // $FF: synthetic method
   static void zaa(zaap var0) {
      var0.zae();
   }

   // $FF: synthetic method
   static void zaa(zaap var0, GoogleApiClient var1, StatusPendingResult var2, boolean var3) {
      var0.zaa(var1, var2, true);
   }

   private static String zab(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            return var0 != 3 ? "UNKNOWN" : "SIGN_IN_MODE_NONE";
         } else {
            return "SIGN_IN_MODE_OPTIONAL";
         }
      } else {
         return "SIGN_IN_MODE_REQUIRED";
      }
   }

   // $FF: synthetic method
   static void zab(zaap var0) {
      var0.zaf();
   }

   // $FF: synthetic method
   static Context zac(zaap var0) {
      return var0.zaj;
   }

   private final void zad() {
      this.zag.zab();
      ((zabn)Preconditions.checkNotNull(this.zah)).zaa();
   }

   private final void zae() {
      this.zaf.lock();

      try {
         if (this.zal) {
            this.zad();
         }
      } finally {
         this.zaf.unlock();
      }

   }

   private final void zaf() {
      this.zaf.lock();

      try {
         if (this.zab()) {
            this.zad();
         }
      } finally {
         this.zaf.unlock();
      }

   }

   private final boolean zag() {
      this.zaf.lock();

      Throwable var10000;
      label78: {
         Set var1;
         boolean var10001;
         try {
            var1 = this.zad;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label78;
         }

         if (var1 == null) {
            this.zaf.unlock();
            return false;
         }

         boolean var2;
         try {
            var2 = this.zad.isEmpty();
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label78;
         }

         this.zaf.unlock();
         return var2 ^ true;
      }

      Throwable var9 = var10000;
      this.zaf.unlock();
      throw var9;
   }

   public final ConnectionResult blockingConnect() {
      Looper var1 = Looper.myLooper();
      Looper var2 = Looper.getMainLooper();
      boolean var3 = true;
      boolean var4;
      if (var1 != var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4, "blockingConnect must not be called on the UI thread");
      this.zaf.lock();

      Throwable var10000;
      label477: {
         boolean var10001;
         label478: {
            label480: {
               label470: {
                  label469: {
                     try {
                        if (this.zai < 0) {
                           break label470;
                        }

                        if (this.zaw != null) {
                           break label469;
                        }
                     } catch (Throwable var46) {
                        var10000 = var46;
                        var10001 = false;
                        break label477;
                     }

                     var4 = false;
                     break label480;
                  }

                  var4 = var3;
                  break label480;
               }

               try {
                  if (this.zaw == null) {
                     this.zaw = zaa(this.zab.values(), false);
                     break label478;
                  }
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label477;
               }

               try {
                  if (this.zaw != 2) {
                     break label478;
                  }
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label477;
               }

               try {
                  IllegalStateException var47 = new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                  throw var47;
               } catch (Throwable var42) {
                  var10000 = var42;
                  var10001 = false;
                  break label477;
               }
            }

            try {
               Preconditions.checkState(var4, "Sign-in mode should have been set explicitly by auto-manage.");
            } catch (Throwable var43) {
               var10000 = var43;
               var10001 = false;
               break label477;
            }
         }

         ConnectionResult var49;
         try {
            this.zaa((Integer)Preconditions.checkNotNull(this.zaw));
            this.zag.zab();
            var49 = ((zabn)Preconditions.checkNotNull(this.zah)).zab();
         } catch (Throwable var41) {
            var10000 = var41;
            var10001 = false;
            break label477;
         }

         this.zaf.unlock();
         return var49;
      }

      Throwable var48 = var10000;
      this.zaf.unlock();
      throw var48;
   }

   public final ConnectionResult blockingConnect(long var1, TimeUnit var3) {
      boolean var4;
      if (Looper.myLooper() != Looper.getMainLooper()) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkState(var4, "blockingConnect must not be called on the UI thread");
      Preconditions.checkNotNull(var3, "TimeUnit must not be null");
      this.zaf.lock();

      Throwable var10000;
      label221: {
         boolean var10001;
         label225: {
            label226: {
               try {
                  if (this.zaw == null) {
                     this.zaw = zaa(this.zab.values(), false);
                     break label226;
                  }
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label221;
               }

               try {
                  if (this.zaw == 2) {
                     break label225;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label221;
               }
            }

            ConnectionResult var25;
            try {
               this.zaa((Integer)Preconditions.checkNotNull(this.zaw));
               this.zag.zab();
               var25 = ((zabn)Preconditions.checkNotNull(this.zah)).zaa(var1, var3);
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label221;
            }

            this.zaf.unlock();
            return var25;
         }

         label208:
         try {
            IllegalStateException var27 = new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            throw var27;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label208;
         }
      }

      Throwable var26 = var10000;
      this.zaf.unlock();
      throw var26;
   }

   public final PendingResult<Status> clearDefaultAccountAndReconnect() {
      Preconditions.checkState(this.isConnected(), "GoogleApiClient is not connected yet.");
      Integer var1 = this.zaw;
      boolean var2;
      if (var1 != null && var1 == 2) {
         var2 = false;
      } else {
         var2 = true;
      }

      Preconditions.checkState(var2, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
      StatusPendingResult var6 = new StatusPendingResult(this);
      if (this.zab.containsKey(Common.CLIENT_KEY)) {
         this.zaa(this, var6, false);
      } else {
         AtomicReference var3 = new AtomicReference();
         zaar var4 = new zaar(this, var3, var6);
         zaau var5 = new zaau(this, var6);
         GoogleApiClient var7 = (new GoogleApiClient.Builder(this.zaj)).addApi(Common.API).addConnectionCallbacks(var4).addOnConnectionFailedListener(var5).setHandler(this.zao).build();
         var3.set(var7);
         var7.connect();
      }

      return var6;
   }

   public final void connect() {
      this.zaf.lock();

      Throwable var10000;
      label519: {
         int var1;
         boolean var10001;
         try {
            var1 = this.zai;
         } catch (Throwable var59) {
            var10000 = var59;
            var10001 = false;
            break label519;
         }

         boolean var2 = false;
         if (var1 >= 0) {
            label490: {
               try {
                  if (this.zaw == null) {
                     break label490;
                  }
               } catch (Throwable var56) {
                  var10000 = var56;
                  var10001 = false;
                  break label519;
               }

               var2 = true;
            }

            try {
               Preconditions.checkState(var2, "Sign-in mode should have been set explicitly by auto-manage.");
            } catch (Throwable var55) {
               var10000 = var55;
               var10001 = false;
               break label519;
            }
         } else {
            label518: {
               try {
                  if (this.zaw == null) {
                     this.zaw = zaa(this.zab.values(), false);
                     break label518;
                  }
               } catch (Throwable var58) {
                  var10000 = var58;
                  var10001 = false;
                  break label519;
               }

               try {
                  if (this.zaw != 2) {
                     break label518;
                  }
               } catch (Throwable var57) {
                  var10000 = var57;
                  var10001 = false;
                  break label519;
               }

               try {
                  IllegalStateException var3 = new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                  throw var3;
               } catch (Throwable var54) {
                  var10000 = var54;
                  var10001 = false;
                  break label519;
               }
            }
         }

         try {
            this.connect((Integer)Preconditions.checkNotNull(this.zaw));
         } catch (Throwable var53) {
            var10000 = var53;
            var10001 = false;
            break label519;
         }

         this.zaf.unlock();
         return;
      }

      Throwable var60 = var10000;
      this.zaf.unlock();
      throw var60;
   }

   public final void connect(int var1) {
      this.zaf.lock();
      boolean var2 = true;
      boolean var3 = var2;
      if (var1 != 3) {
         var3 = var2;
         if (var1 != 1) {
            if (var1 == 2) {
               var3 = var2;
            } else {
               var3 = false;
            }
         }
      }

      try {
         StringBuilder var4 = new StringBuilder(33);
         var4.append("Illegal sign-in mode: ");
         var4.append(var1);
         Preconditions.checkArgument(var3, var4.toString());
         this.zaa(var1);
         this.zad();
      } finally {
         this.zaf.unlock();
      }

   }

   public final void disconnect() {
      this.zaf.lock();

      Throwable var10000;
      label306: {
         boolean var10001;
         try {
            this.zae.zaa();
            if (this.zah != null) {
               this.zah.zac();
            }
         } catch (Throwable var32) {
            var10000 = var32;
            var10001 = false;
            break label306;
         }

         Iterator var1;
         try {
            this.zau.zaa();
            var1 = this.zaa.iterator();
         } catch (Throwable var30) {
            var10000 = var30;
            var10001 = false;
            break label306;
         }

         while(true) {
            try {
               if (!var1.hasNext()) {
                  break;
               }

               BaseImplementation.ApiMethodImpl var2 = (BaseImplementation.ApiMethodImpl)var1.next();
               var2.zaa((zacn)null);
               var2.cancel();
            } catch (Throwable var31) {
               var10000 = var31;
               var10001 = false;
               break label306;
            }
         }

         zabn var33;
         try {
            this.zaa.clear();
            var33 = this.zah;
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            break label306;
         }

         if (var33 == null) {
            this.zaf.unlock();
            return;
         }

         try {
            this.zab();
            this.zag.zaa();
         } catch (Throwable var28) {
            var10000 = var28;
            var10001 = false;
            break label306;
         }

         this.zaf.unlock();
         return;
      }

      Throwable var34 = var10000;
      this.zaf.unlock();
      throw var34;
   }

   public final void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      var3.append(var1).append("mContext=").println(this.zaj);
      var3.append(var1).append("mResuming=").print(this.zal);
      var3.append(" mWorkQueue.size()=").print(this.zaa.size());
      zacl var5 = this.zae;
      var3.append(" mUnconsumedApiCalls.size()=").println(var5.zab.size());
      zabn var6 = this.zah;
      if (var6 != null) {
         var6.zaa(var1, var2, var3, var4);
      }

   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1) {
      Api var2 = var1.getApi();
      boolean var3 = this.zab.containsKey(var1.getClientKey());
      String var7;
      if (var2 != null) {
         var7 = var2.zad();
      } else {
         var7 = "the API";
      }

      StringBuilder var4 = new StringBuilder(String.valueOf(var7).length() + 65);
      var4.append("GoogleApiClient is not configured to use ");
      var4.append(var7);
      var4.append(" required for this call.");
      Preconditions.checkArgument(var3, var4.toString());
      this.zaf.lock();

      try {
         if (this.zah == null) {
            this.zaa.add(var1);
            return var1;
         }

         var1 = this.zah.zaa(var1);
      } finally {
         this.zaf.unlock();
      }

      return var1;
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T var1) {
      Api var2 = var1.getApi();
      boolean var3 = this.zab.containsKey(var1.getClientKey());
      String var37;
      if (var2 != null) {
         var37 = var2.zad();
      } else {
         var37 = "the API";
      }

      StringBuilder var4 = new StringBuilder(String.valueOf(var37).length() + 65);
      var4.append("GoogleApiClient is not configured to use ");
      var4.append(var37);
      var4.append(" required for this call.");
      Preconditions.checkArgument(var3, var4.toString());
      this.zaf.lock();

      Throwable var10000;
      label343: {
         zabn var38;
         boolean var10001;
         try {
            var38 = this.zah;
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label343;
         }

         if (var38 != null) {
            label337: {
               label347: {
                  try {
                     if (this.zal) {
                        this.zaa.add(var1);
                        break label347;
                     }
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break label337;
                  }

                  try {
                     var1 = var38.zab(var1);
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label337;
                  }

                  this.zaf.unlock();
                  return var1;
               }

               while(true) {
                  try {
                     if (!this.zaa.isEmpty()) {
                        BaseImplementation.ApiMethodImpl var39 = (BaseImplementation.ApiMethodImpl)this.zaa.remove();
                        this.zae.zaa(var39);
                        var39.setFailedResult(Status.RESULT_INTERNAL_ERROR);
                        continue;
                     }
                  } catch (Throwable var30) {
                     var10000 = var30;
                     var10001 = false;
                     break;
                  }

                  this.zaf.unlock();
                  return var1;
               }
            }
         } else {
            label339:
            try {
               IllegalStateException var36 = new IllegalStateException("GoogleApiClient is not connected yet.");
               throw var36;
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label339;
            }
         }
      }

      Throwable var35 = var10000;
      this.zaf.unlock();
      throw var35;
   }

   public final <C extends Api.Client> C getClient(Api.AnyClientKey<C> var1) {
      Api.Client var2 = (Api.Client)this.zab.get(var1);
      Preconditions.checkNotNull(var2, "Appropriate Api was not requested.");
      return var2;
   }

   public final ConnectionResult getConnectionResult(Api<?> var1) {
      this.zaf.lock();

      Throwable var10000;
      label451: {
         boolean var10001;
         label456: {
            try {
               if (!this.isConnected() && !this.zal) {
                  break label456;
               }
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label451;
            }

            ConnectionResult var48;
            label441: {
               try {
                  if (this.zab.containsKey(var1.zac())) {
                     var48 = ((zabn)Preconditions.checkNotNull(this.zah)).zaa(var1);
                     break label441;
                  }
               } catch (Throwable var43) {
                  var10000 = var43;
                  var10001 = false;
                  break label451;
               }

               try {
                  IllegalArgumentException var2 = new IllegalArgumentException(String.valueOf(var1.zad()).concat(" was never registered with GoogleApiClient"));
                  throw var2;
               } catch (Throwable var41) {
                  var10000 = var41;
                  var10001 = false;
                  break label451;
               }
            }

            if (var48 != null) {
               this.zaf.unlock();
               return var48;
            }

            ConnectionResult var45;
            label432: {
               try {
                  if (!this.zal) {
                     break label432;
                  }

                  var45 = ConnectionResult.RESULT_SUCCESS;
               } catch (Throwable var42) {
                  var10000 = var42;
                  var10001 = false;
                  break label451;
               }

               this.zaf.unlock();
               return var45;
            }

            try {
               Log.w("GoogleApiClientImpl", this.zac());
               String var46 = String.valueOf(var1.zad()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map");
               Exception var50 = new Exception();
               Log.wtf("GoogleApiClientImpl", var46, var50);
               var45 = new ConnectionResult(8, (PendingIntent)null);
            } catch (Throwable var40) {
               var10000 = var40;
               var10001 = false;
               break label451;
            }

            this.zaf.unlock();
            return var45;
         }

         label420:
         try {
            IllegalStateException var49 = new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            throw var49;
         } catch (Throwable var39) {
            var10000 = var39;
            var10001 = false;
            break label420;
         }
      }

      Throwable var47 = var10000;
      this.zaf.unlock();
      throw var47;
   }

   public final Context getContext() {
      return this.zaj;
   }

   public final Looper getLooper() {
      return this.zak;
   }

   public final boolean hasApi(Api<?> var1) {
      return this.zab.containsKey(var1.zac());
   }

   public final boolean hasConnectedApi(Api<?> var1) {
      if (!this.isConnected()) {
         return false;
      } else {
         Api.Client var2 = (Api.Client)this.zab.get(var1.zac());
         return var2 != null && var2.isConnected();
      }
   }

   public final boolean isConnected() {
      zabn var1 = this.zah;
      return var1 != null && var1.zad();
   }

   public final boolean isConnecting() {
      zabn var1 = this.zah;
      return var1 != null && var1.zae();
   }

   public final boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks var1) {
      return this.zag.zab(var1);
   }

   public final boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener var1) {
      return this.zag.zab(var1);
   }

   public final boolean maybeSignIn(SignInConnectionListener var1) {
      zabn var2 = this.zah;
      return var2 != null && var2.zaa(var1);
   }

   public final void maybeSignOut() {
      zabn var1 = this.zah;
      if (var1 != null) {
         var1.zag();
      }

   }

   public final void reconnect() {
      this.disconnect();
      this.connect();
   }

   public final void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
      this.zag.zaa(var1);
   }

   public final void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
      this.zag.zaa(var1);
   }

   public final <L> ListenerHolder<L> registerListener(L var1) {
      this.zaf.lock();

      ListenerHolder var4;
      try {
         var4 = this.zau.zaa(var1, this.zak, "NO_TYPE");
      } finally {
         this.zaf.unlock();
      }

      return var4;
   }

   public final void stopAutoManage(FragmentActivity var1) {
      LifecycleActivity var2 = new LifecycleActivity(var1);
      if (this.zai >= 0) {
         com.google.android.gms.common.api.internal.zai.zaa(var2).zaa(this.zai);
      } else {
         throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
      }
   }

   public final void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks var1) {
      this.zag.zac(var1);
   }

   public final void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener var1) {
      this.zag.zac(var1);
   }

   public final void zaa(int var1, boolean var2) {
      if (var1 == 1 && !var2 && !this.zal) {
         this.zal = true;
         if (this.zaq == null && !ClientLibraryUtils.isPackageSide()) {
            try {
               GoogleApiAvailability var3 = this.zap;
               Context var4 = this.zaj.getApplicationContext();
               zaav var5 = new zaav(this);
               this.zaq = var3.zaa((Context)var4, (zabl)var5);
            } catch (SecurityException var8) {
            }
         }

         zaaw var9 = this.zao;
         var9.sendMessageDelayed(var9.obtainMessage(1), this.zam);
         var9 = this.zao;
         var9.sendMessageDelayed(var9.obtainMessage(2), this.zan);
      }

      Set var10 = this.zae.zab;
      int var6 = 0;
      BasePendingResult[] var11 = (BasePendingResult[])var10.toArray(new BasePendingResult[0]);

      for(int var7 = var11.length; var6 < var7; ++var6) {
         var11[var6].forceFailureUnlessReady(zacl.zaa);
      }

      this.zag.zaa(var1);
      this.zag.zaa();
      if (var1 == 2) {
         this.zad();
      }

   }

   public final void zaa(Bundle var1) {
      while(!this.zaa.isEmpty()) {
         this.execute((BaseImplementation.ApiMethodImpl)this.zaa.remove());
      }

      this.zag.zaa(var1);
   }

   public final void zaa(ConnectionResult var1) {
      if (!this.zap.isPlayServicesPossiblyUpdating(this.zaj, var1.getErrorCode())) {
         this.zab();
      }

      if (!this.zal) {
         this.zag.zaa(var1);
         this.zag.zaa();
      }

   }

   public final void zaa(zack var1) {
      this.zaf.lock();

      try {
         if (this.zad == null) {
            HashSet var2 = new HashSet();
            this.zad = var2;
         }

         this.zad.add(var1);
      } finally {
         this.zaf.unlock();
      }

   }

   public final void zab(zack var1) {
      this.zaf.lock();

      label208: {
         Throwable var10000;
         label212: {
            boolean var10001;
            Set var2;
            try {
               var2 = this.zad;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label212;
            }

            Exception var23;
            if (var2 == null) {
               label194:
               try {
                  var23 = new Exception();
                  Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", var23);
               } catch (Throwable var19) {
                  var10000 = var19;
                  var10001 = false;
                  break label194;
               }
            } else {
               label213: {
                  try {
                     if (!this.zad.remove(var1)) {
                        var23 = new Exception();
                        Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", var23);
                        break label208;
                     }
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label213;
                  }

                  label196:
                  try {
                     if (!this.zag() && this.zah != null) {
                        this.zah.zaf();
                     }
                  } catch (Throwable var20) {
                     var10000 = var20;
                     var10001 = false;
                     break label196;
                  }
               }
            }
            break label208;
         }

         Throwable var24 = var10000;
         this.zaf.unlock();
         throw var24;
      }

      this.zaf.unlock();
   }

   final boolean zab() {
      if (!this.zal) {
         return false;
      } else {
         this.zal = false;
         this.zao.removeMessages(2);
         this.zao.removeMessages(1);
         zabj var1 = this.zaq;
         if (var1 != null) {
            var1.zaa();
            this.zaq = null;
         }

         return true;
      }
   }

   final String zac() {
      StringWriter var1 = new StringWriter();
      this.dump("", (FileDescriptor)null, new PrintWriter(var1), (String[])null);
      return var1.toString();
   }
}

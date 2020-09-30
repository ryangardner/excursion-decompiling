package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
   static final ThreadLocal<Boolean> zaa = new zao();
   private BasePendingResult.zaa mResultGuardian;
   private final Object zab = new Object();
   private final BasePendingResult.CallbackHandler<R> zac;
   private final WeakReference<GoogleApiClient> zad;
   private final CountDownLatch zae = new CountDownLatch(1);
   private final ArrayList<PendingResult.StatusListener> zaf = new ArrayList();
   private ResultCallback<? super R> zag;
   private final AtomicReference<zacn> zah = new AtomicReference();
   private R zai;
   private Status zaj;
   private volatile boolean zak;
   private boolean zal;
   private boolean zam;
   private ICancelToken zan;
   private volatile zack<R> zao;
   private boolean zap = false;

   @Deprecated
   BasePendingResult() {
      this.zac = new BasePendingResult.CallbackHandler(Looper.getMainLooper());
      this.zad = new WeakReference((Object)null);
   }

   @Deprecated
   protected BasePendingResult(Looper var1) {
      this.zac = new BasePendingResult.CallbackHandler(var1);
      this.zad = new WeakReference((Object)null);
   }

   protected BasePendingResult(GoogleApiClient var1) {
      Looper var2;
      if (var1 != null) {
         var2 = var1.getLooper();
      } else {
         var2 = Looper.getMainLooper();
      }

      this.zac = new BasePendingResult.CallbackHandler(var2);
      this.zad = new WeakReference(var1);
   }

   protected BasePendingResult(BasePendingResult.CallbackHandler<R> var1) {
      this.zac = (BasePendingResult.CallbackHandler)Preconditions.checkNotNull(var1, "CallbackHandler must not be null");
      this.zad = new WeakReference((Object)null);
   }

   public static void zaa(Result var0) {
      if (var0 instanceof Releasable) {
         try {
            ((Releasable)var0).release();
            return;
         } catch (RuntimeException var3) {
            String var2 = String.valueOf(var0);
            StringBuilder var4 = new StringBuilder(String.valueOf(var2).length() + 18);
            var4.append("Unable to release ");
            var4.append(var2);
            Log.w("BasePendingResult", var4.toString(), var3);
         }
      }

   }

   private static <R extends Result> ResultCallback<R> zab(ResultCallback<R> var0) {
      return var0;
   }

   private final void zab(R var1) {
      this.zai = var1;
      this.zaj = var1.getStatus();
      this.zan = null;
      this.zae.countDown();
      if (this.zal) {
         this.zag = null;
      } else {
         ResultCallback var5 = this.zag;
         if (var5 == null) {
            if (this.zai instanceof Releasable) {
               this.mResultGuardian = new BasePendingResult.zaa((zao)null);
            }
         } else {
            this.zac.removeMessages(2);
            this.zac.zaa(var5, this.zac());
         }
      }

      ArrayList var6 = (ArrayList)this.zaf;
      int var2 = var6.size();
      int var3 = 0;

      while(var3 < var2) {
         Object var4 = var6.get(var3);
         ++var3;
         ((PendingResult.StatusListener)var4).onComplete(this.zaj);
      }

      this.zaf.clear();
   }

   private final R zac() {
      Object var1 = this.zab;
      synchronized(var1){}

      Result var17;
      label164: {
         Throwable var10000;
         boolean var10001;
         label159: {
            boolean var2;
            label158: {
               label157: {
                  try {
                     if (!this.zak) {
                        break label157;
                     }
                  } catch (Throwable var15) {
                     var10000 = var15;
                     var10001 = false;
                     break label159;
                  }

                  var2 = false;
                  break label158;
               }

               var2 = true;
            }

            label151:
            try {
               Preconditions.checkState(var2, "Result has already been consumed.");
               Preconditions.checkState(this.isReady(), "Result is not ready.");
               var17 = this.zai;
               this.zai = null;
               this.zag = null;
               this.zak = true;
               break label164;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label151;
            }
         }

         while(true) {
            Throwable var3 = var10000;

            try {
               throw var3;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      }

      zacn var16 = (zacn)this.zah.getAndSet((Object)null);
      if (var16 != null) {
         var16.zaa(this);
      }

      return (Result)Preconditions.checkNotNull(var17);
   }

   public final void addStatusListener(PendingResult.StatusListener var1) {
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Callback cannot be null.");
      Object var3 = this.zab;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label220: {
         label213: {
            try {
               if (this.isReady()) {
                  var1.onComplete(this.zaj);
                  break label213;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label220;
            }

            try {
               this.zaf.add(var1);
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label220;
            }
         }

         label204:
         try {
            return;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label204;
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public final R await() {
      Preconditions.checkNotMainThread("await must not be called on the UI thread");
      boolean var1 = this.zak;
      boolean var2 = true;
      Preconditions.checkState(var1 ^ true, "Result has already been consumed");
      if (this.zao != null) {
         var2 = false;
      }

      Preconditions.checkState(var2, "Cannot await if then() has been called.");

      try {
         this.zae.await();
      } catch (InterruptedException var4) {
         this.forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
      }

      Preconditions.checkState(this.isReady(), "Result is not ready.");
      return this.zac();
   }

   public final R await(long var1, TimeUnit var3) {
      if (var1 > 0L) {
         Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
      }

      boolean var4 = this.zak;
      boolean var5 = true;
      Preconditions.checkState(var4 ^ true, "Result has already been consumed.");
      if (this.zao != null) {
         var5 = false;
      }

      Preconditions.checkState(var5, "Cannot await if then() has been called.");

      try {
         if (!this.zae.await(var1, var3)) {
            this.forceFailureUnlessReady(Status.RESULT_TIMEOUT);
         }
      } catch (InterruptedException var6) {
         this.forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
      }

      Preconditions.checkState(this.isReady(), "Result is not ready.");
      return this.zac();
   }

   public void cancel() {
      // $FF: Couldn't be decompiled
   }

   protected abstract R createFailedResult(Status var1);

   @Deprecated
   public final void forceFailureUnlessReady(Status var1) {
      Object var2 = this.zab;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (!this.isReady()) {
               this.setResult(this.createFailedResult(var1));
               this.zam = true;
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

   public boolean isCanceled() {
      // $FF: Couldn't be decompiled
   }

   public final boolean isReady() {
      return this.zae.getCount() == 0L;
   }

   protected final void setCancelToken(ICancelToken param1) {
      // $FF: Couldn't be decompiled
   }

   public final void setResult(R var1) {
      Object var2 = this.zab;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label344: {
         boolean var3;
         label339: {
            try {
               if (!this.zam && !this.zal) {
                  this.isReady();
                  var3 = this.isReady();
                  break label339;
               }
            } catch (Throwable var34) {
               var10000 = var34;
               var10001 = false;
               break label344;
            }

            try {
               zaa(var1);
               return;
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label344;
            }
         }

         boolean var4 = true;
         if (!var3) {
            var3 = true;
         } else {
            var3 = false;
         }

         label327: {
            label326: {
               try {
                  Preconditions.checkState(var3, "Results have already been set");
                  if (!this.zak) {
                     break label326;
                  }
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label344;
               }

               var3 = false;
               break label327;
            }

            var3 = var4;
         }

         label320:
         try {
            Preconditions.checkState(var3, "Result has already been consumed");
            this.zab(var1);
            return;
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            break label320;
         }
      }

      while(true) {
         Throwable var35 = var10000;

         try {
            throw var35;
         } catch (Throwable var30) {
            var10000 = var30;
            var10001 = false;
            continue;
         }
      }
   }

   public final void setResultCallback(ResultCallback<? super R> var1) {
      Object var2 = this.zab;
      synchronized(var2){}
      Throwable var10000;
      boolean var10001;
      if (var1 == null) {
         label619:
         try {
            this.zag = null;
            return;
         } catch (Throwable var70) {
            var10000 = var70;
            var10001 = false;
            break label619;
         }
      } else {
         label652: {
            boolean var3;
            try {
               var3 = this.zak;
            } catch (Throwable var76) {
               var10000 = var76;
               var10001 = false;
               break label652;
            }

            boolean var4 = true;
            if (!var3) {
               var3 = true;
            } else {
               var3 = false;
            }

            label642: {
               label641: {
                  try {
                     Preconditions.checkState(var3, "Result has already been consumed.");
                     if (this.zao == null) {
                        break label641;
                     }
                  } catch (Throwable var75) {
                     var10000 = var75;
                     var10001 = false;
                     break label652;
                  }

                  var3 = false;
                  break label642;
               }

               var3 = var4;
            }

            try {
               Preconditions.checkState(var3, "Cannot set callbacks if then() has been called.");
               if (this.isCanceled()) {
                  return;
               }
            } catch (Throwable var74) {
               var10000 = var74;
               var10001 = false;
               break label652;
            }

            label630: {
               try {
                  if (this.isReady()) {
                     this.zac.zaa(var1, this.zac());
                     break label630;
                  }
               } catch (Throwable var73) {
                  var10000 = var73;
                  var10001 = false;
                  break label652;
               }

               try {
                  this.zag = var1;
               } catch (Throwable var72) {
                  var10000 = var72;
                  var10001 = false;
                  break label652;
               }
            }

            label621:
            try {
               return;
            } catch (Throwable var71) {
               var10000 = var71;
               var10001 = false;
               break label621;
            }
         }
      }

      while(true) {
         Throwable var77 = var10000;

         try {
            throw var77;
         } catch (Throwable var69) {
            var10000 = var69;
            var10001 = false;
            continue;
         }
      }
   }

   public final void setResultCallback(ResultCallback<? super R> var1, long var2, TimeUnit var4) {
      Object var5 = this.zab;
      synchronized(var5){}
      Throwable var10000;
      boolean var10001;
      if (var1 == null) {
         label619:
         try {
            this.zag = null;
            return;
         } catch (Throwable var73) {
            var10000 = var73;
            var10001 = false;
            break label619;
         }
      } else {
         label652: {
            boolean var6;
            try {
               var6 = this.zak;
            } catch (Throwable var79) {
               var10000 = var79;
               var10001 = false;
               break label652;
            }

            boolean var7 = true;
            if (!var6) {
               var6 = true;
            } else {
               var6 = false;
            }

            label642: {
               label641: {
                  try {
                     Preconditions.checkState(var6, "Result has already been consumed.");
                     if (this.zao == null) {
                        break label641;
                     }
                  } catch (Throwable var78) {
                     var10000 = var78;
                     var10001 = false;
                     break label652;
                  }

                  var6 = false;
                  break label642;
               }

               var6 = var7;
            }

            try {
               Preconditions.checkState(var6, "Cannot set callbacks if then() has been called.");
               if (this.isCanceled()) {
                  return;
               }
            } catch (Throwable var77) {
               var10000 = var77;
               var10001 = false;
               break label652;
            }

            label630: {
               try {
                  if (this.isReady()) {
                     this.zac.zaa(var1, this.zac());
                     break label630;
                  }
               } catch (Throwable var76) {
                  var10000 = var76;
                  var10001 = false;
                  break label652;
               }

               try {
                  this.zag = var1;
                  BasePendingResult.CallbackHandler var80 = this.zac;
                  var2 = var4.toMillis(var2);
                  var80.sendMessageDelayed(var80.obtainMessage(2, this), var2);
               } catch (Throwable var75) {
                  var10000 = var75;
                  var10001 = false;
                  break label652;
               }
            }

            label621:
            try {
               return;
            } catch (Throwable var74) {
               var10000 = var74;
               var10001 = false;
               break label621;
            }
         }
      }

      while(true) {
         Throwable var81 = var10000;

         try {
            throw var81;
         } catch (Throwable var72) {
            var10000 = var72;
            var10001 = false;
            continue;
         }
      }
   }

   public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> var1) {
      Preconditions.checkState(this.zak ^ true, "Result has already been consumed.");
      Object var2 = this.zab;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label694: {
         zack var3;
         try {
            var3 = this.zao;
         } catch (Throwable var77) {
            var10000 = var77;
            var10001 = false;
            break label694;
         }

         boolean var4 = false;
         boolean var5;
         if (var3 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         label685: {
            label684: {
               try {
                  Preconditions.checkState(var5, "Cannot call then() twice.");
                  if (this.zag == null) {
                     break label684;
                  }
               } catch (Throwable var76) {
                  var10000 = var76;
                  var10001 = false;
                  break label694;
               }

               var5 = false;
               break label685;
            }

            var5 = true;
         }

         try {
            Preconditions.checkState(var5, "Cannot call then() if callbacks are set.");
         } catch (Throwable var75) {
            var10000 = var75;
            var10001 = false;
            break label694;
         }

         var5 = var4;

         label673: {
            try {
               if (this.zal) {
                  break label673;
               }
            } catch (Throwable var74) {
               var10000 = var74;
               var10001 = false;
               break label694;
            }

            var5 = true;
         }

         TransformedResult var78;
         label695: {
            try {
               Preconditions.checkState(var5, "Cannot call then() if result was canceled.");
               this.zap = true;
               var3 = new zack(this.zad);
               this.zao = var3;
               var78 = this.zao.then(var1);
               if (this.isReady()) {
                  this.zac.zaa(this.zao, this.zac());
                  break label695;
               }
            } catch (Throwable var73) {
               var10000 = var73;
               var10001 = false;
               break label694;
            }

            try {
               this.zag = this.zao;
            } catch (Throwable var72) {
               var10000 = var72;
               var10001 = false;
               break label694;
            }
         }

         label658:
         try {
            return var78;
         } catch (Throwable var71) {
            var10000 = var71;
            var10001 = false;
            break label658;
         }
      }

      while(true) {
         Throwable var79 = var10000;

         try {
            throw var79;
         } catch (Throwable var70) {
            var10000 = var70;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zaa(zacn var1) {
      this.zah.set(var1);
   }

   public final boolean zaa() {
      Object var1 = this.zab;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label183: {
         label182: {
            try {
               if ((GoogleApiClient)this.zad.get() != null && this.zap) {
                  break label182;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label183;
            }

            try {
               this.cancel();
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label183;
            }
         }

         label173:
         try {
            boolean var2 = this.isCanceled();
            return var2;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            break label173;
         }
      }

      while(true) {
         Throwable var3 = var10000;

         try {
            throw var3;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public final void zab() {
      boolean var1;
      if (!this.zap && !(Boolean)zaa.get()) {
         var1 = false;
      } else {
         var1 = true;
      }

      this.zap = var1;
   }

   public static class CallbackHandler<R extends Result> extends com.google.android.gms.internal.base.zap {
      public CallbackHandler() {
         this(Looper.getMainLooper());
      }

      public CallbackHandler(Looper var1) {
         super(var1);
      }

      public void handleMessage(Message var1) {
         int var2 = var1.what;
         if (var2 != 1) {
            if (var2 != 2) {
               var2 = var1.what;
               StringBuilder var6 = new StringBuilder(45);
               var6.append("Don't know how to handle message: ");
               var6.append(var2);
               Log.wtf("BasePendingResult", var6.toString(), new Exception());
            } else {
               ((BasePendingResult)var1.obj).forceFailureUnlessReady(Status.RESULT_TIMEOUT);
            }
         } else {
            Pair var3 = (Pair)var1.obj;
            ResultCallback var5 = (ResultCallback)var3.first;
            Result var7 = (Result)var3.second;

            try {
               var5.onResult(var7);
            } catch (RuntimeException var4) {
               BasePendingResult.zaa(var7);
               throw var4;
            }
         }
      }

      public final void zaa(ResultCallback<? super R> var1, R var2) {
         this.sendMessage(this.obtainMessage(1, new Pair((ResultCallback)Preconditions.checkNotNull(BasePendingResult.zab(var1)), var2)));
      }
   }

   private final class zaa {
      private zaa() {
      }

      // $FF: synthetic method
      zaa(zao var2) {
         this();
      }

      protected final void finalize() throws Throwable {
         BasePendingResult.zaa(BasePendingResult.this.zai);
         super.finalize();
      }
   }
}

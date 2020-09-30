package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;

public final class zack<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
   private ResultTransform<? super R, ? extends Result> zaa = null;
   private zack<? extends Result> zab = null;
   private volatile ResultCallbacks<? super R> zac = null;
   private PendingResult<R> zad = null;
   private final Object zae = new Object();
   private Status zaf = null;
   private final WeakReference<GoogleApiClient> zag;
   private final zacm zah;
   private boolean zai = false;

   public zack(WeakReference<GoogleApiClient> var1) {
      Preconditions.checkNotNull(var1, "GoogleApiClient reference must not be null");
      this.zag = var1;
      GoogleApiClient var2 = (GoogleApiClient)var1.get();
      Looper var3;
      if (var2 != null) {
         var3 = var2.getLooper();
      } else {
         var3 = Looper.getMainLooper();
      }

      this.zah = new zacm(this, var3);
   }

   // $FF: synthetic method
   static ResultTransform zaa(zack var0) {
      return var0.zaa;
   }

   private static void zaa(Result var0) {
      if (var0 instanceof Releasable) {
         try {
            ((Releasable)var0).release();
            return;
         } catch (RuntimeException var3) {
            String var2 = String.valueOf(var0);
            StringBuilder var4 = new StringBuilder(String.valueOf(var2).length() + 18);
            var4.append("Unable to release ");
            var4.append(var2);
            Log.w("TransformedResultImpl", var4.toString(), var3);
         }
      }

   }

   private final void zaa(Status param1) {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   static void zaa(zack var0, Result var1) {
      zaa(var1);
   }

   // $FF: synthetic method
   static void zaa(zack var0, Status var1) {
      var0.zaa(var1);
   }

   // $FF: synthetic method
   static zacm zab(zack var0) {
      return var0.zah;
   }

   private final void zab() {
      if (this.zaa != null || this.zac != null) {
         GoogleApiClient var1 = (GoogleApiClient)this.zag.get();
         if (!this.zai && this.zaa != null && var1 != null) {
            var1.zaa(this);
            this.zai = true;
         }

         Status var2 = this.zaf;
         if (var2 != null) {
            this.zab(var2);
         } else {
            PendingResult var3 = this.zad;
            if (var3 != null) {
               var3.setResultCallback(this);
            }

         }
      }
   }

   private final void zab(Status var1) {
      Object var2 = this.zae;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label200: {
         label204: {
            try {
               if (this.zaa != null) {
                  var1 = (Status)Preconditions.checkNotNull(this.zaa.onFailure(var1), "onFailure must not return null");
                  ((zack)Preconditions.checkNotNull(this.zab)).zaa(var1);
                  break label204;
               }
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label200;
            }

            try {
               if (this.zac()) {
                  ((ResultCallbacks)Preconditions.checkNotNull(this.zac)).onFailure(var1);
               }
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label200;
            }
         }

         label190:
         try {
            return;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label190;
         }
      }

      while(true) {
         Throwable var23 = var10000;

         try {
            throw var23;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   // $FF: synthetic method
   static WeakReference zac(zack var0) {
      return var0.zag;
   }

   private final boolean zac() {
      GoogleApiClient var1 = (GoogleApiClient)this.zag.get();
      return this.zac != null && var1 != null;
   }

   // $FF: synthetic method
   static Object zad(zack var0) {
      return var0.zae;
   }

   // $FF: synthetic method
   static zack zae(zack var0) {
      return var0.zab;
   }

   public final void andFinally(ResultCallbacks<? super R> var1) {
      Object var2 = this.zae;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label226: {
         ResultCallbacks var3;
         try {
            var3 = this.zac;
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label226;
         }

         boolean var4 = true;
         boolean var5;
         if (var3 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         label217: {
            label216: {
               try {
                  Preconditions.checkState(var5, "Cannot call andFinally() twice.");
                  if (this.zaa == null) {
                     break label216;
                  }
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label226;
               }

               var5 = false;
               break label217;
            }

            var5 = var4;
         }

         label210:
         try {
            Preconditions.checkState(var5, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zac = var1;
            this.zab();
            return;
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label210;
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   public final void onResult(R var1) {
      Object var2 = this.zae;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label320: {
         label324: {
            label326: {
               try {
                  if (var1.getStatus().isSuccess()) {
                     if (this.zaa == null) {
                        break label326;
                     }

                     ExecutorService var3 = zaca.zaa();
                     zacj var4 = new zacj(this, var1);
                     var3.submit(var4);
                     break label324;
                  }
               } catch (Throwable var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label320;
               }

               try {
                  this.zaa(var1.getStatus());
                  zaa(var1);
                  break label324;
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label320;
               }
            }

            try {
               if (this.zac()) {
                  ((ResultCallbacks)Preconditions.checkNotNull(this.zac)).onSuccess(var1);
               }
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label320;
            }
         }

         label301:
         try {
            return;
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            break label301;
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

   public final <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> var1) {
      Object var2 = this.zae;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label226: {
         ResultTransform var3;
         try {
            var3 = this.zaa;
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label226;
         }

         boolean var4 = true;
         boolean var5;
         if (var3 == null) {
            var5 = true;
         } else {
            var5 = false;
         }

         label217: {
            label216: {
               try {
                  Preconditions.checkState(var5, "Cannot call then() twice.");
                  if (this.zac == null) {
                     break label216;
                  }
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label226;
               }

               var5 = false;
               break label217;
            }

            var5 = var4;
         }

         label210:
         try {
            Preconditions.checkState(var5, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zaa = var1;
            zack var27 = new zack(this.zag);
            this.zab = var27;
            this.zab();
            return var27;
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label210;
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   final void zaa() {
      this.zac = null;
   }

   public final void zaa(PendingResult<?> param1) {
      // $FF: Couldn't be decompiled
   }
}

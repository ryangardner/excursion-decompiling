package com.google.android.gms.tasks;

import android.app.Activity;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

final class zzu<TResult> extends Task<TResult> {
   private final Object zza = new Object();
   private final zzq<TResult> zzb = new zzq();
   private boolean zzc;
   private volatile boolean zzd;
   private TResult zze;
   private Exception zzf;

   private final void zzb() {
      Preconditions.checkState(this.zzc, "Task is not yet complete");
   }

   private final void zzc() {
      if (this.zzc) {
         throw DuplicateTaskCompletionException.of(this);
      }
   }

   private final void zzd() {
      if (this.zzd) {
         throw new CancellationException("Task is already canceled.");
      }
   }

   private final void zze() {
      Object var1 = this.zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            if (!this.zzc) {
               return;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label137;
         }

         try {
            ;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label137;
         }

         this.zzb.zza((Task)this);
         return;
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public final Task<TResult> addOnCanceledListener(Activity var1, OnCanceledListener var2) {
      zzh var3 = new zzh(zzv.zza(TaskExecutors.MAIN_THREAD), var2);
      this.zzb.zza((zzr)var3);
      zzu.zza.zza(var1).zza((zzr)var3);
      this.zze();
      return this;
   }

   public final Task<TResult> addOnCanceledListener(OnCanceledListener var1) {
      return this.addOnCanceledListener(TaskExecutors.MAIN_THREAD, var1);
   }

   public final Task<TResult> addOnCanceledListener(Executor var1, OnCanceledListener var2) {
      this.zzb.zza((zzr)(new zzh(zzv.zza(var1), var2)));
      this.zze();
      return this;
   }

   public final Task<TResult> addOnCompleteListener(Activity var1, OnCompleteListener<TResult> var2) {
      zzi var3 = new zzi(zzv.zza(TaskExecutors.MAIN_THREAD), var2);
      this.zzb.zza((zzr)var3);
      zzu.zza.zza(var1).zza((zzr)var3);
      this.zze();
      return this;
   }

   public final Task<TResult> addOnCompleteListener(OnCompleteListener<TResult> var1) {
      return this.addOnCompleteListener(TaskExecutors.MAIN_THREAD, var1);
   }

   public final Task<TResult> addOnCompleteListener(Executor var1, OnCompleteListener<TResult> var2) {
      this.zzb.zza((zzr)(new zzi(zzv.zza(var1), var2)));
      this.zze();
      return this;
   }

   public final Task<TResult> addOnFailureListener(Activity var1, OnFailureListener var2) {
      zzl var3 = new zzl(zzv.zza(TaskExecutors.MAIN_THREAD), var2);
      this.zzb.zza((zzr)var3);
      zzu.zza.zza(var1).zza((zzr)var3);
      this.zze();
      return this;
   }

   public final Task<TResult> addOnFailureListener(OnFailureListener var1) {
      return this.addOnFailureListener(TaskExecutors.MAIN_THREAD, var1);
   }

   public final Task<TResult> addOnFailureListener(Executor var1, OnFailureListener var2) {
      this.zzb.zza((zzr)(new zzl(zzv.zza(var1), var2)));
      this.zze();
      return this;
   }

   public final Task<TResult> addOnSuccessListener(Activity var1, OnSuccessListener<? super TResult> var2) {
      zzm var3 = new zzm(zzv.zza(TaskExecutors.MAIN_THREAD), var2);
      this.zzb.zza((zzr)var3);
      zzu.zza.zza(var1).zza((zzr)var3);
      this.zze();
      return this;
   }

   public final Task<TResult> addOnSuccessListener(OnSuccessListener<? super TResult> var1) {
      return this.addOnSuccessListener(TaskExecutors.MAIN_THREAD, var1);
   }

   public final Task<TResult> addOnSuccessListener(Executor var1, OnSuccessListener<? super TResult> var2) {
      this.zzb.zza((zzr)(new zzm(zzv.zza(var1), var2)));
      this.zze();
      return this;
   }

   public final <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> var1) {
      return this.continueWith(TaskExecutors.MAIN_THREAD, var1);
   }

   public final <TContinuationResult> Task<TContinuationResult> continueWith(Executor var1, Continuation<TResult, TContinuationResult> var2) {
      zzu var3 = new zzu();
      this.zzb.zza((zzr)(new zzc(zzv.zza(var1), var2, var3)));
      this.zze();
      return var3;
   }

   public final <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> var1) {
      return this.continueWithTask(TaskExecutors.MAIN_THREAD, var1);
   }

   public final <TContinuationResult> Task<TContinuationResult> continueWithTask(Executor var1, Continuation<TResult, Task<TContinuationResult>> var2) {
      zzu var3 = new zzu();
      this.zzb.zza((zzr)(new zzd(zzv.zza(var1), var2, var3)));
      this.zze();
      return var3;
   }

   public final Exception getException() {
      // $FF: Couldn't be decompiled
   }

   public final TResult getResult() {
      Object var1 = this.zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            this.zzb();
            this.zzd();
            if (this.zzf == null) {
               Object var16 = this.zze;
               return var16;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label116:
         try {
            RuntimeExecutionException var15 = new RuntimeExecutionException(this.zzf);
            throw var15;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label116;
         }
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public final <X extends Throwable> TResult getResult(Class<X> var1) throws X {
      Object var2 = this.zza;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label204: {
         label203: {
            try {
               this.zzb();
               this.zzd();
               if (!var1.isInstance(this.zzf)) {
                  if (this.zzf != null) {
                     break label203;
                  }

                  Object var23 = this.zze;
                  return var23;
               }
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break label204;
            }

            try {
               throw (Throwable)var1.cast(this.zzf);
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label204;
            }
         }

         label192:
         try {
            RuntimeExecutionException var25 = new RuntimeExecutionException(this.zzf);
            throw var25;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label192;
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   public final boolean isCanceled() {
      return this.zzd;
   }

   public final boolean isComplete() {
      // $FF: Couldn't be decompiled
   }

   public final boolean isSuccessful() {
      Object var1 = this.zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label158: {
         boolean var2;
         label157: {
            label156: {
               try {
                  if (this.zzc && !this.zzd && this.zzf == null) {
                     break label156;
                  }
               } catch (Throwable var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label158;
               }

               var2 = false;
               break label157;
            }

            var2 = true;
         }

         label146:
         try {
            return var2;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label146;
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

   public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(SuccessContinuation<TResult, TContinuationResult> var1) {
      return this.onSuccessTask(TaskExecutors.MAIN_THREAD, var1);
   }

   public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor var1, SuccessContinuation<TResult, TContinuationResult> var2) {
      zzu var3 = new zzu();
      this.zzb.zza((zzr)(new zzp(zzv.zza(var1), var2, var3)));
      this.zze();
      return var3;
   }

   public final void zza(Exception param1) {
      // $FF: Couldn't be decompiled
   }

   public final void zza(TResult param1) {
      // $FF: Couldn't be decompiled
   }

   public final boolean zza() {
      Object var1 = this.zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            if (this.zzc) {
               return false;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label137;
         }

         try {
            this.zzc = true;
            this.zzd = true;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label137;
         }

         this.zzb.zza((Task)this);
         return true;
      }

      while(true) {
         Throwable var2 = var10000;

         try {
            throw var2;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public final boolean zzb(Exception var1) {
      Preconditions.checkNotNull(var1, "Exception must not be null");
      Object var2 = this.zza;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            if (this.zzc) {
               return false;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label137;
         }

         try {
            this.zzc = true;
            this.zzf = var1;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label137;
         }

         this.zzb.zza((Task)this);
         return true;
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

   public final boolean zzb(TResult var1) {
      Object var2 = this.zza;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label137: {
         try {
            if (this.zzc) {
               return false;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label137;
         }

         try {
            this.zzc = true;
            this.zze = var1;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label137;
         }

         this.zzb.zza((Task)this);
         return true;
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

   private static class zza extends LifecycleCallback {
      private final List<WeakReference<zzr<?>>> zza = new ArrayList();

      private zza(LifecycleFragment var1) {
         super(var1);
         this.mLifecycleFragment.addCallback("TaskOnStopCallback", this);
      }

      public static zzu.zza zza(Activity var0) {
         LifecycleFragment var1 = getFragment(var0);
         zzu.zza var2 = (zzu.zza)var1.getCallbackOrNull("TaskOnStopCallback", zzu.zza.class);
         zzu.zza var3 = var2;
         if (var2 == null) {
            var3 = new zzu.zza(var1);
         }

         return var3;
      }

      public void onStop() {
         List var1 = this.zza;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label277: {
            Iterator var2;
            try {
               var2 = this.zza.iterator();
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break label277;
            }

            while(true) {
               zzr var3;
               try {
                  if (!var2.hasNext()) {
                     break;
                  }

                  var3 = (zzr)((WeakReference)var2.next()).get();
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break label277;
               }

               if (var3 != null) {
                  try {
                     var3.zza();
                  } catch (Throwable var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label277;
                  }
               }
            }

            label259:
            try {
               this.zza.clear();
               return;
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label259;
            }
         }

         while(true) {
            Throwable var34 = var10000;

            try {
               throw var34;
            } catch (Throwable var29) {
               var10000 = var29;
               var10001 = false;
               continue;
            }
         }
      }

      public final <T> void zza(zzr<T> param1) {
         // $FF: Couldn't be decompiled
      }
   }
}

package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {
   private Tasks() {
   }

   public static <TResult> TResult await(Task<TResult> var0) throws ExecutionException, InterruptedException {
      Preconditions.checkNotMainThread();
      Preconditions.checkNotNull(var0, "Task must not be null");
      if (var0.isComplete()) {
         return zza(var0);
      } else {
         Tasks.zzb var1 = new Tasks.zzb((zzy)null);
         zza(var0, var1);
         var1.zza();
         return zza(var0);
      }
   }

   public static <TResult> TResult await(Task<TResult> var0, long var1, TimeUnit var3) throws ExecutionException, InterruptedException, TimeoutException {
      Preconditions.checkNotMainThread();
      Preconditions.checkNotNull(var0, "Task must not be null");
      Preconditions.checkNotNull(var3, "TimeUnit must not be null");
      if (var0.isComplete()) {
         return zza(var0);
      } else {
         Tasks.zzb var4 = new Tasks.zzb((zzy)null);
         zza(var0, var4);
         if (var4.zza(var1, var3)) {
            return zza(var0);
         } else {
            throw new TimeoutException("Timed out waiting for Task");
         }
      }
   }

   @Deprecated
   public static <TResult> Task<TResult> call(Callable<TResult> var0) {
      return call(TaskExecutors.MAIN_THREAD, var0);
   }

   @Deprecated
   public static <TResult> Task<TResult> call(Executor var0, Callable<TResult> var1) {
      Preconditions.checkNotNull(var0, "Executor must not be null");
      Preconditions.checkNotNull(var1, "Callback must not be null");
      zzu var2 = new zzu();
      var0.execute(new zzy(var2, var1));
      return var2;
   }

   public static <TResult> Task<TResult> forCanceled() {
      zzu var0 = new zzu();
      var0.zza();
      return var0;
   }

   public static <TResult> Task<TResult> forException(Exception var0) {
      zzu var1 = new zzu();
      var1.zza(var0);
      return var1;
   }

   public static <TResult> Task<TResult> forResult(TResult var0) {
      zzu var1 = new zzu();
      var1.zza(var0);
      return var1;
   }

   public static Task<Void> whenAll(Collection<? extends Task<?>> var0) {
      if (var0 != null && !var0.isEmpty()) {
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            if ((Task)var1.next() == null) {
               throw new NullPointerException("null tasks are not accepted");
            }
         }

         zzu var2 = new zzu();
         Tasks.zzc var4 = new Tasks.zzc(var0.size(), var2);
         Iterator var3 = var0.iterator();

         while(var3.hasNext()) {
            zza((Task)var3.next(), var4);
         }

         return var2;
      } else {
         return forResult((Object)null);
      }
   }

   public static Task<Void> whenAll(Task<?>... var0) {
      return var0 != null && var0.length != 0 ? whenAll((Collection)Arrays.asList(var0)) : forResult((Object)null);
   }

   public static Task<List<Task<?>>> whenAllComplete(Collection<? extends Task<?>> var0) {
      return var0 != null && !var0.isEmpty() ? whenAll(var0).continueWithTask(new zzz(var0)) : forResult(Collections.emptyList());
   }

   public static Task<List<Task<?>>> whenAllComplete(Task<?>... var0) {
      return var0 != null && var0.length != 0 ? whenAllComplete((Collection)Arrays.asList(var0)) : forResult(Collections.emptyList());
   }

   public static <TResult> Task<List<TResult>> whenAllSuccess(Collection<? extends Task<?>> var0) {
      return var0 != null && !var0.isEmpty() ? whenAll(var0).continueWith(new zzaa(var0)) : forResult(Collections.emptyList());
   }

   public static <TResult> Task<List<TResult>> whenAllSuccess(Task<?>... var0) {
      return var0 != null && var0.length != 0 ? whenAllSuccess((Collection)Arrays.asList(var0)) : forResult(Collections.emptyList());
   }

   private static <TResult> TResult zza(Task<TResult> var0) throws ExecutionException {
      if (var0.isSuccessful()) {
         return var0.getResult();
      } else if (var0.isCanceled()) {
         throw new CancellationException("Task is already canceled");
      } else {
         throw new ExecutionException(var0.getException());
      }
   }

   private static <T> void zza(Task<T> var0, Tasks.zza<? super T> var1) {
      var0.addOnSuccessListener((Executor)TaskExecutors.zza, var1);
      var0.addOnFailureListener((Executor)TaskExecutors.zza, var1);
      var0.addOnCanceledListener((Executor)TaskExecutors.zza, var1);
   }

   interface zza<T> extends OnCanceledListener, OnFailureListener, OnSuccessListener<T> {
   }

   private static final class zzb implements Tasks.zza {
      private final CountDownLatch zza;

      private zzb() {
         this.zza = new CountDownLatch(1);
      }

      // $FF: synthetic method
      zzb(zzy var1) {
         this();
      }

      public final void onCanceled() {
         this.zza.countDown();
      }

      public final void onFailure(Exception var1) {
         this.zza.countDown();
      }

      public final void onSuccess(Object var1) {
         this.zza.countDown();
      }

      public final void zza() throws InterruptedException {
         this.zza.await();
      }

      public final boolean zza(long var1, TimeUnit var3) throws InterruptedException {
         return this.zza.await(var1, var3);
      }
   }

   private static final class zzc implements Tasks.zza {
      private final Object zza = new Object();
      private final int zzb;
      private final zzu<Void> zzc;
      private int zzd;
      private int zze;
      private int zzf;
      private Exception zzg;
      private boolean zzh;

      public zzc(int var1, zzu<Void> var2) {
         this.zzb = var1;
         this.zzc = var2;
      }

      private final void zza() {
         if (this.zzd + this.zze + this.zzf == this.zzb) {
            if (this.zzg != null) {
               zzu var1 = this.zzc;
               int var2 = this.zze;
               int var3 = this.zzb;
               StringBuilder var4 = new StringBuilder(54);
               var4.append(var2);
               var4.append(" out of ");
               var4.append(var3);
               var4.append(" underlying tasks failed");
               var1.zza((Exception)(new ExecutionException(var4.toString(), this.zzg)));
               return;
            }

            if (this.zzh) {
               this.zzc.zza();
               return;
            }

            this.zzc.zza((Object)null);
         }

      }

      public final void onCanceled() {
         // $FF: Couldn't be decompiled
      }

      public final void onFailure(Exception param1) {
         // $FF: Couldn't be decompiled
      }

      public final void onSuccess(Object param1) {
         // $FF: Couldn't be decompiled
      }
   }
}

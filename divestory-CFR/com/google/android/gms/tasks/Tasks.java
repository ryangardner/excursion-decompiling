/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzaa;
import com.google.android.gms.tasks.zzu;
import com.google.android.gms.tasks.zzy;
import com.google.android.gms.tasks.zzz;
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

    public static <TResult> TResult await(Task<TResult> task) throws ExecutionException, InterruptedException {
        Preconditions.checkNotMainThread();
        Preconditions.checkNotNull(task, "Task must not be null");
        if (task.isComplete()) {
            return Tasks.zza(task);
        }
        zzb zzb2 = new zzb(null);
        Tasks.zza(task, zzb2);
        zzb2.zza();
        return Tasks.zza(task);
    }

    public static <TResult> TResult await(Task<TResult> task, long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        Preconditions.checkNotMainThread();
        Preconditions.checkNotNull(task, "Task must not be null");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return Tasks.zza(task);
        }
        zzb zzb2 = new zzb(null);
        Tasks.zza(task, zzb2);
        if (!zzb2.zza(l, timeUnit)) throw new TimeoutException("Timed out waiting for Task");
        return Tasks.zza(task);
    }

    @Deprecated
    public static <TResult> Task<TResult> call(Callable<TResult> callable) {
        return Tasks.call(TaskExecutors.MAIN_THREAD, callable);
    }

    @Deprecated
    public static <TResult> Task<TResult> call(Executor executor, Callable<TResult> callable) {
        Preconditions.checkNotNull(executor, "Executor must not be null");
        Preconditions.checkNotNull(callable, "Callback must not be null");
        zzu zzu2 = new zzu();
        executor.execute(new zzy(zzu2, callable));
        return zzu2;
    }

    public static <TResult> Task<TResult> forCanceled() {
        zzu zzu2 = new zzu();
        zzu2.zza();
        return zzu2;
    }

    public static <TResult> Task<TResult> forException(Exception exception) {
        zzu zzu2 = new zzu();
        zzu2.zza(exception);
        return zzu2;
    }

    public static <TResult> Task<TResult> forResult(TResult TResult) {
        zzu<TResult> zzu2 = new zzu<TResult>();
        zzu2.zza(TResult);
        return zzu2;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> object) {
        if (object == null) return Tasks.forResult(null);
        if (object.isEmpty()) {
            return Tasks.forResult(null);
        }
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            if (object2.next() == null) throw new NullPointerException("null tasks are not accepted");
        }
        zzu<Void> zzu2 = new zzu<Void>();
        object2 = new zzc(object.size(), zzu2);
        object = object.iterator();
        while (object.hasNext()) {
            Tasks.zza((Task)object.next(), object2);
        }
        return zzu2;
    }

    public static Task<Void> whenAll(Task<?> ... arrtask) {
        if (arrtask == null) return Tasks.forResult(null);
        if (arrtask.length != 0) return Tasks.whenAll(Arrays.asList(arrtask));
        return Tasks.forResult(null);
    }

    public static Task<List<Task<?>>> whenAllComplete(Collection<? extends Task<?>> collection) {
        if (collection == null) return Tasks.forResult(Collections.emptyList());
        if (!collection.isEmpty()) return Tasks.whenAll(collection).continueWithTask(new zzz(collection));
        return Tasks.forResult(Collections.emptyList());
    }

    public static Task<List<Task<?>>> whenAllComplete(Task<?> ... arrtask) {
        if (arrtask == null) return Tasks.forResult(Collections.emptyList());
        if (arrtask.length != 0) return Tasks.whenAllComplete(Arrays.asList(arrtask));
        return Tasks.forResult(Collections.emptyList());
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Collection<? extends Task<?>> collection) {
        if (collection == null) return Tasks.forResult(Collections.emptyList());
        if (!collection.isEmpty()) return Tasks.whenAll(collection).continueWith(new zzaa(collection));
        return Tasks.forResult(Collections.emptyList());
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Task<?> ... arrtask) {
        if (arrtask == null) return Tasks.forResult(Collections.emptyList());
        if (arrtask.length != 0) return Tasks.whenAllSuccess(Arrays.asList(arrtask));
        return Tasks.forResult(Collections.emptyList());
    }

    private static <TResult> TResult zza(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        if (!task.isCanceled()) throw new ExecutionException(task.getException());
        throw new CancellationException("Task is already canceled");
    }

    private static <T> void zza(Task<T> task, zza<? super T> zza2) {
        task.addOnSuccessListener(TaskExecutors.zza, zza2);
        task.addOnFailureListener(TaskExecutors.zza, zza2);
        task.addOnCanceledListener(TaskExecutors.zza, zza2);
    }

    static interface zza<T>
    extends OnCanceledListener,
    OnFailureListener,
    OnSuccessListener<T> {
    }

    private static final class zzb
    implements zza {
        private final CountDownLatch zza = new CountDownLatch(1);

        private zzb() {
        }

        /* synthetic */ zzb(zzy zzy2) {
            this();
        }

        @Override
        public final void onCanceled() {
            this.zza.countDown();
        }

        @Override
        public final void onFailure(Exception exception) {
            this.zza.countDown();
        }

        @Override
        public final void onSuccess(Object object) {
            this.zza.countDown();
        }

        public final void zza() throws InterruptedException {
            this.zza.await();
        }

        public final boolean zza(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.zza.await(l, timeUnit);
        }
    }

    private static final class zzc
    implements zza {
        private final Object zza = new Object();
        private final int zzb;
        private final zzu<Void> zzc;
        private int zzd;
        private int zze;
        private int zzf;
        private Exception zzg;
        private boolean zzh;

        public zzc(int n, zzu<Void> zzu2) {
            this.zzb = n;
            this.zzc = zzu2;
        }

        private final void zza() {
            if (this.zzd + this.zze + this.zzf != this.zzb) return;
            if (this.zzg != null) {
                zzu<Void> zzu2 = this.zzc;
                int n = this.zze;
                int n2 = this.zzb;
                StringBuilder stringBuilder = new StringBuilder(54);
                stringBuilder.append(n);
                stringBuilder.append(" out of ");
                stringBuilder.append(n2);
                stringBuilder.append(" underlying tasks failed");
                zzu2.zza(new ExecutionException(stringBuilder.toString(), this.zzg));
                return;
            }
            if (this.zzh) {
                this.zzc.zza();
                return;
            }
            this.zzc.zza((Void)null);
        }

        @Override
        public final void onCanceled() {
            Object object = this.zza;
            synchronized (object) {
                ++this.zzf;
                this.zzh = true;
                this.zza();
                return;
            }
        }

        @Override
        public final void onFailure(Exception exception) {
            Object object = this.zza;
            synchronized (object) {
                ++this.zze;
                this.zzg = exception;
                this.zza();
                return;
            }
        }

        @Override
        public final void onSuccess(Object object) {
            Object object2 = this.zza;
            synchronized (object2) {
                ++this.zzd;
                this.zza();
                return;
            }
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.tasks;

import android.app.Activity;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.DuplicateTaskCompletionException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzc;
import com.google.android.gms.tasks.zzd;
import com.google.android.gms.tasks.zzh;
import com.google.android.gms.tasks.zzi;
import com.google.android.gms.tasks.zzl;
import com.google.android.gms.tasks.zzm;
import com.google.android.gms.tasks.zzp;
import com.google.android.gms.tasks.zzq;
import com.google.android.gms.tasks.zzr;
import com.google.android.gms.tasks.zzv;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

final class zzu<TResult>
extends Task<TResult> {
    private final Object zza = new Object();
    private final zzq<TResult> zzb = new zzq();
    private boolean zzc;
    private volatile boolean zzd;
    private TResult zze;
    private Exception zzf;

    zzu() {
    }

    private final void zzb() {
        Preconditions.checkState(this.zzc, "Task is not yet complete");
    }

    private final void zzc() {
        if (this.zzc) throw DuplicateTaskCompletionException.of(this);
    }

    private final void zzd() {
        if (this.zzd) throw new CancellationException("Task is already canceled.");
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final void zze() {
        Object object = this.zza;
        synchronized (object) {
            if (!this.zzc) {
                return;
            }
        }
        this.zzb.zza(this);
    }

    @Override
    public final Task<TResult> addOnCanceledListener(Activity activity, OnCanceledListener object) {
        object = new zzh(zzv.zza(TaskExecutors.MAIN_THREAD), (OnCanceledListener)object);
        this.zzb.zza((zzr<TResult>)object);
        zza.zza(activity).zza(object);
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnCanceledListener(OnCanceledListener onCanceledListener) {
        return ((Task)this).addOnCanceledListener(TaskExecutors.MAIN_THREAD, onCanceledListener);
    }

    @Override
    public final Task<TResult> addOnCanceledListener(Executor executor, OnCanceledListener onCanceledListener) {
        this.zzb.zza(new zzh(zzv.zza(executor), onCanceledListener));
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnCompleteListener(Activity activity, OnCompleteListener<TResult> object) {
        object = new zzi<TResult>(zzv.zza(TaskExecutors.MAIN_THREAD), (OnCompleteListener<TResult>)object);
        this.zzb.zza((zzr<TResult>)object);
        zza.zza(activity).zza(object);
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnCompleteListener(OnCompleteListener<TResult> onCompleteListener) {
        return ((Task)this).addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @Override
    public final Task<TResult> addOnCompleteListener(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.zzb.zza(new zzi<TResult>(zzv.zza(executor), onCompleteListener));
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnFailureListener(Activity activity, OnFailureListener object) {
        object = new zzl(zzv.zza(TaskExecutors.MAIN_THREAD), (OnFailureListener)object);
        this.zzb.zza((zzr<TResult>)object);
        zza.zza(activity).zza(object);
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnFailureListener(OnFailureListener onFailureListener) {
        return ((Task)this).addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @Override
    public final Task<TResult> addOnFailureListener(Executor executor, OnFailureListener onFailureListener) {
        this.zzb.zza(new zzl(zzv.zza(executor), onFailureListener));
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnSuccessListener(Activity activity, OnSuccessListener<? super TResult> object) {
        object = new zzm<TResult>(zzv.zza(TaskExecutors.MAIN_THREAD), (OnSuccessListener<? super TResult>)object);
        this.zzb.zza((zzr<TResult>)object);
        zza.zza(activity).zza(object);
        this.zze();
        return this;
    }

    @Override
    public final Task<TResult> addOnSuccessListener(OnSuccessListener<? super TResult> onSuccessListener) {
        return ((Task)this).addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @Override
    public final Task<TResult> addOnSuccessListener(Executor executor, OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzb.zza(new zzm<TResult>(zzv.zza(executor), onSuccessListener));
        this.zze();
        return this;
    }

    @Override
    public final <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation2) {
        return ((Task)this).continueWith(TaskExecutors.MAIN_THREAD, continuation2);
    }

    @Override
    public final <TContinuationResult> Task<TContinuationResult> continueWith(Executor executor, Continuation<TResult, TContinuationResult> continuation2) {
        zzu<TResult> zzu2 = new zzu<TResult>();
        this.zzb.zza(new zzc<TResult, TContinuationResult>(zzv.zza(executor), continuation2, zzu2));
        this.zze();
        return zzu2;
    }

    @Override
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation2) {
        return ((Task)this).continueWithTask(TaskExecutors.MAIN_THREAD, continuation2);
    }

    @Override
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(Executor executor, Continuation<TResult, Task<TContinuationResult>> continuation2) {
        zzu<TResult> zzu2 = new zzu<TResult>();
        this.zzb.zza(new zzd<TResult, TContinuationResult>(zzv.zza(executor), continuation2, zzu2));
        this.zze();
        return zzu2;
    }

    @Override
    public final Exception getException() {
        Object object = this.zza;
        synchronized (object) {
            return this.zzf;
        }
    }

    @Override
    public final TResult getResult() {
        Object object = this.zza;
        synchronized (object) {
            this.zzb();
            this.zzd();
            if (this.zzf == null) {
                TResult TResult = this.zze;
                return TResult;
            }
            RuntimeExecutionException runtimeExecutionException = new RuntimeExecutionException(this.zzf);
            throw runtimeExecutionException;
        }
    }

    @Override
    public final <X extends Throwable> TResult getResult(Class<X> serializable) throws Throwable {
        Object object = this.zza;
        synchronized (object) {
            this.zzb();
            this.zzd();
            if (serializable.isInstance(this.zzf)) throw (Throwable)serializable.cast(this.zzf);
            if (this.zzf == null) {
                serializable = this.zze;
                return (TResult)serializable;
            }
            serializable = new Serializable(this.zzf);
            throw serializable;
        }
    }

    @Override
    public final boolean isCanceled() {
        return this.zzd;
    }

    @Override
    public final boolean isComplete() {
        Object object = this.zza;
        synchronized (object) {
            return this.zzc;
        }
    }

    @Override
    public final boolean isSuccessful() {
        Object object = this.zza;
        synchronized (object) {
            if (!this.zzc) return false;
            if (this.zzd) return false;
            if (this.zzf != null) return false;
            return true;
        }
    }

    @Override
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return ((Task)this).onSuccessTask(TaskExecutors.MAIN_THREAD, successContinuation);
    }

    @Override
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        zzu<TResult> zzu2 = new zzu<TResult>();
        this.zzb.zza(new zzp<TResult, TContinuationResult>(zzv.zza(executor), successContinuation, zzu2));
        this.zze();
        return zzu2;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void zza(Exception exception) {
        Preconditions.checkNotNull(exception, "Exception must not be null");
        Object object = this.zza;
        synchronized (object) {
            this.zzc();
            this.zzc = true;
            this.zzf = exception;
        }
        this.zzb.zza(this);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void zza(TResult TResult) {
        Object object = this.zza;
        synchronized (object) {
            this.zzc();
            this.zzc = true;
            this.zze = TResult;
        }
        this.zzb.zza(this);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final boolean zza() {
        Object object = this.zza;
        synchronized (object) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zzd = true;
        }
        this.zzb.zza(this);
        return true;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final boolean zzb(Exception exception) {
        Preconditions.checkNotNull(exception, "Exception must not be null");
        Object object = this.zza;
        synchronized (object) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zzf = exception;
        }
        this.zzb.zza(this);
        return true;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final boolean zzb(TResult TResult) {
        Object object = this.zza;
        synchronized (object) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zze = TResult;
        }
        this.zzb.zza(this);
        return true;
    }

    private static class zza
    extends LifecycleCallback {
        private final List<WeakReference<zzr<?>>> zza = new ArrayList();

        private zza(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("TaskOnStopCallback", this);
        }

        public static zza zza(Activity object) {
            LifecycleFragment lifecycleFragment = zza.getFragment(object);
            zza zza2 = lifecycleFragment.getCallbackOrNull("TaskOnStopCallback", zza.class);
            object = zza2;
            if (zza2 != null) return object;
            return new zza(lifecycleFragment);
        }

        @Override
        public void onStop() {
            List<WeakReference<zzr<?>>> list = this.zza;
            synchronized (list) {
                Iterator<WeakReference<zzr<?>>> iterator2 = this.zza.iterator();
                do {
                    if (!iterator2.hasNext()) {
                        this.zza.clear();
                        return;
                    }
                    zzr zzr2 = (zzr)iterator2.next().get();
                    if (zzr2 == null) continue;
                    zzr2.zza();
                } while (true);
            }
        }

        public final <T> void zza(zzr<T> zzr2) {
            List<WeakReference<zzr<?>>> list = this.zza;
            synchronized (list) {
                List<WeakReference<zzr<?>>> list2 = this.zza;
                WeakReference<zzr<T>> weakReference = new WeakReference<zzr<T>>(zzr2);
                list2.add(weakReference);
                return;
            }
        }
    }

}


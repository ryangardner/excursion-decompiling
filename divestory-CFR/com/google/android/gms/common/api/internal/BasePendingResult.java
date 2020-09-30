/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
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
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.api.internal.zacn;
import com.google.android.gms.common.api.internal.zao;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BasePendingResult<R extends Result>
extends PendingResult<R> {
    static final ThreadLocal<Boolean> zaa = new zao();
    private zaa mResultGuardian;
    private final Object zab = new Object();
    private final CallbackHandler<R> zac;
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
        this.zac = new CallbackHandler(Looper.getMainLooper());
        this.zad = new WeakReference<Object>(null);
    }

    @Deprecated
    protected BasePendingResult(Looper looper) {
        this.zac = new CallbackHandler(looper);
        this.zad = new WeakReference<Object>(null);
    }

    protected BasePendingResult(GoogleApiClient googleApiClient) {
        Looper looper = googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper();
        this.zac = new CallbackHandler(looper);
        this.zad = new WeakReference<GoogleApiClient>(googleApiClient);
    }

    protected BasePendingResult(CallbackHandler<R> callbackHandler) {
        this.zac = Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        this.zad = new WeakReference<Object>(null);
    }

    public static void zaa(Result object) {
        if (!(object instanceof Releasable)) return;
        try {
            ((Releasable)object).release();
            return;
        }
        catch (RuntimeException runtimeException) {
            String string2 = String.valueOf(object);
            object = new StringBuilder(String.valueOf(string2).length() + 18);
            ((StringBuilder)object).append("Unable to release ");
            ((StringBuilder)object).append(string2);
            Log.w((String)"BasePendingResult", (String)((StringBuilder)object).toString(), (Throwable)runtimeException);
        }
    }

    private static <R extends Result> ResultCallback<R> zab(ResultCallback<R> resultCallback) {
        return resultCallback;
    }

    private final void zab(R object) {
        this.zai = object;
        this.zaj = object.getStatus();
        this.zan = null;
        this.zae.countDown();
        if (this.zal) {
            this.zag = null;
        } else {
            object = this.zag;
            if (object == null) {
                if (this.zai instanceof Releasable) {
                    this.mResultGuardian = new zaa(null);
                }
            } else {
                this.zac.removeMessages(2);
                this.zac.zaa((ResultCallback<R>)object, this.zac());
            }
        }
        object = this.zaf;
        int n = ((ArrayList)object).size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.zaf.clear();
                return;
            }
            Object e = ((ArrayList)object).get(n2);
            ++n2;
            ((PendingResult.StatusListener)e).onComplete(this.zaj);
        } while (true);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final R zac() {
        R r;
        Object object = this.zab;
        synchronized (object) {
            boolean bl = !this.zak;
            Preconditions.checkState(bl, "Result has already been consumed.");
            Preconditions.checkState(this.isReady(), "Result is not ready.");
            r = this.zai;
            this.zai = null;
            this.zag = null;
            this.zak = true;
        }
        object = this.zah.getAndSet(null);
        if (object == null) return (R)((Result)Preconditions.checkNotNull(r));
        object.zaa(this);
        return (R)((Result)Preconditions.checkNotNull(r));
    }

    @Override
    public final void addStatusListener(PendingResult.StatusListener statusListener) {
        boolean bl = statusListener != null;
        Preconditions.checkArgument(bl, "Callback cannot be null.");
        Object object = this.zab;
        synchronized (object) {
            if (this.isReady()) {
                statusListener.onComplete(this.zaj);
            } else {
                this.zaf.add(statusListener);
            }
            return;
        }
    }

    @Override
    public final R await() {
        Preconditions.checkNotMainThread("await must not be called on the UI thread");
        boolean bl = this.zak;
        boolean bl2 = true;
        Preconditions.checkState(bl ^ true, "Result has already been consumed");
        if (this.zao != null) {
            bl2 = false;
        }
        Preconditions.checkState(bl2, "Cannot await if then() has been called.");
        try {
            this.zae.await();
        }
        catch (InterruptedException interruptedException) {
            this.forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(this.isReady(), "Result is not ready.");
        return this.zac();
    }

    @Override
    public final R await(long l, TimeUnit timeUnit) {
        if (l > 0L) {
            Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
        }
        boolean bl = this.zak;
        boolean bl2 = true;
        Preconditions.checkState(bl ^ true, "Result has already been consumed.");
        if (this.zao != null) {
            bl2 = false;
        }
        Preconditions.checkState(bl2, "Cannot await if then() has been called.");
        try {
            if (!this.zae.await(l, timeUnit)) {
                this.forceFailureUnlessReady(Status.RESULT_TIMEOUT);
            }
        }
        catch (InterruptedException interruptedException) {
            this.forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(this.isReady(), "Result is not ready.");
        return this.zac();
    }

    @Override
    public void cancel() {
        Object object = this.zab;
        synchronized (object) {
            if (this.zal) return;
            if (this.zak) {
                return;
            }
            ICancelToken iCancelToken = this.zan;
            if (iCancelToken != null) {
                try {
                    this.zan.cancel();
                }
                catch (RemoteException remoteException) {}
            }
            BasePendingResult.zaa(this.zai);
            this.zal = true;
            this.zab(this.createFailedResult(Status.RESULT_CANCELED));
            return;
        }
    }

    protected abstract R createFailedResult(Status var1);

    @Deprecated
    public final void forceFailureUnlessReady(Status status) {
        Object object = this.zab;
        synchronized (object) {
            if (this.isReady()) return;
            this.setResult(this.createFailedResult(status));
            this.zam = true;
            return;
        }
    }

    @Override
    public boolean isCanceled() {
        Object object = this.zab;
        synchronized (object) {
            return this.zal;
        }
    }

    public final boolean isReady() {
        if (this.zae.getCount() != 0L) return false;
        return true;
    }

    protected final void setCancelToken(ICancelToken iCancelToken) {
        Object object = this.zab;
        synchronized (object) {
            this.zan = iCancelToken;
            return;
        }
    }

    public final void setResult(R r) {
        Object object = this.zab;
        synchronized (object) {
            if (!this.zam && !this.zal) {
                this.isReady();
                boolean bl = this.isReady();
                boolean bl2 = true;
                bl = !bl;
                Preconditions.checkState(bl, "Results have already been set");
                bl = !this.zak ? bl2 : false;
                Preconditions.checkState(bl, "Result has already been consumed");
                this.zab(r);
                return;
            }
            BasePendingResult.zaa(r);
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        Object object = this.zab;
        synchronized (object) {
            if (resultCallback == null) {
                this.zag = null;
                return;
            }
            boolean bl = this.zak;
            boolean bl2 = true;
            bl = !bl;
            Preconditions.checkState(bl, "Result has already been consumed.");
            bl = this.zao == null ? bl2 : false;
            Preconditions.checkState(bl, "Cannot set callbacks if then() has been called.");
            if (((PendingResult)this).isCanceled()) {
                return;
            }
            if (this.isReady()) {
                this.zac.zaa(resultCallback, (R)this.zac());
            } else {
                this.zag = resultCallback;
            }
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> object, long l, TimeUnit timeUnit) {
        Object object2 = this.zab;
        synchronized (object2) {
            if (object == null) {
                this.zag = null;
                return;
            }
            boolean bl = this.zak;
            boolean bl2 = true;
            bl = !bl;
            Preconditions.checkState(bl, "Result has already been consumed.");
            bl = this.zao == null ? bl2 : false;
            Preconditions.checkState(bl, "Cannot set callbacks if then() has been called.");
            if (((PendingResult)this).isCanceled()) {
                return;
            }
            if (this.isReady()) {
                this.zac.zaa((ResultCallback<? super R>)object, (R)this.zac());
            } else {
                this.zag = object;
                CallbackHandler<R> callbackHandler = this.zac;
                l = timeUnit.toMillis(l);
                callbackHandler.sendMessageDelayed(callbackHandler.obtainMessage(2, (Object)this), l);
            }
            return;
        }
    }

    @Override
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> object) {
        Preconditions.checkState(this.zak ^ true, "Result has already been consumed.");
        Object object2 = this.zab;
        synchronized (object2) {
            zack<R> zack2 = this.zao;
            boolean bl = false;
            boolean bl2 = zack2 == null;
            Preconditions.checkState(bl2, "Cannot call then() twice.");
            bl2 = this.zag == null;
            Preconditions.checkState(bl2, "Cannot call then() if callbacks are set.");
            bl2 = bl;
            if (!this.zal) {
                bl2 = true;
            }
            Preconditions.checkState(bl2, "Cannot call then() if result was canceled.");
            this.zap = true;
            zack2 = new zack<R>(this.zad);
            this.zao = zack2;
            object = ((TransformedResult)this.zao).then(object);
            if (this.isReady()) {
                this.zac.zaa(this.zao, this.zac());
            } else {
                this.zag = this.zao;
            }
            return object;
        }
    }

    public final void zaa(zacn zacn2) {
        this.zah.set(zacn2);
    }

    public final boolean zaa() {
        Object object = this.zab;
        synchronized (object) {
            if ((GoogleApiClient)this.zad.get() != null) {
                if (this.zap) return ((PendingResult)this).isCanceled();
            }
            ((PendingResult)this).cancel();
            return ((PendingResult)this).isCanceled();
        }
    }

    public final void zab() {
        boolean bl = this.zap || zaa.get().booleanValue();
        this.zap = bl;
    }

    public static class CallbackHandler<R extends Result>
    extends zap {
        public CallbackHandler() {
            this(Looper.getMainLooper());
        }

        public CallbackHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n != 2) {
                    n = ((Message)object).what;
                    object = new StringBuilder(45);
                    ((StringBuilder)object).append("Don't know how to handle message: ");
                    ((StringBuilder)object).append(n);
                    Log.wtf((String)"BasePendingResult", (String)((StringBuilder)object).toString(), (Throwable)new Exception());
                    return;
                }
                ((BasePendingResult)((Message)object).obj).forceFailureUnlessReady(Status.RESULT_TIMEOUT);
                return;
            }
            Object object2 = (Pair)((Message)object).obj;
            object = (ResultCallback)object2.first;
            object2 = (Result)object2.second;
            try {
                object.onResult(object2);
                return;
            }
            catch (RuntimeException runtimeException) {
                BasePendingResult.zaa((Result)object2);
                throw runtimeException;
            }
        }

        public final void zaa(ResultCallback<? super R> resultCallback, R r) {
            this.sendMessage(this.obtainMessage(1, (Object)new Pair((Object)Preconditions.checkNotNull(BasePendingResult.zab(resultCallback)), r)));
        }
    }

    private final class zaa {
        private zaa() {
        }

        /* synthetic */ zaa(zao zao2) {
            this();
        }

        protected final void finalize() throws Throwable {
            BasePendingResult.zaa(BasePendingResult.this.zai);
            super.finalize();
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.util.Log
 */
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
import com.google.android.gms.common.api.internal.zaca;
import com.google.android.gms.common.api.internal.zacj;
import com.google.android.gms.common.api.internal.zacm;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class zack<R extends Result>
extends TransformedResult<R>
implements ResultCallback<R> {
    private ResultTransform<? super R, ? extends Result> zaa = null;
    private zack<? extends Result> zab = null;
    private volatile ResultCallbacks<? super R> zac = null;
    private PendingResult<R> zad = null;
    private final Object zae = new Object();
    private Status zaf = null;
    private final WeakReference<GoogleApiClient> zag;
    private final zacm zah;
    private boolean zai = false;

    public zack(WeakReference<GoogleApiClient> object) {
        Preconditions.checkNotNull(object, "GoogleApiClient reference must not be null");
        this.zag = object;
        object = (GoogleApiClient)((Reference)object).get();
        object = object != null ? ((GoogleApiClient)object).getLooper() : Looper.getMainLooper();
        this.zah = new zacm(this, (Looper)object);
    }

    static /* synthetic */ ResultTransform zaa(zack zack2) {
        return zack2.zaa;
    }

    private static void zaa(Result object) {
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
            Log.w((String)"TransformedResultImpl", (String)((StringBuilder)object).toString(), (Throwable)runtimeException);
        }
    }

    private final void zaa(Status status) {
        Object object = this.zae;
        synchronized (object) {
            this.zaf = status;
            this.zab(status);
            return;
        }
    }

    static /* synthetic */ void zaa(zack zack2, Result result) {
        zack.zaa(result);
    }

    static /* synthetic */ void zaa(zack zack2, Status status) {
        zack2.zaa(status);
    }

    static /* synthetic */ zacm zab(zack zack2) {
        return zack2.zah;
    }

    private final void zab() {
        if (this.zaa == null && this.zac == null) {
            return;
        }
        PendingResult<R> pendingResult = (GoogleApiClient)this.zag.get();
        if (!this.zai && this.zaa != null && pendingResult != null) {
            ((GoogleApiClient)((Object)pendingResult)).zaa(this);
            this.zai = true;
        }
        if ((pendingResult = this.zaf) != null) {
            this.zab((Status)((Object)pendingResult));
            return;
        }
        pendingResult = this.zad;
        if (pendingResult == null) return;
        pendingResult.setResultCallback(this);
    }

    private final void zab(Status status) {
        Object object = this.zae;
        synchronized (object) {
            if (this.zaa != null) {
                status = Preconditions.checkNotNull(this.zaa.onFailure(status), "onFailure must not return null");
                zack.super.zaa(status);
            } else {
                if (!this.zac()) return;
                Preconditions.checkNotNull(this.zac).onFailure(status);
            }
            return;
        }
    }

    static /* synthetic */ WeakReference zac(zack zack2) {
        return zack2.zag;
    }

    private final boolean zac() {
        GoogleApiClient googleApiClient = (GoogleApiClient)this.zag.get();
        if (this.zac == null) return false;
        if (googleApiClient == null) return false;
        return true;
    }

    static /* synthetic */ Object zad(zack zack2) {
        return zack2.zae;
    }

    static /* synthetic */ zack zae(zack zack2) {
        return zack2.zab;
    }

    @Override
    public final void andFinally(ResultCallbacks<? super R> resultCallbacks) {
        Object object = this.zae;
        synchronized (object) {
            ResultCallbacks<? super R> resultCallbacks2 = this.zac;
            boolean bl = true;
            boolean bl2 = resultCallbacks2 == null;
            Preconditions.checkState(bl2, "Cannot call andFinally() twice.");
            bl2 = this.zaa == null ? bl : false;
            Preconditions.checkState(bl2, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zac = resultCallbacks;
            this.zab();
            return;
        }
    }

    @Override
    public final void onResult(R r) {
        Object object = this.zae;
        synchronized (object) {
            if (r.getStatus().isSuccess()) {
                if (this.zaa != null) {
                    ExecutorService executorService = zaca.zaa();
                    zacj zacj2 = new zacj(this, (Result)r);
                    executorService.submit(zacj2);
                } else {
                    if (!this.zac()) return;
                    Preconditions.checkNotNull(this.zac).onSuccess(r);
                }
            } else {
                this.zaa(r.getStatus());
                zack.zaa(r);
            }
            return;
        }
    }

    @Override
    public final <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> zack2) {
        Object object = this.zae;
        synchronized (object) {
            ResultTransform<? super R, ? extends Result> resultTransform = this.zaa;
            boolean bl = true;
            boolean bl2 = resultTransform == null;
            Preconditions.checkState(bl2, "Cannot call then() twice.");
            bl2 = this.zac == null ? bl : false;
            Preconditions.checkState(bl2, "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zaa = zack2;
            zack2 = new zack<R>(this.zag);
            this.zab = zack2;
            this.zab();
            return zack2;
        }
    }

    final void zaa() {
        this.zac = null;
    }

    public final void zaa(PendingResult<?> pendingResult) {
        Object object = this.zae;
        synchronized (object) {
            this.zad = pendingResult;
            this.zab();
            return;
        }
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.OptionalPendingResultImpl;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.internal.Preconditions;

public final class PendingResults {
    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        StatusPendingResult statusPendingResult = new StatusPendingResult(Looper.getMainLooper());
        ((PendingResult)statusPendingResult).cancel();
        return statusPendingResult;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R object) {
        Preconditions.checkNotNull(object, "Result must not be null");
        boolean bl = object.getStatus().getStatusCode() == 16;
        Preconditions.checkArgument(bl, "Status code must be CommonStatusCodes.CANCELED");
        object = new zab<R>(object);
        ((PendingResult)object).cancel();
        return object;
    }

    public static <R extends Result> PendingResult<R> immediateFailedResult(R r, GoogleApiClient object) {
        Preconditions.checkNotNull(r, "Result must not be null");
        Preconditions.checkArgument(r.getStatus().isSuccess() ^ true, "Status code must not be SUCCESS");
        object = new zaa<R>((GoogleApiClient)object, r);
        ((BasePendingResult)object).setResult(r);
        return object;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        Preconditions.checkNotNull(r, "Result must not be null");
        zac<R> zac2 = new zac<R>(null);
        zac2.setResult(r);
        return new OptionalPendingResultImpl(zac2);
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r, GoogleApiClient object) {
        Preconditions.checkNotNull(r, "Result must not be null");
        object = new zac((GoogleApiClient)object);
        ((BasePendingResult)object).setResult(r);
        return new OptionalPendingResultImpl(object);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        Preconditions.checkNotNull(status, "Result must not be null");
        StatusPendingResult statusPendingResult = new StatusPendingResult(Looper.getMainLooper());
        statusPendingResult.setResult(status);
        return statusPendingResult;
    }

    public static PendingResult<Status> immediatePendingResult(Status status, GoogleApiClient object) {
        Preconditions.checkNotNull(status, "Result must not be null");
        object = new StatusPendingResult((GoogleApiClient)object);
        ((BasePendingResult)object).setResult(status);
        return object;
    }

    private static final class zaa<R extends Result>
    extends BasePendingResult<R> {
        private final R zab;

        public zaa(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zab = r;
        }

        @Override
        protected final R createFailedResult(Status status) {
            return this.zab;
        }
    }

    private static final class zab<R extends Result>
    extends BasePendingResult<R> {
        private final R zab;

        public zab(R r) {
            super(Looper.getMainLooper());
            this.zab = r;
        }

        @Override
        protected final R createFailedResult(Status status) {
            if (status.getStatusCode() != this.zab.getStatus().getStatusCode()) throw new UnsupportedOperationException("Creating failed results is not supported");
            return this.zab;
        }
    }

    private static final class zac<R extends Result>
    extends BasePendingResult<R> {
        public zac(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override
        protected final R createFailedResult(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

}


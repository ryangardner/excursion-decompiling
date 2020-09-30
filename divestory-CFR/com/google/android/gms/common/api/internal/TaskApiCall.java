/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.zacf;
import com.google.android.gms.common.api.internal.zacg;
import com.google.android.gms.common.api.internal.zach;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class TaskApiCall<A extends Api.AnyClient, ResultT> {
    private final Feature[] zaa;
    private final boolean zab;
    private final int zac;

    @Deprecated
    public TaskApiCall() {
        this.zaa = null;
        this.zab = false;
        this.zac = 0;
    }

    private TaskApiCall(Feature[] arrfeature, boolean bl, int n) {
        this.zaa = arrfeature;
        this.zab = bl;
        this.zac = n;
    }

    /* synthetic */ TaskApiCall(Feature[] arrfeature, boolean bl, int n, zacf zacf2) {
        this(arrfeature, bl, n);
    }

    public static <A extends Api.AnyClient, ResultT> Builder<A, ResultT> builder() {
        return new Builder(null);
    }

    protected abstract void doExecute(A var1, TaskCompletionSource<ResultT> var2) throws RemoteException;

    public boolean shouldAutoResolveMissingFeatures() {
        return this.zab;
    }

    public final Feature[] zaa() {
        return this.zaa;
    }

    public static class Builder<A extends Api.AnyClient, ResultT> {
        private RemoteCall<A, TaskCompletionSource<ResultT>> zaa;
        private boolean zab = true;
        private Feature[] zac;
        private int zad = 0;

        private Builder() {
        }

        /* synthetic */ Builder(zacf zacf2) {
            this();
        }

        static /* synthetic */ RemoteCall zaa(Builder builder) {
            return builder.zaa;
        }

        public TaskApiCall<A, ResultT> build() {
            boolean bl = this.zaa != null;
            Preconditions.checkArgument(bl, "execute parameter required");
            return new zacg(this, this.zac, this.zab, this.zad);
        }

        @Deprecated
        public Builder<A, ResultT> execute(BiConsumer<A, TaskCompletionSource<ResultT>> biConsumer) {
            this.zaa = new zach(biConsumer);
            return this;
        }

        public Builder<A, ResultT> run(RemoteCall<A, TaskCompletionSource<ResultT>> remoteCall) {
            this.zaa = remoteCall;
            return this;
        }

        public Builder<A, ResultT> setAutoResolveMissingFeatures(boolean bl) {
            this.zab = bl;
            return this;
        }

        public Builder<A, ResultT> setFeatures(Feature ... arrfeature) {
            this.zac = arrfeature;
            return this;
        }

        public Builder<A, ResultT> setMethodKey(int n) {
            boolean bl = n != 0;
            Preconditions.checkArgument(bl);
            this.zad = n;
            return this;
        }
    }

}


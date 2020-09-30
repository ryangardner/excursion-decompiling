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
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zabt;
import com.google.android.gms.common.api.internal.zabu;
import com.google.android.gms.common.api.internal.zabv;
import com.google.android.gms.common.api.internal.zabw;
import com.google.android.gms.common.api.internal.zabx;
import com.google.android.gms.common.api.internal.zaby;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

public class RegistrationMethods<A extends Api.AnyClient, L> {
    public final RegisterListenerMethod<A, L> zaa;
    public final UnregisterListenerMethod<A, L> zab;
    public final Runnable zac;

    private RegistrationMethods(RegisterListenerMethod<A, L> registerListenerMethod, UnregisterListenerMethod<A, L> unregisterListenerMethod, Runnable runnable2) {
        this.zaa = registerListenerMethod;
        this.zab = unregisterListenerMethod;
        this.zac = runnable2;
    }

    /* synthetic */ RegistrationMethods(RegisterListenerMethod registerListenerMethod, UnregisterListenerMethod unregisterListenerMethod, Runnable runnable2, zabt zabt2) {
        this(registerListenerMethod, unregisterListenerMethod, runnable2);
    }

    public static <A extends Api.AnyClient, L> Builder<A, L> builder() {
        return new Builder(null);
    }

    public static class Builder<A extends Api.AnyClient, L> {
        private RemoteCall<A, TaskCompletionSource<Void>> zaa;
        private RemoteCall<A, TaskCompletionSource<Boolean>> zab;
        private Runnable zac = zabv.zaa;
        private ListenerHolder<L> zad;
        private Feature[] zae;
        private boolean zaf = true;

        private Builder() {
        }

        /* synthetic */ Builder(zabt zabt2) {
            this();
        }

        static /* synthetic */ RemoteCall zaa(Builder builder) {
            return builder.zaa;
        }

        static /* synthetic */ RemoteCall zab(Builder builder) {
            return builder.zab;
        }

        public RegistrationMethods<A, L> build() {
            Object object = this.zaa;
            boolean bl = true;
            boolean bl2 = object != null;
            Preconditions.checkArgument(bl2, "Must set register function");
            bl2 = this.zab != null;
            Preconditions.checkArgument(bl2, "Must set unregister function");
            bl2 = this.zad != null ? bl : false;
            Preconditions.checkArgument(bl2, "Must set holder");
            object = Preconditions.checkNotNull(this.zad.getListenerKey(), "Key must not be null");
            return new RegistrationMethods(new zabw(this, this.zad, this.zae, this.zaf), new zaby(this, (ListenerHolder.ListenerKey)object), this.zac, null);
        }

        public Builder<A, L> onConnectionSuspended(Runnable runnable2) {
            this.zac = runnable2;
            return this;
        }

        public Builder<A, L> register(RemoteCall<A, TaskCompletionSource<Void>> remoteCall) {
            this.zaa = remoteCall;
            return this;
        }

        @Deprecated
        public Builder<A, L> register(BiConsumer<A, TaskCompletionSource<Void>> biConsumer) {
            this.zaa = new zabu(biConsumer);
            return this;
        }

        public Builder<A, L> setAutoResolveMissingFeatures(boolean bl) {
            this.zaf = bl;
            return this;
        }

        public Builder<A, L> setFeatures(Feature ... arrfeature) {
            this.zae = arrfeature;
            return this;
        }

        public Builder<A, L> unregister(RemoteCall<A, TaskCompletionSource<Boolean>> remoteCall) {
            this.zab = remoteCall;
            return this;
        }

        @Deprecated
        public Builder<A, L> unregister(BiConsumer<A, TaskCompletionSource<Boolean>> biConsumer) {
            this.zaa = new zabx(this);
            return this;
        }

        public Builder<A, L> withHolder(ListenerHolder<L> listenerHolder) {
            this.zad = listenerHolder;
            return this;
        }

        final /* synthetic */ void zaa(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
            this.zaa.accept(anyClient, taskCompletionSource);
        }
    }

}


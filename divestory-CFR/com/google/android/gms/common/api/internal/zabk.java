/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 *  org.checkerframework.checker.initialization.qual.NotOnlyInitialized
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.api.internal.zaz;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zabk<O extends Api.ApiOptions>
extends zaz {
    @NotOnlyInitialized
    private final GoogleApi<O> zaa;

    public zabk(GoogleApi<O> googleApi) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zaa = googleApi;
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T t) {
        return this.zaa.doRead(t);
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T t) {
        return this.zaa.doWrite(t);
    }

    @Override
    public final Context getContext() {
        return this.zaa.getApplicationContext();
    }

    @Override
    public final Looper getLooper() {
        return this.zaa.getLooper();
    }

    @Override
    public final void zaa(zack zack2) {
    }

    @Override
    public final void zab(zack zack2) {
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.checkerframework.checker.initialization.qual.NotOnlyInitialized
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zaay;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zaaq
implements zaay {
    @NotOnlyInitialized
    private final zaax zaa;

    public zaaq(zaax zaax2) {
        this.zaa = zaax2;
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T t) {
        this.zaa.zad.zaa.add(t);
        return t;
    }

    @Override
    public final void zaa() {
        Iterator<Api.Client> iterator2 = this.zaa.zaa.values().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.zaa.zad.zac = Collections.emptySet();
                return;
            }
            iterator2.next().disconnect();
        } while (true);
    }

    @Override
    public final void zaa(int n) {
    }

    @Override
    public final void zaa(Bundle bundle) {
    }

    @Override
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean bl) {
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override
    public final boolean zab() {
        return true;
    }

    @Override
    public final void zac() {
        this.zaa.zah();
    }
}


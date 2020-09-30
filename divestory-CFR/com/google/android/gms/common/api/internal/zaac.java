/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.DeadObjectException
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zaab;
import com.google.android.gms.common.api.internal.zaae;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.common.api.internal.zabm;
import com.google.android.gms.common.api.internal.zack;
import com.google.android.gms.common.api.internal.zacl;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zaac
implements zaay {
    private final zaax zaa;
    private boolean zab = false;

    public zaac(zaax zaax2) {
        this.zaa = zaax2;
    }

    static /* synthetic */ zaax zaa(zaac zaac2) {
        return zaac2.zaa;
    }

    @Override
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T t) {
        return this.zab(t);
    }

    @Override
    public final void zaa() {
    }

    @Override
    public final void zaa(int n) {
        this.zaa.zaa((ConnectionResult)null);
        this.zaa.zae.zaa(n, this.zab);
    }

    @Override
    public final void zaa(Bundle bundle) {
    }

    @Override
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean bl) {
    }

    @Override
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T t) {
        try {
            this.zaa.zad.zae.zaa((BasePendingResult<? extends Result>)t);
            zaap zaap2 = this.zaa.zad;
            Object object = ((BaseImplementation.ApiMethodImpl)t).getClientKey();
            object = zaap2.zab.get(object);
            Preconditions.checkNotNull(object, "Appropriate Api was not requested.");
            if (!object.isConnected() && this.zaa.zab.containsKey(((BaseImplementation.ApiMethodImpl)t).getClientKey())) {
                object = new Status(17);
                ((BaseImplementation.ApiMethodImpl)t).setFailedResult((Status)object);
                return t;
            }
            ((BaseImplementation.ApiMethodImpl)t).run((Object)object);
            return t;
        }
        catch (DeadObjectException deadObjectException) {
            this.zaa.zaa(new zaab(this, this));
        }
        return t;
    }

    @Override
    public final boolean zab() {
        if (this.zab) {
            return false;
        }
        Set<zack> set = this.zaa.zad.zad;
        if (set != null && !set.isEmpty()) {
            this.zab = true;
            set = set.iterator();
            while (set.hasNext()) {
                ((zack)set.next()).zaa();
            }
            return false;
        }
        this.zaa.zaa((ConnectionResult)null);
        return true;
    }

    @Override
    public final void zac() {
        if (!this.zab) return;
        this.zab = false;
        this.zaa.zaa(new zaae(this, this));
    }

    final void zad() {
        if (!this.zab) return;
        this.zab = false;
        this.zaa.zad.zae.zaa();
        this.zab();
    }
}


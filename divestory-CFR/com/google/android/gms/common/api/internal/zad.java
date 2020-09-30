/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zac;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.common.internal.Preconditions;

public final class zad<A extends BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>>
extends zac {
    private final A zab;

    public zad(int n, A a) {
        super(n);
        this.zab = (BaseImplementation.ApiMethodImpl)Preconditions.checkNotNull(a, "Null methods are not runnable.");
    }

    @Override
    public final void zaa(Status status) {
        try {
            ((BaseImplementation.ApiMethodImpl)this.zab).setFailedResult(status);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            Log.w((String)"ApiCallRunner", (String)"Exception reporting failure", (Throwable)illegalStateException);
            return;
        }
    }

    @Override
    public final void zaa(zaw zaw2, boolean bl) {
        zaw2.zaa((BasePendingResult<? extends Result>)this.zab, bl);
    }

    @Override
    public final void zaa(Exception object) {
        String string2 = object.getClass().getSimpleName();
        object = ((Throwable)object).getLocalizedMessage();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 2 + String.valueOf(object).length());
        stringBuilder.append(string2);
        stringBuilder.append(": ");
        stringBuilder.append((String)object);
        object = new Status(10, stringBuilder.toString());
        try {
            ((BaseImplementation.ApiMethodImpl)this.zab).setFailedResult((Status)object);
            return;
        }
        catch (IllegalStateException illegalStateException) {
            Log.w((String)"ApiCallRunner", (String)"Exception reporting failure", (Throwable)illegalStateException);
            return;
        }
    }

    @Override
    public final void zac(GoogleApiManager.zaa<?> zaa2) throws DeadObjectException {
        try {
            ((BaseImplementation.ApiMethodImpl)((Object)this.zab)).run((Api.Client)zaa2.zab());
            return;
        }
        catch (RuntimeException runtimeException) {
            ((zac)this).zaa(runtimeException);
            return;
        }
    }
}


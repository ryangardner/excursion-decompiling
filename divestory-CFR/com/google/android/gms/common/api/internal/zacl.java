/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zacn;
import com.google.android.gms.common.api.internal.zaco;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public final class zacl {
    public static final Status zaa = new Status(8, "The connection to Google Play services was lost");
    final Set<BasePendingResult<?>> zab = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zacn zac = new zaco(this);

    public final void zaa() {
        Object object = this.zab;
        int n = 0;
        BasePendingResult[] arrbasePendingResult = object.toArray(new BasePendingResult[0]);
        int n2 = arrbasePendingResult.length;
        while (n < n2) {
            object = arrbasePendingResult[n];
            ((BasePendingResult)object).zaa(null);
            if (((BasePendingResult)object).zaa()) {
                this.zab.remove(object);
            }
            ++n;
        }
    }

    final void zaa(BasePendingResult<? extends Result> basePendingResult) {
        this.zab.add(basePendingResult);
        basePendingResult.zaa(this.zac);
    }
}


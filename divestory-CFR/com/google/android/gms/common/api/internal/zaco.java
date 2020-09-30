/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zacl;
import com.google.android.gms.common.api.internal.zacn;
import java.util.Set;

final class zaco
implements zacn {
    private final /* synthetic */ zacl zaa;

    zaco(zacl zacl2) {
        this.zaa = zacl2;
    }

    @Override
    public final void zaa(BasePendingResult<?> basePendingResult) {
        this.zaa.zab.remove(basePendingResult);
    }
}


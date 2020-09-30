/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaai;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaba;

final class zaah
extends zaba {
    private final /* synthetic */ ConnectionResult zaa;
    private final /* synthetic */ zaai zab;

    zaah(zaai zaai2, zaay zaay2, ConnectionResult connectionResult) {
        this.zab = zaai2;
        this.zaa = connectionResult;
        super(zaay2);
    }

    @Override
    public final void zaa() {
        zaad.zaa(this.zab.zaa, this.zaa);
    }
}


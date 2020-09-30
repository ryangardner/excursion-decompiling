/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.internal.zak;

final class zaas
implements zak {
    private final /* synthetic */ zaap zaa;

    zaas(zaap zaap2) {
        this.zaa = zaap2;
    }

    @Override
    public final Bundle getConnectionHint() {
        return null;
    }

    @Override
    public final boolean isConnected() {
        return ((GoogleApiClient)this.zaa).isConnected();
    }
}


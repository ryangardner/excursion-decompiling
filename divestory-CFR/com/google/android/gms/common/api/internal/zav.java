/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.zaw;

final class zav
implements PendingResult.StatusListener {
    private final /* synthetic */ BasePendingResult zaa;
    private final /* synthetic */ zaw zab;

    zav(zaw zaw2, BasePendingResult basePendingResult) {
        this.zab = zaw2;
        this.zaa = basePendingResult;
    }

    @Override
    public final void onComplete(Status status) {
        zaw.zaa(this.zab).remove(this.zaa);
    }
}


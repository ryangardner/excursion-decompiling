/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import com.google.android.gms.common.api.internal.zaad;

final class zaag
implements Runnable {
    private final /* synthetic */ zaad zaa;

    zaag(zaad zaad2) {
        this.zaa = zaad2;
    }

    @Override
    public final void run() {
        zaad.zab(this.zaa).cancelAvailabilityErrorNotifications(zaad.zaa(this.zaa));
    }
}


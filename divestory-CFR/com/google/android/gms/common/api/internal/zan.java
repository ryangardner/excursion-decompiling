/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 */
package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import com.google.android.gms.common.api.internal.zabl;
import com.google.android.gms.common.api.internal.zak;
import com.google.android.gms.common.api.internal.zal;

final class zan
extends zabl {
    private final /* synthetic */ Dialog zaa;
    private final /* synthetic */ zal zab;

    zan(zal zal2, Dialog dialog) {
        this.zab = zal2;
        this.zaa = dialog;
    }

    @Override
    public final void zaa() {
        this.zab.zaa.zab();
        if (!this.zaa.isShowing()) return;
        this.zaa.dismiss();
    }
}


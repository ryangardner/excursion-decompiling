/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.internal.zab;

final class zad
extends zab {
    private final /* synthetic */ Intent zaa;
    private final /* synthetic */ Fragment zab;
    private final /* synthetic */ int zac;

    zad(Intent intent, Fragment fragment, int n) {
        this.zaa = intent;
        this.zab = fragment;
        this.zac = n;
    }

    @Override
    public final void zaa() {
        Intent intent = this.zaa;
        if (intent == null) return;
        this.zab.startActivityForResult(intent, this.zac);
    }
}


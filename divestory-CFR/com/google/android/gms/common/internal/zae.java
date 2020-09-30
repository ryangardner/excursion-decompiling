/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.common.internal.zab;

final class zae
extends zab {
    private final /* synthetic */ Intent zaa;
    private final /* synthetic */ Activity zab;
    private final /* synthetic */ int zac;

    zae(Intent intent, Activity activity, int n) {
        this.zaa = intent;
        this.zab = activity;
        this.zac = n;
    }

    @Override
    public final void zaa() {
        Intent intent = this.zaa;
        if (intent == null) return;
        this.zab.startActivityForResult(intent, this.zac);
    }
}


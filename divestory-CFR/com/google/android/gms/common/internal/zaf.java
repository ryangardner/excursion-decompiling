/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.internal.zab;

final class zaf
extends zab {
    private final /* synthetic */ Intent zaa;
    private final /* synthetic */ LifecycleFragment zab;
    private final /* synthetic */ int zac;

    zaf(Intent intent, LifecycleFragment lifecycleFragment, int n) {
        this.zaa = intent;
        this.zab = lifecycleFragment;
        this.zac = 2;
    }

    @Override
    public final void zaa() {
        Intent intent = this.zaa;
        if (intent == null) return;
        this.zab.startActivityForResult(intent, this.zac);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.LifecycleDelegate;

final class zaa
implements DeferredLifecycleHelper.zaa {
    private final /* synthetic */ Activity zaa;
    private final /* synthetic */ Bundle zab;
    private final /* synthetic */ Bundle zac;
    private final /* synthetic */ DeferredLifecycleHelper zad;

    zaa(DeferredLifecycleHelper deferredLifecycleHelper, Activity activity, Bundle bundle, Bundle bundle2) {
        this.zad = deferredLifecycleHelper;
        this.zaa = activity;
        this.zab = bundle;
        this.zac = bundle2;
    }

    @Override
    public final int zaa() {
        return 0;
    }

    @Override
    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        DeferredLifecycleHelper.zab(this.zad).onInflate(this.zaa, this.zab, this.zac);
    }
}


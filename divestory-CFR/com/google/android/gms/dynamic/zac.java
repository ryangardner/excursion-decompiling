/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.LifecycleDelegate;

final class zac
implements DeferredLifecycleHelper.zaa {
    private final /* synthetic */ FrameLayout zaa;
    private final /* synthetic */ LayoutInflater zab;
    private final /* synthetic */ ViewGroup zac;
    private final /* synthetic */ Bundle zad;
    private final /* synthetic */ DeferredLifecycleHelper zae;

    zac(DeferredLifecycleHelper deferredLifecycleHelper, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zae = deferredLifecycleHelper;
        this.zaa = frameLayout;
        this.zab = layoutInflater;
        this.zac = viewGroup;
        this.zad = bundle;
    }

    @Override
    public final int zaa() {
        return 2;
    }

    @Override
    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        this.zaa.removeAllViews();
        this.zaa.addView(DeferredLifecycleHelper.zab(this.zae).onCreateView(this.zab, this.zac, this.zad));
    }
}


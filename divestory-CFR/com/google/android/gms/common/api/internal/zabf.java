/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zabh;
import com.google.android.gms.common.internal.BaseGmsClient;

final class zabf
implements BaseGmsClient.SignOutCallbacks {
    final /* synthetic */ GoogleApiManager.zaa zaa;

    zabf(GoogleApiManager.zaa zaa2) {
        this.zaa = zaa2;
    }

    @Override
    public final void onSignOutComplete() {
        GoogleApiManager.zaa(this.zaa.GoogleApiManager.this).post((Runnable)new zabh(this));
    }
}


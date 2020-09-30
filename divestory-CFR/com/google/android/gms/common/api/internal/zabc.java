/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Message;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.api.internal.GoogleApiManager;

final class zabc
implements BackgroundDetector.BackgroundStateChangeListener {
    private final /* synthetic */ GoogleApiManager zaa;

    zabc(GoogleApiManager googleApiManager) {
        this.zaa = googleApiManager;
    }

    @Override
    public final void onBackgroundStateChanged(boolean bl) {
        GoogleApiManager.zaa(this.zaa).sendMessage(GoogleApiManager.zaa(this.zaa).obtainMessage(1, (Object)bl));
    }
}


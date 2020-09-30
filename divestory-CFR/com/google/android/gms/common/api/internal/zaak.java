/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.zaai;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.common.internal.BaseGmsClient;

final class zaak
extends zaba {
    private final /* synthetic */ BaseGmsClient.ConnectionProgressReportCallbacks zaa;

    zaak(zaai zaai2, zaay zaay2, BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zaa = connectionProgressReportCallbacks;
        super(zaay2);
    }

    @Override
    public final void zaa() {
        this.zaa.onReportServiceBinding(new ConnectionResult(16, null));
    }
}


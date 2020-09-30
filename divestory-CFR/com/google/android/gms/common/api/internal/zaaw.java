/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.internal.base.zap;

final class zaaw
extends zap {
    private final /* synthetic */ zaap zaa;

    zaaw(zaap zaap2, Looper looper) {
        this.zaa = zaap2;
        super(looper);
    }

    public final void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n == 1) {
            zaap.zab(this.zaa);
            return;
        }
        if (n != 2) {
            n = ((Message)object).what;
            object = new StringBuilder(31);
            ((StringBuilder)object).append("Unknown message id: ");
            ((StringBuilder)object).append(n);
            Log.w((String)"GoogleApiClientImpl", (String)((StringBuilder)object).toString());
            return;
        }
        zaap.zaa(this.zaa);
    }
}


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
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.internal.base.zap;

final class zaaz
extends zap {
    private final /* synthetic */ zaax zaa;

    zaaz(zaax zaax2, Looper looper) {
        this.zaa = zaax2;
        super(looper);
    }

    public final void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n != 1) {
            if (n == 2) throw (RuntimeException)((Message)object).obj;
            n = ((Message)object).what;
            object = new StringBuilder(31);
            ((StringBuilder)object).append("Unknown message id: ");
            ((StringBuilder)object).append(n);
            Log.w((String)"GACStateManager", (String)((StringBuilder)object).toString());
            return;
        }
        ((zaba)((Message)object).obj).zaa(this.zaa);
    }
}


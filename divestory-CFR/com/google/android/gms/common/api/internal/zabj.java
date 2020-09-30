/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.api.internal.zabl;

public final class zabj
extends BroadcastReceiver {
    private Context zaa;
    private final zabl zab;

    public zabj(zabl zabl2) {
        this.zab = zabl2;
    }

    public final void onReceive(Context object, Intent intent) {
        object = intent.getData();
        object = object != null ? object.getSchemeSpecificPart() : null;
        if (!"com.google.android.gms".equals(object)) return;
        this.zab.zaa();
        this.zaa();
    }

    public final void zaa() {
        synchronized (this) {
            if (this.zaa != null) {
                this.zaa.unregisterReceiver((BroadcastReceiver)this);
            }
            this.zaa = null;
            return;
        }
    }

    public final void zaa(Context context) {
        this.zaa = context;
    }
}


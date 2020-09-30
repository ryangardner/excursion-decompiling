/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import com.google.android.gms.common.api.internal.NonGmsServiceBrokerClient;

final class zabp
implements Runnable {
    private final NonGmsServiceBrokerClient zaa;
    private final IBinder zab;

    zabp(NonGmsServiceBrokerClient nonGmsServiceBrokerClient, IBinder iBinder) {
        this.zaa = nonGmsServiceBrokerClient;
        this.zab = iBinder;
    }

    @Override
    public final void run() {
        this.zaa.zaa(this.zab);
    }
}


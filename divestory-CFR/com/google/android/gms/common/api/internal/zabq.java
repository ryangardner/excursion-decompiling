/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.NonGmsServiceBrokerClient;

final class zabq
implements Runnable {
    private final NonGmsServiceBrokerClient zaa;

    zabq(NonGmsServiceBrokerClient nonGmsServiceBrokerClient) {
        this.zaa = nonGmsServiceBrokerClient;
    }

    @Override
    public final void run() {
        this.zaa.zaa();
    }
}


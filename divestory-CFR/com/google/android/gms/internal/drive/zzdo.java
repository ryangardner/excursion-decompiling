/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.internal.drive.zzdg;
import com.google.android.gms.internal.drive.zzdk;

final class zzdo
implements ListenerHolder.Notifier<OpenFileCallback> {
    private final /* synthetic */ zzdg zzgp;

    zzdo(zzdk zzdk2, zzdg zzdg2) {
        this.zzgp = zzdg2;
    }

    @Override
    public final /* synthetic */ void notifyListener(Object object) {
        object = (OpenFileCallback)object;
        this.zzgp.accept(object);
    }

    @Override
    public final void onNotifyListenerFailed() {
    }
}


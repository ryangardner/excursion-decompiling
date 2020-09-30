/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.drive.zzbp;

final class zzbq
implements ListenerHolder.Notifier<DriveFile.DownloadProgressListener> {
    private final /* synthetic */ long zzfb;
    private final /* synthetic */ long zzfc;

    zzbq(zzbp zzbp2, long l, long l2) {
        this.zzfb = l;
        this.zzfc = l2;
    }

    @Override
    public final /* synthetic */ void notifyListener(Object object) {
        ((DriveFile.DownloadProgressListener)object).onProgress(this.zzfb, this.zzfc);
    }

    @Override
    public final void onNotifyListenerFailed() {
    }
}


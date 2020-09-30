/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.drive.zzbq;

final class zzbp
implements DriveFile.DownloadProgressListener {
    private final ListenerHolder<DriveFile.DownloadProgressListener> zzfa;

    public zzbp(ListenerHolder<DriveFile.DownloadProgressListener> listenerHolder) {
        this.zzfa = listenerHolder;
    }

    @Override
    public final void onProgress(long l, long l2) {
        this.zzfa.notifyListener(new zzbq(this, l, l2));
    }
}


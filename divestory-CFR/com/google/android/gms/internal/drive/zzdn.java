/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.internal.drive.zzdg;
import com.google.android.gms.internal.drive.zzdk;
import com.google.android.gms.internal.drive.zzfh;

final class zzdn
implements zzdg {
    private final zzdk zzgl;
    private final zzfh zzgo;

    zzdn(zzdk zzdk2, zzfh zzfh2) {
        this.zzgl = zzdk2;
        this.zzgo = zzfh2;
    }

    public final void accept(Object object) {
        this.zzgl.zza(this.zzgo, (OpenFileCallback)object);
    }
}


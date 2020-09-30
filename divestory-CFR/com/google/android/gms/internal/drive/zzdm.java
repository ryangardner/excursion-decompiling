/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.internal.drive.zzdg;
import com.google.android.gms.internal.drive.zzfl;

final class zzdm
implements zzdg {
    private final zzfl zzgn;

    zzdm(zzfl zzfl2) {
        this.zzgn = zzfl2;
    }

    public final void accept(Object object) {
        zzfl zzfl2 = this.zzgn;
        ((OpenFileCallback)object).onProgress(zzfl2.zzhy, zzfl2.zzhz);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.internal.drive.zzdg;
import com.google.android.gms.internal.drive.zzdk;

final class zzdl
implements zzdg {
    private final zzdk zzgl;
    private final Status zzgm;

    zzdl(zzdk zzdk2, Status status) {
        this.zzgl = zzdk2;
        this.zzgm = status;
    }

    public final void accept(Object object) {
        this.zzgl.zza(this.zzgm, (OpenFileCallback)object);
    }
}


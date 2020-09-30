/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.drive.events.zzk;
import com.google.android.gms.drive.events.zzm;
import com.google.android.gms.internal.drive.zzf;
import com.google.android.gms.internal.drive.zzh;
import java.util.Locale;

public final class zze
implements zzk {
    private final zzm zzcv;
    private final long zzcw;
    private final long zzcx;

    public zze(zzh zzh2) {
        this.zzcv = new zzf(zzh2);
        this.zzcw = zzh2.zzcw;
        this.zzcx = zzh2.zzcx;
    }

    public final boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (zze)object;
        if (!Objects.equal(this.zzcv, ((zze)object).zzcv)) return false;
        if (this.zzcw != ((zze)object).zzcw) return false;
        if (this.zzcx != ((zze)object).zzcx) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzcx, this.zzcw, this.zzcx);
    }

    public final String toString() {
        return String.format(Locale.US, "FileTransferProgress[FileTransferState: %s, BytesTransferred: %d, TotalBytes: %d]", this.zzcv.toString(), this.zzcw, this.zzcx);
    }
}


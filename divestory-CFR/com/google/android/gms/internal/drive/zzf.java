/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzh;

public final class zzf {
    private final int status;
    private final int zzct;
    private final DriveId zzk;

    public zzf(zzh zzh2) {
        this.zzk = zzh2.zzk;
        this.zzct = zzh2.zzct;
        this.status = zzh2.status;
    }

    public final boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (zzf)object;
        if (!Objects.equal(this.zzk, ((zzf)object).zzk)) return false;
        if (this.zzct != ((zzf)object).zzct) return false;
        if (this.status != ((zzf)object).status) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzk, this.zzct, this.status);
    }

    public final String toString() {
        return String.format("FileTransferState[TransferType: %d, DriveId: %s, status: %d]", this.zzct, this.zzk, this.status);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzi;

public final class zzh
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();
    final int status;
    final int zzct;
    final long zzcw;
    final long zzcx;
    final DriveId zzk;

    public zzh(int n, DriveId driveId, int n2, long l, long l2) {
        this.zzct = n;
        this.zzk = driveId;
        this.status = n2;
        this.zzcw = l;
        this.zzcx = l2;
    }

    public final boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (zzh)object;
        if (this.zzct != ((zzh)object).zzct) return false;
        if (!Objects.equal(this.zzk, ((zzh)object).zzk)) return false;
        if (this.status != ((zzh)object).status) return false;
        if (this.zzcw != ((zzh)object).zzcw) return false;
        if (this.zzcx != ((zzh)object).zzcx) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzct, this.zzk, this.status, this.zzcw, this.zzcx);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzct);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzk, n, false);
        SafeParcelWriter.writeInt(parcel, 4, this.status);
        SafeParcelWriter.writeLong(parcel, 5, this.zzcw);
        SafeParcelWriter.writeLong(parcel, 6, this.zzcx);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


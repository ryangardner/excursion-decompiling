/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.events.zzf;
import java.util.List;

public final class zze
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zze> CREATOR = new zzf();
    private final int zzbw;
    private final boolean zzbx;
    private final List<DriveSpace> zzby;

    zze(int n, boolean bl, List<DriveSpace> list) {
        this.zzbw = n;
        this.zzbx = bl;
        this.zzby = list;
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
        if (!Objects.equal(this.zzby, ((zze)object).zzby)) return false;
        if (this.zzbw != ((zze)object).zzbw) return false;
        if (this.zzbx != ((zze)object).zzbx) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzby, this.zzbw, this.zzbx);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzbw);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzbx);
        SafeParcelWriter.writeTypedList(parcel, 4, this.zzby, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


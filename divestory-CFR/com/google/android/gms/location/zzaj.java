/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.zzak;

public final class zzaj
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzaj> CREATOR = new zzak();
    private final int zzar;
    private final int zzas;
    private final long zzat;
    private final long zzbt;

    zzaj(int n, int n2, long l, long l2) {
        this.zzas = n;
        this.zzar = n2;
        this.zzbt = l;
        this.zzat = l2;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (zzaj)object;
        if (this.zzas != ((zzaj)object).zzas) return false;
        if (this.zzar != ((zzaj)object).zzar) return false;
        if (this.zzbt != ((zzaj)object).zzbt) return false;
        if (this.zzat != ((zzaj)object).zzat) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzar, this.zzas, this.zzat, this.zzbt);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkLocationStatus:");
        stringBuilder.append(" Wifi status: ");
        stringBuilder.append(this.zzas);
        stringBuilder.append(" Cell status: ");
        stringBuilder.append(this.zzar);
        stringBuilder.append(" elapsed time NS: ");
        stringBuilder.append(this.zzat);
        stringBuilder.append(" system time ms: ");
        stringBuilder.append(this.zzbt);
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzas);
        SafeParcelWriter.writeInt(parcel, 2, this.zzar);
        SafeParcelWriter.writeLong(parcel, 3, this.zzbt);
        SafeParcelWriter.writeLong(parcel, 4, this.zzat);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


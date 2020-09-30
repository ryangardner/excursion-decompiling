/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.zzb;

public class Feature
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new zzb();
    private final String zza;
    @Deprecated
    private final int zzb;
    private final long zzc;

    public Feature(String string2, int n, long l) {
        this.zza = string2;
        this.zzb = n;
        this.zzc = l;
    }

    public Feature(String string2, long l) {
        this.zza = string2;
        this.zzc = l;
        this.zzb = -1;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Feature)) return false;
        object = (Feature)object;
        if (this.getName() == null || !this.getName().equals(((Feature)object).getName())) {
            if (this.getName() != null) return false;
            if (((Feature)object).getName() != null) return false;
        }
        if (this.getVersion() != ((Feature)object).getVersion()) return false;
        return true;
    }

    public String getName() {
        return this.zza;
    }

    public long getVersion() {
        long l;
        long l2 = l = this.zzc;
        if (l != -1L) return l2;
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(this.getName(), this.getVersion());
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", this.getName()).add("version", this.getVersion()).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.getName(), false);
        SafeParcelWriter.writeInt(parcel, 2, this.zzb);
        SafeParcelWriter.writeLong(parcel, 3, this.getVersion());
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


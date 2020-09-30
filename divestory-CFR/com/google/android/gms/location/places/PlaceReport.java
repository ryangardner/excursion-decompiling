/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.places.zza;

public class PlaceReport
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<PlaceReport> CREATOR = new zza();
    private final String tag;
    private final int versionCode;
    private final String zza;
    private final String zzb;

    PlaceReport(int n, String string2, String string3, String string4) {
        this.versionCode = n;
        this.zza = string2;
        this.tag = string3;
        this.zzb = string4;
    }

    public static PlaceReport create(String string2, String string3) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotEmpty(string3);
        Preconditions.checkNotEmpty("unknown");
        Preconditions.checkArgument(true, "Invalid source");
        return new PlaceReport(1, string2, string3, "unknown");
    }

    public boolean equals(Object object) {
        if (!(object instanceof PlaceReport)) {
            return false;
        }
        object = (PlaceReport)object;
        if (!Objects.equal(this.zza, ((PlaceReport)object).zza)) return false;
        if (!Objects.equal(this.tag, ((PlaceReport)object).tag)) return false;
        if (!Objects.equal(this.zzb, ((PlaceReport)object).zzb)) return false;
        return true;
    }

    public String getPlaceId() {
        return this.zza;
    }

    public String getTag() {
        return this.tag;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.tag, this.zzb);
    }

    public String toString() {
        Objects.ToStringHelper toStringHelper = Objects.toStringHelper(this);
        toStringHelper.add("placeId", this.zza);
        toStringHelper.add("tag", this.tag);
        if ("unknown".equals(this.zzb)) return toStringHelper.toString();
        toStringHelper.add("source", this.zzb);
        return toStringHelper.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.getPlaceId(), false);
        SafeParcelWriter.writeString(parcel, 3, this.getTag(), false);
        SafeParcelWriter.writeString(parcel, 4, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.zzaa;
import com.google.android.gms.location.zzaj;
import java.util.Arrays;

public final class LocationAvailability
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationAvailability> CREATOR = new zzaa();
    @Deprecated
    private int zzar;
    @Deprecated
    private int zzas;
    private long zzat;
    private int zzau;
    private zzaj[] zzav;

    LocationAvailability(int n, int n2, int n3, long l, zzaj[] arrzzaj) {
        this.zzau = n;
        this.zzar = n2;
        this.zzas = n3;
        this.zzat = l;
        this.zzav = arrzzaj;
    }

    public static LocationAvailability extractLocationAvailability(Intent intent) {
        if (LocationAvailability.hasLocationAvailability(intent)) return (LocationAvailability)intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
        return null;
    }

    public static boolean hasLocationAvailability(Intent intent) {
        if (intent != null) return intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
        return false;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (LocationAvailability)object;
        if (this.zzar != ((LocationAvailability)object).zzar) return false;
        if (this.zzas != ((LocationAvailability)object).zzas) return false;
        if (this.zzat != ((LocationAvailability)object).zzat) return false;
        if (this.zzau != ((LocationAvailability)object).zzau) return false;
        if (!Arrays.equals(this.zzav, ((LocationAvailability)object).zzav)) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzau, this.zzar, this.zzas, this.zzat, this.zzav);
    }

    public final boolean isLocationAvailable() {
        if (this.zzau >= 1000) return false;
        return true;
    }

    public final String toString() {
        boolean bl = this.isLocationAvailable();
        StringBuilder stringBuilder = new StringBuilder(48);
        stringBuilder.append("LocationAvailability[isLocationAvailable: ");
        stringBuilder.append(bl);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzar);
        SafeParcelWriter.writeInt(parcel, 2, this.zzas);
        SafeParcelWriter.writeLong(parcel, 3, this.zzat);
        SafeParcelWriter.writeInt(parcel, 4, this.zzau);
        SafeParcelWriter.writeTypedArray((Parcel)parcel, (int)5, (Parcelable[])this.zzav, (int)n, (boolean)false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


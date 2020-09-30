/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.location.Location
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.zzac;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationResult> CREATOR;
    static final List<Location> zzbb;
    private final List<Location> zzbc;

    static {
        zzbb = Collections.emptyList();
        CREATOR = new zzac();
    }

    LocationResult(List<Location> list) {
        this.zzbc = list;
    }

    public static LocationResult create(List<Location> list) {
        List<Location> list2 = list;
        if (list != null) return new LocationResult(list2);
        list2 = zzbb;
        return new LocationResult(list2);
    }

    public static LocationResult extractResult(Intent intent) {
        if (LocationResult.hasResult(intent)) return (LocationResult)intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
        return null;
    }

    public static boolean hasResult(Intent intent) {
        if (intent != null) return intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
        return false;
    }

    public final boolean equals(Object iterator2) {
        Location location;
        Location location2;
        if (!(iterator2 instanceof LocationResult)) {
            return false;
        }
        iterator2 = (LocationResult)((Object)iterator2);
        if (((LocationResult)iterator2).zzbc.size() != this.zzbc.size()) {
            return false;
        }
        iterator2 = ((LocationResult)iterator2).zzbc.iterator();
        Iterator<Location> iterator3 = this.zzbc.iterator();
        do {
            if (!iterator2.hasNext()) return true;
            location2 = iterator3.next();
            location = iterator2.next();
        } while (location2.getTime() == location.getTime());
        return false;
    }

    public final Location getLastLocation() {
        int n = this.zzbc.size();
        if (n != 0) return this.zzbc.get(n - 1);
        return null;
    }

    public final List<Location> getLocations() {
        return this.zzbc;
    }

    public final int hashCode() {
        Iterator<Location> iterator2 = this.zzbc.iterator();
        int n = 17;
        while (iterator2.hasNext()) {
            long l = iterator2.next().getTime();
            n = n * 31 + (int)(l ^ l >>> 32);
        }
        return n;
    }

    public final String toString() {
        String string2 = String.valueOf(this.zzbc);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 27);
        stringBuilder.append("LocationResult[locations: ");
        stringBuilder.append(string2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.getLocations(), false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


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
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.zzah;

public final class LocationSettingsResult
extends AbstractSafeParcelable
implements Result {
    public static final Parcelable.Creator<LocationSettingsResult> CREATOR = new zzah();
    private final Status zzbl;
    private final LocationSettingsStates zzbm;

    public LocationSettingsResult(Status status) {
        this(status, null);
    }

    public LocationSettingsResult(Status status, LocationSettingsStates locationSettingsStates) {
        this.zzbl = status;
        this.zzbm = locationSettingsStates;
    }

    public final LocationSettingsStates getLocationSettingsStates() {
        return this.zzbm;
    }

    @Override
    public final Status getStatus() {
        return this.zzbl;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.getStatus(), n, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.getLocationSettingsStates(), n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


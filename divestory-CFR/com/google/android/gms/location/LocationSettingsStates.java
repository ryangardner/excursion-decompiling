/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.location.zzai;

public final class LocationSettingsStates
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LocationSettingsStates> CREATOR = new zzai();
    private final boolean zzbn;
    private final boolean zzbo;
    private final boolean zzbp;
    private final boolean zzbq;
    private final boolean zzbr;
    private final boolean zzbs;

    public LocationSettingsStates(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6) {
        this.zzbn = bl;
        this.zzbo = bl2;
        this.zzbp = bl3;
        this.zzbq = bl4;
        this.zzbr = bl5;
        this.zzbs = bl6;
    }

    public static LocationSettingsStates fromIntent(Intent intent) {
        return SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
    }

    public final boolean isBlePresent() {
        return this.zzbs;
    }

    public final boolean isBleUsable() {
        return this.zzbp;
    }

    public final boolean isGpsPresent() {
        return this.zzbq;
    }

    public final boolean isGpsUsable() {
        return this.zzbn;
    }

    public final boolean isLocationPresent() {
        if (this.zzbq) return true;
        if (!this.zzbr) return false;
        return true;
    }

    public final boolean isLocationUsable() {
        if (this.zzbn) return true;
        if (!this.zzbo) return false;
        return true;
    }

    public final boolean isNetworkLocationPresent() {
        return this.zzbr;
    }

    public final boolean isNetworkLocationUsable() {
        return this.zzbo;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.isGpsUsable());
        SafeParcelWriter.writeBoolean(parcel, 2, this.isNetworkLocationUsable());
        SafeParcelWriter.writeBoolean(parcel, 3, this.isBleUsable());
        SafeParcelWriter.writeBoolean(parcel, 4, this.isGpsPresent());
        SafeParcelWriter.writeBoolean(parcel, 5, this.isNetworkLocationPresent());
        SafeParcelWriter.writeBoolean(parcel, 6, this.isBlePresent());
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


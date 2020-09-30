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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.internal.drive.zzgp;

public final class zzgo
extends AbstractSafeParcelable
implements TransferPreferences {
    public static final Parcelable.Creator<zzgo> CREATOR = new zzgp();
    private final boolean zzbm;
    private final int zzbn;
    private final int zzgy;

    zzgo(int n, int n2, boolean bl) {
        this.zzgy = n;
        this.zzbn = n2;
        this.zzbm = bl;
    }

    @Override
    public final int getBatteryUsagePreference() {
        return this.zzbn;
    }

    @Override
    public final int getNetworkPreference() {
        return this.zzgy;
    }

    @Override
    public final boolean isRoamingAllowed() {
        return this.zzbm;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzgy);
        SafeParcelWriter.writeInt(parcel, 3, this.zzbn);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzbm);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


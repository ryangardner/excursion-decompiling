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
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.internal.drive.zzej;

@Deprecated
public final class zzei
extends AbstractSafeParcelable
implements FileUploadPreferences {
    public static final Parcelable.Creator<zzei> CREATOR = new zzej();
    private int zzbn;
    private int zzgy;
    private boolean zzgz;

    public zzei(int n, int n2, boolean bl) {
        this.zzgy = n;
        this.zzbn = n2;
        this.zzgz = bl;
    }

    public zzei(TransferPreferences transferPreferences) {
        this(transferPreferences.getNetworkPreference(), transferPreferences.getBatteryUsagePreference(), transferPreferences.isRoamingAllowed());
    }

    private static boolean zzh(int n) {
        if (n == 1) return true;
        if (n == 2) return true;
        return false;
    }

    private static boolean zzi(int n) {
        if (n == 256) return true;
        if (n == 257) return true;
        return false;
    }

    @Override
    public final int getBatteryUsagePreference() {
        if (zzei.zzi(this.zzbn)) return this.zzbn;
        return 0;
    }

    @Override
    public final int getNetworkTypePreference() {
        if (zzei.zzh(this.zzgy)) return this.zzgy;
        return 0;
    }

    @Override
    public final boolean isRoamingAllowed() {
        return this.zzgz;
    }

    @Override
    public final void setBatteryUsagePreference(int n) {
        if (!zzei.zzi(n)) throw new IllegalArgumentException("Invalid battery usage preference value.");
        this.zzbn = n;
    }

    @Override
    public final void setNetworkTypePreference(int n) {
        if (!zzei.zzh(n)) throw new IllegalArgumentException("Invalid data connection preference value.");
        this.zzgy = n;
    }

    @Override
    public final void setRoamingAllowed(boolean bl) {
        this.zzgz = bl;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzgy);
        SafeParcelWriter.writeInt(parcel, 3, this.zzbn);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzgz);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzac;

public final class zzab
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzab> CREATOR = new zzac();
    private final DriveId zzdd;

    public zzab(DriveId driveId) {
        this.zzdd = driveId;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdd, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


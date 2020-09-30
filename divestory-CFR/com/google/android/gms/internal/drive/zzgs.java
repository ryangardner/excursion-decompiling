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
import com.google.android.gms.drive.events.zzt;
import com.google.android.gms.internal.drive.zzgt;

public final class zzgs
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgs> CREATOR = new zzgt();
    private final int zzda;
    private final zzt zzdc;
    private final DriveId zzk;

    public zzgs(DriveId driveId, int n) {
        this(driveId, n, null);
    }

    zzgs(DriveId driveId, int n, zzt zzt2) {
        this.zzk = driveId;
        this.zzda = n;
        this.zzdc = zzt2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, n, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzda);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdc, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


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
import com.google.android.gms.internal.drive.zzgx;
import java.util.List;

public final class zzgw
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgw> CREATOR = new zzgx();
    private final DriveId zzis;
    private final List<DriveId> zzit;

    public zzgw(DriveId driveId, List<DriveId> list) {
        this.zzis = driveId;
        this.zzit = list;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzis, n, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzit, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


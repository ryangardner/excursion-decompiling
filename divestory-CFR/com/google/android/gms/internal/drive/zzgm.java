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
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.internal.drive.zzgn;

public final class zzgm
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzgm> CREATOR = new zzgn();
    private final String zzba;
    private final String[] zzbb;
    private final DriveId zzbd;
    private final FilterHolder zzbe;

    public zzgm(String string2, String[] arrstring, DriveId driveId, FilterHolder filterHolder) {
        this.zzba = string2;
        this.zzbb = arrstring;
        this.zzbd = driveId;
        this.zzbe = filterHolder;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzba, false);
        SafeParcelWriter.writeStringArray(parcel, 3, this.zzbb, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzbd, n, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzbe, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


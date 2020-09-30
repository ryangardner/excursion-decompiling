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
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzz;

public final class zzy
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzy> CREATOR = new zzz();
    private final MetadataBundle zzdn;
    private final DriveId zzdp;

    public zzy(DriveId driveId, MetadataBundle metadataBundle) {
        this.zzdp = Preconditions.checkNotNull(driveId);
        this.zzdn = Preconditions.checkNotNull(metadataBundle);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdp, n, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdn, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzx;

public final class zzw
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzw> CREATOR = new zzx();
    private final String zzan;
    private final Contents zzdf;
    private final MetadataBundle zzdn;
    private final Integer zzdo;
    private final DriveId zzdp;
    private final boolean zzdq;
    private final int zzdr;
    private final int zzds;

    public zzw(DriveId driveId, MetadataBundle metadataBundle, int n, int n2, ExecutionOptions executionOptions) {
        this(driveId, metadataBundle, null, n2, executionOptions.zzm(), executionOptions.zzl(), executionOptions.zzn(), n);
    }

    zzw(DriveId driveId, MetadataBundle metadataBundle, Contents contents, int n, boolean bl, String string2, int n2, int n3) {
        if (contents != null && n3 != 0) {
            boolean bl2 = contents.getRequestId() == n3;
            Preconditions.checkArgument(bl2, "inconsistent contents reference");
        }
        if (n == 0 && contents == null) {
            if (n3 == 0) throw new IllegalArgumentException("Need a valid contents");
        }
        this.zzdp = Preconditions.checkNotNull(driveId);
        this.zzdn = Preconditions.checkNotNull(metadataBundle);
        this.zzdf = contents;
        this.zzdo = n;
        this.zzan = string2;
        this.zzdr = n2;
        this.zzdq = bl;
        this.zzds = n3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdp, n, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzdn, n, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdf, n, false);
        SafeParcelWriter.writeIntegerObject(parcel, 5, this.zzdo, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzdq);
        SafeParcelWriter.writeString(parcel, 7, this.zzan, false);
        SafeParcelWriter.writeInt(parcel, 8, this.zzdr);
        SafeParcelWriter.writeInt(parcel, 9, this.zzds);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


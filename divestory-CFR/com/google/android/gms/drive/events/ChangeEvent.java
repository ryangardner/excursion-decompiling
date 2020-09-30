/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ResourceEvent;
import com.google.android.gms.drive.events.zza;
import java.util.Locale;

public final class ChangeEvent
extends AbstractSafeParcelable
implements ResourceEvent {
    public static final Parcelable.Creator<ChangeEvent> CREATOR = new zza();
    private final int zzbu;
    private final DriveId zzk;

    public ChangeEvent(DriveId driveId, int n) {
        this.zzk = driveId;
        this.zzbu = n;
    }

    @Override
    public final DriveId getDriveId() {
        return this.zzk;
    }

    @Override
    public final int getType() {
        return 1;
    }

    public final boolean hasBeenDeleted() {
        if ((this.zzbu & 4) == 0) return false;
        return true;
    }

    public final boolean hasContentChanged() {
        if ((this.zzbu & 2) == 0) return false;
        return true;
    }

    public final boolean hasMetadataChanged() {
        if ((this.zzbu & 1) == 0) return false;
        return true;
    }

    public final String toString() {
        return String.format(Locale.US, "ChangeEvent [id=%s,changeFlags=%x]", this.zzk, this.zzbu);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzk, n, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzbu);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}


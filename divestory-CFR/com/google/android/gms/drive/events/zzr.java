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
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzs;
import com.google.android.gms.internal.drive.zzh;

public final class zzr
extends AbstractSafeParcelable
implements DriveEvent {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private final zzh zzcs;

    public zzr(zzh zzh2) {
        this.zzcs = zzh2;
    }

    public final boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (zzr)object;
        return Objects.equal(this.zzcs, ((zzr)object).zzcs);
    }

    @Override
    public final int getType() {
        return 8;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzcs);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzcs, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final zzh zzac() {
        return this.zzcs;
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzw;
import com.google.android.gms.internal.drive.zzh;
import java.util.List;

public final class zzv
extends AbstractSafeParcelable
implements DriveEvent {
    public static final Parcelable.Creator<zzv> CREATOR = new zzw();
    private final List<zzh> zzcu;

    public zzv(List<zzh> list) {
        this.zzcu = list;
    }

    public final boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (zzv)object;
        return Objects.equal(this.zzcu, ((zzv)object).zzcu);
    }

    @Override
    public final int getType() {
        return 7;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzcu);
    }

    public final String toString() {
        return String.format("TransferStateEvent[%s]", TextUtils.join((CharSequence)"','", this.zzcu));
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzcu, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


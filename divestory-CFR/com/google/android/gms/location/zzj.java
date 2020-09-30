/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.zzk;

public final class zzj
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    private boolean zzt;
    private long zzu;
    private float zzv;
    private long zzw;
    private int zzx;

    public zzj() {
        this(true, 50L, 0.0f, Long.MAX_VALUE, Integer.MAX_VALUE);
    }

    zzj(boolean bl, long l, float f, long l2, int n) {
        this.zzt = bl;
        this.zzu = l;
        this.zzv = f;
        this.zzw = l2;
        this.zzx = n;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzj)) {
            return false;
        }
        object = (zzj)object;
        if (this.zzt != ((zzj)object).zzt) return false;
        if (this.zzu != ((zzj)object).zzu) return false;
        if (Float.compare(this.zzv, ((zzj)object).zzv) != 0) return false;
        if (this.zzw != ((zzj)object).zzw) return false;
        if (this.zzx != ((zzj)object).zzx) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzt, this.zzu, Float.valueOf(this.zzv), this.zzw, this.zzx);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeviceOrientationRequest[mShouldUseMag=");
        stringBuilder.append(this.zzt);
        stringBuilder.append(" mMinimumSamplingPeriodMs=");
        stringBuilder.append(this.zzu);
        stringBuilder.append(" mSmallestAngleChangeRadians=");
        stringBuilder.append(this.zzv);
        long l = this.zzw;
        if (l != Long.MAX_VALUE) {
            long l2 = SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            stringBuilder.append(l - l2);
            stringBuilder.append("ms");
        }
        if (this.zzx != Integer.MAX_VALUE) {
            stringBuilder.append(" num=");
            stringBuilder.append(this.zzx);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.zzt);
        SafeParcelWriter.writeLong(parcel, 2, this.zzu);
        SafeParcelWriter.writeFloat(parcel, 3, this.zzv);
        SafeParcelWriter.writeLong(parcel, 4, this.zzw);
        SafeParcelWriter.writeInt(parcel, 5, this.zzx);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


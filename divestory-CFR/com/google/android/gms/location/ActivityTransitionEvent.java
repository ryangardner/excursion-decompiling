/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.zzd;

public class ActivityTransitionEvent
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ActivityTransitionEvent> CREATOR = new zzd();
    private final int zzi;
    private final int zzj;
    private final long zzk;

    public ActivityTransitionEvent(int n, int n2, long l) {
        DetectedActivity.zzb(n);
        ActivityTransition.zza(n2);
        this.zzi = n;
        this.zzj = n2;
        this.zzk = l;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ActivityTransitionEvent)) {
            return false;
        }
        object = (ActivityTransitionEvent)object;
        if (this.zzi != ((ActivityTransitionEvent)object).zzi) return false;
        if (this.zzj != ((ActivityTransitionEvent)object).zzj) return false;
        if (this.zzk != ((ActivityTransitionEvent)object).zzk) return false;
        return true;
    }

    public int getActivityType() {
        return this.zzi;
    }

    public long getElapsedRealTimeNanos() {
        return this.zzk;
    }

    public int getTransitionType() {
        return this.zzj;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzi, this.zzj, this.zzk);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.zzi;
        StringBuilder stringBuilder2 = new StringBuilder(24);
        stringBuilder2.append("ActivityType ");
        stringBuilder2.append(n);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" ");
        n = this.zzj;
        stringBuilder2 = new StringBuilder(26);
        stringBuilder2.append("TransitionType ");
        stringBuilder2.append(n);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" ");
        long l = this.zzk;
        stringBuilder2 = new StringBuilder(41);
        stringBuilder2.append("ElapsedRealTimeNanos ");
        stringBuilder2.append(l);
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.getActivityType());
        SafeParcelWriter.writeInt(parcel, 2, this.getTransitionType());
        SafeParcelWriter.writeLong(parcel, 3, this.getElapsedRealTimeNanos());
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


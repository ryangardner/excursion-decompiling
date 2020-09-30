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
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.zzc;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ActivityTransition
extends AbstractSafeParcelable {
    public static final int ACTIVITY_TRANSITION_ENTER = 0;
    public static final int ACTIVITY_TRANSITION_EXIT = 1;
    public static final Parcelable.Creator<ActivityTransition> CREATOR = new zzc();
    private final int zzi;
    private final int zzj;

    ActivityTransition(int n, int n2) {
        this.zzi = n;
        this.zzj = n2;
    }

    public static void zza(int n) {
        boolean bl = true;
        if (n < 0 || n > 1) {
            bl = false;
        }
        StringBuilder stringBuilder = new StringBuilder(41);
        stringBuilder.append("Transition type ");
        stringBuilder.append(n);
        stringBuilder.append(" is not valid.");
        Preconditions.checkArgument(bl, stringBuilder.toString());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ActivityTransition)) {
            return false;
        }
        object = (ActivityTransition)object;
        if (this.zzi != ((ActivityTransition)object).zzi) return false;
        if (this.zzj != ((ActivityTransition)object).zzj) return false;
        return true;
    }

    public int getActivityType() {
        return this.zzi;
    }

    public int getTransitionType() {
        return this.zzj;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzi, this.zzj);
    }

    public String toString() {
        int n = this.zzi;
        int n2 = this.zzj;
        StringBuilder stringBuilder = new StringBuilder(75);
        stringBuilder.append("ActivityTransition [mActivityType=");
        stringBuilder.append(n);
        stringBuilder.append(", mTransitionType=");
        stringBuilder.append(n2);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.getActivityType());
        SafeParcelWriter.writeInt(parcel, 2, this.getTransitionType());
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }

    public static class Builder {
        private int zzi = -1;
        private int zzj = -1;

        public ActivityTransition build() {
            int n = this.zzi;
            boolean bl = true;
            boolean bl2 = n != -1;
            Preconditions.checkState(bl2, "Activity type not set.");
            bl2 = this.zzj != -1 ? bl : false;
            Preconditions.checkState(bl2, "Activity transition type not set.");
            return new ActivityTransition(this.zzi, this.zzj);
        }

        public Builder setActivityTransition(int n) {
            ActivityTransition.zza(n);
            this.zzj = n;
            return this;
        }

        public Builder setActivityType(int n) {
            DetectedActivity.zzb(n);
            this.zzi = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SupportedActivityTransition {
    }

}


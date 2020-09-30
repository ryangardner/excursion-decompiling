/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.zzg;
import java.util.Collections;
import java.util.List;

public class ActivityTransitionResult
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ActivityTransitionResult> CREATOR = new zzg();
    private final List<ActivityTransitionEvent> zzn;

    public ActivityTransitionResult(List<ActivityTransitionEvent> list) {
        Preconditions.checkNotNull(list, "transitionEvents list can't be null.");
        if (!list.isEmpty()) {
            for (int i = 1; i < list.size(); ++i) {
                boolean bl = list.get(i).getElapsedRealTimeNanos() >= list.get(i - 1).getElapsedRealTimeNanos();
                Preconditions.checkArgument(bl);
            }
        }
        this.zzn = Collections.unmodifiableList(list);
    }

    public static ActivityTransitionResult extractResult(Intent intent) {
        if (ActivityTransitionResult.hasResult(intent)) return SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT", CREATOR);
        return null;
    }

    public static boolean hasResult(Intent intent) {
        if (intent != null) return intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT");
        return false;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() == object.getClass()) return this.zzn.equals(((ActivityTransitionResult)object).zzn);
        return false;
    }

    public List<ActivityTransitionEvent> getTransitionEvents() {
        return this.zzn;
    }

    public int hashCode() {
        return this.zzn.hashCode();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.getTransitionEvents(), false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


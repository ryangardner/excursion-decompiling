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
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.zze;
import com.google.android.gms.location.zzf;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class ActivityTransitionRequest
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ActivityTransitionRequest> CREATOR = new zzf();
    public static final Comparator<ActivityTransition> IS_SAME_TRANSITION = new zze();
    private final String tag;
    private final List<ActivityTransition> zzl;
    private final List<ClientIdentity> zzm;

    public ActivityTransitionRequest(List<ActivityTransition> list) {
        this(list, null, null);
    }

    public ActivityTransitionRequest(List<ActivityTransition> list, String string2, List<ClientIdentity> list2) {
        Preconditions.checkNotNull(list, "transitions can't be null");
        boolean bl = list.size() > 0;
        Preconditions.checkArgument(bl, "transitions can't be empty.");
        TreeSet<ActivityTransition> treeSet = new TreeSet<ActivityTransition>(IS_SAME_TRANSITION);
        for (ActivityTransition activityTransition : list) {
            Preconditions.checkArgument(treeSet.add(activityTransition), String.format("Found duplicated transition: %s.", activityTransition));
        }
        this.zzl = Collections.unmodifiableList(list);
        this.tag = string2;
        list = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.zzm = list;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (ActivityTransitionRequest)object;
        if (!Objects.equal(this.zzl, ((ActivityTransitionRequest)object).zzl)) return false;
        if (!Objects.equal(this.tag, ((ActivityTransitionRequest)object).tag)) return false;
        if (!Objects.equal(this.zzm, ((ActivityTransitionRequest)object).zzm)) return false;
        return true;
    }

    public int hashCode() {
        int n = this.zzl.hashCode();
        Object object = this.tag;
        int n2 = 0;
        int n3 = object != null ? ((String)object).hashCode() : 0;
        object = this.zzm;
        if (object == null) return (n * 31 + n3) * 31 + n2;
        n2 = object.hashCode();
        return (n * 31 + n3) * 31 + n2;
    }

    public void serializeToIntentExtra(Intent intent) {
        SafeParcelableSerializer.serializeToIntentExtra(this, intent, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_REQUEST");
    }

    public String toString() {
        String string2 = String.valueOf(this.zzl);
        String string3 = this.tag;
        String string4 = String.valueOf(this.zzm);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 61 + String.valueOf(string3).length() + String.valueOf(string4).length());
        stringBuilder.append("ActivityTransitionRequest [mTransitions=");
        stringBuilder.append(string2);
        stringBuilder.append(", mTag='");
        stringBuilder.append(string3);
        stringBuilder.append('\'');
        stringBuilder.append(", mClients=");
        stringBuilder.append(string4);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzl, false);
        SafeParcelWriter.writeString(parcel, 2, this.tag, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzm, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


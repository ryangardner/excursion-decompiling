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
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzbh;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.zzq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GeofencingRequest
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GeofencingRequest> CREATOR = new zzq();
    public static final int INITIAL_TRIGGER_DWELL = 4;
    public static final int INITIAL_TRIGGER_ENTER = 1;
    public static final int INITIAL_TRIGGER_EXIT = 2;
    private final String tag;
    private final List<zzbh> zzap;
    private final int zzaq;

    GeofencingRequest(List<zzbh> list, int n, String string2) {
        this.zzap = list;
        this.zzaq = n;
        this.tag = string2;
    }

    public List<Geofence> getGeofences() {
        ArrayList<Geofence> arrayList = new ArrayList<Geofence>();
        arrayList.addAll(this.zzap);
        return arrayList;
    }

    public int getInitialTrigger() {
        return this.zzaq;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GeofencingRequest[");
        stringBuilder.append("geofences=");
        stringBuilder.append(this.zzap);
        int n = this.zzaq;
        CharSequence charSequence = new StringBuilder(30);
        ((StringBuilder)charSequence).append(", initialTrigger=");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(", ");
        stringBuilder.append(((StringBuilder)charSequence).toString());
        charSequence = String.valueOf(this.tag);
        charSequence = ((String)charSequence).length() != 0 ? "tag=".concat((String)charSequence) : new String("tag=");
        stringBuilder.append((String)charSequence);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzap, false);
        SafeParcelWriter.writeInt(parcel, 2, this.getInitialTrigger());
        SafeParcelWriter.writeString(parcel, 3, this.tag, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }

    public static final class Builder {
        private String tag = "";
        private final List<zzbh> zzap = new ArrayList<zzbh>();
        private int zzaq = 5;

        public final Builder addGeofence(Geofence geofence) {
            Preconditions.checkNotNull(geofence, "geofence can't be null.");
            Preconditions.checkArgument(geofence instanceof zzbh, "Geofence must be created using Geofence.Builder.");
            this.zzap.add((zzbh)geofence);
            return this;
        }

        public final Builder addGeofences(List<Geofence> object) {
            if (object == null) return this;
            if (object.isEmpty()) {
                return this;
            }
            Iterator<Geofence> iterator2 = object.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                if (object == null) continue;
                this.addGeofence((Geofence)object);
            }
            return this;
        }

        public final GeofencingRequest build() {
            Preconditions.checkArgument(this.zzap.isEmpty() ^ true, "No geofence has been added to this request.");
            return new GeofencingRequest(this.zzap, this.zzaq, this.tag);
        }

        public final Builder setInitialTrigger(int n) {
            this.zzaq = n & 7;
            return this;
        }
    }

}


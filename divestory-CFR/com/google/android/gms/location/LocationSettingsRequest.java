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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzae;
import com.google.android.gms.location.zzag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationSettingsRequest
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<LocationSettingsRequest> CREATOR = new zzag();
    private final List<LocationRequest> zzbg;
    private final boolean zzbh;
    private final boolean zzbi;
    private zzae zzbj;

    LocationSettingsRequest(List<LocationRequest> list, boolean bl, boolean bl2, zzae zzae2) {
        this.zzbg = list;
        this.zzbh = bl;
        this.zzbi = bl2;
        this.zzbj = zzae2;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, Collections.unmodifiableList(this.zzbg), false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzbh);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzbi);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzbj, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public static final class Builder {
        private boolean zzbh = false;
        private boolean zzbi = false;
        private zzae zzbj = null;
        private final ArrayList<LocationRequest> zzbk = new ArrayList();

        public final Builder addAllLocationRequests(Collection<LocationRequest> object) {
            object = object.iterator();
            while (object.hasNext()) {
                LocationRequest locationRequest = (LocationRequest)object.next();
                if (locationRequest == null) continue;
                this.zzbk.add(locationRequest);
            }
            return this;
        }

        public final Builder addLocationRequest(LocationRequest locationRequest) {
            if (locationRequest == null) return this;
            this.zzbk.add(locationRequest);
            return this;
        }

        public final LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zzbk, this.zzbh, this.zzbi, null);
        }

        public final Builder setAlwaysShow(boolean bl) {
            this.zzbh = bl;
            return this;
        }

        public final Builder setNeedBle(boolean bl) {
            this.zzbi = bl;
            return this;
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.internal.zzc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AppVisibleCustomProperties
extends AbstractSafeParcelable
implements ReflectedParcelable,
Iterable<zzc> {
    public static final Parcelable.Creator<AppVisibleCustomProperties> CREATOR = new com.google.android.gms.drive.metadata.internal.zza();
    public static final AppVisibleCustomProperties zzjb = new zza().zzbb();
    private final List<zzc> zzjc;

    AppVisibleCustomProperties(Collection<zzc> collection) {
        Preconditions.checkNotNull(collection);
        this.zzjc = new ArrayList<zzc>(collection);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (object.getClass() == this.getClass()) return this.zzba().equals(((AppVisibleCustomProperties)object).zzba());
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzjc);
    }

    @Override
    public final Iterator<zzc> iterator() {
        return this.zzjc.iterator();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzjc, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }

    public final Map<CustomPropertyKey, String> zzba() {
        HashMap<CustomPropertyKey, String> hashMap = new HashMap<CustomPropertyKey, String>(this.zzjc.size());
        Iterator<zzc> iterator2 = this.zzjc.iterator();
        while (iterator2.hasNext()) {
            zzc zzc2 = iterator2.next();
            hashMap.put(zzc2.zzje, zzc2.value);
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static final class zza {
        private final Map<CustomPropertyKey, zzc> zzjd = new HashMap<CustomPropertyKey, zzc>();

        public final zza zza(CustomPropertyKey customPropertyKey, String string2) {
            Preconditions.checkNotNull(customPropertyKey, "key");
            this.zzjd.put(customPropertyKey, new zzc(customPropertyKey, string2));
            return this;
        }

        public final zza zza(zzc zzc2) {
            Preconditions.checkNotNull(zzc2, "property");
            this.zzjd.put(zzc2.zzje, zzc2);
            return this;
        }

        public final AppVisibleCustomProperties zzbb() {
            return new AppVisibleCustomProperties(this.zzjd.values());
        }
    }

}


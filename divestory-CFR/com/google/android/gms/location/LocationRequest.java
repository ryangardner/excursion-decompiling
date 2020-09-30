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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.location.zzab;

public final class LocationRequest
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<LocationRequest> CREATOR = new zzab();
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    private int priority;
    private long zzaf;
    private long zzaw;
    private long zzax;
    private boolean zzay;
    private float zzaz;
    private long zzba;
    private int zzx;

    public LocationRequest() {
        this.priority = 102;
        this.zzaw = 3600000L;
        this.zzax = 600000L;
        this.zzay = false;
        this.zzaf = Long.MAX_VALUE;
        this.zzx = Integer.MAX_VALUE;
        this.zzaz = 0.0f;
        this.zzba = 0L;
    }

    LocationRequest(int n, long l, long l2, boolean bl, long l3, int n2, float f, long l4) {
        this.priority = n;
        this.zzaw = l;
        this.zzax = l2;
        this.zzay = bl;
        this.zzaf = l3;
        this.zzx = n2;
        this.zzaz = f;
        this.zzba = l4;
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void zza(long l) {
        if (l >= 0L) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(38);
        stringBuilder.append("invalid interval: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocationRequest)) {
            return false;
        }
        object = (LocationRequest)object;
        if (this.priority != ((LocationRequest)object).priority) return false;
        if (this.zzaw != ((LocationRequest)object).zzaw) return false;
        if (this.zzax != ((LocationRequest)object).zzax) return false;
        if (this.zzay != ((LocationRequest)object).zzay) return false;
        if (this.zzaf != ((LocationRequest)object).zzaf) return false;
        if (this.zzx != ((LocationRequest)object).zzx) return false;
        if (this.zzaz != ((LocationRequest)object).zzaz) return false;
        if (this.getMaxWaitTime() != ((LocationRequest)object).getMaxWaitTime()) return false;
        return true;
    }

    public final long getExpirationTime() {
        return this.zzaf;
    }

    public final long getFastestInterval() {
        return this.zzax;
    }

    public final long getInterval() {
        return this.zzaw;
    }

    public final long getMaxWaitTime() {
        long l = this.zzba;
        long l2 = this.zzaw;
        long l3 = l;
        if (l >= l2) return l3;
        return l2;
    }

    public final int getNumUpdates() {
        return this.zzx;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final float getSmallestDisplacement() {
        return this.zzaz;
    }

    public final int hashCode() {
        return Objects.hashCode(this.priority, this.zzaw, Float.valueOf(this.zzaz), this.zzba);
    }

    public final boolean isFastestIntervalExplicitlySet() {
        return this.zzay;
    }

    public final LocationRequest setExpirationDuration(long l) {
        long l2 = SystemClock.elapsedRealtime();
        this.zzaf = l > Long.MAX_VALUE - l2 ? Long.MAX_VALUE : l + l2;
        if (this.zzaf >= 0L) return this;
        this.zzaf = 0L;
        return this;
    }

    public final LocationRequest setExpirationTime(long l) {
        this.zzaf = l;
        if (l >= 0L) return this;
        this.zzaf = 0L;
        return this;
    }

    public final LocationRequest setFastestInterval(long l) {
        LocationRequest.zza(l);
        this.zzay = true;
        this.zzax = l;
        return this;
    }

    public final LocationRequest setInterval(long l) {
        LocationRequest.zza(l);
        this.zzaw = l;
        if (this.zzay) return this;
        this.zzax = (long)((double)l / 6.0);
        return this;
    }

    public final LocationRequest setMaxWaitTime(long l) {
        LocationRequest.zza(l);
        this.zzba = l;
        return this;
    }

    public final LocationRequest setNumUpdates(int n) {
        if (n > 0) {
            this.zzx = n;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder(31);
        stringBuilder.append("invalid numUpdates: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final LocationRequest setPriority(int n) {
        if (n != 100 && n != 102 && n != 104 && n != 105) {
            StringBuilder stringBuilder = new StringBuilder(28);
            stringBuilder.append("invalid quality: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.priority = n;
        return this;
    }

    public final LocationRequest setSmallestDisplacement(float f) {
        if (!(f < 0.0f)) {
            this.zzaz = f;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder(37);
        stringBuilder.append("invalid displacement: ");
        stringBuilder.append(f);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final String toString() {
        long l;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request[");
        int n = this.priority;
        String string2 = n != 100 ? (n != 102 ? (n != 104 ? (n != 105 ? "???" : "PRIORITY_NO_POWER") : "PRIORITY_LOW_POWER") : "PRIORITY_BALANCED_POWER_ACCURACY") : "PRIORITY_HIGH_ACCURACY";
        stringBuilder.append(string2);
        if (this.priority != 105) {
            stringBuilder.append(" requested=");
            stringBuilder.append(this.zzaw);
            stringBuilder.append("ms");
        }
        stringBuilder.append(" fastest=");
        stringBuilder.append(this.zzax);
        stringBuilder.append("ms");
        if (this.zzba > this.zzaw) {
            stringBuilder.append(" maxWait=");
            stringBuilder.append(this.zzba);
            stringBuilder.append("ms");
        }
        if (this.zzaz > 0.0f) {
            stringBuilder.append(" smallestDisplacement=");
            stringBuilder.append(this.zzaz);
            stringBuilder.append("m");
        }
        if ((l = this.zzaf) != Long.MAX_VALUE) {
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
        SafeParcelWriter.writeInt(parcel, 1, this.priority);
        SafeParcelWriter.writeLong(parcel, 2, this.zzaw);
        SafeParcelWriter.writeLong(parcel, 3, this.zzax);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzay);
        SafeParcelWriter.writeLong(parcel, 5, this.zzaf);
        SafeParcelWriter.writeInt(parcel, 6, this.zzx);
        SafeParcelWriter.writeFloat(parcel, 7, this.zzaz);
        SafeParcelWriter.writeLong(parcel, 8, this.zzba);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


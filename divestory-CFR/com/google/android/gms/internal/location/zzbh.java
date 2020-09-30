/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzbi;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

public final class zzbh
extends AbstractSafeParcelable
implements Geofence {
    public static final Parcelable.Creator<zzbh> CREATOR = new zzbi();
    private final String zzad;
    private final int zzae;
    private final short zzag;
    private final double zzah;
    private final double zzai;
    private final float zzaj;
    private final int zzak;
    private final int zzal;
    private final long zzdo;

    public zzbh(String charSequence, int n, short s, double d, double d2, float f, long l, int n2, int n3) {
        if (charSequence != null && ((String)charSequence).length() <= 100) {
            if (f <= 0.0f) {
                charSequence = new StringBuilder(31);
                ((StringBuilder)charSequence).append("invalid radius: ");
                ((StringBuilder)charSequence).append(f);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            if (!(d > 90.0) && !(d < -90.0)) {
                if (!(d2 > 180.0) && !(d2 < -180.0)) {
                    int n4 = n & 7;
                    if (n4 != 0) {
                        this.zzag = s;
                        this.zzad = charSequence;
                        this.zzah = d;
                        this.zzai = d2;
                        this.zzaj = f;
                        this.zzdo = l;
                        this.zzae = n4;
                        this.zzak = n2;
                        this.zzal = n3;
                        return;
                    }
                    charSequence = new StringBuilder(46);
                    ((StringBuilder)charSequence).append("No supported transition specified: ");
                    ((StringBuilder)charSequence).append(n);
                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder(43);
                ((StringBuilder)charSequence).append("invalid longitude: ");
                ((StringBuilder)charSequence).append(d2);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder(42);
            ((StringBuilder)charSequence).append("invalid latitude: ");
            ((StringBuilder)charSequence).append(d);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (((String)(charSequence = String.valueOf(charSequence))).length() != 0) {
            charSequence = "requestId is null or too long: ".concat((String)charSequence);
            throw new IllegalArgumentException((String)charSequence);
        }
        charSequence = new String("requestId is null or too long: ");
        throw new IllegalArgumentException((String)charSequence);
    }

    public static zzbh zza(byte[] object) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(object, 0, ((byte[])object).length);
        parcel.setDataPosition(0);
        object = (zzbh)CREATOR.createFromParcel(parcel);
        parcel.recycle();
        return object;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof zzbh)) {
            return false;
        }
        object = (zzbh)object;
        if (this.zzaj != ((zzbh)object).zzaj) {
            return false;
        }
        if (this.zzah != ((zzbh)object).zzah) {
            return false;
        }
        if (this.zzai != ((zzbh)object).zzai) {
            return false;
        }
        if (this.zzag == ((zzbh)object).zzag) return true;
        return false;
    }

    @Override
    public final String getRequestId() {
        return this.zzad;
    }

    public final int hashCode() {
        long l = Double.doubleToLongBits(this.zzah);
        int n = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.zzai);
        return ((((n + 31) * 31 + (int)(l ^ l >>> 32)) * 31 + Float.floatToIntBits(this.zzaj)) * 31 + this.zzag) * 31 + this.zzae;
    }

    public final String toString() {
        Locale locale = Locale.US;
        String string2 = this.zzag != 1 ? null : "CIRCLE";
        return String.format(locale, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", string2, this.zzad.replaceAll("\\p{C}", "?"), this.zzae, this.zzah, this.zzai, Float.valueOf(this.zzaj), this.zzak / 1000, this.zzal, this.zzdo);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.getRequestId(), false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzdo);
        SafeParcelWriter.writeShort(parcel, 3, this.zzag);
        SafeParcelWriter.writeDouble(parcel, 4, this.zzah);
        SafeParcelWriter.writeDouble(parcel, 5, this.zzai);
        SafeParcelWriter.writeFloat(parcel, 6, this.zzaj);
        SafeParcelWriter.writeInt(parcel, 7, this.zzae);
        SafeParcelWriter.writeInt(parcel, 8, this.zzak);
        SafeParcelWriter.writeInt(parcel, 9, this.zzal);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}


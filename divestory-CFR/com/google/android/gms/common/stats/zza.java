/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.stats.WakeLockEvent;
import java.util.List;

public final class zza
implements Parcelable.Creator<WakeLockEvent> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        long l;
        String string3;
        String string4;
        long l2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        long l3 = l = (l2 = 0L);
        String string5 = null;
        Object object = string5;
        String string6 = string4 = (string3 = (string2 = object));
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        float f = 0.0f;
        boolean bl = false;
        block17 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new WakeLockEvent(n2, l2, n3, string5, n4, (List<String>)object, string2, l, n5, string3, string4, f, l3, string6, bl);
            }
            int n6 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n6)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n6);
                    continue block17;
                }
                case 18: {
                    bl = SafeParcelReader.readBoolean(parcel, n6);
                    continue block17;
                }
                case 17: {
                    string6 = SafeParcelReader.createString(parcel, n6);
                    continue block17;
                }
                case 16: {
                    l3 = SafeParcelReader.readLong(parcel, n6);
                    continue block17;
                }
                case 15: {
                    f = SafeParcelReader.readFloat(parcel, n6);
                    continue block17;
                }
                case 14: {
                    n5 = SafeParcelReader.readInt(parcel, n6);
                    continue block17;
                }
                case 13: {
                    string4 = SafeParcelReader.createString(parcel, n6);
                    continue block17;
                }
                case 12: {
                    string2 = SafeParcelReader.createString(parcel, n6);
                    continue block17;
                }
                case 11: {
                    n3 = SafeParcelReader.readInt(parcel, n6);
                    continue block17;
                }
                case 10: {
                    string3 = SafeParcelReader.createString(parcel, n6);
                    continue block17;
                }
                case 8: {
                    l = SafeParcelReader.readLong(parcel, n6);
                    continue block17;
                }
                case 6: {
                    object = SafeParcelReader.createStringList(parcel, n6);
                    continue block17;
                }
                case 5: {
                    n4 = SafeParcelReader.readInt(parcel, n6);
                    continue block17;
                }
                case 4: {
                    string5 = SafeParcelReader.createString(parcel, n6);
                    continue block17;
                }
                case 2: {
                    l2 = SafeParcelReader.readLong(parcel, n6);
                    continue block17;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n6);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new WakeLockEvent[n];
    }
}


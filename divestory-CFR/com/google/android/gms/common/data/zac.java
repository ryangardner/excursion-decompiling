/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zac
implements Parcelable.Creator<DataHolder> {
    public final /* synthetic */ Object createFromParcel(Parcel object) {
        Bundle bundle;
        Bundle bundle2;
        int n = SafeParcelReader.validateObjectHeader((Parcel)object);
        Bundle bundle3 = bundle = (bundle2 = null);
        int n2 = 0;
        int n3 = 0;
        do {
            if (object.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd((Parcel)object, n);
                object = new DataHolder(n2, (String[])bundle2, (CursorWindow[])bundle, n3, bundle3);
                ((DataHolder)object).zaa();
                return object;
            }
            int n4 = SafeParcelReader.readHeader((Parcel)object);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) {
                            if (n5 != 1000) {
                                SafeParcelReader.skipUnknownField((Parcel)object, n4);
                                continue;
                            }
                            n2 = SafeParcelReader.readInt((Parcel)object, n4);
                            continue;
                        }
                        bundle3 = SafeParcelReader.createBundle((Parcel)object, n4);
                        continue;
                    }
                    n3 = SafeParcelReader.readInt((Parcel)object, n4);
                    continue;
                }
                bundle = (CursorWindow[])SafeParcelReader.createTypedArray((Parcel)object, n4, CursorWindow.CREATOR);
                continue;
            }
            bundle2 = SafeParcelReader.createStringArray((Parcel)object, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new DataHolder[n];
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.internal.zzr;
import java.util.ArrayList;
import java.util.List;

public final class zzb
implements Parcelable.Creator<Query> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Object object;
        Object object2;
        ArrayList<String> arrayList;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Object object3 = object = (object2 = null);
        ArrayList<Object> arrayList2 = arrayList = object3;
        boolean bl = false;
        boolean bl2 = false;
        block9 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new Query((zzr)object2, (String)object, (SortOrder)object3, (List<String>)arrayList, bl, (List<DriveSpace>)arrayList2, bl2);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n2)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue block9;
                }
                case 8: {
                    bl2 = SafeParcelReader.readBoolean(parcel, n2);
                    continue block9;
                }
                case 7: {
                    arrayList2 = SafeParcelReader.createTypedList(parcel, n2, DriveSpace.CREATOR);
                    continue block9;
                }
                case 6: {
                    bl = SafeParcelReader.readBoolean(parcel, n2);
                    continue block9;
                }
                case 5: {
                    arrayList = SafeParcelReader.createStringList(parcel, n2);
                    continue block9;
                }
                case 4: {
                    object3 = SafeParcelReader.createParcelable(parcel, n2, SortOrder.CREATOR);
                    continue block9;
                }
                case 3: {
                    object = SafeParcelReader.createString(parcel, n2);
                    continue block9;
                }
                case 1: 
            }
            object2 = SafeParcelReader.createParcelable(parcel, n2, zzr.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new Query[n];
    }
}


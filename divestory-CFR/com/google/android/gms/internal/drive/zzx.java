/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzw;

public final class zzx
implements Parcelable.Creator<zzw> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Object object;
        Object object2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Integer n2 = null;
        Object object3 = object2 = (object = null);
        String string2 = object3;
        boolean bl = false;
        int n3 = 0;
        int n4 = 0;
        block10 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzw((DriveId)object, (MetadataBundle)object2, (Contents)object3, n2, bl, string2, n3, n4);
            }
            int n5 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n5)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n5);
                    continue block10;
                }
                case 9: {
                    n4 = SafeParcelReader.readInt(parcel, n5);
                    continue block10;
                }
                case 8: {
                    n3 = SafeParcelReader.readInt(parcel, n5);
                    continue block10;
                }
                case 7: {
                    string2 = SafeParcelReader.createString(parcel, n5);
                    continue block10;
                }
                case 6: {
                    bl = SafeParcelReader.readBoolean(parcel, n5);
                    continue block10;
                }
                case 5: {
                    n2 = SafeParcelReader.readIntegerObject(parcel, n5);
                    continue block10;
                }
                case 4: {
                    object3 = SafeParcelReader.createParcelable(parcel, n5, Contents.CREATOR);
                    continue block10;
                }
                case 3: {
                    object2 = SafeParcelReader.createParcelable(parcel, n5, MetadataBundle.CREATOR);
                    continue block10;
                }
                case 2: 
            }
            object = SafeParcelReader.createParcelable(parcel, n5, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzw[n];
    }
}


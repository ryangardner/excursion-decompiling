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
import com.google.android.gms.internal.drive.zzm;

public final class zzn
implements Parcelable.Creator<zzm> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Object object;
        Object object2;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Object object3 = object = (object2 = null);
        String string2 = object3;
        boolean bl = false;
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = false;
        boolean bl3 = true;
        block11 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzm((DriveId)object2, (MetadataBundle)object, (Contents)object3, bl, string2, n2, n3, bl2, bl3);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n4)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n4);
                    continue block11;
                }
                case 10: {
                    bl3 = SafeParcelReader.readBoolean(parcel, n4);
                    continue block11;
                }
                case 9: {
                    bl2 = SafeParcelReader.readBoolean(parcel, n4);
                    continue block11;
                }
                case 8: {
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue block11;
                }
                case 7: {
                    n2 = SafeParcelReader.readInt(parcel, n4);
                    continue block11;
                }
                case 6: {
                    string2 = SafeParcelReader.createString(parcel, n4);
                    continue block11;
                }
                case 5: {
                    bl = SafeParcelReader.readBoolean(parcel, n4);
                    continue block11;
                }
                case 4: {
                    object3 = SafeParcelReader.createParcelable(parcel, n4, Contents.CREATOR);
                    continue block11;
                }
                case 3: {
                    object = SafeParcelReader.createParcelable(parcel, n4, MetadataBundle.CREATOR);
                    continue block11;
                }
                case 2: 
            }
            object2 = SafeParcelReader.createParcelable(parcel, n4, DriveId.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzm[n];
    }
}

